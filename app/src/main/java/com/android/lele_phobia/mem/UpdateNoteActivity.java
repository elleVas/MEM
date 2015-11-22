package com.android.lele_phobia.mem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class UpdateNoteActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_update_note);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        String strvalue = extras.getString("TAG_NOTA");

        final TextView mTextView = (TextView) findViewById(R.id.invDescription);
        mTextView.setText(strvalue);

    }

    public void updateNota(View view) {
        myDb = new DatabaseHelper(this);
        editNota = (EditText) findViewById(R.id.invDescription);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        
        //recupero l'id della nota

        String strId = extras.getString("TAG_ID");
        int idNota = Integer.parseInt(strId);


        boolean isInserted = myDb.updateNota(idNota, editNota.getText().toString(), giorno + "/" + mese + "/" + anno + " " + ore + ":" + min + ":" + sec);
        if (isInserted = true) {
            Toast.makeText(UpdateNoteActivity.this, "Nota aggiornata con successo", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(UpdateNoteActivity.this, "Errore nell'aggiornamento della nota", Toast.LENGTH_LONG).show();
        }

    }

}
