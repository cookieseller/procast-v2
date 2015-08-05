package fix.de.procast.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fix.de.procast.Data.Category;
import fix.de.procast.Data.Podcast;
import fix.de.procast.OnlineDirectory.FeedWranglerParser;
import fix.de.procast.R;
import fix.de.procast.adapter.ImageListAdapter;

public class PodcastListActivity extends AppCompatActivity {

    private Context context;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        context = this;
        String listURL = getIntent().getStringExtra("ListURL");

        list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new ListItemClickListener());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new BackButtonListener());

        new CategoryFeedLoader().execute(listURL);
    }

    private class BackButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    }

    private class CategoryFeedLoader extends AsyncTask<String, String, ArrayList<Podcast>>
    {
        @Override
        protected ArrayList<Podcast> doInBackground(String... params)
        {
            return new FeedWranglerParser().getPodcastsFromURL(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Podcast> podcastList)
        {
            List<Podcast> entries = new ArrayList<>();
            for (Podcast podcast: podcastList) {
                entries.add(podcast);
            }
            ImageListAdapter adapter = new ImageListAdapter(context, entries);
            list.setAdapter(adapter);
        }
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            Intent subscribeActivity = new Intent(PodcastListActivity.this, SubscribeActivity.class);

            Podcast podcast = (Podcast) adapter.getItemAtPosition(position);
            subscribeActivity.putExtra("Podcast", podcast.feedUrlString);
            startActivity(subscribeActivity);
        }
    }
}
