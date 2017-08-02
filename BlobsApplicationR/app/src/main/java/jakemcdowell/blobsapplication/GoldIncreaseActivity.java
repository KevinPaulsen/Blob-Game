package jakemcdowell.blobsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GoldIncreaseActivity extends AppCompatActivity {
    public int currentprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_increase);
        TextView golddisplay = (TextView) findViewById(R.id.textView12);
        golddisplay.setText("" + PlayerData.currentGold);
        if (PlayerData.goldIncreaseLevel == 0) {
            currentprice = Constants.goldIncreasePriceLevel1;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.goldIncreaseLevel == 1) {
            currentprice = Constants.goldIncreasePriceLevel2;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.goldIncreaseLevel == 2) {
            currentprice = Constants.goldIncreasePriceLevel3;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.goldIncreaseLevel == 3) {
            currentprice = Constants.goldIncreasePriceLevel4;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.goldIncreaseLevel == 4) {
            currentprice = Constants.goldIncreasePriceLevel5;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.goldIncreaseLevel == 5) {
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.fullcircle);
        }
    }

    public void purchaseupgradebutton(View v)  {
        if (PlayerData.currentGold >= currentprice && PlayerData.goldIncreaseLevel < 5) {
            PlayerData.goldIncreaseLevel = PlayerData.goldIncreaseLevel + 1;
            PlayerData.currentGold = PlayerData.currentGold - currentprice;
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        else if (PlayerData.goldIncreaseLevel == 5) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        else {

        }
    }

    public void leavepurchasebutton(View v) {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
