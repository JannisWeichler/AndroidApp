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
