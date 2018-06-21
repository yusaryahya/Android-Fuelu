package com.progik.fuelu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DriverInputActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //Declaring an Spinner
    private Spinner spinnerdriver, spinnervehicle, spinnerfuelman;

    PopupWindow popupWindowProperty;

    //An ArrayList for Spinner Items
    private ArrayList<String> driver;
    private ArrayList<String> vehicle;
    private ArrayList<String> fuelman;

    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;

    //JSON Array
    private JSONArray result, resultfuelman, resultoperator;

    //TextViews to display details
    private EditText a_driver_input_et_driverid;
    private EditText a_driver_input_et_drivername;
    private EditText a_driver_input_et_drivernip;
    private EditText a_driver_input_et_vehicleid;
    private EditText a_driver_input_et_nopol;
    private EditText a_driver_input_et_unit;
    private EditText a_driver_input_et_unitdump;
    private EditText a_driver_input_et_fuelmanid;
    private EditText a_driver_input_et_fuelmanname;
    private EditText a_driver_input_et_fuelmannip;
    private EditText a_driver_input_et_isi;
    private EditText a_driver_input_et_kmunit;
    private EditText a_driver_input_et_kontraktor;
    private EditText a_driver_input_et_informasi;
    private EditText a_driver_input_et_pengawas;

    private Button ib_submit;
    private Button ib_preview;
    private Button ib_history;
    private Button a_driver_input_bt_choosefuelman;
    private Button a_driver_input_bt_chooseoperator;
    private Button a_driver_input_bt_choosenolambung;

    private TextView a_driver_input_tv_fuelmanname;
    private TextView a_driver_input_tv_operatorname;
    private TextView a_driver_input_tv_nolambung;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_input);

        //memanggil custom action bar
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

        //Initializing the ArrayList
        driver = new ArrayList<String>();
        vehicle = new ArrayList<String>();
        fuelman = new ArrayList<String>();

        //Initializing Spinner
        spinnerdriver = (Spinner) findViewById(R.id.spinner_driver);
        spinnervehicle = (Spinner) findViewById(R.id.spinner_vehicle);
        spinnerfuelman = (Spinner) findViewById(R.id.spinner_fuelman);

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener
        spinnerdriver.setOnItemSelectedListener(this);
        spinnervehicle.setOnItemSelectedListener(this);
        spinnerfuelman.setOnItemSelectedListener(this);

        //Initializing TextViews
        a_driver_input_et_driverid = (EditText) findViewById(R.id.a_driver_input_et_driverid);
        a_driver_input_et_drivername = (EditText) findViewById(R.id.a_driver_input_et_drivername);
        a_driver_input_et_drivernip = (EditText) findViewById(R.id.a_driver_input_et_drivernip);
        a_driver_input_et_vehicleid = (EditText) findViewById(R.id.a_driver_input_et_vehicleid);
        //a_driver_input_et_nopol = (EditText) findViewById(R.id.a_driver_input_et_nopol);
        a_driver_input_et_unit = (EditText) findViewById(R.id.a_driver_input_et_unit);
        a_driver_input_et_unitdump = (EditText) findViewById(R.id.a_driver_input_et_unitdump);
        a_driver_input_et_fuelmanid = (EditText) findViewById(R.id.a_driver_input_et_fuelmanid);
        a_driver_input_et_fuelmanname = (EditText) findViewById(R.id.a_driver_input_et_fuelmanname);
        a_driver_input_et_fuelmannip = (EditText) findViewById(R.id.a_driver_input_et_fuelmannip);
        a_driver_input_et_kmunit = (EditText) findViewById(R.id.a_driver_input_et_kmunit);
        a_driver_input_et_isi = (EditText) findViewById(R.id.a_driver_input_et_isi);
        a_driver_input_et_kontraktor = (EditText) findViewById(R.id.a_driver_input_et_kontraktor);
        a_driver_input_et_pengawas = (EditText) findViewById(R.id.a_driver_input_et_pengawas);
        a_driver_input_et_informasi = (EditText) findViewById(R.id.a_driver_input_et_informasi);

        //ib_submit = (ImageButton) findViewById(R.id.a_driver_input_ib_submit);
        ib_submit = (Button) findViewById(R.id.a_driver_input_ib_submit);
        ib_submit.setOnClickListener(this);

        //ib_preview = (ImageButton) findViewById(R.id.a_driver_input_ib_preview);
        ib_preview = (Button) findViewById(R.id.a_driver_input_ib_preview);
        ib_preview.setOnClickListener(this);

        //ib_preview = (ImageButton) findViewById(R.id.a_driver_input_ib_preview);
        ib_history = (Button) findViewById(R.id.a_driver_input_ib_history);
        ib_history.setOnClickListener(this);

        a_driver_input_bt_choosefuelman = (Button) findViewById(R.id.a_driver_input_bt_choosefuelman);
        a_driver_input_bt_choosefuelman.setOnClickListener(this);

        a_driver_input_bt_chooseoperator = (Button) findViewById(R.id.a_driver_input_bt_chooseoperator);
        a_driver_input_bt_chooseoperator.setOnClickListener(this);

        a_driver_input_bt_choosenolambung = (Button) findViewById(R.id.a_driver_input_bt_choosenolambung);
        a_driver_input_bt_choosenolambung.setOnClickListener(this);

        a_driver_input_tv_fuelmanname = (TextView) findViewById(R.id.a_driver_input_tv_fuelmanname);
        a_driver_input_tv_operatorname = (TextView) findViewById(R.id.a_driver_input_tv_operatorname);
        a_driver_input_tv_nolambung = (TextView) findViewById(R.id.a_driver_input_tv_nolambung);

        //This method will fetch the data from the URL
        getFuelmanData();

        getDriverData();

        getVehicleData();


    }

    private void submitPengisian(){
        Date tanggalSistem = (Date) Calendar.getInstance().getTime();
        SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String tanggalrefuel = formatTanggal.format(tanggalSistem).toString().trim();

        final String fuelmanid = a_driver_input_et_fuelmanid.getText().toString().trim();
        final String fuelmanname = a_driver_input_et_fuelmanname.getText().toString().trim();
        final String driverid = a_driver_input_et_driverid.getText().toString().trim();
        final String drivername = a_driver_input_et_drivername.getText().toString().trim();
        final String vehicleid = a_driver_input_et_vehicleid.getText().toString().trim();
        final String unit= a_driver_input_et_unit.getText().toString().trim();
        final String kmunit = a_driver_input_et_kmunit.getText().toString().trim();
        final String isi = a_driver_input_et_isi.getText().toString().trim();
        final String kontraktor = a_driver_input_et_kontraktor.getText().toString().trim();
        final String pengawas = a_driver_input_et_pengawas.getText().toString().trim();
        final String informasi = a_driver_input_et_informasi.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.INSERT_PENGISIAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        clearText();

                        Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DriverInputActivity.this,error.toString(),Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v) {
        final String isi = a_driver_input_et_isi.getText().toString().trim();
        final String fuelmanid = a_driver_input_et_fuelmanid.getText().toString().trim();
        final String driverid = a_driver_input_et_driverid.getText().toString().trim();
        final String vehicleid = a_driver_input_et_vehicleid.getText().toString().trim();
        final String unit= a_driver_input_et_unit.getText().toString().trim();
        final String kmunit = a_driver_input_et_kmunit.getText().toString().trim();
        final String kontraktor = a_driver_input_et_kontraktor.getText().toString().trim();
        final String pengawas = a_driver_input_et_pengawas.getText().toString().trim();
        final String informasi = a_driver_input_et_informasi.getText().toString().trim();
        if(v == ib_submit){
            if (kmunit.isEmpty()){
                Toast.makeText(DriverInputActivity.this,"Field \"KM/HM Unit\" Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }
            else if (isi.isEmpty()){
                Toast.makeText(DriverInputActivity.this,"Field \"Isi\" Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }
            else if (kontraktor.isEmpty()){
                Toast.makeText(DriverInputActivity.this,"Field \"Kontraktor\" Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }
            else if (pengawas.isEmpty()){
                Toast.makeText(DriverInputActivity.this,"Field \"Pengawas\" Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }
            else if (informasi.isEmpty()){
                Toast.makeText(DriverInputActivity.this,"Field \"Informasi\" Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }

            else {
                submitPengisian();
            }
        }
        else if(v == ib_preview){
            if (kmunit.isEmpty()){
                Toast.makeText(DriverInputActivity.this,"Field \"KM/HM Unit\" Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }
            else if (isi.isEmpty()){
                Toast.makeText(DriverInputActivity.this,"Field \"Isi\" Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }
            else if (kontraktor.isEmpty()){
                Toast.makeText(DriverInputActivity.this,"Field \"Kontraktor\" Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }
            else if (pengawas.isEmpty()){
                Toast.makeText(DriverInputActivity.this,"Field \"Pengawas\" Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }
            else if (informasi.isEmpty()){
                Toast.makeText(DriverInputActivity.this,"Field \"Informasi\" Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(getApplicationContext(), PreviewActivity.class);
                //Membuat obyek bundle
                Bundle b = new Bundle();

                //Menyisipkan tipe data String ke dalam obyek bundle
                b.putString("fuelmannip", a_driver_input_et_fuelmannip.getText().toString());
                b.putString("fuelmanid", a_driver_input_et_fuelmanid.getText().toString());
                b.putString("fuelmanname", spinnerfuelman.getSelectedItem().toString());
                b.putString("operatornip", a_driver_input_et_drivernip.getText().toString());
                b.putString("driverid", a_driver_input_et_driverid.getText().toString());
                b.putString("drivername", spinnerdriver.getSelectedItem().toString());
                b.putString("vehicleid", a_driver_input_et_vehicleid.getText().toString());
                b.putString("bodyno", spinnervehicle.getSelectedItem().toString());
                b.putString("unit", a_driver_input_et_unit.getText().toString());
                b.putString("kmunit", a_driver_input_et_kmunit.getText().toString());
                b.putString("isi", a_driver_input_et_isi.getText().toString());
                b.putString("kontraktor", a_driver_input_et_kontraktor.getText().toString());
                b.putString("pengawas", a_driver_input_et_pengawas.getText().toString());
                b.putString("informasi", a_driver_input_et_informasi.getText().toString());

                //Menambahkan bundle intent
                intent.putExtras(b);

                //memulai Activity kedua
                startActivity(intent);
            }
        }
        else if (v == ib_history){
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);

            //memulai Activity kedua
            startActivity(intent);
        }
    }

    public void clearText(){
        a_driver_input_et_fuelmanid.setText("");
        a_driver_input_et_fuelmannip.setText("");
        a_driver_input_et_fuelmanname.setText("");
        a_driver_input_et_driverid.setText("");
        a_driver_input_et_drivernip.setText("");
        a_driver_input_et_drivername.setText("");
        a_driver_input_et_vehicleid.setText("");
        a_driver_input_et_unit.setText("");
        a_driver_input_et_kmunit.setText("");
        a_driver_input_et_isi.setText("");
        a_driver_input_et_pengawas.setText("");
        a_driver_input_et_kontraktor.setText("");
        a_driver_input_et_informasi.setText("");
    }

      private void getDriverData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.SELECT_DRIVER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            resultoperator = j.getJSONArray(Config.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getOperatorName(resultoperator);
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

    private void getOperatorName(JSONArray j){
        //Traversing through all the items in the json array
        driver.add("Choose Operator");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                driver.add(json.getString(Config.TAG_DRIVERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerdriver.setAdapter(new ArrayAdapter<String>(DriverInputActivity.this, android.R.layout.simple_spinner_dropdown_item, driver));
    }

    //Method to get student name of a particular position
    private String getDriverName(int position) {
        String drivername = "";
        try {
            //Getting object of given index
            JSONObject json = resultoperator.getJSONObject(position);

            //Fetching name from that object
            drivername = json.getString(Config.TAG_DRIVERNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return drivername;
    }


    //Method to get student name of a particular position
    private String getDriverId(int position) {
        String driverid = "";
        try {
            //Getting object of given index
            JSONObject json = resultoperator.getJSONObject(position);

            //Fetching name from that object
            driverid = json.getString(Config.TAG_DRIVERID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return driverid;
    }

    private String getDriverNip(int position) {
        String drivernip = "";
        try {
            //Getting object of given index
            JSONObject json = resultoperator.getJSONObject(position);

            //Fetching name from that object
            drivernip = json.getString(Config.TAG_OPERATORNIP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return drivernip;
    }

    private void getFuelmanData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.SELECT_FUELMAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            resultfuelman = j.getJSONArray(Config.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getFuelmanName(resultfuelman);
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
        fuelman.add("Choose Fuelman");
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                fuelman.add(json.getString(Config.TAG_FUELMANNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerfuelman.setAdapter(new ArrayAdapter<String>(DriverInputActivity.this, android.R.layout.simple_spinner_dropdown_item, fuelman));
    }


    //Method to get student name of a particular position
    private String getFuelmanId(int position) {
        String fuelmanid = "";
        try {
            //Getting object of given index
            JSONObject json = resultfuelman.getJSONObject(position);

            //Fetching name from that object
            fuelmanid = json.getString(Config.TAG_FUELMANID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return fuelmanid;
    }

    //Method to get student name of a particular position
    private String getFuelmanNamez(int position) {
        String fuelmannamez = "";
        try {
            //Getting object of given index
            JSONObject json = resultfuelman.getJSONObject(position);

            //Fetching name from that object
            fuelmannamez = json.getString(Config.TAG_FUELMANNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return fuelmannamez;
    }

    private String getFuelmanNip(int position) {
        String fuelmannip = "";
        try {
            //Getting object of given index
            JSONObject json = resultfuelman.getJSONObject(position);

            //Fetching name from that object
            fuelmannip = json.getString(Config.TAG_FUELMANNIP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return fuelmannip;
    }

    private void getVehicleData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.SELECT_VEHICLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(Config.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getBodyNo(result);
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

    private void getBodyNo(JSONArray j){
        vehicle.add("Choose Vehicle");
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                vehicle.add(json.getString(Config.TAG_BODYNO));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnervehicle.setAdapter(new ArrayAdapter<String>(DriverInputActivity.this, android.R.layout.simple_spinner_dropdown_item, vehicle));
    }

    //Method to get student name of a particular position
    private String getVehicleId(int position) {
        String vehicleid = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            vehicleid = json.getString(Config.TAG_VEHICLEID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return vehicleid;
    }

    //Method to get student name of a particular position
    private String getJenis(int position) {
        String vehicletypename = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            vehicletypename = json.getString(Config.TAG_VEHICLETYPENAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return vehicletypename;
    }

    //Method to get student name of a particular position
    private String getBodyNoz(int position) {
        String bodynoz = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            bodynoz = json.getString(Config.TAG_BODYNO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return bodynoz;
    }

        @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item
            a_driver_input_et_unit.setText("");
            Spinner spinner = (Spinner) parent;

            if(spinner.getId() == R.id.spinner_driver)
            {
                a_driver_input_et_drivernip.setText(getDriverNip(position));
                a_driver_input_et_driverid.setText(getDriverId(position));
                a_driver_input_tv_operatorname.setText(getDriverName(position));
            }
            else if(spinner.getId() == R.id.spinner_fuelman)
            {
                a_driver_input_et_fuelmannip.setText(getFuelmanNip(position));
                a_driver_input_et_fuelmanid.setText(getFuelmanId(position));
                a_driver_input_tv_fuelmanname.setText(getFuelmanNamez(position));
            }
            else if(spinner.getId() == R.id.spinner_vehicle)
            {
                a_driver_input_et_vehicleid.setText(getVehicleId(position));
                a_driver_input_et_unit.setText(getJenis(position));
                a_driver_input_tv_nolambung.setText(getBodyNoz(position));
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showPropertyPopUp(View v) {
        //f = dbHelper.getAllProperties();
        //dbHelper.closeDB();

        if(fuelman != null && fuelman.size() > 0) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popUpView = inflater.inflate(R.layout.pop_up, null, false);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.default_buttonblack);
            popupWindowProperty = new PopupWindow(popUpView, a_driver_input_bt_choosefuelman.getWidth(),
                    300, true);
            popupWindowProperty.setContentView(popUpView);
            popupWindowProperty.setBackgroundDrawable(new BitmapDrawable(getResources(),
                    bitmap));
            popupWindowProperty.setOutsideTouchable(true);
            popupWindowProperty.setFocusable(true);
            popupWindowProperty.showAsDropDown(a_driver_input_bt_choosefuelman, 0, 0);
            ListView dropdownListView = (ListView) popUpView.
                    findViewById(R.id.dropdownListView);
            /*PropertyDropdownAdapter adapter = new PropertyDropdownAdapter(
                    DriverInputActivity.this,
                    R.layout.row_pop_up_list, fuelman);
            dropdownListView.setAdapter(adapter);
            dropdownListView.setOnItemClickListener(this); */
        }
    }

}
