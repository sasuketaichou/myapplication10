package com.example.mierul.myapplication10;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyandroidanimations.library.FlipHorizontalToAnimation;

public class CustomListViewAdapter extends ArrayAdapter<Bitmap> {
        private final Context context;
        private final Bitmap[] values;
        private  final String[] name;
        private  final String[] reminder;
        private FloatingActionButton fab;

        public CustomListViewAdapter(Context context, Bitmap[] values,String[] name,String[] reminder) {
            super(context, R.layout.rowlayout, values);
            this.context = context;
            this.values = values;
            this.name = name;
            this.reminder = reminder;
        }

        public class Holder
        {
            TextView remind;
            TextView tv;
            ImageView img;
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

            final Holder holder = new Holder();
            holder.remind = (TextView)rowView.findViewById(R.id.textView2);
            holder.tv = (TextView)rowView.findViewById(R.id.textView1);
            holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
            holder.remind.setText(reminder[position]);
            holder.remind.setVisibility(View.INVISIBLE);
            holder.tv.setText(name[position]);
            Bitmap s = values[position];
            holder.img.setImageBitmap(s);

            final int id_to_get = position+1;

            fab = (FloatingActionButton)rowView.findViewById(R.id.fab);
            //fab.setVisibility(View.INVISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Click action
                    Intent intent = new Intent(context,Display.class);
                    intent.putExtra("Value",1);
                    intent.putExtra("Id",id_to_get);
                    context.startActivity(intent);


                }
            });

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FlipHorizontalToAnimation(holder.img).setFlipToView(holder.remind)
                            .setInterpolator(new LinearInterpolator()).animate();
                    //fab.setVisibility(View.VISIBLE);
                }
            });

            return rowView;
        }
    }
