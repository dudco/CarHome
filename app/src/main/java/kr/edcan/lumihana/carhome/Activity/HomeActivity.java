package kr.edcan.lumihana.carhome.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import kr.edcan.lumihana.carhome.R;
import kr.edcan.lumihana.carhome.Utils.ShareManager;

public class HomeActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ShareManager shareManager;
    private float defaultTemp = 18.0f;
    private final float speed = 0.1f;
    private BluetoothSPP bluetoothSPP;
    private Thread updateThread;
    private int loopCount = 0;
    private float currentTemp = 0.0f;
    private boolean currentWorking = true;
    private SharedPreferences sharedPreferences;
    private int time = 60;
    private TextView text_air, text_connection, text_onoff, text_time;
    private ImageView image_status;
    private RelativeLayout relative_temp, relative_visit, relative_security;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        text_air = (TextView) findViewById(R.id.home_text_air);
        text_connection = (TextView) findViewById(R.id.home_text_connection);
        text_onoff = (TextView) findViewById(R.id.home_text_onoff);
        text_time = (TextView) findViewById(R.id.home_text_time);
        image_status = (ImageView) findViewById(R.id.home_image_status);
        relative_temp = (RelativeLayout) findViewById(R.id.home_relative_temp);
        relative_visit = (RelativeLayout) findViewById(R.id.home_relative_visit);
        relative_security = (RelativeLayout) findViewById(R.id.home_relative_security);

        shareManager = new ShareManager(getApplicationContext());
        sharedPreferences = getSharedPreferences("carHome", MODE_PRIVATE);

        setToggleConnection(true);
        initBluetooth();

        relative_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });

        relative_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Error")
                        .setMessage("Permission Denied")
                        .show();
            }
        });

        relative_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Error")
                        .setMessage("Permission Denied")
                        .show();
            }
        });

        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    shareManager.setShareGet();
                    shareManager.getTemperature();
                    final float receivedTemp = Float.parseFloat(sharedPreferences.getString("CarTemperature", "0.0f"));
                    final boolean isWorking = sharedPreferences.getBoolean("Working", false);
                    HomeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text_time.setText("집까지 남은 시간 : " + time);
                        }
                    });

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
            Toast.makeText(getApplicationContext(), "블루투스가 지원되지 않습니다.", Toast.LENGTH_SHORT).show();
            Log.e("BluetoothSPP", "Bluetooth not supported");

            bluetoothSPP = null;
            finish();
        }
        if (!bluetoothSPP.isBluetoothEnabled()) {  //블루투스 활성화 확인
            Toast.makeText(getApplicationContext(), "블루투스가 꺼져있습니다.", Toast.LENGTH_SHORT).show();
            Log.e("BluetoothSPP", "Bluetooth not enabled");

//            Intent bluetoothSetting = new Intent(Intent.ACTION_MAIN);
//            bluetoothSetting.setComponent(new ComponentName("com.android.settings", "com.android.settings.bluetooth.BluetoothSettings"));
//            startActivity(bluetoothSetting);
        }

        bluetoothSPP.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) {    //연결됐을 때
                Toast.makeText(getApplicationContext(), "블루투스 연결됨 : " + name + "(" + address + ")", Toast.LENGTH_SHORT).show();
                Log.i("BluetoothSPP", "Bluetooth connected with " + name + "(" + address + ")");

            }

            @Override
            public void onDeviceDisconnected() {    //연결 끊겼을 때
                Toast.makeText(getApplicationContext(), "블루투스 연결 끊김", Toast.LENGTH_SHORT).show();
                Log.e("BluetoothSPP", "Bluetooth disconnected");
            }

            @Override
            public void onDeviceConnectionFailed() {    //연결 실패했을 때
                Toast.makeText(getApplicationContext(), "블루투스 연결 실패", Toast.LENGTH_SHORT).show();
                Log.e("BluetoothSPP", "Bluetooth connection failed");
            }
        });

        bluetoothSPP.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            @Override
            public void onAutoConnectionStarted() { //자동 연결 시작했을 떄
                progressDialog = new ProgressDialog(HomeActivity.this);
                progressDialog.setTitle("자동 연결중...");
                progressDialog.setMessage("블루투스에 자동으로 연결합니다.");
                progressDialog.show();
                Toast.makeText(getApplicationContext(), "Auto Connection started", Toast.LENGTH_SHORT).show();
                Log.i("BluetoothSPP", "Auto Connection started");

            }

            @Override
            public void onNewConnection(String name, String address) {  //새로 연결됐을 때
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "블루투스 연결됨 : " + name + "(" + address + ")", Toast.LENGTH_SHORT).show();
                Log.i("BluetoothSPP", "Bluetooth connected with " + name + "(" + address + ")");

            }
        });

        bluetoothSPP.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {   //데이터 수신됐을 때
                Toast.makeText(getApplicationContext(), "메시지 : " + message + ", 데이터 : " + new String(data), Toast.LENGTH_SHORT).show();
                Log.i("BluetoothSPP", "Bluetooth data recived");
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
                Toast.makeText(getApplicationContext(), "블루투스를 켜주세요", Toast.LENGTH_SHORT).show();

                bluetoothSPP = null;
                finish();
            }
        }
    }
}
