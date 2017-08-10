package jakemcdowell.blobsapplication.bugs;

import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import jakemcdowell.blobsapplication.Constants;
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
        return -5;
    }

    @Override
    public int getHealthBarYOffset() {
        return -30;
    }

    private class Beeper implements Runnable {
        public void run() {
            if (getX() <= 30) {
                isAtLowEdgeX = true;
                isAtHighEdgeX = false;

                directionX = possibleDirections.get(1);
            } else if (getX() >= 600) {
                isAtLowEdgeX = false;
                isAtHighEdgeX = true;

                directionX = possibleDirections.get(2);
            }
            if (getY() <= 220) {
                isAtLowEdgeY = true;
                isAtHighEdgeY = false;

                directionY = possibleDirections.get(1);
            } else if (getY() >= 1420) {
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
