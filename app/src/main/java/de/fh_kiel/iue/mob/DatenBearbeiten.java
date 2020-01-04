package de.fh_kiel.iue.mob;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;


public class DatenBearbeiten {
    //String Konstanten Intent
    static final String STADT_LISTE = "stadtListe";
    static final String POSITION = "position";
    static final String DEMO = "demo";

    //String Konstanten Speichern
    static final String FILE_STADT = "fileStadt.txt";
    static final String KEINE_STADT_VORHANDEN = "KEINE STADT VORHANDEN BITTE STADTNAMEN EINGEBEN";

    //String Konstanten Volley
    static final String URL = "http://api.openweathermap.org/data/2.5/weather?appid=9e08d4137d8eaf7fc04882184d748f0e&units=metric&lang=de&q=";

    static final SimpleDateFormat UHRZEIT_API24 =new SimpleDateFormat("HH:mm:ss");
    static final SimpleDateFormat UHRZEIT_API23 = new SimpleDateFormat ("HH:mm:ss zzz");
    static final SimpleDateFormat DATUM = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss zzz");

    //String Konstanten Tags
    static final String TAG = "VolleyTAG";





    //ObjektListe in String
    static String listInStringStadt(List<Stadt> stadtList) {
        String json = new Gson().toJson(stadtList);
        return json;
    }


    //String mit Liste in ObjektListe
    static List<Stadt> stringAuslesenStadtList(String json) {
        Type listType = new TypeToken<List<Stadt>>() {
        }.getType();
        List<Stadt> stadtList = new Gson().fromJson(json, listType);
        return stadtList;
    }

    //String in Objekt
    public static List<Stadt> stadtAuslesen(String json, Integer position, List<Stadt> stadtList) {
        Stadt data = new Gson().fromJson(json, Stadt.class);
        stadtList.get(position).setData(data);
        return stadtList;
    }

    //Intent Auslesen
    static List<Stadt> intentAuslesenStadtList(Intent intent) {
        return stringAuslesenStadtList(intent.getStringExtra(STADT_LISTE));
    }

    static int intentAuslesenPosition(Intent intent) {
        return intent.getIntExtra(POSITION, -1);
    }


}
