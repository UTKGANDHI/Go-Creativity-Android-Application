package com.example.ashutosh.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashutosh.mpiricmodule1.ALL_VIDEOS;
import com.example.ashutosh.mpiricmodule1.Card;
import com.example.ashutosh.mpiricmodule1.Cart;
import com.example.ashutosh.mpiricmodule1.CartAdapter;
import com.example.ashutosh.mpiricmodule1.Home;
import com.example.ashutosh.mpiricmodule1.R;
import com.example.ashutosh.mpiricmodule1.Versatile_logo;
import com.squareup.picasso.Picasso;

import java.util.List;

import Models.Util1;

/**
 * Created by Admin on 12/20/2016.
 */

public class VideoFragment extends Fragment {
    CardView cv;
    SharedPreferences preferences1;
    private RecyclerView recyclerView;
    private CartAdapter1 adapter;
    public static int pos_video,pos_logo;
    private List<Card> cartlist;
    Context context;

    String apiURL= Util1.GET_SUBLOGOLIST_API;
    Context mContext;
    public static boolean flag=false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        flag=true;
        mContext = getActivity().getApplicationContext();
        View v = inflater.inflate(R.layout.recycler, container, false);
        cv = (CardView) v.findViewById(R.id.card_view);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        adapter = new CartAdapter1(context, Home.videolist,2);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(v.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
      //  Home.videolist.clear();
        return v;
    }

    public class CartAdapter1 extends RecyclerView.Adapter<CartAdapter1.MyViewHolder>
    {
        private Context mContext;
        SharedPreferences preferences1;

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

        public CartAdapter1(Context mContext, List<Card> cartList,int pos)
        {
            this.mContext = mContext;
            this.cartList = cartList;
            this.pos=pos;
        }

        @Override
        public CartAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.n1, parent, false);

            return new CartAdapter1.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final CartAdapter1.MyViewHolder holder, int position) {

            if(LogoFragment.flag)
            {
                holder.button.setVisibility(View.GONE);
            }
            final Card card = cartList.get(position);

            holder.a.setText(card.getName()); // setting the name of the cards in the fragment (Logo and Video)

             // take url list of videos

                URL1=Home.url_subvideo.get(position).toString();
                try {
                    Picasso.with(mContext).load(URL1).into(holder.img1);
                }catch (Exception e) {
                    e.printStackTrace();
                }


            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pos==1){
                        if(holder.getAdapterPosition()==0){
                            Intent i=new Intent(v.getContext(),Versatile_logo.class);
                            pos_logo=holder.getAdapterPosition();
                            v.getContext().startActivity(i);
                        }
                    }
                    else if(pos==2)
                    {

                            Intent i=new Intent(v.getContext(),ALL_VIDEOS.class);
                            pos_video=holder.getAdapterPosition();
                            v.getContext().startActivity(i);

                    }
                    else{}
                }
            });

        }

        public int getItemCount() {
            return cartList.size();
        }
    }

}