package com.example.ashutosh.mpiricmodule1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ashutosh.fragments.VideoFragment;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
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

public class ALL_VIDEOS extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, AsyncRequest.OnAsyncRequestComplete{



    String selectedText;
    int cateid1;

    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private List<Card> albumList;
    int s3; int f1,bindpos;
    public static final String API_KEY = "AIzaSyBtCAYeZSAsmDRQH_wtN_UPdjWaeGLHpNc";
    public String VID="";public String VID1="";public String VID2="";public String VIDEOID="";
    public static String cartelement,purl;
    public static final String VIDEO_ID = "bzSTpdcs-EI";
    public  String thumb,url,url1,description;
    static String Email,mEmail1;
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
    ArrayList<String> Filterprice;
    String apiURL=Util1.GET_EXPLAINERVIDEO_API;
    int keyCode;
    AsyncRequest getPosts;
    ArrayList<String> AnimationArray,AnimationArray1,DesignArray,DesignArray1;
    String name;
    int price,prod_id,cateid;
    ArrayList<String> filter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__videos);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AnimationArray=new ArrayList<>();
        AnimationArray1=new ArrayList<>();
        DesignArray=new ArrayList<>();
        DesignArray1=new ArrayList<>();
        mEmail1=GetEmailCredential();
//        try {
//            Glide.with(this).load(R.drawable.album1).into((ImageView) findViewById(R.id.backdrop));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Intent i= getIntent();
        if(i!=null) {
            selectedText = i.getStringExtra("Parameter");
            cateid1 = i.getIntExtra("Cateid", 0);
           // Toast.makeText(getApplicationContext(), "Selected Text:" + cateid1, Toast.LENGTH_LONG).show();

            //cateid2=Integer.valueOf(cateid1);}
            // Toast.makeText(getApplicationContext(),"Selected Text:"+cateid2,Toast.LENGTH_LONG).show();

        }
        // video playing
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(API_KEY, this);
        // floating button for filter

        FAB = (Button) findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ALL_VIDEOS.this,Filter.class);
                i.putExtra("Type","Videos");
                startActivity(i);
            }
        });

        Button sort=(Button)findViewById(R.id.imageButton1);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSortDialog();
            }
        });

        AsyncRequest getPosts = new AsyncRequest(this, "GET");
        getPosts.execute(apiURL);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
//
//    public void init()
//    {
//
//
//        viewPager = (ViewPager) findViewById(R.id.pager);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
//        mDrawerLayout.setDrawerListener(toggle);
//        toggle.syncState();
//        mrecycler= (RecyclerView) findViewById(R.id.drawer_recycle);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        mrecycler.setLayoutManager(mLayoutManager);
//
//    }
public ArrayList<String> FilterDesign(ArrayList<String> pricefilter)
{    if(pricefilter!=null){
    for(int i=0;i<pricefilter.size();i++)
    {    s1=pricefilter.get(i);
        if(s1.contains("Design")){
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

    @Override
    public void asyncResponse(String response) {
        try {
            // create a JSON array from the response string
            JSONArray  objects = new JSONArray(response);
             filter=Checkforfilter();
            if(filter!=null){
                f1=filterbyprice(filter);
                stprice=Integer.toString(f1);
                AnimationArray1=FilterCategory(filter);
                DesignArray1=FilterDesign(filter);
               // Toast.makeText(getApplicationContext(),"FilterAnimation:"+AnimationArray1.toString(),Toast.LENGTH_LONG).show();

            }
            response1=response;
            for (int i = 0; i < objects.length(); i++)
            {
                String type="Videos";
                JSONObject object = (JSONObject) objects.getJSONObject(i);
                String tags=object.getString("prod_tags");
                String[] Tags1=tags.split("\\,");
                List<String> TagList= Arrays.asList(Tags1);
                price = object.getInt("prod_price");
                description = object.getString("prod_description");
                name = object.getString("prod_name");
                url = object.getString("prod_demourl");
                prod_id = object.getInt("prod_id");
                cateid = object.getInt("prod_subcateid");
                VIDEOID = url.substring(url.lastIndexOf("?") + 1);
                VID = (VIDEOID.substring(VIDEOID.indexOf("=") + 1));
                if (VID.contains("&")) {
                    String[] VID1 = VID.split("\\&");
                    VID = VID1[0];
                }
                thumb = "https://img.youtube.com/vi/" + VID + "/mqdefault.jpg";
                //  TagList.add("Animation");
                if(AnimationArray1.isEmpty()) {
                    if (Home.vid.get(VideoFragment.pos_video)==cateid) {
                        Card a = new Card(name,price, thumb,type,prod_id,mEmail1,url);
                        if(price<=f1 && f1!=0){
                            albumList.add(a);}
                        else if(stprice==null ||stprice.equals("5")||filter.toString().contains("5")){albumList.add(a);}
                        adapter = new CardAdapter(this, albumList,2);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }
                }
                else if(!AnimationArray1.isEmpty()){
                    if(CollectionUtils.containsAny(TagList,AnimationArray1)){
                        if (Home.vid.get(VideoFragment.pos_video)==cateid) {
                            Card a = new Card(name,price, thumb,type,prod_id,mEmail1,url);
                            if(price<=f1 && f1!=0){
                                albumList.add(a);}
                            else if(stprice==null ||stprice.equals("5")||filter.toString().contains("5")){albumList.add(a);}
                            adapter = new CardAdapter(this, albumList,2);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        }
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
    @Override
    public void onBackPressed() {

        Intent i=new Intent(getApplicationContext(),Home.class);
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
                int prodid=ob2.getInt("prod_id");
                int price=ob2.getInt("prod_price");
                String tags=ob2.getString("prod_tags");
                String[] Tags1=tags.split("\\,");
                List<String> TagList=Arrays.asList(Tags1);
                cateid = ob2.getInt("prod_subcateid");
                mycard.setName(ob2.getString("prod_name"));
                mycard.setNumOfSongs(ob2.getInt("prod_price"));
                String url=ob2.getString("prod_demourl");
                String type="Videos";
                VIDEOID=url.substring(url.lastIndexOf("?")+1);
                VID=(VIDEOID.substring(VIDEOID.indexOf("=")+1));
                if(VID.contains("&"))
                {
                    String[] VID1=VID.split("\\&");
                    VID=VID1[0];
                    //  Toast.makeText(getApplicationContext(),"Url:"+VID,Toast.LENGTH_LONG).show();
                }
                thumb="https://i.ytimg.com/vi/"+VID+"/maxresdefault.jpg";
                mycard.setThumbnail(thumb);
                mycard.setType(type);
                mycard.setUrl(url);
                mycard.setProd_id(prodid);
                if(AnimationArray1.isEmpty()){
                    if (Home.vid.get(VideoFragment.pos_video)==cateid){
                        List1.add(mycard);}}
                else if(!AnimationArray1.isEmpty()){
                    if(CollectionUtils.containsAny(TagList,AnimationArray1)){
                        List1.add(mycard);
                    }
                }
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




    public String GetEmailCredential(){

        sh1= getSharedPreferences("LoginAuth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh1.edit();
        Email=sh1.getString("Email","");
        return Email;
    }

    public void openSortDialog() {

        alertDialog = new AlertDialog.Builder(ALL_VIDEOS.this).create();
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo("zIGCfRpjw-w");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

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
