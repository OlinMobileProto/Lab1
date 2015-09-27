package com.mtruehle.photostreamer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpHandler {
    public RequestQueue queue;
    private static final String BASE_URL = "https://www.googleapis.com/customsearch/v1";
    private static final String API_KEY = "AIzaSyBZXarOKz47LVAAWWLYD7zPeFwo7l5PlDg";
    private static final String SEARCH_ENGINE_ID = "012460441176894959753:_00wxhxtfok";


    public HttpHandler(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void imageSearch(String searchQuery, Callback callback) {
        final Callback staticCallback = callback;
        searchQuery = searchQuery.replaceAll(" ", "+");
        String URL = BASE_URL + "?key=" + API_KEY + "&cx=" + SEARCH_ENGINE_ID + "&q=" + searchQuery + "&searchType=image";
        JSONObject body = new JSONObject(); // not sure what this is for. will try to find out later.
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String[] imageLinkList = handleJson(response);
                        staticCallback.callback(imageLinkList, true);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String[] emptyList = new String[2]; // callback needs it; doesn't get used.
                        staticCallback.callback(emptyList, false);
                    }
                }
        );
        queue.add(getRequest);
    }

    public String[] handleJson(JSONObject input) {
        String[] imageUrls = new String[10];

        try {
            JSONArray jArray = input.getJSONArray("items");
            int size = jArray.length();
            if (size > 10) {
                size = 10; // if more than 10 results get returned, limits to the first ten. Shouldn't happen, but just in case.
            }
            for (int i = 0; i < size; i++) {
                JSONObject currentJsonObject = jArray.getJSONObject(i);
                try {
                    String currentUrl = currentJsonObject.getString("link");
                    imageUrls[i] = currentUrl;
                } catch (JSONException ex) {
                    String currentUrl = "PASS"; // flags the callback to skip this item.
                    imageUrls[i] = currentUrl;
                    Log.e("Error", ex.getMessage());
                }
            }
        } catch (JSONException ex) {
            Log.e("Error", ex.getMessage());
            imageUrls[0] = "ERROR"; // flags the callback to ignore all items.
            imageUrls[1] = "ERROR";
            imageUrls[2] = "ERROR"; // probably unnecessary extra declarations.
        }
        Log.i("PRINTER", imageUrls[0] + "\t" + imageUrls[1] + "\t" + imageUrls[2] + "...");
        return imageUrls;
    }


}
