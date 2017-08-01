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
        golddisplay.setText("" + PlayerData.currentgold);
        if (PlayerData.goldincreaselevel == 0) {
            currentprice = Constants.goldincreasepricelevel1;
        }
        if (PlayerData.goldincreaselevel == 1) {
            currentprice = Constants.goldincreasepricelevel2;
        }
        if (PlayerData.goldincreaselevel == 2) {
            currentprice = Constants.goldincreasepricelevel3;
        }
        if (PlayerData.goldincreaselevel == 3) {
            currentprice = Constants.goldincreasepricelevel4;
        }
        if (PlayerData.goldincreaselevel == 4) {
            currentprice = Constants.goldincreasepricelevel5;
        }
        if (PlayerData.goldincreaselevel == 5) {

        }
    }

    public void leavepurchasebutton(View v) {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
