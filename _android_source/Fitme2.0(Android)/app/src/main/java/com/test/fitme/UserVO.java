package com.test.fitme;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

public class UserVO implements Serializable{
    private String user_id;
    private String pw;
    private String name;
    private String birth;
    private String gender;
    private int height;
    private int weight;
    private String tel;
    private int u_check;

//    @Expose
//    ArrayList<RunVO> runInfo;
//    @Expose
//    ArrayList<WalkVO> walkInfo;
//    @Expose
//    ArrayList<SitUpVO> sitUpInfo;
//    @Expose
//    ArrayList<JumpRopeVO> jumpLopeInfo;
//    @Expose
//    ArrayList<TemperatureVO> temperatureInfo;
//    @Expose
//    ArrayList<HeartBeatVO> heartBeatInfo;
//    @Expose
//    ArrayList<BarbellVO> barbelInfo;
//    @Expose
//    ArrayList<DumbBellVO> dumbBeInfo;
//    @Expose
//    ArrayList<SleepVO> sleepInfo;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getU_check() {
        return u_check;
    }

    public void setU_check(int u_check) {
        this.u_check = u_check;
    }
    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
