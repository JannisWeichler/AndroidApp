package de.fh_kiel.iue.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    public static final String STADTNAME = "Stadtname";
    ArrayList<Stadt> stadtArrayList = new ArrayList<>();
    ArrayList<String> auslesenArrayList;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        orientationcheck();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        stadtausgeben();
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
            auslesenArrayList = intent.getStringArrayListExtra(MainActivity.ARRAY);
            position = intent.getIntExtra(MainActivity.POSITION,-1);

            for(int i=0; i<auslesenArrayList.size();++i){
                Stadt stadt2 = new Gson().fromJson(auslesenArrayList.get(i),Stadt.class);
                stadtArrayList.add(stadt2);
            }

            String input = stadtArrayList.get(position).getStadtName();

            intent.putExtra(STADTNAME,input);
            setResult(2,intent);
            finish();
        }
    }

    void stadtausgeben(){
        Intent intent = getIntent();

        auslesenArrayList = intent.getStringArrayListExtra(MainActivity.ARRAY);
        position = intent.getIntExtra(MainActivity.POSITION,-1);

        for(int i=0; i<auslesenArrayList.size();++i){
            Stadt stadt2 = new Gson().fromJson(auslesenArrayList.get(i),Stadt.class);
            stadtArrayList.add(stadt2);
        }

        String input = stadtArrayList.get(position).getStadtName();

        DetailsFragment fragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fragment.stadtanzeigen(input);
    }



}
