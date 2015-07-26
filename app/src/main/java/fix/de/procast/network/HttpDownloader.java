package fix.de.procast.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import fix.de.procast.misc.Constants;

public class HttpDownloader {

    public void downloadFromUrl(URL url) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            File dir = new File(Constants.ROOT_DIR_EXTERNAL_STORAGE);
            dir.mkdirs();

            File file = new File(Constants.ROOT_DIR_EXTERNAL_STORAGE, "Test.xml");
            FileOutputStream fileOutput = new FileOutputStream(file);

            InputStream inputStream = urlConnection.getInputStream();

            int bufferLength = 0;
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];

            while ((bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
            }

            inputStream.close();
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void downloadImageFromUrl(final String urlString, final String fileName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    InputStream input = url.openStream();

                    File image = new File(Constants.ROOT_DIR_EXTERNAL_STORAGE, fileName + ".jpg");

                    if (image.exists()) {
                        return;
                    }

                    OutputStream output = new FileOutputStream(image);
                    try {
                        byte[] buffer = new byte[1024];
                        int bytesRead = 0;
                        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                            output.write(buffer, 0, bytesRead);
                        }
                    } finally {
                        output.close();
                    }
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


}
