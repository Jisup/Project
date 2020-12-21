package com.e.cursorloader;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.e.cursorloader.R;
import com.e.cursorloader.SMSAdapter;
import com.e.cursorloader.SMSHelper;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import static com.e.cursorloader.ReadMmsSms.al_setup;
import static com.e.cursorloader.ReadMmsSms.database;
import static com.e.cursorloader.ReadMmsSms.pay;
import static com.e.cursorloader.ReadMmsSms.paycount;
import static com.e.cursorloader.ReadMmsSms.grade;
import static com.e.cursorloader.SMSAdapter.bindBody;
import static com.e.cursorloader.SMSAdapter.bindID;
import static com.e.cursorloader.SMSAdapter.bindName;
import static com.e.cursorloader.SMSHelper.contentResolver;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static String whereText = "";
    static Handler mHandler;

    static long crTime = System.currentTimeMillis();
    static SQLiteDatabase db;
    static Cursor cursor;

    static SMSHelper smsHelper;
    static SMSAdapter smsAdapter;
    static ListView lv_sms;

    final static String dbSMS = "sms.db";
    final static String tbl_sms = "smstable";
    final static int dbVer_sms = 1;

    static Context context;
    static TextView tv_unique;
    static EditText et_mainName, et_mainBody;
    static Button btn_all;

    static LinearLayout inc_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        // 메인 핸들러
        mHandler = new Handler(Looper.getMainLooper());

        inc_data = (LinearLayout) findViewById(R.id.inc_data);

        getLoaderManager().initLoader(0, null, this);

        tv_unique = (TextView) findViewById(R.id.tv_unique);

        btn_all = (Button) findViewById(R.id.btn_all);
        btn_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor = smsHelper.getData();
                smsAdapter = new SMSAdapter(context, cursor);
                lv_sms.setAdapter(smsAdapter);
                tv_unique.setVisibility(View.VISIBLE);
                btn_all.setVisibility(View.GONE);
            }
        });

        et_mainBody = (EditText) findViewById(R.id.et_mainBody);
        et_mainBody.setInputType(0);
        et_mainBody.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et_mainBody.setInputType(1);
            }
        });
        et_mainBody.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_mainBody.setText("");
                return true;
            }
        });
        et_mainBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tv_unique.append("(1)" + s);
                // smsAdapter.bodyFilter(s.toString().trim());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_unique.append("(2)" + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_unique.append("(3)" + s);
                smsAdapter.bodyFilter(s.toString().trim());
                Log.e("FAT=", tv_unique.getText().toString());
            }
        });

        lv_sms = (ListView) findViewById(R.id.lv_sms);
        smsHelper = new SMSHelper(this, dbSMS, null, dbVer_sms);
        db = smsHelper.getWritableDatabase();
        setPopupName(); // NullPointerException 예방
        viewAllMessage();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String taxpay;
                if (pay) taxpay = "Perpect";
                else if (paycount != 0) taxpay = "Good";
                else taxpay = "Bad";
                Snackbar.make(view, "My Grade : " + grade + "\npay positive: " + taxpay, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    // 모든 데이터 보기
    static void viewAllMessage() {
        cursor = smsHelper.getData();
        smsAdapter = new SMSAdapter(context, cursor);
        lv_sms.setAdapter(smsAdapter);
    }

    // 키보드 감추기
    static void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // if(imm.isAcceptingText()) imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); // 조건 신뢰도 미약
        if (editText != null && imm != null) //==//
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    // 키보드 보이기
    static void showKeyboard(EditText editText) {
        editText.requestFocus();
        // editText.setPrivateImeOptions("defaultInputmode=korean;"); // 레이아웃으로 넘김
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        if(! imm.isAcceptingText()) imm.showSoftInputFromInputMethod(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(MainActivity.this) {
            @Override
            public Cursor loadInBackground() {
                // return dbHelper.getData();
                return smsHelper.getData();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        smsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        smsAdapter.swapCursor(null);
    }

    // 전화번호 나누기, 하이픈(-) 표시
    static String phonNumFormat(String phonenumber) {
        // 1) 하이픈(-) 있으면 그대로 표시
        if (phonenumber.contains("-")) return phonenumber;
        // 2) 구형폰(빌드 21 미만)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return PhoneNumberUtils.formatNumber(phonenumber);
            // 3) 신형폰
        else
            return PhoneNumberUtils.formatNumber(phonenumber, Locale.getDefault().getCountry());
    }

    public void go_delete(int checked) {
        if (checked == 0) {
            // 데이터 전부 삭제
            db.delete(tbl_sms, null, null);
        }
        else {
            for (int id:smsAdapter.al_checked) {
                db.delete(tbl_sms, "_id = "+id, null);
            };
            smsAdapter.al_checked.clear();
        }
        cursor = smsHelper.getData();
        smsAdapter = new SMSAdapter(context, cursor);
        lv_sms.setAdapter(smsAdapter);
    }

    // BackPress(백키) 처리
    @Override
    public void onBackPressed() {
        final int checked = SMSAdapter.al_checked.size();

        if (inc_data.getVisibility()==View.VISIBLE)
            closeDataPage();
        else {
            if (mPopupWindow != null && mPopupWindow.isShowing())
                mPopupWindow.dismiss();
            else {
                if (checked > 0) {
                    // 체크 표시 있으면 전부 해제
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle(checked + "개 선택 표시\n지우고 끝낼까요?");
                    alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ContentValues values = new ContentValues();
                            values.put("chk", 0);
                            for (int id:SMSAdapter.al_checked) db.update(tbl_sms, values, "_id = "+id, null);
                            SMSAdapter.al_checked.clear();
                            finish();
                        }
                    });
                    alertDialog.show();
                }
                else finish();
            }
        }
    }

    private static View popupView;
    private static PopupWindow mPopupWindow;
    private static Button btn_append;
    private static ListView lv_name;
    private void setPopupName() {
        popupView = getLayoutInflater().inflate(R.layout.name_popup, null);
        lv_name = (ListView) popupView.findViewById(R.id.lv_name);

        mPopupWindow = new PopupWindow(popupView, 440, 900, true);
        // RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // mPopupWindow.setAnimationStyle(-1); // 애니메이션 설정(-1:설정, 0:설정안함)
        btn_append = (Button) findViewById(R.id.btn_append);

        et_mainName = (EditText) popupView.findViewById(R.id.et_mainName);
        et_mainName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_mainName.setText("");
                hideKeyboard(et_mainName);
                return true;
            }
        });
        et_mainName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                smsAdapter.nameFilter(s.toString().trim());
            }
        });

        btnClosePopup = (TextView) popupView.findViewById(R.id.btn_close_popup);
        btnClosePopup.setOnClickListener(cancel_button_click_listener);

        uniqueNames = new ArrayList<String>();
        Cursor nameCur = db.rawQuery("SELECT DISTINCT name FROM "+tbl_sms+";", null);
        while (nameCur.moveToNext()) {
            uniqueNames.add(nameCur.getString(nameCur.getColumnIndex("name")));
        }
        nameCur.close();
        Collections.sort(uniqueNames, myComparator);
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, uniqueNames);
        lv_name.setAdapter(nameAdapter);
    }
    static ArrayList<String> uniqueNames;

    private final static Comparator myComparator= new Comparator() {
        private final Collator collator = Collator.getInstance();
        @Override
        public int compare(Object object1, Object object2) {
            return collator.compare(object1.toString(), object2.toString());
        }
    };

    private TextView btnClosePopup;
    // NameList 팝업
    public void popNameList(View view) {
        if (mPopupWindow == null) setPopupName();
        // mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, -100);
        if (mPopupWindow != null && mPopupWindow.isShowing()) mPopupWindow.dismiss();
        else mPopupWindow.showAsDropDown(btn_append, 50, 0);
    }

    private OnClickListener cancel_button_click_listener = new OnClickListener() {
        public void onClick(View v) {
// Toast.makeText(context, "cancel_button_click_listener", Toast.LENGTH_SHORT).show();
            if (mPopupWindow != null && mPopupWindow.isShowing()) mPopupWindow.dismiss();
        }
    };

    // update 화면갱신(CursorAdapter-ListView)
    // CursorAdapter 클래스에서 데이터 수정 후 호출
    static void refreshDB(final int pos) {
        et_mainBody.setText("");

        if (whereText.isEmpty()) cursor = smsHelper.getData();
        else cursor = smsHelper.getData(whereText);

        smsAdapter = new SMSAdapter(context, cursor);
        lv_sms.setAdapter(smsAdapter);

        tv_unique.setText("보이는 아이템 "+cursor.getCount()+" 건");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_sms.setSelectionFromTop(pos, 0);
            }
        }, 100);
    }

    // 데이터 row 삭제
    public void del_row() {
        final int checked = SMSAdapter.al_checked.size();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        if (checked == 0) alertDialog.setTitle("모든 데이터를 삭제합니다\n되 돌릴 수 없습니다.\n전부 다 지우겠습니까?");
        else alertDialog.setTitle(checked + "개 데이터 지우겠습니까?\n되 돌릴 수 없습니다.");
        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                go_delete(checked);
            }
        });
        alertDialog.show();
    }

    private static EditText et_name, et_body;
    private static TextView btn_mod;

    private static void setDataPage() {
        et_name = (EditText) inc_data.findViewById(R.id.et_name);
        et_body = (EditText) inc_data.findViewById(R.id.et_body);

        TextView btn_cancel = (TextView) inc_data.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 편집화면 닫기
                closeDataPage();
            }
        });

        TextView btn_add = (TextView) inc_data.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "데이터 추가 코딩", Toast.LENGTH_SHORT).show();
            }
        });

        TextView btn_delText = (TextView) inc_data.findViewById(R.id.btn_delText);
        btn_delText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et_name.setText("");
                et_body.setText("");
            }
        });

        btn_mod = (TextView) inc_data.findViewById(R.id.btn_mod);
        btn_mod.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newName = et_name.getText().toString();
                final String newBody = et_body.getText().toString();
                // 실수로 클릭한 경우(1)
                if (newBody.isEmpty()) {
                    // if (newName.isEmpty() || newBody.isEmpty()) { // 이름/제목 없는 경우 통과
                    Toast.makeText(context, "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                final int nName = newName.compareTo(bindName);
                final int nBody = newBody.compareTo(bindBody);
                // 실수로 클릭한 경우(2)
                if (nName==0 && nBody==0) {
                    Toast.makeText(context, "변경된 내용이 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("수정한 내용대로 저장할까요?\n되돌릴 수 없습니다.");
                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = new ContentValues();
                        if (nName!=0) values.put("name", newName);
                        if (nBody!=0) values.put("body", newBody);
                        // values.put("date", System.currentTimeMillis());
                        db.update(tbl_sms, values, "_id = ?", new String[] { String.valueOf(bindID)});
                        // 화면 갱신
                        refreshDB(lv_sms.getFirstVisiblePosition());
                        // 편집화면 닫기
                        closeDataPage();
                    }
                });
                alertDialog.show();
            }
        });
    }
    public static void addRow() {
        inc_data.setVisibility(View.VISIBLE);
        if (et_name == null) setDataPage();
        et_name.setText(bindName);
        et_body.setText(bindBody);
        btn_mod.setVisibility(View.GONE);
    }
    public static void editRow() {
        inc_data.setVisibility(View.VISIBLE);
        if (et_name == null) setDataPage();
        et_name.setText(bindName);
        et_body.setText(bindBody);
        btn_mod.setVisibility(View.VISIBLE);
    }

    private static void closeDataPage() {
        inc_data.setVisibility(View.GONE);
        hideKeyboard(et_name);
        hideKeyboard(et_body);
    }

    public void mainClick(View view) {
        int id = view.getId();
        switch (id) {
            case (R.id.btn_del) : del_row(); break;
            case (R.id.btn_append) : addRow(); break;
        }
    }

}