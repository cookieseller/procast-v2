package fix.de.procast.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import fix.de.procast.Data.Podcast;
import fix.de.procast.R;
import fix.de.procast.adapter.ImageListAdapter;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FetcherException;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubscribeActivity extends AppCompatActivity {

    private Context context;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        context = this;
        Podcast podcast = getIntent().getParcelableExtra("Podcast");

        list = (ListView) findViewById(R.id.listView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new BackButtonListener());

        new PopularFeedLoader().execute(podcast.feedUrlString);
    }

    private class BackButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    }

    private class PopularFeedLoader extends AsyncTask<String, String, SyndFeed>
    {
        @Override
        protected SyndFeed doInBackground(String... params)
        {
            try {
                FeedFetcher feedFetcher = new HttpURLFeedFetcher();
                return feedFetcher.retrieveFeed(new URL(params[0]));
            } catch (IOException|FeedException|FetcherException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(SyndFeed feed)
        {
            List<SyndEntry> entries = new ArrayList<>();
            for (Iterator<?> entryIter = feed.getEntries().iterator(); entryIter.hasNext();) {
                SyndEntry entry = (SyndEntry) entryIter.next();

                entries.add(entry);
//                if (entry.getContents() != null) {
//                    entry.getAuthor();
//                }
            }
            ImageListAdapter adapter = new ImageListAdapter(context, entries);
            list.setAdapter(adapter);
        }
    }
}
