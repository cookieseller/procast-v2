package fix.de.procast.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fix.de.procast.Data.Category;
import fix.de.procast.Data.Podcast;
import fix.de.procast.OnlineDirectory.FeedWranglerParser;
import fix.de.procast.R;
import fix.de.procast.adapter.CategoryImageAdapter;

public class SubscribeCategoriesActivity extends AppCompatActivity {

    private Context context;
    private GridView gridView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_categories);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new BackButtonListener());

        gridView = (GridView) findViewById(R.id.categories);
        gridView.setAdapter(new CategoryImageAdapter(this, new ArrayList<Category>()));
        gridView.setOnItemClickListener(new GridViewItemClickListener());

        context = this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        new PopularFeedLoader().execute(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subscribe_categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private class BackButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    }

    private class PopularFeedLoader extends AsyncTask<Context, String, ArrayList<Category>>
    {
        @Override
        protected ArrayList<Category> doInBackground(Context... params)
        {
            FeedWranglerParser parser = new FeedWranglerParser();
            return parser.getCategories();
        }

        @Override
        protected void onPostExecute(ArrayList<Category> list)
        {
            gridView.setAdapter(new CategoryImageAdapter(context, list));

            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        }
    }

    private class GridViewItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            Intent podcastListActivity = new Intent(SubscribeCategoriesActivity.this, PodcastListActivity.class);

            Category category = (Category) adapter.getItemAtPosition(position);
            podcastListActivity.putExtra("ListURL", category.categoryUrlString);
            startActivity(podcastListActivity);
        }
    }
}
