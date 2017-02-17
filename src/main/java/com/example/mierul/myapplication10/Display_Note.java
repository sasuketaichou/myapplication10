package com.example.mierul.myapplication10;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class Display_Note extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_note);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setLogo(R.drawable.icon_title);

        Bundle bundle = getIntent().getExtras();
        ImageView imageView = (ImageView)findViewById(R.id.imageView);

        assert imageView != null;
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                showAlertDialog();
                return false;
            }
        });

    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] items = {"Note", "Reminder", "Location"};
        builder.setTitle("Choose..");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Note")) {
                    //startActivity(new Intent(getBaseContext(),NoteLayout.class));
                    dialog.cancel();

                } else if (items[item].equals("Reminder")) {
                    startActivity(new Intent(getBaseContext(),Reminder.class));

                } else if (items[item].equals("Location")) {
                    startActivity(new Intent(getBaseContext(),LocationLayout.class));

                }
            }
        });
        builder.show();
    }

}
