package com.example.ashutosh.viewholders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.ashutosh.mpiricmodule1.Account;
import com.example.ashutosh.mpiricmodule1.LoginActivity;
import com.example.ashutosh.mpiricmodule1.Orders;
import com.example.ashutosh.mpiricmodule1.R;
import com.example.ashutosh.mpiricmodule1.Versatile_logo;

import com.example.ashutosh.mpiricmodule1.ru.ifsoft.qachat.AppActivity;
import com.example.ashutosh.mpiricmodule1.ru.ifsoft.qachat.ChatActivity;
import com.example.ashutosh.mpiricmodule1.ru.ifsoft.qachat.MainActivity;
import com.example.ashutosh.mpiricmodule1.ru.ifsoft.qachat.app.App;

/**
 * Created by Admin on 12/20/2016.
 */

public class DrawerParentViewHolder extends ParentViewHolder {

    public TextView mCrimeTitleTextView;
    public ImageButton mParentDropDownArrow;
    public String selectedText;
    SharedPreferences sh1;

    public DrawerParentViewHolder(View itemView) {
        super(itemView);

        mCrimeTitleTextView = (TextView) itemView.findViewById(R.id.parent_list_item_crime_title_text_view);
        mParentDropDownArrow = (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);


        mCrimeTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedText=(String) mCrimeTitleTextView.getText();
                if(selectedText.equals("My Account"))
                {
                    Intent i = new Intent(v.getContext(), Account.class);
                    v.getContext().startActivity(i);
                }
                else if(selectedText.equals("My Orders"))
                {
                    Intent i = new Intent(v.getContext(), Orders.class);
                    v.getContext().startActivity(i);
                }
                else if(selectedText.equals("Chat With Us"))
                {
                    Intent i = new Intent(v.getContext(), AppActivity.class);
                    v.getContext().startActivity(i);
                }

                else if(selectedText.equals("Logout"))
                {

                    sh1=v.getContext().getSharedPreferences("LoginAuth", Context.MODE_PRIVATE);
                    Boolean log=sh1.getBoolean("LoginAuth",true);
                    if(log){
                        SharedPreferences.Editor editor=sh1.edit();
                        editor.putBoolean("LoginAuth",false);
                        editor.commit();

                       // Toast.makeText(v.getContext(),"Logged Out"+log,Toast.LENGTH_LONG).show();
                        Intent i=new Intent(v.getContext(), LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(i);
                        ((Activity)v.getContext()).finish();

                    }

                }
            }
        });

    }
}