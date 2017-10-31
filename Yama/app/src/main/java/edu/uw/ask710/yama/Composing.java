package edu.uw.ask710.yama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Composing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composing);
        if (savedInstanceState == null) {

            String result  = getIntent().getStringExtra(Reading.COMPOSING_KEY);
            TextView text = (TextView)findViewById(R.id.test);
            text.setText(result);

        }
    }
}
