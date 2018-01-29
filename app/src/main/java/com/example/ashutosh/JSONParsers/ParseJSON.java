package com.example.ashutosh.JSONParsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 12/22/2016.
 */

public class ParseJSON {
    public static String[] ids;
    public static String[] names;
    public static String[] emails;

   // public static final String JSON_ARRAY = "result";
    public static final String KEY_ID = "Cat_ID";
    public static final String KEY_NAME = "Cat_Name";
    public static final String KEY_EMAIL = "Cat_Date";

    private JSONArray users = null;

    private String json;

//    public ParseJSON(String json){
//        this.json = json;
//    }

    public void parseJSON(String json){
        JSONObject jsonObject=null;
        try {
            users = new JSONArray(json);

          //  jsonObject = users.getJSONObject(0);

            ids = new String[users.length()];
            names = new String[users.length()];
            emails = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                ids[i] = jo.getString(KEY_ID);
                names[i] = jo.getString(KEY_NAME);
                emails[i] = jo.getString(KEY_EMAIL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
