package com.progik.fuelu;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.progik.fuelu.R.id.a_driver_input_et_driverid;
import static com.progik.fuelu.R.id.a_driver_input_et_drivername;
import static com.progik.fuelu.R.id.a_driver_input_et_drivernip;
import static com.progik.fuelu.R.id.a_driver_input_et_fuelmanname;
import static com.progik.fuelu.R.id.a_driver_input_et_fuelmannip;

public class StatusActivity extends AppCompatActivity implements  View.OnClickListener{

    private Button ib_back2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //masukan custom action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();

        ib_back2 = (Button) findViewById(R.id.a_status_ib_back);
        ib_back2.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, DriverInputActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == ib_back2) {
            onBackPressed();
        }


        //@Override

    /*public void onBackPressed() {
        Intent intentsuccess = new Intent(getApplicationContext(), DriverInputActivity.class);
        //Membuat obyek bundle
        Bundle c = new Bundle();

        //Menyisipkan tipe data String ke dalam obyek bundle
        c.putString("fuelmannip", "");
        c.putString("fuelmanid", "");
        c.putString("fuelmanname", "");
        c.putString("operatornip", "");
        c.putString("driverid", "");
        c.putString("drivername", "");
        c.putString("vehicleid", "");
        c.putString("bodyno", "");
        c.putString("unit", "");
        c.putString("kmunit", "");
        c.putString("isi", "");
        c.putString("kontraktor", "");
        c.putString("pengawas", "");
        c.putString("informasi", "");

        //Menambahkan bundle intent
        intentsuccess.putExtras(c);

        //memulai Activity kedua
        finish();
        startActivity(intentsuccess);



        /*Intent startMain = new Intent(Intent.);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        */
        //}

      /*
    } */

    }
}
