package comp3710.aj.au2048game;

import android.app.Activity;

import java.util.Random;

/**
 * Model class which represents the data of the app.
 */

class Model {
    private int[][] arr;
    private int score;
    private Random rand;
    private Activity activity;
    private boolean wantsToContinue;
    private boolean sound_on = true;

    Model() {
        wantsToContinue = true;
    }

    int[][] getArr() {
        return arr;
    }

    void setArr(int[][] inArr) {
        arr = inArr;
    }

    int getScore() {
        return score;
    }

    void setScore(int scoreIn) {
        score = scoreIn;
    }

    Random getRand() {
        return rand;
    }

    void setRand(Random randIn) {
        rand = randIn;
    }

    Activity getModelActivity() {
        return activity;
    }

    void setActivity(Activity activityIn) {
        activity = activityIn;
    }

    boolean doesWantToContinue() {
        return wantsToContinue;
    }

    void setWantsToContinue(boolean boolin) {
        wantsToContinue = boolin;
    }

    boolean getSound() {
        return sound_on;
    }

    void setSound(boolean SoundIn) {
        sound_on = SoundIn;
    }
}
