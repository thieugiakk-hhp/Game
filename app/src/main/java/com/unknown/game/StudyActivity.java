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

import com.unknown.game.helper.Const;
import com.unknown.game.helper.SetFullScreen;

import java.util.Set;

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

    private int score;

    private boolean music;

    private MediaPlayer mediaPlayer;

    private MediaPlayer clickSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        SetFullScreen.hideSystemUI(getWindow());

        SetSound();

        tvCalculation = findViewById(R.id.tvCalculation);
        tvScore = findViewById(R.id.tvScore);

        progressBarLeft = findViewById(R.id.progressBarLeft);
        progressBarRight = findViewById(R.id.progressBarRight);

        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);

        music = true;

        btnTrue.setOnClickListener(onClickListener);
        btnFalse.setOnClickListener(onClickListener);

        tvTap = findViewById(R.id.tvTap);
        tvTap.setOnClickListener(tapToStart);

        btnSetVolume = findViewById(R.id.btnSetVolume);
        btnSetVolume.setOnClickListener(setVolumeOnClickListener);

        score = 0;
    }

    private void initFreakingMath() {
        FreakingMath freakingMath = FreakingMath.randomFreakingMath();
        result = freakingMath.isResult();
        tvCalculation.setText(freakingMath.getN1() + freakingMath.getSign() + freakingMath.getN2());

        CountDown();
    }

    private void CountDown() {
        if (timer != null) {
            timer.cancel();
        }

        mediaPlayer.start();

        timer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long s = millisUntilFinished / 1000;
                progressBarLeft.setProgress((int) s);
                progressBarRight.setProgress((int) s);
            }

            @Override
            public void onFinish() {
                timer.cancel();
                Intent intent = new Intent(StudyActivity.this, ResultActivity.class);
                intent.putExtra(Const.SCORE, score);
                intent.putExtra(Const.ACTIVITY, "studyActivity");
                startActivity(intent);
                finish();
            }
        };

        timer.start();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            clickSound.start();
            mediaPlayer.pause();

            // TODO Auto-generated method stub
            String tag = String.valueOf(view.getTag());
            boolean b = Boolean.parseBoolean(tag);

            if (result == b) {
                score += 10;
                tvScore.setText(String.valueOf(score));
                initFreakingMath();
            } else {
                timer.onFinish();
            }
        }
    };

    private View.OnClickListener tapToStart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clickSound.start();
            tvTap.setVisibility(View.INVISIBLE);
            playLayout = findViewById(R.id.playLayout);
            playLayout.setVisibility(View.VISIBLE);

            initFreakingMath();
        }
    };

    private View.OnClickListener setVolumeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickSound.start();
            if (music) {
                btnSetVolume.setBackground(getDrawable(R.drawable.ic_mute));
                music = !music;
                Log.e("music", String.valueOf(music));
                mediaPlayer.setVolume(0, 0);
                clickSound.setVolume(0, 0);
            } else {
                btnSetVolume.setBackground(getDrawable(R.drawable.ic_volume));
                music = !music;
                Log.e("music", String.valueOf(music));
                mediaPlayer.setVolume(1, 1);
                clickSound.setVolume(1, 1);
            }
        }
    };

    private void SetSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_tiktok);
        clickSound = MediaPlayer.create(this, R.raw.sound_click);

        mediaPlayer.setLooping(true);
        clickSound.setLooping(false);

        mediaPlayer.setVolume(1, 1);
        clickSound.setVolume(1, 1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetFullScreen.hideSystemUI(getWindow());
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
    }
}