package com.e.cursorloader;

import static com.e.cursorloader.MainActivity.smsHelper;

public class Obj_Message {
    private int chk;
    private String name;
    private String chosung;
    private String phone;
    private long date;
    private String body;

    public Obj_Message() { }
    public Obj_Message(String name, String phone, long date, String body) {
        this.chk = 0;
        this.name = name;
        this.chosung = smsHelper.stringToChosungs(name);
        this.phone = phone;
        this.date = date;
        this.body = body;
    }

    public String getName() { return name; }
    public String getChosung() { return chosung; }
    public long getDate() { return date; }
    public String getPhone() { return phone; }
    public String getBody() { return body; }

}