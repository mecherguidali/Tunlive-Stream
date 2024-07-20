package com.tun.live.ui.exoplayer;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.SimpleExoPlayer;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.MergingMediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.ui.PlayerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;

import com.tun.live.R;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class youtubeplayer extends AppCompatActivity {

    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;
    private String url;

    @OptIn(markerClass = UnstableApi.class) @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer);

        // Find the ExoPlayerView in the layout
        // Replace R.id.player_veiw with the correct ID of your PlayerView
        playerView = this.findViewById(R.id.player_veiw);
        //ExoPlayer simpleExoPlayer = new ExoPlayer.Builder(this).build();
        // Create a SimpleExoPlayer instance
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(simpleExoPlayer);
        // Create a MediaItem

        url = getIntent().getStringExtra("url");
        playyoutubevv(url);
    }
    @OptIn(markerClass = UnstableApi.class) void playyoutubevv(String urlYoutue){
        new YouTubeExtractor(this) {


            @OptIn(markerClass = UnstableApi.class) @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {

                    int videotag = 137;
                    int audiotag = 140;
                    MediaSource audioSource = new ProgressiveMediaSource
                            .Factory(new DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles.get(audiotag).getUrl()));
                    MediaSource videosource = new ProgressiveMediaSource
                            .Factory(new DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles.get(videotag).getUrl()));
                    simpleExoPlayer.setMediaSource(new MergingMediaSource(
                                    true,
                                    videosource,
                                    audioSource),
                            true
                    );

                }
            }
        }.extract(urlYoutue,false,false);
        // Prepare the player
        simpleExoPlayer.prepare();

        // Start playback
        simpleExoPlayer.play();
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @OptIn(markerClass = UnstableApi.class) @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the player when the activity is destroyed
        simpleExoPlayer.release();
    }
}
