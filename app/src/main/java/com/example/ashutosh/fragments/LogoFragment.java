package com.example.ashutosh.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashutosh.OnItemClickListener;
import com.example.ashutosh.mpiricmodule1.AsyncRequest;
import com.example.ashutosh.mpiricmodule1.Card;
import com.example.ashutosh.mpiricmodule1.CardAdapter;
import com.example.ashutosh.mpiricmodule1.CartAdapter;
import com.example.ashutosh.mpiricmodule1.Home;
import com.example.ashutosh.mpiricmodule1.R;
import com.example.ashutosh.mpiricmodule1.Versatile_logo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.Util1;

import static com.example.ashutosh.mpiricmodule1.Home.logolist;

/**
 * Created by Utkarsh on 12/20/2016.
 */

public class LogoFragment extends Fragment  {
    CardView cv;
    SharedPreferences preferences1;
    private RecyclerView recyclerView;
    private CartAdapter adapter;
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
        //     return inflater.inflate(R.layout.logo_fragment, container, false);
        flag=true;
        mContext = getActivity().getApplicationContext();
        View v = inflater.inflate(R.layout.recycler, container, false);
       // cv = (CardView) v.findViewById(R.id.card_view);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        adapter = new CartAdapter(context, Home.logolist,1);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return v;
    }



}