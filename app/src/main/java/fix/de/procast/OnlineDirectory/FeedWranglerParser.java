package fix.de.procast.OnlineDirectory;


import com.android.volley.ExecutorDelivery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import fix.de.procast.Data.Category;
import fix.de.procast.Data.Podcast;

public class FeedWranglerParser {

    public ArrayList<Podcast> getPopularPodcasts() {
        return getListFromURL("https://feedwrangler.net/api/v2/podcasts/popular");
    }

    public ArrayList<Category> getCategories() {
        try {
            String json = readJSONStringFromUrl("https://feedwrangler.net/api/v2/podcasts/categories");
            JSONObject rootObject = new JSONObject(json);

            if (jsonContainsError(rootObject)) {
                return new ArrayList<>();
            }

            return getCategoriesFromJSON(rootObject);

        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Podcast> getPodcastsFromURL(String url) {
        try {
            String json = readJSONStringFromUrl(url);
            JSONObject rootObject = new JSONObject(json);

            if (jsonContainsError(rootObject)) {
                return new ArrayList<>();
            }

            return getArrayListFromJSON(rootObject);

        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Podcast> getListFromURL(String url) {
        try {
            String json = readJSONStringFromUrl(url);
            JSONObject rootObject = new JSONObject(json);

            if (jsonContainsError(rootObject)) {
                return new ArrayList<>();
            }

            return getArrayListFromJSON(rootObject);

        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readJSONStringFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        InputStreamReader streamReader = new InputStreamReader(url.openStream());
        BufferedReader bufferedReader = new BufferedReader(streamReader);

        StringBuilder builder = new StringBuilder();
        String readLine;
        while ((readLine = bufferedReader.readLine()) != null) {
            builder.append(readLine);
        }
        streamReader.close();
        bufferedReader.close();

        return builder.toString();
    }

    private ArrayList<Category> getCategoriesFromJSON(JSONObject jsonObject) throws  JSONException {
        ArrayList<Category> categoryList = new ArrayList<>();

        JSONArray categories = jsonObject.getJSONArray("podcasts");
        for (int i = 0; i < jsonObject.getInt("count"); i++) {
            JSONObject obj = categories.getJSONObject(i);

            Category category = new Category();
            category.setCategoryFromJSONObject(obj);

            categoryList.add(category);
        }
        return categoryList;
    }

    private ArrayList<Podcast> getArrayListFromJSON(JSONObject jsonObject) throws JSONException {
        ArrayList<Podcast> podcastList = new ArrayList<>();

        JSONArray podcasts = jsonObject.getJSONArray("podcasts");
        for (int i = 0; i < podcasts.length(); i++) {
            JSONObject obj = podcasts.getJSONObject(i);

            Podcast podcast = new Podcast();
            podcast.setValuesFromJSONObject(obj);

            podcastList.add(podcast);
        }
        return podcastList;
    }

    private boolean jsonContainsError(JSONObject jsonObject) throws JSONException
    {
        return jsonObject.getString("error") == null;
    }
}
