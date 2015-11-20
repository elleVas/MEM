package com.android.lele_phobia.mem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NewNoteActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editNota;
    GregorianCalendar gc = new GregorianCalendar();
    int anno = gc.get(Calendar.YEAR);
    int mese = gc.get(Calendar.MONTH) + 1;
    int giorno = gc.get(Calendar.DATE);
    int ore = gc.get(Calendar.HOUR);
    int min = gc.get(Calendar.MINUTE);
    int sec = gc.get(Calendar.SECOND);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        myDb = new DatabaseHelper(this);


    }

    public void newNota(View view) {
        myDb = new DatabaseHelper(this);
        editNota = (EditText) findViewById(R.id.invDescription);


        boolean isInserted = myDb.insertNota(1, editNota.getText().toString(), giorno + "/" + mese + "/" + anno + " " + ore + ":" + min + ":" + sec);
        if (isInserted = true) {
            Toast.makeText(NewNoteActivity.this, "Nota inserita con successo", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(NewNoteActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
        }

    }
}
