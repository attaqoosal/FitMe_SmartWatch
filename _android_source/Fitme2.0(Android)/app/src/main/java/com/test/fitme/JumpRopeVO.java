package com.test.fitme;

public class JumpRopeVO {
    String wdate;
    int count;
    int kcal;
    String user_id;

    public JumpRopeVO() {}

    public JumpRopeVO(String wdate, int count, int kcal, String user_id) {
        this.wdate = wdate;
        this.count = count;
        this.kcal = kcal;
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

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }
}
