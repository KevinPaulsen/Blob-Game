package jakemcdowell.blobsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.media.MediaPlayer;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playgamebuttonclick(View v) {
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void entershopbuttonclick(View v) {
        Intent intent2 = new Intent(this,ShopActivity.class);
        startActivity(intent2);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void howtoplaybuttonclick(View v) {
        Intent intent3 = new Intent(this,HelpActivity.class);
        startActivity(intent3);
    }
}
