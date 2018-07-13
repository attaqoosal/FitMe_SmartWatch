package com.test.fitme;

public class DumbBellVO {
    String wdate;
    int count;
    String user_id;

    public DumbBellVO() {}

    public DumbBellVO(String wdate, int count, String user_id) {
        this.wdate = wdate;
        this.count = count;
        this.user_id = user_id;
    }

    public String getDate() {
        return wdate;
    }

    public void setDate(String wdate) {
        this.wdate = wdate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
