package com.alj968.android.audiodemo;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.AudioManager;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements
        AudioManager.OnAudioFocusChangeListener {

    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;
    AudioFocusRequest mFocusRequest;
    AudioAttributes mPlaybackAttributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mPlaybackAttributes = new AudioAttributes.Builder().
                setUsage(AudioAttributes.USAGE_MEDIA).
                setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();

        mFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).
                setAudioAttributes(mPlaybackAttributes).
                setOnAudioFocusChangeListener(this).build();


        mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.bensound_funnysong);
        mMediaPlayer.setAudioAttributes(mPlaybackAttributes);

        Button startButton = findViewById(R.id.buttonPlay);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = mAudioManager.requestAudioFocus(mFocusRequest);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    if (mMediaPlayer != null) {
                        mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.bensound_funnysong);
                    }

                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });

        Button pauseButton = findViewById(R.id.buttonPause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaPlayer.pause();
            }
        });
    }

    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            mMediaPlayer.pause();
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            mMediaPlayer.start();

        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            releaseMediaPlayer();
        }
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocusRequest(mFocusRequest);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}

