package com.test.fitme;

public class GoalVO {
    public String date;
    public int gWalk_count;
    public int gRun_count;
    public int gDumbbell_count;
    public int gBarbell_count;
    public int gJumprope_count;
    public int gSitup_count;

    public GoalVO() {}
    public GoalVO(String date, int gWalk_count, int gRun_count, int gDumbbell_count, int gBarbell_count, int gJumprope_count, int gSitup_count) {
        this.date = date;
        this.gWalk_count = gWalk_count;
        this.gRun_count = gRun_count;
        this.gDumbbell_count = gDumbbell_count;
        this.gBarbell_count = gBarbell_count;
        this.gJumprope_count = gJumprope_count;
        this.gSitup_count = gSitup_count;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getgWalk_count() {
        return gWalk_count;
    }

    public void setgWalk_count(int gWalk_count) {
        this.gWalk_count = gWalk_count;
    }

    public int getgRun_count() {
        return gRun_count;
    }

    public void setgRun_count(int gRun_count) {
        this.gRun_count = gRun_count;
    }

    public int getgDumbbell_count() {
        return gDumbbell_count;
    }

    public void setgDumbbell_count(int gDumbbell_count) {
        this.gDumbbell_count = gDumbbell_count;
    }

    public int getgBarbell_count() {
        return gBarbell_count;
    }

    public void setgBarbell_count(int gBarbell_count) {
        this.gBarbell_count = gBarbell_count;
    }

    public int getgJumprope_count() {
        return gJumprope_count;
    }

    public void setgJumprope_count(int gJumprope_count) {
        this.gJumprope_count = gJumprope_count;
    }

    public int getgSitup_count() {
        return gSitup_count;
    }

    public void setgSitup_count(int gSitup_count) {
        this.gSitup_count = gSitup_count;
    }
}
