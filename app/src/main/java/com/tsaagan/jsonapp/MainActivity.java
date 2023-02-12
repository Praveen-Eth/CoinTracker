package com.tsaagan.jsonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button button;
    String coin = "default coin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.text);
        button  = findViewById(R.id.button1);


        FirstFragment fragment = new FirstFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame,fragment).commit();
    //    getSupportFragmentManager().executePendingTransactions();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.coingecko.com/api/v3/coins/list?include_platform=true";


        button.setOnClickListener(v-> {
            TextView tv = fragment.getView().findViewById(R.id.textInFragment);
            tv.setText("hello pal");
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.toString());
                        for (int i = 0; i < 10; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(1);
                            String id = jsonObject.getString("id");
                            String symbol = jsonObject.getString("symbol");
                            String name = jsonObject.getString("name");
                            JSONObject platforms = jsonObject.getJSONObject("platforms");
                            tv.append(id+symbol+name+platforms+"\n");

                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(jsonArrayRequest);
        });



    }

}