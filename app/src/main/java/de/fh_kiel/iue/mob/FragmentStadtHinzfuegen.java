package de.fh_kiel.iue.mob;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
public class FragmentStadtHinzfuegen extends Fragment {

    public interface Listener{
        void saveStadtList(List<Stadt> stadtList);
    }

    Listener listener;
    public void register (Listener listener){
        this.listener = listener;
    }

    public FragmentStadtHinzfuegen() {
        // Required empty public constructor
    }

    static EditText editText;
    static Button button;
    static MyAdapter mAdapter;

    FragmentStadtHinzfuegen(EditText editText, Button button, MyAdapter adapter){
        this.editText = editText;
        this.button = button;
        mAdapter = adapter;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_neue_stadt, container, false);
    }

    public void neueStadt1(){
        editText.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String neueStadt = editText.getText().toString();


                Stadt.Main main = new Stadt.Main(0,0,0,0,0);
                Stadt.Wind wind = new Stadt.Wind(0,0);
                Stadt.Sys sys = new Stadt.Sys(0,0);
                Stadt.Cloud cloud = new Stadt.Cloud(0);
                if (ActivityMain.DataContainer.daten.get(0).getStadtName().equals(DatenBearbeiten.KEINE_STADT_VORHANDEN)){
                    ActivityMain.DataContainer.daten.clear();
                }
                ActivityMain.DataContainer.adddata(new Stadt(neueStadt,main,wind,sys,cloud));

                listener.saveStadtList(ActivityMain.DataContainer.daten);
                mAdapter.notifyDataSetChanged();
                button.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);

            }
        });
    }




}
