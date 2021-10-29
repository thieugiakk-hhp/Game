package com.unknown.game.helper;

import android.content.Context;
import android.media.MediaPlayer;

public class SetBackgroundMusics {

    public static MediaPlayer SetBackgroundMusic(Context context, int musicId, float volume) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, musicId);

        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(volume, volume);

        return mediaPlayer;
    }

    public static void SetStartMusic(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    public static void SetPauseMusic(MediaPlayer mediaPlayer) {
        mediaPlayer.pause();
    }

    public static void SetVolume(MediaPlayer mediaPlayer, float leftVolume, float rightVolume) {
        mediaPlayer.setVolume(leftVolume, rightVolume);
    }
}
