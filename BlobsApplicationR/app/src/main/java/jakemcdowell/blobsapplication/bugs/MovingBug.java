package jakemcdowell.blobsapplication.bugs;

import android.content.res.Resources;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import jakemcdowell.blobsapplication.Constants;
import jakemcdowell.blobsapplication.Game;
import jakemcdowell.blobsapplication.R;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by kevin on 7/28/17.
 */

public class MovingBug extends Bug {

    private ScheduledFuture beeperHandle = null;
    private boolean isAtLowEdgeX = false;
    private boolean isAtHighEdgeX = false;
    private boolean isAtLowEdgeY = false;
    private boolean isAtHighEdgeY = false;
    private int steps = 0;
    private int directionX = 20;
    private int directionY = 20;

    // 0th element = 0
    // 1st element = 20
    // 2nd element = -20
    private List<Integer> possibleDirections = new ArrayList<>();

    public MovingBug(View view, int health, ProgressBar hp, int totalKnockOus) {
        super(view, health, hp, totalKnockOus);
        view.setBackgroundResource(R.drawable.movingbuganimation);
        possibleDirections.add(0);
        possibleDirections.add(20);
        possibleDirections.add(-20);
    }

    @Override
    public void move() {
        if (beeperHandle == null) {
            super.move();
            final Runnable beeper = new Beeper();
            this.beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, Constants.MOVING_BUG_SPEED, MILLISECONDS);
        }
    }

    @Override
    public void moveOffScreen() {
        super.moveOffScreen();
        if (beeperHandle != null) {
            beeperHandle.cancel(true);
            beeperHandle = null;
        }
    }

    @Override
    public int getHealthBarXOffset() {
        int barOffsetX = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.004629);
        return -barOffsetX;
    }

    @Override
    public int getHealthBarYOffset() {
        int barOffsetY = (int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.0156);
        return -barOffsetY;
    }

    private class Beeper implements Runnable {

        float smallX = (float) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.0277);
        float largeX = (float) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.5555);
        float smallY = (float) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.11458);
        float largeY = (float) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.73958);

        public void run() {
            if (getX() <= smallX) {
                isAtLowEdgeX = true;
                isAtHighEdgeX = false;

                directionX = possibleDirections.get(1);
            } else if (getX() >= largeX) {
                isAtLowEdgeX = false;
                isAtHighEdgeX = true;

                directionX = possibleDirections.get(2);
            }
            if (getY() <= smallY) {
                isAtLowEdgeY = true;
                isAtHighEdgeY = false;

                directionY = possibleDirections.get(1);
            } else if (getY() >= largeY) {
                isAtLowEdgeY = false;
                isAtHighEdgeY = true;

                directionY = possibleDirections.get(2);
            }

            if (steps % 20 == 0 && !isAtEdge()) {
                changeDirectionRandomX();
                changeDirectionRandomY();
                if (directionY == 0 && directionX == 0) {
                    directionY = possibleDirections.get((int) ((Math.random() * 2) + 1));
                }

                setIsAtEdge(false);
            }

            setX(getX() + directionX);
            setY(getY() + directionY);
            steps++;
        }
    }

    public void pauseKnockout() {
        setBackgroundResource(R.drawable.deadbug);
        clickable = false;
        beeperHandle.cancel(true);

        if (getKnockOuts() != Constants.KOS_PER_DEATH) {
            button.postDelayed(new Runnable() {
                @Override
                public void run() {
                    beeperHandle = null;
                    move();
                    setBackgroundResource(R.drawable.movingbuganimation);
                    startAnimation();
                    clickable = true;
                }
            }, 500);
        }
    }

    public void pauseDeath(final Game g) {
        button.setBackgroundResource(R.drawable.deadbug);
        clickable = false;

        button.postDelayed(new Runnable() {
            @Override
            public void run() {
                setBackgroundResource(R.drawable.movingbuganimation);
                startAnimation();
                clickable = true;
                if (g.isAllDead()) {
                    getGameActivity().allBugsDead();
                    g.resetBugsToInitialState();
                }
                moveOffScreen();
            }
        }, 500);

    }

    public void changeDirectionRandomX() {
        directionX = possibleDirections.get((int) (Math.random() * 3));
    }

    public void changeDirectionRandomY() {
        directionY = possibleDirections.get((int) (Math.random() * 3));
    }

    public boolean isAtEdge() {
        return isAtLowEdgeX && isAtLowEdgeY && isAtHighEdgeX && isAtHighEdgeY;
    }

    public void setIsAtEdge(boolean temp) {
        isAtHighEdgeX = temp;
        isAtLowEdgeX = temp;
        isAtHighEdgeY = temp;
        isAtLowEdgeY = temp;
    }
}
