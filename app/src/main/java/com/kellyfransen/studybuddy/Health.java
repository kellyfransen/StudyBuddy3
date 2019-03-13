package com.kellyfransen.studybuddy;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Health extends AppCompatActivity implements SensorEventListener {
    //create buttons to count cups of coffee, water, etc
    public int[] healthCount = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        Button coffeeButton = (Button) findViewById(R.id.countButton);
        coffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthCount[0]++;
                TextView countTV0 = (TextView) findViewById(R.id.countTV);
                countTV0.setText(healthCount[0] + "");
            }
        });

        Button waterButton = (Button) findViewById(R.id.countButton2);
        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthCount[1]++;
                TextView countTV2 = (TextView) findViewById(R.id.countTV2);
                countTV2.setText(healthCount[1] + "");
            }
        });
    }

    SensorManager sensorManager;
    TextView stepCountTV = (textView) findViewByID(R.id.stepCountTV);
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

}
