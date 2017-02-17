package com.example.mierul.myapplication10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by amierul.japri on 4/20/2016.
 */
public class Display_3_template extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getExtras();
        int value = extra.getInt("Value");

        if (value == 1){
            setContentView(R.layout.display_note_template);
        }

        else if (value == 2){
            setContentView(R.layout.display_reminder_template);
        }
        else
            setContentView(R.layout.display_gps_template);
    }
}
