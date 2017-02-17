package com.example.mierul.myapplication10;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "com.example.mierul.myapplication10.fileprovider";
    final static int SELECT_FILE = 1;
    final static int REQUEST_CAMERA = 2;
    private ListView lv;
    private String fileName;
    private DBhelper db;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setLogo(R.drawable.icon_title);


        db = new DBhelper(this);
        lv = (ListView) this.findViewById(R.id.listView);
        initLay(savedInstanceState);
        refresh();

    }

    private void initLay(Bundle savedInstanceState) {
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.three_buttons_activity);

        BottomBar bottomBar = BottomBar.attach(this,savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.three_menu_bar, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {

                switch (itemId) {
                    case R.id._small_list_view:
                        Snackbar.make(coordinatorLayout, "Recent Item Selected", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id._galery:
                        //Snackbar.make(coordinatorLayout, "Favorite Item Selected", Snackbar.LENGTH_LONG).show();
                    {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(
                                Intent.createChooser(intent, "Select File"),
                                SELECT_FILE);
                    }
                        break;
                    case R.id._camera: {
                        //Snackbar.make(coordinatorLayout, "Location Item Selected", Snackbar.LENGTH_LONG).show();
                        //padam 3 baris ni je tok gune thumbnail
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(intent, REQUEST_CAMERA);
//                    File path = new File(getFilesDir(), "path/image");
//                    if (path.exists()) {
//                        Log.e("Path", "exist");
//                    }
//                    path.mkdirs();
//                    fileName = System.currentTimeMillis() + ".jpg";
//                    Log.e("SelectImage", fileName);
//                    File image = new File(path, fileName);
//                    Log.e("Image", image.getAbsolutePath());
//                    Uri imageUri = FileProvider.getUriForFile(context, CAPTURE_IMAGE_FILE_PROVIDER, image);
//                    if (imageUri == null) {
//                        Log.e("Uri", "Uri is null");
//                    }
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                    startActivityForResult(intent, REQUEST_CAMERA);
                    }
                        break;
                }
            }
        });
        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        bottomBar.setActiveTabColor("#FFFFFF");

        // Use the dark theme. Ignored on mobile when there are more than three tabs.
        bottomBar.useDarkTheme(true);

        // Use custom text appearance in tab titles.
        //bottomBar.setTextAppearance(R.style.MyTextAppearance);

        // Use custom typeface that's located at the "/src/main/assets" directory. If using with
        // custom text appearance, set the text appearance first.
        //bottomBar.setTypeFace("MyFont.ttf");
    }

    public void refresh() {
        final ArrayList<String> array_list = db.getAllName();

        int i = 0;
        Cursor c = db.getData();
        Bitmap[] array = new Bitmap[c.getCount()];

        while (i < c.getCount()) {
            String nameFile = array_list.get(i);
            //Log.e("ErrorNameFile",nameFile);
            File path = new File(getFilesDir(), "path/image");
            File gambar = new File(path, nameFile);
            if (gambar.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(gambar.getAbsolutePath());
                array[i] = bitmap;
            }
            i++;
        }
        ArrayList<String> array_list_2 = db.getAllReminder();
        String[] reminder = new String[array_list_2.size()];
        String[] name = new String[array_list.size()];

        CustomListViewAdapter adapter3 = new CustomListViewAdapter(this, array, array_list.toArray(name),array_list_2.toArray(reminder));
        lv.setAdapter(adapter3);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent(getApplicationContext(), Display.class);
//                intent.putExtra("Value", 1);
//                intent.putExtra("Id", position + 1);
//                startActivity(intent);
//
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_SEARCH:
                Snackbar.make(lv, "Search", Snackbar.LENGTH_LONG).show();
                break;

            case R.id.action_Refresh:
                refresh();
                break;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
              Log.e("Resultcode", "result code ok");
            if (requestCode == REQUEST_CAMERA) {

                /** uncomment g ni kalau nk save gambar*/


//                //Get our saved file into a bitmap object:
//                Log.e("Saje", "Request code is ok, lepas if statement");
//
//                File path = new File(getFilesDir(), "path/image");
//                if (!path.exists()) {
//
//                    path.mkdirs();
//                    Log.e("Path", "Path is not exist");
//                }
//                fileName = System.currentTimeMillis()+".jpg"; //delete yg ni kalau nk gune full image, declare mase klik
//
//                File file = new File(path, fileName);
//                Log.e("ONactivity", file.toString());
//
////padam 1 baris ni je tok gune full image
//                Bitmap bm = (Bitmap)data.getExtras().get("data");
////                Bitmap bm = processBitmap(file.getAbsolutePath());
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//
//            FileOutputStream fo;
//            try {
//                file.createNewFile();
//                fo = new FileOutputStream(file);
//                fo.write(bytes.toByteArray());
//                fo.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            //db.insert(fileName);
            Intent intent = new Intent(this, Display_Note.class);
            //intent.putExtra("FileName", fileName);
            //intent.putExtra("Value", 0);
            startActivity(intent);
            //refresh();
            }

         else if (requestCode == SELECT_FILE) {

            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                    null);
            Cursor cursor = cursorLoader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();

            String selectedImagePath = cursor.getString(column_index);

            Bitmap bm = processBitmap(selectedImagePath);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            fileName = System.currentTimeMillis() + ".jpg";

            File path = new File(getFilesDir(), "path/image");
                if (!path.exists()) {

                    path.mkdirs();
                    Log.e("Path", "Path is not exist");
                }
            File file = new File(path, fileName);

            FileOutputStream fo;
            try {
                file.createNewFile();
                fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e("FileInputStream","file takde");
            } catch (IOException e) {
                Log.e("IOException","IO Exception");
                e.printStackTrace();
            }

            db.insert(fileName);
            Intent intent = new Intent(this, Display.class);
            intent.putExtra("FileName", fileName);
            intent.putExtra("Value", 0);
            startActivity(intent);
            refresh();


        }
    }
}


    public Bitmap processBitmap(String file){

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(file, options);

        return bm;

    }
}
