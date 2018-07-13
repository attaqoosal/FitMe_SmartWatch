package com.test.fitme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.test.fitme.retrofit.RetrofitCallback;
import com.test.fitme.retrofit.SensorRetrofitClient;

public class SignUpDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{
    private Spinner emailSpinner;
    private SensorRetrofitClient sensorRetrofitClient;

    public static SignUpDialogFragment newInstance() {
        SignUpDialogFragment signUpDialogFragment = new SignUpDialogFragment();
        return signUpDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment_signup, container, false);
        sensorRetrofitClient = SensorRetrofitClient.getInstance(getActivity()).createBaseApi();

        if (getShowsDialog()) {
            getDialog().setTitle("회원가입");
        } else {
            getActivity().setTitle("회원가입");
        }
        emailSpinner = v.findViewById(R.id.email_spinner);
        emailSpinner.setOnItemSelectedListener(this);
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void postSignUp(UserVO user) {
        Log.d("아아아", "postUser");
        sensorRetrofitClient.postUser(user, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("postUser", "" + receivedData);
            }
            @Override
            public void onFailure(int code) { }
        });
    }
}
