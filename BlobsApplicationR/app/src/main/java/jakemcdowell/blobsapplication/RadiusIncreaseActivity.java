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
        }
        if (PlayerData.radiusincreaselevel == 1) {
            currentprice = Constants.radiusincreasepricelevel2;
        }
        if (PlayerData.radiusincreaselevel == 2) {
            currentprice = Constants.radiusincreasepricelevel3;
        }
        if (PlayerData.radiusincreaselevel == 3) {
            currentprice = Constants.radiusincreasepricelevel4;
        }
        if (PlayerData.radiusincreaselevel == 4) {
            currentprice = Constants.radiusincreasepricelevel5;
        }
        if (PlayerData.radiusincreaselevel == 5) {

        }
    }

    public void leavepurchasebutton(View v) {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
