package de.fh_kiel.iue.mob;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ActivityMain extends AppCompatActivity implements MyAdapter.Listener, MyAsyncTask.Listener, FragmentStadtHinzfuegen.Listener {

    private MyAdapter myAdapter = new MyAdapter(DataContainer.daten,this);
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

    protected static boolean demo=false;
    protected static boolean load=false;
    protected static boolean del=false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState!=null){
            DataContainer.daten.clear();
            DataContainer.daten.addAll(DatenBearbeiten.stringAuslesenStadtList(savedInstanceState.getString(DatenBearbeiten.STADT_LISTE)));
            demo = savedInstanceState.getBoolean(DatenBearbeiten.DEMO);
        }else{
            if (!demo){
                loadStadtList();
            }
        }



        if (DataContainer.daten.get(0).getStadtName().equals(DatenBearbeiten.KEINE_STADT_VORHANDEN)){
            EditText editText = findViewById(R.id.editTextNeueStadt);
            Button button = findViewById(R.id.buttonNeueStadt);
            FragmentStadtHinzfuegen fragmentStadtHinzfuegen = new FragmentStadtHinzfuegen(editText,button,myAdapter);
            fragmentStadtHinzfuegen.register(this);
            fragmentStadtHinzfuegen.neueStadt1();
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
            FragmentDetails fragment = (FragmentDetails) getSupportFragmentManager().findFragmentById(R.id.fragment);
            if (fragment != null) {
                fragment.loadStadtlist(DataContainer.daten, DatenBearbeiten.intentAuslesenPosition(data));
                fragment.akt();
            }
        }else if (!demo){
            loadStadtList();
        }
    }


    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
        savedInstanceState.putString(DatenBearbeiten.STADT_LISTE,DatenBearbeiten.listInStringStadt(DataContainer.daten));
        savedInstanceState.putBoolean(DatenBearbeiten.DEMO,demo);
        super.onSaveInstanceState(savedInstanceState);
    }



    static class DataContainer{
        static List<Stadt> daten = new CopyOnWriteArrayList<>();

        static void adddata(Stadt stadt){
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
        Toast.makeText(this,"Daten geladen",Toast.LENGTH_SHORT).show();
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
        if(!del) {
            if (!DataContainer.daten.get(position).getStadtName().equals(DatenBearbeiten.KEINE_STADT_VORHANDEN)) {
                if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation) {
                    FragmentDetails fragment = (FragmentDetails) getSupportFragmentManager().findFragmentById(R.id.fragment);
                    if (fragment != null) {
                        fragment.loadStadtlist(DataContainer.daten, position);
                    }
                } else {
                    String stadtListString = DatenBearbeiten.listInStringStadt(DataContainer.daten);
                    Intent intent = new Intent(getApplicationContext(), ActivityDetails.class);
                    intent.putExtra(DatenBearbeiten.DEMO, demo);
                    intent.putExtra(DatenBearbeiten.POSITION, position);
                    intent.putExtra(DatenBearbeiten.STADT_LISTE, stadtListString);
                    startActivityForResult(intent, 2);
                }
            }
        }
        else{
            DataContainer.daten.remove(position);
            if (DataContainer.daten.isEmpty()){
                DataContainer.daten.add(new Stadt(DatenBearbeiten.KEINE_STADT_VORHANDEN, new Stadt.Weather[]{new Stadt.Weather("noch nie geladen")}, new Stadt.Main(0,0,0,0, 0),new Stadt.Wind(0,0), new Stadt.Sys(0,0),new Stadt.Cloud(0)));
            }
            myAdapter.notifyDataSetChanged();
            saveStadtList(DataContainer.daten);
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
        myAdapter.notifyDataSetChanged();

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (!load) {
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
                    FragmentStadtHinzfuegen fragmentStadtHinzfuegen = new FragmentStadtHinzfuegen(editText, button, myAdapter);
                    fragmentStadtHinzfuegen.register(this);
                    fragmentStadtHinzfuegen.neueStadt1();
                    return true;
                case R.id.menue_stadtl??schen:
                    del = true;
                    Toast.makeText(this,"Stadt zum L??schen anklicken",Toast.LENGTH_LONG).show();
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

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (stadtList.isEmpty()){
            stadtList.add(new Stadt(DatenBearbeiten.KEINE_STADT_VORHANDEN, new Stadt.Weather[]{new Stadt.Weather("Noch nie geladen")}, new Stadt.Main(0,0,0,0, 0),new Stadt.Wind(0,0), new Stadt.Sys(0,0),new Stadt.Cloud(0)));
        }
        DataContainer.daten.clear();
        DataContainer.daten.addAll(stadtList);
        myAdapter.notifyDataSetChanged();
    }

    //Save StadtListe
    @Override
    public void saveStadtList (List<Stadt> stadtList) {
        if (!demo) {
            FileOutputStream fos = null;

            String stadtListString = DatenBearbeiten.listInStringStadt(DataContainer.daten);


            try {
                fos = openFileOutput(DatenBearbeiten.FILE_STADT, MODE_PRIVATE);
                fos.flush();
                fos.write(stadtListString.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
