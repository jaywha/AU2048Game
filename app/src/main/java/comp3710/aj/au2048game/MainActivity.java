package comp3710.aj.au2048game;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    Controller control;
    private Integer score;
    private int[][] arr;
    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreText = (TextView)findViewById(R.id.scoreValue);
        arr = new int[4][4];
        control = new Controller();
        //arr = control.getArr();

        List<Integer> arrBoard = new ArrayList<>();
        SharedPreferences pref  = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = pref.edit();
        String string = pref.getString("board", null);
        if(string != null) {
            try {
                JSONArray array = new JSONArray(pref.getString("board", null));
                for (int i = 0; i < array.length(); i++) {
                    if (array.length() == 0)
                        break;
                    String current = array.getString(i);
                    arrBoard.add(i, Integer.parseInt(current));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

                for (int i = 0; i < 16; i++)
                    arr[i / 4][i % 4] = arrBoard.get(i);
        }
        else
            arr = new int[4][4];
        score = pref.getInt("score",0);
        scoreText.setText(score.toString());
        control.setArr(arr);
        control.setScore(score);
    }
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        SharedPreferences pref  = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("score", score);
        List<Integer> arrBoard = new ArrayList<>();
        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                arrBoard.add(arr[i][j]);
        JSONArray board = new JSONArray(arrBoard);
        edit.putString("board", board.toString());
        edit.apply();

    }

    //TODO: Make fragment for tiles : BoardView similar to CannonView?
    //TODO: Add High-score to layout : Separate Class in the model?
    //TODO: Connect controller logic to layout : Controller --> View
    //TODO: OPTIONAL :: Make UML Diagrams
    public void UpClick(View v){
        if(v.getId() == R.id.upArrow) {
            control.shiftUp();
            arr = control.getArr();
            score = control.getScore();
            scoreText.setText(score.toString());
            //draw onto fragment with updated array
        }
    }
    public void DownClick(View v){
        if(v.getId() == R.id.downArrow) {
            control.shiftDown();
            arr = control.getArr();
            score = control.getScore();
            scoreText.setText(score.toString());
            //draw onto fragment with updated array
        }
    }
    public void LeftClick(View v){
        if(v.getId() == R.id.leftArrow) {
            control.shiftLeft();
            arr = control.getArr();
            score = control.getScore();
            scoreText.setText(score.toString());
            //draw onto fragment with updated array
        }
    }
    public void RightClick(View v){
        if(v.getId() == R.id.rightArrow) {
            control.shiftRight();
            arr = control.getArr();
            score = control.getScore();
            scoreText.setText(score.toString());
            //draw onto fragment with updated array
        }
    }
    public void ResetClick(View v){
        if(v.getId() == R.id.restartButton) {
            control.reset();
            arr = control.getArr();
            score = 0;
            scoreText.setText(score.toString());
            //draw onto fragment with updated array
        }
    }
}
