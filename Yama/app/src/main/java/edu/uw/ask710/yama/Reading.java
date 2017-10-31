package edu.uw.ask710.yama;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class Reading extends AppCompatActivity {

    public static final String COMPOSING_KEY = "compose";
    public static final String TAG = "Reading";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Message> messages = new ArrayList<Message>();
        Message message = new Message("Test", "Hello", 0L);
        messages.add(message);

        ArrayList<String> data = new ArrayList<String>();
        data.add("Test");
        Log.v(TAG, data.get(0));
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.reading_list_item, R.id.author, data);
        AdapterView listView = (AdapterView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reading.this, Composing.class);
                intent.putExtra(COMPOSING_KEY, "test");

                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
            author.setText(holder.message.author);


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
