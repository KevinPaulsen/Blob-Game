package jakemcdowell.blobsapplication.bugs;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by kevin on 7/28/17.
 */

public class MovingBug extends Bug {

    public MovingBug(View view, int health, ProgressBar hp, int totalKnockOus) {
        super(view, health, hp, totalKnockOus);
    }
}
