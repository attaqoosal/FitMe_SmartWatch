package com.test.fitme.retrofit;

public interface RetrofitCallback<T> {

    void onError(Throwable t);

    void onSuccess(int code, T receivedData);

    void onFailure(int code);
}
