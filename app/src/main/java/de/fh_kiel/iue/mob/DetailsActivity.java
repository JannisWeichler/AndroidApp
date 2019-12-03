package de.fh_kiel.iue.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    public static final String STADTNAME = "Stadtname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        orientationcheck();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Stadtausgeben();

        /*
        String input = getIntent().getStringExtra(STADTNAME);
        DetailsFragment fragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fragment.stadtanzeigen(input);

         */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }



    private void orientationcheck(){
        if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            Intent intent = new Intent();
            String input = getIntent().getStringExtra(STADTNAME);
            intent.putExtra(STADTNAME,input);
            setResult(2,intent);
            finish();
        }
    }

    ArrayList<Stadt> stadtArrayList = new ArrayList<>();
    ArrayList<String> auslesenArrayList;
    int position;

    void Stadtausgeben(){
        Intent intent = getIntent();

        //TextView stadttv = findViewById(R.id.town);
        //TextView temp = findViewById(R.id.temp);

        auslesenArrayList = intent.getStringArrayListExtra("array");

        for(int i=0; i<auslesenArrayList.size();++i){
            Stadt stadt2 = new Gson().fromJson(auslesenArrayList.get(i),Stadt.class);
            stadtArrayList.add(stadt2);
        }

        position = intent.getIntExtra("test",-1);

        String input = stadtArrayList.get(position).getStadt();

        DetailsFragment fragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fragment.stadtanzeigen(input);

        //stadttv.setText(input);
        //temp.setText(String.valueOf(stadtArrayList.get(position).getTemp()));

    }



}
