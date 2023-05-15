package com.doniapriano.httpurlconnectionfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tvSatu;
    Button btn;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSatu = findViewById(R.id.satu);
        btn = findViewById(R.id.btn);
        editText = findViewById(R.id.et_nama);

        new GetJsonTask(this).execute("http://190.100.2.50/laundryhehe/retrive.php");

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String data = editText.getText().toString();
//                new InsertDataTask().execute("http://192.168.43.36/laundryhehe/create.php", data);
//            }
//        });

    }

    public void processData(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                // dapatkan nilai dari setiap kunci pada JSONObject
                String value1 = jsonObject1.getString("nis");
                String value2 = jsonObject1.getString("nama");
                // tampilkan nilai pada TextView
                tvSatu.setText(value1+value2);
            }
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
        }
    }

}