package com.unknown.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.unknown.game.databinding.ActivityResultAtivityBinding;
import com.unknown.game.helper.Const;
import com.unknown.game.GameActivity;
import com.unknown.game.helper.SetFullScreen;
import com.unknown.game.helper.SetSoundEffects;

public class ResultAtivity extends AppCompatActivity {

    private ActivityResultAtivityBinding binding;

    private SharedPreferences sharedPreferencesStudy;
    private SharedPreferences sharedPreferencesGame;

    private SharedPreferences.Editor studyEditor;
    private SharedPreferences.Editor gameEditor;

    private MediaPlayer clickSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result_ativity);

        SetFullScreen.hideSystemUI(getWindow());

        sharedPreferencesStudy = getSharedPreferences(Const.STUDY_DATA, Context.MODE_PRIVATE);
        sharedPreferencesGame = getSharedPreferences(Const.GAME_DATA, Context.MODE_PRIVATE);

        studyEditor = sharedPreferencesStudy.edit();
        gameEditor = sharedPreferencesGame.edit();

        clickSound = SetSoundEffects.SetClickSound(this, R.raw.sound_click, 100);

        String strActivity = getIntent().getStringExtra(Const.ACTIVITY);

        if (strActivity.compareTo("gameActivity") == 0) {
            ResultGameActivity();
        } else if (strActivity.compareTo("studyActivity") == 0) {
            ResultStudyActivity();
        }
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetFullScreen.hideSystemUI(getWindow());
    }

    private void ResultStudyActivity() {
        int score = getIntent().getIntExtra(Const.SCORE, 0);
        binding.tvCurrentScore.setText(String.valueOf(score));
        Log.e(Const.SCORE, String.valueOf(score));

        studyEditor.putInt(Const.EXP, sharedPreferencesStudy.getInt(Const.EXP, 0) + score);
        studyEditor.apply();

        int highScore = sharedPreferencesStudy.getInt(Const.HIGH_SCORE, 0);
        if (score > highScore) {
            clickSound.start();
            highScore = score;
            studyEditor.putInt(Const.HIGH_SCORE, score);
            studyEditor.apply();
        }
        binding.tvHighScore.setText(String.valueOf(highScore));
        Log.e(Const.HIGH_SCORE, String.valueOf(highScore));

        binding.btnTryAgain.setOnClickListener(view -> {
            clickSound.start();
            startActivity(new Intent(ResultAtivity.this, StudyActivity.class));
            finish();
        });

        int exp = sharedPreferencesStudy.getInt(Const.EXP, score);
        Log.e(Const.EXP, String.valueOf(exp));
        binding.btnContinue.setOnClickListener(view -> {
            clickSound.start();
            startActivity(new Intent(ResultAtivity.this, MainActivity.class).putExtra(Const.EXP, exp));
            finish();
        });
    }

    private void ResultGameActivity() {
        int score = getIntent().getIntExtra(Const.SCORE, 0);
        binding.tvCurrentScore.setText(String.valueOf(score));
        Log.e(Const.SCORE, String.valueOf(score));

        gameEditor.putInt(Const.COIN, sharedPreferencesGame.getInt(Const.COIN, 0) + score);
        gameEditor.apply();

        Log.e("coin", String.valueOf(sharedPreferencesGame.getInt(Const.COIN, 0)));

        int highScore = sharedPreferencesGame.getInt(Const.HIGH_SCORE, 0);
        if (score > highScore) {
            highScore = score;
            gameEditor.putInt(Const.HIGH_SCORE, score);
            gameEditor.apply();
        }
        binding.tvHighScore.setText(String.valueOf(highScore));
        Log.e(Const.HIGH_SCORE, String.valueOf(highScore));

        binding.btnTryAgain.setOnClickListener(view -> {
            clickSound.start();
            startActivity(new Intent(ResultAtivity.this, GameActivity.class));
            finish();
        });

        int coin = sharedPreferencesGame.getInt(Const.COIN, score);
        Log.e(Const.COIN, String.valueOf(coin));
        binding.btnContinue.setOnClickListener(view -> {
            clickSound.start();
            startActivity(new Intent(ResultAtivity.this, MainActivity.class).putExtra(Const.COIN, coin));
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        gameEditor.putInt(Const.COIN, 0);
        gameEditor.apply();

        studyEditor.putInt(Const.EXP, 0);
        studyEditor.apply();

        Log.e("onDestroy", "Đã finish");
    }
}