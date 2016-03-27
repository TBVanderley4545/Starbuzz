package com.tbvanderleystudios.starbuzz;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategoryActivity extends ListActivity {

    public static final String EXTRA_DRINK_ID_TAG = "drinkNo";

    private SQLiteDatabase mDB;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listDrinks = getListView();

        try {
            // StarbuzzDatabaseHelper is created
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            // starbuzzDatabaseHelper creates a SQLiteDatabase object called mDB
            mDB = starbuzzDatabaseHelper.getReadableDatabase();

            // The Cursor is created by using the SQLiteDatabase query() method
            mCursor = mDB.query(
                    "DRINK",
                    new String[] {"_id", "NAME"},
                    null,
                    null,
                    null,
                    null,
                    null
            );

            CursorAdapter listAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    mCursor,
                    new String[] {"NAME"},
                    new int[] {android.R.id.text1},
                    0
            );

            listDrinks.setAdapter(listAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        intent.putExtra(EXTRA_DRINK_ID_TAG, (int) id);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCursor.close();
        mDB.close();
    }
}
