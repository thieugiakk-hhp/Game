package com.unknown.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class PetIn4Activity extends AppCompatActivity {
    private static final String PET_INFORMATION = "PET_INFORMATION";
    private static final String PET_NAME = "PET_NAME";
    private static final String PET_LEVEL = "PET_LEVEL";
    private static final String PET_MONEY = "PET_MONEY";
    private static final String PET_HUNGRY = "PET_HUNGRY";
    private static final String PET_HEALTH = "PET_HEALTH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_in4);

        SetFullScreen.hideSystemUI(getWindow()); //Set Fullscreen_Hide System UI

        SharedPreferences sharedPreferences= this.getSharedPreferences(PET_INFORMATION, Context.MODE_PRIVATE);
        //Intent intent = getIntent();

        //GetPetData.GetAllIn4(sharedPreferences, intent, PET_NAME, PET_LEVEL, PET_MONEY, PET_HUNGRY, PET_HEALTH);

        TextView tvName = (TextView) findViewById(R.id.tvPetName);
        TextView tvLevel = (TextView) findViewById(R.id.tvPetLevel);
        TextView tvMoney = (TextView) findViewById(R.id.tvPetMoney);
        TextView tvHungry = (TextView) findViewById(R.id.tvPetHungry);
        TextView tvHealth = (TextView) findViewById(R.id.tvPetHealth);
        SeekBar sbHungry = (SeekBar) findViewById(R.id.sbHungry);
        SeekBar sbHealth = (SeekBar) findViewById(R.id.sbHealth);

        /*tvName.setText(sharedPreferences.getString(PET_NAME, ""));
        tvLevel.setText(String.valueOf(Math.round(sharedPreferences.getFloat(PET_LEVEL, 3f))));
        tvMoney.setText(String.valueOf(sharedPreferences.getInt(PET_MONEY, 0)));*/

        GetPetIn4(sharedPreferences, tvName, tvLevel, tvMoney);
        GetPetStatus(sharedPreferences, tvHungry, sbHungry, tvHealth, sbHealth);
    }

    private void GetPetIn4(SharedPreferences sharedPreferences, TextView tvName, TextView tvLevel, TextView tvMoney) {
        tvName.setText(sharedPreferences.getString(PET_NAME, ""));
        tvLevel.setText(String.valueOf(Math.round(sharedPreferences.getFloat(PET_LEVEL, 1f))));
        tvMoney.setText(String.valueOf(sharedPreferences.getInt(PET_MONEY, 0)));
    }

    private void GetPetStatus(SharedPreferences sharedPreferences, TextView tvHungry, SeekBar sbHungry, TextView tvHealth, SeekBar sbHealth) {
        tvHungry.setText(String.valueOf(sharedPreferences.getInt(PET_HUNGRY, 50)));
        tvHealth.setText(String.valueOf(sharedPreferences.getInt(PET_HEALTH, 50)));

        sbHungry.setProgress(sharedPreferences.getInt(PET_HUNGRY, 15));
        sbHungry.setEnabled(false);
        sbHealth.setProgress(sharedPreferences.getInt(PET_HEALTH, 15));
        sbHealth.setEnabled(false);
    }
}