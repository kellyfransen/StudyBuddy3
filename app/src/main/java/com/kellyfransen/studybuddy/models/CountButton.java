/**
 * This CountButton class is the button class for the countbuttons that are dynamically created in the health screen
 * they are buttons with the extra features needed:
 * - a count variable
 * - a function to increment said count variable
 * - the appropriate background image
 */


package com.kellyfransen.studybuddy.models;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CountButton extends AppCompatButton {
    public int count = 0;
    public String name;

    public CountButton(Context context, String name) {
        super(context);

        // get background image by searching for the icon drawable
        this.setBackground(this.getResources().getDrawable(getResources()
                .getIdentifier(name + "_icon", "drawable", context.getPackageName())));

        //text aesthetics
        this.setTextSize(35);
        this.setTextColor(Color.parseColor("#404040"));

       //toast for feedback
        Toast.makeText(context, name + "button added!", Toast.LENGTH_SHORT).show();

        //set onclicklistener for incrementing the count variable
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });
    }


    //count up (call on click of button)
    public void increment() {
        count++;
        setText(count + "");
    }

}
