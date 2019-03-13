package com.kellyfransen.studybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button_planning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_planning = findViewById(R.id.button_planning);
        button_planning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlanning();

            }
        });
    }

    public void openPlanning() {
        Intent intent = new Intent(this, Planning.class);
        startActivity(intent);
    }
}
