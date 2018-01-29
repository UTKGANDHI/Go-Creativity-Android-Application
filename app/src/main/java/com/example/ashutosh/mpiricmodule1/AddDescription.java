package com.example.ashutosh.mpiricmodule1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.DataOutputStream;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;

import Models.Util1;

public class AddDescription extends AppCompatActivity implements View.OnClickListener,AsyncRequest2.OnAsyncRequestComplete{

    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = AddDescription.class.getSimpleName();
    private String selectedFilePath,fname;
    String requestURL = Util1.REQUEST;
    String requestURL1 = Util1.REQUEST_COMPLETE;
    private String SERVER_URL = "http://creativityportal.tallyvm.co.in/api/code/DA/UploadToServer.php";
     Button ivAttachment;
    Button bUpload;String uid;
    TextView tvFileName;
    ProgressDialog dialog;
    public File fileTosend;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);
        ivAttachment = (Button) findViewById(R.id.ivAttachment);
        bUpload = (Button) findViewById(R.id.b_upload);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       tvFileName = (TextView) findViewById(R.id.tv_file_name);
        ivAttachment.setOnClickListener(this);
        bUpload.setOnClickListener(this);

        String udata="Choose your Requirement File";
        ivAttachment.setPaintFlags(ivAttachment.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ivAttachment.setText(udata);
        context=getApplicationContext();

    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @Override
    public void onClick(View v) {
        if(v== ivAttachment){

            //on attachment icon click
            showFileChooser();
        }
        if(v== bUpload){

            //on upload button Click
            if(selectedFilePath != null){
                dialog = ProgressDialog.show(AddDescription.this,"","Uploading File...",true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //creating new thread to handle Http Operations
                        uploadFile(selectedFilePath);
                    }
                }).start();
            }else{
                Toast.makeText(AddDescription.this,"Please choose a File First or Enable permissions",Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onBackPressed() {

        Intent i=new Intent(getApplicationContext(),Orders.class);
        startActivity(i);

    }
    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types o   f files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_FILE_REQUEST){
                if(data == null){
                    //no data present
                    return;
                }

                Uri selectedFileUri = data.getData();
                selectedFilePath=getPath(context,selectedFileUri);
             //   selectedFilePath = FilePath.getPath(this,selectedFileUri);
                Log.i(TAG,"Selected File Path:" + selectedFilePath);
               // fname=selectedFilePath.substring(selectedFilePath.lastIndexOf("/")+1);


                if(selectedFilePath != null && !selectedFilePath.equals("")){
                    tvFileName.setText(selectedFilePath);
                }else{
                   // Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                }

                //on upload button Click
                if(selectedFilePath != null){

                    fileTosend = new File(selectedFilePath);
                    String myFileName= fileTosend.getName();

                }else{
                   //Toast.makeText(AddDescription.this,"Please choose a File First",Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddDescription.this,"File Uploading",Toast.LENGTH_SHORT).show();
                    callalways();
                }
            }
        }
    }
    //UPDATED!

    public String getPath(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, null, null, null);

                //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
                //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    //android upload file to server
    public int uploadFile(final String selectedFilePath){

        int serverResponseCode = 0;
        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead,bytesAvailable,bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length-1];

        if (!selectedFile.isFile()){
            dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        }else{
            try{
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file",selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer,0,bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0){
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer,0,bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvFileName.setText("File Upload completed.");
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();
                callalways();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddDescription.this,"File Not Found",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(AddDescription.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(AddDescription.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            return serverResponseCode;
        }


    }

    @Override
    public void asyncResponse1(String response) {

    }
     public void callalways()
     {
         SharedPreferences sh1=getSharedPreferences("LoginAuth", Context.MODE_PRIVATE);
         uid=sh1.getString("USERID",null);
         AsyncRequest2 getPosts11 = new AsyncRequest2(AddDescription.this, "GET");
         Intent i=getIntent();int pid=i.getIntExtra("productid",0);
         if(Orders.wait==true)
         {
             getPosts11.execute(requestURL +"&pid="+pid+"&uid="+Integer.valueOf(uid));
         }
         else if(Orders.complete==true)
         {
             getPosts11.execute(requestURL1 +"&pid="+pid+"&uid="+Integer.valueOf(uid));
         }

         Intent i22=new Intent(getApplicationContext(), Orders.class);
         startActivity(i22);

     }
}