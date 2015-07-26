package fix.de.procast.Data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import fix.de.procast.misc.Constants;
import fix.de.procast.network.HttpDownloader;

public class Podcast {
    public String title;
    public String remoteImage;
    public URL feedUrl;
    public String feedUrlString;

    public void setValuesFromJSONObject(JSONObject json) {
        try {
            this.title = json.getString("title");
            this.remoteImage = json.getString("image_url");
            this.feedUrl = new URL(json.getString("feed_url"));
            this.feedUrlString = json.getString("feed_url");

            new HttpDownloader().downloadImageFromUrl(json.getString("image_url"), json.getString("title"));
        } catch (JSONException|MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO needs to be async
     *
     * @return Bitmap
     */
    public Bitmap getLocalImage() {
        File image = new File(Constants.ROOT_DIR_EXTERNAL_STORAGE, title + ".jpg");
        if (image.exists()) {
            return BitmapFactory.decodeFile(Constants.ROOT_DIR_EXTERNAL_STORAGE + title + ".jpg");
        }

        new HttpDownloader().downloadImageFromUrl(remoteImage, title);

        return null;
    }
}
