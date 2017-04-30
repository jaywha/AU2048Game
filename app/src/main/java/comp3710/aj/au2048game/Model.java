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

    public Model() {
        wantsToContinue = true;
    }

    public int[][] getArr() {
        return arr;
    }

    public void setArr(int[][] inArr) {
        arr = inArr;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int scoreIn) {
        score = scoreIn;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random randIn) {
        rand = randIn;
    }

    Activity getModelActivity() {
        return activity;
    }

    public void setActivity(Activity activityIn) {
        activity = activityIn;
    }

    public boolean doesWantToContinue() {
        return wantsToContinue;
    }

    public void setWantsToContinue(boolean boolin) {
        wantsToContinue = boolin;
    }
}
