package kr.edcan.lumihana.carhome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import kr.edcan.lumihana.carhome.R;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout relative_cars, relative_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        relative_cars = (RelativeLayout) findViewById(R.id.select_relative_cars);
        relative_home = (RelativeLayout) findViewById(R.id.select_relative_home);
        relative_cars.setOnClickListener(this);
        relative_home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_relative_cars : startActivity(new Intent(getApplicationContext(), CarActivity.class)); break;
            case R.id.select_relative_home : startActivity(new Intent(getApplicationContext(), HomeActivity.class)); break;
        }
        finish();
    }
}
