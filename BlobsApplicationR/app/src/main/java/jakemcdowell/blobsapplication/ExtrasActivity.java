package jakemcdowell.blobsapplication;

import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ExtrasActivity extends AppCompatActivity implements View.OnClickListener {

    Button returntomenu;
    AnimationDrawable normalBugDancing;
    AnimationDrawable fireBugDancing;
    AnimationDrawable movingBugDancing;
    AnimationDrawable teleportingBugDancing;
    AnimationDrawable kazoo1;
    AnimationDrawable kazoo2;
    AnimationDrawable kazoo3;
    AnimationDrawable kazoo4;
    View normalbugimage;
    View firebugimage;
    View movingbugimage;
    View teleportingbugimage;
    View kazoo1image;
    View kazoo2image;
    View kazoo3image;
    View kazoo4image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);
        returntomenu = (Button) this.findViewById(R.id.button22);
        returntomenu.setOnClickListener(this);
        normalbugimage = findViewById(R.id.imageView41);
        normalbugimage.setBackgroundResource(R.drawable.normalbugdancinganim);
        normalBugDancing = (AnimationDrawable) normalbugimage.getBackground();
        firebugimage = findViewById(R.id.imageView42);
        firebugimage.setBackgroundResource(R.drawable.firebugdancinganim);
        fireBugDancing = (AnimationDrawable) firebugimage.getBackground();
        movingbugimage = findViewById(R.id.imageView43);
        movingbugimage.setBackgroundResource(R.drawable.movingbugdancinganim);
        movingBugDancing = (AnimationDrawable) movingbugimage.getBackground();
        teleportingbugimage = findViewById(R.id.imageView44);
        teleportingbugimage.setBackgroundResource(R.drawable.teleportingbugdancinganim);
        teleportingBugDancing = (AnimationDrawable) teleportingbugimage.getBackground();
        kazoo1image = findViewById(R.id.imageView49);
        kazoo1image.setBackgroundResource(R.drawable.partykazooanim);
        kazoo1 = (AnimationDrawable) kazoo1image.getBackground();
        kazoo2image = findViewById(R.id.imageView50);
        kazoo2image.setBackgroundResource(R.drawable.partykazooanim);
        kazoo2 = (AnimationDrawable) kazoo2image.getBackground();
        kazoo3image = findViewById(R.id.imageView51);
        kazoo3image.setBackgroundResource(R.drawable.partykazooanim);
        kazoo3 = (AnimationDrawable) kazoo3image.getBackground();
        kazoo4image = findViewById(R.id.imageView52);
        kazoo4image.setBackgroundResource(R.drawable.partykazooanim);
        kazoo4 = (AnimationDrawable) kazoo4image.getBackground();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        MediaPlayer creditstheme = MediaPlayer.create(this,R.raw.creditstheme);
        MusicPlayer.startSong(creditstheme);
        normalBugDancing.start();
        fireBugDancing.start();
        movingBugDancing.start();
        teleportingBugDancing.start();
        kazoo1.start();
        kazoo2.start();
        kazoo3.start();
        kazoo4.start();
    }

    public void onClick(View v) {
        if (v.equals(returntomenu)) {
            returnbutton();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void returnbutton() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
