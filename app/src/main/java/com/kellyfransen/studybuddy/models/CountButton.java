package com.kellyfransen.studybuddy.models;

import android.graphics.Color;
import android.widget.Button;

public class CountButton {
    private int count = 0;
    private Button button;
    public String name;

    public CountButton(Button button){
        this.button = button;
    }

//count (call on click of button)
    public void increment(){
        this.count++;
        this.button.setText(count + "");
        button.setTextSize(35);
        button.setTextColor(Color.parseColor("#404040"));
    }

}
