package com.doniapriano.httpurlconnectionfirst;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetJsonTask extends AsyncTask<String,Void,String> {

    private MainActivity activity;

    public GetJsonTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = new BufferedInputStream(conn.getInputStream());
            response = convertStreamString(inputStream);
        } catch (Exception e) {
            Log.e("Error", e.getMessage(), e);
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        activity.processData(s);
    }

    private String convertStreamString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
