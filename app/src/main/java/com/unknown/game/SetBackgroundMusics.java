package com.unknown.game;

import android.content.Context;
import android.media.MediaPlayer;

public class SetBackgroundMusics {

    private static MediaPlayer mediaPlayer;

    public static void SetPlayBackgroundMusic(Context context, int musicId, float volume) {
        mediaPlayer = MediaPlayer.create(context, musicId);

        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(volume, volume);
        mediaPlayer.start();
    }

    public static void SetPauseBackgroundMusic() {
        mediaPlayer.pause();
    }

    public static void SetResumeBackgroundMusic() {
        mediaPlayer.start();
    }

    public static void SetMute() {
        mediaPlayer.setVolume(0,0);
    }

    public static void SetMaxVolume() {
        mediaPlayer.setVolume(100,100);
    }
}
