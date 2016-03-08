package com.tbvanderleystudios.starbuzz;

/**
 * Created by tylervanderley on 3/8/16.
 */
public class Drink {
    private String mName;
    private String mDescription;
    private int mImageResourceId;

    // Each drink has a name, description, and an image resource
    private Drink(String name, String description, int imageResourceId) {
        mName = name;
        mDescription = description;
        mImageResourceId = imageResourceId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        mImageResourceId = imageResourceId;
    }

    public String toString() {
        return mName;
    }

    // drinks is an array of Drinks
    public static final Drink[] drinks = {
        new Drink("Latte", "A couple of espresso shots with steamed milk", R.drawable.latte),
        new Drink("Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino),
        new Drink("Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter)
    };
}