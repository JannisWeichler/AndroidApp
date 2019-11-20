package de.fh_kiel.iue.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    String stadtname = "Stadtname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        orientationcheck();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        stadtanzeigen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    private void stadtanzeigen(){
        TextView textView = findViewById(R.id.anzeigeStadt);
        String input = getIntent().getStringExtra(stadtname);
        textView.setText(input);
    }

    private void orientationcheck(){
        if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            Intent intent = new Intent();
            String input = getIntent().getStringExtra(stadtname);
            intent.putExtra(stadtname,input);
            setResult(2,intent);
            finish();
        }
    }

}
