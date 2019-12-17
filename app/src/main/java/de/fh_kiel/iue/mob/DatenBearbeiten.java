package de.fh_kiel.iue.mob;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

    //String Konstanten Volley
    final static String URL = "http://api.openweathermap.org/data/2.5/weather?appid=9e08d4137d8eaf7fc04882184d748f0e&units=metric&q=";

    //String Konstanten Tags
    public static final String TAG = "VolleyTAG";

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



    //Daten aus Internet Laden
    public static void loadStadtList(final String stadtname, final int position, RequestQueue requestQueue){


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Stadt localClass = new Gson().fromJson(response, Stadt.class);
                    //DetailsActivity.stadtList.get(position).setData(localClass);
                    Log.i(TAG, response);

                } catch (Exception ex) {

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error fetching content: " + error.getMessage());
            }
        };

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, URL + stadtname, responseListener, errorListener) {
            @Override
            public Request.Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };

        requestQueue.add(stringRequest1);
    }


    //Daten aus Internet Laden
    public static Stadt[] loadStadt(final List<Stadt> stadtList, final int position, RequestQueue requestQueue){

        final String stadtname = stadtList.get(position).getStadtName();
        final Stadt[] stadt = new Stadt[1];

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Stadt localClass = new Gson().fromJson(response, Stadt.class);
                    Log.i(TAG, response);
                    stadt[0] = localClass;

                } catch (Exception ex) {

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error fetching content: " + error.getMessage());
            }
        };

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, URL + stadtname, responseListener, errorListener) {
            @Override
            public Request.Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };

        requestQueue.add(stringRequest1);
        return stadt;
    }
}
