package com.example.davic.wanoalarm;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class WANOAlarm extends AppCompatActivity {

    Handler handler = new Handler();
    Handler handler1 = new Handler();
    Calendar c = new GregorianCalendar();
    TextView horaYa;
    TextView alarmaText;
    String horita;
    int hora,minut,segun;
    EditText eth;
    EditText etm;
    EditText ets;
    Integer horas = c.get(Calendar.HOUR_OF_DAY), minutos = c.get(Calendar.MINUTE), segundos = c.get(Calendar.SECOND), milesimas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanoalarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        horaYa = (TextView) findViewById(R.id.HoraTxt);
        eth = (EditText) findViewById(R.id.HorsTxt);
        etm = (EditText) findViewById(R.id.MinsTxt);
        ets = (EditText) findViewById(R.id.SegsTxt);
        alarmaText = (TextView) findViewById(R.id.textAlarma);
        Hilisho();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public Runnable ejecutarReloj = new Runnable() {
        @Override
        public void run() {
            try {
                horaYa.setText(horita);
            } catch (Exception e) {
                Log.d("msn", "Excepción en " + e);
            }

        }
    };

    public Runnable ejecutarAlarma = new Runnable() {
        @Override
        public void run() {
            try{
                alarmaText.setText("¡DESPIERTA O REPRUEBAS!");
                alarmaText.setBackgroundColor(Color.rgb(255, 10, 9));
                alarmaText.setTextColor(Color.rgb(255, 255, 255));
                final ProgressDialog ring = ProgressDialog.show(WANOAlarm.this,"¿Funciona?","SIIIII!",false);
                ring.setCancelable(true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ring.dismiss();
                    }
                }).start();
            } catch (Exception e){
                Log.d("msn", "Excepción en " + e);
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wanoalarm, menu);
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

    public void Hilisho(){

        Thread t = new Thread(){
            @Override
            public void run() {

                String min, seg, hor;
                try {
                    for (int i=0; i<=10000; i++) {

                        Thread.sleep(1000);
                        milesimas += 1000;

                        if (milesimas == 1000) {
                            milesimas = 0;
                            segundos += 1;
                            if (segundos == 60) {
                                segundos = 0;
                                minutos++;
                                if (minutos == 60) {
                                    minutos = 0;
                                    horas += 1;
                                }
                            }

                            if (minutos < 10) min = "0" + minutos;
                            else min = String.valueOf(minutos);
                            if (segundos < 10) seg = "0" + segundos;
                            else seg = String.valueOf(segundos);
                            if (horas < 10) hor = "0" + horas;
                            else hor = String.valueOf(horas);

                            horita = hor+":"+min+":"+seg;
                            Log.d("msn", horita);
                            handler.post(ejecutarReloj);

                            if(hora == horas && minut == minutos && segun == segundos) {
                                Log.d("msn", "Alarma Prueba");
                                handler1.post(ejecutarAlarma);
                            }
                        }

                    }
                } catch (Exception e) {
                    Log.d("msn", "Excepción en " + e);
                }

            }
        };
        t.start();
    }
    public void EstablecerAlarma(View view){
        hora = Integer.parseInt(String.valueOf(eth.getText()));
        minut = Integer.parseInt(String.valueOf(etm.getText()));
        segun = Integer.parseInt(String.valueOf(ets.getText()));
        Log.d("msn", "Alarma: " + hora + ":" + minut + ":" + segun);
    }
}
