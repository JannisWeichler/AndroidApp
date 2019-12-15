package de.fh_kiel.iue.mob;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NeueStadt extends Fragment {


    public NeueStadt() {
        // Required empty public constructor
    }

    static EditText editText;
    static Button button;
    static MyAdapter mAdapter;

    NeueStadt(EditText editText,Button button, MyAdapter adapter){
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

    public static void neueStadt1(){
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
                MainActivity.DataContainer.adddata(new Stadt(neueStadt,main,wind,sys,cloud));
                mAdapter.notifyDataSetChanged();
                button.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
            }
        });
    }

}
