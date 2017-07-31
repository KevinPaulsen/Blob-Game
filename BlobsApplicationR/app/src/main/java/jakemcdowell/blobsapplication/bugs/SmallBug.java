package jakemcdowell.blobsapplication.bugs;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by jakemcdowell on 7/26/17.
 */

public class SmallBug extends Bug {

    public SmallBug(View view, int health, ProgressBar hp, int totalKnockOuts) {
        super(view, health, hp, totalKnockOuts);
    }

    public void changeButtonsize() {

    }
}
