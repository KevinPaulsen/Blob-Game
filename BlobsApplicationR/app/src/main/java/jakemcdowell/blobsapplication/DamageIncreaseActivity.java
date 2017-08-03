package jakemcdowell.blobsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DamageIncreaseActivity extends AppCompatActivity {
    public int currentprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_increase);
        TextView golddisplay = (TextView) findViewById(R.id.textView12);
        golddisplay.setText("" + PlayerData.currentGold);
        Button e = (Button)findViewById(R.id.button14);
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
            e.setVisibility(View.GONE);
        }
    }

    public void purchaseupgradebutton(View v)  {
        if (PlayerData.currentGold >= currentprice && PlayerData.damageIncreaseLevel < 5) {
                PlayerData.damageIncreaseLevel = PlayerData.damageIncreaseLevel + 1;
                PlayerData.currentGold = PlayerData.currentGold - currentprice;
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        else if (PlayerData.damageIncreaseLevel == 5) {
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
