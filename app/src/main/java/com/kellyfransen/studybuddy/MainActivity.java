package com.kellyfransen.studybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //create buttons to direct to the planning and health page
    private Button button_planning;
    private Button button_health;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Open planning activity when pressing planning button
        button_planning = findViewById(R.id.button_planning);
        button_planning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(Planning.class);

            }
        });
        //Open health activity when pressing health button
        button_health = findViewById(R.id.button_health);
        button_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(Health.class);
            }
        });
    }

    //open new activity
    public void openPage(Class page) {
        Intent intent = new Intent(this, page);
        startActivity(intent);
    }
}


