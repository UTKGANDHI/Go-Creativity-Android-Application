package com.example.ashutosh.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashutosh.ItemDecoration.DividerItemDecoration;
import com.example.ashutosh.mpiricmodule1.AsyncRequest;
import com.example.ashutosh.mpiricmodule1.Card;
import com.example.ashutosh.mpiricmodule1.CardAdapter;
import com.example.ashutosh.mpiricmodule1.CartAdapter;
import com.example.ashutosh.mpiricmodule1.Home;
import com.example.ashutosh.mpiricmodule1.HttpHandler;
import com.example.ashutosh.mpiricmodule1.LOGO_ITEM_ACTIVITY;
import com.example.ashutosh.mpiricmodule1.R;
import com.bumptech.glide.Glide;
import com.example.ashutosh.mpiricmodule1.V_play;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.Util1;



/**
 * Created by Admin on 12/20/2016.
 */

public class HomeFragment extends Fragment implements AsyncRequest.OnAsyncRequestComplete {
    private AlbumsAdapter adapter;
    private CardAdapter adapter1;
    public  List<Card> homelist1 = new ArrayList<>();
    private List<Card> albumList1=new ArrayList<>();
    public ArrayList<String> urlList2=new ArrayList<>();
    public  List<String> vlist = new ArrayList<>();

    RecyclerView recyclerView_home,recyclerView_home2 ;
    String apiURL=Util1.GET_ALLCATEGORY_EPIC;
    Context context;
    public String VID="";
    public String VID1="";
    public String VID2="";
    public String VIDEOID="";
    public  String thumb,url,url1;
    int prod_id;
    String demo_url,url2;int bindpos;
    String apiURL1=Util1.GET_SUBVIDEOLIST_API;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        recyclerView_home= (RecyclerView) view.findViewById(R.id.recycler_home);
        recyclerView_home2= (RecyclerView) view.findViewById(R.id.recycler_home2);
//        AsyncRequest getPosts = new AsyncRequest(getActivity(), "GET");
//        getPosts.execute(apiURL1);

        LinearLayoutManager horizontalLayoutmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayoutmanager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView_home.setLayoutManager(horizontalLayoutmanager);
        recyclerView_home.setItemAnimator(new DefaultItemAnimator());
    //    fillList();
        setThisToAdapter();


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView_home2.setLayoutManager(mLayoutManager);
        recyclerView_home2.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView_home2.setItemAnimator(new DefaultItemAnimator());
        // For vertical item
        new MyTask().execute();

        return view;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(apiURL);
            try {
                // create a JSON array from the response string
                JSONArray objects = new JSONArray(jsonStr);
                homelist1.clear();
                for (int i = 0; i < objects.length(); i++) {
                    JSONObject object = (JSONObject) objects.getJSONObject(i);
                    String name = object.getString("prod_name");
                    int price=object.getInt("prod_price");
                    String type=object.getString("prod_type");
                    if(type.equals("Video")){
                    url=object.getString("prod_demourl");
                     prod_id=object.getInt("prod_id");
                    VIDEOID=url.substring(url.lastIndexOf("?")+1);
                    VID=(VIDEOID.substring(VIDEOID.indexOf("=")+1));
                    if(VID.contains("&"))
                    {
                        String[] VID1=VID.split("\\&");
                        VID=VID1[0];
                    }
                    thumb="https://img.youtube.com/vi/"+VID+"/mqdefault.jpg";
                    Card b = new Card(name, price, thumb, type,prod_id,"",url);
                    homelist1.add(b);}else if(type.equals("Logo"))
                    {
                         demo_url=object.getString("prod_demourl");
                        urlList2.add(demo_url);
                 //       Toast.makeText(getContext(),"urlList1:"+urlList1,Toast.LENGTH_LONG).show();
                        Card a = new Card(name, price,demo_url, type,prod_id,"",demo_url);
                        homelist1.add(a);

                    }
                }fillList();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            adapter1 = new CardAdapter(getActivity(), homelist1,2);
            recyclerView_home2.setAdapter(adapter1);
        }

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void fillList() {
            for (int i = 0; i < Home.homelist.size(); i++) {
                String name=Home.homelist.get(i).getName();
                String url_name=Home.homelist.get(i).getUrl();
                String type=Home.homelist.get(i).getType();
                int price=Home.homelist.get(i).getNumOfSongs();
                int prid=Home.homelist.get(i).getProd_id();
                Card b = new Card(name, price, " ", type, prid, "", url_name);
                albumList1.add(b);
      //          urlList1.add(Home.homelist.get(i).getUrl());
            //    Toast.makeText(getContext(),"urlList1:"+urlList1,Toast.LENGTH_LONG).show();
            }
    }

    public void setThisToAdapter()
    {
        this.url2=url2;
        adapter = new AlbumsAdapter(getActivity(),albumList1,Home.name_home,urlList2);
        recyclerView_home.setAdapter(adapter);
    }

    public void fillList1()
    {
        for(int i = 0; i< homelist1.size(); i++)
        {
            vlist.add(homelist1.get(i).getName());
        }
        adapter1 = new CardAdapter(getActivity(), homelist1,2);
        recyclerView_home2.setAdapter(adapter1);
    }

    @Override
    public void asyncResponse(String response)
    {
        try {
            // create a JSON array from the response string
            JSONArray objects = new JSONArray(response);

            for (int i = 0; i < objects.length(); i++)
            {
                JSONObject object = (JSONObject) objects.getJSONObject(i);
                String name = object.getString("sub_name");
                int price=object.getInt("sub_id");
                Card b = new Card(name, price, " ", "",null,"","");
                homelist1.add(b);
                fillList1();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
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


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

        private Context mContext;
        private List<Card> albumList;
        public ArrayList<String> list;
        public ArrayList<String> urlList1;
        String url;
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tv, count;
            public ImageView thumbnail1, overflow;


            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv);
                thumbnail1= (ImageView) view.findViewById(R.id.iv);

                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        bindpos=getAdapterPosition();
                        String url1=albumList.get(getAdapterPosition()).getUrl();
                        String type=albumList.get(getAdapterPosition()).getType();

                        if(type.equals("Videos" )|| type.equals("Video")) {
                            Intent i = new Intent(v.getContext(), V_play.class);
                            i.putExtra("url",url1);
                            i.putExtra("bindpos",bindpos);
                            // int cardpos=cardList.get(getAdapterPosition());
                            int p=albumList.get(getAdapterPosition()).getProd_id();
                            String name=albumList.get(getAdapterPosition()).getName();
                            String price=albumList.get(getAdapterPosition()).getNumOfSongs().toString();
                            i.putExtra("p",p);
                            i.putExtra("name",name);
                            i.putExtra("price",price);
                            v.getContext().startActivity(i);
                        }
                        else if(type.equals("Logo")) {
                            Intent i = new Intent(v.getContext(), LOGO_ITEM_ACTIVITY.class);
                            i.putExtra("url",url1);
                            i.putExtra("bindpos",bindpos);
                            int p=albumList.get(getAdapterPosition()).getProd_id();
                            String name=albumList.get(getAdapterPosition()).getName();
                            String price=albumList.get(getAdapterPosition()).getNumOfSongs().toString();
                            i.putExtra("p",p);
                            i.putExtra("name",name);
                            i.putExtra("price",price);
                            v.getContext().startActivity(i);
                        }
                    }
                });
                thumbnail1.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        bindpos=getAdapterPosition();
                        String url1=albumList.get(getAdapterPosition()).getUrl();
                        String type=albumList.get(getAdapterPosition()).getType();

                        if(type.equals("Videos" )|| type.equals("Video")) {
                            Intent i = new Intent(v.getContext(), V_play.class);
                            i.putExtra("url",url1);
                            i.putExtra("bindpos",bindpos);
                            // int cardpos=cardList.get(getAdapterPosition());
                            int p=albumList.get(getAdapterPosition()).getProd_id();
                            String name=albumList.get(getAdapterPosition()).getName();
                            String price=albumList.get(getAdapterPosition()).getNumOfSongs().toString();
                            i.putExtra("p",p);
                            i.putExtra("name",name);
                            i.putExtra("price",price);
                            v.getContext().startActivity(i);
                        }
                        else if(type.equals("Logo")) {
                            Intent i = new Intent(v.getContext(), LOGO_ITEM_ACTIVITY.class);
                            i.putExtra("url",url1);
                            i.putExtra("bindpos",bindpos);
                            int p=albumList.get(getAdapterPosition()).getProd_id();
                            String name=albumList.get(getAdapterPosition()).getName();
                            String price=albumList.get(getAdapterPosition()).getNumOfSongs().toString();
                            i.putExtra("p",p);
                            i.putExtra("name",name);
                            i.putExtra("price",price);
                            v.getContext().startActivity(i);
                        }
                    }
                });

            }
        }

        public AlbumsAdapter(Context mContext, List<Card> albumList,ArrayList<String> list,ArrayList<String>urlList1) {
            this.mContext = mContext;
            this.albumList = albumList;
            this.list=list;
            this.urlList1=urlList1;
            //this.url=url;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_horizon_recycler_list_item, parent, false);


            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
           // Album album = albumList.get(position);
           // holder.tv.setText(albumList.get(position));
        //    Toast.makeText(getContext(),"UrlList:"+urlList1,Toast.LENGTH_LONG).show();
            holder.tv.setText(list.get(position));

            Picasso.with(getContext()).
                    load(urlList1.get(position)).resize(340,180)
                    .into(holder.thumbnail1);


        }

        @Override
        public int getItemCount() {
            return albumList.size();
        }

    }
}