package de.fh_kiel.iue.mob;


import android.icu.util.TimeZone;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetails extends Fragment {

    List<Stadt> stadtList = new ArrayList<>();
    int position;


    public FragmentDetails() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }


    public void loadStadtlist(List<Stadt> newstadtList, int position){
        this.position = position;
        stadtList=newstadtList;
        if (ActivityMain.demo==false) {

            loadStadtVolley();
        }else {
            long letzeAkt = System.currentTimeMillis();
            stadtList.get(position).setLetzteAkt(letzeAkt);
            datenAnzeigen(stadtList, position);
        }
    }




    //Daten aus Internet Laden
    void loadStadtVolley(){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String stadtname = stadtList.get(position).getStadtName();


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Stadt stadt = new Gson().fromJson(response, Stadt.class);

                    long letzeAkt = System.currentTimeMillis();
                    stadt.setLetzteAkt(letzeAkt);

                    stadtList.get(position).setData(stadt);
                    datenAnzeigen(stadtList,position);
                    saveStadtList(stadtList);
                } catch (Exception ex) {
                    Toast.makeText(getActivity(),"Fehler beim Laden von Daten", Toast.LENGTH_LONG).show();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(DatenBearbeiten.TAG, "error fetching content: " + error.getMessage()+hashCode());
                if (error.networkResponse!=null && error.networkResponse.statusCode==404){
                    Toast.makeText(getActivity(),"Datenaktualisierung Fehlgeschlagen\nStadt konnte nicht gefunden werden", Toast.LENGTH_LONG).show();
                }else
                Toast.makeText(getActivity(),"Datenaktualisierung Fehlgeschlagen\nÜberprüfen Sie Ihre Internetverbindung", Toast.LENGTH_LONG).show();
                datenAnzeigen(stadtList,position);
            }
        };

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, DatenBearbeiten.URL + stadtname, responseListener, errorListener) {
            @Override
            public Request.Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };

        requestQueue.add(stringRequest1);
    }



    public void datenAnzeigen(List<Stadt> stadtList, final Integer postion){
        final TextView stadt = getActivity().findViewById(R.id.textViewStadtName);
        final TextView temp = getActivity().findViewById(R.id.textViewTemp);
        final TextView pressure = getActivity().findViewById(R.id.textViewPressure);
        final TextView humidity = getActivity().findViewById(R.id.textViewHumidity);
        final TextView tempMin = getActivity().findViewById(R.id.textViewTempMin);
        final TextView tempMax = getActivity().findViewById(R.id.textViewTempMax);
        final TextView speed = getActivity().findViewById(R.id.textViewSpeed);
        final TextView deg = getActivity().findViewById(R.id.textViewDeg);
        final TextView sunrise = getActivity().findViewById(R.id.textViewSunrise);
        final TextView sunset = getActivity().findViewById(R.id.textViewSunset);
        final TextView cloud = getActivity().findViewById(R.id.textViewCloud);
        final TextView dt = getActivity().findViewById(R.id.textViewDt);
        final TextView description = getActivity().findViewById(R.id.textViewDescription);


        long sunriseEx = stadtList.get(postion).getSunrise()*1000;
        long sunsetEX = stadtList.get(postion).getSunset()*1000;

        Date sunriseDate =new Date(sunriseEx);
        Date sunsetDate =new Date(sunsetEX);
        Date letzteAktDate = new Date(stadtList.get(postion).getLetzteAkt());


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            TimeZone timeZone= TimeZone.getDefault();
            sunriseEx += stadtList.get(postion).getTimezone()*1000-timeZone.getRawOffset();
            sunriseDate.setTime(sunriseEx);
            sunsetEX += stadtList.get(postion).getTimezone()*1000-timeZone.getRawOffset();
            sunsetDate.setTime(sunsetEX);
        }


        stadt.setText(stadtList.get(postion).getStadtName());
        description.setText(stadtList.get(postion).getDescription());
        temp.setText("Temperatur: "+String.valueOf(stadtList.get(postion).getTemp())+"°C");
        pressure.setText("Luftdruck: "+String.valueOf(stadtList.get(postion).getPressure()) + "hPa");
        humidity.setText("Lufteuchtigkeit: "+String.valueOf(stadtList.get(postion).getHumidity()) + "%");
        tempMin.setText("Min: "+ String.valueOf(stadtList.get(postion).getTemp_min())+"°C");
        tempMax.setText("Max: " +String.valueOf(stadtList.get(postion).getTemp_max())+"°C");
        speed.setText("Windgeschwindigkeit: " + String.valueOf(stadtList.get(postion).getSpeed()*3.6) +"km/h");


        if (stadtList.get(postion).getDeg()<=22||stadtList.get(postion).getDeg()>=338){
            deg.setText("Windrichtung: Nord" +  String.valueOf(stadtList.get(postion).getDeg()));
        }else if (stadtList.get(postion).getDeg()<=67){
            deg.setText("Windrichtung: Nord-Ost");
        }else if (stadtList.get(postion).getDeg()<=112){
            deg.setText("Windrichtung: Ost");
        }else if (stadtList.get(postion).getDeg()<=157){
            deg.setText("Windrichtung: Süd-Ost");
        }else if (stadtList.get(postion).getDeg()<=202){
            deg.setText("Windrichtung: Süd");
        }else if (stadtList.get(postion).getDeg()<=247){
            deg.setText("Windrichtung: Süd-West");
        }else if (stadtList.get(postion).getDeg()<=292){
            deg.setText("Windrichtung: West");
        }else if (stadtList.get(postion).getDeg()<=337) {
            deg.setText("Windrichtung: Nord-West");
        }

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            sunrise.setText("Sonnenaufgang: " + DatenBearbeiten.UHRZEIT_API24.format(sunriseDate));
            sunset.setText("Sonnenuntergang: " + DatenBearbeiten.UHRZEIT_API24.format(sunsetDate));
        }else{
            sunrise.setText("Sonnenaufgang: " + DatenBearbeiten.UHRZEIT_API23.format(sunriseDate));
            sunset.setText("Sonnenuntergang: " + DatenBearbeiten.UHRZEIT_API23.format(sunsetDate));
        }
        cloud.setText("Bewölkung: " + String.valueOf(stadtList.get(postion).getCloudAll()) + "%");
        dt.setText("Letzte Aktualisierung: \n" + DatenBearbeiten.DATUM.format(letzteAktDate));
    }

    //Save StadtListe
    public void saveStadtList (List<Stadt> stadtList){
        FileOutputStream fos = null;

        String stadtListString = DatenBearbeiten.listInStringStadt(stadtList);


        try {
            fos = getActivity().openFileOutput(DatenBearbeiten.FILE_STADT, getActivity().MODE_PRIVATE);
            fos.flush();
            fos.write(stadtListString.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void akt(){
        ImageButton imageButton = getActivity().findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityMain.demo==false) {

                    loadStadtVolley();
                }else {
                    datenAnzeigen(stadtList, position);
                }
            }
        });
    }
}
