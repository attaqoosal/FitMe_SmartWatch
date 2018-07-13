package com.test.fitme;

import android.support.annotation.NonNull;

import java.util.Comparator;

public class SleepVO implements Comparable{
    String wdate;
    int value;
    String user_id;

    public SleepVO() {}

    public SleepVO(String wdate, int value, String user_id) {
        this.wdate = wdate;
        this.value = value;
        this.user_id = user_id;
    }

    public String getWdate() {
        return wdate;
    }

    public void setWdate(String wdate) {
        this.wdate = wdate;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return this.getWdate().compareTo(((SleepVO)o).getWdate());
    }
}
