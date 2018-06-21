package com.progik.fuelu;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.progik.fuelu.util.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner spinnerpengisianfuelman;

    private ArrayList<String> pengisisanfuelman;

    private JSONArray resultpengisianfuelman;

    private TextView a_history_tv_berkas;
    private TextView a_history_tv_isi;

    private EditText a_history_et_berkas;

    private Button ib_back;

    public HistoryActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //masukan custom action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();

        TextView tvTanggal = (TextView) findViewById(R.id.a_history_tv_tanggalwaktu);

        //ambil tanggal dan waktu sistem
        Date tanggalSistem = (Date) Calendar.getInstance().getTime();

        //deklarasi format
        SimpleDateFormat formatTanggal = new SimpleDateFormat("EEEE\ndd MMMM yyyy");

        //format tanggal dari tanggalSistem
        String tanggal = formatTanggal.format(tanggalSistem);

        //tampilkan di textview
        tvTanggal.setText(tanggal.toString());

        pengisisanfuelman = new ArrayList<String>();

        resultpengisianfuelman = new JSONArray();

        //Initializing Spinner
        spinnerpengisianfuelman = (Spinner) findViewById(R.id.spinner_pengisianfuelman);

        spinnerpengisianfuelman.setOnItemSelectedListener(this);

        ib_back = (Button) findViewById(R.id.a_history_ib_back);
        ib_back.setOnClickListener(this);



        a_history_tv_berkas = (TextView) findViewById(R.id.a_history_tv_berkas);
        a_history_tv_isi = (TextView) findViewById(R.id.a_history_tv_totalisi);

        getPengisian();
    }

    @Override
    public void onClick(View v) {
        if (v == ib_back){
            finish();
        }
    }

    private void getPengisian(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.SELECT_PENGISIANFUELMAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            resultpengisianfuelman = j.getJSONArray(Config.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getFuelmanName(resultpengisianfuelman);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getFuelmanName(JSONArray j){
        //Traversing through all the items in the json array
        pengisisanfuelman.add("Choose Fuelman");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                pengisisanfuelman.add(json.getString(Config.TAG_FUELMANNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerpengisianfuelman.setAdapter(new ArrayAdapter<String>(HistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, pengisisanfuelman));
    }

    //Method to get student name of a particular position
    private String getBerkas(int position) {
        String berkas = "";
        try {
            //Getting object of given index
            JSONObject json = resultpengisianfuelman.getJSONObject(position);

            //Fetching name from that object
            berkas = json.getString(Config.TAG_BERKAS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return berkas;
    }


    //Method to get student name of a particular position
    private String getIsi(int position) {
        String isi = "";
        try {
            //Getting object of given index
            JSONObject json = resultpengisianfuelman.getJSONObject(position);

            //Fetching name from that object
            isi = json.getString(Config.TAG_ISI);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return isi;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item
        Spinner spinner = (Spinner) parent;

        if(spinner.getId() == R.id.spinner_pengisianfuelman)
        {
            a_history_tv_berkas.setText(getBerkas(position) + " berkas");
            a_history_tv_isi.setText(getIsi(position) + " liter");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
