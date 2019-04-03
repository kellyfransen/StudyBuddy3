package com.kellyfransen.studybuddy;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kellyfransen.studybuddy.models.CountButton;

import java.util.ArrayList;
import java.util.List;

public class Health extends AppCompatActivity implements View.OnClickListener, SensorEventListener {
      Button addButton;
    //Declarations for step counter

    ConstraintLayout healthLayout;
    boolean running = false;
    SensorManager sensorManager;
    TextView stepCountTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        healthLayout = findViewById(R.id.Health);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton.performLongClick();
            }
        });


        stepCountTV = (TextView) findViewById(R.id.stepCountTV);

        sensorManager = (SensorManager)

                getSystemService(Context.SENSOR_SERVICE);

        registerForContextMenu(addButton);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.healthbuttons_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        String name = getResources().getResourceEntryName(item.getItemId());
        Button button = new Button(this);
        button.setHeight(30);
        button.setWidth(30);
        final CountButton countButton = new CountButton(button);
        button.setX(addButton.getX());
        button.setY(addButton.getY());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countButton.increment();
            }
        });
        button.setBackground(this.getResources().getDrawable(getResources()
                .getIdentifier(name + "_icon", "drawable", getPackageName())));
        healthLayout.addView(button);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        //move addButton
        addButton.setX(addButton.getX() + addButton.getWidth() + 100);
        if (addButton.getX() > healthLayout.getWidth() - 200) {
            addButton.setY(addButton.getY() + 60);
            addButton.setX(24);
        }


        return super.onContextItemSelected(item);
    }



    //STEP COUNTER
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

    @Override
    public void onClick(View v) {

    }
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






                Button coffeeButton = (Button) findViewById(R.id.CountButton);
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










                /*
                private String name = item.getItemId();
                private String buttonName = name + "Button";

                Button = new Button(this);
                coffeeButton.setText("coffee");

            }
        */

