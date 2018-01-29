package com.example.ashutosh.mpiricmodule1;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Models.Util1;

/**
 * Created by Ashutosh on 23-11-2016.
 */


public class MyService extends Service implements  AsyncRequest1.OnAsyncRequestComplete,AsyncRequest2.OnAsyncRequestComplete {

    private static final String apiURL= Util1.GET_ORDERS_API;
    private static final String apiURL1= Util1.GET_ALLCATEGORY_EPIC;
    int count=0;
    String status,name;
    String userid;
    ArrayList<String> buylist=new ArrayList<String>();
    ArrayList<String> namelist=new ArrayList<String>();
    int i=0;
    int j;
    AsyncRequest1 getPosts;
    AsyncRequest2 getPosts2;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        SharedPreferences sh1=getSharedPreferences("LoginAuth", Context.MODE_PRIVATE);
        userid=sh1.getString("USERID",null);
        // Toast.makeText(getApplicationContext(),"In OnCreate",Toast.LENGTH_SHORT).show();

        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

// This schedule a runnable task every 5 seconds
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Intent i=new Intent(getApplicationContext(),MyService.class);
                getApplicationContext().startService(i);
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public  boolean wifiState()
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean iswifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();
        return iswifi;
    }
    public  boolean NetworkState()
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        return is3g;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        //   super.onDestroy();
        Intent intent = new Intent(getBaseContext(), MyService.class);
        startService(intent);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        getPosts = new AsyncRequest1(this, "GET");
        if (wifiState() || NetworkState()) {

            getPosts.execute(apiURL);
        }

        else{
            Toast.makeText(getApplicationContext(),"Please enable network",Toast.LENGTH_LONG).show();
            getPosts.cancel(true);
        }

        return START_STICKY;
    }


    @Override
    public void asyncResponse(String response)
    {
        try {
            if(count==0) {
                if(wifiState()||NetworkState()){
                j = 0;
                if (wifiState() || NetworkState()) {
                    JSONArray objects = new JSONArray(response);
                    for (int i = 0; i < objects.length(); i++) {

                        JSONObject object = (JSONObject) objects.getJSONObject(i);
                        int uid = object.getInt("purrec_uid");
                        status = object.getString("pur_status");
                        if (String.valueOf(uid).equals(userid) && status.equalsIgnoreCase("Order Completed") && uid != 0 && !status.equals("null")) {
                            int prod_id = object.getInt("purrec_prodid");
                            buylist.add(String.valueOf(prod_id));
                        }
                    }}
                    getPosts2 = new AsyncRequest2(this, "GET");
                    if (wifiState() || NetworkState()) {

                        count++;
                        getPosts2.execute(apiURL1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enable network", Toast.LENGTH_LONG).show();
                        getPosts2.cancel(true);
                    }
                }
          /* if(count==1)
            {
                JSONArray objects1 = new JSONArray(response);
                for (int i = 0; i < objects1.length(); i++) {
                    JSONObject object1 = (JSONObject) objects1.getJSONObject(i);
                    int prod_id=object1.getInt("prod_id");
                    if(buylist.contains(String.valueOf(prod_id))) {
                        name=object1.getString("prod_name");
                        if(!name.isEmpty()){
                        namelist.add(name);
                            Toast.makeText(getApplicationContext(),"nameList:"+namelist,Toast.LENGTH_LONG).show();
                        getNotification();}
                    }
                }
            }*/
            }}
        catch (JSONException e) {
            e.printStackTrace();
        }

        // Toast.makeText(getApplicationContext(),"Service Running:"+name,Toast.LENGTH_LONG).show();
    }



    @Override
    public void asyncResponse1(String response)
    {
        try {
            if(wifiState()||NetworkState()){
            if(count==1)
            {
                JSONArray objects1 = new JSONArray(response);
                for (int i = 0; i < objects1.length(); i++) {
                    JSONObject object1 = (JSONObject) objects1.getJSONObject(i);
                    int prod_id=object1.getInt("prod_id");
                    if(buylist.contains(String.valueOf(prod_id))) {
                        name=object1.getString("prod_name");
                        if(!name.isEmpty()){
                            namelist.add(name);
                            //Toast.makeText(getApplicationContext(),"nameList:"+namelist,Toast.LENGTH_LONG).show();
                            getNotification();}
                    }
                }
            }
        }}
        catch (JSONException e) {
            e.printStackTrace();
        }

        // Toast.makeText(getApplicationContext(),"Service Running:"+name,Toast.LENGTH_LONG).show();
    }



    public void getNotification()
    {
        if(namelist.size()>0){
            NotificationCompat.Builder builder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.app_logo)
                            .setContentTitle("Order Completed")
                            .setContentText("Your order "+ namelist.get(j)+" has been completed");
            Random random = new Random();
            int randomNumber = random.nextInt(9999 - 1000) + 1000;
            // int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


            Intent notificationIntent = new Intent(this, Orders.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(randomNumber, builder.build());
            builder.setAutoCancel(true);
            j++;}
    }



}



