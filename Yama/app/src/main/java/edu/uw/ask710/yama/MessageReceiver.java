package edu.uw.ask710.yama;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static android.provider.Telephony.Sms.Intents.getMessagesFromIntent;

/**
 * Created by Anirudh Subramanyam on 11/2/2017.
 */

public class MessageReceiver extends BroadcastReceiver{

    public static final String TAG = "RECEIVER";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() == Composing.ACTION_SMS_STATUS) {
            if(getResultCode() == Activity.RESULT_OK){
                Toast.makeText(context, "Message sent!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Error sending message", Toast.LENGTH_SHORT).show();
            }
        }if(intent.getExtras() != null){ //receiving messages
            SmsMessage[] otherMessages = getMessagesFromIntent(intent);
            for(int i = 0; i < otherMessages.length; i++){
                String oldNumber = otherMessages[i].getDisplayOriginatingAddress();
                String oldMessage = otherMessages[i].getDisplayMessageBody();
                Log.v(TAG, "This is working here: " + oldNumber + " " + oldMessage);
            }

        }
    }
}
