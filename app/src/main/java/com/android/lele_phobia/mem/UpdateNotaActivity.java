package com.android.lele_phobia.mem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UpdateNotaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper myDb;
    EditText editNota;
    EditText editTitolo
            ;
    GregorianCalendar gc = new GregorianCalendar();
    int anno = gc.get(Calendar.YEAR);
    int mese = gc.get(Calendar.MONTH) + 1;
    int giorno = gc.get(Calendar.DATE);
    int ore = gc.get(Calendar.HOUR);
    int min = gc.get(Calendar.MINUTE);
    int sec = gc.get(Calendar.SECOND);
    String currentDateandTime = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nota);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        String strvalue = extras.getString("TAG_NOTA");
        String strTitle = extras.getString("TAG_TITOLO");

        final TextView mTextView = (TextView) findViewById(R.id.invDescription);
        mTextView.setText(strvalue);
        final TextView mTextViewTitle = (TextView) findViewById(R.id.titoloNota);
        mTextViewTitle.setText(strTitle);


    }

    public void updateNota(UpdateNotaActivity view) {
        myDb = new DatabaseHelper(this);
        editNota = (EditText) findViewById(R.id.invDescription);
        editTitolo = (EditText) findViewById(R.id.titoloNota);
        Intent i = getIntent();
        Bundle extras = i.getExtras();

        //recupero l'id della nota

        String strId = extras.getString("TAG_ID");
        int idNota = Integer.parseInt(strId);


        boolean isInserted = myDb.updateNota(idNota, editNota.getText().toString(), currentDateandTime, editTitolo.getText().toString());
        if (isInserted = true) {
            Toast.makeText(UpdateNotaActivity.this, "Update successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(UpdateNotaActivity.this, "Error on update", Toast.LENGTH_LONG).show();

        }

    }

    public void deleteNota(UpdateNotaActivity view) {
        myDb = new DatabaseHelper(this);
        editNota = (EditText) findViewById(R.id.invDescription);
        editTitolo = (EditText) findViewById(R.id.titoloNota);
        Intent i = getIntent();
        Bundle extras = i.getExtras();

        //recupero l'id della nota

        String strId = extras.getString("TAG_ID");
        int idNota = Integer.parseInt(strId);


        boolean isInserted = myDb.deleteNota(idNota);
        if (isInserted = true) {
            Toast.makeText(UpdateNotaActivity.this, "Delete successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(UpdateNotaActivity.this, "Error on delete", Toast.LENGTH_LONG).show();

        }

    }
    public void deleteAlert(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Delete Note");

        // set dialog message
        alertDialogBuilder
                .setMessage("Click yes to delete the note!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        deleteNota(UpdateNotaActivity.this);
                        // if this button is clicked, close
                        // current activity
                        UpdateNotaActivity.this.finish();

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

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
        getMenuInflater().inflate(R.menu.update_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_salva:
                updateNota(this);
                return true;
            case R.id.action_delete:
                deleteAlert();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
     }
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
