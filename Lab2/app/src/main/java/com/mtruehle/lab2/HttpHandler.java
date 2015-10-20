package com.mtruehle.lab2;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

/**
 * Created by matt on 9/17/15.
 */
public class HttpHandler {

    public RequestQueue queue;

    public HttpHandler(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void searchSpotify(String  searchQuery) {
        String query = searchQuery.replaceAll(" ", "+");
        String url = "https://api.spotify.com/v1/search";
        url = url + "?q=" + query;
        url = url + "&type=track";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Success", "woohoo");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR!!!!", "error");
                    }
                })
    }
}
