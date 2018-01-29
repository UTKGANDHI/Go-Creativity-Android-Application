package com.example.ashutosh.mpiricmodule1;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class Account extends AppCompatActivity {
    UpdatePassword task;
 // public  boolean success;
    Button update;
    TextView name,email;
    TextView pass;
    String email1;
     EditText input1,input2;
    String finalchangepass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        update=(Button)findViewById(R.id.button);
        name=(TextView)findViewById(R.id.ususr);
        email=(TextView) findViewById(R.id.email);
        pass=(TextView)findViewById(R.id.pswrd);
        SharedPreferences sh=getSharedPreferences("LoginAuth",MODE_PRIVATE);
        name.setText(sh.getString("USERNAME",null));
        email.setText(sh.getString("Email",null));

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"Inside Click method",Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertdialog=new AlertDialog.Builder(Account.this);
                alertdialog.setTitle("Change Password");
              //  alertdialog.setMessage("Enter Password");
                input1=new EditText(Account.this);
               input2=new EditText(Account.this);

                input1.setHint("Enter new password");
                input2.setHint("Confirm new password");
                LinearLayout lp=new LinearLayout(getApplicationContext());
                lp.setOrientation(LinearLayout.VERTICAL);
                lp.addView(input1);
                lp.addView(input2);
                alertdialog.setView(lp);
                alertdialog.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                      if(isValidPassword(input1.getText().toString().trim())==true) {
                          if (input1.getText().toString().equals(input2.getText().toString())) {
                              Toast.makeText(getApplicationContext(), "Password Changed", Toast.LENGTH_LONG).show();
                              finalchangepass=(String)input1.getText().toString().trim();

                          }
                          //dialog.dismiss();
                          else {
                              Toast.makeText(getApplicationContext(), "Please confirm both fields are same", Toast.LENGTH_LONG).show();
                          }
                      }else if(input1.getText().toString().trim()==null || input2.getText().toString().trim()==null){
                          Toast.makeText(getApplicationContext(),"Please ensure both fields arent left blank",Toast.LENGTH_LONG).show();
                      }
                      else if(isValidPassword(input1.getText().toString().trim())!=true){
                          Toast.makeText(getApplicationContext(),"Password should contain atleast one Uppercase character,special character and digits",Toast.LENGTH_LONG).show();

                      } }
                });
                        alertdialog.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                alertdialog.create();
                alertdialog.show();
            }
        });
    }

    public void cclick(View v)
    {
        // update details to the server
        String u=name.getText().toString().trim();
        email1=email.getText().toString().trim();
        String p=pass.getText().toString().trim();

        if(u.matches("")|| p.toString()==null || email1.toString()==null )
        {
            Toast.makeText(getApplicationContext(), "Please enter details", Toast.LENGTH_LONG).show();
        }
        else
        {
            // handle the server part`
              task=new UpdatePassword(email1,finalchangepass);
              task.execute();


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
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}

 class UpdatePassword extends AsyncTask<Void, Void, Boolean> {
    public boolean success;
    private final String mEmail;
    private final String mPassword;

    UpdatePassword(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        // TODO: attempt authentication against a network service.
        HttpHandler sh = new HttpHandler();
        List<NameValuePair> params1=new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("mEmail",mEmail.toString()));
        params1.add(new BasicNameValuePair("mPassword",mPassword.toString()));
        String CHANGEURL1="http://creativityportal.tallyvm.co.in/api/code/main/frmApi_creativity.php?api=updatePassword&"+"email="+mEmail+"&password="+mPassword;
        try
        {
            String jsonStr = sh.makeServiceCall(CHANGEURL1);
            Log.e(TAG, "Response from url: " + jsonStr);
            //JSONObject json = jparser.makeHttpRequest(LOGINURL, "POST", params1);
            // System.out.println("JSON RESPONSE:"+json1.toString());
            success=true;
            try
            {
        }catch(Exception e)
        {}
        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }

    //    for (String credential : DUMMY_CREDENTIALS) {
      //      String[] pieces = credential.split(":");
        //    if (pieces[0].equals(mEmail)) {
                // Account exists, return true if the password matches.
          //      return pieces[1].equals(mPassword);
           // }
        //}

        // TODO: register the new account here.
        return true;
    }catch(Exception e){}
        return success;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        //mAuth0Task = null;
      //  showProgress(false);

            /*if (success) {
                Toast.makeText( getApplicationContext(),"Successfully logged in", Toast.LENGTH_LONG).show();
                Intent i=new Intent(getApplicationContext(),Versatile_logo.class);
                startActivity(i);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }*/
    }

    @Override
    protected void onCancelled() {

    }
}


