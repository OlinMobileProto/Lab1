package com.frantonlin.grocerylist;

/**
 * Created by franton on 9/14/15.
 */
public class GroceryItem {
    private String name;
    private Boolean checked;

    public GroceryItem(String name) {
        this.name = name;
        this.checked = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void toggleChecked() {
        this.checked = !this.checked;
    }

    public Boolean getChecked() {
        return this.checked;
    }

}
