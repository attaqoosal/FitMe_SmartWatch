package com.test.fitme;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fitme_db";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String id;
    private final int RESULT_WALK_VALUE = 1;
    private final int RESULT_RUN_VALUE = 2;
    private final int RESULT_DUMBBELL_VALUE = 3;
    private final int RESULT_BARBELL_VALUE = 4;
    private final int RESULT_JUMPROPE_VALUE = 5;
    private final int RESULT_SITUP_VALUE = 6;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        sp = context.getSharedPreferences("login", 0);
        editor = sp.edit();
        id = sp.getString("id", "");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String walk_table = ("CREATE TABLE WALK ( NUM INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID TEXT, DATE TEXT, COUNT INTEGER, DIST INTEGER, KCAL INTEGER )");
        db.execSQL(walk_table);
        String run_table = ("CREATE TABLE RUN ( NUM INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID TEXT, DATE TEXT, COUNT INTEGER, DIST INTEGER, KCAL INTEGER )");
        db.execSQL(run_table);
        String heartbeat_table = ("CREATE TABLE HEARTBEAT ( NUM INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID TEXT, DATE TEXT, MAX INTEGER, MIN INTEGER)");
        db.execSQL(heartbeat_table);
        String jumprope_table = ("CREATE TABLE JUMPROPE ( NUM INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID TEXT, DATE TEXT, COUNT INTEGER, KCAL INTEGER )");
        db.execSQL(jumprope_table);
        String situp_table = ("CREATE TABLE SITUP ( NUM INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID TEXT, DATE TEXT, COUNT INTEGER, KCAL INTEGER )");
        db.execSQL(situp_table);
        String dumbbell_table = ("CREATE TABLE DUMBBELL ( NUM INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID TEXT, DATE TEXT, COUNT INTEGER )");
        db.execSQL(dumbbell_table);
        String barbell_table = ("CREATE TABLE BARBELL ( NUM INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID TEXT, DATE TEXT, COUNT INTEGER )");
        db.execSQL(barbell_table);
        String sleep_table = ("CREATE TABLE SLEEP ( NUM INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID TEXT, DATE TEXT, VALUE INTEGER )");
        db.execSQL(sleep_table);
        String goal_table = ("CREATE TABLE GOAL ( NUM INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID TEXT, DATE TEXT, WALK_COUNT INTEGER, RUN_COUNT INTEGER, " +
                "DUMBBELL_COUNT INTEGER, BARBELL_COUNT INTEGER, JUMPROPE_COUNT INTEGER, SITUP_COUNT INTEGER )");
        db.execSQL(goal_table);
//        String user_table = ("CREATE TABLE USER ( ID TEXT PRIMARY KEY, PW TEXT, NAME TEXT, BIRTH TEXT," +
//                "GENDER TEXT, HEIGHT INTEGER, WEIGHT INTEGER, TEL STRING )");
//        db.execSQL(user_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insert(UserVO user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID", user.getUser_id());
        values.put("PW", user.getPw());
        values.put("NAME", user.getName());
        values.put("BIRTH", user.getBirth());
        values.put("GENDER", user.getGender());
        values.put("HEIGHT", user.getHeight());
        values.put("WEIGHT",user.getWeight());
        values.put("TEL", user.getTel());

        db.insert("USER", null, values);
    }
    public void insert(WalkVO walk) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USER_ID", id);
        values.put("DATE", walk.getDate());
        values.put("COUNT", walk.getCount());
        values.put("DIST", walk.getDist());
        values.put("KCAL", walk.getKcal());

        db.insert("WALK", null, values);
    }
    public void insert(RunVO run) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USER_ID", id);
        values.put("DATE", run.getDate());
        values.put("COUNT", run.getCount());
        values.put("DIST", run.getDist());
        values.put("KCAL", run.getKcal());

        db.insert("RUN", null, values);
    }
    public void insert(HeartBeatVO heartBeat) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USER_ID", id);
        values.put("DATE", heartBeat.getDate());
        values.put("MAX", heartBeat.getMax());
        values.put("MIN", heartBeat.getMin());

        db.insert("HEARTBEAT", null, values);
    }
    public void insert(JumpRopeVO jumpRope) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USER_ID", id);
        values.put("DATE", jumpRope.getDate());
        values.put("COUNT", jumpRope.getCount());
        values.put("KCAL", jumpRope.getKcal());

        db.insert("JUMPROPE", null, values);
    }
    public void insert(SitUpVO situp) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USER_ID", id);
        values.put("DATE", situp.getDate());
        values.put("COUNT", situp.getCount());
        values.put("KCAL", situp.getKcal());

        db.insert("SITUP", null, values);
    }
    public void insert(DumbBellVO dumbBell) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USER_ID", id);
        values.put("DATE", dumbBell.getDate());
        values.put("COUNT", dumbBell.getCount());

        db.insert("DUMBBELL", null, values);
    }
    public void insert(BarbellVO barbell) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USER_ID", id);
        values.put("DATE", barbell.getDate());
        values.put("COUNT", barbell.getCount());

        db.insert("BARBELL", null, values);
    }
    public void insert(SleepVO sleep) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USER_ID", id);
        values.put("DATE", sleep.getWdate());
        values.put("VALUE", sleep.getValue());

        db.insert("SLEEP", null, values);
    }
    public void insert(GoalVO goal) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USER_ID", id);
        values.put("DATE", goal.getDate());
        values.put("WALK_COUNT", goal.getgWalk_count());
        values.put("RUN_COUNT", goal.getgRun_count());
        values.put("DUMBBELL_COUNT", goal.getgDumbbell_count());
        values.put("BARBELL_COUNT", goal.getgBarbell_count());
        values.put("JUMPROPE_COUNT", goal.getgJumprope_count());
        values.put("SITUP_COUNT", goal.getgSitup_count());

        db.insert("GOAL", null, values);
    }

    public ArrayList<SleepVO> selectAll_sleep(String date) {
        SQLiteDatabase db = getWritableDatabase();
        date = date.replaceAll("-", "");
        Cursor cursor = db.rawQuery("SELECT * FROM SLEEP WHERE USER_ID = ? AND SUBSTR(DATE, 1, 8) = ?", new String[] {id, date});
        ArrayList<SleepVO> sleeps = new ArrayList<>();
        boolean[] isVisited = new boolean[24];
        SleepVO sleep = null;
        if(cursor.moveToFirst()) {
            do {
                sleep = new SleepVO();
                sleep.setValue(cursor.getInt(3));
                sleep.setWdate(cursor.getString(2));
                sleeps.add(sleep);
                isVisited[Integer.parseInt(cursor.getString(2).substring(8))] = true;
            }while(cursor.moveToNext());
        }
        for(int i = 0; i < 24; i++) {
            if(!isVisited[i]) {
                sleep = new SleepVO();
                sleep.setValue(0);
                sleep.setWdate(date + i);
                sleeps.add(sleep);
            }
        }
        Collections.sort(sleeps);
        cursor.close();
        return sleeps;
    }

    public ArrayList<HeartBeatVO> selectAll_heartBeat(String date) {
        SQLiteDatabase db = getWritableDatabase();
        date = date.replaceAll("-", "");
        Log.d("확인", "date" + date + " " + id);
        Cursor cursor = db.rawQuery("SELECT * FROM HEARTBEAT WHERE USER_ID = ? AND SUBSTR(DATE, 1, 8) = ?", new String[] {id, date});
        ArrayList<HeartBeatVO> heartBeats = new ArrayList<>();
        boolean[] isVisited = new boolean[24];
        HeartBeatVO heartBeat = null;
        if(cursor.moveToFirst()) {
            do {
                heartBeat = new HeartBeatVO();
                heartBeat.setMax(cursor.getInt(3));
                heartBeat.setMin(cursor.getInt(4));
                heartBeat.setDate(cursor.getString(2));
                heartBeats.add(heartBeat);
                isVisited[Integer.parseInt(cursor.getString(2).substring(8))] = true;
            }while(cursor.moveToNext());
        }
        for(int i = 0; i < 24; i++) {
            if(!isVisited[i]) {
                heartBeat = new HeartBeatVO();
                heartBeat.setMax(110);
                heartBeat.setMin(70);
                heartBeat.setDate(date + i);
                heartBeats.add(heartBeat);
            }
        }
        Collections.sort(heartBeats);
        cursor.close();
        return heartBeats;
    }

    public GoalVO select_goal(String date) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM GOAL WHERE USER_ID = ? AND DATE = ?", new String[] {id, date});
        GoalVO goal = null;
        if(cursor.moveToFirst()) {
            do {
                goal = new GoalVO();
                goal.setgWalk_count(cursor.getInt(3));
                goal.setgRun_count(cursor.getInt(4));
                goal.setgBarbell_count(cursor.getInt(5));
                goal.setgDumbbell_count(cursor.getInt(6));
                goal.setgJumprope_count(cursor.getInt(7));
                goal.setgSitup_count(cursor.getInt(8));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return goal;
    }
    public WalkVO select_walk(String date) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM WALK WHERE USER_ID = ? AND DATE = ?", new String[] {id, date});
        WalkVO walk = null;
        if(cursor.moveToFirst()) {
            do {
                walk = new WalkVO();
                walk.setCount(cursor.getInt(3));
                walk.setDist(cursor.getInt(4));
                walk.setKcal(cursor.getInt(5));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return walk;
    }
    public RunVO select_run(String date) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RUN WHERE USER_ID = ? AND DATE = ?", new String[] {id, date});
        RunVO run = null;
        if(cursor.moveToFirst()) {
            do {
                run = new RunVO();
                run.setCount(cursor.getInt(3));
                run.setDist(cursor.getInt(4));
                run.setKcal(cursor.getInt(5));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return run;
    }
    public DumbBellVO select_dumbBell(String date) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DUMBBELL WHERE USER_ID = ? AND DATE = ?", new String[] {id, date});
        DumbBellVO dumbBell = null;
        if(cursor.moveToFirst()) {
            do {
                dumbBell = new DumbBellVO();
                dumbBell.setCount(cursor.getInt(3));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return dumbBell;
    }
    public BarbellVO select_barbell(String date) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BARBELL WHERE USER_ID = ? AND DATE = ?", new String[] {id, date});
        BarbellVO barbell = null;
        if(cursor.moveToFirst()) {
            do {
                barbell = new BarbellVO();
                barbell.setCount(cursor.getInt(3));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return barbell;
    }
    public JumpRopeVO select_jumpRope(String date) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM JUMPROPE WHERE USER_ID = ? AND DATE = ?", new String[] {id, date});
        JumpRopeVO jumpRope = null;
        if(cursor.moveToFirst()) {
            do {
                jumpRope = new JumpRopeVO();
                jumpRope.setCount(cursor.getInt(3));
                jumpRope.setKcal(cursor.getInt(4));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return jumpRope;
    }
    public SitUpVO select_situp(String date) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SITUP WHERE USER_ID = ? AND DATE = ?", new String[] {id, date});
        SitUpVO situp = null;
        if(cursor.moveToFirst()) {
            do {
                situp = new SitUpVO();
                situp.setCount(cursor.getInt(3));
                situp.setKcal(cursor.getInt(4));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return situp;
    }
    public void update(int cnt, int requestCode, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        switch (requestCode) {
            case RESULT_WALK_VALUE:
                values.put("WALK_COUNT", cnt);
                break;
            case RESULT_RUN_VALUE:
                values.put("RUN_COUNT", cnt);
                break;
            case RESULT_DUMBBELL_VALUE:
                values.put("DUMBBELL_COUNT", cnt);
                break;
            case RESULT_BARBELL_VALUE:
                values.put("BARBELL_COUNT", cnt);
                break;
            case RESULT_JUMPROPE_VALUE:
                values.put("JUMPROPE_COUNT", cnt);
                break;
            case RESULT_SITUP_VALUE:
                values.put("SITUP_COUNT", cnt);
                break;
        }
        db.update("GOAL", values, "USER_ID=? AND DATE=?", new String[] {id, date});
    }

    public void update(WalkVO walk) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE WALK SET COUNT=COUNT+" + walk.getCount() + ", DIST=DIST+" + walk.getDist() + ", KCAL=KCAL+"
                + walk.getKcal() + " WHERE USER_ID=? AND DATE=?", new String[] {id, walk.getDate()});
    }
    public void update(RunVO run) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE RUN SET COUNT=COUNT+" + run.getCount() + ", DIST=DIST+" + run.getDist() + ", KCAL=KCAL+"
                + run.getKcal() + " WHERE USER_ID=? AND DATE=?", new String[] {id, run.getDate()});
    }
    public void update(DumbBellVO dumbBell) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE DUMBBELL SET COUNT=COUNT+" + dumbBell.getCount() +
                " WHERE USER_ID=? AND DATE=?", new String[] {id, dumbBell.getDate()});
    }
    public void update(BarbellVO barbell) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE BARBELL SET COUNT=COUNT+" + barbell.getCount() +
                " WHERE USER_ID=? AND DATE=?", new String[] {id, barbell.getDate()});
    }
    public void update(SitUpVO sitUp) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE SITUP SET COUNT=COUNT+" + sitUp.getCount() + ", KCAL=KCAL+" + sitUp.getKcal() +
                " WHERE USER_ID=? AND DATE=?", new String[] {id, sitUp.getDate()});
    }
    public void update(JumpRopeVO jumpRope) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE JUMPROPE SET COUNT=COUNT+" + jumpRope.getCount() + ", KCAL=KCAL+" + jumpRope.getKcal() +
                " WHERE USER_ID=? AND DATE=?", new String[] {id, jumpRope.getDate()});
    }

    public boolean isGoal(String date) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM GOAL WHERE USER_ID = ? AND DATE = ?", new String[] {id, date});
        boolean isGoal = false;
        if(cursor.getCount() > 0) {
            isGoal = true;
        }
        cursor.close();
        return isGoal;
    }
}
