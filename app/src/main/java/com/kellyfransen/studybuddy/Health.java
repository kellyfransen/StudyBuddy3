package com.kellyfransen.studybuddy;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;

public class Health extends AppCompatActivity implements SensorEventListener {
    public int numButtons = 3;
    public int[] healthCount = new int[numButtons];
    public Button[] healthButtons = new Button[numButtons];
    public String[] healtButtonNames = new String[numButtons];


    ConstraintLayout healthLayout;
    boolean running = false;
    SensorManager sensorManager;
    TextView stepCountTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        healthLayout = findViewById(R.id.Health);

        stepCountTV = (TextView) findViewById(R.id.stepCountTV);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        healthButtons[0] = (Button) findViewById(R.id.countButton);
        healthButtons[1] = (Button) findViewById(R.id.countButton2);
        healthButtons[2] = (Button) findViewById(R.id.countButton3);


        for (int i = 0; i < numButtons; i++) {
            healthButtons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    healthCount[i]++;
                    healthButtons[i].setText(healthCount[i] + "");
                }
            });
        }
/*
        final Button waterButton = (Button) findViewById(R.id.countButton2);
        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthCount[1]++;
                waterButton.setText(healthCount[1] + "");
            }
        });

        final Button pizzaButton = (Button) findViewById(R.id.countButton3);
        pizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthCount[2]++;
                pizzaButton.setText(healthCount[2] + "");
            }
        });
        */
    }

    /*
            TextView addButtonTV = findViewById(R.id.addButtonTextView);
            registerForContextMenu(addButtonTV);

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

    //button menu
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            getMenuInflater().inflate(R.menu.healthbuttons_menu, menu);
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            //private String name = item.getItemId();
            Button coffeeButton = new Button(this);
            coffeeButton.setText("Coffee");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
            layoutParams.setMargins(20 + 30 * buttonCount, 20, 0, 0); // left, top, right, bottom
            coffeeButton.setLayoutParams(layoutParams);
            healthLayout.addView(coffeeButton);
            buttonCount++;






            /*
            private String name = item.getItemId();
            private String buttonName = name + "Button";

            Button = new Button(this);
            coffeeButton.setText("coffee");

            return super.onContextItemSelected(item);
        }
    */
    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            stepCountTV.setText("you have taken " + String.valueOf(event.values[0]) + " steps today!");
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
