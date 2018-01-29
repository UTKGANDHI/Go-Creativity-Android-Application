package com.example.ashutosh.mpiricmodule1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Gandhi on 18-Jan-17.
 */

public class AsyncRequest extends AsyncTask<String, Integer, String> {

    OnAsyncRequestComplete caller;
    Context context;
    String method = "GET";
    List<NameValuePair> parameters = null;
    ProgressDialog pDialog = null;


    // Three Constructors
    public AsyncRequest(Activity a, String m, List<NameValuePair> p) {
        caller = (OnAsyncRequestComplete) a;
        context = a;
        method = m;
        parameters = p;
    }

    public AsyncRequest(Context a, String m) {
        context = a;
        caller = (OnAsyncRequestComplete) a;


        method = m;
    }

    public AsyncRequest(Activity a) {
        caller = (OnAsyncRequestComplete) a;
        context = a;
    }
    public AsyncRequest(Context a, String method, OnAsyncRequestComplete caller)
    {
        this.caller = caller;
        context = a;

        this.method=method;
    }

    // Interface to be implemented by calling activity
    public interface OnAsyncRequestComplete {
        public void asyncResponse(String response);
    }

    public String doInBackground(String... urls) {
        // get url pointing to entry point of API
        String address = urls[0].toString();
        if (method == "POST") { // naa evu to nahi joyu but this class works awesome badhi jagya aa j use kairu che
            return post(address);
        }

        if (method == "GET") {
            return get(address);
        }
        return null;
    }

    public void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading data..");
        pDialog.show();
    }

    public void onProgressUpdate(Integer... progress) {
        // you can implement some progressBar and update it in this record
        // setProgressPercent(progress[0]);
    }

    public void onPostExecute(String response) {
      Log.e("erro","postexec");
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        super.onPostExecute(response);
        caller.asyncResponse(response);
    }

    protected void onCancelled(String response) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        caller.asyncResponse(response);
    }

    @SuppressWarnings("deprecation")
    private String get(String address) {
        try {

            String response = null;
            try {
                URL url = new URL(address);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = convertStreamToString(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                //Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                //Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
               // Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
               // Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
            ///////////////////////////////////

//            if (parameters != null) {
//
//                String query = "";
//                String EQ = "="; String AMP = "&";
//                for (NameValuePair param : parameters) {
//                    query += param.getName() + EQ + URLEncoder.encode(param.getValue()) + AMP;
//                }
//
//                if (query != "") {
//                    address += "?" + query;
//                }
//            }
//
//            HttpClient client = new DefaultHttpClient();
//            HttpGet get= new HttpGet(address);
//
//            HttpResponse response = client.execute(get);
//            return stringifyResponse(response);

        } catch (Exception e) {
            // TODO Auto-generated catch block
        }

        return null;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private String post(String address) {
        try {

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(address);

            if (parameters != null) {
                post.setEntity(new UrlEncodedFormEntity(parameters));
            }

            HttpResponse response = client.execute(post);
            return stringifyResponse(response);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return null;
    }

    private String stringifyResponse(HttpResponse response) {
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();

            return sb.toString();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}

