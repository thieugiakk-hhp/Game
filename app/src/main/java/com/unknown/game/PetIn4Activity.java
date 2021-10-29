package com.unknown.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.unknown.game.helper.Const;

public class PetIn4Activity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private TextView tvName;
    private TextView tvLevel;
    private TextView tvMoney;
    private TextView tvHungry;
    private TextView tvHealth;
    private SeekBar sbHungry;
    private SeekBar sbHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_in4);

        SetFullScreen.hideSystemUI(getWindow()); //Set Fullscreen_Hide System UI

        sharedPreferences = getSharedPreferences(Const.PET_INFORMATION, Context.MODE_PRIVATE);

        tvName = (TextView) findViewById(R.id.tvPetName);
        tvLevel = (TextView) findViewById(R.id.tvPetLevel);
        tvMoney = (TextView) findViewById(R.id.tvPetMoney);
        tvHungry = (TextView) findViewById(R.id.tvPetHungry);
        tvHealth = (TextView) findViewById(R.id.tvPetHealth);
        sbHungry = (SeekBar) findViewById(R.id.sbHungry);
        sbHealth = (SeekBar) findViewById(R.id.sbHealth);

        GetPetIn4();
        GetPetStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetFullScreen.hideSystemUI(getWindow());
    }

    private void GetPetIn4() {
        tvName.setText(sharedPreferences.getString(Const.PET_NAME, ""));
        tvLevel.setText(String.valueOf(Math.round(sharedPreferences.getFloat(Const.PET_LEVEL, 1f))));
        tvMoney.setText(String.valueOf(sharedPreferences.getInt(Const.PET_MONEY, 0)));
    }

    private void GetPetStatus() {
        tvHungry.setText(String.valueOf(sharedPreferences.getInt(Const.PET_HUNGRY, 50)));
        tvHealth.setText(String.valueOf(sharedPreferences.getInt(Const.PET_HEALTH, 50)));

        sbHungry.setProgress(sharedPreferences.getInt(Const.PET_HUNGRY, 0));
        sbHungry.setEnabled(false);
        sbHealth.setProgress(sharedPreferences.getInt(Const.PET_HEALTH, 0));
        sbHealth.setEnabled(false);
    }
}