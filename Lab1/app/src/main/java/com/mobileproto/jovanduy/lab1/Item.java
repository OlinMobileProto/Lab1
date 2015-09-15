package com.mobileproto.jovanduy.lab1;

/**
 * Created by Jordan on 9/14/15.
 */
public class Item {

    public String text;
    public Boolean isCompleted;

    public Item(String text, Boolean isCompleted) {
        this.text = text;
        this.isCompleted = isCompleted;
    }

    public Boolean getStatus() {
        return this.isCompleted;
    }
    public void changeStatus(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String newText) {
        this.text = newText;
    }

}
