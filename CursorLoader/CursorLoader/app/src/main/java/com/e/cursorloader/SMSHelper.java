package com.e.cursorloader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static com.e.cursorloader.MainActivity.context;
import static com.e.cursorloader.MainActivity.db;
import static com.e.cursorloader.MainActivity.tbl_sms;
import static com.e.cursorloader.MainActivity.whereText;

public class SMSHelper extends SQLiteOpenHelper {

    static ContentResolver contentResolver;

    static String[] chosung = new String[] { "ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ","ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ" };
    static String[] jungsung = new String[] { "ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ","ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ" };

    public SMSHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        contentResolver = context.getContentResolver();
    }

    // 글자 > 초성 추출
    private String getChosung(String s) { return getChosung(s.charAt(0)); }
    private String getChosung(Character c) {
        if(c < 0xAC00) return null; // unicode-value가 44032 보다 작으면 한글 아님
        char uniVal = (char) (c - 44032); // 0xAC00
        char cho = (char) (((uniVal - (uniVal % 28))/28)/21);
        return chosung[cho];
    }
    public String stringToChosungs(String text) {
        String chosungs = "";
        String s = "";
        ArrayList<String> al = new ArrayList<String>();
        if (! text.isEmpty()) {
            for (int i=0; i<text.length(); i++) {
                s = getChosung(text.substring(i,i+1));
                if (s != null) {
                    al.add(s);
                }
            }
        }
        if (! al.isEmpty()) chosungs = uniqueListString(al);
        return chosungs;
    }

    // ArrayList 중복 제거(유일본 추출) > String 리턴
    private String uniqueListString(ArrayList<String> srcList) {
        String resultString = "";
        HashSet hs = new HashSet(srcList);
        Iterator it = hs.iterator();
        while (it.hasNext()) resultString += it.next();
        return resultString;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + tbl_sms +
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, chosung TEXT, phone TEXT, date LONG, " +
                "body TEXT, chk INTEGER not null default 0);";
        db.execSQL(sql);

        new ReadMmsSms(context).collectMessage(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 참고 : onUpgrade 메소드 샘플 http://blog.daum.net/andro_java/1248
        // 이미 배포한 앱의 데이터베이스 구조 변경이 있는 업그레이드 시 위 페이지 참고할 것
        // 단순히 DROP TABLE > Create 하면 기존 데이터 다 날아감
    }

    Cursor getData() {
        whereText = "";
        return getWritableDatabase().rawQuery("SELECT * FROM "+tbl_sms+";", null);
    }

    public Cursor getData(String whereS) {
        whereText = whereS;
        return getWritableDatabase().rawQuery("SELECT * FROM "+tbl_sms+" WHERE "+whereS+";", null);
    }

    static Cursor getData(String whereS, String[] whereArray) {
        whereText = whereS;
        return db.rawQuery("SELECT * FROM "+tbl_sms+" WHERE "+whereS+";", whereArray);
    }

}