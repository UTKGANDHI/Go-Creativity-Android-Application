package com.example.ashutosh.mpiricmodule1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commit451.youtubeextractor.YouTubeExtractionResult;
import com.commit451.youtubeextractor.YouTubeExtractor;
import com.example.ashutosh.mpiricmodule1.ru.ifsoft.qachat.MainActivity;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Models.Util1;
import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Orders extends AppCompatActivity implements AsyncRequest.OnAsyncRequestComplete,AsyncRequest2.OnAsyncRequestComplete{

    String apiURL = Util1.GET_ORDERS_API;
    String cancelURL = Util1.CANCEL;
    String apiURL1 = Util1.GET_ALLCATEGORY_EPIC;
    public String VID = "";
    URL url1;String status;
    public static boolean wait=false;
    public static boolean complete=false;
    String selectedText;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder build;
    YouTubeExtractor mExtractor;
    ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    //private static String file_url = "http://api.androidhive.info/progressdialog/hive.jpg";
    // private static String Zip_url="data:application/zip;base64,UEsDBAoAAAAAAFVbhErV4Dm3DAAAAAwAAAAJAAAASGVsbG8udHh0SGVsbG8gd29ybGQKUEsBAhQACgAAAAAAVVuEStXgObcMAAAADAAAAAkAAAAAAAAAAAAAAAAAAAAAAEhlbGxvLnR4dFBLBQYAAAAAAQABADcAAAAzAAAAAAA=";
    public String VIDEOID = "";
    String thumb, userid,uid;
    int count = 0;
    ArrayList<String> buylist = new ArrayList<String>();
    ArrayList<String> statuslist = new ArrayList<String>();
    String url = "https://i.ytimg.com/vi/wRWIsbSXoyQ/mqdefault.jpg";
    public static OrderAdapter adapter;
    private RecyclerView recyclerView;
    public static List<OrderCard> cartlist, cartlist1;
    String downloadUrl;
    CountDownTimer cdt;
    //  String youtubeLink="https://r6---sn-gwpa-5hqd.googlevideo.com/videoplayback?id=o-AEIwxS28AhLgj5djwL3NgIt-4giFmwxX3GpQuZufd-hl&mn=sn-gwpa-5hqd&mm=31&gir=yes&source=youtube&initcwndbps=632500&signature=6AD83224C8A600A822DAAC6D8132AB83CCDBE7F4.494E314246C8684D2A8528247A061608047AEB1D&ip=2405%3A204%3A8083%3A1688%3A7956%3A2984%3A5159%3A9e45&ms=au&mv=m&mt=1491432506&pl=36&clen=20107094&ei=rnXlWN6zC8zZogPlhLHwDQ&pcm2cms=yes&upn=ozeva82Dxp8&ipbits=0&requiressl=yes&itag=18&ratebypass=yes&dur=446.589&lmt=1486722659384697&sparams=clen%2Cdur%2Cei%2Cgir%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpcm2cms%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&key=yt6&mime=video%2Fmp4&expire=1491454478&signature=6AD83224C8A600A822DAAC6D8132AB83CCDBE7F4.494E314246C8684D2A8528247A061608047AEB1D";
    String Link,GlobalUrl,Name,Type;
    YouTubeExtractionResult result;
    File filelocation;
    String retVidUrl;
    String url123="";
    URL url53;
    int totalSize,downloadedSize;
    AlertDialog alert;
    ArrayList<String> VideoYoutubeUrls,YoutubeUrls1,MainList,MainList1;
    String format,quality,YoutubeUrl123;
    String[] words;
    ProgressDialog dialog;
    String ActualYoutubeFormatUrl;

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (shouldAskPermissions()) {
            askPermissions();
        }
        cartlist = new ArrayList<OrderCard>();
        cartlist1 = new ArrayList<OrderCard>();
        SharedPreferences sh1 = getSharedPreferences("LoginAuth", Context.MODE_PRIVATE);
        userid = sh1.getString("USERID", null);
        VideoYoutubeUrls=new ArrayList<String>();
        MainList1=new ArrayList<String>();
        MainList=new ArrayList<String>();

        //     YoutubeUrls1=new ArrayList<String>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        YoutubeUrls1=new ArrayList<String>();
        AsyncRequest getPosts = new AsyncRequest(this, "GET");
        if(wifiState()||NetworkState()){
            getPosts.execute(apiURL);}
        else{
            displayMobileDataSettingsDialog(Orders.this,getApplicationContext(),"No Internet Found","Please enable internet");
            getPosts.cancel(true);
        }

    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),Home.class);
        startActivity(i);
    }
    public AlertDialog displayMobileDataSettingsDialog(Activity activity, final Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        //  builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //    moveTaskToBack(true);
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //  Intent intent1=new Intent(getBaseContext(),Home.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                //    getPosts.cancel(true);
                //     finish();
                //       activity.finish();

            }
        });

        builder.show();

        return builder.create();
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

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStat
        // ement
        if (id == R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void asyncResponse(String response) {
        try {
            JSONArray objects = new JSONArray(response);
            if (count == 0) {
                for (int i = 0; i < objects.length(); i++) {

                    JSONObject object = (JSONObject) objects.getJSONObject(i);
                    int uid = object.getInt("purrec_uid");
                    if (String.valueOf(uid).equals(userid)) {
                        status = object.getString("pur_status");
                        statuslist.add(status);
                        int prod_id = object.getInt("purrec_prodid");
                        int oid = object.getInt("purrec_id");
                        String date=object.getString("purrec_date");
                        OrderCard a = new OrderCard("", null, "", "", prod_id, "", "", status, oid,date);
                        cartlist.add(a);
                        buylist.add(String.valueOf(prod_id));
                    }
                }
                AsyncRequest getPosts2 = new AsyncRequest(this, "GET");
                count++;
                getPosts2.execute(apiURL1);
            }
            if (count == 1) {
                for (int i = 0; i < objects.length(); i++) {
                    JSONObject object = (JSONObject) objects.getJSONObject(i);
                    String type = object.getString("prod_type");
                    if (type.equals("Logo")) {
                        int prod_id = object.getInt("prod_id");
                        if (buylist.contains(String.valueOf(prod_id))) {
                            int price = object.getInt("prod_price");
                            String name = object.getString("prod_name");
                            url = object.getString("prod_demourl");
                            OrderCard a = new OrderCard(name, price, url, type, prod_id, "", url, "", null,"");
                            cartlist1.add(a);
                        }
                    } else if (type.equals("Video")) {
                        int prod_id = object.getInt("prod_id");
                        if (buylist.contains(String.valueOf(prod_id))) {
                            int price = object.getInt("prod_price");
                            String name = object.getString("prod_name");
                            url = object.getString("prod_demourl");
                            VIDEOID = url.substring(url.lastIndexOf("?") + 1);
                            VID = (VIDEOID.substring(VIDEOID.indexOf("=") + 1));
                            if (VID.contains("&")) {
                                String[] VID1 = VID.split("\\&");
                                VID = VID1[0];
                            }
                            thumb = "https://img.youtube.com/vi/" + VID + "/default.jpg";
                            OrderCard a = new OrderCard(name, price, thumb, type, prod_id, "", url, "", null,"");
                            cartlist1.add(a);
                        }
                    }
                }
            }
            adapter = new OrderAdapter(this, cartlist, cartlist1, 0);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void asyncResponse1(String response) {
        Toast.makeText(getApplicationContext(), "Order Cancelled", Toast.LENGTH_SHORT).show();
    }


    // Adapter for Orders

    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
        Context mContext;
        private List<OrderCard> orderList, orderList1;
        int pos;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, order1,price, a, b, c, status, orderid1,deldate;
            RelativeLayout rel;
            Button button;
            ImageView img1;
            CardView card_view;


            public MyViewHolder(View view) {
                super(view);

                a = (TextView) view.findViewById(R.id.pname);
                b = (TextView) view.findViewById(R.id.price);
                c = (TextView) view.findViewById(R.id.status);
                //date=(TextView) view.findViewById(R.id.date);
                deldate=(TextView) view.findViewById(R.id.deldate);
                order1 = (TextView) view.findViewById(R.id.order);
                orderid1 = (TextView) view.findViewById(R.id.orderid);
                img1 = (ImageView) view.findViewById(R.id.Img1);
                button = (Button) view.findViewById(R.id.button_dwnld);
                card_view = (CardView) view.findViewById(R.id.card_view);
                rel = (RelativeLayout) view.findViewById(R.id.relparent);
            }
        }

        public OrderAdapter(Context mContext, List<OrderCard> orderList, List<OrderCard> orderList1, int pos) {
            this.mContext = mContext;
            this.orderList = orderList;
            this.orderList1 = orderList1;
            this.pos = pos;
            notifyDataSetChanged();
        }

        @Override
        public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_orders, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            if(statuslist.get(position).equalsIgnoreCase("Waiting for requirements") || statuslist.get(position).equalsIgnoreCase("In Development") || statuslist.get(position).equalsIgnoreCase("Analyzing Requirements") ){
                //  holder.button.setVisibility(View.INVISIBLE);
                holder.button.setEnabled(false);
                holder.button.setTextColor(Color.GRAY);
            }
            else
            {
                holder.button.setVisibility(View.VISIBLE);  holder.button.setEnabled(true);
            }
            if (orderList.size() == 0 || orderList1.size() == 0) {
                holder.rel.setVisibility(View.GONE);
                holder.order1.setText("There are no orders");
            } else {
                holder.order1.setVisibility(View.GONE);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                holder.b.setText("Price: $" + orderList1.get(position).getNumOfSongs().toString()); // setting the name of the cards in the fragment (Logo and Video)
                holder.c.setText("Status: " + orderList.get(position).getStatus());
                //holder.date.setText(orderList.get(position).getDate());
                holder.a.setText("Name: " + orderList1.get(position).getName());
                int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                holder.orderid1.setText(String.valueOf(m + orderList.get(position).getOid()));
                final int pid=orderList.get(position).getProd_id();
                SharedPreferences sh1=getSharedPreferences("LoginAuth", Context.MODE_PRIVATE);
                uid=sh1.getString("USERID",null);
                holder.deldate.setText("Delivery: Within 4-7 days");
                holder.card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (holder.c.getText().toString().contains("Waiting for requirements") ) {
                            final AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(Orders.this);
                            alertDialog3.create();
                            alertDialog3.setTitle("Track your Order");
                            alertDialog3.setMessage(holder.c.getText().toString().trim()+"\n"+holder.deldate.getText().toString().trim());

                            alertDialog3.setPositiveButton("Upload Requirements", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //    moveTaskToBack(true);
                                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    wait=true;complete=false;
                                    Intent intent1=new Intent(getBaseContext(),AddDescription.class);
                                    intent1.putExtra("productid",pid);
                                    startActivity(intent1);

                                }
                            });
                            alertDialog3.setNegativeButton("Cancel Order", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if(wifiState()||NetworkState()){
                                        AsyncRequest2 getPosts = new AsyncRequest2(v.getContext(), "GET");
                                        getPosts.execute(cancelURL +"&pid="+pid+"&uid="+Integer.valueOf(uid));
                                        updatelist(position);

                                    }
                                }
                            });
                            Orders.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();
                                }
                            });

                            alertDialog3.setCancelable(true);
                            alertDialog3.show();
                            //           Intent i = new Intent(getApplicationContext(), AddDescription.class);
                            //          startActivity(i);
                        }
                        else if(holder.c.getText().toString().contains("Order Completed")) {
                            final AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(Orders.this);
                            alertDialog3.create();
                            alertDialog3.setTitle("Your Order");
                            alertDialog3.setMessage(holder.c.getText().toString().trim()+"\n");
                            alertDialog3.setPositiveButton("Change Order", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Intent intent1=new Intent(getBaseContext(),AddDescription.class);complete=true;wait=false;
                                    intent1.putExtra("productid",pid);
                                    startActivity(intent1);

                                }
                            });
                            alertDialog3.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                }
                            });
                            alertDialog3.setCancelable(true);
                            alertDialog3.show();

                        }
                        else if(holder.c.getText().toString().contains("In Development")|| holder.c.getText().toString().contains("Analyzing Requirements") ) {
                            final AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(Orders.this);
                            alertDialog3.create();
                            alertDialog3.setTitle("Your Order");
                            alertDialog3.setMessage(holder.c.getText().toString().trim()+"\n"+"\n"+holder.deldate.getText().toString().trim());
                            alertDialog3.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog3.setCancelable(true);
                            alertDialog3.show();

                        }
                    }
                });

                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        url123=orderList1.get(position).getUrl();
                        Type=orderList1.get(position).getType();
                        Name=orderList1.get(position).getName();
                        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        build = new NotificationCompat.Builder(Orders.this);
                        build.setContentTitle("Download").setContentText("Downloading..")
                                .setSmallIcon(R.drawable.app_logo);

                        if(Type.equals("Logo")|| Type.equals("Logos")){
                            if(!url123.isEmpty()){
                                new DownloadFileFromURL().execute(url123);
                            }}

                        else if(Type.equals("Video")|| Type.equals("Videos")) {
                            if (!url123.isEmpty()) {
                                if(!YoutubeUrls1.isEmpty()){
                                    YoutubeUrls1.clear();}
                                if(!MainList1.isEmpty()){MainList1.clear();}
                                new YouTubePageStreamUriGetter().execute(url123);

                            }

                        }

                    }
                });

                String Image = orderList1.get(position).getThumbnail();

                try {
                    url1 = new URL(Image);
                } catch (MalformedURLException e) {
                }

                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                    holder.img1.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void updatelist(int position)
        {
            orderList.remove(position);
            //orderList.addAll(orderList);
            orderList1.remove(position);//orderList1.addAll(orderList1);
            notifyDataSetChanged();
        }


        protected Dialog onCreateDialog(int id) {
            switch (id) {
                case progress_bar_type: // we set this to 0
                    pDialog = new ProgressDialog(getApplicationContext());
                    pDialog.setMessage("Downloading file. Please wait...");
                    pDialog.setIndeterminate(false);
                    pDialog.setMax(100);
                    pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pDialog.setCancelable(true);
                    pDialog.show();
                    return pDialog;
                default:
                    return null;
            }
        }

        public int getItemCount() {
            return orderList.size();
        }
    }

    public class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");
            build.setProgress(100, 0, false);
            mNotifyManager.notify(123, build.build());

            pDialog = new ProgressDialog(Orders.this);
            pDialog.setMessage("Downloading... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected void onProgressUpdate(String... values) {
            // Update progress
            build.setProgress(100, Integer.parseInt(values[0]), false);
            mNotifyManager.notify(123, build.build());
            build.setContentText("Downloading:"+values[0].toString()+"%");
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                String root = Environment.getExternalStorageDirectory().toString();
                File path = Environment.getExternalStoragePublicDirectory(String.valueOf(Environment.getDataDirectory()));
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);

                System.out.println("Downloading");

                if(!Type.isEmpty()) {
                    if(Type.equals("Logo")||Type.equals("Logos")){
                        if(!Name.isEmpty()){
                            url53=new URL(url123);
                            filelocation = new File(path + "/MpiricModule/ProfileVideos/"+Name+".jpg");}
                    }
                    if(Type.equals("Video")||Type.equals("Videos")){
                        if(!Name.isEmpty()){
                            url53= new URL(ActualYoutubeFormatUrl);
                            if(ActualYoutubeFormatUrl.contains("webm")){
                                filelocation = new File(path + "/MpiricModule/ProfileVideos/"+Name+".webm");}
                            else if(ActualYoutubeFormatUrl.contains("mp4")){
                                filelocation = new File(path + "/MpiricModule/ProfileVideos/"+Name+".mp4");
                            }}
                    }}

                URLConnection conection = url53.openConnection();
                conection.connect();
                totalSize = conection.getContentLength();
                InputStream input = new BufferedInputStream(url53.openStream(), 8192);

                // Output stream to write file

                downloadedSize = 0;
                if(!filelocation.exists()){
                    filelocation.getParentFile().mkdirs();}
                String currentpath = filelocation.getAbsolutePath();
                OutputStream output = new FileOutputStream(filelocation);
                byte[] buffer = new byte[1024];
                int bufferLength = 0;

                while ((bufferLength = input.read(buffer)) > 0)
                {
                    downloadedSize += bufferLength;
                    int previousProgress = 0;int latestPercentDone,percentDone=-1;
                    //latestPercentDone = (int) Math.round(((downloadedSize / ((float)totalSize/1000)) * 100.0));
                    if(Type.equals("Videos")||Type.equals("Video")){
                        latestPercentDone = (int) Math.round(((downloadedSize / ((float)totalSize)) * 100.0));}
                    else{
                        latestPercentDone = (int) Math.round(((downloadedSize / ((float)totalSize)) * 100.0));
                    }
                    //  int prog=(downloadedSize/totalSize)*100;
                    if (percentDone != latestPercentDone) {
                        percentDone = latestPercentDone;

                    }  output.write(buffer, 0, bufferLength);
                    try{
                        Thread.sleep(15);
                        publishProgress(String.valueOf(percentDone));

                    }catch(Exception e){}
                }


                if (downloadedSize == totalSize) {
                    output.close();
                    //  Toast.makeText(getApplicationContext(), "Downloaded", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            Toast.makeText(getApplicationContext(),"Video Downloaded ",Toast.LENGTH_LONG).show();
            //  pDialog.dismiss();
            build.setContentText("Download complete");
            // Removes the progress bar
            build.setProgress(0, 0, false);
            mNotifyManager.notify(123, build.build());
            build.setAutoCancel(true);
        }
    }




    class Meta {
        public String num;
        public String type;
        public String ext;

        Meta(String num, String ext, String type) {
            this.num = num;
            this.ext = ext;
            this.type = type;
        }
    }

    class Video {
        public String ext = "";
        public String type = "";
        public String url = "";

        Video(String ext, String type, String url) {
            this.ext = ext;
            this.type = type;
            this.url = url;
        }
    }

    public ArrayList<Video> getStreamingUrisFromYouTubePage(String ytUrl)
            throws IOException {
        if (ytUrl == null) {
            return null;
        }

        // Remove any query params in query string after the watch?v=<vid> in
        // e.g.
        // http://www.youtube.com/watch?v=0RUPACpf8Vs&feature=youtube_gdata_player
        int andIdx = ytUrl.indexOf('&');
        if (andIdx >= 0) {
            ytUrl = ytUrl.substring(0, andIdx);
        }

        // Get the HTML response
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0.1)";
        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
                userAgent);
        HttpGet request = new HttpGet(ytUrl);
        HttpResponse response = client.execute(request);
        String html = "";
        InputStream in = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder str = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            str.append(line.replace("\\u0026", "&"));
        }
        in.close();
        html = str.toString();

        // Parse the HTML response and extract the streaming URIs
        if (html.contains("verify-age-thumb")) {
            //        CLog.w("YouTube is asking for age verification. We can't handle that sorry.");
            //      return null;
        }

        if (html.contains("das_captcha")) {
//            CLog.w("Captcha found, please try with different IP address.");
            //          return null;
        }

        Pattern p = Pattern.compile("\"url_encoded_fmt_stream_map\":\"(.*?)?\"",Pattern.MULTILINE);
        //Pattern p = Pattern.compile("/stream_map=(.[^&]*?)\"/");
        Matcher m = p.matcher(html);
        List<String> matches = new ArrayList<String>();
        while (m.find()) {
            matches.add(m.group());
        }

        if (matches.size() != 1) {
            //   CLog.w("Found zero or too many stream maps.");
            return null;
        }

        String urls[] = matches.get(0).split(",");
        HashMap<String, String> foundArray = new HashMap<String, String>();
        for (String ppUrl : urls) {
            String url = URLDecoder.decode(ppUrl, "UTF-8");

            Pattern p1 = Pattern.compile("itag=([0-9]+?)[&]",Pattern.MULTILINE);
            Matcher m1 = p1.matcher(url);
            String itag = null;
            if (m1.find()) {
                itag = m1.group(1);
            }

            Pattern p2 = Pattern.compile("signature=(.*?)[&]",Pattern.MULTILINE);
            Matcher m2 = p2.matcher(url);
            String sig = null;
            if (m2.find()) {
                sig = m2.group(1);
            }

            Pattern p3 = Pattern.compile("url=(.*?)[&]",Pattern.MULTILINE);
            Matcher m3 = p3.matcher(ppUrl);
            String um = null;
            if (m3.find()) {
                um = m3.group(1);
            }

            if (itag != null && sig != null && um != null) {
                foundArray.put(itag, URLDecoder.decode(um, "UTF-8") + "&"
                        + "signature=" + sig);
            }
        }

        if (foundArray.size() == 0) {
            //  CLog.w("Couldn't find any URLs and corresponding signatures");
            return null;
        }

        HashMap<String, Meta> typeMap = new HashMap<String, Meta>();
        typeMap.put("13", new Meta("13", "3GP", "Low Quality - 176x144"));
        typeMap.put("17", new Meta("17", "3GP", "Medium Quality - 176x144"));
        typeMap.put("36", new Meta("36", "3GP", "High Quality - 320x240"));
        typeMap.put("5", new Meta("5", "FLV", "Low Quality - 400x226"));
        typeMap.put("6", new Meta("6", "FLV", "Medium Quality - 640x360"));
        typeMap.put("34", new Meta("34", "FLV", "Medium Quality - 640x360"));
        typeMap.put("35", new Meta("35", "FLV", "High Quality - 854x480"));
        typeMap.put("43", new Meta("43", "WEBM", "Low Quality - 640x360"));
        typeMap.put("44", new Meta("44", "WEBM", "Medium Quality - 854x480"));
        typeMap.put("45", new Meta("45", "WEBM", "High Quality - 1280x720"));
        typeMap.put("18", new Meta("18", "MP4", "Medium Quality - 480x360"));
        typeMap.put("22", new Meta("22", "MP4", "High Quality - 1280x720"));
        typeMap.put("37", new Meta("37", "MP4", "High Quality - 1920x1080"));
        typeMap.put("33", new Meta("38", "MP4", "High Quality - 4096x230"));

        ArrayList<Video> videos = new ArrayList<Video>();

        for (String format : typeMap.keySet()) {
            Meta meta = typeMap.get(format);

            if (foundArray.containsKey(format)) {
                Video newVideo = new Video(meta.ext, meta.type,
                        foundArray.get(format));
                videos.add(newVideo);

                //  CLog.d("YouTube Video streaming details: ext:" + newVideo.ext
                //        + ", type:" + newVideo.type + ", url:" + newVideo.url);
            }
        }


        return videos;
    }


    public class YouTubePageStreamUriGetter extends
            AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        String YoutubeUrl;
        final AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(Orders.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(Orders.this);
            dialog.setMessage("Loading Download Formats");
            dialog.setCancelable(false);
            dialog.setIndeterminate(true);
            dialog.show();


            //  progressDialog = ProgressDialog.show(ARViewer.this, "",
            //        "Connecting to YouTube...", true);
        }

        @Override
        public String doInBackground(String... params) {
            String url = params[0];
            try {
                ArrayList<Video> videos = getStreamingUrisFromYouTubePage(url);

                if (videos != null && !videos.isEmpty()) {
                    for (Video video : videos) {
                        if (video.ext.toLowerCase().contains("mp4")
                                && video.type.contains("High Quality - 1920x1080")) {
                            YoutubeUrl=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString();
                            YoutubeUrl123=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString()+","+video.url;
                            VideoYoutubeUrls.add(YoutubeUrl);
                            MainList.add(YoutubeUrl123);
                            // retVidUrl = video.url;

                        }
                        if (video.ext.toLowerCase().contains("mp4")
                                && video.type.contains("High Quality - 4096x230")) {
                            //  YoutubeUrl=video.ext.toLowerCase().toString()+video.type.toLowerCase().toString();
                            YoutubeUrl=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString();
                            YoutubeUrl123=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString()+","+video.url;
                            VideoYoutubeUrls.add(YoutubeUrl);
                            MainList.add(YoutubeUrl123);
                            //retVidUrl = video.url;

                        }
                        if (video.ext.toLowerCase().contains("mp4")
                                && video.type.contains("High Quality - 1280x720")) {
                            YoutubeUrl=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString();
                            //YoutubeUrl=video.ext.toLowerCase().toString()+video.type.toLowerCase().toString();
                            YoutubeUrl123=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString()+","+video.url;
                            VideoYoutubeUrls.add(YoutubeUrl);
                            MainList.add(YoutubeUrl123);
                            //retVidUrl = video.url;

                        }

                        if (video.ext.toLowerCase().contains("mp4")
                                && video.type.contains("Medium Quality - 480x360")) {
                            YoutubeUrl=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString();
                            YoutubeUrl123=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString()+","+video.url;
                            //YoutubeUrl=video.ext.toLowerCase().toString()+video.type.toLowerCase().toString();
                            VideoYoutubeUrls.add(YoutubeUrl);
                            MainList.add(YoutubeUrl123);
                            //retVidUrl = video.url;

                        }
                        if (video.ext.toLowerCase().contains("mp4")
                                && video.type.contains("Medium Quality - 480x360")) {
                            YoutubeUrl=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString();
                            YoutubeUrl123=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString()+","+video.url;
                            //    YoutubeUrl=video.ext.toLowerCase().toString()+video.type.toLowerCase().toString();
                            VideoYoutubeUrls.add(YoutubeUrl);
                            MainList.add(YoutubeUrl123);
                            //retVidUrl = video.url;
                        }
                        if (video.ext.toLowerCase().contains("webm")
                                && video.type.contains("High Quality - 1280x720")) {
                            YoutubeUrl=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString();
                            //  YoutubeUrl=video.ext.toLowerCase().toString()+video.type.toLowerCase().toString();
                            YoutubeUrl123=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString()+","+video.url;
                            VideoYoutubeUrls.add(YoutubeUrl);
                            MainList.add(YoutubeUrl123);
                            //  retVidUrl = video.url;
                        }
                        if (video.ext.toLowerCase().contains("webm")
                                && video.type.contains("Medium Quality - 854x480")) {
                            YoutubeUrl=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString();
                            YoutubeUrl123=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString()+","+video.url;
                            //YoutubeUrl=video.ext.toLowerCase().toString()+video.type.toLowerCase().toString();
                            VideoYoutubeUrls.add(YoutubeUrl);
                            MainList.add(YoutubeUrl123);
                            //    retVidUrl = video.url;

                        }
                        if (video.ext.toLowerCase().contains("webm")
                                && video.type.contains("Low Quality - 640x360")) {
                            YoutubeUrl=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString();
                            YoutubeUrl123=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString()+","+video.url;
                            //    YoutubeUrl=video.ext.toLowerCase().toString()+video.type.toLowerCase().toString();
                            VideoYoutubeUrls.add(YoutubeUrl);
                            MainList.add(YoutubeUrl123);
                            // retVidUrl = video.url;
                        }
                        if (video.ext.toLowerCase().contains("flv")
                                && video.type.contains("High Quality - 854x480")) {
                            YoutubeUrl=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString();
                            YoutubeUrl123=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString()+","+video.url;
                            // YoutubeUrl=video.ext.toLowerCase().toString()+video.type.toLowerCase().toString();
                            VideoYoutubeUrls.add(YoutubeUrl);
                            MainList.add(YoutubeUrl123);
                            // retVidUrl = video.url;
                        }
                        if (video.ext.toLowerCase().contains("flv")
                                && video.type.contains("Medium Quality - 640x360")) {
                            YoutubeUrl=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString();
                            YoutubeUrl123=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString()+","+video.url;
                            //  YoutubeUrl=video.ext.toLowerCase().toString()+video.type.toLowerCase().toString();
                            VideoYoutubeUrls.add(YoutubeUrl);
                            MainList.add(YoutubeUrl123);
                            //   retVidUrl = video.url;

                        }
                        if (video.ext.toLowerCase().contains("flv")
                                && video.type.contains("Low Quality - 400x226")) {
                            YoutubeUrl=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString();
                            YoutubeUrl123=video.ext.toLowerCase().toString()+"-"+video.type.toLowerCase().toString()+","+video.url;
                            //YoutubeUrl=video.ext.toLowerCase().toString()+video.type.toLowerCase().toString();
                            VideoYoutubeUrls.add(YoutubeUrl);
                            MainList.add(YoutubeUrl123);
                            //     retVidUrl = video.url;

                        }


                    }
                    YoutubeUrls1=VideoYoutubeUrls;
                    MainList1=MainList;
                    if (retVidUrl == null) {
                        for (Video video : videos) {
                            if (video.ext.toLowerCase().contains("3gp")
                                    && video.type.toLowerCase().contains(
                                    "medium")) {
                                retVidUrl = video.url;
                                break;

                            }
                        }
                    }
                    if (retVidUrl == null) {

                        for (Video video : videos) {
                            if (video.ext.toLowerCase().contains("mp4")
                                    && video.type.toLowerCase().contains("low")) {
                                retVidUrl = video.url;
                                break;

                            }
                        }
                    }
                    if (retVidUrl == null) {
                        for (Video video : videos) {
                            if (video.ext.toLowerCase().contains("3gp")
                                    && video.type.toLowerCase().contains("low")) {
                                retVidUrl = video.url;
                                break;
                            }
                        }
                    }
                    GlobalUrl=retVidUrl;

                    return retVidUrl;
                }
            } catch (Exception e) {
                //      CLog.e("Couldn't get YouTube streaming URL", e);
            }
            //    CLog.w("Couldn't get stream URI for " + url);
            return retVidUrl;
        }


        @Override
        protected void onPostExecute(String streamingUrl) {
            super.onPostExecute(streamingUrl);
            streamingUrl=retVidUrl;
            final String []Array = new String[YoutubeUrls1.size()];
            YoutubeUrls1.toArray(Array);
            //  String[] Array={"1","2","3"};

            AlertDialog alertDialog = alertDialog2.create();
            alertDialog2.setTitle("Download Formats");
            alertDialog.setCancelable(true);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.video_alertdialog, null);
            ListView lv = (ListView) convertView.findViewById(R.id.videourllist1);
            if(Array.length!=0){
                dialog.dismiss();
                alertDialog2.setItems(Array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        selectedText = Array[which];
                        for (int i = 0; i < MainList.size(); i++) {
                            String s = MainList.get(i).toString();
                            if (s.contains(selectedText)) {
                                int index = MainList.indexOf(s);
                                String u = MainList.get(index);
                                //    Toast.makeText(getApplicationContext(), "SelectedText:" + u, Toast.LENGTH_LONG).show();
                                String[] words=u.split("\\,");
                                ActualYoutubeFormatUrl=words[1];
                                if (!ActualYoutubeFormatUrl.isEmpty()) {
                                    new DownloadFileFromURL().execute(ActualYoutubeFormatUrl);
                                }
                            }
                            // int index = MainList.indexOf(MainList.get(i).contains(selectedText.toString()));

                        }
                    }
                });

                alertDialog2.show();}
            else{
                Toast.makeText(getApplicationContext(),"No Formats Available Please check Connection",Toast.LENGTH_LONG).show();
            }


            //  progressDialog.dismiss();
            if (streamingUrl != null) {
                System.out.println(streamingUrl);



            }
        }
    }

}





