package com.tbvanderleystudios.starbuzz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // Get the extra from the Intent
        int drinkNo = (Integer) getIntent().getExtras().get(DrinkCategoryActivity.EXTRA_DRINK_ID_TAG);

        // Get the data from our drink array at the select index
        Drink drink = Drink.drinks[drinkNo];

        // Establish connection between Java file and XML's views
        ImageView drinkImageView = (ImageView) findViewById(R.id.drinkImageView);
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);

        // set the views' information to the currently selected drink.
        drinkImageView.setImageResource(drink.getImageResourceId());
        drinkImageView.setContentDescription(drink.getName());
        nameTextView.setText(drink.getName());
        descriptionTextView.setText(drink.getDescription());
    }
}
