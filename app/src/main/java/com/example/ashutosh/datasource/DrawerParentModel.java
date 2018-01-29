package com.example.ashutosh.datasource;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by Admin on 12/20/2016.
 */

public class DrawerParentModel implements ParentObject {

    /* Create an instance variable for your list of children */
    private List<Object> mChildrenList;
    private String title;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Your constructor and any other accessor
     *  methods should go here.
     */

    @Override
    public List<Object> getChildObjectList() {
        return mChildrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mChildrenList = list;
    }
}