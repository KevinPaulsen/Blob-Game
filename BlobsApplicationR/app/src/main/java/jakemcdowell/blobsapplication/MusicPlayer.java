package jakemcdowell.blobsapplication;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.media.MediaPlayer.OnCompletionListener;

/**
 * Created by jakemcdowell on 8/3/17.
 */

public class MusicPlayer {
    public static MediaPlayer currentSong;
    public static boolean firstRun = true;

    public static void startSong(MediaPlayer songmain) {
        if (!MainActivity.isMuted) {
            if (firstRun) {
                firstRun = false;
            } else {
                resetMusic(currentSong);
            }
            songmain.setLooping(true);
            songmain.start();
            currentSong = songmain;
        } else {
            firstRun = true;
        }
    }

    public static void startSongOnce(MediaPlayer song) {
        if (!MainActivity.isMuted) {
            resetMusic(currentSong);
            song.start();
        }
    }

    public static void resetMusic(MediaPlayer songplaying) {
        songplaying.reset();
    }

    public static void stopCurrentMusic() { currentSong.pause(); }

    public static void continueCurrentMusic() { currentSong.start(); firstRun = true;}

    public static void playEndingMusic(final MediaPlayer endingsong) {
        if (!MainActivity.isMuted) {
            currentSong.setLooping(false);
            currentSong.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    resetMusic(currentSong);
                    currentSong = endingsong;
                    endingsong.start();
                }
            });
        }
    }
}
