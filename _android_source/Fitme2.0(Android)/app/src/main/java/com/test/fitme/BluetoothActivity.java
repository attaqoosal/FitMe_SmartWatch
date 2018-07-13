package com.test.fitme;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.fitme.bluetooth.BleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {
   // Debugging
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private AnimatedCircleLoadingView animatedCircleLoadingView;

    // Constants
    public static final long SCAN_PERIOD = 5*1000;	// Stops scanning after a pre-defined scan period.

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    private ActivityHandler mActivityHandler;
    private BluetoothAdapter mBtAdapter;
    private BleManager mBleManager;

    private ArrayList<BluetoothDevice> mDevices = new ArrayList<BluetoothDevice>();

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    // UI stuff
//    Button mScanButton = null;
//    private TextView deviceTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bluetooth);

        // Set result CANCELED incase the user backs out
        setResult(Activity.RESULT_CANCELED);

        sp = getSharedPreferences("login", 0);
        editor = sp.edit();

        mActivityHandler = new ActivityHandler();
        animatedCircleLoadingView = findViewById(R.id.bluetooth_progressbar);
        // Initialize the button to perform device discovery
//        mScanButton = (Button) findViewById(R.id.bluetooth_scan);
//        mScanButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                doDiscovery();
//                v.setVisibility(View.GONE);
//                startLoading();
//                //startPercentMockThread();
//            }
//        });

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        // Get BLE Manager
        mBleManager = BleManager.getInstance(getApplicationContext(), null);
        mBleManager.setScanCallback(mLeScanCallback);

//        deviceTv = findViewById(R.id.bluetooth_text);
//        deviceTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mBtAdapter.cancelDiscovery();
//                // Get the device MAC address, which is the last 17 chars in the View
//                String info = ((TextView) v).getText().toString();
//                if(info != null && info.length() > 16) {
//                    String address = info.substring(info.length() - 17);
//                    Log.d(TAG, "User selected device : " + address);
//
//                    // Create the result Intent and include the MAC address
//                    Intent intent = new Intent();
//                    intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
//
//                    // Set result and finish this Activity
//                    setResult(Activity.RESULT_OK, intent);
//                    finish();
//                }
//            }
//        });

        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+ Permission APIs
            fuckMarshMallow();
        }
//        doDiscovery();
//        startLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }
    }
    private void startLoading() {
        animatedCircleLoadingView.startDeterminate();
    }

    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //Thread.sleep(1000);
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(40);
                        changePercent(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    private void changePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.setPercent(percent);
            }
        });
    }

    public void resetLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.resetLoading();
            }
        });
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        if(D) Log.d(TAG, "doDiscovery()");

        // Indicate scanning in the title
        setTitle("Scanning");

        // Empty cache
        mDevices.clear();

        // If we're already discovering, stop it
        if (mBleManager.getState() == BleManager.STATE_SCANNING) {
            mBleManager.scanLeDevice(false);
        }

        // Request discover from BluetoothAdapter
        mBleManager.scanLeDevice(true);

        // Stops scanning after a pre-defined scan period.
        //일정 시간이 지나면 자동으로 스캔을 종료한다.
        mActivityHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopDiscovery();
            }
        }, SCAN_PERIOD);
    }

    /**
     * Stop device discover
     */
    private void stopDiscovery() {
        // Indicate scanning in the title
        setTitle("complete");
        // Show scan button
        //mScanButton.setVisibility(View.VISIBLE);
        mBleManager.scanLeDevice(false);

        if(mDevices.size() > 0) {
            animatedCircleLoadingView.stopOk();
            mBtAdapter.cancelDiscovery();
            String address = mDevices.get(0).getAddress();

            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

            // Set result and finish this Activity
            setResult(Activity.RESULT_OK, intent);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                }
            }, 3000);
        }else {
            animatedCircleLoadingView.stopFailure();
            setResult(Activity.RESULT_CANCELED);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                }
            }, 3000);
        }

    }

    /**
     * Check if it's already cached
     */
    private boolean checkDuplicated(BluetoothDevice device) {
        for(BluetoothDevice dvc : mDevices) {
            if(device.getAddress().equalsIgnoreCase(dvc.getAddress())) {
                return true;
            }
        }
        return false;
    }


    /**
     * BLE scan callback
     */
//    sp.getString("serial_num", "")
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                                if(!checkDuplicated(device)) {
                                    if(device.getName() != null && device.getName().equals("111111")) {
                                        //deviceTv.setText(device.getName() + "\n" + device.getAddress());
                                        mDevices.add(device);
                                    }
                                }
                            }
                        }
                    });
                }
            };

    public class ActivityHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {}
            super.handleMessage(msg);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }

                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    doDiscovery();
                    startLoading();
                    Toast.makeText(this, "All Permission GRANTED", Toast.LENGTH_SHORT)
                            .show();

                } else {
                    // Permission Denied
                    Toast.makeText(this, "One or More Permissions are DENIED Exiting App", Toast.LENGTH_SHORT)
                            .show();

                    finish();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void fuckMarshMallow() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("Show Location");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {

                // Need Rationale
                String message = "App need access to " + permissionsNeeded.get(0);

                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);

                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
        Log.d("블루투스", "ㅁㅁㅁ");
        doDiscovery();
        startLoading();
//        Toast.makeText(this, "No new Permission Required- Launching App .You are Awesome!!", Toast.LENGTH_SHORT)
//                .show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {

        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }
}
