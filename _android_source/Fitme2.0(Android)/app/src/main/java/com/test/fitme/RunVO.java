package com.test.fitme;

public class RunVO {
    String wdate;
    int count;
    int dist;
    int kcal;
    String user_id;

    public  RunVO() {}

    public RunVO(String wdate, int count, int dist, int kcal, String user_id) {
        this.wdate = wdate;
        this.count = count;
        this.dist = dist;
        this.kcal = kcal;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }
}
