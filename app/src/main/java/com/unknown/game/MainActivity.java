package com.unknown.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String PET_INFORMATION = "PET_INFORMATION";
    private static final String PET_NAME = "PET_NAME";
    private static final String PET_LEVEL = "PET_LEVEL";
    private static final String PET_MONEY = "PET_MONEY";
    private static final String PET_HUNGRY = "PET_HUNGRY";
    private static final String PET_HEALTH = "PET_HEALTH";

    private boolean music;

    private TextView tvNotiStt;

    private CountDownTimer timer;

    private MediaPlayer mediaPlayer;
    
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetFullScreen.hideSystemUI(getWindow()); //Set Fullscreen_Hide System UI

        sharedPreferences = getSharedPreferences(PET_INFORMATION, Context.MODE_PRIVATE);
        GetPetData();

        tvNotiStt = (TextView) findViewById(R.id.tvNotiStt);
        music = true;

        mediaPlayer = SetBackgroundMusics.SetBackgroundMusic(this, R.raw.background_music, 100);
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SetBackgroundMusics.SetStartMusic(mediaPlayer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetBackgroundMusics.SetStartMusic(mediaPlayer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SetBackgroundMusics.SetPauseMusic(mediaPlayer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int intExp = getIntent().getIntExtra("petIndex", 0);
        float exp = (float) intExp / 250;
        int hungryIndex = (int) (getIntent().getIntExtra("petIndex", 0) / 5);
        int healthIndex = (int) (getIntent().getIntExtra("petIndex", 0) / 5);

        float currentLevel = sharedPreferences.getFloat(PET_LEVEL, 1f) + exp;
        sharedPreferences.edit().putFloat(PET_LEVEL, currentLevel);
        sharedPreferences.edit().putInt(PET_HUNGRY, sharedPreferences.getInt(PET_HUNGRY, 100) - hungryIndex);
        sharedPreferences.edit().putInt(PET_HEALTH, sharedPreferences.getInt(PET_HEALTH, 100) - healthIndex);

        sharedPreferences.edit().apply();
    }

    public void btnIn4OnClick(View view) {
        Intent intent = new Intent(MainActivity.this, PetIn4Activity.class);
        intent.putExtra(PET_NAME, sharedPreferences.getString(PET_NAME, ""));
        intent.putExtra(PET_LEVEL, sharedPreferences.getFloat(PET_LEVEL, 5f));
        intent.putExtra(PET_MONEY, sharedPreferences.getInt(PET_MONEY, 24));
        intent.putExtra(PET_HUNGRY, sharedPreferences.getInt(PET_HUNGRY, 75));
        intent.putExtra(PET_HEALTH, sharedPreferences.getInt(PET_HEALTH, 75));
        startActivity(intent);
    }

    public void btnSetVolumeOnClick(View view) {
        if (music) {
            view.setBackground(getDrawable(R.drawable.ic_mute));
            music = !music;
            Log.e("music", String.valueOf(music));
            SetBackgroundMusics.SetVolume(mediaPlayer, 0, 0);
        }

        else if (!music) {
            view.setBackground(getDrawable(R.drawable.ic_volume));
            music = !music;
            Log.e("music", String.valueOf(music));
            SetBackgroundMusics.SetVolume(mediaPlayer, 1, 1);
        }
    }

    public void btnStudyOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, StudyActivity.class);
        finish();
        SetBackgroundMusics.SetPauseMusic(mediaPlayer);
        startActivity(intent);
    }

    public void btnPlayOnClick(View view) {

    }

    public void btnEatingOnClick(View view) {
        if (sharedPreferences.getInt(PET_MONEY, 0) >= 1000) {
            if (sharedPreferences.getInt(PET_HUNGRY, 0) < 70) {
                if (sharedPreferences.getInt(PET_HUNGRY, 100) + 50 > 100) {
                    SetTimeTextViewNoti("Tiền: -1000, " + "Độ no của " + sharedPreferences.getString(PET_NAME, "") + " đã đầy");
                    sharedPreferences.edit().putInt(PET_HUNGRY,  100);
                } else {
                    SetTimeTextViewNoti("Tiền: -1000, Độ no + 50");
                    sharedPreferences.edit().putInt(PET_HUNGRY,  sharedPreferences.getInt(PET_HUNGRY, 100) + 50);
                }
            } else {
                SetTimeTextViewNoti(sharedPreferences.getString(PET_NAME, "") + " đã no bụng");
            }
        } else {
            SetTimeTextViewNoti("Bạn không đủ tiền để mua thức ăn");
        }

        sharedPreferences.edit().apply();
    }

    public void btnHealingOnClick(View view) {

    }

    private void GetPetData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getString(PET_NAME, "") == "") {
            editor.putString(PET_NAME, getIntent().getStringExtra(PET_NAME));
        }
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

    private void SetTimeTextViewNoti(String str) {
        timer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long s = millisUntilFinished / 1000;
                tvNotiStt.setText(str);
                tvNotiStt.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                timer.cancel();
                tvNotiStt.setVisibility(View.INVISIBLE);
            }
        }.start();
    }
}