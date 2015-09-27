package com.mtruehle.photostreamer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {
    public HttpHandler handler;

    public SearchFragment() {
        // apparently this empty public constructor is required.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout searchLayout = (LinearLayout) inflater.inflate(R.layout.search_fragment,
                                                                        container, false); // gets the layout identified by R.layout.search_fragment, sets that as the view for this. A way to "summon" the xml file layout thing, I believe.
        Button searchButton = (Button) searchLayout.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchField = (EditText) getActivity().findViewById(R.id.search_box);
                String thingToSearch = searchField.getText().toString();
                imageSearch(thingToSearch);
                Log.i("PRINTER", "testing");
            }
        });
        handler = new HttpHandler(getActivity());
        return searchLayout;
    }

    public void imageSearch(String searchQuery) {
        Toast.makeText(getActivity(), "Searching for \"" + searchQuery + "\"...", Toast.LENGTH_SHORT).show(); // Will probably erase this for the final version; right now it's useful having this feedback while debugging.
        handler.imageSearch(searchQuery, new Callback() {
            @Override
            public void callback(String[] links, boolean success) {
                if (!success) {
                    Log.e("PRINTER", "returned False");
                }
                for (int i = 0; i < links.length; i++) {
                    Log.i("PRINTER", links[i]);
                }
            }

        });

    }
}
