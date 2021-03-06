package com.tbvanderleystudios.starbuzz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    private ImageView mDrinkImageView;
    private TextView mNameTextView;
    private TextView mDescriptionTextView;
    private CheckBox mFavoriteCheckBox;
    private SQLiteOpenHelper mStarbuzzDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // Get the extra from the Intent
        final int drinkNo = (Integer) getIntent().getExtras().get(DrinkCategoryActivity.EXTRA_DRINK_ID_TAG);


        // StarbuzzDatabaseHelper is created
        mStarbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);

        try {
            // starbuzzDatabaseHelper creates a SQLiteDatabase object called db
            SQLiteDatabase db = mStarbuzzDatabaseHelper.getReadableDatabase();

            // The Cursor is created by using the SQLiteDatabase query() method
            Cursor cursor = db.query(
                    "DRINK",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
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
                boolean isFavorite = (cursor.getInt(3) == 1);

                // Establish connection between Java file and XML's views
                mDrinkImageView = (ImageView) findViewById(R.id.drinkImageView);
                mNameTextView = (TextView) findViewById(R.id.nameTextView);
                mDescriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
                mFavoriteCheckBox = (CheckBox) findViewById(R.id.favoriteCheckBox);

                // Populate the UI with the data from the cursor
                mNameTextView.setText(nameText);
                mDescriptionTextView.setText(descriptionText);
                mDrinkImageView.setImageResource(imageResourceId);
                mDrinkImageView.setContentDescription(descriptionText);
                mFavoriteCheckBox.setChecked(isFavorite);
            }

            // Close the cursor and database
            cursor.close();
            db.close();



        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "The database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


        // Allow the CheckBox to update the database when it's clicked.
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateDrinkTask().execute(drinkNo);
            }
        };

        mFavoriteCheckBox.setOnClickListener(listener);

    }

    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {

        ContentValues drinkValues;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mFavoriteCheckBox = (CheckBox) findViewById(R.id.favoriteCheckBox);
            drinkValues = new ContentValues();
            drinkValues.put("FAVORITE", mFavoriteCheckBox.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... drinks) {
            int drinkNo = drinks[0];

            mStarbuzzDatabaseHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);

            try {
                SQLiteDatabase dbWritable = mStarbuzzDatabaseHelper.getWritableDatabase();

                dbWritable.update("DRINK",
                        drinkValues,
                        "_id = ?",
                        new String[]{Integer.toString(drinkNo)});

                dbWritable.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(!success) {
                Toast toast = Toast.makeText(DrinkActivity.this, "Database unavailable", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
