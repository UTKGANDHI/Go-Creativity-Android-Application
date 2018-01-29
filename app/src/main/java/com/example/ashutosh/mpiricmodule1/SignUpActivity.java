package com.example.ashutosh.mpiricmodule1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Utkarsh on 19-Dec-16.
 */

public class SignUpActivity extends AppCompatActivity {
    EditText Uname, Email, Password;
    Button SignUp, SignIn;
    private Context context1;
    private Context context;   Boolean registerStatus = false;
    int keyCode;
    private static final String TAG_ERROR = "error_msg";
    private String errorMsg;
    String TAG = "";
    private static final String TAG_STATUS = "status";
    String url = "http://creativityportal.tallyvm.co.in/api/code/main/frmApi_creativity.php?api=validateUser";
    String email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Uname = (EditText) findViewById(R.id.uname);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        SignUp = (Button) findViewById(R.id.sign_up_button);
        SignIn = (Button) findViewById(R.id.sign_in_button);

        // email checking
        Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        NetworkAvailability check=new NetworkAvailability();
        check.NetworkAvailbility(getApplicationContext(),SignUpActivity.this);
        SignUp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                email = Email.getText().toString().trim();
                String password1=Password.getText().toString().trim();
                NetworkAvailability check=new NetworkAvailability();
         //       check.checkNetworkAvailbility();

                String expression = "(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (email.matches(emailPattern)) {
                    //Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                }
                if(!password1.matches(expression)){
                    Toast.makeText(getApplicationContext(),"Please enter a password containing atleast one on Uppercase and Lowercase character and special case character",Toast.LENGTH_LONG).show();
                }

                if ((email.length() < 1 && Password.length() < 6)) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters and atleast 1 Capital letter and special charaacter!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (!check.NetworkAvailbility(getApplicationContext(),SignUpActivity.this))
                    {
                        displayMobileDataSettingsDialog(SignUpActivity.this,getApplicationContext(),"No Internet Found","Please enable internet");
                    }
                   else{

                        if(!password1.isEmpty()&& !email.isEmpty() && email.matches(emailPattern) && password1.matches(expression)){
                    HashMap map = new HashMap();
                    map.put(Uname.toString(), "u");
                    map.put(Password.toString(), "p");
                    map.put(Email.toString(), "e");
                    if (map != null) {
                        String uname = Uname.getText().toString().trim();
                        String password = Password.getText().toString().trim();

                        new RegisterUser().execute(uname, email, password);
                        //Log.d(TAG, "Successfully signed in");
                        //Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                    }     //startActivity(i);

                    }else{
                            Toast.makeText(getApplicationContext(),"Please enter valid credentials",Toast.LENGTH_SHORT).show();
                        }} //else  {
                       // Toast.makeText(getApplicationContext(), "No credentials inserted ", Toast.LENGTH_LONG).show();
                    // }
                }
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i1);
            }
        });



    }
    public AlertDialog displayMobileDataSettingsDialog(Activity activity, final Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        //  builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent myIntent = new Intent(getBaseContext(), SignUpActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
                    startActivity(myIntent);

                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent sameIntent = new Intent(SignUpActivity.this,
                        SignUpActivity.class);
                startActivity(sameIntent);
                finish();
                //       activity.finish();

            }
        });

        builder.show();

        return builder.create();
    }



    private class RegisterUser extends AsyncTask<String, Void, Boolean> {

        // Hashmap for parameters
        LinkedHashMap<String, String> params;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {



            params = new LinkedHashMap<>();
            params.put("username", args[0]);
            params.put("email", args[1]);
            params.put("password", args[2]);

            // Creating service handler class instance
            WebRequest webreq = new WebRequest();

            // Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(url, WebRequest.POST, params);

            //String finalUrl = url+"?name="+args[0]+"&username="+args[1]+"&password="+args[2];
            //String jsonStr = webreq.makeWebServiceCall(finalUrl, WebRequest.GET,null);

            Log.d(TAG, "Response: " + jsonStr);

            //registerStatus = ParseJSON(jsonStr);
            if (jsonStr.contains("1"))
            {
                registerStatus=true;
            }
            else if (jsonStr.contains("0"))
                registerStatus=false;
            return registerStatus;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            context1=getApplicationContext();
            if (pDialog.isShowing())
                pDialog.dismiss();

            if(registerStatus==true)
            {
                Toast.makeText(getApplicationContext(),"User has already been registered",Toast.LENGTH_SHORT).show();
            }
            else if(registerStatus==false){
                Toast.makeText(context1, "You are successfully registered!", Toast.LENGTH_LONG).show();
                Intent i1= new Intent(getApplicationContext(),LoginActivity.class);startActivity(i1);
            }
//            if (result != null) {
//                if (result.booleanValue()) {
//                    Toast.makeText(context1, "You are successfully registered!", Toast.LENGTH_LONG).show();
//
//                    finish();
//                } else
//                    Toast.makeText(context1, errorMsg, Toast.LENGTH_LONG).show();
//            } else
//              Toast.makeText(context1, "Registeration done !!", Toast.LENGTH_LONG).show();



        }

    }

    private Boolean ParseJSON(String json)
    {
        if (json != null) {
            try {
                JSONObject jsonObj = new JSONObject(json);
                boolean status = jsonObj.getBoolean(TAG_STATUS);
                ;

                if (status == false)
                    errorMsg = jsonObj.getString(TAG_ERROR);
                    return Boolean.valueOf(status);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else
        {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }

}

