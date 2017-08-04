package jakemcdowell.blobsapplication.bugs;

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
            this.beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, Constants.MOVINGBUGSPEED, MILLISECONDS);
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

    private class Beeper implements Runnable {
        public void run() {
            if (getButton().getX() <= 30) {
                isAtLowEdgeX = true;
                isAtHighEdgeX = false;

                directionX = possibleDirections.get(1);
            } else if (getButton().getX() >= 600) {
                isAtLowEdgeX = false;
                isAtHighEdgeX = true;

                directionX = possibleDirections.get(2);
            }
            if (getButton().getY() <= 220) {
                isAtLowEdgeY = true;
                isAtHighEdgeY = false;

                directionY = possibleDirections.get(1);
            } else if (getButton().getY() >= 1420) {
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

            getButton().setX(getButton().getX() + directionX);
            getButton().setY(getButton().getY() + directionY);
            getHp().setX(getButton().getX() - 5);
            getHp().setY(getButton().getY() - 30);
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
