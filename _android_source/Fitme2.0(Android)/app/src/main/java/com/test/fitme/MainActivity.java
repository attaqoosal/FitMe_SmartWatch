package com.test.fitme;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.fitme.bluetooth.AppSettings;
import com.test.fitme.bluetooth.BTCTemplateService;
import com.test.fitme.bluetooth.Constants;
import com.test.fitme.bluetooth.RecycleUtils;
import com.test.fitme.fragment.GoalFragment;
import com.test.fitme.fragment.HealthFragment;
import com.test.fitme.fragment.HeartBeatFragment;
import com.test.fitme.fragment.HomeFragment;
import com.test.fitme.fragment.RunFragment;
import com.test.fitme.fragment.SleepFragment;
import com.test.fitme.retrofit.RetrofitCallback;
import com.test.fitme.retrofit.SensorRetrofitClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Timer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private SensorRetrofitClient sensorRetrofitClient;
    private TextView registerTv, logoutTv, nameTv;
    private ImageView homeImg;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    // Context, System
    private Context mContext;
    private BTCTemplateService mService;
    private ActivityHandler mActivityHandler;

    private ImageView mImageBT = null;
    private TextView mTextStatus = null;

    // Refresh timer
    private Timer mRefreshTimer = null;

    private Button sendBtn;
    private EditText sendEdit;

    private SQLiteHelper helper;
    private boolean isSuccessBluetooth;
    private StringBuffer receiveDate;
    private String user_id;

//    private final int WALK_COUNT = 0;
//    private final int RUN_COUNT = 1;
//    private final int WALK_DIST = 2;
//    private final int RUN_DIST = 3;
//    private final int DUMBELL_COUNT = 4;
//    private final int BARBELL_COUNT = 5;
//    private final int JUMPROPE_COUNT = 6;
//    private final int SITUP_COUNT = 7;
//    private final int HEARTBEAT_MAX = 8;
//    private final int HEARTBEAT_MIN = 9;
//    private final int SLEEP_COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;	//.getApplicationContext();
        mActivityHandler = new ActivityHandler();
        AppSettings.initializeAppSettings(mContext);

        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("login", 0);
        editor = sp.edit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        homeImg = findViewById(R.id.appbar_home);
        homeImg.setOnClickListener(this);
        registerTv = navigationView.getHeaderView(0).findViewById(R.id.nav_header_product_register);
        registerTv.setPaintFlags(registerTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        registerTv.setOnClickListener(this);
        logoutTv = navigationView.getHeaderView(0).findViewById(R.id.nav_header_logout);
        logoutTv.setPaintFlags(logoutTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        logoutTv.setOnClickListener(this);
        nameTv = navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
        if(sp.getString("name", "").length() > 0) {
            nameTv.setText(sp.getString("name", ""));
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, HomeFragment.newInstance());
        fragmentTransaction.commit();

        sensorRetrofitClient = SensorRetrofitClient.getInstance(this).createBaseApi();

//        sendEdit = findViewById(R.id.test_edit);
//        sendBtn = findViewById(R.id.test_btn);
//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mService != null && sendEdit.getText().toString() != null)
//                    mService.sendMessageToRemote(sendEdit.getText().toString());
//            }
//        });
        // Setup views
        mImageBT = findViewById(R.id.status_title);
        mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
        mTextStatus = findViewById(R.id.status_text);
        mTextStatus.setText(getResources().getString(R.string.bt_state_init));


        this.deleteDatabase("fitme_db");
        helper = new SQLiteHelper(this);

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if(helper.select_goal(today) == null) {
            helper.insert(new GoalVO(today, 1000, 1000, 100, 100, 100, 100));
        }
        helper.insert(new GoalVO("2018-06-27", 1000, 2000, 77, 100, 100, 100));
        helper.insert(new GoalVO("2018-06-25", 5000, 1000, 100, 100, 100, 50));

        // Do data initialization after service started and binded

//        double aa = Calculation.walk_kcal(180, 70, 10000);
//        double bb = Calculation.walk_kcal(170, 70, 10000);
//        double cc = Calculation.walk_kcal(170, 60, 10000);
//        double dd = Calculation.walk_kcal(170, 65, 10000);

        receiveDate = new StringBuffer();
        user_id = sp.getString("id", "");
        doStartService();

        ///////////////////////////////////////////////////////////////////////////////////////
        WalkVO walk1 = new WalkVO(today, 678, 260, 264);
        WalkVO walk2 = new WalkVO("2018-06-27", 232, 67, 78);
        WalkVO walk3 = new WalkVO("2018-06-25", 1200, 502, 671);

        helper.insert(walk1);
        helper.insert(walk2);
        helper.insert(walk3);

        RunVO runVO = new RunVO(today, 0, 0, 0, user_id);
        helper.insert(runVO);
        RunVO runVO1 = new RunVO("2018-06-27", 200, 131, 244, user_id);
        helper.insert(runVO1);

        HeartBeatVO heartBeatVO = new HeartBeatVO("2018062800", 120, 66, user_id);
        helper.insert(heartBeatVO);
        HeartBeatVO heartBeatVO1 = new HeartBeatVO("2018062801", 119, 68, user_id);
        helper.insert(heartBeatVO1);
        HeartBeatVO heartBeatVO2 = new HeartBeatVO("2018062802", 122, 76, user_id);
        helper.insert(heartBeatVO2);
        HeartBeatVO heartBeatVO3 = new HeartBeatVO("2018062803",115,  77, user_id);
        helper.insert(heartBeatVO3);
        HeartBeatVO heartBeatVO4 = new HeartBeatVO("2018062804", 125,65,  user_id);
        helper.insert(heartBeatVO4);
        HeartBeatVO heartBeatVO5 = new HeartBeatVO("2018062805",128,  66, user_id);
        helper.insert(heartBeatVO5);
        HeartBeatVO heartBeatVO6 = new HeartBeatVO("2018062806", 113,66,  user_id);
        helper.insert(heartBeatVO6);
        HeartBeatVO heartBeatVO7 = new HeartBeatVO("2018062807", 127,74,  user_id);
        helper.insert(heartBeatVO7);
        HeartBeatVO heartBeatVO8 = new HeartBeatVO("2018062816", 112, 62, user_id);
        helper.insert(heartBeatVO8);
        HeartBeatVO heartBeatVO9 = new HeartBeatVO("2018062810", 101, 64, user_id);
        helper.insert(heartBeatVO9);
        HeartBeatVO heartBeatVO10 = new HeartBeatVO("2018062812", 113, 74, user_id);
        helper.insert(heartBeatVO10);
        HeartBeatVO heartBeatV11 = new HeartBeatVO("2018062816", 127, 75, user_id);
        helper.insert(heartBeatV11);
        HeartBeatVO heartBeatV12 = new HeartBeatVO("2018062818", 119, 71, user_id);
        helper.insert(heartBeatV12);


        SleepVO sleepVO = new SleepVO("2018062818", 15, user_id);
        helper.insert(sleepVO);
        SleepVO sleepVO1 = new SleepVO("2018062819", -5, user_id);
        helper.insert(sleepVO1);
        SleepVO sleepVO2 = new SleepVO("2018062823", 11, user_id);
        helper.insert(sleepVO2);
        SleepVO sleepVO3 = new SleepVO("2018062820", 14, user_id);
        helper.insert(sleepVO3);
        SleepVO sleepVO4 = new SleepVO("2018062822", 6, user_id);
        helper.insert(sleepVO4);
        SleepVO sleepVO5 = new SleepVO("2018062821", 9, user_id);
        helper.insert(sleepVO5);
        SleepVO sleepVO6 = new SleepVO("2018062817", -7, user_id);
        helper.insert(sleepVO6);

        String today3 = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date());
        WalkVO walkVO = new WalkVO(today3, 100, 200, 100);
        helper.insert(walkVO);

        String today2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        SitUpVO sitUpVO = new SitUpVO(today2, 44, 100, user_id);
        helper.insert(sitUpVO);
        BarbellVO barbellVO = new BarbellVO(today2, 15, user_id);
        helper.insert(barbellVO);
        DumbBellVO dumbBellVO = new DumbBellVO(today2, 0, user_id);
        helper.insert(dumbBellVO);
        JumpRopeVO jumpRopeVO = new JumpRopeVO(today2, 13, 14, user_id);
        helper.insert(jumpRopeVO);

        String today1 = "2018-06-27";
        helper.insert(new GoalVO(today1, 1000, 1000, 100, 100, 100, 100));
        SitUpVO sitUpVO1 = new SitUpVO(today1, 77, 100, user_id);
        helper.insert(sitUpVO1);
        BarbellVO barbellVO1 = new BarbellVO(today1, 54, user_id);
        helper.insert(barbellVO1);
        DumbBellVO dumbBellVO1 = new DumbBellVO(today1, 2, user_id);
        helper.insert(dumbBellVO1);
        JumpRopeVO jumpRopeVO1 = new JumpRopeVO(today1, 100, 14, user_id);
        helper.insert(jumpRopeVO1);

//        WalkVO walkVO = new WalkVO(today2, 100, 200, 100);
//        walkVO.setUser_id(user_id);
//        postWalk(walkVO);

//        RunVO runVO = new RunVO(today2, 111, 222, 333, user_id);
//        postRun(runVO);

//        BarbellVO barbellVO = new BarbellVO(today2, 22, user_id);
//        postBarbell(barbellVO);
//
//        DumbBellVO dumbBellVO = new DumbBellVO(today2, 33, user_id);
//        postDumbBell(dumbBellVO);

//        SitUpVO sitUpVO = new SitUpVO(today2, 44, 100, user_id);
//        postSitup(sitUpVO);
//
//        JumpRopeVO jumpRopeVO = new JumpRopeVO(today2, 13, 14, user_id);
//        postJumpRope(jumpRopeVO);

//        String today3 = new SimpleDateFormat("yyyyMMddHH").format(new Date());
//        HeartBeatVO heartBeatVO = new HeartBeatVO(today3+"00", 55, 66, user_id);
//        postHeartBeat(heartBeatVO);
//
//        SleepVO sleepVO = new SleepVO(today3+"00", 20, user_id);
//        postSleep(sleepVO);


    }
    public void postWalk(WalkVO walk) {
        sensorRetrofitClient.postWalk(walk, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("확인(postWalk)", "" + receivedData);
            }
            @Override
            public void onFailure(int code) { }
        });
    }
    public void postRun(RunVO run) {
        sensorRetrofitClient.postRun(run, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("확인(postWalk)", "" + receivedData);
            }
            @Override
            public void onFailure(int code) { }
        });
    }
    public void postDumbBell(DumbBellVO dumbBell) {
        sensorRetrofitClient.postDumbbell(dumbBell, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("확인(postWalk)", "" + receivedData);
            }
            @Override
            public void onFailure(int code) { }
        });
    }
    public void postBarbell(BarbellVO barbell) {
        sensorRetrofitClient.postBarbell(barbell, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("확인(postWalk)", "" + receivedData);
            }
            @Override
            public void onFailure(int code) { }
        });
    }
    public void postJumpRope(JumpRopeVO jumpRope) {
        sensorRetrofitClient.postJumpeRope(jumpRope, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("확인(postWalk)", "" + receivedData);
            }
            @Override
            public void onFailure(int code) { }
        });
    }
    public void postSitup(SitUpVO situp) {
        sensorRetrofitClient.postSitup(situp, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("확인(postWalk)", "" + receivedData);
            }
            @Override
            public void onFailure(int code) { }
        });
    }
    public void postHeartBeat(HeartBeatVO heartBeat) {
        sensorRetrofitClient.postHeartBeat(heartBeat, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("확인(postWalk)", "" + receivedData);
            }
            @Override
            public void onFailure(int code) { }
        });
    }
    public void postSleep(SleepVO sleep) {
        sensorRetrofitClient.postSleep(sleep, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) { }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("확인(postWalk)", "" + receivedData);
            }
            @Override
            public void onFailure(int code) { }
        });
    }

//    public void postUser(UserVO user) {
//        sensorRetrofitClient.postUser(user, new RetrofitCallback() {
//            @Override
//            public void onError(Throwable t) { }
//
//            @Override
//            public void onSuccess(int code, Object receivedData) {
//                Log.d("확인(postWalk)", "" + receivedData);
//            }
//            @Override
//            public void onFailure(int code) { }
//        });
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bluetooth) {
            if(!isSuccessBluetooth) {
                if(sp.getString("serial_num", "").isEmpty()) {
                    Toast.makeText(getApplicationContext(), "기기를 등록하세요.", Toast.LENGTH_SHORT).show();
                }else {
                    doScan();
                }
            }else {
                Toast.makeText(getApplicationContext(), "블루투스가 이미 연결되었습니다.", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_walk) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, HomeFragment.newInstance()).commit();
        } else if (id == R.id.nav_health) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, HealthFragment.newInstance()).commit();
        } else if (id == R.id.nav_sleep) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, SleepFragment.newInstance()).commit();
        } else if (id == R.id.nav_run) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, RunFragment.newInstance()).commit();
        } else if (id == R.id.nav_heartbeat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, HeartBeatFragment.newInstance()).commit();
        } else if (id == R.id.nav_record) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, RunFragment.newInstance()).commit();
        } else if (id == R.id.nav_goal) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, GoalFragment.newInstance()).commit();
        } else if (id == R.id.nav_info) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            NumberPickerDialogFragment numberPickerDialogFragment = NumberPickerDialogFragment.newInstance(0);
            numberPickerDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
            numberPickerDialogFragment.show(ft, "NumberPicker");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_header_product_register:
                if(sp.getString("serial_num", "").isEmpty()) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    RegisterDialogFragment registerDialogFragment = RegisterDialogFragment.newInstance();
                    registerDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                    registerDialogFragment.show(ft, "Register Smartwatch");
                }else {
                    Toast.makeText(getApplicationContext(), "기기가 이미 등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_header_logout:
                Log.d("로그아웃", sp.getString("id", "") + " " + sp.getString("pw", "") +
                        " " + sp.getBoolean("save", false) + " " + sp.getBoolean("auto", false));
                editor.remove("user");
                editor.remove("pw");
                editor.remove("name");
                editor.remove("serial_num");
                editor.remove("weight");
                editor.remove("height");
                editor.putBoolean("auto", false);
                if(!sp.getBoolean("save", false)) {
                    editor.remove("id");
                }
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.appbar_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, HomeFragment.newInstance()).commit();
                break;
        }
    }

    @Override
    public void onStop() {
        // Stop the timer
        if(mRefreshTimer != null) {
            mRefreshTimer.cancel();
            mRefreshTimer = null;
        }
        super.onStop();
    }

    @Override
    public void onLowMemory (){
        super.onLowMemory();
        // onDestroy is not always called when applications are finished by Android system.
        finalizeActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!sp.getBoolean("auto", false)) {
            editor.remove("user");
            editor.remove("pw");
            editor.remove("name");
            editor.remove("weight");
            editor.remove("height");
            editor.remove("serial_num");
            if (!sp.getBoolean("save", false)) {
                editor.remove("id");
            }
            editor.commit();
        }
        finalizeActivity();
    }

    private ServiceConnection mServiceConn = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder) {
            mService = ((BTCTemplateService.ServiceBinder) binder).getService();

            // Activity couldn't work with mService until connections are made
            // So initialize parameters and settings here. Do not initialize while running onCreate()
            initialize();
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };

    /**
     * Start service if it's not running
     */
    private void doStartService() {
        startService(new Intent(this, BTCTemplateService.class));
        bindService(new Intent(this, BTCTemplateService.class), mServiceConn, Context.BIND_AUTO_CREATE);
    }

    /**
     * Stop the service
     */
    private void doStopService() {
        mService.finalizeService();
        stopService(new Intent(this, BTCTemplateService.class));
    }

    /**
     * Initialization / Finalization
     */
    private void initialize() {
        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.bt_ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        mService.setupService(mActivityHandler);

        // If BT is not on, request that it be enabled.
        // RetroWatchService.setupBT() will then be called during onActivityResult
        if(!mService.isBluetoothEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, Constants.REQUEST_ENABLE_BT);
        }

        // Load activity reports and display
        if(mRefreshTimer != null) {
            mRefreshTimer.cancel();
        }

        // Use below timer if you want scheduled job
        //mRefreshTimer = new Timer();
        //mRefreshTimer.schedule(new RefreshTimerTask(), 5*1000);
    }

    private void finalizeActivity() {
        if(!AppSettings.getBgService()) {
            doStopService();
        } else {
        }

        // Clean used resources
        RecycleUtils.recursiveRecycle(getWindow().getDecorView());
        System.gc();
    }

    /**
     * Launch the DeviceListActivity to see devices and do scan
     */
    private void doScan() {
        Intent intent = new Intent(this, BluetoothActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CONNECT_DEVICE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case Constants.REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    isSuccessBluetooth = true;
                    // Get the device MAC address
                    String address = data.getExtras().getString(BluetoothActivity.EXTRA_DEVICE_ADDRESS);
                    // Attempt to connect to the device
                    if(address != null && mService != null)
                        mService.connectDevice(address);
                } else if(resultCode == Activity.RESULT_CANCELED) {
                    isSuccessBluetooth = false;
                }
                break;

            case Constants.REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a BT session
                    mService.setupBLE();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                }
                break;
        }	// End of switch(requestCode)
    }

    public class ActivityHandler extends Handler {
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what) {
                // Receives BT state messages from service
                // and updates BT state UI
                case Constants.MESSAGE_BT_STATE_INITIALIZED:
                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
                            getResources().getString(R.string.bt_state_init));
                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
                    break;
                case Constants.MESSAGE_BT_STATE_LISTENING:
                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
                            getResources().getString(R.string.bt_state_wait));
                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
                    break;
                case Constants.MESSAGE_BT_STATE_CONNECTING:
                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
                            getResources().getString(R.string.bt_state_connect));
                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_away));
                    break;
                case Constants.MESSAGE_BT_STATE_CONNECTED:
                    if(mService != null) {
                        String deviceName = mService.getDeviceName();
                        if(deviceName != null) {
                            mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
                                    getResources().getString(R.string.bt_state_connected) + " " + deviceName);
                            mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
                        } else {
                            mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +
                                    getResources().getString(R.string.bt_state_connected) + " no name");
                            mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
                        }
                    }
                    break;
                case Constants.MESSAGE_BT_STATE_ERROR:
                    mTextStatus.setText(getResources().getString(R.string.bt_state_error));
                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_busy));
                    break;

                // BT Command status
                case Constants.MESSAGE_CMD_ERROR_NOT_CONNECTED:
                    mTextStatus.setText(getResources().getString(R.string.bt_cmd_sending_error));
                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_busy));
                    break;

                ///////////////////////////////////////////////
                // When there's incoming packets on bluetooth
                // do the UI works like below
                ///////////////////////////////////////////////
                case Constants.MESSAGE_READ_CHAT_DATA:
                    if(msg.obj != null) {
                        Log.d("가즈야", (String) msg.obj);
                        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        String postToday = new SimpleDateFormat("yyyyMMdd").format(new Date());
                        receiveDate.append(msg.obj);
                        char delim = receiveDate.charAt(0);
                        receiveDate.deleteCharAt(0);
                        receiveDate.deleteCharAt(receiveDate.length() - 1);
                        StringTokenizer st = null;
                        int count = 0;
                        int dist = 0;
                        switch (delim) {
                            case 'w':
                                Log.d("가즈야", "Walk");
                                st = new StringTokenizer(receiveDate.toString(), ":");
                                count = Integer.parseInt(st.nextToken());
                                dist = Integer.parseInt(st.nextToken());
                                if (count > 0 && dist > 0) {
                                    WalkVO walk = new WalkVO(today, count, dist, Calculation.walk_kcal
                                            (sp.getInt("height", 0), sp.getInt("weight", 0), count));
                                    walk.setUser_id(user_id);
                                    if(helper.select_walk(today) != null) {
                                        helper.update(walk);
                                    }else {
                                        helper.insert(walk);
                                    }
                                    walk.setDate(postToday);
                                    postWalk(walk);
                                }
                                break;
                            case 'r':
                                Log.d("가즈야", "Run" + receiveDate.toString());
                                st = new StringTokenizer(receiveDate.toString(), ":");
                                count = Integer.parseInt(st.nextToken());
                                dist = Integer.parseInt(st.nextToken());
                                if(count > 0 && dist > 0) {
                                    RunVO run = new RunVO(today, count, dist, Calculation.run_kcal
                                            (sp.getInt("height", 0), sp.getInt("weight", 0), count), user_id);
                                    if(helper.select_run(today) != null) {
                                        helper.update(run);
                                    }else {
                                        helper.insert(run);
                                    }
                                    run.setDate(postToday);
                                    postRun(run);
                                }
                                break;
                            case 'd':
                                Log.d("가즈야", "Dumbbell");
                                count = Integer.parseInt(receiveDate.toString());
                                DumbBellVO dumbBell = new DumbBellVO(today, count, user_id);
                                if(helper.select_dumbBell(today) != null) {
                                    helper.update(dumbBell);
                                }else {
                                    helper.insert(dumbBell);
                                }
                                dumbBell.setDate(postToday);
                                postDumbBell(dumbBell);
                                break;
                            case 'b':
                                Log.d("가즈야", "Barbell");
                                count = Integer.parseInt(receiveDate.toString());
                                BarbellVO barbell = new BarbellVO(today, count, user_id);
                                if(helper.select_barbell(today) != null) {
                                    helper.update(barbell);
                                }else {
                                    helper.insert(barbell);
                                }
                                barbell.setDate(postToday);
                                postBarbell(barbell);
                                break;
                            case 's':
                                Log.d("가즈야", "Sleep");
                                count = Integer.parseInt(receiveDate.toString());
                                SitUpVO situp = new SitUpVO(today, count, Calculation.situp_kcal(count), user_id);
                                if(helper.select_situp(today) != null) {
                                    helper.update(situp);
                                }else {
                                    helper.insert(situp);
                                }
                                situp.setDate(postToday);
                                postSitup(situp);
                                break;
                            case 'j':
                                Log.d("가즈야", "Jumprope");
                                count = Integer.parseInt(receiveDate.toString());
                                JumpRopeVO jumpRope = new JumpRopeVO(today, count, Calculation.jumpRope_kcal(count), user_id);
                                if(helper.select_jumpRope(today) != null) {
                                    helper.update(jumpRope);
                                }else {
                                    helper.insert(jumpRope);
                                }
                                jumpRope.setDate(postToday);
                                postJumpRope(jumpRope);
                                break;
                            case 'h':
                                Log.d("가즈야", "Heartbeat");
                                st = new StringTokenizer(receiveDate.toString(), ":");
                                String time = new SimpleDateFormat("yyyyMMddHH").format(new Date());
                                int max = Integer.parseInt(st.nextToken());
                                int min = Integer.parseInt(st.nextToken());
                                HeartBeatVO heartBeat = new HeartBeatVO(time, max, min, user_id);
                                helper.insert(heartBeat);
                                postHeartBeat(heartBeat);
                                break;
                            case 'a':
                                Log.d("가즈야", "Sleep");
                                st = new StringTokenizer(receiveDate.toString(), ":");
                                String time2 = new SimpleDateFormat("yyyyMMddHH").format(new Date());
                                int value = Integer.parseInt(st.nextToken());
                                if(value <= 15) {
                                    value -= 16;
                                }else {
                                    value -= 15;
                                }
                                int cnt = Integer.parseInt(st.nextToken());
                                SleepVO sleep = new SleepVO(time2, value, user_id);
                                helper.insert(sleep);
                                postSleep(sleep);
                                break;
                        }
                        receiveDate.setLength(0);
                    }

//                case Constants.MESSAGE_READ_CHAT_DATA:
//                    if(msg.obj != null) {
//                        Log.d("가즈야", (String)msg.obj);
//                        receiveDate.append(msg.obj);
//                        if(receiveDate.charAt(receiveDate.length()-1) == 'e') {
//                            ArrayList<Integer> parsingDate = new ArrayList<>();
//                            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
//                            receiveDate.deleteCharAt(0);
//                            receiveDate.deleteCharAt(receiveDate.length()-1);
//                            StringTokenizer st = new StringTokenizer(receiveDate.toString(), ":");
//                            while(st.hasMoreTokens()) {
//                                parsingDate.add(Integer.parseInt(st.nextToken()));
//                            }
//                            for(int index = 0; index < parsingDate.size(); index++) {
//                                switch (index) {
//                                    case WALK_COUNT:
//                                        if(parsingDate.get(WALK_COUNT) > 0) {
//                                            int count = parsingDate.get(WALK_COUNT);
//                                            WalkVO walk = new WalkVO(today, count, parsingDate.get(WALK_DIST), Calculation.walk_kcal
//                                                    (sp.getInt("height", 0), sp.getInt("weight", 0), count));
//                                            walk.setUser_id(user_id);
//                                            helper.insert(walk);
//                                            postWalk(walk);
//                                        }
//                                        break;
//                                    case RUN_COUNT:
//                                        if(parsingDate.get(RUN_COUNT) > 0) {
//                                            int count = parsingDate.get(RUN_COUNT);
//                                            RunVO run = new RunVO(today, count, parsingDate.get(RUN_DIST), Calculation.run_kcal
//                                                    (sp.getInt("height", 0), sp.getInt("weight", 0), count), user_id);
//                                            helper.insert(run);
//                                            postRun(run);
//                                        }
//                                        break;
//                                    case DUMBELL_COUNT:
//                                        if(parsingDate.get(DUMBELL_COUNT) > 0) {
//                                            int count = parsingDate.get(DUMBELL_COUNT);
//                                            DumbBellVO dumbBell = new DumbBellVO(today, count, user_id);
//                                            helper.insert(dumbBell);
//                                            postDumbBell(dumbBell);
//                                        }
//                                        break;
//                                    case BARBELL_COUNT:
//                                        if(parsingDate.get(BARBELL_COUNT) > 0) {
//                                            int count = parsingDate.get(BARBELL_COUNT);
//                                            BarbellVO barbell = new BarbellVO(today, count, user_id);
//                                            helper.insert(barbell);
//                                            postBarbell(barbell);
//                                        }
//                                        break;
//                                    case JUMPROPE_COUNT:
//                                        if(parsingDate.get(JUMPROPE_COUNT) > 0) {
//                                            int count = parsingDate.get(JUMPROPE_COUNT);
//                                            JumpRopeVO jumpRope = new JumpRopeVO(today, count, Calculation.jumpRope_kcal(count), user_id);
//                                            helper.insert(jumpRope);
//                                            postJumpRope(jumpRope);
//                                        }
//                                        break;
//                                    case SITUP_COUNT:
//                                        if(parsingDate.get(SITUP_COUNT) > 0) {
//                                            int count = parsingDate.get(SITUP_COUNT);
//                                            SitUpVO situp = new SitUpVO(today, count, Calculation.situp_kcal(count), user_id);
//                                            helper.insert(situp);
//                                            postSitup(situp);
//                                        }
//                                        break;
//                                    case HEARTBEAT_MAX:
//                                        if(parsingDate.get(HEARTBEAT_MAX) > 0) {
//                                            String time = new SimpleDateFormat("yyyyMMddHH").format(new Date());
//                                            time += "00";
//                                            int max = parsingDate.get(HEARTBEAT_MAX);
//                                            int min = parsingDate.get(HEARTBEAT_MIN);
//                                            HeartBeatVO heartBeat = new HeartBeatVO(time, max, min, user_id);
//                                            helper.insert(heartBeat);
//                                            postHeartBeat(heartBeat);
//                                        }
//                                        break;
//                                    case SLEEP_COUNT:
//                                        if(parsingDate.get(SLEEP_COUNT) > 0) {
//                                            String time = new SimpleDateFormat("yyyyMMddHH").format(new Date());
//                                            time += "00";
//                                            int value = parsingDate.get(SLEEP_COUNT);
//                                            SleepVO sleep = new SleepVO(time, value, user_id);
//                                            helper.insert(sleep);
//                                            postSleep(sleep);
//                                        }
//                                        break;
//                                }
//                            }
//                            receiveDate.setLength(0);
//                        }else if(receiveDate.charAt(receiveDate.length()-1) == 'w'){
//                            receiveDate.deleteCharAt(0);
//                            receiveDate.deleteCharAt(receiveDate.length()-1);
//
//                            receiveDate.setLength(0);
//
//                        }
//
//                    }
//                    break;
//
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    }	// End of class ActivityHandler
}
