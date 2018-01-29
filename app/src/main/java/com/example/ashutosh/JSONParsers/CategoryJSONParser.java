package com.example.ashutosh.JSONParsers;

import com.example.ashutosh.datasource.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/21/2016.
 */

public class CategoryJSONParser {
    static List<CategoryModel> catList;

    public static List<CategoryModel> parseData(String content) {

        JSONArray cat_arry = null;
        CategoryModel cm = null;
        try {

            cat_arry = new JSONArray(content);
            catList = new ArrayList<>();

            for (int i = 0; i < cat_arry.length(); i++) {

                JSONObject obj = cat_arry.getJSONObject(i);
                cm = new CategoryModel();

                cm.setCat_ID(obj.getString("Cat_ID"));
                cm.setCat_Name(obj.getString("Cat_Name"));
                cm.setCat_Date(obj.getString("Cat_Date"));

                catList.add(cm);
            }
            return catList;

        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
