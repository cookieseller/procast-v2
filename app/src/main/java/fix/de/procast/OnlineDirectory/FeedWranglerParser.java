package fix.de.procast.OnlineDirectory;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import fix.de.procast.Data.Podcast;

public class FeedWranglerParser {

    public ArrayList<Podcast> getPopularPodcasts() {
        return getListFromURL("https://feedwrangler.net/api/v2/podcasts/popular");
    }

    public ArrayList<Podcast> getCategories() {
        return getListFromURL("https://feedwrangler.net/api/v2/podcasts/categories");
    }

    private ArrayList<Podcast> getListFromURL(String url) {
        try {
            String json = readJSONStringFromUrl("https://feedwrangler.net/api/v2/podcasts/categories");
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

    private String readJSONStringFromUrl(String urlString) throws IOException
    {
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

    private ArrayList<Podcast> getArrayListFromJSON(JSONObject jsonObject) throws JSONException
    {
        ArrayList<Podcast> podcastList = new ArrayList<>();

        JSONArray podcasts = jsonObject.getJSONArray("podcasts");
        for (int i = 0; i < jsonObject.getInt("count"); i++) {
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
