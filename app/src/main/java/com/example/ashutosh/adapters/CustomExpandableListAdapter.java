package com.example.ashutosh.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.example.ashutosh.mpiricmodule1.R;

import java.util.List;
import java.util.Map;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> mExpandableListTitle;
    private Map<String, List<String>> mExpandableListDetail;
    private LayoutInflater mLayoutInflater;

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

//    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
//                                       Map<String, List<String>> expandableListDetail) {
//        mContext = context;
//        mExpandableListTitle = expandableListTitle;
//        mExpandableListDetail = expandableListDetail;
//        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public Object getChild(int listPosition, int expandedListPosition) {
//        return mExpandableListDetail.get(mExpandableListTitle.get(listPosition))
//            .get(expandedListPosition);
//    }
//
//    @Override
//    public long getChildId(int listPosition, int expandedListPosition) {
//        return expandedListPosition;
//    }
//
//    @Override
//    public View getChildView(int listPosition, final int expandedListPosition,
//                             boolean isLastChild, View convertView, ViewGroup parent) {
//        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
//        if (convertView == null) {
//            convertView = mLayoutInflater.inflate(R.layout.drawer_child_list_item, null);
//        }
//        TextView expandedListTextView = (TextView) convertView
//            .findViewById(R.id.);
//        expandedListTextView.setText(expandedListText);
//        return convertView;
//    }
//
//    @Override
//    public int getChildrenCount(int listPosition) {
//        return mExpandableListDetail.get(mExpandableListTitle.get(listPosition))
//            .size();
//    }
//
//    @Override
//    public Object getGroup(int listPosition) {
//        return mExpandableListTitle.get(listPosition);
//    }
//
//    @Override
//    public int getGroupCount() {
//        return mExpandableListTitle.size();
//    }
//
//    @Override
//    public long getGroupId(int listPosition) {
//        return listPosition;
//    }
//
//    @Override
//    public View getGroupView(int listPosition, boolean isExpanded,
//                             View convertView, ViewGroup parent) {
//        String listTitle = (String) getGroup(listPosition);
//        if (convertView == null) {
//            convertView = mLayoutInflater.inflate(R.layout.list_item_drawer_row, null);
//        }
////        TextView listTitleTextView = (TextView) convertView
////            .findViewById(R.id.textView2);
////        listTitleTextView.setTypeface(null, Typeface.BOLD);
////        listTitleTextView.setText(listTitle);
//        return convertView;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    @Override
//    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
//        return true;
//    }
}
