package com.android.lele_phobia.mem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);


        //qui la lista delle note
        SimpleCursorAdapter dataAdapter;
        Cursor cursor = myDb.selNote();

        // The desired columns to be bound
        String[] columns = new String[] {
                DatabaseHelper.COL_1,
                DatabaseHelper.COL_4,
                DatabaseHelper.COL_8
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.id_nota,
                R.id.data,
                R.id.titoloNota,

        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.row,
                cursor,
                columns,
                to,
                1);

        ListView listView = (ListView) findViewById(R.id.listViewDemo);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setClickable(true);
        final ListView test = ((ListView) findViewById(R.id.listViewDemo));



        test.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //private String value;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // Toast.makeText(MainActivity.this, "hai cliccato alla posizione: " + position + " con id:" + id, Toast.LENGTH_LONG).show();
                DatabaseHelper myDbHelper2 = new DatabaseHelper(getApplicationContext());
                myDbHelper2.open();

                Cursor cursor = (Cursor) test.getItemAtPosition(position);


                if (cursor != null) {
                    //cursor.moveToFirst();

                    String id_nota = cursor.getString(cursor.getColumnIndex("_id"));
                    String nota = cursor.getString(cursor.getColumnIndex("note"));
                    String titolo = cursor.getString(cursor.getColumnIndex("titolo"));
                    String data = cursor.getString(cursor.getColumnIndex("data"));

                    //create bundle for data in new activity
                    Bundle bundle = new Bundle();
                    bundle.putString("TAG_ID", id_nota);
                    bundle.putString("TAG_NOTA", nota);
                    bundle.putString("TAG_TITOLO", titolo);
                    bundle.putString("TAG_DATA", data);
                    // Starting new intent
                    Intent in = new Intent(getApplicationContext(), UpdateNotaActivity.class);
                    in.putExtras(bundle);
                    startActivity(in);
                }
                myDb.close();
            }

        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewNotaActivity.class);
                startActivity(intent);

                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });


    }


}

