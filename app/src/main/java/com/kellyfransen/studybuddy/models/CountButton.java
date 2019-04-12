package com.kellyfransen.studybuddy.models;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CountButton extends AppCompatButton {
    private int count = 0;
    public String name;

    public CountButton(Context context, String name) {
        super(context);
        this.setBackground(this.getResources().getDrawable(getResources()
                .getIdentifier(name + "_icon", "drawable", context.getPackageName())));
        this.setTextSize(35);
        this.setTextColor(Color.parseColor("#404040"));
        Toast.makeText(context, name + "button added!", Toast.LENGTH_SHORT).show();
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                setText(count + "");
            }
        });
    }


    //count (call on click of button)
    public void increment() {


    }

}
