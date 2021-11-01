package com.unknown.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.unknown.game.helper.Const;
import com.unknown.game.helper.SetFullScreen;

public class ResultActivity extends AppCompatActivity {

    private TextView tvCurrentScore;
    private TextView tvHighScore;

    private Button btnTryAgain;
    private Button btnContinue;

    private SharedPreferences sharedPreferencesStudy;
    private SharedPreferences sharedPreferencesGame;

    private SharedPreferences.Editor studyEditor;
    private SharedPreferences.Editor gameEditor;

    private MediaPlayer clickSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SetFullScreen.hideSystemUI(getWindow());

        sharedPreferencesStudy = getSharedPreferences(Const.STUDY_DATA, Context.MODE_PRIVATE);
        sharedPreferencesGame = getSharedPreferences(Const.GAME_DATA, Context.MODE_PRIVATE);

        studyEditor = sharedPreferencesStudy.edit();
        gameEditor = sharedPreferencesGame.edit();

        clickSound = MediaPlayer.create(this, R.raw.sound_click);
        clickSound.setLooping(false);
        clickSound.setVolume(1, 1);

        tvCurrentScore = findViewById(R.id.tvCurrentScore);
        tvHighScore = findViewById(R.id.tvHighScore);

        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnContinue = findViewById(R.id.btnContinue);

        String strActivity = getIntent().getStringExtra(Const.ACTIVITY);
        Log.e("Activity ", strActivity);
        if (strActivity.compareTo("gameActivity") == 0) {
            ResultGameActivity();
        } else {
            ResultStudyActivity();
        }
    }

    private void ResultStudyActivity() {
        int score = getIntent().getIntExtra(Const.SCORE, 0);
        tvCurrentScore.setText(String.valueOf(score));
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
        tvHighScore.setText(String.valueOf(highScore));
        Log.e(Const.HIGH_SCORE, String.valueOf(highScore));

        btnTryAgain.setOnClickListener(view -> {
            clickSound.start();
            startActivity(new Intent(this, StudyActivity.class));
            finish();
        });

        int exp = sharedPreferencesStudy.getInt(Const.EXP, score);
        Log.e(Const.EXP, String.valueOf(exp));

        btnContinue.setOnClickListener(view -> {
            clickSound.start();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(Const.EXP, exp);
            startActivity(intent);
            studyEditor.putInt(Const.EXP, 0);
            studyEditor.apply();
            finish();
        });
    }

    private void ResultGameActivity() {
        int score = getIntent().getIntExtra(Const.SCORE, 0);
        tvCurrentScore.setText(String.valueOf(score));
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
        tvHighScore.setText(String.valueOf(highScore));
        Log.e(Const.HIGH_SCORE, String.valueOf(highScore));

        btnTryAgain.setOnClickListener(view -> {
            clickSound.start();
            startActivity(new Intent(this, GameActivity.class));
            finish();
        });

        int coin = sharedPreferencesGame.getInt(Const.COIN, score);
        Log.e(Const.COIN, String.valueOf(coin));
        btnContinue.setOnClickListener(view -> {
            clickSound.start();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(Const.COIN, coin);
            startActivity(intent);
            gameEditor.putInt(Const.COIN, 0);
            gameEditor.apply();
            finish();
        });
    }
}