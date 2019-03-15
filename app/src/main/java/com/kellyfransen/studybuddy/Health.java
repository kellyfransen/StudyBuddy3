package com.kellyfransen.studybuddy;

import android.content.Context;
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

public class Health extends AppCompatActivity {
    public int numButtons = 0;

    ConstraintLayout healthLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        healthLayout = findViewById(R.id.Health);

        TextView addButtonTV = findViewById(R.id.addButtonTextView);
        registerForContextMenu(addButtonTV);

/*
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
*/
    }

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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(400, 200);
        layoutParams.setMargins(20, 20, 0, 0); // left, top, right, bottom
        coffeeButton.setLayoutParams(layoutParams);
        healthLayout.addView(coffeeButton);






        /*
        private String name = item.getItemId();
        private String buttonName = name + "Button";

        Button = new Button(this);
        coffeeButton.setText("coffee");
*/
        return super.onContextItemSelected(item);
    }


}
//    SensorManager sensorManager;
//    TextView stepCountTV = (textView) findViewByID(R.id.stepCountTV);
//    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


