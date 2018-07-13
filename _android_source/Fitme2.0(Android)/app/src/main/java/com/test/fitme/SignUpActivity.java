package com.test.fitme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.test.fitme.retrofit.RetrofitCallback;
import com.test.fitme.retrofit.SensorRetrofitClient;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private SensorRetrofitClient sensorRetrofitClient;
    private EditText idEdit, pwEdit, nameEdit, heightEdit, weightEdit, telEdit;
    private Button okBtn, cancelBtn, checkBtn;
    private DatePicker birthDP;
    private RadioGroup genderRG;
    private RadioButton genderRB;
    private Spinner emailSpinner;
    private boolean doCheckedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sensorRetrofitClient = SensorRetrofitClient.getInstance(this).createBaseApi();
        idEdit = findViewById(R.id.signup_id);
        pwEdit = findViewById(R.id.signup_pw);
        nameEdit = findViewById(R.id.signup_name);
        telEdit = findViewById(R.id.signup_tel);
        heightEdit = findViewById(R.id.signup_height);
        weightEdit = findViewById(R.id.signup_weight);
        okBtn = findViewById(R.id.signup_ok);
        okBtn.setOnClickListener(this);
        cancelBtn = findViewById(R.id.signup_cancel);
        cancelBtn.setOnClickListener(this);
        genderRG = findViewById(R.id.signup_rg);
        birthDP = findViewById(R.id.signup_birth);
        checkBtn = findViewById(R.id.signup_check);
        checkBtn.setOnClickListener(this);
        emailSpinner = findViewById(R.id.signup_email_spinner);

        idEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doCheckedId = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void postSignUp(UserVO user) {
        Log.d("아아아", "postUser");
        sensorRetrofitClient.postUser(user, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("postUser", "" + receivedData);
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(int code) { }
        });
    }

    public void getIdCheck(String user_id) {
        Log.d("아아아", "getIdCheck");
        sensorRetrofitClient.getIdCheck(user_id, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("getIdCheck", "" + receivedData);
                if((int)receivedData == 0) {
                    doCheckedId = true;
                    Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                }else {
                    doCheckedId = false;
                    Toast.makeText(getApplicationContext(), "사용중인 아이디입니다.", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(int code) { }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_ok:
                if(doCheckedId) {
                    if (idEdit.getText().length() > 0 && pwEdit.getText().length() > 0 && nameEdit.getText().length() > 0 &&
                            telEdit.getText().length() > 0 && heightEdit.getText().length() > 0 &&
                            pwEdit.getText().length() > 0) {
                        UserVO user = new UserVO();
                        user.setUser_id(idEdit.getText().toString() + emailSpinner.getSelectedItem().toString());
                        user.setPw(pwEdit.getText().toString());
                        user.setName(nameEdit.getText().toString());
                        user.setTel(telEdit.getText().toString());
                        user.setWeight(Integer.parseInt(weightEdit.getText().toString()));
                        user.setHeight(Integer.parseInt(heightEdit.getText().toString()));
                        String month = "";
                        String day = "";
                        if (birthDP.getMonth() < 10) {
                            month = "0" + (birthDP.getMonth() + 1);
                        }
                        if (birthDP.getDayOfMonth() < 10) {
                            day = "0" + birthDP.getDayOfMonth();
                        }
                        user.setBirth("" + birthDP.getYear() + month + day);
                        genderRB = findViewById(genderRG.getCheckedRadioButtonId());
                        user.setGender(genderRB.getText().toString());
                        user.setU_check(0);
                        postSignUp(user);
                    } else {
                        Toast.makeText(getApplicationContext(), "정보를 정확히 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "아이디 중복체크를 하세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signup_cancel:
                finish();
                break;
            case R.id.signup_check:
                if(idEdit.getText().length() > 0) {
                    getIdCheck(idEdit.getText().toString() + emailSpinner.getSelectedItem().toString());
                }else {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
