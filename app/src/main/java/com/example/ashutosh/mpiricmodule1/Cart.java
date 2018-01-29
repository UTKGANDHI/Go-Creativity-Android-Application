package com.example.ashutosh.mpiricmodule1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashutosh.fragments.LogoFragment;
import com.example.ashutosh.fragments.VideoFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Models.Util1;

public class Cart extends AppCompatActivity implements  AsyncRequest.OnAsyncRequestComplete
{
    Button remove;
    public static String size;
    SharedPreferences sh1,shpay;
    Set<String> set1=new HashSet<>();
    private RecyclerView recyclerView;
     DBCartAdapter adapter;
    public static int total=0;
    public static List<Card> cartlist,newcart;
    TextView Txt1,Txt2,Txt3;
    public static ArrayList<String>pricelist=new ArrayList<String>();
    CardView cv;
    SharedPreferences preferences1;
    String Email,mEmail1;
    String apiURL= Util1.GET_ITEMCART_API;
    ArrayList<String> CartPid;
    ArrayList<String> totalcart=new ArrayList<>();

    SharedPreferences sh;
    String pid;
    URL url1;
    int pid1,pid2;static int tprice;
    static String  pidurl;
    String url="https://i.ytimg.com/vi/wRWIsbSXoyQ/mqdefault.jpg";
    ArrayList<Integer> Allpids;
    Set<String> Cartset;
    public String VID="";
    public String VID1="";
    public String VID2="";
    SharedPreferences sh3;
    public String VIDEOID="";
    int cpos; LinearLayout fl;
    TextView t;
    String thumb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fl=(LinearLayout)findViewById(R.id.FilterLayout);
        cv = (CardView) findViewById(R.id.card_view);
        cartlist=new ArrayList<Card>();
         t=(TextView)findViewById(R.id.totalprice);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
         sh3=getSharedPreferences("Total",Context.MODE_PRIVATE);
        // TextView t=(TextView)findViewById(R.id.totalprice);
        CartPid=new ArrayList<String>();
        Allpids=new ArrayList<Integer>();
        mEmail1=GetEmailCredential();

        sh1= getSharedPreferences("CartElement", Context.MODE_PRIVATE);
        if(sh1!=null)
        {
           Cartset=new HashSet<>();
           Cartset=sh1.getStringSet("CartElement1",null);
        }
        if (Cartset==null || Cartset.size()==0)
        {
            Cartset=new HashSet<>();total=0;
            Toast.makeText(getApplicationContext(),"Your Cart is empty",Toast.LENGTH_LONG).show();
            fl.setVisibility(View.GONE);

        }
         if(Cartset.size()!=0) {
            prepareCart(Cartset);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
        else {
//          Toast.makeText(getApplicationContext(),"Your Cart is empty",Toast.LENGTH_LONG).show();
//             Intent i=new Intent(getApplicationContext(),Cart.class);
//             startActivity(i);

        }
        Button buybutton =(Button)findViewById(R.id.buyall);
        buybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(getApplicationContext(),Payment.class);
                    i.putExtra("price",total);
                    shpay=getSharedPreferences("Pay", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorpay=shpay.edit();
                    editorpay.putStringSet("Pay1",Cartset);
                    editorpay.commit();
                    startActivity(i);

                }});



    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),Home.class);
        startActivity(i);
    }

    public String GetEmailCredential(){
        sh1= getSharedPreferences("LoginAuth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh1.edit();
        Email=sh1.getString("Email","");
        return Email;
    }
// for removing items from the cart


    public void prepareCart(Set<String> s) {
        if(s.size()!=0) {
            cartlist = new ArrayList<>();
            pidurl = "";
            List<String> list = new ArrayList<String>(s);

            for (int i = 0; i < list.size(); i++) {
                pidurl = "'" + list.get(i) + "'," + pidurl;
            }

            pidurl = pidurl.substring(0, pidurl.length() - 1);

            AsyncRequest getPosts = new AsyncRequest(this, "GET");
            getPosts.execute(apiURL + "&pid=" + pidurl);
        }
        else{
            cartlist.clear();
            adapter = new DBCartAdapter(this,cartlist,0);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

    }

    public void asyncResponse(String response) {
        try {
            pricelist.clear();
            // create a JSON array from the response string
            JSONArray objects = new JSONArray(response);
            for (int i = 0; i < objects.length(); i++)
            {

                JSONObject object = (JSONObject) objects.getJSONObject(i);
                String type=object.getString("prod_type");
                if(type.equals("Logo")){
                    int price=object.getInt("prod_price");
                    String name=object.getString("prod_name");
                    url=object.getString("prod_demourl");
                    int prod_id=object.getInt("prod_id");
                    pricelist.add(String.valueOf(price));
                    Card a = new Card(name,price, url,type,prod_id,mEmail1,url);
                    cartlist.add(a);
                }
                else if(type.equals("Video")){
                    int price=object.getInt("prod_price");
                    String name=object.getString("prod_name");
                    url=object.getString("prod_demourl");
                    int prod_id=object.getInt("prod_id");
                    pricelist.add(String.valueOf(price));
                    VIDEOID=url.substring(url.lastIndexOf("?")+1);
                    VID=(VIDEOID.substring(VIDEOID.indexOf("=")+1));
                    if(VID.contains("&"))
                    {
                        String[] VID1=VID.split("\\&");
                        VID=VID1[0];
                    }
                    thumb="https://img.youtube.com/vi/"+VID+"/default.jpg";
                    Card a = new Card(name,price, thumb,type,prod_id,mEmail1,url);
                    cartlist.add(a);
                }
            }
            adapter = new DBCartAdapter(this,cartlist,0);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            if(totalcart.size()==0) {
                total=0;
                for (int i=0;i<cartlist.size();i++){
                    total = total + cartlist.get(i).getNumOfSongs();
                    totalcart.add(cartlist.get(i).getProd_id().toString());
                }
            }
            else {
                for (int j=0;j<cartlist.size();j++) {
                    if (totalcart.contains(cartlist.get(j).getProd_id().toString())) {
                    } else {
                        total = total + cartlist.get(j).getNumOfSongs();
                        totalcart.add(cartlist.get(j).getProd_id().toString());
                    }
                }
            }
            t.setText("Total Price: $"+total);


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class DBCartAdapter extends RecyclerView.Adapter<DBCartAdapter.MyViewHolder> implements View.OnClickListener
    {
        private Context mContext;
        SharedPreferences preferences1;

        private List<Card> cartList;int pos;

        @Override
        public void onClick(View v) {
           // Toast.makeText(mContext, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder
        {

            public TextView title, price,a,b,c,totalprice;
            Button button_rmv,buyall,buybutton;
            ImageView img1;
            CardView card_view;

            public MyViewHolder(View view) {
                super(view);

                a=(TextView)view.findViewById(R.id.TextView1);
                b=(TextView)view.findViewById(R.id.TextView2);
                c=(TextView)view.findViewById(R.id.TextView3);
                buyall=(Button)view.findViewById(R.id.buyall);
                View inflatedView = getLayoutInflater().inflate(R.layout.recycler1, null);
                buybutton=(Button) inflatedView.findViewById(R.id.buyall);
                button_rmv=(Button) view.findViewById(R.id.button_rmv);
                card_view=(CardView) view.findViewById(R.id.card_view);
                img1=(ImageView)view.findViewById(R.id.Img1);
            }
        }

        public DBCartAdapter(Context mContext, List<Card> cartList,int pos)
        {
            this.mContext = mContext;
            this.cartList = cartList;
            this.pos=pos;
        }

        @Override
        public DBCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_cart, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final Card card = cartList.get(position);
            pid2=card.getProd_id();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            holder.a.setText(cartList.get(position).getName());
            holder.b.setText("$"+cartList.get(position).getNumOfSongs().toString());
            String Image=cartList.get(position).getThumbnail();

            try {
               url1=new URL(Image);}
            catch(MalformedURLException e){}

            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                holder.img1.setImageBitmap(bmp);
            } catch (IOException e) {e.printStackTrace();}

            //holder.totalprice.setText("Total Price: $"+total);

            holder.button_rmv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     SharedPreferences.Editor editor1=sh1.edit();
                     int newprodid=cartList.get(position).getProd_id();
                     set1=sh1.getStringSet("CartElement1",null);
                     set1.remove(String.valueOf(newprodid));
                   // editor1.clear();
                     editor1.putStringSet("CartElement1",null);
                     editor1.commit();
                     sh1=null;
                     totalcart.remove(String.valueOf(newprodid));
                     total=total-cartList.get(position).getNumOfSongs();
                     sh1= getSharedPreferences("CartElement", Context.MODE_PRIVATE);
                     SharedPreferences.Editor editor2=sh1.edit().putStringSet("CartElement1",set1);
                     editor2.commit();

                     set1=sh1.getStringSet("CartElement1",null);
                     Cartset=set1;
                     prepareCart(set1);

                     if(cartList.size()==0){
                         V_play.prodIds.clear();
                         Intent i=new Intent(getApplicationContext(),Cart.class);
                         startActivity(i);
                         Toast.makeText(getApplicationContext(),"Your Cart is empty",Toast.LENGTH_LONG).show();
                     }
                     else {
                         V_play.prodIds.remove(String.valueOf(newprodid));
                     }

            }});

//            holder.buybutton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent i=new Intent(getApplicationContext(),Payment.class);
//                    i.putExtra("price",total);
//                    shpay=getSharedPreferences("Pay", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editorpay=shpay.edit();
//                    editorpay.putStringSet("Pay1",Cartset);
//                    editorpay.commit();
//                    startActivity(i);
//
//                }});


            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pos==1){
                        if(holder.getAdapterPosition()==0){
                            Intent i=new Intent(v.getContext(),Versatile_logo.class);
                            v.getContext().startActivity(i);
                        }
                    }
                    else if(pos==2)
                    {
                        if(holder.getAdapterPosition()==0){
                            Intent i=new Intent(v.getContext(),ALL_VIDEOS.class);
                            v.getContext().startActivity(i);
                        }
                    }
                    else{}
                }
            });
        }

        public int getItemCount() {
            size=String.valueOf(cartList.size());
            return cartList.size();
        }
    }


}


