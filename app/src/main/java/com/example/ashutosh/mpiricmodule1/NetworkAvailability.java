package com.example.ashutosh.mpiricmodule1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Ashutosh on 31-01-2017.
 */

public class NetworkAvailability  extends AppCompatActivity {

    boolean isNetworkEnabled = false;
    boolean IsWifiEnabled = false;
    Context mContext;
    Activity activity;

    public  boolean NetworkAvailbility(Context mContext,Activity activity)
    {   this.activity=activity;
        this.mContext=mContext;
        WifiManager wifi=(WifiManager)mContext.getSystemService(mContext.WIFI_SERVICE);
        ConnectivityManager conn=(ConnectivityManager)mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        final android.net.NetworkInfo wifi1 = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi1.isConnectedOrConnecting ()==false && mobile.isConnectedOrConnecting ()==false) {
            Toast.makeText(mContext, " No Wifi or Mobile Data connection", Toast.LENGTH_LONG).show();
        }
        else if(mobile.isConnectedOrConnecting ()){
            isNetworkEnabled=true;
        }


        if(wifi.isWifiEnabled())
        {
            IsWifiEnabled=true;
        }

return wifi.isWifiEnabled() || isNetworkEnabled ;
   }

/*   public  AlertDialog displayMobileDataSettingsDialog( Activity activity,final Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

     //  builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                //activity.finish();

            }
        });

        builder.show();

        return builder.create();
    }*/
}


