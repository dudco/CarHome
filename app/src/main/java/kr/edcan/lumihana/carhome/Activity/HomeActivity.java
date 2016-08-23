package kr.edcan.lumihana.carhome.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import kr.edcan.lumihana.carhome.R;
import kr.edcan.lumihana.carhome.Utils.ShareManager;

public class HomeActivity extends AppCompatActivity {
    private ShareManager shareManager;
    private float defaultTemp = 18.0f;
    private final float speed = 0.1f;
    private BluetoothSPP bluetoothSPP;
    private Thread updateThread;
    private int loopCount = 0;
    private float currentTemp = 0.0f;
    private boolean currentWorking = true;
    private boolean isGas, isElect;
    private SharedPreferences sharedPreferences;
    private int time = 60;
    private RelativeLayout relative_temp, relative_visit, relative_security;
    private View view_temp, view_visit, view_security;
    private TextView text_air, text_connection, text_onoff, text_time, text_gas, text_elec;
    private ImageView image_status;
    private LinearLayout linear_temp, linear_security;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        text_air = (TextView) findViewById(R.id.home_text_air);
        text_connection = (TextView) findViewById(R.id.home_text_connection);
        text_onoff = (TextView) findViewById(R.id.home_text_onoff);
        text_time = (TextView) findViewById(R.id.home_text_time);
        text_gas = (TextView) findViewById(R.id.home_text_gas);
        text_elec = (TextView) findViewById(R.id.home_text_elec);
        image_status = (ImageView) findViewById(R.id.home_image_status);
        relative_temp = (RelativeLayout) findViewById(R.id.home_relative_temp);
        relative_visit = (RelativeLayout) findViewById(R.id.home_relative_visit);
        relative_security = (RelativeLayout) findViewById(R.id.home_relative_security);
        linear_security = (LinearLayout) findViewById(R.id.home_linear_security);
        linear_temp = (LinearLayout) findViewById(R.id.home_linear_temp);
        view_temp = (View) findViewById(R.id.home_view_temp);
        view_visit = (View) findViewById(R.id.home_view_visit);
        view_security = (View) findViewById(R.id.home_view_security);

        shareManager = new ShareManager(HomeActivity.this);
        sharedPreferences = getSharedPreferences("carHome", MODE_PRIVATE);

        setToggleConnection(true);
        initBluetooth();


        relative_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(0);
                return;
            }
        });

        relative_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(1);
            }
        });

        relative_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(2);
            }
        });

        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    shareManager.getTurn();
                    shareManager.setShareGet();
                    shareManager.getTemperature();
                    shareManager.getEg();
                    final boolean isTurn = sharedPreferences.getBoolean("turn", false);
                    final boolean isGas = sharedPreferences.getBoolean("isGas", false);
                    final boolean isElect = sharedPreferences.getBoolean("isElect", false);
                    final float receivedTemp = Float.parseFloat(sharedPreferences.getString("CarTemperature", "0.0f"));
                    final boolean isWorking = sharedPreferences.getBoolean("Working", false);

                    HomeActivity.this.isGas = isGas;
                    HomeActivity.this.isElect = isElect;

                    if (currentTemp != receivedTemp) {
                        HomeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isWorking) {
                                    setToggleOnOff(true);
                                    text_air.setText("현재 차량 온도 : " + (int) receivedTemp);
                                } else {
                                    setToggleOnOff(false);
                                    text_air.setText("현재 차량 온도 : " + isWorking);
                                }
                            }
                        });


                        if (isWorking && isWorking != currentWorking) sendWithBluetooth("T");
                        else if (!isWorking && isWorking != currentWorking) sendWithBluetooth("F");

                        if (isWorking) sendWithBluetooth((int) receivedTemp + "");

                        currentTemp = receivedTemp;
                        currentWorking = isWorking;
                    }

                    if (isTurn) {
                        Log.e("sended", isGas + " " + isElect);
                        shareManager.setData(isGas, isElect);
                        if (isGas) {
                            HomeActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    text_gas.setText("off");
                                    text_gas.setBackgroundResource(R.drawable.ripple_toggle_button_off);
                                }
                            });
                            sendWithBluetooth("G");
                        }
                        if (isElect) {
                            HomeActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    text_elec.setText("off");
                                    text_elec.setBackgroundResource(R.drawable.ripple_toggle_button_off);
                                }
                            });
                            sendWithBluetooth("E");
                        }
                        shareManager.turnOff();
                        HomeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text_gas.setText("off");
                                text_gas.setBackgroundResource(R.drawable.ripple_toggle_button_off);
                                text_elec.setText("off");
                                text_elec.setBackgroundResource(R.drawable.ripple_toggle_button_off);
                            }
                        });
                        HomeActivity.this.isElect = false;
                        HomeActivity.this.isGas = false;
                        shareManager.egUpdate(false, false);
                    }

                    HomeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text_time.setText("집까지 남은 시간 : " + time);

                            if(!isTurn) {
                                if (HomeActivity.this.isGas) {
                                    text_gas.setText("on");
                                    text_gas.setBackgroundResource(R.drawable.ripple_toggle_button_on);
                                } else {
                                    text_gas.setText("off");
                                    text_gas.setBackgroundResource(R.drawable.ripple_toggle_button_off);
                                }

                                if (HomeActivity.this.isElect) {
                                    text_elec.setText("on");
                                    text_elec.setBackgroundResource(R.drawable.ripple_toggle_button_on);
                                } else {
                                    text_elec.setText("off");
                                    text_elec.setBackgroundResource(R.drawable.ripple_toggle_button_off);
                                }
                            }
                        }
                    });

                    if (time > 0) time--;

                    loopCount++;
                    Log.e("Home -> loopCount", loopCount + "");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        );

        updateThread.start();
    }

    private void selectTab(int num) {
        switch (num) {
            case 0: {
                view_temp.setVisibility(View.VISIBLE);
                view_visit.setVisibility(View.INVISIBLE);
                view_security.setVisibility(View.INVISIBLE);
                linear_temp.setVisibility(View.VISIBLE);
                linear_security.setVisibility(View.GONE);
                break;
            }
            case 1: {
                new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Error")
                        .setMessage("Permission Denied")
                        .show();
                break;
            }
            case 2: {
                view_temp.setVisibility(View.INVISIBLE);
                view_visit.setVisibility(View.INVISIBLE);
                view_security.setVisibility(View.VISIBLE);
                linear_temp.setVisibility(View.GONE);
                linear_security.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void setToggleConnection(boolean onoff) {
        if (onoff) {
            text_connection.setText("connected");
            text_connection.setBackground(getResources().getDrawable(R.drawable.background_toggle_button_on));
        } else {
            text_connection.setText("unconnected");
            text_connection.setBackground(getResources().getDrawable(R.drawable.background_toggle_button_off));
        }
    }

    private void setToggleOnOff(boolean onoff) {
        if (onoff) {
            text_onoff.setText("on");
            text_onoff.setBackground(getResources().getDrawable(R.drawable.background_toggle_button_on));
            image_status.setImageDrawable(getResources().getDrawable(R.drawable.airconditioner_on));
        } else {
            text_onoff.setText("off");
            text_onoff.setBackground(getResources().getDrawable(R.drawable.background_toggle_button_off));
            image_status.setImageDrawable(getResources().getDrawable(R.drawable.airconditioner));
        }
    }

    private void initBluetooth() {  //블루투스 설정
        bluetoothSPP = new BluetoothSPP(this);
        if (!bluetoothSPP.isBluetoothAvailable()) {   //블루투스 지원 확인
            Toast.makeText(HomeActivity.this, "블루투스가 지원되지 않습니다.", Toast.LENGTH_SHORT).show();
            Log.e("BluetoothSPP", "Bluetooth not supported");

            bluetoothSPP = null;
            finish();
        }
        if (!bluetoothSPP.isBluetoothEnabled()) {  //블루투스 활성화 확인
            Toast.makeText(HomeActivity.this, "블루투스가 꺼져있습니다.", Toast.LENGTH_SHORT).show();
            Log.e("BluetoothSPP", "Bluetooth not enabled");

//            Intent bluetoothSetting = new Intent(Intent.ACTION_MAIN);
//            bluetoothSetting.setComponent(new ComponentName("com.android.settings", "com.android.settings.bluetooth.BluetoothSettings"));
//            startActivity(bluetoothSetting);
        }

        bluetoothSPP.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) {    //연결됐을 때
                Toast.makeText(HomeActivity.this, "블루투스 연결됨 : " + name + "(" + address + ")", Toast.LENGTH_SHORT).show();
                Log.i("BluetoothSPP", "Bluetooth connected with " + name + "(" + address + ")");

            }

            @Override
            public void onDeviceDisconnected() {    //연결 끊겼을 때
                Toast.makeText(HomeActivity.this, "블루투스 연결 끊김", Toast.LENGTH_SHORT).show();
                Log.e("BluetoothSPP", "Bluetooth disconnected");
            }

            @Override
            public void onDeviceConnectionFailed() {    //연결 실패했을 때
                Toast.makeText(HomeActivity.this, "블루투스 연결 실패", Toast.LENGTH_SHORT).show();
                Log.e("BluetoothSPP", "Bluetooth connection failed");
            }
        });

        bluetoothSPP.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            @Override
            public void onAutoConnectionStarted() { //자동 연결 시작했을 떄
                Toast.makeText(HomeActivity.this, "Auto Connection started", Toast.LENGTH_SHORT).show();
                Log.i("BluetoothSPP", "Auto Connection started");

            }

            @Override
            public void onNewConnection(String name, String address) {  //새로 연결됐을 때
                Toast.makeText(HomeActivity.this, "블루투스 연결됨 : " + name + "(" + address + ")", Toast.LENGTH_SHORT).show();
                Log.i("BluetoothSPP", "Bluetooth connected with " + name + "(" + address + ")");

            }
        });

        bluetoothSPP.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {   //데이터 수신됐을 때
                Toast.makeText(HomeActivity.this, "메시지 : " + message + ", 데이터 : " + new String(data), Toast.LENGTH_SHORT).show();
                Log.i("BluetoothSPP", new String(data));

                shareManager.getEg();
                String temp = new String(data);
                if (temp.equals("G0")) {
                    shareManager.egUpdate(false, sharedPreferences.getBoolean("isElect", false));
                } else if (temp.equals("G1")) {
                    shareManager.egUpdate(true, sharedPreferences.getBoolean("isElect", false));
                } else if (temp.equals("E0")) {
                    shareManager.egUpdate(sharedPreferences.getBoolean("isGas", false), false);
                } else if (temp.equals("E1")) {
                    shareManager.egUpdate(sharedPreferences.getBoolean("isGas", false), true);
                }
            }
        });
    }

    private void sendWithBluetooth(String data) {
        if (bluetoothSPP != null && bluetoothSPP.isBluetoothAvailable() && bluetoothSPP.isBluetoothEnabled()) {
            Log.e("sended", "bluetooth");
            bluetoothSPP.send(data.getBytes(), false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothSPP.stopService();
        bluetoothSPP = null;
        updateThread = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!bluetoothSPP.isBluetoothEnabled()) bluetoothSPP.enable();
        else {
            if (!bluetoothSPP.isServiceAvailable()) {
                bluetoothSPP.setupService();
                bluetoothSPP.startService(BluetoothState.DEVICE_OTHER);
                bluetoothSPP.autoConnect("?");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bluetoothSPP.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bluetoothSPP.setupService();
            } else {
                Toast.makeText(HomeActivity.this, "블루투스를 켜주세요", Toast.LENGTH_SHORT).show();

                bluetoothSPP = null;
                finish();
            }
        }
    }
}
