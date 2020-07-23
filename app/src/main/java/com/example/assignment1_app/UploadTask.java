package com.example.assignment1_app;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class UploadTask extends AsyncTask<String,String,String> {

    public static final String DB_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DB_name = "logindb";
    public static final String TAG = "FROM_UPLOADTASK";
    @Override
    protected String doInBackground(String... strings) {
        try {
            String url = "http://10.218.107.121/cse535/upload_video.php";
            String charset = "UTF-8";
            String group_id = "3";
            String ASUid = "1217969285";
            String accept = "1";

            File dbFile = new File(DB_FILE_PATH + File.separator + DB_name);
            String boundary = Long.toHexString(System.currentTimeMillis());
            String CRLF = "\r\n";

            URLConnection connection;
            connection = new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (
                    OutputStream output = connection.getOutputStream();
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
            ) {
                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"accept\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(accept).append(CRLF).flush();

                // Send normal accept.
                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"id\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(ASUid).append(CRLF).flush();

                // Send normal accept.
                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"group_id\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(group_id).append(CRLF).flush();


                // Send db file.
                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + dbFile.getName() + "\"").append(CRLF);
                writer.append("Content-Type: multipart/form-data; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
                writer.append(CRLF).flush();
                FileInputStream vf = new FileInputStream(dbFile);
                try {
                    byte[] buffer = new byte[1024];
                    int bytesRead = 0;
                    while ((bytesRead = vf.read(buffer, 0, buffer.length)) >= 0) {
                        output.write(buffer, 0, bytesRead);
                    }
                } catch (Exception exception) {
                    Log.d("Error", String.valueOf(exception));
                    publishProgress(String.valueOf(exception));
                }

                output.flush();
                writer.append(CRLF).flush();
                writer.append("--" + boundary + "--").append(CRLF).flush();


            }
            int responseCode = ((HttpURLConnection) connection).getResponseCode();
            Log.d(TAG,"The response code is " + responseCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onProgressUpdate(String... text) {
        Log.d(TAG,"File has been uploaded !");
    }

}
