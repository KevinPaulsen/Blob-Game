package jakemcdowell.blobsapplication;

import android.media.MediaPlayer;
import android.provider.MediaStore;

/**
 * Created by jakemcdowell on 8/3/17.
 */

public class MusicPlayer {
    public static MediaPlayer currentSong;
    public static boolean firstRun = true;

    public static void startSong(MediaPlayer songmain) {
        if (firstRun) {
            firstRun = false;
        } else {
            resetMusic(currentSong);
        }
        songmain.setLooping(true);
        songmain.start();
        currentSong = songmain;
    }

    public static void startSongOnce(MediaPlayer song) {
        resetMusic(currentSong);
        song.start();
    }

    public static void resetMusic(MediaPlayer songplaying) {
        songplaying.reset();
    }

    public static void playEndingMusic(MediaPlayer endingsong) {
        resetMusic(currentSong);
        endingsong.start();
    }
}
