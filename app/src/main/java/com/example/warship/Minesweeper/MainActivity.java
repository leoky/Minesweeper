package com.example.warship.Minesweeper;

import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private Button tombolplay,tombolexit,tombolhowto,tombolabout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if(savedInstanceState==null){
//            getSupportFragmentManager().beginTransaction().
//                    add(R.id.utama,
//                            new awal(),
//                            awal.class.getSimpleName()).commit();
//        }
        tombolplay=(Button) findViewById(R.id.play);
        tombolexit=(Button) findViewById(R.id.keluar);
        tombolhowto=(Button) findViewById(R.id.how);
        tombolabout=(Button) findViewById(R.id.about);

        tombolplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToNextActivity = new Intent(getApplicationContext(), play.class);
                startActivity(goToNextActivity);
            }
            
        });

        tombolhowto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToNextActivity = new Intent(getApplicationContext(), HowToPlay.class);
                startActivity(goToNextActivity);
            }
        });
        tombolabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToNextActivity = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(goToNextActivity);
            }
        });
        tombolexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

    }

}
