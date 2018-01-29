package com.example.ashutosh.mpiricmodule1;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.zip.Inflater;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static android.R.id.list;

public class Filter extends AppCompatActivity {
   int click=0;
    int j;
    int unclick=0;
    Context context;
    View convertView;
    ViewGroup parent;
    int itemselectedindex;
    private ListView list1,list2 ;
    Button apply,clear;
    Model[] modelItems;
    Model[] modelItems1,modelItems2;
 //   int width;
    LayoutInflater inflater;
    private ArrayAdapter<String> listAdapter,listAdapter2 ;
    boolean priceclicked=false;
    RelativeLayout mLayoutTab;
    CheckBox cb;
    View vi;
    String name;
    ArrayList<String> LocalList,ApplyList,TempList;
    public static ArrayList<String> ApplyList1;
    public static HashMap<String,String> FilterMap,FilterMap1;
    public static Hashtable<String,String>FilterHashtable;
    public static HashMap<String,String>PreserveFilter;
    LinearLayout mLayoutTab1;
    TextView PriceTag,PriceValue;
    SeekBar seekbar1;
    int value;
    ArrayList<Model> SharedPreferenceBrand;
    ArrayList<Model> SharedPreferenceColor;
    ArrayList<Model>CheckList;
    Model[] CheckList1,CheckList2;
    SharedPreferences sh1;
    ArrayList<String> PreserveList;
     CustomAdapter customadapter2;
    String Type;
    Intent intent1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        apply=(Button)findViewById(R.id.Button1);
        clear=(Button)findViewById(R.id.Button2);
        list1 = (ListView) findViewById( R.id.list1 );
        list2=(ListView)findViewById(R.id.list2);
        seekbar1 = (SeekBar)findViewById(R.id.seekBar1);
        PriceValue = (TextView)findViewById(R.id.textView2);
        cb=(CheckBox)findViewById(R.id.checkBox1);
        inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vi =inflater.inflate(R.layout.simplerow2,null);

        FilterMap=new HashMap<String,String>();
        FilterMap1=new HashMap<String,String>();
     //   modelItems[0] = new Model("A", 0);
      //  modelItems[1] = new Model("B", 1);

        Intent intent=getIntent();
        if(intent!=null) {
            Type = intent.getStringExtra("Type");
        }
        String[] planets = new String[] { "Animations", "Designs", "Price"};
        String[] planets2=new String[]{"WhiteBoard","Motion-Graphic","Digital-Cutout","Cartoon","Animated Music"};
        String[] planets3=new String[]{"WordMark","LetterMark","Combination Mark"};
        modelItems = new Model[planets3.length];
        modelItems1=new Model[planets2.length];
        for(int i=0;i<planets2.length;i++){
            modelItems1[i]=new Model(planets2[i],i);
        }
        for(int i=0;i<planets3.length;i++){
            modelItems[i]=new Model(planets3[i],i);
        }
         customadapter2=new CustomAdapter(this,modelItems1);
        final CustomAdapter adapter = new CustomAdapter(this, modelItems);
     //   customadapter2.SaveChecked(modelItems1);

       PreserveList=new ArrayList<>();
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll( Arrays.asList(planets) );


        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);
        listAdapter2 = new ArrayAdapter<String>(this, R.layout.simplerow2, planetList);
       final ArrayAdapter sampleAdapter=(ArrayAdapter)list2.getAdapter();
        ApplyList1=new ArrayList<String>();
     //   seekbar1.setProgress(100);
       //storeforapply();
        value=5;
        PriceValue.setText(Integer.toString(value)+"$");
        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value=progress*10;
                //PriceValue.setText(Integer.toString(defaultvalue)+"$");
                PriceValue.setText(Integer.toString(value)+"$");
                if(value==0){
                  value+=5;
                    PriceValue.setText(Integer.toString(value)+"$");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        list1.setAdapter( listAdapter );




        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position==0)
                {   PreserveFilter();
                    name=list1.getItemAtPosition(0).toString();
                  //  Toast.makeText(getApplicationContext(),"Name:"+name,Toast.LENGTH_LONG).show();
                       // list2.setAdapter(customadapter2);
                         if(priceclicked==false)
                         {
                             CheckList1=new Model[10];
                             if(CheckList!=null){
                                // CheckList2=new Model[10];
                              //   CheckList2=ConvertArraytoModel(CheckList);
                           ConvertList(modelItems1);
                             list2.setAdapter(customadapter2);
                           // storetopreference();
                        }
                             else{
                                list2.setAdapter(customadapter2);
                                 ConvertList(modelItems1);
                             }
                         }
                    else if(priceclicked==true){
                            CheckList=customadapter2.SaveChecked(modelItems1);
                            setUP();
                            list2.setAdapter(customadapter2);
                            ConvertList(modelItems1);
                        }

                }

                else if(position==1)
                {  PreserveFilter();
                    name=list1.getItemAtPosition(1).toString();
                    //Toast.makeText(getApplicationContext(),"Name:"+name,Toast.LENGTH_LONG).show();
                    if(priceclicked==false) {
                    list2.setAdapter(adapter);
                    ConvertList(modelItems);
                    // list2.setAdapter(listAdapter2);
                }else if(priceclicked==true)
                {
                  setUP();
                    list2.setAdapter(adapter);
                    ConvertList(modelItems);
                    //list2.setAdapter(listAdapter2);
                }}
                else if(position==2)
                {  PreserveFilter();
                    name=list1.getItemAtPosition(2).toString();
                    //Toast.makeText(getApplicationContext(),"Name:"+name,Toast.LENGTH_LONG).show();
                  setDOWN();
                }
            }
        });



    }


    public void clear(){


              //  LinearLayout r1=(LinearLayout)findViewById(R.id.LinearLayout123);


                for(int i=0;i<list2.getChildCount();i++)
                {
                    cb=(CheckBox)list2.getChildAt(i).findViewById(R.id.checkBox1);
                    cb.setChecked(false);
                }
                customadapter2.notifyDataSetChanged();


    }


    public void apply(){
          if(Type.equals("Videos")){
                 intent1=new Intent(Filter.this,V_play.class);}
        else if(Type.equals("Logos")){
         intent1=new Intent(Filter.this,LOGO_ITEM_ACTIVITY.class);
}
             //   LinearLayout r1=(LinearLayout)findViewById(R.id.LinearLayout123);
                // LocalList=new ArrayList<String>();
                ApplyList=new ArrayList<String>();
                // LocalList.addAll(list2);

                if((value>=0))
                {   String s3=name+"="+Integer.toString(value);
                    ApplyList1.add(s3);
                }

                for(int i=0;i<list2.getChildCount();i++)
                {
                    cb=(CheckBox)list2.getChildAt(i).findViewById(R.id.checkBox1);
                    if(cb.isChecked())
                    {
                        String s=name.toString()+"="+TempList.get(i).toString();
                        ApplyList.add(s);
                      //  Toast.makeText(getApplicationContext(),"Filter parameter:"+ApplyList,Toast.LENGTH_LONG).show();
                    }
                }
                    ApplyList1.addAll(ApplyList);
                    ApplyList1.addAll(PreserveList);
                   //Toast.makeText(getApplicationContext(),"FinalList:"+ApplyList1,Toast.LENGTH_LONG).show();


        storetopreference(ApplyList1);

        intent1.putStringArrayListExtra("Filter",ApplyList1);
                startActivity(intent1);




    }

    public void PreserveFilter()
    {
        for(int i=0;i<list2.getChildCount();i++)
        {
            cb=(CheckBox)list2.getChildAt(i).findViewById(R.id.checkBox1);
            if(cb.isChecked())
            {   cb.setChecked(true);
                String s1=name+"="+TempList.get(i).toString();
                PreserveList.add(s1);
           //     Toast.makeText(getApplicationContext(),"PreserveFilter:"+PreserveList,Toast.LENGTH_LONG).show();
            }


        }// PreserveList.add(PreserveFilter);

    }



public void ConvertList(Model[] ModelItem){

      TempList=new ArrayList<String>();
    for(int i=0;i<ModelItem.length;i++){
        //String Object=ModelItem[i].getName();
        TempList.add(ModelItem[i].getName().toString());
        //Toast.makeText(getApplicationContext(),"TempList:"+TempList,Toast.LENGTH_LONG).show();
    }
}

    public Model[] ConvertArraytoModel(ArrayList<Model> arrayList){
        for(int i=0;i<arrayList.size();i++)
        {
            CheckList1[i]=new Model(arrayList.get(i).getName(),i);
        }
        return CheckList1;
    }



    public void setUP(){
        View LineView=(View)findViewById(R.id.verticallineview);

        LinearLayout Parent=(LinearLayout)findViewById(R.id.List1);
        LinearLayout child1=(LinearLayout)findViewById(R.id.List2);
        LinearLayout child2=(LinearLayout)findViewById(R.id.LinearLayout3);



        mLayoutTab = (RelativeLayout)findViewById(R.id.Relativelayout1);
        mLayoutTab1 = (LinearLayout)findViewById(R.id.LinearLayout2);
        PriceValue = (TextView)findViewById(R.id.textView2);
        mLayoutTab1.setVisibility(View.GONE);
         list2.setVisibility(View.VISIBLE);
        PriceTag=(TextView)findViewById(R.id.textView1);
        PriceTag.setVisibility(View.GONE);
        PriceValue.setVisibility(View.GONE);
        LineView.setVisibility(View.VISIBLE);
        // view.setAlpha(0.0f);
  //      Toast.makeText(getApplicationContext(),"Width"+width,Toast.LENGTH_LONG).show();
        child1.setLayoutParams(new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT));
        mLayoutTab.animate()
                .translationY(0)
                .alpha(1.0f);
    }

    public void setDOWN() {
        priceclicked = true;

        LinearLayout Parent=(LinearLayout)findViewById(R.id.List1);
        LinearLayout child1=(LinearLayout)findViewById(R.id.List2);
        LinearLayout child2=(LinearLayout)findViewById(R.id.LinearLayout3);
        LinearLayout layout = (LinearLayout) findViewById(R.id.List2);
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        list2.setAdapter(null);
        mLayoutTab = (RelativeLayout) findViewById(R.id.Relativelayout1);


// Start the animation
        mLayoutTab.animate()
                .translationY(800)
                .alpha(1.0f)
                ;
        ListView List1 = (ListView) findViewById(R.id.list1);
        View LineView = (View) findViewById(R.id.verticallineview);
        PriceTag = (TextView) findViewById(R.id.textView1);
        PriceTag.setVisibility(View.VISIBLE);
        PriceValue = (TextView) findViewById(R.id.textView2);
        PriceValue.setVisibility(View.VISIBLE);
        LineView.setVisibility(View.GONE);
        list2.setVisibility(View.GONE);
        mLayoutTab1 = (LinearLayout) findViewById(R.id.LinearLayout2);
        mLayoutTab1.animate().translationY(0);
        mLayoutTab1.setVisibility(View.VISIBLE);
        child1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


    }

    public void storetopreference(ArrayList<String> T1)
    {

        Set<String> set = new HashSet<String>();
        set.addAll(ApplyList1);
        sh1= getApplicationContext().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh1.edit();
        editor.putStringSet("HashSet",set);
        editor.commit();
      //  Toast.makeText(getApplicationContext(),"Hashset:"+set,Toast.LENGTH_LONG).show();
    }


    public ArrayList getPreference(String Preference){
        Gson gson=new Gson();
        sh1=getApplicationContext().getSharedPreferences("MyPreferences",MODE_PRIVATE);
      String response=sh1.getString("key","");
        ArrayList<Model> lstArrayList=gson.fromJson(response, new TypeToken<List<Model>>()
        {
        }.getType());
        return lstArrayList;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           case R.id.apply:
                // User chose the "Settings" item, show the app settings UI...
                apply();
                return true;

            case R.id.Clear:

                clear();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}