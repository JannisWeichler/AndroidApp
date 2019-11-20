package de.fh_kiel.iue.mob;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MyAdapter.Listener {

    String stadtname2 = "Stadtname";

    private static class DataContainer{
            static ArrayList<String> initializeData () {
                ArrayList<String> data = new ArrayList<>();
                for (int i = 0; i < 99; i++) {
                    data.add("Stadt" + i);
                }
                return data;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(DataContainer.initializeData(),this));

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
             Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
                    intent.putExtra(stadtname2,stadtname);
                    startActivityForResult(intent,2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            TextView stadtname = findViewById(R.id.anzeigeStadt);
            String stadtname1=data.getStringExtra(stadtname2);
            stadtname.setText(stadtname1);
        }
    }

}
