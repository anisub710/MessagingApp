package edu.uw.ask710.yama;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Composing extends AppCompatActivity {

    public static final int REQUEST_SELECT_PHONE_NUMBER = 1;
    public static final String TAG = "Composing";
    public static final String ACTION_SMS_STATUS = "edu.uw.ask710.yama.ACTION_SMS_STATUS";
    private String number;

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
            Button send = (Button) findViewById(R.id.send);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMessage();
                }
            });

            if(getIntent().getStringExtra(MessageReceiver.NOTIFICATION_REPLY) != null){
                number = getIntent().getStringExtra(MessageReceiver.NOTIFICATION_REPLY);
                TextView text = (TextView)findViewById(R.id.number);
                text.setText("Chosen number: " + number);
                Log.v(TAG, "Here it is " + number);

            }


        }
    }

    public void sendMessage(){
        if(number != null){
            EditText msg = (EditText) findViewById(R.id.message);
            String message = msg.getText().toString();

            Intent intent = new Intent();
            intent.setAction(ACTION_SMS_STATUS);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(Composing.this, 0, intent, 0);

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, pendingIntent, null);
            clearInput();

        }
    }

    public void clearInput(){
        number = null;
        EditText editText = (EditText)findViewById(R.id.message);
        editText.setText("");
        TextView text = (TextView)findViewById(R.id.number);
        text.setText("");
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
                number = cursor.getString(numberIndex);
            }
        }
        TextView text = (TextView)findViewById(R.id.number);
        text.setText("Chosen number: " + number);
        Log.v(TAG, "Here it is " + number);

    }
}
