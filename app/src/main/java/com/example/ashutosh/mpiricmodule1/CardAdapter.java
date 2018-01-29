package com.example.ashutosh.mpiricmodule1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.List;

/**
 * Created by Gandhi on 21-Dec-16.
 */


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder>
{
    private Context mContext;
    static List<Card> cardList;
    int bindpos;
    private int pos;
    static Card c;
    public  String thumb,url;
    public class MyViewHolder extends RecyclerView.ViewHolder
    {

            public TextView title, price,type;
            public ImageView thumbnail, overflow;

            public MyViewHolder(View view)
            {

                super(view);
                type=(TextView) view.findViewById(R.id.type);
                title = (TextView) view.findViewById(R.id.title);
                price= (TextView) view.findViewById(R.id.price);
                thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                overflow = (ImageView) view.findViewById(R.id.overflow);

                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        bindpos=getAdapterPosition();
                        String url1=cardList.get(getAdapterPosition()).getUrl();
                        String type=cardList.get(getAdapterPosition()).getType();

                        if(type.equals("Videos" )|| type.equals("Video")) {
                            Intent i = new Intent(v.getContext(), V_play.class);
                            i.putExtra("url",url1);
                            i.putExtra("bindpos",bindpos);
                            // int cardpos=cardList.get(getAdapterPosition());
                            int p=cardList.get(getAdapterPosition()).getProd_id();
                            String name=cardList.get(getAdapterPosition()).getName();
                            String price=cardList.get(getAdapterPosition()).getNumOfSongs().toString();
                            i.putExtra("p",p);
                            i.putExtra("name",name);
                            i.putExtra("price",price);
                            c=new Card(name,Integer.valueOf(price),"",type,p,"",url1);
                            v.getContext().startActivity(i);
                        }
                        else if(type.equals("Logo")|| type.equals("Logos")) {
                            Intent i = new Intent(v.getContext(), LOGO_ITEM_ACTIVITY.class);
                            i.putExtra("url",url1);
                            i.putExtra("bindpos",bindpos);
                            int p=cardList.get(getAdapterPosition()).getProd_id();
                            String name=cardList.get(getAdapterPosition()).getName();
                            String price=cardList.get(getAdapterPosition()).getNumOfSongs().toString();
                            i.putExtra("p",p);
                            i.putExtra("name",name);
                            i.putExtra("price",price);c=new Card(name,Integer.valueOf(price),"",type,p,"",url1);
                            v.getContext().startActivity(i);
                        }
                    }
                    });



                    thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Class classname= null;
                        bindpos=getAdapterPosition();
                        String url1=cardList.get(getAdapterPosition()).getUrl();
                        String type=cardList.get(getAdapterPosition()).getType();

                        if(type.equals("Videos") || type.equals("Video")) {
                            Intent i = new Intent(v.getContext(), V_play.class);
                            i.putExtra("url",url1);
                            i.putExtra("bindpos",bindpos);
                           // int cardpos=cardList.get(getAdapterPosition());
                            int p=cardList.get(getAdapterPosition()).getProd_id();
                            String name=cardList.get(getAdapterPosition()).getName();
                            String price=cardList.get(getAdapterPosition()).getNumOfSongs().toString();
                            i.putExtra("p",p);
                            i.putExtra("name",name);
                            i.putExtra("price",price);c=new Card(name,Integer.valueOf(price),"",type,p,"",url1);
                            v.getContext().startActivity(i);
                        }
                        else if(type.equals("Logos")||type.equals("Logo")) {
                            Intent i = new Intent(v.getContext(), LOGO_ITEM_ACTIVITY.class);
                            i.putExtra("url",url1);
                            i.putExtra("bindpos",bindpos);
                            int p=cardList.get(getAdapterPosition()).getProd_id();
                            String name=cardList.get(getAdapterPosition()).getName();
                            String price=cardList.get(getAdapterPosition()).getNumOfSongs().toString();
                            i.putExtra("p",p);
                            i.putExtra("name",name);
                            i.putExtra("price",price);c=new Card(name,Integer.valueOf(price),"",type,p,"",url1);
                            v.getContext().startActivity(i);
                        }
                    }
                    });
            }

    }


    public CardAdapter(Context mContext, List<Card> cardList, int pos) {
        this.mContext = mContext;
        this.cardList = cardList;
        this.pos=pos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.title.setText(card.getName());
        holder.price.setText("$"+card.getNumOfSongs() );
        holder.type.setText(card.getType());
        url=card.getUrl();
        // loading album cover using Glide library
        Glide.with(mContext).load(card.getThumbnail()).into(holder.thumbnail);


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);

            }
        });


    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.action_cart:
                    Toast.makeText(mContext, "Added to Cart", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
       return cardList.size();
    }
}
