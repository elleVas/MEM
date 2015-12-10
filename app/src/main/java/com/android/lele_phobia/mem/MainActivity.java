package com.android.lele_phobia.mem;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private NotificationManager alarmNotificationManager;

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
        setContentView(R.layout.activity_main);
        listNote();
       // allarme();
        setAlarm();
        //SetAllarme();


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
    public void allarme() {
        System.out.println("sei dentro allarme");
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();


    }


    public void SetAllarme()
    {
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override public void onReceive( Context context, Intent _ )
            {
                fab.setBackgroundColor( Color.RED );
                context.unregisterReceiver( this ); // this == BroadcastReceiver, not Activity
            }
        };

        this.registerReceiver(receiver, new IntentFilter("com.blah.blah.somemessage"));

        PendingIntent pintent = PendingIntent.getBroadcast(this, 0, new Intent("com.blah.blah.somemessage"), 0);
        AlarmManager manager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));

        // set alarm to fire 5 sec (1000*5) from now (SystemClock.elapsedRealtime())
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000 * 5, pintent);
    }
    private void setAlarm(){

        Context context = getApplicationContext();

        AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        Calendar myCal = Calendar.getInstance();

        String currentDateandTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date dexter = sdf2.parse(currentDateandTime);
            System.out.println(dexter.getTime());

            System.out.println(sdf2.format(sdf2.parse(currentDateandTime)));

            myCal.setTimeInMillis(dexter.getTime());

            mgr.set(AlarmManager.RTC_WAKEUP, myCal.getTimeInMillis(), pi);

            System.out.println("alarm set for " + myCal.getTime().toLocaleString());

            Toast.makeText(getApplicationContext(),"Alarm set for " + myCal.getTime().toLocaleString(), Toast.LENGTH_LONG).show();

            sendNotification("Wake Up! Wake Up!");

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void sendNotification(String msg) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);


        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void listNote(){
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

    }


}

