package com.e.cursorloader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.e.cursorloader.MainActivity.btn_all;
import static com.e.cursorloader.MainActivity.context;
import static com.e.cursorloader.MainActivity.cursor;
import static com.e.cursorloader.MainActivity.db;
import static com.e.cursorloader.MainActivity.lv_sms;
import static com.e.cursorloader.MainActivity.refreshDB;
import static com.e.cursorloader.MainActivity.smsAdapter;
import static com.e.cursorloader.MainActivity.smsHelper;
import static com.e.cursorloader.MainActivity.tbl_sms;
import static com.e.cursorloader.MainActivity.tv_unique;
import static com.e.cursorloader.MainActivity.whereText;

public class SMSAdapter extends CursorAdapter {

    private TextView tv_check, tv_name, tv_phone, tv_date, tv_body;

    public ArrayList<String> al_names = new ArrayList<>();

    public SMSAdapter(Context context, Cursor c) { super(context, c); }

    static ArrayList<String> al_chosung;
    // 초성 확인을 위해 배열을 리스트로 변환한다.
    private void setChosung() {
        if (al_chosung == null)
            al_chosung = new ArrayList<String>(Arrays.asList(smsHelper.chosung));
    }

    static ArrayList<String> al_jungsung;
    // 중성 확인을 위해 배열을 리스트로 변환한다.
    private void setJungsung() {
        if (al_jungsung == null)
            al_jungsung = new ArrayList<String>(Arrays.asList(smsHelper.jungsung));
    }

    // 소문자로, 앞뒤 공백 제거
    private String setCharText(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        return charText.trim();
    }

    // 본문 에디트에 입력된 키워드에 따라 데이터 필터링
    public void bodyFilter(String charText) {
        setChosung(); // 초성 배열 > 리스트
        setJungsung(); // 중성 배열 > 리스트
        charText = setCharText(charText); // 소문자로, 앞뒤 공백 제거

        if (charText.length() == 0) {
            // 에디트에 글자가 모두 지워지면 모든 데이터를 가져온다.
            cursor = smsHelper.getData();
        }
        else {
            whereText = bodyWhere(charText);
// Toast.makeText(context, whereText, Toast.LENGTH_SHORT).show();
tv_unique.append("["+charText+"]"+whereText);
            if (whereText.isEmpty()) cursor = smsHelper.getData();
            else cursor = smsHelper.getData(whereText);
        }
        smsAdapter = new SMSAdapter(context, cursor);
        lv_sms.setAdapter(smsAdapter);
    }

    // 장문(문자열) 앤드(And)검색을 위한 where 범위 설정
    private String bodyWhere(String s) {
        String whereS = "";
        String[] arr = s.split(" ");
        int len = 0;
        String last_1_letter = "";
        for (String s1:arr) {
            len = s1.length();
            if (len == 1) {
                // 온전한 글자(초성/중성 아님)일 때에만 가공한다.
                if ((al_chosung.indexOf(s1) == -1) && (al_jungsung.indexOf(s1) == -1))
                    whereS += "AND body like '%"+s1+"%' ";
            }
            else {
                last_1_letter = s1.substring(len-1, len);
                // 끝 글자가 (초성/중성)이면 잘라서 버린다.
                if ((al_chosung.indexOf(last_1_letter) != -1) || (al_jungsung.indexOf(last_1_letter) != -1))
                    s1 = s1.substring(0, len-1);
                whereS += "AND body like '%"+s1+"%' ";
            }
            // whereS += "AND body like '%"+s1+"%' ";
        }
        if (!whereS.isEmpty()) whereS = whereS.substring(3);

        return whereS;
    }

    // 이름/제목 에디트에 입력된 키워드에 따라 데이터 필터링
    public void nameFilter(String charText) {
        setJungsung(); // 중성 배열 > 리스트
        charText = setCharText(charText); // 소문자로, 앞뒤 공백 제거
        charText = charText.replaceAll(" ", ""); // space 모두 제거

        if (charText.length() == 0) {
            // 에디트에 글자가 모두 지워지면 모든 데이터를 가져온다.
            cursor = smsHelper.getData();
        }
        else {
            int size = charText.length();
            String last_1_letter = charText.substring(size-1,size);
            // 마지막 글자로 중성 입력되면 리스트를 갱신하지 않는다.
            if (al_jungsung.indexOf(last_1_letter) != -1)
                return;

            whereText = nameWhere(charText);
            cursor = smsHelper.getData(whereText);
        }
        smsAdapter = new SMSAdapter(context, cursor);
        lv_sms.setAdapter(smsAdapter);
    }

    // 단문(초성/문자열) 혼합검색을 위한 where 범위 설정
    private String nameWhere(String s) {
        String whereS = "";
        Pattern pattern = Pattern.compile("[ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ]");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            if (matcher.start() != 0) {
                whereS += " name like '%"+s.substring(0,matcher.start())+"%' AND ";
                s = s.substring(matcher.start());
            }
            // 위에서 초성 이전 가공/제거하여 초성을 첫 글자로 만든 후 ...
            if (s.length() == 1) {
                whereS += " chosung like '%"+s+"%' ";
                s = "";
                break;
            }
            else {
                whereS += " chosung like '%"+s.substring(0,1)+"%' AND ";
                s = s.substring(1);
                matcher = pattern.matcher(s);
            }
        }
        // 글자가 남아 있으면 초성 아닌 글자이다.
        if (! s.isEmpty()) whereS += " name like '%"+s+"%' ";
        return whereS;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sms_row, parent, false);
        return view;
    }

    // 선택된 레코드 아이디
    static ArrayList<Integer> al_checked = new ArrayList<>();

    private void showChecked(int chkNo) {
        if (chkNo==1) tv_check.setBackgroundResource(R.drawable.ic_check_black);
        else if (chkNo==0) tv_check.setBackgroundResource(R.drawable.ic_uncheck);
        else Toast.makeText(context, "??? chkNo 이상 - 개발자에게 문의하세요.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        tv_check = (TextView)view.findViewById(R.id.tv_check);
        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_phone = (TextView)view.findViewById(R.id.tv_phone);
        tv_date = (TextView)view.findViewById(R.id.tv_date);
        tv_body = (TextView)view.findViewById(R.id.tv_body);

        final int id = cursor.getInt(cursor.getColumnIndex("_id"));
        tv_check.setText(String.valueOf(id));

        final int chkNo = cursor.getInt(cursor.getColumnIndex("chk"));
        showChecked(chkNo);
        tv_check.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int newNo = (chkNo==0) ? 1 : 0;
                // 선택 아이템 리스트 추가/제거
                if (newNo==1) al_checked.add(id);
                else {
                    int p = al_checked.indexOf(id);
                    if (p != -1) al_checked.remove(p);
                }
                // 데이터 업데이트
                ContentValues values = new ContentValues();
                values.put("chk", newNo);
                db.update(tbl_sms, values, "_id = ?", new String[] { String.valueOf(id)});
                // 화면 갱신
                int pos = lv_sms.getFirstVisiblePosition();
                refreshDB(pos);
            }
        });

        final String name = cursor.getString(cursor.getColumnIndex("name"));
        final String phone = cursor.getString(cursor.getColumnIndex("phone"));
        final long date = cursor.getLong(cursor.getColumnIndex("date"));
        final String body = cursor.getString(cursor.getColumnIndex("body"));

        tv_name.setText(name);
        tv_name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // editRow(id, name, body);
                loadByName("name = '"+name+"'", new String[]{name});
            }
        });

        tv_phone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "??? : "+MainActivity.phonNumFormat(phone), Toast.LENGTH_SHORT).show();
            }
        });

        final String sDate = new SimpleDateFormat("yyyy-MM-dd HH", Locale.KOREA).format(date);
        tv_date.setText(sDate);
        tv_date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, new SimpleDateFormat("HH:mm:ss//SSS", Locale.KOREA).format(date), Toast.LENGTH_SHORT).show();
            }
        });

        tv_body.setText(body);
        tv_body.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bindID = id;
                bindName = name;
                bindBody = body;
                MainActivity.editRow();
            }
        });

    }

    static String bindName = "";
    static String bindBody = "";
    static int bindID;

    // name 칼럼이 같은 Row 가져오기
    private void loadByName(String whereS, String[] whereArray) {
        cursor = smsHelper.getData(whereS);
        smsAdapter = new SMSAdapter(context, cursor);
        lv_sms.setAdapter(smsAdapter);
        tv_unique.setVisibility(View.GONE);
        btn_all.setVisibility(View.VISIBLE);
    }

}