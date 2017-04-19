package comp3710.aj.au2048game;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Main file for AU2048Game (Project 2 and Final(?))
 *
 * @author AJ McCarthy
 * @author Jay Whaley
 * @version 04-18-2017
 *
 * @startuml
 * class MainActivity extends AppCompatActivity
 *  'variables
 *  MainActivity : ~control : Controller
 *  MainActivity : -score : Integer
 *  MainActivity : -highScore : Integer
 *  MainActivity : -arr : int[][]
 *  MainActivity : -arrView : ImageView[]
 *  MainActivity : ~scoreText : TextView
 *  MainActivity : ~highScoreText : TextView
 *
 *  'methods
 *  MainActivity : #onCreate(Bundle) : void
 *  MainActivity : -setupArrView(ImageView[]) : void
 *  MainActivity : -setArrView(int[][], ImageView[]) : void
 *  MainActivity : +onSaveInstanceState(Bundle) : void
 *  MainActivity : +UpClick(View) : void
 *  MainActivity : +RightClick(View) : void
 *  MainActivity : +DownClick(View) : void
 *  MainActivity : +LeftClick(View) : void
 *  MainActivity : +ResetClick(View) : void
 * 'end MainActivity
 *
 * class MainActivityFragment extends Fragment
 * 'variables
 * 'methods
 * MainActivityFragment : +onCreateView(LayoutInflator, ViewGroup, Bundle) : View
 * MainActivityFragment : +onPause() : void
 * MainActivityFragment : +onDestroy() : void
 *
 * 'end MainActivityFragment
 *
 * class Controller
 * 'variables
 *  Controller : -arr : int[][]
 *  Controller : -score : int
 *  Controller : -rand : Random
 *  Controller : ~activity : Activity
 *
 * 'methods
 *  Controller : +Controller(Context) : Controller
 *  Controller : +getArr() : int[][]
 *  Controller : +getScore() : int
 *  Controller : +setArr(int[][]) : void
 *  Controller : +setScore(int) : void
 *  Controller : +shiftUp() : void
 *  Controller : +shiftLeft() : void
 *  Controller : +shiftDown() : void
 *  Controller : +shiftRight() : void
 *  Controller : +addNewNumber() : void
 *  Controller : +noMovesPossible() : boolean
 *  Controller : +gameOver() : void
 *  Controller : +checkWin() : boolean
 *  Controller : +win() : void
 *  Controller : +reset() : void
 *
 * 'end Controller
 *
 * Controller - MainActivity : drives >
 * MainActivity - MainActivityFragment : has >
 * @enduml
 * */

public class MainActivity extends AppCompatActivity {
    Controller control;
    private Integer score;
    private Integer highScore;
    private int[][] arr;
    private ImageView[] arrView = new ImageView[16];
    TextView scoreText;
    TextView highScoreText;
    private boolean past2048 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreText = (TextView)findViewById(R.id.scoreValue);
        highScoreText = (TextView)findViewById(R.id.highScoreValue);
        arr = new int[4][4];
        control = new Controller(this);


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
        past2048 = pref.getBoolean("past2048", true);
        highScore = pref.getInt("highScore", 0);
        highScoreText.setText(highScore.toString());
        edit.apply();
        control.setArr(arr);
        setArrView(arr, arrView);
        control.setScore(score);
    }

    private void setupArrView(ImageView[] arrView) {
        arrView[0] = (ImageView)findViewById(R.id.tile_0);
        arrView[1] = (ImageView)findViewById(R.id.tile_1);
        arrView[2] = (ImageView)findViewById(R.id.tile_2);
        arrView[3] = (ImageView)findViewById(R.id.tile_3);
        arrView[4] = (ImageView)findViewById(R.id.tile_4);
        arrView[5] = (ImageView)findViewById(R.id.tile_5);
        arrView[6] = (ImageView)findViewById(R.id.tile_6);
        arrView[7] = (ImageView)findViewById(R.id.tile_7);
        arrView[8] = (ImageView)findViewById(R.id.tile_8);
        arrView[9] = (ImageView)findViewById(R.id.tile_9);
        arrView[10] = (ImageView)findViewById(R.id.tile_10);
        arrView[11] = (ImageView)findViewById(R.id.tile_11);
        arrView[12] = (ImageView)findViewById(R.id.tile_12);
        arrView[13] = (ImageView)findViewById(R.id.tile_13);
        arrView[14] = (ImageView)findViewById(R.id.tile_14);
        arrView[15] = (ImageView)findViewById(R.id.tile_15);
    }

    private void setArrView(int[][] arr, ImageView[] arrView) {
        setupArrView(arrView);
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                switch(arr[i][j]) {
                    case 2:
                        arrView[4*i+j].setImageResource(R.drawable.tile2);
                        break;
                    case 4:
                        arrView[4*i+j].setImageResource(R.drawable.tile4);
                        break;
                    case 8:
                        arrView[4*i+j].setImageResource(R.drawable.tile8);
                        break;
                    case 16:
                        arrView[4*i+j].setImageResource(R.drawable.tile16);
                        break;
                    case 32:
                        arrView[4*i+j].setImageResource(R.drawable.tile32);
                        break;
                    case 64:
                        arrView[4*i+j].setImageResource(R.drawable.tile64);
                        break;
                    case 128:
                        arrView[4*i+j].setImageResource(R.drawable.tile128);
                        break;
                    case 256:
                        arrView[4*i+j].setImageResource(R.drawable.tile256);
                        break;
                    case 512:
                        arrView[4*i+j].setImageResource(R.drawable.tile512);
                        break;
                    case 1024:
                        arrView[4*i+j].setImageResource(R.drawable.tile1024);
                        break;
                    case 2048:
                        arrView[4*i+j].setImageResource(R.drawable.tile2048);
                        break;
                    case 4096:
                        arrView[4*i+j].setImageResource(R.drawable.tile4096);
                        break;
                    default:
                        arrView[4*i+j].setImageResource(R.drawable.rect);
                        break;
                }
            }
        }
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
        edit.putInt("highScore", highScore);
        edit.putBoolean("past2048", past2048);
        edit.apply();

        setArrView(arr, arrView);
    }

    public void UpClick(View v){
        if(v.getId() == R.id.upArrow) {
            control.shiftUp();
            arr = control.getArr();
            score = control.getScore();
            scoreText.setText(score.toString());
            if(score>highScore)
                highScore=score;
            highScoreText.setText(highScore.toString());
            //draw onto fragment with updated array
            if(control.checkWin() && past2048)
                 past2048 = control.win();
            if(control.noMovesPossible())
                control.gameOver();

        }

        setArrView(arr, arrView);
    }
    public void DownClick(View v){
        if(v.getId() == R.id.downArrow) {
            control.shiftDown();
            arr = control.getArr();
            score = control.getScore();
            scoreText.setText(score.toString());
            if(score>highScore)
                highScore=score;
            highScoreText.setText(highScore.toString());
            //draw onto fragment with updated array
            if(control.checkWin() && past2048)
                past2048 = control.win();
            if(control.noMovesPossible())
                control.gameOver();

        }

        setArrView(arr, arrView);
    }
    public void LeftClick(View v){
        if(v.getId() == R.id.leftArrow) {
            control.shiftLeft();
            arr = control.getArr();
            score = control.getScore();
            scoreText.setText(score.toString());
            if(score>highScore)
                highScore=score;
            highScoreText.setText(highScore.toString());
            //draw onto fragment with updated array
            if(control.checkWin() && past2048)
                past2048 = control.win();
            if(control.noMovesPossible())
                control.gameOver();

        }

        setArrView(arr, arrView);
    }
    public void RightClick(View v){
        if(v.getId() == R.id.rightArrow) {
            control.shiftRight();
            arr = control.getArr();
            score = control.getScore();
            scoreText.setText(score.toString());
            if(score>highScore)
                highScore=score;
            highScoreText.setText(highScore.toString());
            //draw onto fragment with updated array
            if(control.checkWin() && past2048)
               past2048 = control.win();
            if(control.noMovesPossible())
                control.gameOver();

        }

        setArrView(arr, arrView);
    }
    public void ResetClick(View v){
        if(v.getId() == R.id.restartButton) {
            control.reset();
            arr = control.getArr();
            score = 0;
            past2048 = true;
            scoreText.setText(score.toString());
            //draw onto fragment with updated array
        }

        setArrView(arr, arrView);
    }
}
