package com.example.warship.Minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class HowToPlay extends AppCompatActivity {
//    private Button rumah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setMovementMethod(new ScrollingMovementMethod());
//        rumah=(Button) findViewById(R.id.rumah) ;
//        rumah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HowToPlay.this,MainActivity.class);
//                startActivityForResult(intent,1);
//            }
//        });

    }
}
