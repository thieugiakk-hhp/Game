package com.unknown.game;

import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class StudyActivity extends AppCompatActivity {

    private Context context = this;
    private TextView tvCalculation;
    private TextView tvScore;
    private Button btnTrue;
    private Button btnFalse;

    private Button btnSetVolume;

    private ProgressBar progressBarLeft;
    private ProgressBar progressBarRight;

    private boolean exactly = false;

    private boolean result;

    private CountDownTimer timer;

    private TextView tvTap;

    private ConstraintLayout playLayout;
    private ConstraintLayout endGameLayout;

    private int score;

    private SharedPreferences sharedPreferences;

    private boolean music;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        SetFullScreen.hideSystemUI(getWindow());

        sharedPreferences = getSharedPreferences("SCORE", MODE_PRIVATE);

        tvCalculation = findViewById(R.id.tvCalculation);
        tvScore = findViewById(R.id.tvScore);

        progressBarLeft = findViewById(R.id.progressBarLeft);
        progressBarRight = findViewById(R.id.progressBarRight);

        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);

        music = true;
        btnSetVolume = findViewById(R.id.btnSetVolume);
    }

    @Override
    protected void onStart() {
        super.onStart();

        score = 0;

        btnTrue.setOnClickListener(onClickListener);
        btnFalse.setOnClickListener(onClickListener);

        tvTap = findViewById(R.id.tvTap);
        tvTap.setOnClickListener(tapToStart);

        btnSetVolume.setOnClickListener(setVolumeOnClickListener);

        mediaPlayer = SetBackgroundMusics.SetBackgroundMusic(StudyActivity.this, R.raw.sound_tiktok, 100);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_study);
        onStart();
    }

    private void initFreakingMath() {
        FreakingMath freakingMath = FreakingMath.randomFreakingMath();
        result = freakingMath.isResult();
        tvCalculation.setText(freakingMath.getN1() + freakingMath.getSign() + freakingMath.getN2());

        CountDown();
        timer.start();
    }

    private void CountDown() {
        timer = new CountDownTimer(7000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long s = millisUntilFinished / 1000;
                progressBarLeft.setProgress((int) s);
                progressBarRight.setProgress((int) s);

                mediaPlayer.start();
            }

            @Override
            public void onFinish() {
                timer.cancel();
                SetBackgroundMusics.SetPauseMusic(mediaPlayer);
                setEndGameLayout();
            }
        };
    }

    private void setEndGameLayout() {
        LayoutInflater inflater = StudyActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.mini_game_result_dialog, null);
        setContentView(view);

        TextView tvYourScore = findViewById(R.id.tvYourScore);
        TextView tvHighScore = findViewById(R.id.tvHighScore);

        tvYourScore.setText(String.valueOf(score));
        tvHighScore.setText(String.valueOf(sharedPreferences.getInt("highScore", score)));

        Button btnTryAgain = findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putInt("yourScore", sharedPreferences.getInt("yourScore", 0) + score);
                sharedPreferences.edit().apply();
                onRestart();
            }
        });

        Button btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudyActivity.this, MainActivity.class).putExtra("petIndex", sharedPreferences.getInt("yourScore", score)));
                finish();
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            String tag = String.valueOf(view.getTag());
            boolean b = Boolean.parseBoolean(tag);

            if (result == b) {
                Log.e("res", String.valueOf(result));
                score += 10;
                tvScore.setText(String.valueOf(score));
                initFreakingMath();
            } else {
                if (score > sharedPreferences.getInt("highScore", 0)) {
                    sharedPreferences.edit().putInt("highScore", score);
                }
                sharedPreferences.edit().putInt("yourScore", score);
                //playLayout.setVisibility(View.INVISIBLE);
                setEndGameLayout();
                Log.e("res", String.valueOf(result));
            }
            sharedPreferences.edit().apply();
        }
    };

    private View.OnClickListener tapToStart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tvTap.setVisibility(View.INVISIBLE);
            playLayout = findViewById(R.id.playLayout);
            playLayout.setVisibility(View.VISIBLE);

            initFreakingMath();
        }
    };

    private View.OnClickListener setVolumeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (music) {
                btnSetVolume.setBackground(getDrawable(R.drawable.ic_mute));
                music = !music;
                Log.e("music", String.valueOf(music));
                SetBackgroundMusics.SetVolume(mediaPlayer, 0, 0);
            } else {
                btnSetVolume.setBackground(getDrawable(R.drawable.ic_volume));
                music = !music;
                Log.e("music", String.valueOf(music));
                SetBackgroundMusics.SetVolume(mediaPlayer, 1, 1);
            }
        }
    };

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
}