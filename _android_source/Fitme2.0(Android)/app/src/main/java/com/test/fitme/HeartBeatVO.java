package com.test.fitme;

import android.support.annotation.NonNull;

public class HeartBeatVO implements Comparable{
    String wdate;
    int max;
    int min;
    String user_id;

    public HeartBeatVO() {}

    public HeartBeatVO(String wdate, int max, int min, String user_id) {
        this.wdate = wdate;
        this.max = max;
        this.min = min;
        this.user_id = user_id;
    }

    public String getDate() {
        return wdate;
    }

    public void setDate(String wdate) {
        this.wdate = wdate;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return this.getDate().compareTo(((HeartBeatVO)o).getDate());
    }
}
