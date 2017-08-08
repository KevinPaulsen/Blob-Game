package jakemcdowell.blobsapplication;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

public class RadiusIncreaseActivity extends AppCompatActivity {
    public int currentprice;
    boolean isClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radius_increase);
        TextView golddisplay = (TextView) findViewById(R.id.textView12);
        golddisplay.setText("" + PlayerData.currentGold);
        ImageView e = (ImageView)findViewById(R.id.soldout);
        if (PlayerData.radiusIncreaseLevel == 0) {
            currentprice = Constants.radiusIncreasePriceLevel1;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.radiusIncreaseLevel == 1) {
            currentprice = Constants.radiusIncreasePriceLevel2;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.radiusIncreaseLevel == 2) {
            currentprice = Constants.radiusIncreasePriceLevel3;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.radiusIncreaseLevel == 3) {
            currentprice = Constants.radiusIncreasePriceLevel4;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.radiusIncreaseLevel == 4) {
            currentprice = Constants.radiusIncreasePriceLevel5;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.radiusIncreaseLevel == 5) {
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.fullcircle);
            ViewCompat.setTranslationZ(e, 10);
        }
    }

    public void purchaseupgradebutton(View v)  {
        if (PlayerData.currentGold >= currentprice && PlayerData.radiusIncreaseLevel < 5 && isClicked == false) {
            PlayerData.radiusIncreaseLevel = PlayerData.radiusIncreaseLevel + 1;
            PlayerData.currentGold = PlayerData.currentGold - currentprice;
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            isClicked = true;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void leavepurchasebutton(View v) {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
