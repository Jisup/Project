package com.ramotion.foldingcell.examples.simple;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.text.Collator;
import java.util.Comparator;
import static com.ramotion.foldingcell.examples.simple.ReadMmsSms.pay;
import static com.ramotion.foldingcell.examples.simple.ReadMmsSms.paycount;
import static com.ramotion.foldingcell.examples.simple.ReadMmsSms.payofcharge;
import static com.ramotion.foldingcell.examples.simple.ReadMmsSms.grade;


public class MainActivity extends AppCompatActivity {
    public static String whereText = "";
    static Handler mHandler;
    static SQLiteDatabase db;

    static SMSHelper smsHelper;
    final static String dbSMS = "sms.db";
    final static String tbl_sms = "smstable";
    final static int dbVer_sms = 1;

    static Context context;

    long Tday, Thour, Tmint, Tsec;
    long receive, delivery, missed, draft, callcount = 0;
    long receive_time, delivery_time;
    long durationcount = 0;
    public static final String MESSAGE_TYPE_INBOX = "1";
    public static final String MESSAGE_TYPE_SENT = "2";
    public static final String MESSAGE_TYPE_CONVERSATIONS = "3";
    public static final String MESSAGE_TYPE_DRAFT = "5";
    final static private String[] CALL_PROJECTION = { CallLog.Calls.TYPE,
            CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER,
            CallLog.Calls.DATE,        CallLog.Calls.DURATION };

    private static final String TAG = "Victor-Manage_Clique";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        mHandler = new Handler(Looper.getMainLooper());
        smsHelper = new SMSHelper(this, dbSMS, null, dbVer_sms);
        db = smsHelper.getWritableDatabase();

        callLog();
        TextView call_total = (TextView)findViewById(R.id.call_total);
        call_total.setText(""+callcount);
        TextView incoming = (TextView)findViewById(R.id.incoming);
        incoming.setText(""+receive);
        TextView outgoing = (TextView)findViewById(R.id.outgoing);
        outgoing.setText(""+delivery);
        TextView out = (TextView)findViewById(R.id.out);
        out.setText(""+missed);
        TextView refusal = (TextView)findViewById(R.id.refusal);
        refusal.setText(""+draft);
        TextView incoming_time = (TextView)findViewById(R.id.incoming_time);
        incoming_time.setText(timeToString(receive_time));
        TextView outgoing_time = (TextView)findViewById(R.id.outgoing_time);
        outgoing_time.setText(timeToString(delivery_time));
        ImageView cell_image = (ImageView)findViewById(R.id.cell_image);
        cell_image.setBackground(new ShapeDrawable(new OvalShape()));
        cell_image.setClipToOutline(true);
        ImageView in_image = (ImageView)findViewById(R.id.in_image);
        in_image.setBackground(new ShapeDrawable(new OvalShape()));
        in_image.setClipToOutline(true);
        ImageView out_image = (ImageView)findViewById(R.id.out_image);
        out_image.setBackground(new ShapeDrawable(new OvalShape()));
        out_image.setClipToOutline(true);
        ImageView not_image = (ImageView)findViewById(R.id.not_image);
        not_image.setBackground(new ShapeDrawable(new OvalShape()));
        not_image.setClipToOutline(true);
        ImageView refusal_image = (ImageView)findViewById(R.id.refusal_image);
        refusal_image.setBackground(new ShapeDrawable(new OvalShape()));
        refusal_image.setClipToOutline(true);
        // get our folding cell
        final FoldingCell fc0 = (FoldingCell) findViewById(R.id.folding_cell0);
        final FoldingCell fc1 = (FoldingCell) findViewById(R.id.folding_cell1);
        final FoldingCell fc2 = (FoldingCell) findViewById(R.id.folding_cell2);
        final FoldingCell fc3 = (FoldingCell) findViewById(R.id.folding_cell3);
        final FoldingCell fc4 = (FoldingCell) findViewById(R.id.folding_cell4);

        // attach click listener to fold btn

        fc0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc0.toggle(false);
            }
        });
        fc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc1.toggle(false);
            }
        });
        fc2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String taxpay;
                if (pay) taxpay = "Perpect";
                else if (paycount == 2) taxpay = "Great";
                else if (paycount == 1) taxpay = "Good";
                else taxpay = "Bad";
                TextView pay_count = (TextView)findViewById(R.id.pay_count);
                pay_count.setText(String.valueOf(3-paycount));
                TextView positive_pay = (TextView)findViewById(R.id.positive_pay);
                positive_pay.setText(taxpay);
                int my_charge = Integer.parseInt(payofcharge);
                String my_e_charge = String.format("%,d",my_charge);
                my_charge *= paycount;
                TextView positive_auto = (TextView)findViewById(R.id.positive_auto);
                positive_auto.setText(my_e_charge+"원");
                String my_t_charge = String.format("%,d",+my_charge);
                TextView positive_charge = (TextView)findViewById(R.id.positive_charge);
                positive_charge.setText(my_t_charge+"원");
                ImageView my_image_grade = (ImageView)findViewById(R.id.my_image_grade);
                int image_id = getResources().getIdentifier(grade, "drawable", getPackageName());
                my_image_grade.setImageResource(image_id);
                fc2.toggle(false);
            }
        });
        fc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc3.toggle(false);
            }
        });
        fc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc4.toggle(false);
            }
        });

        int score = 825;
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
        progressBar.setProgress(score);
    }
    private final static Comparator myComparator= new Comparator() {
        private final Collator collator = Collator.getInstance();
        @Override
        public int compare(Object object1, Object object2) {
            return collator.compare(object1.toString(), object2.toString());
        }
    };
    private Cursor getCallHistoryCursor(Context context) {
        Cursor cursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI, CALL_PROJECTION,
                null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        return cursor;
    }
    long nowdate2 = System.currentTimeMillis();
    private void callLog() {
        Cursor curCallLog = getCallHistoryCursor(this);
        if (curCallLog.moveToFirst() && curCallLog.getCount() > 0) {
            while (curCallLog.isAfterLast() == false) {
                long date = curCallLog.getLong(curCallLog.getColumnIndex(CallLog.Calls.DATE));
                if (date < 10000000000L) date = date * 1000;
                if (nowdate2 - date > 12776000000L)
                    // 86400000   1day   // 604800000  1week
                    // 1209600000L 2week // 2592000000L 4week
                    // 12776000000L 3month
                    break;
                StringBuffer sb = new StringBuffer();
                long take = curCallLog.getInt(curCallLog.getColumnIndex(CallLog.Calls.DURATION));
                if (curCallLog.getString(curCallLog.getColumnIndex(CallLog.Calls.TYPE)).equals(MESSAGE_TYPE_INBOX)) {
                    receive_time += take;
                    receive++;
                } else if (curCallLog.getString(curCallLog.getColumnIndex(CallLog.Calls.TYPE)).equals(MESSAGE_TYPE_SENT)) {
                    delivery_time += take;
                    delivery++;
                } else if (curCallLog.getString(curCallLog.getColumnIndex(CallLog.Calls.TYPE)).equals(MESSAGE_TYPE_CONVERSATIONS)) {
                    missed++;
                } else if (curCallLog.getString(curCallLog.getColumnIndex(CallLog.Calls.TYPE)).equals(MESSAGE_TYPE_DRAFT)) {
                    draft++;
                }
                durationcount += take;
                callcount++;
                curCallLog.moveToNext();
            }
        }
    }

    long sec, mint, hour, day;
    private String timeToString(Long time) {
        sec=0;mint=0;hour=0;day=0;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0: sec = time % 60;break;
                case 1: mint = time % 60;break;
                case 2: hour = time % 60;break;
                case 3: day = time % 24;break;
            }
            time /= 60;
        }
        String date="00:00:00";
        if (day != 0)
            date = (day+"day+"+String.format("%02d",hour)+":"+String.format("%02d",mint)+":"+String.format("%02d",sec));
        else
            date = (String.format("%02d",hour)+":"+String.format("%02d",mint)+":"+String.format("%02d",sec));
        return date;
    }
}
