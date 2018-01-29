package com.example.ashutosh.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import com.example.ashutosh.mpiricmodule1.R;
import com.example.ashutosh.datasource.DrawerChildModel;
import com.example.ashutosh.datasource.DrawerParentModel;
import com.example.ashutosh.viewholders.DrawerChildViewHolder;
import com.example.ashutosh.viewholders.DrawerParentViewHolder;

import java.util.List;

/**
 * Created by Admin on 12/20/2016.
 */

public class DrawerExpandableAdapter extends ExpandableRecyclerAdapter<DrawerParentViewHolder
        , DrawerChildViewHolder> {
    LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

     List<DrawerParentModel> my_parentItemList;
     //List<List<Object>> my_childlist;
    List<ParentObject> pob;
    public DrawerExpandableAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        // this.my_parentItemList=parentItemList;
        //   this.my_childlist=mychildlist;
        this.pob=parentItemList;
    }

    @Override
    public DrawerParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.list_item_drawer_row, viewGroup, false);
        return new DrawerParentViewHolder(view);
    }

    @Override
    public DrawerChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.drawer_child_list_item, viewGroup, false);
        return new DrawerChildViewHolder(view);     }



    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void onBindParentViewHolder(DrawerParentViewHolder drawerParentViewHolder, int i, Object o) {

        DrawerParentModel parentModel = (DrawerParentModel) o;

        drawerParentViewHolder.mCrimeTitleTextView.setText(parentModel.getTitle());

        try {

            if (pob.get(i).getChildObjectList()==null) {
                drawerParentViewHolder.mParentDropDownArrow.setVisibility(View.GONE);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

       /* int pos=parentModel.getPosition();
        if(my_childlist.get(pos).equals(""))
        {
            drawerParentViewHolder.mParentDropDownArrow.setVisibility(View.GONE);
        }*/

    }

    @Override
    public void onBindChildViewHolder(DrawerChildViewHolder drawerChildViewHolder, int i, Object o) {
        DrawerChildModel childModel = (DrawerChildModel) o;
        drawerChildViewHolder.mCrimeDateText.setText(childModel.getMstr());
    }
}