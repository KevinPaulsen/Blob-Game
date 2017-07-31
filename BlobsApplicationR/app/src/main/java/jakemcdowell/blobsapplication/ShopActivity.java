package jakemcdowell.blobsapplication;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ShopActivity extends AppCompatActivity implements Runnable {

    AnimationDrawable dooranimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Button leaveshopbutton = (Button) findViewById(R.id.LeaveShopButton);
        leaveshopbutton.setBackgroundResource(R.drawable.dooranimation);
        dooranimation = (AnimationDrawable) leaveshopbutton.getBackground();
    }

    public void leaveshopbutton(View v) {
        dooranimation.start();
        Thread thread = new Thread(this);
        thread.start();
    }
    public void run() {
        SystemClock.sleep(220);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
