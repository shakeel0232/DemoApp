package com.demo.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String url = "https://www.breakingbadapi.com/api/characters?limit=10";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        listView = (ListView) findViewById(R.id.listView);
        callApi();
    }

    public void callApi() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            String name;
                            String birthday;
                            String img;
                            CharListClass.CharacterList.clear();
                            try {
                                JSONArray JSONArray = new JSONArray(response);
                                for (int i = 0; i < JSONArray.length(); i++) {
                                    JSONObject jsonObject = JSONArray.getJSONObject(i);
                                    name = jsonObject.getString("name");
                                    birthday = jsonObject.getString("birthday");
//                                    birthday = jsonObject.getString("birthday").replace("Unknown", "0-0-2021");
                                    img = jsonObject.getString("img");
                                    Character mChar = new Character();
                                    mChar.setCharName(name);
                                    mChar.setCharAge(birthday);
                                    mChar.setCharUrl(img);
                                    CharListClass.CharacterList.add(mChar);

                                }
                                listView.setAdapter(new CharAdapter(getApplication(), CharListClass.CharacterList));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "No Response ", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}