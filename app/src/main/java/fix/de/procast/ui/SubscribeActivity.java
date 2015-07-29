package fix.de.procast.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import fix.de.procast.Data.Podcast;
import fix.de.procast.R;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FetcherException;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.net.URL;

public class SubscribeActivity extends AppCompatActivity {

    private TextView author;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        author = (TextView) findViewById(R.id.author);
        title = (TextView) findViewById(R.id.title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new BackButtonListener());

        Podcast podcast = getIntent().getParcelableExtra("Podcast");
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
            author.setText(feed.getAuthor());
            title.setText(feed.getTitle());
        }
    }
}
