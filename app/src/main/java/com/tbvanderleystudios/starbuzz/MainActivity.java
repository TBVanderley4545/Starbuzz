package com.tbvanderleystudios.starbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private StarbuzzDatabaseHelper mStarbuzzDatabaseHelper;
    private SQLiteDatabase mDB;
    private Cursor mCursor;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Have to use an AdapterView for ListViews.
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Intent intent = new Intent(MainActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };

        // Set our ListView to use the onItemClickListener that we created.
        mListView = (ListView) findViewById(R.id.listOptions);
        mListView.setOnItemClickListener(itemClickListener);


        // Get a reference to the favorite ListView
        ListView favoritesListView = (ListView) findViewById(R.id.listFavorites);

        try {
            // Create our new cursor
            mStarbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            mDB = mStarbuzzDatabaseHelper.getReadableDatabase();
            mCursor = mDB.query("DRINK",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = ?",
                    new String[]{Integer.toString(1)},
                    null,
                    null,
                    null
            );

            // Create our new CursorAdapter
            CursorAdapter listAdapter = new SimpleCursorAdapter(
                    MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    mCursor,
                    new String[] {"NAME"},
                    new int[] {android.R.id.text1},
                    0
            );

            favoritesListView.setAdapter(listAdapter);

        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }

        // Navigate to a drinkActivity if it is clicked
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkCategoryActivity.EXTRA_DRINK_ID_TAG, (int) id);
                startActivity(intent);
                }
            };

        favoritesListView.setOnItemClickListener(onItemClickListener);

        Toast.makeText(this, "Test", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCursor.close();
        mDB.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        try {
            mStarbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            mDB = mStarbuzzDatabaseHelper.getReadableDatabase();
            // Create a new Cursor
            Cursor newCursor = mDB.query(
                    "DRINK",
                    new String[] {"_id", "NAME"},
                    "FAVORITE = ?",
                    new String[] {Integer.toString(1)},
                    null,
                    null,
                    null
            );

            mListView = (ListView) findViewById(R.id.listFavorites);

            CursorAdapter adapter = (CursorAdapter) mListView.getAdapter();
            adapter.changeCursor(newCursor);
            mCursor = newCursor;
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}



