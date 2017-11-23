package ch.hes.santour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //button create track
        Button buttonCreate = findViewById(R.id.createTrack);
        buttonCreate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CreateTrackActivity.class);
                startActivity(intent);
            }
        });

        // button about
        Button buttonAbout =  findViewById(R.id.about);
        buttonAbout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AboutActivity.class);

                startActivity(intent);
            }
        });

    }


}
