package com.unknown.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.unknown.game.helper.Const;
import com.unknown.game.helper.SetFullScreen;

public class StartActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private MediaPlayer clickSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SetFullScreen.hideSystemUI(getWindow()); //Set Fullscreen_Hide System UI

        SetSound();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
        SetFullScreen.hideSystemUI(getWindow());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();

        Log.e("onDestroy", "Đã finish");
    }

    public void btnPlayGameOnClick(View view) {
        view.setVisibility(View.INVISIBLE);
        SharedPreferences sharedPreferences= getSharedPreferences(Const.PET_INFORMATION, Context.MODE_PRIVATE);

        if (sharedPreferences.getString(Const.PET_NAME, "") == "") {
            EditText edtName = (EditText) findViewById(R.id.edtName);
            edtName.setVisibility(View.VISIBLE);
            Button btnOK = (Button) findViewById(R.id.btnOK);
            btnOK.setVisibility(View.VISIBLE);
            Toast.makeText(this,"Nhập tên thú cưng của bạn",Toast.LENGTH_LONG).show();
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtName.getText().toString().length() <= 1) {
                        TextView tvNoti = (TextView) findViewById(R.id.tvNoti);
                        tvNoti.setVisibility(View.VISIBLE);
                    }
                    else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String petName = edtName.getText().toString().trim();
                        editor.putString(Const.PET_NAME, petName);
                        editor.apply();

                        clickSound.start();
                        //mediaPlayer.pause();
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        intent.putExtra(Const.PET_NAME, sharedPreferences.getString(Const.PET_NAME, ""));
                        startActivity(intent);
                        view.setVisibility(View.VISIBLE);
                        edtName.setVisibility(View.INVISIBLE);
                        btnOK.setVisibility(View.INVISIBLE);
                        //finish();
                    }
                }
            });
        }
        else {
            clickSound.start();
            //mediaPlayer.pause();
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            intent.putExtra(Const.PET_NAME, sharedPreferences.getString(Const.PET_NAME, ""));
            startActivity(intent);
            view.setVisibility(View.VISIBLE);
            //finish();
        }
    }

    private void SetSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        clickSound = MediaPlayer.create(this, R.raw.sound_click);

        mediaPlayer.setLooping(true);
        clickSound.setLooping(false);

        mediaPlayer.setVolume(1, 1);
        clickSound.setVolume(1, 1);
    }
}