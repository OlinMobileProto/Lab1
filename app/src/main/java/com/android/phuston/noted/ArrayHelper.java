package com.android.phuston.noted;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by patrick on 9/7/15.
 */
public class ArrayHelper {
    Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public ArrayHelper(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
    }

    public void saveArray(String key, ArrayList<String> array) {
        JSONArray jArray = new JSONArray(array);
        editor.remove(key);
        editor.putString(key, jArray.toString());
        editor.commit();
    }

    public ArrayList<String> getArray(String key) {
        ArrayList<String> array = new ArrayList<String>();
        String jsonArrayString = prefs.getString(key, "NO_PREF_SAVED");
        if(jsonArrayString == "NO_PREF_SAVED"){
            return getDefaultArray();
        } else {
            try {
                JSONArray jsonArray = new JSONArray(jsonArrayString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    array.add(jsonArray.getString(i));
                }
                return array;
            } catch (JSONException e) {
                return getDefaultArray();
            }
        }
    }

    public ArrayList<String> getDefaultArray(){
        ArrayList<String> array = new ArrayList<String>();
        array.add("Add a new note!");
        array.add("It's super easy, just press the '+' button below!");

        return array;
    }
}
