package com.example.calixta.flashcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Question extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        // call the question we just made
        findViewById(R.id.first_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change to other tex
                (findViewById(R.id.answer)).setVisibility(View.VISIBLE);
                (findViewById(R.id.first_question)).setVisibility(View.INVISIBLE);
            }
        });
}
            }