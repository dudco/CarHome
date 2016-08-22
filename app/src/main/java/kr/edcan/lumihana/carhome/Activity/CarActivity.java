package kr.edcan.lumihana.carhome.Activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kr.edcan.lumihana.carhome.R;
import kr.edcan.lumihana.carhome.Utils.ShareManager;
import kr.edcan.lumihana.carhome.Utils.WeatherManager;

public class CarActivity extends AppCompatActivity {
    private WeatherManager weatherManager;
    private ShareManager shareManager;
    private SharedPreferences sharedPreferences;
    private Thread carThread;

    private TextView text_out, text_in, text_air;
    private Button button_up, button_down, button_power, button_onoff;
    private static final float defaultTemp = 26.0f;
    private float defaultInside = 0.0f;
    private boolean airPower = false;
    private boolean isEnabled = false;
    private float airTemp = defaultTemp;
    private float insideTemp = 0.0f;
    private float outside = 0.0f;
    private int loopCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        sharedPreferences = getSharedPreferences("carHome", MODE_PRIVATE);
        text_out = (TextView) findViewById(R.id.car_text_out);
        text_in = (TextView) findViewById(R.id.car_text_in);
        text_air = (TextView) findViewById(R.id.car_text_air);
        button_up = (Button) findViewById(R.id.car_button_up);
        button_down = (Button) findViewById(R.id.car_button_down);
        button_power = (Button) findViewById(R.id.car_button_power);
        button_onoff = (Button) findViewById(R.id.car_button_onoff);

        weatherManager = new WeatherManager(getApplicationContext(), WeatherManager.VERSION, "daa93524-10c6-34e8-b7ef-c0c702e65362");
        weatherManager.getWeather("서울", "강남구", "도곡동");
        shareManager = new ShareManager(getApplicationContext());

        button_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnabled) {
                    isEnabled = false;
                    button_onoff.setText("OFF");
                    shareManager.setOff();
                } else {
                    new AlertDialog.Builder(CarActivity.this)
                            .setTitle("경고")
                            .setMessage("이 기능을 사용함으로써 발생하는 문제에 대해서는 사용자에게 책임이 있습니다.\n확인을 누르시면 동의하는 것으로 간주합니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    isEnabled = true;
                                    button_onoff.setText("ON");
                                    shareManager.setOn();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });

        button_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (airPower) {
                    airPower = false;
                    text_air.setText("OFF");
                } else {
                    airPower = true;
                    text_air.setText(airTemp + "");
                }
            }
        });

        button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (airPower) {
                    airTemp += 0.5f;
                    text_air.setText(airTemp + "");
                }
            }
        });

        button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (airPower) {
                    airTemp -= 0.5f;
                    text_air.setText(airTemp + "");
                }
            }
        });

        carThread = new Thread() {
            @Override
            public void run() {
                super.run();

                while (true) {
                    weatherManager.getWeather("서울", "강남구", "삼성동");
                    outside = Float.parseFloat(sharedPreferences.getString("Temperature", "0.0f"));
                    if (defaultInside == 0.0f) defaultInside = outside;
                    if (insideTemp == 0.0f) insideTemp = defaultInside;
                    float speed = 0.1f;

                    if (airPower) {
                        String temp = String.format("%.1f", airTemp);
                        shareManager.setTemperature(temp);
                    }

                    if (airPower && (insideTemp != airTemp)) {
                        if (insideTemp > airTemp) insideTemp -= speed;
                        else if (insideTemp < airTemp) insideTemp += speed;
                    } else if (!airPower && (insideTemp != outside)) {
                        if (insideTemp < outside) insideTemp += speed;
                    }

                    CarActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text_out.setText(outside + "");
                            String temp = String.format("%.1f", insideTemp);
                            text_in.setText(temp + "");
                        }
                    });

                    loopCount++;
                    Log.e("Car -> loopCount", loopCount + "");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        carThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        carThread = null;
    }
}


