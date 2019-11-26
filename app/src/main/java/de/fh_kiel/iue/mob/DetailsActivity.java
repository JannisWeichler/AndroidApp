package de.fh_kiel.iue.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class DetailsActivity extends AppCompatActivity {

    public static final String STADTNAME = "Stadtname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        orientationcheck();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String input = getIntent().getStringExtra(STADTNAME);
        DetailsFragment fragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fragment.stadtanzeigen(input);

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

}
