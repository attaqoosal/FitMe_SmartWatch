package com.test.fitme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.fitme.retrofit.RetrofitCallback;
import com.test.fitme.retrofit.SensorRetrofitClient;

public class RegisterDialogFragment extends DialogFragment implements View.OnClickListener{
    private SensorRetrofitClient sensorRetrofitClient;
    private EditText inputEdit;
    private TextView cancelTv, okTv;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public static RegisterDialogFragment newInstance() {
        RegisterDialogFragment registerDialogFragment = new RegisterDialogFragment();
        return registerDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment_register, container, false);
        sensorRetrofitClient = SensorRetrofitClient.getInstance(getActivity()).createBaseApi();
        sp = getActivity().getSharedPreferences("login", 0);
        editor = sp.edit();

        inputEdit = v.findViewById(R.id.register_edit);
        cancelTv = v.findViewById(R.id.register_cancel);
        cancelTv.setOnClickListener(this);
        okTv = v.findViewById(R.id.register_ok);
        okTv.setOnClickListener(this);

        if (getShowsDialog()) {
            getDialog().setTitle("Register Smartwatch");
        } else {
            getActivity().setTitle("Register Smartwatch");
        }
        return v;
    }

    public void getRegisterPro(String user_id, int serial_num) {
        Log.d("아아아", "getRegisterPro" + user_id + " " + serial_num);
        sensorRetrofitClient.getRegisterPro(user_id, serial_num, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("확인(getRegisterPro)", "" + receivedData);
                if((int)receivedData > 0) {
                    editor.putString("serial_num", inputEdit.getText().toString());
                    editor.commit();
                    getDialog().cancel();
                    Toast.makeText(getActivity(), "기기 등록이 완료되었습니다..", Toast.LENGTH_SHORT).show();
                }else {
                    inputEdit.setText("");
                    Toast.makeText(getActivity(), "등록된 기기가 아닙니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int code) { }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_ok:
                if(inputEdit.getText().length() == 6) {
                    getRegisterPro(sp.getString("id", ""), Integer.parseInt(inputEdit.getText().toString()));
                }else {
                    inputEdit.setText("");
                    Toast.makeText(getActivity(), "시리얼넘버 6자리를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register_cancel:
                getDialog().cancel();
                break;
        }
    }
}
