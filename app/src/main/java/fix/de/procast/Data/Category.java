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

public class Category implements Parcelable {
    public String title;
    public String remoteImage;
    public URL categoryUrl;
    public String categoryUrlString;

    public Category() {
    }

    public Category(Parcel parcel) {
        title = parcel.readString();
        remoteImage = parcel.readString();
        categoryUrlString = parcel.readString();
        try {
            categoryUrl = new URL(categoryUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setCategoryFromJSONObject(JSONObject json) {
        try {
            this.title = json.getString("title");
            this.remoteImage = json.getString("image_url");
            this.setURLString(json.getString("podcasts_url"));
            this.categoryUrl = new URL(this.categoryUrlString);

        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setURLString(String urlString) {
        if (urlString.startsWith("/")) {
            urlString = "https://feedwrangler.net" + urlString;
        }
        this.categoryUrlString = urlString;
    }

    public void setValuesFromJSONObject(JSONObject json) {
        try {
            this.title = json.getString("title");
            this.remoteImage = json.getString("image_url");
            this.categoryUrl = new URL(json.getString("feed_url"));
            this.categoryUrlString = json.getString("feed_url");

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
        dest.writeString(categoryUrlString);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {

        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
