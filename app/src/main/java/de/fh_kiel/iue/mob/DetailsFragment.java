package de.fh_kiel.iue.mob;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    //public static final String TAG = "VolleyTAG";
    List<Stadt> stadtList = new ArrayList<>();
    int position;

    public DetailsFragment() {
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
        if (MainActivity.demo==false) {

            loadStadtVolley();
        }else {
            datenAnzeigen(stadtList, position);
        }
    }




    //Daten aus Internet Laden
    public void loadStadtVolley(){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String stadtname = stadtList.get(position).getStadtName();


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Stadt stadt = new Gson().fromJson(response, Stadt.class);
                    stadtList.get(position).setData(stadt);
                    datenAnzeigen(stadtList,position);
                    saveStadtList(stadtList);
                } catch (Exception ex) {

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(DatenBearbeiten.TAG, "error fetching content: " + error.getMessage());
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

        stadt.setText(stadtList.get(postion).getStadtName());
        temp.setText("Temperatur :"+String.valueOf(stadtList.get(postion).getTemp()));
        pressure.setText("Luftdruck :"+String.valueOf(stadtList.get(postion).getPressure()));
        humidity.setText("Feuchtigkeit :"+String.valueOf(stadtList.get(postion).getHumidity()));
        tempMin.setText("Temperatur min :"+ String.valueOf(stadtList.get(postion).getTemp_min()));
        tempMax.setText("Temperatur max:" +String.valueOf(stadtList.get(postion).getTemp_max()));
        speed.setText("Wind " + String.valueOf(stadtList.get(postion).getSpeed()));
        deg.setText("Deg " +String.valueOf(stadtList.get(postion).getDeg()));
        sunrise.setText("Sonnenaufgang " +String.valueOf(stadtList.get(postion).getSunrise()));
        sunset.setText("Sonnenuntergang " + String.valueOf(stadtList.get(postion).getSunset()));
        cloud.setText("Wolken " +String.valueOf(stadtList.get(postion).getCloudAll()));
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
}
