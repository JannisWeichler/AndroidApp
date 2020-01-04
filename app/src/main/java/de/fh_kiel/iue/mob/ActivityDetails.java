package de.fh_kiel.iue.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;



import java.util.ArrayList;
import java.util.List;

public class ActivityDetails extends AppCompatActivity {

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


    private void orientationcheck(){
        if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            Intent intent = getIntent();

            intent.putExtra(DatenBearbeiten.POSITION,position);
            intent.putExtra(DatenBearbeiten.STADT_LISTE,DatenBearbeiten.listInStringStadt(stadtList));
            setResult(2,intent);
            finish();
        }
    }


    public void datenAusgeben(){
        Intent intent = getIntent();
        stadtList = DatenBearbeiten.intentAuslesenStadtList(intent);
        position = DatenBearbeiten.intentAuslesenPosition(intent);
        FragmentDetails fragment = (FragmentDetails) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fragment.loadStadtlist(stadtList,position);
        fragment.akt();

    }

}
