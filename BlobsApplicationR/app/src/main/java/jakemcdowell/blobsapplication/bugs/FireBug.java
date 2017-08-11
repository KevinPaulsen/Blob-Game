package jakemcdowell.blobsapplication.bugs;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import jakemcdowell.blobsapplication.Constants;
import jakemcdowell.blobsapplication.Game;
import jakemcdowell.blobsapplication.GameActivity;
import jakemcdowell.blobsapplication.GameOverActivity;
import jakemcdowell.blobsapplication.PlayerData;
import jakemcdowell.blobsapplication.R;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by kevin on 7/28/17.
 */

public class FireBug extends Bug {

    private ScheduledFuture beeperHandle = null;
    public boolean bugAlreadyExploded = false;
    private boolean isAtLowEdgeX = false;
    private boolean isAtHighEdgeX = false;
    private boolean isAtLowEdgeY = false;
    private boolean isAtHighEdgeY = false;
    private boolean isClickable = false;
    private int steps = 0;
    private int directionX = 20;
    private int directionY = 20;
    private int randomDelay = (int) ((Math.random() * 50) + 20);
    private boolean canBeClicked = true;

    // 0th element = 0
    // 1st element = 20
    // 2nd element = -20
    private List<Integer> possibleDirections = new ArrayList<>();

    public FireBug(View view, int health, ProgressBar hp, int totalKnockOus) {
        super(view, health, hp, totalKnockOus);
        view.setBackgroundResource(R.drawable.armedfirebuganimation);
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

            if (steps % randomDelay == 0) {
                randomDelay = (int) ((Math.random() * 50) + 20);
                isClickable = !isClickable;
                getGameActivity().runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                AnimationDrawable oldBackground = (AnimationDrawable) getBackground();
                                oldBackground.stop();
                                if (!isClickable) {
                                    setBackgroundResource(R.drawable.armedfirebuganimation);
                                } else {
                                    setBackgroundResource(R.drawable.unarmedfirebuganimation);
                                }
                                ((AnimationDrawable) getBackground()).start();
                            } catch (Exception ex) {
                                System.out.println("ERROR: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        }
                    }
                );
            }
        }
    }

    @Override
    public void damageBug(Game game) {
        if (!isClickable && !bugAlreadyExploded) {
            bugAlreadyExploded = true;
            GameOverActivity.fireBugDeath = true;
            getGameActivity().gameOver();
        } else {
            super.damageBug(game);
            if (canBeClicked) {
                if (!isClickable && !bugAlreadyExploded) {
                    bugAlreadyExploded = true;
                    getGameActivity().gameOver();
                } else {
                    super.damageBug(game);
                }
            }
        }
    }

    public void pauseKnockout() {
        setBackgroundResource(R.drawable.deadbug);
        canBeClicked = false;

        if (getKnockOuts() != Constants.KOS_PER_DEATH) {
            button.postDelayed(new Runnable() {
                @Override
                public void run() {
                    move();
                    if (isClickable) {
                        setBackgroundResource(R.drawable.unarmedfirebuganimation);
                    } else {
                        setBackgroundResource(R.drawable.armedfirebuganimation);
                    }
                    startAnimation();
                    canBeClicked = true;
                }
            }, 500);
        }
    }

    public void pauseDeath(final Game g) {
        button.setBackgroundResource(R.drawable.deadbug);
        canBeClicked = false;

        button.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isClickable) {
                    setBackgroundResource(R.drawable.unarmedfirebuganimation);
                } else {
                    setBackgroundResource(R.drawable.armedfirebuganimation);
                }                startAnimation();
                canBeClicked = true;
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
