package com.progik.fuelu.util;

/**
 * Created by YUSAR on 21/01/2017.
 */

public class Config {
    //JSON URL
    public static final String URL = "http://192.168.0.105/android/fuelu/";
    public static final String SELECT_DRIVER = URL + "selectdriver.php";
    public static final String SELECT_VEHICLE = URL + "selectvehicle.php";
    public static final String SELECT_FUELMAN = URL + "selectfuelman.php";
    public static final String SELECT_PENGISIANFUELMAN = URL + "selectpengisianfuelman.php";
    public static final String INSERT_PENGISIAN = URL + "pengisian.php";

    //Tags used in the JSON String
    public static final String TAG_DRIVERID = "driverid";
    public static final String TAG_DRIVERNAME = "drivername";
    public static final String TAG_OPERATORNIP = "operatornip";

    public static final String TAG_VEHICLEID = "vehicleid";
    public static final String TAG_BODYNO = "bodyno";
    public static final String TAG_NOPOL = "nopol";
    public static final String TAG_VEHICLETYPENAME = "vehicletypename";

    public static final String TAG_FUELMANID = "fuelmanid";
    public static final String TAG_FUELMANNAME = "fuelmanname";
    public static final String TAG_FUELMANNIP = "fuelmannip";

    public static final String TAG_TANGGALREFUEL = "tanggalrefuel";
    public static final String TAG_KMUNIT = "kmunit";
    public static final String TAG_KONTRAKTOR = "kontraktor";
    public static final String TAG_ISI = "isi";
    public static final String TAG_INFORMASI = "informasi";
    public static final String TAG_PENGAWAS = "pengawas";
    public static final String TAG_BERKAS = "berkas";

    //JSON array name
    public static final String JSON_ARRAY = "result";
}
