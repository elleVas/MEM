package com.android.lele_phobia.mem;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.database.DatabaseUtils.dumpCursorToString;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        Cursor cursor = myDb.selNote();



        //qui si visualizza anche la lista delle note

        System.out.println(dumpCursorToString(cursor));
        // Find ListView to populate
        ListView listView = (ListView) findViewById(R.id.listViewDemo);

        cursor.moveToFirst();
        ArrayList<String> names = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
                names.add(cursor.getString(cursor.getColumnIndex("note")));
                cursor.moveToNext();
        }
        cursor.close();

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, R.layout.row, R.id.textViewList, names);
        listView.setAdapter(arrayAdapter);


        listView.setClickable(true);
        final ListView test = ((ListView) findViewById(R.id.listViewDemo));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent,
                                                            View view, int position, long id) {

                                        // Get the cursor, positioned to the
                                        // corresponding row in the result set
                                        try (OIncome income = list.get(position);) {

                                            if (cursor != null) {
                                                //cursor.moveToFirst();
                                                // Get the state's capital from this row in the database.

                                                String track_id = cursor.getString(cursor.getColumnIndex("id"));
                                                String size = cursor.getString(cursor.getColumnIndex("id_user"));
                                                String length = cursor.getString(cursor.getColumnIndex("note"));
                                                String title = cursor.getString(cursor.getColumnIndex("data"));

                                                //create bundle for data in new activity
                                                Bundle bundle = new Bundle();

                                                bundle.putString("TAG_ID", track_id);
                                                bundle.putString("TAG_SIZE", size);
                                                bundle.putString("TAG_LENGTH", length);
                                                bundle.putString("TAG_TITLE", title);

                                                // Starting new intent
                                                Intent in = new Intent(getApplicationContext(), NewNoteActivity.class);

                                                in.putExtras(bundle);
                                                //startActivity(in);


                                                Toast.makeText(MainActivity.this, "You Clicked at " + track_id + " nota: " + length, Toast.LENGTH_SHORT).show();
                                            }
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
                                        Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
                                        startActivity(intent);

                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                                    }
                                });

                                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                                drawer.setDrawerListener(toggle);
                                toggle.syncState();

                                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                                navigationView.setNavigationItemSelectedListener(this);
                            }

                            @Override
                            public void onBackPressed() {
                                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                if (drawer.isDrawerOpen(GravityCompat.START)) {
                                    drawer.closeDrawer(GravityCompat.START);
                                } else {
                                    super.onBackPressed();
                                }
                            }

                            @Override
                            public boolean onCreateOptionsMenu(Menu menu) {
                                // Inflate the menu; this adds items to the action bar if it is present.
                                getMenuInflater().inflate(R.menu.main, menu);
                                return true;
                            }

                            @Override
                            public boolean onOptionsItemSelected(MenuItem item) {
                                // Handle action bar item clicks here. The action bar will
                                // automatically handle clicks on the Home/Up button, so long
                                // as you specify a parent activity in AndroidManifest.xml.
                                int id = item.getItemId();

                                //noinspection SimplifiableIfStatement
                                if (id == R.id.action_settings) {
                                    return true;
                                }

                                return super.onOptionsItemSelected(item);
                            }

                            @SuppressWarnings("StatementWithEmptyBody")
                            @Override
                            public boolean onNavigationItemSelected(MenuItem item) {
                                // Handle navigation view item clicks here.
                                int id = item.getItemId();

                                if (id == R.id.nav_camara) {
                                    // Handle the camera action
                                } else if (id == R.id.nav_gallery) {

                                } else if (id == R.id.nav_slideshow) {

                                } else if (id == R.id.nav_manage) {

                                } else if (id == R.id.nav_share) {

                                } else if (id == R.id.nav_send) {

                                }

                                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            }
                        }
