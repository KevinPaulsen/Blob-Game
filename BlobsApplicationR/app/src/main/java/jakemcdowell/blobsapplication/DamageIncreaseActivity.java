package jakemcdowell.blobsapplication;

import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DamageIncreaseActivity extends AppCompatActivity {
    public int currentprice;
    boolean isClicked = false;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        saveAndLoadData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_increase);
        TextView golddisplay = (TextView) findViewById(R.id.textView12);
        golddisplay.setText("" + PlayerData.currentGold);
        ImageView e = (ImageView) findViewById(R.id.soldout);
        if (PlayerData.damageIncreaseLevel == 0) {
            currentprice = Constants.damageIncreasePriceLevel1;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.damageIncreaseLevel == 1) {
            currentprice = Constants.damageIncreasePriceLevel2;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.damageIncreaseLevel == 2) {
            currentprice = Constants.damageIncreasePriceLevel3;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.damageIncreaseLevel == 3) {
            currentprice = Constants.damageIncreasePriceLevel4;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.damageIncreaseLevel == 4) {
            currentprice = Constants.damageIncreasePriceLevel5;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.damageIncreaseLevel == 5) {
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.fullcircle);
            ViewCompat.setTranslationZ(e, 10);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        saveAndLoadData();
    }

    public void SaveInt(String key, int value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void SaveBoolean(String key, boolean value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void saveAndLoadData() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (PlayerData.currentGold > 0 ) {
            SaveInt("currentgoldkey", PlayerData.currentGold);
            PlayerData.currentGold = sharedPreferences.getInt("currentgoldkey",0);
        } else {
            PlayerData.currentGold = sharedPreferences.getInt("currentgoldkey",0);
            SaveInt("currentgoldkey", PlayerData.currentGold);
        }
        if (PlayerData.currentLevel > 0 ) {
            SaveInt("currentlevelkey", PlayerData.currentLevel);
            PlayerData.currentLevel = sharedPreferences.getInt("currentlevelkey",1);
        } else {
            PlayerData.currentLevel = sharedPreferences.getInt("currentlevelkey",1);
            SaveInt("currentlevelkey", PlayerData.currentLevel);
        }
        if (PlayerData.highestLevel > 0 ) {
            SaveInt("highestlevelkey", PlayerData.highestLevel);
            PlayerData.highestLevel = sharedPreferences.getInt("highestlevelkey",0);
        } else {
            PlayerData.highestLevel = sharedPreferences.getInt("highestlevelkey",0);
            SaveInt("highestlevelkey", PlayerData.highestLevel);
        }
        if (PlayerData.totalEarnedGoldInLevel > 0 ) {
            SaveInt("totalearnedgoldinlevelkey", PlayerData.totalEarnedGoldInLevel);
            PlayerData.totalEarnedGoldInLevel = sharedPreferences.getInt("totalearnedgoldinlevelkey",0);
        } else {
            PlayerData.totalEarnedGoldInLevel = sharedPreferences.getInt("totalearnedgoldinlevelkey",0);
            SaveInt("totalearnedgoldinlevelkey", PlayerData.totalEarnedGoldInLevel);
        }
        if (PlayerData.damageIncreaseLevel > 0 ) {
            SaveInt("damageincreaselevelkey", PlayerData.damageIncreaseLevel);
            PlayerData.damageIncreaseLevel = sharedPreferences.getInt("damageincreaselevelkey",0);
        } else {
            PlayerData.damageIncreaseLevel = sharedPreferences.getInt("damageincreaselevelkey",0);
            SaveInt("damageincreaselevelkey", PlayerData.damageIncreaseLevel);
        }
        if (PlayerData.radiusIncreaseLevel > 0 ) {
            SaveInt("radiusincreaselevelkey", PlayerData.radiusIncreaseLevel);
            PlayerData.radiusIncreaseLevel = sharedPreferences.getInt("radiusincreaselevelkey",0);
        } else {
            PlayerData.radiusIncreaseLevel = sharedPreferences.getInt("radiusincreaselevelkey",0);
            SaveInt("radiusincreaselevelkey", PlayerData.radiusIncreaseLevel);
        }
        if (PlayerData.numberOfPesticide > 0 ) {
            SaveInt("numberofpesticidekey", PlayerData.numberOfPesticide);
            PlayerData.numberOfPesticide = sharedPreferences.getInt("numberofpesticidekey",0);
        } else {
            PlayerData.numberOfPesticide = sharedPreferences.getInt("numberofpesticidekey",0);
            SaveInt("numberofpesticidekey", PlayerData.numberOfPesticide);
        }
        if (PlayerData.goldIncreaseLevel > 0 ) {
            SaveInt("goldincreaselevelkey", PlayerData.goldIncreaseLevel);
            PlayerData.goldIncreaseLevel = sharedPreferences.getInt("goldincreaselevelkey",0);
        } else {
            PlayerData.goldIncreaseLevel = sharedPreferences.getInt("goldincreaselevelkey",0);
            SaveInt("goldincreaselevelkey", PlayerData.goldIncreaseLevel);
        }
        PlayerData.shouldResumeGame = sharedPreferences.getBoolean("shouldresumegamekey", false);
        PlayerData.shouldBeginGame = sharedPreferences.getBoolean("shouldbegingamekey", false);
        SaveBoolean("shouldresumegamekey", PlayerData.shouldResumeGame);
        SaveBoolean("shouldbegingamekey", PlayerData.shouldBeginGame);

    }

    public void purchaseupgradebutton(View v)  {
        if (PlayerData.currentGold >= currentprice && PlayerData.damageIncreaseLevel < 5 && isClicked == false) {
                PlayerData.damageIncreaseLevel = PlayerData.damageIncreaseLevel + 1;
                PlayerData.currentGold = PlayerData.currentGold - currentprice;
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            isClicked = true;
        }
    }

    @Override
    public void onBackPressed () {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void leavepurchasebutton(View v) {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
