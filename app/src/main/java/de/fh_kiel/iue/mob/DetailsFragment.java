package de.fh_kiel.iue.mob;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

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


    public void datenAnzeigen(List<Stadt> stadtList, Integer postion){
        TextView stadt = getActivity().findViewById(R.id.textViewStadtName);
        TextView temp = getActivity().findViewById(R.id.textViewTemp);
        TextView pressure = getActivity().findViewById(R.id.textViewPressure);
        TextView humidity = getActivity().findViewById(R.id.textViewHumidity);
        TextView tempMin = getActivity().findViewById(R.id.textViewTempMin);
        TextView tempMax = getActivity().findViewById(R.id.textViewTempMax);
        TextView speed = getActivity().findViewById(R.id.textViewSpeed);
        TextView deg = getActivity().findViewById(R.id.textViewDeg);
        TextView sunrise = getActivity().findViewById(R.id.textViewSunrise);
        TextView sunset = getActivity().findViewById(R.id.textViewSunset);
        TextView cloud = getActivity().findViewById(R.id.textViewCloud);

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

    }
}
