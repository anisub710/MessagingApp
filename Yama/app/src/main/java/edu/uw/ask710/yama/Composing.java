package edu.uw.ask710.yama;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Composing extends AppCompatActivity {

    public static final int REQUEST_SELECT_PHONE_NUMBER = 1;
    public static final String TAG = "Composing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_composing);
        if (savedInstanceState == null) {

//            String result  = getIntent().getStringExtra(Reading.COMPOSING_KEY);
            Button contact = (Button)findViewById(R.id.contact);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectContact();
                }
            });


        }
    }

    public void selectContact(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK){
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
            if(cursor != null && cursor.moveToFirst()){
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                TextView text = (TextView)findViewById(R.id.number);
                text.setText("Chosen number: " + number);
                Log.v(TAG, "Here it is " + number);

            }
        }
    }
}
