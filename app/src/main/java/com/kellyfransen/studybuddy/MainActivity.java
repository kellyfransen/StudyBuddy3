package com.kellyfransen.studybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button_planning;
    private Button button_health;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_planning = findViewById(R.id.button_planning);
        button_planning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(Planning.class);

            }
        });
        button_health = findViewById(R.id.button_health);
        button_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(Health.class);
            }
        });
    }

    public void openPage(Class page) {
        Intent intent = new Intent(this, page);
        startActivity(intent);
    }
}


