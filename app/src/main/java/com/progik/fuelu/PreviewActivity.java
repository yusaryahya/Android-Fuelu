package com.progik.fuelu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.progik.fuelu.util.Config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PreviewActivity extends AppCompatActivity implements View.OnClickListener{

    private Button ib_submit, ib_back;
    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        //masukan custom action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();

        TextView tvTanggal = (TextView) findViewById(R.id.a_driver_input_tv_tanggalwaktu);

        //ambil tanggal dan waktu sistem
        Date tanggalSistem = (Date) Calendar.getInstance().getTime();

        //deklarasi format
        SimpleDateFormat formatTanggal = new SimpleDateFormat("EEEE\ndd MMMM yyyy");

        //format tanggal dari tanggalSistem
        String tanggal = formatTanggal.format(tanggalSistem);

        //tampilkan di textview
        tvTanggal.setText(tanggal.toString());

        Bundle b = getIntent().getExtras();
        //membuat obyek dari widget textview
        TextView a_preview_tv_preview = (TextView) findViewById(R.id.a_preview_tv_preview);
        TextView a_preview_tv_fuelman = (TextView) findViewById(R.id.a_preview_tv_fuelman);
        TextView a_preview_tv_fuelmanname = (TextView) findViewById(R.id.a_preview_tv_fuelmanname);
        TextView a_preview_tv_fuelmanid = (TextView) findViewById(R.id.a_preview_tv_fuelmanid);
        TextView a_preview_tv_operator = (TextView) findViewById(R.id.a_preview_tv_operator);
        TextView a_preview_tv_operatorname = (TextView) findViewById(R.id.a_preview_tv_operatorname);
        TextView a_preview_tv_operatorid = (TextView) findViewById(R.id.a_preview_tv_operatorid);
        TextView a_preview_tv_bodyno = (TextView) findViewById(R.id.a_preview_tv_bodyno);
        TextView a_preview_tv_vehicleid = (TextView) findViewById(R.id.a_preview_tv_vehicleid);
        TextView a_preview_tv_vehicletype = (TextView) findViewById(R.id.a_preview_tv_vehicletype);
        TextView a_preview_tv_kmunit = (TextView) findViewById(R.id.a_preview_tv_kmunit);
        TextView a_preview_tv_isi = (TextView) findViewById(R.id.a_preview_tv_isi);
        TextView a_preview_tv_kontraktor = (TextView) findViewById(R.id.a_preview_tv_kontraktor);
        TextView a_preview_tv_pengawas = (TextView) findViewById(R.id.a_preview_tv_pengawas);
        TextView a_preview_tv_informasi = (TextView) findViewById(R.id.a_preview_tv_informasi);

        a_preview_tv_fuelman.setText(b.getCharSequence("fuelmannip"));
        a_preview_tv_fuelmanname.setText(b.getCharSequence("fuelmanname"));
        a_preview_tv_fuelmanid.setText(b.getCharSequence("fuelmanid"));
        a_preview_tv_operator.setText(b.getCharSequence("operatornip"));
        a_preview_tv_operatorname.setText(b.getCharSequence("drivername"));
        a_preview_tv_operatorid.setText(b.getCharSequence("driverid"));
        a_preview_tv_bodyno.setText(b.getCharSequence("bodyno"));
        a_preview_tv_vehicletype.setText(b.getCharSequence("unit"));
        a_preview_tv_vehicleid.setText(b.getCharSequence("vehicleid"));
        a_preview_tv_kmunit.setText(b.getCharSequence("kmunit"));
        a_preview_tv_isi.setText(b.getCharSequence("isi"));
        a_preview_tv_kontraktor.setText(b.getCharSequence("kontraktor"));
        a_preview_tv_pengawas.setText(b.getCharSequence("pengawas"));
        a_preview_tv_informasi.setText(b.getCharSequence("informasi"));

        /**
         *

        a_preview_tv_preview.setText("Fuelman : " + b.getCharSequence("fuelmanid") + "\n" +
                "Operator : " + b.getCharSequence("driverid") + "\n" +
                "No. Lambung : " + b.getCharSequence("vehicleid") + "\n" +
                "KM Unit : " + b.getCharSequence("kmunit") + "\n" +
                "Isi : " + b.getCharSequence("isi") + " liter\n" +
                "Kontraktor : " + b.getCharSequence("kontraktor") + "\n" +
                "Pengawas : " + b.getCharSequence("pengawas") + "\n" +
                "Informasi : " + b.getCharSequence("informasi"));
         */
        //ib_submit = (ImageButton) findViewById(R.id.a_preview_ib_submit);
        ib_submit = (Button) findViewById(R.id.a_preview_ib_submit);
        ib_submit.setOnClickListener(this);

        ib_back = (Button) findViewById(R.id.a_preview_ib_back);
        ib_back.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v == ib_submit){
            submitPengisian();
        }
        else if (v == ib_back){
            finish();
        }
    }

    private void submitPengisian(){
        Date tanggalSistem = (Date) Calendar.getInstance().getTime();
        SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String tanggalrefuel = formatTanggal.format(tanggalSistem).toString().trim();

        TextView a_preview_tv_preview = (TextView) findViewById(R.id.a_preview_tv_preview);
        TextView a_preview_tv_fuelman = (TextView) findViewById(R.id.a_preview_tv_fuelman);
        TextView a_preview_tv_fuelmanname = (TextView) findViewById(R.id.a_preview_tv_fuelmanname);
        TextView a_preview_tv_fuelmanid = (TextView) findViewById(R.id.a_preview_tv_fuelmanid);
        TextView a_preview_tv_operator = (TextView) findViewById(R.id.a_preview_tv_operator);
        TextView a_preview_tv_operatorname = (TextView) findViewById(R.id.a_preview_tv_operatorname);
        TextView a_preview_tv_operatorid = (TextView) findViewById(R.id.a_preview_tv_operatorid);
        TextView a_preview_tv_bodyno = (TextView) findViewById(R.id.a_preview_tv_bodyno);
        TextView a_preview_tv_vehicleid = (TextView) findViewById(R.id.a_preview_tv_vehicleid);
        TextView a_preview_tv_vehicletype = (TextView) findViewById(R.id.a_preview_tv_vehicletype);
        TextView a_preview_tv_kmunit = (TextView) findViewById(R.id.a_preview_tv_kmunit);
        TextView a_preview_tv_isi = (TextView) findViewById(R.id.a_preview_tv_isi);
        TextView a_preview_tv_kontraktor = (TextView) findViewById(R.id.a_preview_tv_kontraktor);
        TextView a_preview_tv_pengawas = (TextView) findViewById(R.id.a_preview_tv_pengawas);
        TextView a_preview_tv_informasi = (TextView) findViewById(R.id.a_preview_tv_informasi);

        final String fuelmanid = a_preview_tv_fuelmanid.getText().toString().trim();
        final String fuelmanname = a_preview_tv_fuelmanname.getText().toString().trim();
        final String driverid = a_preview_tv_operatorid.getText().toString().trim();
        final String drivername = a_preview_tv_operatorname.getText().toString().trim();
        final String vehicleid = a_preview_tv_vehicleid.getText().toString().trim();
        final String unit= a_preview_tv_vehicletype.getText().toString().trim();
        final String kmunit = a_preview_tv_kmunit.getText().toString().trim();
        final String isi = a_preview_tv_isi.getText().toString().trim();
        final String kontraktor = a_preview_tv_kontraktor.getText().toString().trim();
        final String pengawas = a_preview_tv_pengawas.getText().toString().trim();
        final String informasi = a_preview_tv_informasi.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.INSERT_PENGISIAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(DriverInputActivity.this,response,Toast.LENGTH_LONG).show();

                        //clearText();


                        Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                        //Membuat obyek bundle
                        //Bundle b = new Bundle();

                        //Menambahkan bundle intent
                        //intent.putExtras(b);

                        //memulai Activity kedua
                        startActivity(intent);
                        finish();

                        //dialog = new AlertDialog.Builder(PreviewActivity.this);
                        //dialog.setCancelable(true);
                        //dialog.show();
                        //return false;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PreviewActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(Config.TAG_TANGGALREFUEL, tanggalrefuel);
                params.put(Config.TAG_FUELMANID, fuelmanid);
                params.put(Config.TAG_FUELMANNAME, fuelmanname);
                params.put(Config.TAG_DRIVERID, driverid);
                params.put(Config.TAG_VEHICLEID, vehicleid);
                params.put(Config.TAG_VEHICLETYPENAME, unit);
                params.put(Config.TAG_KMUNIT, kmunit);
                params.put(Config.TAG_ISI, isi);
                params.put(Config.TAG_KONTRAKTOR, kontraktor);
                params.put(Config.TAG_PENGAWAS, pengawas);
                params.put(Config.TAG_INFORMASI, informasi);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /*private void DialogForm(String button) {
        dialog = new AlertDialog.Builder(PreviewActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_status, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
    } */
}
