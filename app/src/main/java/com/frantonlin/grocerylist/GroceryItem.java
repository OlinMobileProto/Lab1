package com.frantonlin.grocerylist;

/**
 * Created by franton on 9/14/15.
 * Models an item in a grocery list
 */
public class GroceryItem {
    private String name;
    private Boolean checked;

    /**
     * Constructor
     * @param name the name of the grocery item
     */
    public GroceryItem(String name) {
        this.name = name;
        this.checked = false;
    }

    /**
     * Set name method
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get name method
     * @return the name of the grocery item
     */
    public String getName() {
        return this.name;
    }

    /**
     * Toggles whether or not the grocery item is checked off
     */
    public void toggleChecked() {
        this.checked = !this.checked;
    }

    /**
     * Get checked method
     * @return whether or not the grocery item is checked off
     */
    public Boolean getChecked() {
        return this.checked;
    }

}
