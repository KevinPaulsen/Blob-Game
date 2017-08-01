package jakemcdowell.blobsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RadiusIncreaseActivity extends AppCompatActivity {
    public int currentprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radius_increase);
        TextView golddisplay = (TextView) findViewById(R.id.textView12);
        golddisplay.setText("" + PlayerData.currentgold);
        if (PlayerData.radiusincreaselevel == 0) {
            currentprice = Constants.radiusincreasepricelevel1;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.radiusincreaselevel == 1) {
            currentprice = Constants.radiusincreasepricelevel2;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.radiusincreaselevel == 2) {
            currentprice = Constants.radiusincreasepricelevel3;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.radiusincreaselevel == 3) {
            currentprice = Constants.radiusincreasepricelevel4;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.emptycircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.radiusincreaselevel == 4) {
            currentprice = Constants.radiusincreasepricelevel5;
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.emptycircle);
        }
        if (PlayerData.radiusincreaselevel == 5) {
            findViewById(R.id.imageView19).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView15).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView17).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView12).setBackgroundResource(R.drawable.fullcircle);
            findViewById(R.id.imageView14).setBackgroundResource(R.drawable.fullcircle);
        }
    }

    public void purchaseupgradebutton(View v)  {
        if (PlayerData.currentgold >= currentprice && PlayerData.radiusincreaselevel < 5) {
            PlayerData.radiusincreaselevel = PlayerData.radiusincreaselevel + 1;
            PlayerData.currentgold = PlayerData.currentgold - currentprice;
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        else if (PlayerData.radiusincreaselevel == 5) {
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
