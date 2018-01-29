package com.example.ashutosh.viewholders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.ashutosh.mpiricmodule1.ALL_VIDEOS;
import com.example.ashutosh.mpiricmodule1.Account;
import com.example.ashutosh.mpiricmodule1.R;
import com.example.ashutosh.mpiricmodule1.Versatile_logo;

/**
 * Created by Admin on 12/20/2016.
 */

public class DrawerChildViewHolder extends ChildViewHolder {

    public TextView mCrimeDateText;
    public String selectedText;
    public  Class<?> c=null;
    int cateid;
    public DrawerChildViewHolder(View itemView) {
        super(itemView);

        mCrimeDateText = (TextView) itemView.findViewById(R.id.child_list_item_crime_date_text_view);

//        if(selectedText.equals("Versatile"))
        mCrimeDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedText=(String)mCrimeDateText.getText();
                Toast.makeText(v.getContext(),selectedText,Toast.LENGTH_SHORT).show();
                if(selectedText.equals("Versatile")) {
                    cateid=1;
                    Intent i = new Intent(v.getContext(), Versatile_logo.class);
                    i.putExtra("Parameter",selectedText);
                    i.putExtra("Cateid",cateid);
                    v.getContext().startActivity(i);
                }
                else if(selectedText.equals("Professional")) {
                    Intent i = new Intent(v.getContext(), Versatile_logo.class);
                    i.putExtra("Parameter",selectedText);
                    cateid=4;
                    i.putExtra("Cateid",cateid);
                    v.getContext().startActivity(i);
                }
                else if(selectedText.equals("Cartoon")) {
                    Intent i = new Intent(v.getContext(), Versatile_logo.class);
                    cateid=5;
                    i.putExtra("Parameter",selectedText);
                    i.putExtra("Cateid",cateid);
                    v.getContext().startActivity(i);
                }
                else if(selectedText.equals("Explainer Videos")) {
                    Intent i = new Intent(v.getContext(), ALL_VIDEOS.class);
                    cateid = 2;
                    i.putExtra("Parameter", selectedText);
                    i.putExtra("Cateid", cateid);
                    v.getContext().startActivity(i);
                }
                else if(selectedText.equals("Developer Videos")) {
                    Intent i = new Intent(v.getContext(), ALL_VIDEOS.class);
                    cateid=3;
                    i.putExtra("Parameter",selectedText);
                    i.putExtra("Cateid",cateid);
                    v.getContext().startActivity(i);
                }
                else if(selectedText.equals("Whiteboard Videos")) {
                    Intent i = new Intent(v.getContext(), ALL_VIDEOS.class);
                    cateid=6;
                    i.putExtra("Parameter",selectedText);
                    i.putExtra("Cateid",cateid);
                    v.getContext().startActivity(i);
                }

                else
                {
                    if(selectedText!=null) {
                        try {
                            String packagename="com.example.ashutosh.mpiricmodule1."+selectedText;
                            c = Class.forName(packagename);
                            Activity obj=(Activity)c.newInstance();
                            Intent i = new Intent(v.getContext(), c);
                            v.getContext().startActivity(i);
                        }catch(Exception e){

                        }
                    }
                }
            }
        });

    }
}