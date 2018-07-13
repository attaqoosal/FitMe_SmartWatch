package com.test.fitme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.test.fitme.retrofit.RetrofitCallback;
import com.test.fitme.retrofit.SensorRetrofitClient;

public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private EditText idEdit, pwEdit;
    private Button loginBtn;
    private TextView createTv, forgetTv;
    private CheckBox autoCB, saveCB;
    private SensorRetrofitClient sensorRetrofitClient;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sensorRetrofitClient = SensorRetrofitClient.getInstance(this).createBaseApi();
        sp = getSharedPreferences("login", 0);
        editor = sp.edit();

        idEdit = findViewById(R.id.login_id);
        pwEdit = findViewById(R.id.login_pw);
        autoCB = findViewById(R.id.login_auto_checkbox);
        autoCB.setOnCheckedChangeListener(this);
        saveCB = findViewById(R.id.login_save_checkbox);
        saveCB.setOnCheckedChangeListener(this);
        loginBtn = findViewById(R.id.login_ok);
        createTv = findViewById(R.id.login_create);
        createTv.setPaintFlags(createTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        forgetTv = findViewById(R.id.login_forget);
        forgetTv.setPaintFlags(forgetTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if(sp.getBoolean("auto", false)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if(sp.getBoolean("save", false)) {
            idEdit.setText(sp.getString("id", ""));
            pwEdit.requestFocus();
            saveCB.setChecked(true);
        }
        loginBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("아아아", idEdit.getText().toString());
                Log.d("아아아", pwEdit.getText().toString());
                if(idEdit.getText().length() != 0 && pwEdit.getText().length() != 0) {
                    getLoginOk(idEdit.getText().toString(), pwEdit.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createTv.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getLoginOk(String user_id, String pw) {
        Log.d("아아아", "getLoginOk");
        sensorRetrofitClient.getLoginOk(user_id, pw, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d("getLoginOk", "onError" + " " + t);
                Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("확인(getLoginOk)", "" + receivedData);
                Log.d("확인", ((UserVO)receivedData).getName() + " " + ((UserVO)receivedData).getUser_id());

//                Gson gson = new Gson();
//                String json = mPrefs.getString("MyObject", "");
//                MyObject obj = gson.fromJson(json, MyObject.class);

                Gson gson = new Gson();
                String json = gson.toJson((UserVO) receivedData);
                editor.putString("user", json);
                editor.putString("id", ((UserVO) receivedData).getUser_id());
                editor.putString("pw", ((UserVO) receivedData).getPw());
                editor.putString("name", ((UserVO) receivedData).getName());
                editor.putInt("height", ((UserVO) receivedData).getHeight());
                editor.putInt("weight", ((UserVO) receivedData).getHeight());
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onFailure(int code) {
                Log.d("getLoginOk", "" + code);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.login_auto_checkbox:
                if(isChecked) {
                    editor.putBoolean("auto", true);
                }else {
                    editor.putBoolean("auto", false);
                }
                break;
            case R.id.login_save_checkbox:
                if(isChecked) {
                    editor.putBoolean("save", true);
                }else {
                    editor.putBoolean("save", false);
                }
                break;
        }
    }
}
