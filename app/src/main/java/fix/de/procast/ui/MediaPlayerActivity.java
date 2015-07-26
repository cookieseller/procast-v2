package fix.de.procast.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import fix.de.procast.R;

public class MediaPlayerActivity extends AppCompatActivity {

    private ImageButton play;
    private MediaPlayer mediaPlayer;
    private boolean isPrepared = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);

        play = (ImageButton) findViewById(R.id.play);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new BackButtonListener());

        initMediaPlayer();
    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.freakshow_test);
    }

    public void startReplay(View v) {
        if (mediaPlayer.isPlaying()) {
            play.setImageResource(R.drawable.ic_play);
            mediaPlayer.pause();
        } else {
            play.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
        }
    }

    private class BackButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    }
}
