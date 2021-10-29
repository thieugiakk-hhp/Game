package com.unknown.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class ResultAtivity extends AppCompatActivity {

    private ActivityResultAtivityBinding binding;

    private SharedPreferences sharedPreferencesStudy;
    private SharedPreferences sharedPreferencesGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result_ativity);

        SetFullScreen.hideSystemUI(getWindow());

        /*sharedPreferencesGame = getSharedPreferences(Const.GAME_DATA, Context.MODE_PRIVATE);
        ResultGameActivity();*/

        sharedPreferencesStudy = getSharedPreferences(Const.STUDY_DATA, Context.MODE_PRIVATE);
        sharedPreferencesGame = getSharedPreferences(Const.GAME_DATA, Context.MODE_PRIVATE);

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

        sharedPreferencesStudy.edit().putInt(Const.EXP, sharedPreferencesStudy.getInt(Const.EXP, 0) + score);
        sharedPreferencesStudy.edit().apply();

        int highScore = sharedPreferencesStudy.getInt(Const.HIGH_SCORE, 0);
        if (score > highScore) {
            highScore = score;
            sharedPreferencesStudy.edit().putInt(Const.HIGH_SCORE, score);
            sharedPreferencesStudy.edit().apply();
        }
        binding.tvHighScore.setText(String.valueOf(highScore));
        Log.e(Const.HIGH_SCORE, String.valueOf(highScore));

        binding.btnTryAgain.setOnClickListener(view -> {
            startActivity(new Intent(ResultAtivity.this, StudyActivity.class));
            finish();
        });

        int exp = sharedPreferencesStudy.getInt(Const.EXP, score);
        Log.e(Const.EXP, String.valueOf(exp));
        binding.btnContinue.setOnClickListener(view -> {
            startActivity(new Intent(ResultAtivity.this, MainActivity.class).putExtra(Const.EXP, exp));
            sharedPreferencesStudy.edit().putInt(Const.EXP, 0);
            sharedPreferencesStudy.edit().apply();
            finish();
        });
    }

    private void ResultGameActivity() {
        int score = getIntent().getIntExtra(Const.SCORE, 0);
        binding.tvCurrentScore.setText(String.valueOf(score));
        Log.e(Const.SCORE, String.valueOf(score));

        sharedPreferencesGame.edit().putInt(Const.COIN, sharedPreferencesGame.getInt(Const.COIN, 0) + score);
        sharedPreferencesGame.edit().apply();

        int highScore = sharedPreferencesGame.getInt(Const.HIGH_SCORE, 0);
        if (score > highScore) {
            highScore = score;
            sharedPreferencesGame.edit().putInt(Const.HIGH_SCORE, score);
            sharedPreferencesGame.edit().apply();
        }
        binding.tvHighScore.setText(String.valueOf(highScore));
        Log.e(Const.HIGH_SCORE, String.valueOf(highScore));

        binding.btnTryAgain.setOnClickListener(view -> {
            startActivity(new Intent(ResultAtivity.this, GameActivity.class));
            finish();
        });

        int coin = sharedPreferencesGame.getInt(Const.COIN, score);
        Log.e(Const.COIN, String.valueOf(coin));
        binding.btnContinue.setOnClickListener(view -> {
            startActivity(new Intent(ResultAtivity.this, MainActivity.class).putExtra(Const.COIN, coin));
            sharedPreferencesGame.edit().putInt(Const.COIN, 0);
            sharedPreferencesGame.edit().apply();
            finish();
        });
    }
}