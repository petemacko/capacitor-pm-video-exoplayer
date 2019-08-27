package org.petemacko.exoplayerplugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.petemacko.exoplayerplugin.capacitorpmvideoexoplayer.R;

import java.util.Objects;

public class ExoplayerActivity extends AppCompatActivity implements Player.EventListener {
    private static final String TAG = "ExoplayerActivity";

    SimpleExoPlayer player;
    PlayerView playerView;
    MediaController mCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = this.getBaseContext();
        setContentView(R.layout.activity_exoplayer);
        playerView = (PlayerView) findViewById(R.id.player_view);

        // Get the Intent that started this activity and extract the string
        final Intent intent = getIntent();
        Uri url = intent.getParcelableExtra("videoUri");
        ExoplayerPlugin.MediaTypes mediaType =
                (ExoplayerPlugin.MediaTypes) intent.getSerializableExtra("mediaType");

        Log.v(TAG, "display url: " + url);
        if (url != null) {

            // Instantiate the player.
            player = ExoPlayerFactory.newSimpleInstance(context);

            // Attach player to the view.
            playerView.setPlayer(player);

            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, "capacitor-video-exoplayer"));

            // This is the MediaSource representing the media to be played.
            MediaSource videoSource = null;

            switch (mediaType) {
                case Unknown:
                case Hls:
                    videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                            .setAllowChunklessPreparation(true)
                            .createMediaSource(url);
                    break;
                case Dash:
                    videoSource = new DashMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(url);
                    break;
                case SmoothStreaming:
                    videoSource = new SsMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(url);
                    break;
            }

            player.addListener(this);
            // Prepare the player with the source.
            player.prepare(videoSource);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            try {
                Objects.requireNonNull(getSupportActionBar()).hide();
            } catch (NullPointerException npex) {
                Log.e(TAG, "hiding the action bar threw a null pointer exception. love those Andrrhoids.", npex);
            }

            player.setPlayWhenReady(true);

        } else {
            setResult(RESULT_CANCELED, intent.putExtra("result", false));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
        setResult(RESULT_CANCELED, getIntent().putExtra("result", false));
        finish();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Pause");
        if (player != null) {
            player.setPlayWhenReady(false);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Resume");
        if (player != null) {
            player.setPlayWhenReady(true);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            player.release();
        }
        super.onDestroy();
    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {
        if (isBehindLiveWindow(e)) {
            // Re-initialize player at the live edge.
        } else {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
}
