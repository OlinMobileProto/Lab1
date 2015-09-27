package com.mtruehle.photostreamer;

/**
 * Created by matt on 9/22/15.
 * Contains a method which handles JSON Objects, and returns the image links.
 */
public interface Callback {
    void callback(String[] links, boolean success);
}
