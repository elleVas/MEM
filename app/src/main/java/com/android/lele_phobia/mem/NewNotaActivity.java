package com.android.lele_phobia.mem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.hardware.camera2.CameraDevice;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NewNotaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Uri photoPath;
    ImageView imgFoto;
    static int TAKE_PICTURE = 1;


    DatabaseHelper myDb;
    EditText editTitolo;
    EditText editNota;

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
        setContentView(R.layout.activity_new_nota);
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



    }


    public void newNota(NewNotaActivity view) {
        myDb = new DatabaseHelper(this);
        editNota = (EditText) findViewById(R.id.invDescription);
        editTitolo = (EditText) findViewById(R.id.titoloNota);


        boolean isInserted = myDb.insertNota(1, editNota.getText().toString(), currentDateandTime, editTitolo.getText().toString());
        if (isInserted = true) {
            Toast.makeText(NewNotaActivity.this, "Saved successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(NewNotaActivity.this, "Error data not save", Toast.LENGTH_LONG).show();
        }

    }

    public void deleteNota(NewNotaActivity view) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
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
        getMenuInflater().inflate(R.menu.new_nota, menu);
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
                newNota(this);
                return true;
            case R.id.action_delete:
                deleteNota(this);
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
           // Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
           // startActivity(intent);
            editNota = (EditText) findViewById(R.id.invDescription);
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PICTURE);




        } else if (id == R.id.nav_gallery) {
            Toast.makeText(NewNotaActivity.this, "gallery", Toast.LENGTH_LONG).show();


        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(NewNotaActivity.this, "slide", Toast.LENGTH_LONG).show();


        } else if (id == R.id.nav_manage) {
            Toast.makeText(NewNotaActivity.this, "manage", Toast.LENGTH_LONG).show();


        } else if (id == R.id.nav_share) {
            Toast.makeText(NewNotaActivity.this, "share", Toast.LENGTH_LONG).show();


        } else if (id == R.id.nav_send) {
            Toast.makeText(NewNotaActivity.this, "send", Toast.LENGTH_LONG).show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {


        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap)intent.getExtras().get("data");
            imgFoto.setImageBitmap(photo);
            imgFoto.setVisibility(View.VISIBLE);



        }
    }
}
