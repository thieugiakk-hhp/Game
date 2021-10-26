package com.unknown.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String PET_INFORMATION = "PET_INFORMATION";
    private static final String PET_NAME = "PET_NAME";
    private static final String PET_LEVEL = "PET_LEVEL";
    private static final String PET_MONEY = "PET_MONEY";
    private static final String PET_HUNGRY = "PET_HUNGRY";
    private static final String PET_HEALTH = "PET_HEALTH";

    private boolean music = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetFullScreen.hideSystemUI(getWindow()); //Set Fullscreen_Hide System UI

        SharedPreferences sharedPreferencesPetIn4= this.getSharedPreferences(PET_INFORMATION, Context.MODE_PRIVATE);
        GetPetData(sharedPreferencesPetIn4);

        TextView tv = (TextView) findViewById(R.id.tvStt);

        SetBackgroundMusics.SetPlayBackgroundMusic(MainActivity.this, R.raw.background_music, 100);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SetBackgroundMusics.SetPauseBackgroundMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SetBackgroundMusics.SetResumeBackgroundMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SetBackgroundMusics.SetPauseBackgroundMusic();
    }

    @Override
    protected void onStart() {
        super.onStart();
        int intExp = getIntent().getIntExtra("petIndex", 0);
        float exp = (float) (Math.round(intExp / 2000 * 10) / 10);
        int hungryIndex = (int) (Math.round(getIntent().getIntExtra("petIndex", 0) / 5));
        int healthIndex = (int) (Math.round(getIntent().getIntExtra("petIndex", 0) / 5));

        SharedPreferences sharedPreferencesPetIn4= this.getSharedPreferences(PET_INFORMATION, Context.MODE_PRIVATE);
        float currentLevel = sharedPreferencesPetIn4.getFloat(PET_LEVEL, 1f) + exp;
        sharedPreferencesPetIn4.edit().putFloat(PET_LEVEL, currentLevel);
        sharedPreferencesPetIn4.edit().putInt(PET_HUNGRY, sharedPreferencesPetIn4.getInt(PET_HUNGRY, 100) - hungryIndex);
        sharedPreferencesPetIn4.edit().putInt(PET_HEALTH, sharedPreferencesPetIn4.getInt(PET_HEALTH, 100) - healthIndex);

        sharedPreferencesPetIn4.edit().apply();
    }

    public void btnIn4OnClick(View view) {
        SharedPreferences sharedPreferencesPetIn4= this.getSharedPreferences(PET_INFORMATION, Context.MODE_PRIVATE);
        Intent intent = new Intent(MainActivity.this, PetIn4Activity.class);
        intent.putExtra(PET_NAME, sharedPreferencesPetIn4.getString(PET_NAME, ""));
        intent.putExtra(PET_LEVEL, sharedPreferencesPetIn4.getFloat(PET_LEVEL, 5f));
        intent.putExtra(PET_MONEY, sharedPreferencesPetIn4.getInt(PET_MONEY, 24));
        intent.putExtra(PET_HUNGRY, sharedPreferencesPetIn4.getInt(PET_HUNGRY, 75));
        intent.putExtra(PET_HEALTH, sharedPreferencesPetIn4.getInt(PET_HEALTH, 75));
        startActivity(intent);
    }

    public void btnSetVolumeOnClick(View view) {
        if (music) {
            view.setBackground(getDrawable(R.drawable.ic_mute));
            music = !music;
            SetBackgroundMusics.SetMute();
        } else {
            view.setBackground(getDrawable(R.drawable.ic_volume));
            music = !music;
            SetBackgroundMusics.SetMaxVolume();
        }
    }

    public void btnStudyOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, StudyActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnPlayOnClick(View view) {

    }

    public void btnEatingOnClick(View view) {

    }

    public void btnHealingOnClick(View view) {

    }

    private void GetPetData(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent intent = getIntent();
        editor.putString(PET_NAME, intent.getStringExtra(PET_NAME));
        if (sharedPreferences.getFloat(PET_LEVEL, -1) == -1) {
            editor.putFloat(PET_LEVEL, 1f);
        }
        if (sharedPreferences.getInt(PET_MONEY, -1) == -1) {
            editor.putInt(PET_MONEY, 0);
        }
        if (sharedPreferences.getInt(PET_HUNGRY, -1) == -1) {
            editor.putInt(PET_HUNGRY, 75);
        }
        if (sharedPreferences.getInt(PET_HEALTH, -1) == -1) {
            editor.putInt(PET_HEALTH, 75);
        }

        editor.apply();
    }
}