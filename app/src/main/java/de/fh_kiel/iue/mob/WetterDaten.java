package de.fh_kiel.iue.mob;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

public class WetterDaten {

    public static final String TAG = "VolleyTAG";


    WetterDaten(final DetailsActivity.TextViews textViews, RequestQueue requestQueue, String stadtname) {

        //String stadtname = "Kiel";


        String url = "http://api.openweathermap.org/data/2.5/weather?appid=9e08d4137d8eaf7fc04882184d748f0e&units=metric&q=";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Stadt localClass = new Gson().fromJson(response, Stadt.class);
                    textViews.textViewTemp.setText("Temperatur :" + String.valueOf(localClass.getTemp()));
                    textViews.textViewPressure.setText("Luftdruck :" + String.valueOf(localClass.getPressure()));
                    textViews.textViewHumidity.setText("Feuchtigkeit :" + String.valueOf(localClass.getHumidity()));
                    textViews.textViewTempMin.setText("Temperatur min :" + String.valueOf(localClass.getTemp_min()));
                    textViews.textViewTempMax.setText("Temperatur max:" + String.valueOf(localClass.getTemp_max()));
                    textViews.textViewSpeed.setText("Wind " + String.valueOf(localClass.getSpeed()));
                    textViews.textViewDeg.setText("Deg " + String.valueOf(localClass.getDeg()));
                    textViews.textViewSunrise.setText("Sonnenaufgang " + String.valueOf(localClass.getSunrise()));
                    textViews.textViewSunset.setText("Sonnenuntergang " + String.valueOf(localClass.getSunset()));
                    textViews.textViewCloud.setText("Wolken " + String.valueOf(localClass.getCloudAll()));

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

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url + stadtname, responseListener, errorListener) {
            @Override
            public Request.Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };

        requestQueue.add(stringRequest1);
    }

}
