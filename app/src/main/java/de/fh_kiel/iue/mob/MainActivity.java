package de.fh_kiel.iue.mob;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;




import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MyAdapter.Listener,MyAsyncTask.Listener {

    public MyAdapter myAdapter = new MyAdapter(DataContainer.daten,this);
    public ProgressBar progressBar;
    public LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

    public static boolean demo=false;
    public static boolean load=false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState!=null){
            DataContainer.daten.clear();
            DataContainer.daten.addAll(DatenBearbeiten.stringAuslesenStadtList(savedInstanceState.getString(DatenBearbeiten.STADT_LISTE)));
            demo = savedInstanceState.getBoolean(DatenBearbeiten.DEMO);
        }else{
            if (demo==false){
                loadStadtList();
            }
        }



        if (DataContainer.daten.get(0).getStadtName()==DatenBearbeiten.KEINE_STADT_VORHANDEN){
            EditText editText = findViewById(R.id.editTextNeueStadt);
            Button button = findViewById(R.id.buttonNeueStadt);
            new NeueStadt(editText,button,myAdapter);
            NeueStadt.neueStadt1();
        }


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2 && data != null)
        {
            DataContainer.daten = DatenBearbeiten.intentAuslesenStadtList(data);
            DetailsFragment fragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            fragment.loadStadtlist(DataContainer.daten, DatenBearbeiten.intentAuslesenPosition(data));
        }
    }


    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
        savedInstanceState.putString(DatenBearbeiten.STADT_LISTE,DatenBearbeiten.listInStringStadt(DataContainer.daten));
        savedInstanceState.putBoolean(DatenBearbeiten.DEMO,demo);
        super.onSaveInstanceState(savedInstanceState);
    }



    public static class DataContainer{
        static List<Stadt> daten = new ArrayList<Stadt>();

        public static void adddata(Stadt stadt){
            daten.add(stadt);
        }
    }

    @Override
    public void datachanged() {
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void progress(int percent) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(percent);
    }

    @Override
    public void toastausgeben(Boolean result) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,"Hat geklappt "+ result,Toast.LENGTH_SHORT).show();
        load=false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }




    @Override
    public void itemClicked(int position){
        if (DataContainer.daten.get(position).getStadtName()!=DatenBearbeiten.KEINE_STADT_VORHANDEN) {
            if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation) {
                DetailsFragment fragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                fragment.loadStadtlist(DataContainer.daten, position);
            } else {
                String stadtListString = DatenBearbeiten.listInStringStadt(DataContainer.daten);
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra(DatenBearbeiten.DEMO, demo);
                intent.putExtra(DatenBearbeiten.POSITION, position);
                intent.putExtra(DatenBearbeiten.STADT_LISTE, stadtListString);
                startActivityForResult(intent, 2);
            }
        }
    }









    public void onStartAsyncTask(View v){
        progressBar = findViewById(R.id.progressBar);
        MyAsyncTask mAsyncTask = new MyAsyncTask(this);

        Integer num = 100;
        Integer increment = 1;
        Integer sleep = 50;

        DataContainer.daten.clear();
        mAsyncTask.execute(num,increment,sleep);
    }



    public void EchtDatenAnzeigen(){
        DataContainer.daten.clear();
        loadStadtList();
        /*
        Stadt.Main main = new Stadt.Main(0,0,0,0,0);
        Stadt.Wind wind = new Stadt.Wind(0,0);
        Stadt.Sys sys = new Stadt.Sys(0,0);
        Stadt.Cloud cloud = new Stadt.Cloud(0);
        DataContainer.adddata(new Stadt("Kiel",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Köln",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Kall",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("München",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Hamburg",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Leipzig",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Dortmund",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Frankfurt",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Jevenstedt",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Rom",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Madrid",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Mailand",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Chicago",main,wind,sys,cloud));
        DataContainer.adddata(new Stadt("Seattle",main,wind,sys,cloud));
        saveStadtList(DataContainer.daten);*/
        myAdapter.notifyDataSetChanged();

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (load==false) {
            switch (item.getItemId()) {
                case R.id.menue_demodaten:
                    load = true;
                    demo = true;
                    onStartAsyncTask(findViewById(R.id.menue_demodaten));
                    return true;
                case R.id.menue_Echtdaten:
                    demo = false;
                    EchtDatenAnzeigen();
                    return true;
                case R.id.menue_neuestadt:
                    EditText editText = findViewById(R.id.editTextNeueStadt);
                    Button button = findViewById(R.id.buttonNeueStadt);
                    new NeueStadt(editText, button, myAdapter);
                    NeueStadt.neueStadt1();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }


    //Load StadtList
    public void loadStadtList (){
        FileInputStream fis = null;
        List<Stadt> stadtList = new ArrayList<>();

        try {
            fis = openFileInput(DatenBearbeiten.FILE_STADT);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null){
               stadtList.addAll(DatenBearbeiten.stringAuslesenStadtList(text));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (stadtList.size()<1){
            stadtList.add(new Stadt(DatenBearbeiten.KEINE_STADT_VORHANDEN,new Stadt.Main(0,0,0,0, 0),new Stadt.Wind(0,0), new Stadt.Sys(0,0),new Stadt.Cloud(0)));
        }
        DataContainer.daten.clear();
        DataContainer.daten.addAll(stadtList);
        myAdapter.notifyDataSetChanged();
    }

    //Save StadtListe
    public void saveStadtList (List<Stadt> stadtList){
        FileOutputStream fos = null;

        String stadtListString = DatenBearbeiten.listInStringStadt(DataContainer.daten);


        try {
            fos = openFileOutput(DatenBearbeiten.FILE_STADT, MODE_PRIVATE);
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
