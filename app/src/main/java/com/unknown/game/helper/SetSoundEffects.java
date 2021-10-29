package com.unknown.game.helper;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SetSoundEffects {

    public static MediaPlayer SetClickSound(Context context, int musicId, float volume) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, musicId);

        mediaPlayer.setLooping(false);
        mediaPlayer.setVolume(volume, volume);

        return mediaPlayer;
    }

    public static void SetVolume(MediaPlayer mediaPlayer, float leftVolume, float rightVolume) {
        mediaPlayer.setVolume(leftVolume, rightVolume);
    }
}
