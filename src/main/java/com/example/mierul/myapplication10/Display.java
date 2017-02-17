package com.example.mierul.myapplication10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.io.File;

public class Display extends Activity {
    Context context;
    DBhelper db;
    EditText note;
    String filename;
    Button button,buttonBack;
    ImageView disImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_main);

        db = new DBhelper(this);
        context = this;

        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.lvExp);
        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(this);
        expListView.setAdapter(expandableListAdapter);

        buttonBack = (Button)findViewById(R.id.buttonBack);
        button = (Button)findViewById(R.id.button);
        disImage = (ImageView) findViewById(R.id.disImageView);
//        note = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        int value = intent.getExtras().getInt("Value");

        if(value ==1) {
            doDisplay(intent);
        }

        else if (value <1){
            doRegister(intent);
        }

    }

    private void doRegister(Intent intent) {

        buttonBack.setVisibility(View.INVISIBLE);

        filename = intent.getExtras().getString("FileName");
        File path = new File(getFilesDir(), "path/image");
        File file = new File(path, filename);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        disImage.setImageBitmap(bitmap);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String reminder = note.getText().toString();
//                db.insertReminder(reminder, filename); //insert reminder
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void doDisplay(Intent intent) {

        buttonBack.setVisibility(View.VISIBLE);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        });
        button.setVisibility(View.INVISIBLE);

        int id = intent.getExtras().getInt("Id");
        Cursor rs = db.getData(id);
        rs.moveToFirst();

        filename = rs.getString(rs.getColumnIndex("NAME"));
//        String reminder = rs.getString(rs.getColumnIndex("REMINDER"));

        if (!rs.isClosed())
        {
            rs.close();
        }

        File path = new File(getFilesDir(), "path/image");
        File file = new File(path, filename);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        disImage.setImageBitmap(bitmap);

//        TextView editText = (TextView)findViewById(R.id.editText);
//        editText.setText(reminder);
//        editText.setFocusable(false);
//        editText.setClickable(false);


    }

}
