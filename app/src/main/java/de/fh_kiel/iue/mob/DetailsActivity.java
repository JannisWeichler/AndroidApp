package de.fh_kiel.iue.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    public static final String STADTNAME = "Stadtname";
    List<Stadt> stadtList = new ArrayList<>();
    ArrayList<String> auslesenArrayList;
    int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        datenAusgeben();
        orientationcheck();
        test();


    }

    void test(){
        Intent intent = getIntent();
        stadtList = DatenBearbeiten.intentAuslesenStadtList(intent);
        position = DatenBearbeiten.intentAuslesenPosition(intent);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        new WetterDaten(new TextViews(),requestQueue,stadtList.get(position).getStadtName());
    }

    public class TextViews{
        public TextView textViewStadtName = findViewById(R.id.textViewStadtName);
        public TextView textViewTemp = findViewById(R.id.textViewTemp);
        public TextView textViewPressure = findViewById(R.id.textViewPressure);
        public TextView textViewHumidity = findViewById(R.id.textViewHumidity);
        public TextView textViewTempMin = findViewById(R.id.textViewTempMin);
        public TextView textViewTempMax = findViewById(R.id.textViewTempMax);
        public TextView textViewSpeed = findViewById(R.id.textViewSpeed);
        public TextView textViewDeg = findViewById(R.id.textViewDeg);
        public TextView textViewSunrise = findViewById(R.id.textViewSunrise);
        public TextView textViewSunset = findViewById(R.id.textViewSunset);
        public TextView textViewCloud = findViewById(R.id.textViewCloud);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }


    private void orientationcheck(){
        if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            Intent intent = getIntent();

            intent.putExtra(DatenBearbeiten.POSITION,position);
            intent.putExtra(DatenBearbeiten.STADT_LISTE,DatenBearbeiten.listInStringStadt(stadtList));
            setResult(2,intent);
            finish();
        }
    }



    void datenAusgeben(){
        Intent intent = getIntent();

        stadtList = DatenBearbeiten.intentAuslesenStadtList(intent);
        position = DatenBearbeiten.intentAuslesenPosition(intent);

        DetailsFragment fragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fragment.datenAnzeigen(stadtList,position);
    }



}
