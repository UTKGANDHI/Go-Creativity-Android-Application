package com.example.ashutosh.mpiricmodule1;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.ashutosh.JSONParsers.CategoryJSONParser;
import com.example.ashutosh.JSONParsers.SubCategoryJSONParser;

import com.example.ashutosh.adapters.CustomPagerAdapter;
import com.example.ashutosh.adapters.DrawerExpandableAdapter;
import com.example.ashutosh.datasource.CategoryModel;
import com.example.ashutosh.datasource.DrawerChildModel;
import com.example.ashutosh.datasource.DrawerParentModel;
import com.example.ashutosh.datasource.SubCategoryModel;
import com.example.ashutosh.fragments.LogoFragment;
import com.joanzapata.iconify.widget.IconButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Util1;


public class Home extends AppCompatActivity implements AsyncRequest.OnAsyncRequestComplete, TabLayout.OnTabSelectedListener , Application.ActivityLifecycleCallbacks {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListAdapter mExpandableListAdapter;
    private List<String> mExpandableListTitle;
    private String[] items;
    public static ArrayList<String>name_home=new ArrayList<String>();
    public static ArrayList<String>url_sublogo=new ArrayList<String>();
    public static ArrayList<String>url_subvideo=new ArrayList<String>();
    private Map<String, List<String>> mExpandableListData;
    public  static List<Card> logolist = new ArrayList<>();
    public  static List<Card> homelist = new ArrayList<>();
    public  static List<Card> videolist = new ArrayList<>();;
    static List<CategoryModel> categoryList=new ArrayList<>();
    static List<SubCategoryModel> subcategoryList=new ArrayList<>();
    int count=0;
    public static ArrayList<Integer> cid=new ArrayList<Integer>();
    public static ArrayList<Integer> vid=new ArrayList<Integer>();
    public int position;
    int keyCode,backpress=0;
    boolean back;
    boolean  isInterestingActivityVisible;

    public static Boolean flag_logo=false,flag_video=false;
    SharedPreferences sh1;
    LayoutInflater inflater;
    RecyclerView mrecycler;
    ExpandableListView mExpandableListView;
    String videoCatURL= Util1.GET_SUBVIDEOLIST_API;
    String apiURL= Util1.GET_SUBLOGOLIST_API;
    String apiURLRecommend=Util1.GET_RECOMMENDEDITEM_API;
    TabLayout tabLayout;
    DrawerChildModel item1;
    CustomPagerAdapter customPagerAdapter;
    ViewPager viewPager;
    List<ParentObject> pob = new ArrayList<>();
    List<Object> parentList =new ArrayList<>() ;    List<Object> parentList1 =new ArrayList<>() ;
    List<Object>  childList=new ArrayList<>() ;List<Object>  childList1=new ArrayList<>() ;
    RequestQueue requestQueue;
    AsyncRequest getPosts;
    EditText SearchText;
     IconButton  iconButtonSearch,iconButtonCart ;
     TextView text2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        inflater = getLayoutInflater();

       // registerActivityLifecycleCallbacks(this);
         checkforLogin();
      //  Toast.makeText(getApplicationContext(),"Logged:"+log1, Toast.LENGTH_LONG).show();
        init();
         getPosts = new AsyncRequest(this, "GET");
        if (wifiState() || NetworkState()) {

            getPosts.execute(apiURL);
        }

        else{
            displayMobileDataSettingsDialog(Home.this,getApplicationContext(),"No Internet Found","Please enable internet");
            // Toast.makeText(getApplicationContext(),"Please enable network",Toast.LENGTH_LONG).show();
            getPosts.cancel(true);
        }
     //   getPosts.execute(apiURL);
        addToList();
        jsonListForAdapter();
        expandableList();
        Intent intService =  new Intent(this, MyService.class);
        startService(intService);
        registerAlarm(getApplicationContext());
     //   registerAlarm(getApplicationContext());
//        Intent i= new Intent(getApplicationContext(), MyService.class);
//
//        getApplicationContext().startService(i);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int Action = event.getAction();
        if(Action == KeyEvent.ACTION_DOWN){
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something
            back=true;
            moveTaskToBack(false);
        }}
        return super.onKeyDown(keyCode, event);
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
              //  if (keyCode == KeyEvent.KEYCODE_BACK|| back) {
                //    Intent myIntent = new Intent(getBaseContext(), Home.class);
                 //  myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  //  myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
                   // startActivity(myIntent);}


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

    @Override
    public void onActivityResumed(Activity activity) {
      //  if (activity instanceof MyInterestingActivity) {
            isInterestingActivityVisible = true;
        if (keyCode == KeyEvent.KEYCODE_BACK|| back) {
            Intent myIntent = new Intent(getBaseContext(), Home.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// clear back stack
            startActivity(myIntent);}
        if(wifiState()|| NetworkState()){
        getPosts.execute(apiURL);}

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
    //    if (activity instanceof MyInterestingActivity) {
            isInterestingActivityVisible = false;
     //   }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        //    if (activity instanceof MyInterestingActivity) {
        isInterestingActivityVisible = false;
        if(wifiState()|| NetworkState()){
            getPosts.execute(apiURL);}
        //   }
    }

    @Override
    public void onBackPressed() {
        backpress++;
            if(backpress==1) {
                iconButtonCart.setVisibility(View.VISIBLE);
                iconButtonSearch.setVisibility(View.VISIBLE);
                SearchText.setVisibility(View.GONE);
                text2.setVisibility(View.VISIBLE);
            }


       else if(backpress>1 && text2.getVisibility()==View.GONE){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);}
    }

    public static void registerAlarm(Context context) {
        Calendar cal= Calendar.getInstance();
        Intent intent = new Intent(context, MyService.class);
        PendingIntent pintent = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 3*1000, pintent);

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

    public void asyncResponse(String response) {

        try {
            // create a JSON array from the response string
            if (wifiState() || NetworkState()) {
                JSONArray objects = new JSONArray(response);
                if (count == 0) {
                    logolist.clear();
                    for (int i = 0; i < objects.length(); i++) {
                        JSONObject object = (JSONObject) objects.getJSONObject(i);
                        String name = object.getString("sub_name");
                        cid.add(object.getInt("sub_id"));
                        String url_name = object.getString("sub_urlname");
                        Card b = new Card(name, null, " ", "", null, "", url_name);
                        logolist.add(b);
                        url_sublogo.add(url_name);
                    }
                }
                if (count == 1) {
                    videolist.clear();
                    for (int i = 0; i < objects.length(); i++) {
                        JSONObject object = (JSONObject) objects.getJSONObject(i);
                        String name = object.getString("sub_name");
                        vid.add(object.getInt("sub_id"));
                        String url_name = object.getString("sub_urlname");
                        Card b = new Card(name, null, " ", "", null, "", url_name);
                        videolist.add(b);
                        url_subvideo.add(url_name);
                    }

                    AsyncRequest getPosts2 = new AsyncRequest(this, "GET");
                    count++;
                    getPosts2.execute(apiURLRecommend);// for recommended items
                }
                if (count == 2) {
                    homelist.clear();
                    for (int i = 0; i < objects.length(); i++) {
                        JSONObject object = (JSONObject) objects.getJSONObject(i);
                        String name = object.getString("prod_name");
                        String type=object.getString("prod_type");
                        int price=object.getInt("prod_price");
                        int prid=object.getInt("prod_id");
                        String url=object.getString("prod_demourl");
                        Card b = new Card(name, price, "", type, prid, "", url);
                        homelist.add(b);
                        name_home.add(name);
                    }
                    count++;
                    bindToviewPager();
                }

                if (count == 0) {
                    AsyncRequest getPosts1 = new AsyncRequest(this, "GET");
                    count++;
                    getPosts1.execute(videoCatURL);// for video category
                }
            } } catch(JSONException e){
                e.printStackTrace();
            }
        }

    public void  jsonListForAdapter() {
        parentList = new ArrayList<>();
        childList = new ArrayList<>();
        CategoryModel itm_cm ;
        for (int i = 0; i < categoryList.size(); i++)
        {
            itm_cm = new CategoryModel();
            itm_cm.setCat_ID(categoryList.get(i).getCat_ID());
            itm_cm.setCat_Name(categoryList.get(i).getCat_Name());
            parentList.add(itm_cm);

            for (int j = 0; j < 3; j++)
            {
                SubCategoryModel itm_sub_cm=new SubCategoryModel();
                itm_sub_cm.setSub_id(subcategoryList.get(j).getSub_id());
                itm_sub_cm.setSub_name(subcategoryList.get(j).getSub_name());
                childList.add(itm_sub_cm);
            }
            //childList.add(childlist.get());


            itm_cm.setChildObjectList(childList);
            pob.add(itm_cm);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homebuttons, menu);
      MenuItem itemMessages = menu.findItem(R.id.menu_messages);
       // menuSearch.setActionView(R.layout.badgelayout);

       RelativeLayout badgeLayout = (RelativeLayout) itemMessages.getActionView();
       text2=(TextView)badgeLayout.findViewById(R.id.badge_textView2);
        text2.setText("!");
        text2.setVisibility(View.VISIBLE);
        iconButtonCart = (IconButton) badgeLayout.findViewById(R.id.badge_icon_button2);
        iconButtonSearch = (IconButton) badgeLayout.findViewById(R.id.badge_icon_button1);
        SearchText=(EditText)badgeLayout.findViewById(R.id.SearchText);
        SearchText.setVisibility(View.GONE);
        iconButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 SearchText.setVisibility(View.VISIBLE);
          //      iconButtonCart.setVisibility(View.GONE);
                //iconButtonSearch.setVisibility(View.GONE);

            //    SearchText.setVisibility(View.VISIBLE);
             //   text2.setVisibility(View.GONE);
                String Search=SearchText.getText().toString().trim();

                if(!Search.isEmpty()) {
                    Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                    i.putExtra("SearchValue",Search);
                    startActivity(i);
                }
            }
        });

        iconButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(getApplicationContext(),Cart.class);
                startActivity(i);
            }
        });



        return true;
    }

    private int addToList()
    {
        int posi=0;
        String p[]={"Logo","Videos","My Orders","My Account","Chat With Us","Logout"};
        parentList = new ArrayList<>();
        childList = new ArrayList<>();
        DrawerParentModel item=null;
    // for 1st item
        item = new DrawerParentModel();
        item.setTitle(p[0]);
        item.setPosition(0);
        parentList.add(item);
        pob.add(item);
        String[] c1={"Versatile","Professional","Cartoon"};
        for(int i=0;i<c1.length;i++)
        {
            item1 = new DrawerChildModel();
            item1.setMstr(c1[i]);childList.add(item1);
        }
        item.setChildObjectList(childList);
    // for 2nd item
        item = new DrawerParentModel();
        item.setTitle(p[1]);
        item.setPosition(1);
        parentList.add(item);
        pob.add(item);
        String[] c2={"Explainer Videos","Developer Videos","Whiteboard Videos"};
        for(int i=0;i<c2.length;i++)
        {
            item1 = new DrawerChildModel();
            item1.setMstr(c2[i]);childList1.add(item1);
        }
        item.setChildObjectList(childList1);
    //
        item = new DrawerParentModel();
        item.setTitle(p[2]);
        item.setPosition(2);
        parentList.add(item);
        pob.add(item);
        //
        item = new DrawerParentModel();
        item.setTitle(p[3]);
        item.setPosition(3);
        parentList.add(item);
        pob.add(item);
        //
        item = new DrawerParentModel();
        item.setTitle(p[4]);
        item.setPosition(4);
        parentList.add(item);
        pob.add(item); item = new DrawerParentModel();
        item.setTitle(p[5]);
        item.setPosition(5);
        parentList.add(item);
        pob.add(item);


        posi= item.getPosition();
        return posi;
    }

    public void init()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_home));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_logo));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_video));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mrecycler= (RecyclerView) findViewById(R.id.drawer_recycle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mrecycler.setLayoutManager(mLayoutManager);

    }

    public void bindToviewPager()
    {
        customPagerAdapter=new CustomPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(customPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                 position=tab.getPosition();
                Intent i= new Intent(getApplicationContext(),Home.class);
                i.putExtra("pos",position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    public void expandableList()
    {
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        //mExpandableListView.addHeaderView(listHeaderView);
        setExpandableRecycler();//   addDrawerItems();
    }

    private void addDrawerItems() {
     //mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle(R.string.film_genres);
            }
       });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition))))
                        .get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);

                if (items[0].equals(mExpandableListTitle.get(groupPosition))) {
             //       Toast.makeText(v.getContext(), "Actvity 10", Toast.LENGTH_SHORT).show();
                } else if (items[1].equals(mExpandableListTitle.get(groupPosition))) {
                   // mNavigationManager.showFragmentComedy(selectedItem);
                } else if (items[2].equals(mExpandableListTitle.get(groupPosition))) {
                  //  mNavigationManager.showFragmentDrama(selectedItem);
                } else if (items[3].equals(mExpandableListTitle.get(groupPosition))) {
                  //  mNavigationManager.showFragmentMusical(selectedItem);
                } else if (items[4].equals(mExpandableListTitle.get(groupPosition))) {
                  //  mNavigationManager.showFragmentThriller(selectedItem);
                } else {
                    throw new IllegalArgumentException("Not supported fragment type");
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private ArrayList<ParentObject> generateCrimes() {
       /*// CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<DrawerParentModel> parentlist = new ArrayList<>();
        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        for (DrawerParentModel crime : crimes) {
            ArrayList<Object> childList = new ArrayList<>();
            childList.add(new CrimeChild(crime.getDate(), crime.isSolved()));
            crime.setChildObjectList(childList);
            parentObjects.add(crime);
        }*/


        DrawerParentModel  item = new DrawerParentModel();
        List<Object> childlist = new ArrayList<Object>();
        List<Object> parentlist = new ArrayList<Object>();

       /* for(int i=0;i<5;i++)
        {
            //DrawerParentModel parentModel=new DrawerParentModel();
           // parentModel.setTitle("Title"+i);
           // parentlist.add(parentModel);
            DrawerChildModel childModel=new DrawerChildModel();
            childModel.setMstr("my str"+i);
            childlist.add(childModel);


        }

*/
        //  item.setChildObjectList(childlist);
        // pob.add(item);



        return (ArrayList<ParentObject>) pob;
    }

    public void checkforLogin(){
        sh1= getSharedPreferences("LoginAuth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh1.edit();
        Boolean log=sh1.getBoolean("LoginAuth",true);

        if(!log){
            Intent i1=new Intent(Home.this,LoginActivity.class);
            startActivity(i1);
        }

    }

    public void setExpandableRecycler()
    {
        DrawerExpandableAdapter drawerExpandableAdapter = new DrawerExpandableAdapter(this, pob);
        drawerExpandableAdapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        drawerExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        drawerExpandableAdapter.setParentAndIconExpandOnClick(true);
        mrecycler.setAdapter(drawerExpandableAdapter);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
