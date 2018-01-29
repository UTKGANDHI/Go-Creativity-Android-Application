package com.example.ashutosh.datasource;

import android.graphics.Bitmap;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by Admin on 12/21/2016.
 */

public class CategoryModel implements ParentObject {

    private String Cat_ID;
    private String Cat_Name;
    private String Cat_Date;
    private String platforms;
    private String image;
    private Bitmap bitmap;

    public String getCat_ID() {
        return Cat_ID;
    }

    public void setCat_ID(String cat_ID) {
        Cat_ID = cat_ID;
    }

    public String getCat_Name() {
        return Cat_Name;
    }

    public void setCat_Name(String cat_Name) {
        Cat_Name = cat_Name;
    }

    public String getCat_Date() {
        return Cat_Date;
    }

    public void setCat_Date(String cat_Date) {
        Cat_Date = cat_Date;
    }

    @Override
    public List<Object> getChildObjectList() {
        return null;
    }

    @Override
    public void setChildObjectList(List<Object> list) {

    }
}
