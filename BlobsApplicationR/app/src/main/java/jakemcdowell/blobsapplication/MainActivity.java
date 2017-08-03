package jakemcdowell.blobsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import jakemcdowell.blobsapplication.GameActivity;
import jakemcdowell.blobsapplication.HelpActivity;
import jakemcdowell.blobsapplication.R;
import jakemcdowell.blobsapplication.ShopActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView a = (TextView) findViewById(R.id.textView20);
        a.setText("Version: " + Constants.version);
    }

    public void playgamebuttonclick(View v) {
        Intent intent = new Intent(this, GameActivity.class);
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
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
