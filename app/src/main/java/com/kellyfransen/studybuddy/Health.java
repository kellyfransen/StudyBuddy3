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

/**The health screen is where the buttons are added for keeping track of (both positive and negative) consumptions
 * it also includes the step counter
 */

//implement onclicklistener for the buttons and sensoreventlistener for the step counter
public class Health extends AppCompatActivity implements View.OnClickListener, SensorEventListener {
    //create the button to add new buttons
    public static Button addButton;

    //this ArrayList stores the created buttons
    public static ArrayList<CountButton> healthButtons = new ArrayList<>();

    ConstraintLayout healthLayout;
    //Declarations for step counter
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

        //open the context menu on click
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton.performLongClick();
            }
        });

        //values needed to move the addbutton around when new buttons are created
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;

        stepCountTV = (TextView) findViewById(R.id.stepCountTV2);
//create sensormanager for the step counter
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

//register health layout for contextmenu: this means that buttons can be created by long-pressing on the screen
// as well as using the add button
        registerForContextMenu(healthLayout);

        //When the health screen is closed, all dynamically created buttons are killed
        //so they have to be added to the view every time the health screen is opened
        for (CountButton b : healthButtons) {
            healthLayout.addView(b);
            moveAddButton();
        }
    }


    //create the selection menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //get the list items from the menu xml file
        getMenuInflater().inflate(R.menu.healthbuttons_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //get which button is pressed and store it in name
        String name = getResources().getResourceEntryName(item.getItemId());
        //create countbutton
        final CountButton countButton = new CountButton(this, name);
//create it in the current location of the add button
        countButton.setX(addButton.getX());
        countButton.setY(addButton.getY());

        //add the name to activeButtons ArrayList so they now show up when creating a widget button
        CountWidgetConfigureActivity.activeButtons.add(name);

        //add button to view and add it to the healthbuttons ArrayList
        healthLayout.addView(countButton);
        healthButtons.add(countButton);
        //move the add button so it doesn't overlay with the new button
        moveAddButton();


        return super.onContextItemSelected(item);
    }
//move addbutton by incrementing its x and y values
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

    //The buttons have to be destroyed when the health screen is closed to avoid errors
    //and to prevent mistakes in the buttons arraylist
    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (CountButton b : healthButtons) {
            healthLayout.removeView(b);
        }
    }
}
