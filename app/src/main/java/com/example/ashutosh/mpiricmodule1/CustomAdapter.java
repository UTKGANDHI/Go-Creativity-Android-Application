package com.example.ashutosh.mpiricmodule1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Gandhi on 27-Dec-16.
 */

public class CustomAdapter extends ArrayAdapter
{
    Model[] modelItems = null;
    Context context;
     CheckBox cb;
    SharedPreferences.Editor editor;
    View convertView;
    ViewGroup parent;
    Hashtable<String,String> CustomHash;
    RecyclerView.ViewHolder holder;
    ArrayList<Model> CheckList;
    public CustomAdapter(Context context, Model[] resource) {
        super(context,R.layout.simplerow2,resource);
        this.context = context;
        this.modelItems = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String test;
        SharedPreferences sharedPrefs = context.getSharedPreferences("sharedPrefs12", Context.MODE_PRIVATE);

         editor = sharedPrefs.edit();
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.simplerow2, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textView1);
        cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        name.setText(modelItems[position].getName());
        SaveChecked(modelItems);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb.isChecked()){
                      CustomHash.put("Key",modelItems.toString());
               //     Toast.makeText(getContext(),"CustomHash"+CustomHash,Toast.LENGTH_LONG).show();
                    }
            }
        });

      //  test=modelItems[position].getName();
        //if(modelItems[position].getValue() == 1)
          //  cb.setChecked(true);
        //else
          //  cb.setChecked(false);
        return convertView;

    }
    public ArrayList SaveChecked(Model[] modelItems)
    {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.simplerow2, parent, false);
        cb = (CheckBox) convertView.findViewById(R.id.checkBox1);

        for(int i=0;i<modelItems.length;i++)
        {
           if(cb.isChecked()){
               CheckList.add(modelItems[i]);
               cb.setChecked(true);}

        }
        return CheckList;
    }

}
