package com.tbvanderleystudios.starbuzz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // Get the extra from the Intent
        int drinkNo = (Integer) getIntent().getExtras().get(DrinkCategoryActivity.EXTRA_DRINK_ID_TAG);

        try {
            // StarbuzzDatabaseHelper is created
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            // starbuzzDatabaseHelper creates a SQLiteDatabase object called db
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();

            // The Cursor is created by using the SQLiteDatabase query() method
            Cursor cursor = db.query(
                    "DRINK",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[]{Integer.toString(drinkNo)},
                    null,
                    null,
                    null
            );

            // Move to first entry found by cursor
            if (cursor.moveToFirst()) {
                // Get drink details from cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int imageResourceId = cursor.getInt(2);

                // Establish connection between Java file and XML's views
                ImageView drinkImageView = (ImageView) findViewById(R.id.drinkImageView);
                TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
                TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);

                // Populate the UI with the data from the cursor
                nameTextView.setText(nameText);
                descriptionTextView.setText(descriptionText);
                drinkImageView.setImageResource(imageResourceId);
                drinkImageView.setContentDescription(descriptionText);

            }

            // Close the cursor and database
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "The database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
