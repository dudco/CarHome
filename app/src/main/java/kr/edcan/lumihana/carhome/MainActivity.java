package kr.edcan.lumihana.carhome;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

public class MainActivity extends AppCompatActivity {
    BluetoothSPP bluetoothSPP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothSPP = new BluetoothSPP(this);
        if (!bluetoothSPP.isBluetoothAvailable()) {   //블루투스 지원 확인
            Toast.makeText(getApplicationContext(), "블루투스가 지원되지 않습니다.", Toast.LENGTH_SHORT).show();
            Log.e("BluetoothSPP", "Bluetooth not supported");

            bluetoothSPP = null;
            finish();
        }
        if (bluetoothSPP.isBluetoothEnabled()) {  //블루투스 활성화 확인
            Toast.makeText(getApplicationContext(), "블루투스가 꺼져있습니다.", Toast.LENGTH_SHORT).show();
            Log.e("BluetoothSPP", "Bluetooth not enabled");

            Intent bluetoothSetting = new Intent(Intent.ACTION_MAIN);
            bluetoothSetting.setComponent(new ComponentName("com.android.settings", "com.android.settings.bluetooth.BluetoothSettings"));
            startActivity(bluetoothSetting);
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
                Toast.makeText(getApplicationContext(), "Auto Connection started", Toast.LENGTH_SHORT).show();
                Log.i("BluetoothSPP", "Auto Connection started");

            }

            @Override
            public void onNewConnection(String name, String address) {  //새로 연결됐을 때
                Toast.makeText(getApplicationContext(), "블루투스 연결됨 : " + name + "(" + address + ")", Toast.LENGTH_SHORT).show();
                Log.i("BluetoothSPP", "Bluetooth connected with " + name + "(" + address + ")");

            }
        });

        bluetoothSPP.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {   //데이터 수신됐을 때
                Toast.makeText(getApplicationContext(), "메시지 : " + message + ", 데이터 : " + data.toString(), Toast.LENGTH_SHORT).show();
                Log.i("BluetoothSPP", "Bluetooth data recived");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothSPP.stopService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!bluetoothSPP.isBluetoothEnabled()) bluetoothSPP.enable();
        else {
            if (!bluetoothSPP.isServiceAvailable()) {
                bluetoothSPP.setupService();
                bluetoothSPP.startService(BluetoothState.DEVICE_OTHER);
                bluetoothSPP.autoConnect("HC-06");
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


