package com.example.ashutosh.mpiricmodule1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashutosh.fragments.VideoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Models.Util1;


public class Details extends AppCompatActivity implements AsyncRequest.OnAsyncRequestComplete {

    TextView Text1,Text2,Text3;
    Context context;
    public static String response1=null;
    public  String thumb,url,url1;
    String apiURL= Util1.GET_DESCRIPTION;
    int pd;
    String description;
    Spanned conBold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //  Text2=(TextView)findViewById(R.id.Text2);
        //   Text3=(TextView)findViewById(R.id.Text3);

        Intent i=getIntent();
        pd=i.getIntExtra("p",0);
       // Toast.makeText(getApplicationContext(),"pd details:"+pd,Toast.LENGTH_LONG).show();
        AsyncRequest getPosts = new AsyncRequest(this, "GET");
        getPosts.execute(apiURL+"&pid="+pd);
        String s1="<b>"+" Key Features"+"</b>";
        //  String s2="<b>"+" General Description"+"</b>";
        //String s3="<b>"+" Video Features"+"</b>";
        conBold = Html.fromHtml(s1);
        //Spanned conBold1=Html.fromHtml(s2);
        //Spanned conBold2=Html.fromHtml(s3);

        SpannableString ss1=  new SpannableString(s1);
        ss1.setSpan(new RelativeSizeSpan(2f), 0, ss1.length(), 0);
//
//        String s4 = conBold1+"\n\n"+"\u2022 "+"hello android"+"\n"+
//                      "\u2022 "+"hello android"+"\n";
//            String s5 = conBold2+"\n\n"+"\u2022 "+"hello android"+"\n"+
//                  "\u2022 "+"hello android"+"\n";
//
//          Text1.setText(s);
//         Text2.setText(s4);
//        Text3.setText(s5);
//Toast.makeText(getApplicationContext(),"Toast:"+description,Toast.LENGTH_LONG).show();

    }


    @Override
    public void asyncResponse(String response) {
        try {
            // create a JSON array from the response string
            JSONArray objects = new JSONArray(response);
            Text1=(TextView)findViewById(R.id.Text1);
            response1=response;
            for (int i = 0; i < objects.length(); i++)
            {
                String type="Videos";
                JSONObject object = (JSONObject) objects.getJSONObject(i);
                int prod_id=object.getInt("prod_id");
                if(pd==prod_id) {
                    int price = object.getInt("prod_price");
                    String name = object.getString("prod_name");
                    description=object.getString("prod_description");
                    //   url = object.getString("prod_demourl");
                    //   int cateid = object.getInt("prod_subcateid");
                }
            }
            String s = conBold+""+"\n\u2022 "+description+"\n"
                    ;Text1.setText(s);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            //Intent myintent=new Intent(this,HelloCardActivity.class)
        }
        return super.onOptionsItemSelected(item);
    }
}
