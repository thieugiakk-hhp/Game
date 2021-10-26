package com.unknown.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    private static final String PET_INFORMATION = "PET_INFORMATION";
    private static final String PET_NAME = "PET_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SetFullScreen.hideSystemUI(getWindow()); //Set Fullscreen_Hide System UI

        SetBackgroundMusics.SetPlayBackgroundMusic(this, R.raw.background_music, 100);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SetBackgroundMusics.SetPauseBackgroundMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SetBackgroundMusics.SetResumeBackgroundMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SetBackgroundMusics.SetPauseBackgroundMusic();
    }

    public void btnPlayGameOnClick(View view) {
        view.setVisibility(View.INVISIBLE);
        SharedPreferences sharedPreferences= getSharedPreferences(PET_INFORMATION, Context.MODE_PRIVATE);

        if (sharedPreferences.getString(PET_NAME, "") == "") {
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
                        editor.putString(PET_NAME, petName);
                        editor.apply();

                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        intent.putExtra(PET_NAME, sharedPreferences.getString(PET_NAME, ""));
                        startActivity(intent);
                    }
                }
            });
        }
        else {
            Button btnCharacter = (Button) findViewById(R.id.btnCharacter);
            btnCharacter.setVisibility(View.VISIBLE);
            btnCharacter.setText(sharedPreferences.getString(PET_NAME, ""));

            btnCharacter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    intent.putExtra(PET_NAME, sharedPreferences.getString(PET_NAME, ""));
                    startActivity(intent);
                }
            });
        }
    }
}