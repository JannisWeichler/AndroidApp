package de.fh_kiel.iue.mob;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DatenBearbeiten {
    //String Konstanten Intent
    final static String STADT_LISTE = "stadtListe";
    final static String POSITION = "position";
    final static String DEMO = "demo";

    //String Konstanten Speichern
    final static String FILE_STADT = "fileStadt.txt";
    final static String KEINE_STADT_VORHANDEN = "KEINE STADT VORHANDEN BITTE STADTNAMEN EINGEBEN";


    //ObjektListe in String
    public static String listInStringStadt(List<Stadt> stadtList) {
        String json = new Gson().toJson(stadtList);
        return json;
    }


    //String mit Liste in ObjektListe
    public static List<Stadt> stringAuslesenStadtList(String json) {
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
    public static List<Stadt> intentAuslesenStadtList(Intent intent) {
        return stringAuslesenStadtList(intent.getStringExtra(STADT_LISTE));
    }

    public static int intentAuslesenPosition(Intent intent) {
        return intent.getIntExtra(POSITION, -1);
    }

    public static boolean intentAuslesenDemo (Intent intent) {
        return intent.getBooleanExtra(DEMO, false);
    }
}
