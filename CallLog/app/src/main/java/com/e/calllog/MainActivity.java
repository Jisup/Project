package com.e.calllog;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> CallupList = new ArrayList<>();
    public static ArrayAdapter CallarrayAdapter;
    public static ListView CallList;
    long Tday, Thour, Tmint, Tsec;
    long sec, mint, hour, day;
    public static int receive, delivery, missed, callcount = 0;
    public static long durationcount = 0;
    public static final String MESSAGE_TYPE_INBOX = "1";
    public static final String MESSAGE_TYPE_SENT = "2";
    public static final String MESSAGE_TYPE_CONVERSATIONS = "3";
    public static final String MESSAGE_TYPE_NEW = "new";

    final static private String[] CALL_PROJECTION = { CallLog.Calls.TYPE,
            CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER,
            CallLog.Calls.DATE,        CallLog.Calls.DURATION };

    private static final String TAG = "Victor-Manage_Clique";

    private Cursor getCallHistoryCursor(Context context) {
        Cursor cursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI, CALL_PROJECTION,
                null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        return cursor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        CallList = (ListView) findViewById(R.id.CallList);
        CallarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CallupList);
        CallList.setAdapter(CallarrayAdapter);
        callLog();
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0: Tsec = durationcount % 60;break;
                case 1: Tmint = durationcount % 60;break;
                case 2: Thour = durationcount % 60;break;
                case 3: Tday = durationcount % 24;break;
            }
            durationcount /= 60;
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "수신 :" + receive +
                        " 발신 :" + delivery +
                        " 부재중 :" + missed  +
                        " Total : " + callcount +
                        "\nTotal duration : " + Tday +":"+ Thour +":"+ Tmint +":"+ Tsec, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    long nowdate2 = System.currentTimeMillis();
    private void callLog() {
        CallarrayAdapter.clear();
        String callname = "";
        String calltype = "";
        String calllog = "";
        Cursor curCallLog = getCallHistoryCursor(this);
        Log.i( TAG , "processSend() - 1");
        // Log.i( TAG , "curCallLog: " + curCallLog.getCount());
        if (curCallLog.moveToFirst() && curCallLog.getCount() > 0) {
            while (curCallLog.isAfterLast() == false) {
                long date = curCallLog.getLong(curCallLog.getColumnIndex(CallLog.Calls.DATE));
                if (date < 10000000000L) date = date * 1000;
                if (nowdate2 - date > 1209600000L)
                    // 86400000   1day
                    // 604800000  1week
                    // 1209600000L 2week
                    // 2592000000L 4week
                    break;
                StringBuffer sb = new StringBuffer();

                if (curCallLog.getString(curCallLog.getColumnIndex(CallLog.Calls.TYPE)).equals(MESSAGE_TYPE_INBOX)) {
                    calltype = "수신";
                    receive++;
                } else if (curCallLog.getString(curCallLog.getColumnIndex(CallLog.Calls.TYPE)).equals(MESSAGE_TYPE_SENT)) {
                    calltype = "발신";
                    delivery++;
                } else if (curCallLog.getString(curCallLog.getColumnIndex(CallLog.Calls.TYPE)).equals(MESSAGE_TYPE_CONVERSATIONS)) {
                    calltype = "부재중";
                    missed++;
                }
                if (curCallLog.getString(curCallLog.getColumnIndex(CallLog.Calls.CACHED_NAME)) == null) {
                    callname = "NoName";
                } else {
                    callname = curCallLog.getString(curCallLog.getColumnIndex(CallLog.Calls.CACHED_NAME));
                }
                sb.append("[Day] : ").append(timeToString(curCallLog.getLong(curCallLog
                        .getColumnIndex(CallLog.Calls.DATE))));
                sb.append("\t").append(calltype);
                sb.append("\n").append(callname);
                sb.append("\n").append(curCallLog.getString(curCallLog.getColumnIndex(CallLog.Calls.NUMBER)));
                long take = curCallLog.getInt(curCallLog.getColumnIndex(CallLog.Calls.DURATION));
                durationcount += take;
                callcount++;
                curCallLog.moveToNext();
                if (take == 0) {
                    sec = 0;mint = 0;hour = 0;day = 0;
                }
                else
                for (int i = 0; i < 4; i++) {
                    switch (i) {
                        case 0: sec = take % 60;break;
                        case 1: mint = take % 60;break;
                        case 2: hour = take % 60;break;
                        case 3: day = take % 24;break;
                    }
                    take /= 60;
                }
                sb.append("\n").append(day).append(":").append(hour).append(":").append(mint).append(":").append(sec);
                String backupData = sb.toString();
                CallarrayAdapter.add(backupData);
                Log.i("call history[", sb.toString());
            }
        }
    }

    private String timeToString(Long time) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleFormat.format(new Date(time));
        return date;
    }
}
