package com.example.ashutosh.mpiricmodule1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ashutosh.fragments.VideoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Models.Util1;


public class SearchActivity extends AppCompatActivity implements AsyncRequest.OnAsyncRequestComplete{

    public String VID="";public String VID1="";public String VID2="";public String VIDEOID="";
    public static String cartelement,purl;
    public static final String VIDEO_ID = "bzSTpdcs-EI";
    public  String thumb,url,url1,description;
    static String Email,mEmail1;
    static ArrayList<Integer>prodIds=new ArrayList<Integer>();
    static ArrayList<String>prodName=new ArrayList<String>();
    static ArrayList<String>prodPrice=new ArrayList<String>();
    static ArrayList<String>clist=new ArrayList<String>();
    private ImageView imageView;
    public static String response1=null;
    final String names[] = {"High to Low", "Low to High"};
    static Boolean buyflag=false;
    Set<String> set2 = new HashSet<String>();
    DatabaseHandler db = new DatabaseHandler(this);
    AlertDialog alertDialog;
    LinearLayout FilterLayout;
    Button FAB,Buy,Cart,SeeDetails;int pd;
    String stprice,s1,s2;
    Set<String> SharedPreferenceSet;
    SharedPreferences sh1,cart;SharedPreferences sh2;
    private CardAdapter adapter;
    private List<Card> albumList;
    String apiURL= Util1.GET_ALLCATEGORY_EPIC;
   public RecyclerView recyclerView;
    String SearchValue;
    AsyncRequest getPosts;
    int keyCode;
    String[] Tags2;
    int j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        albumList = new ArrayList<>();
        Intent i=getIntent();
        SearchValue=i.getStringExtra("SearchValue");
    //    Toast.makeText(getApplicationContext(),"SearchValue:"+SearchValue,Toast.LENGTH_LONG).show();
        getPosts = new AsyncRequest(this, "GET");

   if (wifiState() || NetworkState()) {

            getPosts.execute(apiURL);
        }

        else{
            displayMobileDataSettingsDialog(SearchActivity.this,getApplicationContext(),"No Internet Found","Please enable internet");
            // Toast.makeText(getApplicationContext(),"Please enable network",Toast.LENGTH_LONG).show();
            getPosts.cancel(true);
        }

    }

    public AlertDialog displayMobileDataSettingsDialog(Activity activity, final Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        //  builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
                    startActivity(myIntent);

                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                getPosts.cancel(true);
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


    @Override
    public void asyncResponse(String response) {
        try {
            if(wifiState()|| NetworkState()){
            // create a JSON array from the response string
            JSONArray objects = new JSONArray(response);
            response1 = response;
            for (int i = 0; i < objects.length(); i++) {

                JSONObject object = (JSONObject) objects.getJSONObject(i);
                 String  type =object.getString("prod_type");
                String Tags=object.getString("prod_tags");
                String[] Tags1=Tags.split("\\,");
                if(Tags.contains(SearchValue) || Arrays.asList(Tags1).contains(SearchValue)||org.apache.commons.lang3.StringUtils.containsIgnoreCase(Tags,SearchValue.toLowerCase())) {
                    description = object.getString("prod_description");
                    String name = object.getString("prod_name");
                    int price = object.getInt("prod_price");
                    url = object.getString("prod_demourl");
                    int prod_id = object.getInt("prod_id");
                    int cateid = object.getInt("prod_subcateid");
                    if (type.equals("Videos") || type.equals("Video")) {
                        VIDEOID = url.substring(url.lastIndexOf("?") + 1);
                        VID = (VIDEOID.substring(VIDEOID.indexOf("=") + 1));
                        if (VID.contains("&")) {
                            String[] VID1 = VID.split("\\&");
                            VID = VID1[0];
                        }
                        thumb = "https://img.youtube.com/vi/" + VID + "/mqdefault.jpg";

                        Card a = new Card(name, price, thumb, type, prod_id, mEmail1, url);
                        albumList.add(a);
                    } else if (type.equals("Logo") || type.equals("Logos")) {
                        Card b = new Card(name, price, url, type, prod_id, mEmail1, url);
                        albumList.add(b);
                    }
                }
                    adapter = new CardAdapter(this, albumList, 2);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);}
                }
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed(){
      //  backpress = (backpress + 1);
       // Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();

        //if (backpress>1) {
          //  this.finish();
        //}
        Intent i=new Intent(getApplicationContext(),Home.class);
        startActivity(i);
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
