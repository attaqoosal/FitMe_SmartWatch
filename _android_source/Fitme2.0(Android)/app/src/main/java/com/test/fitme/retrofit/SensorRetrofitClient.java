package com.test.fitme.retrofit;

import android.content.Context;
import android.util.Log;

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
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SensorRetrofitClient {
    private SensorApiService sensorApiService;
    public static String baseUrl = SensorApiService.Base_URL;
    private static Context mContext;
    private static Retrofit retrofit;

    private static class SingletonHolder {
        private static SensorRetrofitClient INSTANCE = new SensorRetrofitClient(mContext);
    }

    public static SensorRetrofitClient getInstance(Context context) {
        if(context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    private SensorRetrofitClient(Context context) {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }
    public SensorRetrofitClient createBaseApi() {
        sensorApiService = create(SensorApiService.class);
        return this;
    }

    public  <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    public void getIdCheck(String user_id, final RetrofitCallback callback) {
        Log.d("아아아2", "getIdCheck" + " " + user_id);
        sensorApiService.getIdCheck(user_id).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("getIdCheck", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("getIdCheck", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("getIdCheck", "Fail" + t);
                callback.onError(t);
            }
        });
    }

    public void getRegisterPro(String user_id, int serial_num, final RetrofitCallback callback) {
        Log.d("getRegisterPro", "getRegisterPro" + " " + user_id + " " + serial_num);
        sensorApiService.getRegisterPro(user_id, serial_num).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("getRegisterPro", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("getRegisterPro", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("getRegisterPro", "Fail" + t);
                callback.onError(t);
            }
        });
    }

    public void getLoginOk(String user_id, String pw, final RetrofitCallback callback) {
        Log.d("아아아2", "getLoginOk" + " " + user_id + " " + pw);
        sensorApiService.getLoginOk(user_id, pw).enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                if (response.isSuccessful()) {
                    Log.d("getLoginOk", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("getLoginOk", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {
                Log.d("getLoginOk", "Fail" + " " + t);
                callback.onError(t);
            }
        });
    }

    public void postUser(UserVO user, final RetrofitCallback callback) {
        sensorApiService.postUser(user).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("postUser", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("postUser", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("postUser", "Fail" + t);
                callback.onError(t);
            }
        });
    }
    public void postWalk(WalkVO walk, final RetrofitCallback callback) {
        sensorApiService.postWalk(walk).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("postWalk", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("postWalk", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("postWalk", "Fail" + t);
                callback.onError(t);
            }
        });
    }
    public void postRun(RunVO run, final RetrofitCallback callback) {
        sensorApiService.postRun(run).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("postRun", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("postRun", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("postRun", "Fail" + t);
                callback.onError(t);
            }
        });
    }
    public void postDumbbell(DumbBellVO dumbBell, final RetrofitCallback callback) {
        sensorApiService.postDumbbell(dumbBell).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("postDumbbell", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("postDumbbell", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("postDumbbell", "Fail" + t);
                callback.onError(t);
            }
        });
    }
    public void postBarbell(BarbellVO barbell, final RetrofitCallback callback) {
        sensorApiService.postBarbell(barbell).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("postBarbell", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("postBarbell", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("postBarbell", "Fail" + t);
                callback.onError(t);
            }
        });
    }
    public void postJumpeRope(JumpRopeVO jumpRope, final RetrofitCallback callback) {
        sensorApiService.postJumpRope(jumpRope).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("postJumpeRope", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("postJumpeRope", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("postJumpeRope", "Fail" + t);
                callback.onError(t);
            }
        });
    }
    public void postSitup(SitUpVO situp, final RetrofitCallback callback) {
        sensorApiService.postSitup(situp).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("postSitup", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("postSitup", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("postSitup", "Fail" + t);
                callback.onError(t);
            }
        });
    }
    public void postHeartBeat(HeartBeatVO heartBeat, final RetrofitCallback callback) {
        sensorApiService.postHeartBeat(heartBeat).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("postHeartBeat", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("postHeartBeat", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("postHeartBeat", "Fail" + t);
                callback.onError(t);
            }
        });
    }
    public void postSleep(SleepVO sleep, final RetrofitCallback callback) {
        sensorApiService.postSleep(sleep).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("postSleep", "" + response.body().toString());
                    callback.onSuccess(response.code(), response.body());
                } else {
                    Log.d("postSleep", "Fail" + response.code());
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("postSleep", "Fail" + t);
                callback.onError(t);
            }
        });
    }

//    public void getDetail(int id, final RetrofitCallback callback) {
//        sensorApiService.getDetail(id).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    Log.d("getDetail", "" + response.body().toString());
//                    callback.onSuccess(response.code(), response.body());
//                } else {
//                    Log.d("getDetail", "Fail" + response.code());
//                    callback.onFailure(response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("getDetail", "Fail" + t);
//                callback.onError(t);
//            }
//        });
//    }
//
//    public void getLocInfo(double mapX, double mapY, final RetrofitCallback callback) {
//        sensorApiService.getLocInfo(mapX, mapY).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    Log.d("getInfo", "" + response.body().toString());
//                    callback.onSuccess(response.code(), response.body());
//                } else {
//                    Log.d("getInfo", "Fail" + response.code());
//                    callback.onFailure(response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("getInfo", "Fail" + t);
//                callback.onError(t);
//            }
//        });
//    }
//
//    public void getInfo(String startDate, final RetrofitCallback callback) {
//        sensorApiService.getInfo(startDate).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    Log.d("getInfo", "" + response.body().toString());
//                    callback.onSuccess(response.code(), response.body());
//                } else {
//                    Log.d("getInfo", "Fail" + response.code());
//                    callback.onFailure(response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("getInfo", "Fail" + t);
//                callback.onError(t);
//            }
//        });
//    }
//
//    public void getIngInfo(final RetrofitCallback callback) {
//        sensorApiService.getIngInfo().enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    Log.d("getInfo", "" + response.body().toString());
//                    callback.onSuccess(response.code(), response.body());
//                } else {
//                    Log.d("getInfo", "Fail" + response.code());
//                    callback.onFailure(response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("getInfo", "Fail" + t);
//                callback.onError(t);
//            }
//        });
//    }
}
