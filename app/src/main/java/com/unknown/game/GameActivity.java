package com.unknown.game;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.unknown.game.databinding.ActivityGameBinding;
import com.unknown.game.helper.Const;
import com.unknown.game.helper.SetFullScreen;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private ActivityGameBinding binding;

    private int rocketY;
    private int no1X;
    private int no1Y;
    private int no2X;
    private int no2Y;
    private int starX;
    private int starY;

    //vc
    private int vc1X;
    private int vc1Y;
    private int vc2X;
    private int vc2Y;
    private int vc3X;
    private int vc3Y;
    private int vc4X;
    private int vc4Y;


    // set game play
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private boolean isAction = false;
    private boolean isPlay = false;

    //size
    private int frameHeight;
    private int rocketSize;
    private int screenWidth;

    private int score;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game);

        SetFullScreen.hideSystemUI(getWindow());

        mediaPlayer = MediaPlayer.create(this, R.raw.game_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(1, 1);
        mediaPlayer.start();

        defaultPosition();
        //Get Screen size
        binding.no1.setVisibility(View.INVISIBLE);
        binding.no2.setVisibility(View.INVISIBLE);
        binding.imgVc1.setVisibility(View.INVISIBLE);
        binding.imgVc2.setVisibility(View.INVISIBLE);
        binding.imgVc3.setVisibility(View.INVISIBLE);
        binding.imgVc4.setVisibility(View.INVISIBLE);
        binding.star.setVisibility(View.INVISIBLE);

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;

        binding.txtScore.setText("Score: "+ 0);
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

    private void changePos() {

        checkHit();

        // change pos No1, No2, Star, vc1, vc2, vc3, vc4
        no1X -= 16;
        if(no1X < 0){
            no1X = screenWidth + 20;
            no1Y = (int) Math.floor(Math.random()*(frameHeight - binding.no1.getHeight()));
        }
        binding.no1.setX(no1X);
        binding.no1.setY(no1Y);

        no2X -= 12;
        if(no2X < 0){
            no2X = screenWidth + 20;
            no2Y = (int) Math.floor(Math.random()*(frameHeight - binding.no2.getHeight()));
        }
        binding.no2.setX(no2X);
        binding.no2.setY(no2Y);
        starX -= 25;
        if (starX < 0) {
            starX = screenWidth + 20;
            starY = (int) Math.floor(Math.random() * (frameHeight - binding.star.getHeight()));
        }
        binding.star.setX(starX);
        binding.star.setY(starY);

        vc1X -= 10;
        if (vc1X < 0) {
            vc1X = screenWidth + 9000;
            vc1Y = (int) Math.floor(Math.random() * (frameHeight - binding.imgVc1.getHeight()));
        }
        binding.imgVc1.setX(vc1X);
        binding.imgVc1.setY(vc1Y);

        vc2X -= 9;
        if (vc2X < 0) {
            vc2X = screenWidth + 12000;
            vc2Y = (int) Math.floor(Math.random() * (frameHeight - binding.imgVc2.getHeight()));
        }
        binding.imgVc2.setX(vc2X);
        binding.imgVc2.setY(vc2Y);
        vc3X -= 9;
        if (vc3X < 0) {
            vc3X = screenWidth + 16000;
            vc3Y = (int) Math.floor(Math.random() * (frameHeight - binding.imgVc3.getHeight()));
        }
        binding.imgVc3.setX(vc3X);
        binding.imgVc3.setY(vc3Y);
        vc4X -= 11;
        if (vc4X < 0) {
            vc4X = screenWidth + 20000;
            vc4Y = (int) Math.floor(Math.random() * (frameHeight - binding.imgVc4.getHeight()));
        }
        binding.imgVc4.setX(vc4X);
        binding.imgVc4.setY(vc4Y);


        if (isAction) {
            rocketY -= 20;
        } else {
            rocketY += 20;
        }

        // check position
        if (rocketY < 0) rocketY = 0;
        if (rocketY > frameHeight - rocketSize) rocketY = frameHeight - rocketSize;

        binding.pet.setY(rocketY);
        binding.txtScore.setText("Score : " + score);
    }

    private void checkHit(){
        int centerNo1X = no1X + binding.no1.getWidth()/2;
        int centerNo1Y = no1Y + binding.no1.getHeight()/2;
        int centerNo2X = no2X + binding.no2.getWidth()/2;
        int centerNo2Y = no2Y + binding.no2.getHeight()/2;
        int centerStarX = starX + binding.star.getWidth()/2;
        int centerStarY = starY + binding.star.getHeight()/2;


        // plus score
        if (0 < centerNo1X && centerNo1X < rocketSize && rocketY <= centerNo1Y && centerNo1Y <= rocketY + rocketSize) {
            no1X = -20;
            score += 10;
        }

        if (0 < centerStarX && centerStarX < rocketSize && rocketY <= centerStarY && centerStarY <= rocketY + rocketSize) {
            starX = -20;
            score += 20;
        }
        if (0 < centerNo2X && centerNo2X < rocketSize && rocketY <= centerNo2Y && centerNo2Y <= rocketY + rocketSize) {
            no2X = -20;
            timer.cancel();
            timer = null;
            Intent intent = new Intent(GameActivity.this, ResultActivity.class);
            intent.putExtra(Const.SCORE, score);
            intent.putExtra(Const.ACTIVITY, "gameActivity");
            startActivity(intent);
            finish();
        }

    }

    private void defaultPosition() {
        //position No1,No2,Star
        binding.no1.setX(-60);
        binding.no1.setY(-60);
        binding.no2.setX(-60);
        binding.no2.setY(-200);
        binding.star.setX(-60);
        binding.star.setY(-60);

        binding.imgVc1.setX(-200);
        binding.imgVc1.setY(-200);
        binding.imgVc2.setX(-200);
        binding.imgVc2.setY(-200);
        binding.imgVc3.setX(-200);
        binding.imgVc3.setY(-200);
        binding.imgVc4.setX(-200);
        binding.imgVc4.setY(-200);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.test);
        binding.no2.startAnimation((animation));
        binding.imgVc1.startAnimation(animation);
        binding.imgVc2.startAnimation(animation);

    }

    public boolean onTouchEvent(MotionEvent me){
        if (!isPlay){
            isPlay = true;
            binding.no1.setVisibility(View.VISIBLE);
            binding.no2.setVisibility(View.VISIBLE);
            binding.imgVc1.setVisibility(View.VISIBLE);
            binding.imgVc2.setVisibility(View.VISIBLE);
            binding.imgVc3.setVisibility(View.VISIBLE);
            binding.imgVc4.setVisibility(View.VISIBLE);
            binding.star.setVisibility(View.VISIBLE);
            binding.txtStart.setVisibility(View.GONE);
            frameHeight = binding.frame.getHeight();
            rocketY = (int) binding.pet.getY();
            rocketSize = binding.pet.getHeight();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(() -> changePos());
                }
            }, 0, 20);
        } else {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                isAction = true;
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                isAction = false;
            }

        }
        return true;
    }
}