package com.test.fitme.retrofit;

import com.test.fitme.BarbellVO;
import com.test.fitme.DumbBellVO;
import com.test.fitme.HeartBeatVO;
import com.test.fitme.JumpRopeVO;
import com.test.fitme.RunVO;
import com.test.fitme.SitUpVO;
import com.test.fitme.SleepVO;
import com.test.fitme.UserVO;
import com.test.fitme.WalkVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SensorApiService {

    String Base_URL = "http://192.168.0.102:8090/fitMe/";

    @GET("user_login.do")
    Call<UserVO> getLoginOk(@Query("user_id") String user_id, @Query("pw") String pw);
    @GET("rg_insertOK.do")
    Call<Integer> getRegisterPro(@Query("user_id") String user_id, @Query("serial_num") int serial_num);
    //User
    @POST("user_joinOK.do")
    Call<Integer> postUser(@Body UserVO user);
    //id check
    @GET("user_idCheck.do")
    Call<Integer> getIdCheck(@Query("user_id") String user_id);

    @POST("w_selectOneOK.do")
    Call<Integer> postWalk(@Body WalkVO walk);
    @POST("r_selectOneOK.do")
    Call<Integer> postRun(@Body RunVO run);
    @POST("d_selectOneOK.do")
    Call<Integer> postDumbbell(@Body DumbBellVO dumbbell);
    @POST("b_selectOneOK.do")
    Call<Integer> postBarbell(@Body BarbellVO barbell);
    @POST("st_selectOneOK.do")
    Call<Integer> postSitup(@Body SitUpVO situp);
    @POST("j_selectOneOK.do")
    Call<Integer> postJumpRope(@Body JumpRopeVO jumpRope);
    @POST("sl_insertOK.do")
    Call<Integer> postSleep(@Body SleepVO sleep);
    @POST("h_insertOK.do")
    Call<Integer> postHeartBeat(@Body HeartBeatVO heartBeat);


//    //다가오는 축제로 변경
//    @GET("searchFestival?listYN=Y&MobileOS=AND&MobileApp=Festival&arrange=P&numOfRows=100&pageNo=1&ServiceKey=" + SERVER_KEY)
//    Call<String> getInfo(@Query("eventStartDate") String startDate);
//
//    @GET("detailCommon?contentTypeId=15&MobileOS=AND&MobileApp=Festival&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=N&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y&ServiceKey=" + SERVER_KEY)
//    Call<String> getDetail(@Query("contentId") int id);
//
//    //진행중인 축제(조회순으로 가져온 후 빠른시간별 정렬)(HOME)
//    @GET("searchFestival?listYN=Y&MobileOS=AND&MobileApp=Festival&arrange=P&numOfRows=300&pageNo=1&ServiceKey=" + SERVER_KEY)
//    Call<String> getIngInfo();
//
//    //현재위치 반경 2km
//    @GET("locationBasedList?contentTypeId=15&radius=10000&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=P&numOfRows=30&pageNo=1&ServiceKey=" + SERVER_KEY)
//    Call<String> getLocInfo(@Query("mapX") double mapX, @Query("mapY") double mapY);
}

