package de.fh_kiel.iue.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Stadtanzeigen();
    }

    private void Stadtanzeigen(){
        TextView textView = findViewById(R.id.anzeigeStadt);
        String input = getIntent().getStringExtra("Stadtname");
        textView.setText(input);
    }

}
