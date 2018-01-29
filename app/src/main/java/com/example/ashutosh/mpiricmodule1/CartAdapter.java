
package com.example.ashutosh.mpiricmodule1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashutosh.fragments.LogoFragment;
import com.example.ashutosh.fragments.VideoFragment;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Gandhi on 23-Dec-16.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>
{
    private Context mContext;
    public static int pos_logo;
    SharedPreferences preferences1;
    LinearLayout FilterLayout;
    String Item_name1,Item_name2,Item_name3,URL1;
    private List<Card> cartList;int pos;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title, price,type,a,b,c;
        Button button;
        ImageView img1;
        CardView card_view;


        public MyViewHolder(View view) {
            super(view);

            type=(TextView) view.findViewById(R.id.type);
            a=(TextView)view.findViewById(R.id.TextView1);
            b=(TextView)view.findViewById(R.id.TextView2);
            c=(TextView)view.findViewById(R.id.TextView3);
            //FilterLayout=(LinearLayout)view.findViewById(R.id.FilterLayout);
            img1=(ImageView)view.findViewById(R.id.Img1);
            button=(Button) view.findViewById(R.id.button_rmv);
            card_view=(CardView) view.findViewById(R.id.card_view);

        }

        public void click(View v)
        {
            int position=getAdapterPosition();
            cartList.remove(position);
          //  notifyDataSetChanged();
            notifyItemRemoved(position);
            Intent i=new Intent(mContext,Cart.class);
            i.putExtra("p",position);
            v.getContext().startActivity(i);
        }


    }

    public CartAdapter(Context mContext, List<Card> cartList,int pos)
    {
        this.mContext = mContext;
        this.cartList = cartList;
        this.pos=pos;
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cart, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        if(LogoFragment.flag)
        {
               holder.button.setVisibility(View.GONE);
              // FilterLayout.setVisibility(View.GONE);
        }
        final Card card = cartList.get(position);

        holder.a.setText(card.getName()); // setting the name of the cards in the fragment (Logo and Video)

          // take url list for logos

            URL1=Home.url_sublogo.get(position).toString();
            try {
                Picasso.with(mContext).load(URL1).into(holder.img1);
            }catch (Exception e) {
                e.printStackTrace();
            }


        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos==1){

                        Intent i=new Intent(v.getContext(),Versatile_logo.class);
                        pos_logo=holder.getAdapterPosition();
                        v.getContext().startActivity(i);

                }
                else if(pos==2)
                {
                    if(holder.getAdapterPosition()==0){
                        Intent i=new Intent(v.getContext(),ALL_VIDEOS.class);
                        pos_logo=holder.getAdapterPosition();
                        v.getContext().startActivity(i);
                    }
                }
                else{}
            }
        });

    }

    public int getItemCount() {
        return cartList.size();
    }
}
