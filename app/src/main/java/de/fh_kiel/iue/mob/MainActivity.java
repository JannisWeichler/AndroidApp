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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MyAdapter.Listener,MyAsyncTask.Listener {


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
        //String i = result.toString();
        Toast.makeText(this,"Hat geklappt "+ result,Toast.LENGTH_SHORT).show();
    }



    public static class DataContainer{
        static ArrayList<Stadt> daten = new ArrayList<Stadt>();

        public static void adddata(int i){
            daten.add(new Stadt("Stadt"+i,i+15,i+12));
        }
    }

    public MyAdapter myAdapter = new MyAdapter(DataContainer.daten,this);
    public ProgressBar progressBar;
    public LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public void itemClicked(int position, String stadtname){
        if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            TextView textView = findViewById(R.id.anzeigeStadt);
            textView.setText(stadtname);

        }
        else{
             //Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
             //       intent.putExtra(DetailsActivity.STADTNAME,stadtname);
             //       startActivityForResult(intent,2);


            ArrayList<String> stadtArrayList = new ArrayList<>();
            for(int i=0;i<DataContainer.daten.size();++i){
                String json = new Gson().toJson(DataContainer.daten.get(i));
                stadtArrayList.add(json);
            }
            Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
            intent.putExtra("test",position);
            intent.putStringArrayListExtra("array",stadtArrayList);
            startActivityForResult(intent,2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2 && data != null)
        {
            String stadtname1=data.getStringExtra(DetailsActivity.STADTNAME);
            DetailsFragment fragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            fragment.stadtanzeigen(stadtname1);
        }
    }

    public void onStartAsyncTask(View v){
        progressBar = findViewById(R.id.progressBar);
        MyAsyncTask mAsyncTask = new MyAsyncTask(this);

        Integer num = 100;
        Integer increment = 1;
        Integer sleep = 50;

        mAsyncTask.execute(num,increment,sleep);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menue_refresh:
                onStartAsyncTask(findViewById(R.id.menue_refresh));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
