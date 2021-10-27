package com.unknown.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.unknown.game.databinding.ActivityResultAtivityBinding;
import com.unknown.game.helper.Const;
import com.unknown.game.GameActivity;

public class ResultAtivity extends AppCompatActivity {

    private ActivityResultAtivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result_ativity);
        Intent intent = getIntent();

        int score = intent.getIntExtra(Const.SCORE, 0);
        binding.txtCurrentScore.setText(score + "");

        SharedPreferences preferences = getSharedPreferences(Const.GAME_DATA, Context.MODE_PRIVATE);
        int highScore = preferences.getInt(Const.HIGH_SCORE, 0);
        if(score > highScore){
            binding.txtHighScore.setText("High Score: " + score);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(Const.HIGH_SCORE, score);
            editor.apply();
        }else{
            binding.txtHighScore.setText("High Score: " + highScore);
        }
        binding.btnPlayAgain.setOnClickListener(view -> {
            startActivity(new Intent(ResultAtivity.this, GameActivity.class));
            finish();
        });
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

}