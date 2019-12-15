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

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    public static final String TAG = "VolleyTAG";

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


    public void datenAnzeigen(final List<Stadt> stadtList, final Integer postion){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String stadtname = stadtList.get(postion).getStadtName();

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
        temp.setText(String.valueOf(stadtList.get(postion).getTemp()));
        pressure.setText(String.valueOf(stadtList.get(postion).getPressure()));
        humidity.setText(String.valueOf(stadtList.get(postion).getHumidity()));
        tempMin.setText(String.valueOf(stadtList.get(postion).getTemp_min()));
        tempMax.setText(String.valueOf(stadtList.get(postion).getTemp_max()));
        speed.setText(String.valueOf(stadtList.get(postion).getSpeed()));
        deg.setText(String.valueOf(stadtList.get(postion).getDeg()));
        sunrise.setText(String.valueOf(stadtList.get(postion).getSunrise()));
        sunset.setText(String.valueOf(stadtList.get(postion).getSunset()));
        cloud.setText(String.valueOf(stadtList.get(postion).getCloudAll()));

        String url = "http://api.openweathermap.org/data/2.5/weather?appid=9e08d4137d8eaf7fc04882184d748f0e&units=metric&q=";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Stadt localClass = new Gson().fromJson(response, Stadt.class);
                    stadt.setText(stadtname);
                    temp.setText("Temperatur :" + String.valueOf(localClass.getTemp()));
                    //temp.setText("Temperatur" + String.valueOf(stadtList.get(postion).getTemp()));
                    pressure.setText("Luftdruck :" + String.valueOf(localClass.getPressure()));
                    humidity.setText("Feuchtigkeit :" + String.valueOf(localClass.getHumidity()));
                    tempMin.setText("Temperatur min :" + String.valueOf(localClass.getTemp_min()));
                    tempMax.setText("Temperatur max:" + String.valueOf(localClass.getTemp_max()));
                    speed.setText("Wind " + String.valueOf(localClass.getSpeed()));
                    deg.setText("Deg " + String.valueOf(localClass.getDeg()));
                    sunrise.setText("Sonnenaufgang " + String.valueOf(localClass.getSunrise()));
                    sunset.setText("Sonnenuntergang " + String.valueOf(localClass.getSunset()));
                    cloud.setText("Wolken " + String.valueOf(localClass.getCloudAll()));

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
