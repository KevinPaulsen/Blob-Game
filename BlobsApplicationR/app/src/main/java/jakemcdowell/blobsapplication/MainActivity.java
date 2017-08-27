package jakemcdowell.blobsapplication;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import static android.R.color.transparent;


public class MainActivity extends AppCompatActivity {
    public static boolean isMuted = false;
    static boolean mainthemestart = true;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        saveAndLoadData();
        mainthemestart = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView a = (TextView) findViewById(R.id.textView20);
        a.setText("Version: " + Constants.version);
        TextView b = (TextView) findViewById(R.id.textView32);
        b.setText("High Score: Level " + PlayerData.highestLevel);
        if (PlayerData.shouldResumeGame) {
            ((Button) (findViewById(R.id.button))).setText("Continue Level " + (PlayerData.currentLevel));
        }
        if (PlayerData.shouldBeginGame) {
            ((Button) (findViewById(R.id.button))).setText("Begin Level " + (PlayerData.currentLevel));
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

    public void onWindowFocusChanged(boolean hasFocus) {
        if (mainthemestart) {
            playMainTheme();
            mainthemestart = false;
        }
        else {
            mainthemestart = true;
        }
        if (isMuted && findViewById(R.id.button20).getVisibility() == View.VISIBLE) {
            findViewById(R.id.button20).setBackgroundResource(R.drawable.x);
        }
        if (!PlayerData.shouldResumeGame) {
            Button restartGame = (Button) findViewById(R.id.button24);
            Button playGame = (Button) findViewById(R.id.button);
            Button continueGame = (Button) findViewById(R.id.button25);
            restartGame.setVisibility(View.GONE);
            playGame.setVisibility(View.GONE);
            continueGame.setVisibility(View.VISIBLE);
        }
        else {
            Button restartGame = (Button) findViewById(R.id.button24);
            Button playGame = (Button) findViewById(R.id.button);
            Button continueGame = (Button) findViewById(R.id.button25);
            restartGame.setVisibility(View.VISIBLE);
            playGame.setVisibility(View.VISIBLE);
            continueGame.setVisibility(View.GONE);
        }
        /*if (PlayerData.currentLevel > 1) {
            Button restartGameButton = (Button) findViewById(R.id.button23);
            restartGameButton.setVisibility(View.VISIBLE);
            Button continueGameButton = (Button) findViewById(R.id.button);
            continueGameButton.setMinimumWidth(0);
            continueGameButton.setWidth(162);
        }
        else {
            Button restartGameButton = (Button) findViewById(R.id.button23);
            restartGameButton.setVisibility(View.VISIBLE);
            Button continueGameButton = (Button) findViewById(R.id.button);
            continueGameButton.setMinimumWidth(0);
            continueGameButton.setWidth(355);
        }*/
    }

    public void playMainTheme() {
        MediaPlayer maintheme = MediaPlayer.create(this, R.raw.maintheme);
        MusicPlayer.startSong(maintheme);
    }

    /*public void restartGameButtonClick(View v) {
        PlayerData.currentLevel = 1;
    }*/

    public void playgamebuttonclick(View v) {
        MediaPlayer mainTheme = MediaPlayer.create(this, R.raw.maintheme);
        MusicPlayer.resetMusic(mainTheme);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void restartGameClick(View v) {
        PlayerData.currentLevel = 1;
        MediaPlayer mainTheme = MediaPlayer.create(this, R.raw.maintheme);
        MusicPlayer.resetMusic(mainTheme);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void mutebuttonclick(View v) {
        if (isMuted) {
            findViewById(R.id.button20).setBackgroundResource(transparent);
            isMuted = !isMuted;
            playMainTheme();
        } else {
            findViewById(R.id.button20).setBackgroundResource(R.drawable.x);
            MusicPlayer.stopCurrentMusic();
            isMuted = !isMuted;
        }

    }

    public void entershopbuttonclick(View v) {
        MediaPlayer maintheme = MediaPlayer.create(this, R.raw.maintheme);
        MusicPlayer.resetMusic(maintheme);
        ShopActivity.shopmusicstart = true;
        Intent intent2 = new Intent(this,ShopActivity.class);
        startActivity(intent2);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void howtoplaybuttonclick(View v) {
        Intent intent3 = new Intent(this,HelpActivity.class);
        startActivity(intent3);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void extrasbuttonclick(View v) {
        Intent intent4 = new Intent(this, ExtrasActivity.class);
        startActivity(intent4);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
