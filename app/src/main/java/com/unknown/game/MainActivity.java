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

import com.unknown.game.helper.Const;
import com.unknown.game.helper.SetBackgroundMusics;
import com.unknown.game.helper.SetFullScreen;
import com.unknown.game.helper.SetSoundEffects;

public class MainActivity extends AppCompatActivity {

    private boolean music;

    private TextView tvNotificationStt;

    private CountDownTimer timerNotification;

    private CountDownTimer timerDecrease;

    private MediaPlayer mediaPlayer;

    private MediaPlayer clickSound;
    
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetFullScreen.hideSystemUI(getWindow()); //Set Fullscreen_Hide System UI

        sharedPreferences = getSharedPreferences(Const.PET_INFORMATION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        GetPetData();
        SetIndexDecrease();

        tvNotificationStt = findViewById(R.id.tvNotificationStt);
        music = true;

        mediaPlayer = SetBackgroundMusics.SetBackgroundMusic(this, R.raw.background_music, 100);
        mediaPlayer.start();

        clickSound = SetSoundEffects.SetClickSound(this, R.raw.sound_click, 100);

        CaiNayDeTest();
        
        SetPetIndexChange();
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
        SetFullScreen.hideSystemUI(getWindow());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SetBackgroundMusics.SetPauseMusic(mediaPlayer);
    }

    public void btnIn4OnClick(View view) {
        clickSound.start();
        Intent intent = new Intent(MainActivity.this, PetIn4Activity.class);
        intent.putExtra(Const.PET_NAME, sharedPreferences.getString(Const.PET_NAME, ""));
        intent.putExtra(Const.PET_LEVEL, sharedPreferences.getFloat(Const.PET_LEVEL, 1f));
        intent.putExtra(Const.PET_MONEY, sharedPreferences.getInt(Const.PET_MONEY, 0));
        intent.putExtra(Const.PET_HUNGRY, sharedPreferences.getInt(Const.PET_HUNGRY, 0));
        intent.putExtra(Const.PET_HEALTH, sharedPreferences.getInt(Const.PET_HEALTH, 0));
        startActivity(intent);
    }

    public void btnSetVolumeOnClick(View view) {
        clickSound.start();
        if (music) {
            view.setBackground(getDrawable(R.drawable.ic_mute));
            music = !music;
            Log.e("music", String.valueOf(music));
            SetBackgroundMusics.SetVolume(mediaPlayer, 0, 0);
            SetSoundEffects.SetVolume(clickSound, 0, 0);
        }

        else if (!music) {
            view.setBackground(getDrawable(R.drawable.ic_volume));
            music = !music;
            Log.e("music", String.valueOf(music));
            SetBackgroundMusics.SetVolume(mediaPlayer, 1, 1);
            SetSoundEffects.SetVolume(clickSound, 1, 1);
        }
    }

    public void btnStudyOnClick(View view) {
        clickSound.start();
        if (sharedPreferences.getInt(Const.PET_HUNGRY, 0) <= 10) {
            SetTimeTextViewNotification("Đói lắm! Không học đâu!");
        } else if (sharedPreferences.getInt(Const.PET_HEALTH, 0) <= 15) {
            SetTimeTextViewNotification("Mệt lắm! Không học đâu!");
        } else {
            SetBackgroundMusics.SetPauseMusic(mediaPlayer);
            startActivity(new Intent(MainActivity.this, StudyActivity.class));
            finish();
        }
    }

    public void btnPlayOnClick(View view) {
        clickSound.start();
        if (sharedPreferences.getInt(Const.PET_HUNGRY, 0) <= 10) {
            SetTimeTextViewNotification("Đói lắm! Đi ăn cơ!");
        } else if (sharedPreferences.getInt(Const.PET_HEALTH, 0) <= 20) {
            SetTimeTextViewNotification("Mệt lắm! Không đi chơi đâu!");
        } else {
            SetBackgroundMusics.SetPauseMusic(mediaPlayer);
            startActivity(new Intent(MainActivity.this, GameActivity.class));
            finish();
        }
    }

    public void btnEatingOnClick(View view) {
        clickSound.start();
        if (sharedPreferences.getInt(Const.PET_MONEY, 0) >= 500) {
            if (sharedPreferences.getInt(Const.PET_HUNGRY, 0) < 70) {
                if (sharedPreferences.getInt(Const.PET_HUNGRY, 100) + 50 > 100) {
                    SetTimeTextViewNotification("Tiền: -500, " + "Độ no của " + sharedPreferences.getString(Const.PET_NAME, "") + " đã đầy");
                    editor.putInt(Const.PET_HUNGRY,  100);
                } else {
                    SetTimeTextViewNotification("Tiền: -500, Độ no + 50");
                    editor.putInt(Const.PET_HUNGRY,  sharedPreferences.getInt(Const.PET_HUNGRY, 100) + 50);
                }
            } else {
                SetTimeTextViewNotification(sharedPreferences.getString(Const.PET_NAME, "") + " đã no bụng");
            }
        } else {
            SetTimeTextViewNotification("Bạn không đủ tiền để mua thức ăn");
        }

        sharedPreferences.edit().apply();
    }

    public void btnHealingOnClick(View view) {
        clickSound.start();
        if (sharedPreferences.getInt(Const.PET_MONEY, 0) >= 1000) {
            if (sharedPreferences.getInt(Const.PET_HEALTH, 0) < 70) {
                if (sharedPreferences.getInt(Const.PET_HEALTH, 100) + 50 > 100) {
                    SetTimeTextViewNotification("Tiền: -1000, " + "Sức khỏe của " + sharedPreferences.getString(Const.PET_NAME, "") + " đã hồi phục");
                    editor.putInt(Const.PET_HEALTH,  100);
                } else {
                    SetTimeTextViewNotification("Tiền: -1000, Độ no + 50");
                    editor.putInt(Const.PET_HEALTH,  sharedPreferences.getInt(Const.PET_HEALTH, 100) + 50);
                }
            } else {
                SetTimeTextViewNotification(sharedPreferences.getString(Const.PET_NAME, "") + " đang rất khỏe!");
            }
        } else {
            SetTimeTextViewNotification("Bạn không đủ tiền để mua thuốc");
        }

        sharedPreferences.edit().apply();
    }

    private void GetPetData() {
        if (sharedPreferences.getString(Const.PET_NAME, "") == "") {
            editor.putString(Const.PET_NAME, getIntent().getStringExtra(Const.PET_NAME));
        }
        if (sharedPreferences.getFloat(Const.PET_LEVEL, -1) == -1) {
            editor.putFloat(Const.PET_LEVEL, 1f);
        }
        if (sharedPreferences.getInt(Const.PET_MONEY, -1) == -1) {
            editor.putInt(Const.PET_MONEY, 0);
        }
        if (sharedPreferences.getInt(Const.PET_HUNGRY, -1) == -1) {
            editor.putInt(Const.PET_HUNGRY, 75);
        }
        if (sharedPreferences.getInt(Const.PET_HEALTH, -1) == -1) {
            editor.putInt(Const.PET_HEALTH, 75);
        }

        editor.apply();
    }

    private void SetTimeTextViewNotification(String str) {
        timerNotification = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long s = millisUntilFinished / 1000;
                tvNotificationStt.setText(str);
                tvNotificationStt.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                timerNotification.cancel();
                tvNotificationStt.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    private void SetIndexDecrease() {
        timerDecrease = new CountDownTimer(60000, 60000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (sharedPreferences.getInt(Const.PET_HUNGRY, -1) <= 0) {
                    editor.putInt(Const.PET_HUNGRY, 0);
                } else {
                    editor.putInt(Const.PET_HUNGRY, sharedPreferences.getInt(Const.PET_HUNGRY, 0) - 1);
                }

                if (sharedPreferences.getInt(Const.PET_HEALTH, -1) <= 0) {
                    editor.putInt(Const.PET_HEALTH, 0);
                } else {
                    editor.putInt(Const.PET_HEALTH, sharedPreferences.getInt(Const.PET_HEALTH, 0) - 1);
                }

                editor.apply();
            }

            @Override
            public void onFinish() {
                timerDecrease.start();
                Log.e("index", String.valueOf(sharedPreferences.getInt(Const.PET_HUNGRY, 100)));
                Log.e("index1", String.valueOf(sharedPreferences.getInt(Const.PET_HEALTH, 100)));
            }
        }.start();
    }

    private void SetPetIndexChange() {
        int intExp = getIntent().getIntExtra(Const.EXP, 0);
        float exp = (float) intExp / 250;

        int coin = getIntent().getIntExtra(Const.COIN, 0);

        int hungryIndex = (getIntent().getIntExtra(Const.EXP, 0) / 10) + (getIntent().getIntExtra(Const.COIN, 0) / 5);
        int healthIndex = (getIntent().getIntExtra(Const.EXP, 0) / 10) + (getIntent().getIntExtra(Const.COIN, 0) / 10);

        float currentLevel = sharedPreferences.getFloat(Const.PET_LEVEL, 1f) + exp;
        
        int currentMoney = sharedPreferences.getInt(Const.PET_MONEY, 0) + coin;
        
        int currentHungry = sharedPreferences.getInt(Const.PET_HUNGRY, 0) - hungryIndex;
        int currentHealth = sharedPreferences.getInt(Const.PET_HEALTH, 0) - healthIndex;
        currentHungry = currentHungry <= 0 ? 0 : currentHungry;
        currentHealth = currentHealth <= 0 ? 0 : currentHealth;
        
        editor.putFloat(Const.PET_LEVEL, currentLevel);
        editor.putInt(Const.PET_MONEY, currentMoney);
        editor.putInt(Const.PET_HUNGRY, currentHungry);
        editor.putInt(Const.PET_HEALTH, currentHealth);

        editor.apply();
    }
    
    private void CaiNayDeTest() {
        editor.putFloat(Const.PET_LEVEL, 1.75f);
        editor.putInt(Const.PET_MONEY, 100000);
        editor.putInt(Const.PET_HUNGRY, 100);
        editor.putInt(Const.PET_HEALTH, 100);
        
        editor.apply();
    }
}