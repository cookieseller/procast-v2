package fix.de.procast.Data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import fix.de.procast.misc.Constants;
import fix.de.procast.network.HttpDownloader;

public class Podcast implements Parcelable {
    public String title;
    public String remoteImage;
    public URL feedUrl;
    public String feedUrlString;

    public Podcast() {
    }

    public Podcast(Parcel parcel) {
        title = parcel.readString();
        remoteImage = parcel.readString();
        feedUrlString = parcel.readString();
        try {
            feedUrl = new URL(feedUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setValuesFromJSONObject(JSONObject json) {
        try {
            this.title = json.getString("title");
            this.remoteImage = json.getString("image_url");
            this.feedUrl = new URL(json.getString("feed_url"));
            this.feedUrlString = json.getString("feed_url");

            new HttpDownloader().downloadImageFromUrl(json.getString("image_url"), json.getString("title"));
        } catch (JSONException | MalformedURLException e) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(remoteImage);
        dest.writeString(feedUrlString);
    }

    public static final Parcelable.Creator<Podcast> CREATOR = new Parcelable.Creator<Podcast>() {

        @Override
        public Podcast createFromParcel(Parcel source) {
            return new Podcast(source);
        }

        @Override
        public Podcast[] newArray(int size) {
            return new Podcast[size];
        }
    };
}
