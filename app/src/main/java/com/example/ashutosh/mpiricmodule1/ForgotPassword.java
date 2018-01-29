package com.example.ashutosh.mpiricmodule1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ForgotPassword extends AppCompatActivity {
EditText email;
    Button  submit;
    private View mProgressView;
    private UserLoginTask mAuthTask = null;
    String TAG="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email=(EditText)findViewById(R.id.email);
        submit=(Button)findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(!email.getText().toString().trim().isEmpty()){

            mAuthTask = new UserLoginTask(email.getText().toString().trim());
            mAuthTask.execute();
            //   showProgress(true);
        }
        else
            Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_SHORT);
    }
});

    }


    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            //Intent myintent=new Intent(this,HelloCardActivity.class)
        }
        return super.onOptionsItemSelected(item);
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
      //  private final String mPassword;

        UserLoginTask(String email) {
            mEmail = email;
          //  mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            HttpHandler sh = new HttpHandler();
            List<NameValuePair> params1=new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("mEmail",mEmail.toString()));
       //     params1.add(new BasicNameValuePair("mPassword",mPassword.toString()));
            String LOGINURL1="http://creativityportal.tallyvm.co.in/api/code/main/frmApi_creativity.php?api=forgotPassword&"+"email="+mEmail;
            try
            {
                String jsonStr = sh.makeServiceCall(LOGINURL1);
                Log.e(TAG, "Response from url: " + jsonStr);
               // JSONObject json = jparser.makeHttpRequest(LOGINURL, "POST", params1);
              //   System.out.println("JSON RESPONSE:"+json1.toString());
            }catch(Exception e)
            {}
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
  //          showProgress(false);

            Toast.makeText(getApplicationContext(),"Password Change Mail Sent.",Toast.LENGTH_LONG).show();
            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
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
  //          mAuthTask = null;
//            showProgress(false);
        }
    }
}


