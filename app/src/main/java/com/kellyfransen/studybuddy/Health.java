package com.kellyfransen.studybuddy;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kellyfransen.studybuddy.models.CountButton;

import java.util.ArrayList;
import java.util.List;

public class Health extends AppCompatActivity implements View.OnClickListener, SensorEventListener {
    public static Button addButton;
    public static ArrayList<CountButton> healthButtons = new ArrayList<>();


    //Declarations for step counter

    ConstraintLayout healthLayout;
    boolean running = false;
    SensorManager sensorManager;
    TextView stepCountTV;
    public int screenWidth;

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

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;

        stepCountTV = (TextView) findViewById(R.id.stepCountTV);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        registerForContextMenu(healthLayout);
        System.out.println(("screenWidth = " + screenWidth));
        for (CountButton b : healthButtons) {
            healthLayout.addView(b);
            moveAddButton();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.healthbuttons_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String name = getResources().getResourceEntryName(item.getItemId());
        final CountButton countButton = new CountButton(this, name);

        countButton.setX(addButton.getX());
        countButton.setY(addButton.getY());
        CountWidgetConfigureActivity.activeButtons.add(name);

        healthLayout.addView(countButton);
        healthButtons.add(countButton);

        moveAddButton();


        return super.onContextItemSelected(item);
    }

    private void moveAddButton() {
        addButton.setX(addButton.getX() +315);
        if (addButton.getX() > screenWidth - 315) {
            addButton.setY(addButton.getY() + 310);
            addButton.setX(addButton.getX()-(315)*4);

        }
        System.out.print("addButton.getX() = " + addButton.getX());
        System.out.println("addButton.getY() = " + addButton.getY());

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
            stepCountTV.setText(String.valueOf(event.values[0]));
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (CountButton b : healthButtons) {
            healthLayout.removeView(b);
        }
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
