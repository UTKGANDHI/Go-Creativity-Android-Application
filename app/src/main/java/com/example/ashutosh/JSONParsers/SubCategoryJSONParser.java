package com.example.ashutosh.JSONParsers;

import com.example.ashutosh.datasource.CategoryModel;
import com.example.ashutosh.datasource.SubCategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/21/2016.
 */

public class SubCategoryJSONParser {
    static List<SubCategoryModel> subcatList;

    public static List<SubCategoryModel> parseData(String content) {

        JSONArray cat_arry = null;
        SubCategoryModel cm = null;
        try {

            cat_arry = new JSONArray(content);
            subcatList = new ArrayList<>();

            for (int i = 0; i < cat_arry.length(); i++) {

                JSONObject obj = cat_arry.getJSONObject(i);
                cm = new SubCategoryModel();

                cm.setSub_id(obj.getString("sub_id"));
                cm.setSub_name(obj.getString("sub_name"));
                cm.setCat_id(obj.getString("cat_id"));

                subcatList.add(cm);
            }
            return subcatList;

        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
