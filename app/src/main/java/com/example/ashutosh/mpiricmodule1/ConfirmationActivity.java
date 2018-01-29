package com.example.ashutosh.mpiricmodule1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import Models.Util1;

public class ConfirmationActivity extends AppCompatActivity implements  AsyncRequest.OnAsyncRequestComplete {

   String apiURL= Util1.GET_PAYITEM_API;
    String priceurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Intent intent = getIntent();
        String payid="";

        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));
            Log.e("JSONDEtAILS",jsonDetails.toString());
            JSONObject result=jsonDetails.getJSONObject("response");
             payid= result.getString("id");

            //Displaying payment details
           // showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        String s=null;
        if(Cart.pidurl!=null) {
             s = Cart.pidurl.replace("'", "");
        }
        else {
            if(LOGO_ITEM_ACTIVITY.pidlogo==false && V_play.pidvideo==true)
             s = V_play.purl.replace("'", "");
            else if(LOGO_ITEM_ACTIVITY.pidlogo==true && V_play.pidvideo==false)
                s = LOGO_ITEM_ACTIVITY.purl.replace("'", "");
        }
        String amount=String.valueOf(Payment.paymentAmount);
        SharedPreferences sh1=getSharedPreferences("LoginAuth", Context.MODE_PRIVATE);
       // sh1.getString("USERID",null);
        AsyncRequest getPosts = new AsyncRequest(this, "GET");
        getPosts.execute(apiURL+"&total="+amount+"&uid="+LoginActivity.uid+"&pids="+s+"&pids_amt=10,20"+"&email="+sh1.getString("Email",null)+"&payment_id="+payid.toString());
        if(V_play.buyflag==true)
        {
            Intent i=getIntent();
            if(getIntent()!=null){
               // int productid=i.getIntExtra("productid",0);
                SharedPreferences cart=getSharedPreferences("CartElement",Context.MODE_PRIVATE);
                Set<String> set2=cart.getStringSet("CartElement1",null);
                set2.remove(String.valueOf(Payment.prodid));
                SharedPreferences.Editor editor1=cart.edit();
                editor1.putStringSet("CartElement1",set2);
                editor1.commit();
                addnotify();addnotify1();
            }
        }
        else {
            SharedPreferences cart=getSharedPreferences("CartElement",Context.MODE_PRIVATE);
            Set<String> set2=cart.getStringSet("CartElement1",null);
            set2.clear();
            SharedPreferences.Editor editor1=cart.edit();
            editor1.putStringSet("CartElement1",set2);
            editor1.commit();
            addnotify();
            addnotify1();
        }

        int timeout = 4000; // make the activity visible for 4 seconds

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                Intent homepage = new Intent(ConfirmationActivity.this, Home.class);
                startActivity(homepage);
            }
        }, timeout);

    }

    @Override
    public void asyncResponse(String response) {

        Log.e("Resp",response);
    }
    private void addnotify() {
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.app_logo)
                        .setContentTitle("Go Creativity- Order Details")
                        .setContentText("Your order has been successfully placed");

        Intent notificationIntent = new Intent(this, Home.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
        builder.setAutoCancel(true);
    }

    private void addnotify1() {
        Random random = new Random();
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.app_logo)
                        .setContentTitle("Go Creativity- Upload Requirements")
                        .setContentText("Please fill the requirements of your order");

        Intent notificationIntent = new Intent(this, Orders.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(m, builder.build());
        builder.setAutoCancel(true);
    }

    @Override
    public void onBackPressed() {

        Intent i=new Intent(getApplicationContext(),Home.class);
        startActivity(i);

    }
}

