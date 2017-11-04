package edu.uw.ask710.yama;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Reading extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String COMPOSING_KEY = "compose";
    public static final String TAG = "Reading";


    public ArrayList<Message> messages;
    public MessageAdapter msgadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messages = new ArrayList<Message>();

        getSupportLoaderManager().initLoader(0, null, this);
        Uri inboxUri = Telephony.Sms.Inbox.CONTENT_URI;
        String[] projection = new String[]{
                Telephony.Sms.Inbox.ADDRESS,
                Telephony.Sms.Inbox.BODY,
                Telephony.Sms.Inbox.DATE
        };
        Cursor cursor = getContentResolver().query(inboxUri, projection, null, null, null);
        if(cursor != null){
            messages.clear();
            while(cursor.moveToNext()){
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                Log.v(TAG, "Amazing stuff " +  address + " " + body);
                Message message = new Message(address, body, date);
                messages.add(message);
            }

        }

        msgadapter = new MessageAdapter(messages);
        RecyclerView list = (RecyclerView)findViewById(R.id.list_view);
        list.setAdapter(msgadapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reading.this, Composing.class);
                intent.putExtra(COMPOSING_KEY, "test");

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(Reading.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri inboxUri = Telephony.Sms.Inbox.CONTENT_URI;
        String[] projection = new String[]{
                Telephony.Sms.Inbox.ADDRESS,
                Telephony.Sms.Inbox.BODY,
                Telephony.Sms.Inbox.DATE
        };
        CursorLoader loader = new CursorLoader(this,inboxUri, projection, null, null, null);
        return loader;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor != null){
            messages.clear();
            while(cursor.moveToNext()){
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                Log.v(TAG, "Amazing stuff " +  address + " " + body);
                Message message = new Message(address, body, date);
                messages.add(message);
            }

        }
        msgadapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        msgadapter.clear();
    }


    //MESSAGE ADAPTER

    public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

        private final ArrayList<Message> mValues;

        public MessageAdapter(ArrayList<Message> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.reading_list_item, parent, false);
            return new ViewHolder(view);
        }

        //sets up cards and sets onclicklisteners on each card.
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.message = mValues.get(position);
            TextView author = (TextView) holder.mView.findViewById(R.id.author);
            TextView body = (TextView) holder.mView.findViewById(R.id.body);
            TextView date = (TextView) holder.mView.findViewById(R.id.date);
            author.setText(holder.message.author);
            body.setText(holder.message.body);
            date.setText(holder.message.getDate());
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void clear() {
            int size = this.mValues.size();
            this.mValues.clear();
            notifyItemRangeRemoved(0, size);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public Message message;

            public ViewHolder(View view) {
                super(view);
                mView = view;

            }

        }
    }
}
