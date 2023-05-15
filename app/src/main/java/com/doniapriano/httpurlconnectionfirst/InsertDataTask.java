package com.doniapriano.httpurlconnectionfirst;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InsertDataTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];
        String data = strings[1];

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);

            try {
                // Buat objek JSONObject dari string JSON
                JSONObject jsonObj = new JSONObject(urlString);

                // Dapatkan array "data" dari objek JSONObject
                JSONArray dataArray = jsonObj.getJSONArray("data");

                // Buat objek JSONObject baru untuk data baru yang akan dimasukkan
                JSONObject newObject = new JSONObject();
                newObject.put("nis","data");

                // Tambahkan objek baru ke dalam array "data"
                dataArray.put(newObject);

                // Ubah pesan menjadi "Data berhasil ditambahkan"
                jsonObj.put("pesan", "Data berhasil ditambahkan");

                // Kirim objek JSONObject yang sudah diubah ke server melalui permintaan HTTP POST atau PUT
                // ...

            } catch (JSONException e) {
                e.printStackTrace();
            }

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(data.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                return convertStreamString(inputStream);
            } else {
                return "Error";
            }

        } catch (Exception e) {
            Log.e("Error",e.getMessage(),e);
            return "Error";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public String convertStreamString(InputStream inputStream) {
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
