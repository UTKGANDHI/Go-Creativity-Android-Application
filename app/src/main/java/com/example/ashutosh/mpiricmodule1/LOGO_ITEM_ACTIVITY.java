package com.example.ashutosh.mpiricmodule1;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ashutosh.fragments.VideoFragment;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Models.Util1;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class LOGO_ITEM_ACTIVITY extends AppCompatActivity implements  AsyncRequest.OnAsyncRequestComplete{
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private List<Card> albumList;
    public static String purl;
    int bindpos,pd;
    ImageView iv;URL url1;
    //ArrayList<String> AnimationArray,AnimationArray1,DesignArray,DesignArray1;
    static ArrayList<Integer>prodIds=new ArrayList<Integer>();
    static ArrayList<String>prodName=new ArrayList<String>();
    static ArrayList<String>prodPrice=new ArrayList<String>();
    static ArrayList<String>clist=new ArrayList<String>();
    public static String response1=null;
    public static final String VIDEO_ID = "CkQU7IZaqm4";
  //  public static final String thumb="https://img.youtube.com/vi/"+VIDEO_ID+"/default.jpg";
    static Boolean buyflag=false,pidlogo=false;
    public static final String API_KEY = "AIzaSyBtCAYeZSAsmDRQH_wtN_UPdjWaeGLHpNc";
    final String names[] = {"High to Low", "Low to High"};
    boolean isImageFitToScreen=false;
    RelativeLayout reo;
    Set<String> set2 = new HashSet<String>();
    AlertDialog alertDialog;
    SharedPreferences sh1,cart;SharedPreferences sh2;
    String apiURL=Util1.GET_VERSATILELOGO_API;
    Button FAB,Buy,Cart,SeeDetails;
    RelativeLayout reo2,reo1,reo3;
    ArrayList<String> filter;

    int s3; int f1;
    public static String cartelement;
    //public static final String VIDEO_ID = "bzSTpdcs-EI";
    public  String thumb,url,description;
    static String Email,mEmail1;
    DatabaseHandler db = new DatabaseHandler(this);
    //AlertDialog alertDialog;
    LinearLayout FilterLayout;
    //Button FAB,Buy,Cart,SeeDetails;
    String stprice,s1,s2;
    Set<String> SharedPreferenceSet;
    //SharedPreferences sh1,cart;
    //  SharedPreferences sh2;
    ArrayList<String> Filterprice;
    AsyncRequest getPosts;
    ArrayList<String> AnimationArray,AnimationArray1,DesignArray,DesignArray1;
    String name;
    int price,prod_id,cateid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo__item__activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv=(ImageView)findViewById(R.id.backdrop);
        setSupportActionBar(toolbar);
//        initCollapsingToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reo=(RelativeLayout)findViewById(R.id.reo);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // floating button for filter
        Buy=(Button)findViewById(R.id.buy);
        Cart=(Button)findViewById(R.id.cart);

        AnimationArray=new ArrayList<>();
        AnimationArray1=new ArrayList<>();
        DesignArray=new ArrayList<>();
        DesignArray1=new ArrayList<>();

        FAB = (Button) findViewById(R.id.imageButton);

        SeeDetails=(Button)findViewById(R.id.expandableButton2);
        Intent i=getIntent();
        pd=i.getIntExtra("p",0);

        SeeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Details.class);
                i.putExtra("p",pd);
                startActivity(i);

            }
        });
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LOGO_ITEM_ACTIVITY.this,Filter.class);
                i.putExtra("Type","Logos");
                startActivity(i);
            }
        });


        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                int pid=i.getIntExtra("p",0);
                String name1=i.getStringExtra("name");
                String price=i.getStringExtra("price");

                Intent i1=new Intent(getApplicationContext(),Cart.class);

                if (prodIds.size()==0 || prodName.size()==0 || prodPrice.size()==0) {
                    prodIds.add(pid);prodName.add(name1);prodPrice.add(price);
                    clist.clear();
                    clist.add(String.valueOf(pid));
                    Set<String> set1 = new HashSet<String>();

                    cart = getSharedPreferences("CartElement", Context.MODE_PRIVATE);
                    set2 = cart.getStringSet("CartElement1", null);
                    if (set2 == null) {
                        set2 = new HashSet<String>();
                        set2.addAll(clist);
                    }
                    else {
                        set2.addAll(clist);
                    }
                    SharedPreferences.Editor editor = cart.edit();
                    editor.putStringSet("CartElement1",set2);
                    editor.commit();
                }
                else {
                    for(int j=0;j<prodIds.size();j++) {
                        if( !prodIds.get(j).equals(pid))
                        {
                            //prodIds.add(pid);prodName.add(name1);prodPrice.add(price);
                            clist.clear();
                            clist.add(String.valueOf(pid));
                            Set<String> set1 = new HashSet<String>();//Set<String> set2 = new HashSet<String>();
                            // set1.addAll(clist);
                            cart= getSharedPreferences("CartElement", Context.MODE_PRIVATE);
                            set2=cart.getStringSet("CartElement1",null);
                            if(set2==null){set2.addAll(clist);}
                            else{
                                set2.addAll(clist);
                            }
                            SharedPreferences.Editor editor = cart.edit();
                            editor.putStringSet("CartElement1",set2);
                            editor.commit();
                        }
                        if(prodIds.size()==1 && prodIds.get(j).equals(pid))
                        {
                            clist.clear();
                            clist.add(String.valueOf(pid));
                            Set<String> set1 = new HashSet<String>();//Set<String> set2 = new HashSet<String>();
                            // set1.addAll(clist);
                            cart= getSharedPreferences("CartElement", Context.MODE_PRIVATE);
                            set2=cart.getStringSet("CartElement1",null);
                            if(set2==null) {
                                set2.addAll(clist);
                            }
                            else{
                                set2.addAll(clist);
                            }
                            SharedPreferences.Editor editor = cart.edit();
                            editor.putStringSet("CartElement1",set2);
                            editor.commit();
                        }
                    }
                }
                i1.putIntegerArrayListExtra("pid",prodIds);
                purl=prepareCart(set2);
                // dialog box
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseContext());
                alertDialog.setTitle("Confirm Item");
                Intent i2=getIntent();
                bindpos=i2.getIntExtra("bindpos",0);

//                String s=CardAdapter.cardList.get(bindpos).getNumOfSongs().toString();
//                String itemname=CardAdapter.cardList.get(bindpos).getName();
//                int pr=CardAdapter.cardList.get(bindpos).getNumOfSongs();
//                int productid=CardAdapter.cardList.get(bindpos).getProd_id();
                String s=CardAdapter.c.getNumOfSongs().toString();
                String itemname=CardAdapter.c.getName();
                int pr=CardAdapter.c.getNumOfSongs();
                int productid=CardAdapter.c.getProd_id();
                buydialog(LOGO_ITEM_ACTIVITY.this, getApplicationContext(),"Confirm Item" , "Name: "+itemname+"\n"+"Price: "+"$"+s ,pr,productid,itemname);
                buyflag=true;
            }
        });
        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                int pid=i.getIntExtra("p",0);
                String name1=i.getStringExtra("name");
                String price=i.getStringExtra("price");

                Intent i1=new Intent(getApplicationContext(),Cart.class);

                if (prodIds.size()==0 || prodName.size()==0 || prodPrice.size()==0) {
                    prodIds.add(pid);prodName.add(name1);prodPrice.add(price);
                    clist.clear();
                    clist.add(String.valueOf(pid));
                    Set<String> set1 = new HashSet<String>();
                    Set<String> set2 = new HashSet<String>();


                    cart = getSharedPreferences("CartElement", Context.MODE_PRIVATE);
                    set2 = cart.getStringSet("CartElement1", null);
                    if (set2 == null) {
                        set2 = new HashSet<String>();
                        set2.addAll(clist);
                    }
                    else {
                        set2.addAll(clist);
                    }
                    SharedPreferences.Editor editor = cart.edit();
                    editor.putStringSet("CartElement1",set2);
                    editor.commit();
                }
                else {
                    for(int j=0;j<prodIds.size();j++) {
                        if( !prodIds.get(j).equals(pid))
                        {
                            prodIds.add(pid);
                            prodName.add(name1);
                            prodPrice.add(price);
                            clist.clear();
                            clist.add(String.valueOf(pid));
                            Set<String> set1 = new HashSet<String>();
                            Set<String> set2 = new HashSet<String>();
                            // set1.addAll(clist);
                            cart= getSharedPreferences("CartElement", Context.MODE_PRIVATE);

                            set2=cart.getStringSet("CartElement1",null);
                            if(set2==null){set2.addAll(clist);}
                            else{
                                set2.addAll(clist);
                            }
                            SharedPreferences.Editor editor = cart.edit();
                            editor.putStringSet("CartElement1",set2);
                            editor.commit();
                        }
                    }
                }
                i1.putIntegerArrayListExtra("pid",prodIds);
                Toast.makeText(getApplicationContext(),"Added to Cart",Toast.LENGTH_LONG).show();

            }
        });

        Button sort=(Button)findViewById(R.id.imageButton1);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSortDialog();
            }
        });


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent i23=getIntent();
        String Image =i23.getStringExtra("url") ;
        try {
            Glide.with(this).load(Image).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
        reo2=(RelativeLayout)findViewById(R.id.reo2);
        reo1=(RelativeLayout)findViewById(R.id.reo);
        reo3=(RelativeLayout)findViewById(R.id.main_content);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    reo.animate().translationY(0);
                    reo2.animate()
                            .translationY(0)
                            .alpha(1.0f);


                    reo3.setBackgroundColor(Color.WHITE);
                  //  iv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                    iv.setAdjustViewBounds(true);
                }else{
                    isImageFitToScreen=true;
                  //  reo2.setLayoutParams(new RelativeLayout.LayoutParams(500, LinearLayout.LayoutParams.WRAP_CONTENT));
                    reo2.animate()
                            .translationY(reo2.getHeight())
                            .alpha(1.0f);
                    reo.animate().translationY(450);
                    reo3.setBackgroundColor(Color.BLACK);
            //        iv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
              //      iv.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });

        AsyncRequest getPosts = new AsyncRequest(this, "GET");
        getPosts.execute(apiURL);
    }


    public AlertDialog buydialog(Activity activity, final Context context, String title, String message, final int pr, final int productid, final String itemname){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        int cost=pr;
        //  builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent i=new Intent(getApplicationContext(),Payment.class);
                i.putExtra("price",pr);
                i.putExtra("productid",productid);
                i.putExtra("itemname",itemname);
                startActivity(i);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        builder.show();

        return builder.create();
    }


    /*private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }*/

    public String prepareCart(Set<String> s) {
        String pidurl;

        pidurl = "";
        List<String> list = new ArrayList<String>(s);

        for (int i = 0; i < list.size(); i++) {
            pidurl = "'" + list.get(i) + "'," + pidurl;
        }
        pidurl = pidurl.substring(0, pidurl.length() - 1);
        pidlogo=true;
        V_play.pidvideo=false;
        return pidurl;

    }
    @Override
    public void asyncResponse(String response) {
        try {
            // create a JSON array from the response string
            JSONArray  objects = new JSONArray(response);
             filter=Checkforfilter();
            if(filter!=null){
                f1=filterbyprice(filter);
                stprice=Integer.toString(f1);
                //    AnimationArray1=FilterDesign(filter);
                DesignArray1=FilterDesign(filter);
                //Toast.makeText(getApplicationContext(),"FilterDesign:"+DesignArray1.toString(),Toast.LENGTH_LONG).show();

            }
            response1=response;
            for (int i = 0; i < objects.length(); i++)
            {
                String type="Logo";
                JSONObject object = (JSONObject) objects.getJSONObject(i);
                int price=object.getInt("prod_price");
                int prodid=object.getInt("prod_id");
                String tags=object.getString("prod_tags");
                String[] Tags1=tags.split("\\,");
                List<String> TagList= Arrays.asList(Tags1);
                String name=object.getString("prod_name");
                String url=object.getString("prod_demourl");
                int cateid = object.getInt("prod_subcateid");
                if(DesignArray1.isEmpty()) {
                    if (Home.cid.get(CartAdapter.pos_logo)==cateid) {
                        //  Card a = new Card(name,price, thumb,type,prod_id,mEmail1,url);
                        Card a = new Card(name,price, url,type,prodid,"",url);
                        if(price<=f1 && f1!=0){
                            albumList.add(a);}
                        else if(stprice==null ||stprice.equals("5")|| filter.toString().contains("5")){albumList.add(a);}
                        adapter = new CardAdapter(this, albumList,2);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }
                }
                else if(!DesignArray1.isEmpty()){
                    if(CollectionUtils.containsAny(TagList,DesignArray1)){
                        //  Card a = new Card(name,price, thumb,type,prod_id,mEmail1,url);
                        Card a = new Card(name,price, url,type,prodid,"",url);
                        if(price<=f1 && f1!=0){
                            albumList.add(a);}
                        else if(stprice==null ||stprice.equals("5")||filter.toString().contains("5")){albumList.add(a);}
                        adapter = new CardAdapter(this, albumList,2);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);

                    }
                }

               else
                    continue;

            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String> Checkforfilter()
    {

        Intent intent = getIntent();
        if(intent!=null)
        {
            Filterprice = (ArrayList<String>)intent.getSerializableExtra("Filter");
           // Toast.makeText(getApplicationContext(), "FilterPrice:" + Filterprice, Toast.LENGTH_LONG).show();

        }
        else if(intent==null){
            sh1 = getApplicationContext().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
            SharedPreferenceSet=sh1.getStringSet("HashSet",null);
            Filterprice=new ArrayList<String>(SharedPreferenceSet);
           //Toast.makeText(getApplicationContext(),"StoredPreferenceSet:"+Filterprice,Toast.LENGTH_LONG).show();

        }return Filterprice;}

    public int filterbyprice(ArrayList<String> pricefilter)
    {    if(pricefilter!=null){
        for(int i=0;i<pricefilter.size();i++)
        {    s1=pricefilter.get(i);
            if(s1.contains("Price")){
                s2=s1;
                s2=s1.substring(s1.lastIndexOf("=")+1);
                s3= Integer.parseInt(s2);
                if(s2==null)
                {
                    s2="0";
                }
            }

        }

    }return s3;
    }

    public ArrayList<String> FilterDesign(ArrayList<String> pricefilter)
    {    if(pricefilter!=null){
        for(int i=0;i<pricefilter.size();i++)
        {    s1=pricefilter.get(i);
            if(s1.contains("Designs")){
                s2=s1;
                s2=s1.substring(s1.lastIndexOf("=")+1);
                DesignArray.add(s2);

            }

        }

    }return DesignArray;
    }
    public ArrayList<String> FilterCategory(ArrayList<String> pricefilter)
    {    if(pricefilter!=null){
        for(int i=0;i<pricefilter.size();i++)
        {    s1=pricefilter.get(i);
            if(s1.contains("Animations")){
                s2=s1;
                s2=s1.substring(s1.lastIndexOf("=")+1);
                AnimationArray.add(s2);

            }

        }

    }return AnimationArray;
    }
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),Versatile_logo.class);
        startActivity(i);
    }

    // for collapse/ expand
    //  ExpandableRelativeLayout expandableLayout1;
    public void expandableButton1(View view) {
        Intent i = new Intent(LOGO_ITEM_ACTIVITY.this,Details.class);
        startActivity(i);
    }

    public void SortAscending(String response1, String index)
    {
        final String pos=index;
        albumList.clear();
        final ArrayList<Card> List1=new  ArrayList<Card>();
        try{
            JSONArray object1=new JSONArray(response1);

            for(int i=0;i<object1.length();i++)
            {
                Card mycard=new Card();
                JSONObject ob2=object1.getJSONObject(i);
                String url=ob2.getString("prod_demourl");
                int cateid=ob2.getInt("prod_subcateid");
                int prodid=ob2.getInt("prod_id");
                String tags=ob2.getString("prod_tags");
                String[] Tags1=tags.split("\\,");
                List<String> TagList= Arrays.asList(Tags1);
                String type=ob2.getString("prod_type");
                mycard.setName(ob2.getString("prod_name"));
                mycard.setNumOfSongs(ob2.getInt("prod_price"));
                mycard.setThumbnail(url);
                mycard.setProd_id(prodid);
                mycard.setType(type);
                mycard.setUrl(url);
                if(DesignArray1.isEmpty()){
                    if (Home.cid.get(VideoFragment.pos_logo)==cateid){
                        List1.add(mycard);}}
                else if(!DesignArray1.isEmpty()){
                    if(CollectionUtils.containsAny(TagList,DesignArray1)){
                        List1.add(mycard);
                    }}
            }
            Log.e("size", String.valueOf(List1.size()));


        }catch(JSONException e){
            e.printStackTrace();
        }

        Collections.sort(List1, new Comparator<Card>() {
            @Override
            public int compare(Card lhs, Card rhs) {
                int x = 0;
                try {
                    int p1 = lhs.getNumOfSongs();
                    int p2 = rhs.getNumOfSongs();
                    if(pos.equals("1")){
                        x = ((Integer) p1).compareTo(p2);}
                    else{
                        x = ((Integer) p2).compareTo(p1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // return -1;
                }
                return x;}
        });
        albumList.addAll(List1);
        adapter.notifyDataSetChanged();
        alertDialog.dismiss();

    }

    public void openSortDialog() {

        alertDialog = new AlertDialog.Builder(LOGO_ITEM_ACTIVITY.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.custom, null);
        ImageView i1 = (ImageView) convertView.findViewById(R.id.imageView_close);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(convertView);
        alertDialog.setCancelable(true);
        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        lv.setAdapter(adapter);
        alertDialog.show();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String indexid = String.valueOf(position);
                if (indexid.equals("1") || indexid.equals("0")) {
                    SortAscending(response1, indexid);
                }

            }
        });
    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
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

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            //Intent myintent=new Intent(this,HelloCardActivity.class)
        }
        return super.onOptionsItemSelected(item);
    }
}
