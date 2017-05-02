package comp3710.aj.au2048game;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Main file for AU2048Game Final
 *
 * @author AJ McCarthy
 * @author Jay Whaley
 * @version 04-18-2017
 *
 * @startuml
 * title AU2048 Class Diagram
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
 *  Controller : +data : Model
 *
 * 'methods
 *  Controller : +Controller(Context) : Controller
 *  Controller : +shiftUp() : void
 *  Controller : +shiftLeft() : void
 *  Controller : +shiftDown() : void
 *  Controller : +shiftRight() : void
 *  Controller : -addNewNumber() : void
 *  Controller : +noMovesPossible() : boolean
 *  Controller : +gameOver() : void
 *  Controller : +checkWin() : boolean
 *  Controller : +win() : void
 *  Controller : +reset() : void
 *
 * 'end Controller
 *
 * class Model
 * 'variables
 *  Model : -arr : int[][]
 *  Model : -score : int
 *  Model : -rand : Random
 *  Model : -activity : Activity
 *  Model : -wantsToContinue : boolean
 *  Model : -sound_on : boolean
 *
 * 'methods
 *  Model : ~Model() : Model
 *  Model : ~getArr() : int[][]
 *  Model : ~getScore() : int
 *  Model : ~setArr(int[][]) : void
 *  Model : ~setScore(int) : void
 *  Model : ~getRand() : Random
 *  Model : ~setRand(Random) : void
 *  Model : ~getModelActivity() : Activity
 *  Model : ~setActivity(Activity): void
 *  Model : ~doesWantToContinue() : boolean
 *  Model : ~setWantsToContinue(boolean) : void
 * 'end Model
 *
 * class SwipeListener
 * 'variables
 *
 * 'methods
 * SwipeListener : +onFling(MotionEvent, MotionEvent, float, float) : boolean
 * SwipeListener : +onSwipe(Direction) : boolean
 * SwipeListener : +getDirection(float, float, float, float) : Direction
 * SwipeListener : +getAngle(float, float, float, float) : double
 * 'end SwipeListener
 *
 * enum Direction
 * Direction : up
 * Direction : down
 * Direction : left
 * Direction : right
 * 'end Direction
 *
 * Controller - MainActivity : drives >
 * MainActivity - MainActivityFragment : has >
 * Model - Controller : < Uses
 * MainActivity - SwipeListener : > Uses
 * Direction - SwipeListener : contains <
 * @enduml
 *
 * @startuml
 * title AU2048 Swipe Sequence Diagram
 *
 * actor User
 * boundary SwipeListener as SL
 * entity MainActivity as MA
 * control Controller as C
 * entity MainActivityFragment as MAF
 * participant BoardView as BV
 * database Model as M
 *
 * create MA
 * User -> MA : Starts App
 * create C
 * activate MA
 * MA -> C : onCreate()
 * activate C
 * create MAF
 * MA -> MAF : onCreate()
 * activate MAF
 * create BV
 * MAF -> BV : onCreateView()
 * activate BV
 *
 * loop watntstoContinue == true
 * create SL
 * User -> SL : Swipe
 * SL -> MA : GestureDetector.Direction
 * MA -> C : onSwipe(Direction)
 * C -> M : setArr()
 * C -> M : setScore()
 * M --> C : doesWantToContinue()
 * MA -> MAF : setupArrView()
 * MA -> MAF : setArrView()
 *
 * alt noMovesPossible && past2048
 * C --> MA : checkWin()
 * C --> MA : win()
 * alt dialogChoice == reset
 * C -> MA : reset()
 * else dialogChoice == continue
 * note left
 *  reset like reset button
 *  or simply continue loop
 * end note
 * end alt
 * else noMovesPossible && !past2048
 * C --> MA : noMovesPossible()
 * C --> MA : gameover()
 * User -> MA : Presses Reset
 * MA -> C : Action.DOWN
 * C --> MA : reset()
 * end alt
 * deactivate BV
 * deactivate MAF
 * deactivate C
 * end play
 * BV <- MAF : onDestroy()
 * destroy BV
 * MA -> MAF : onDestroy()
 * destroy MAF
 * deactivate MA
 * destroy MA
 * @enduml
 * 
 * @startuml
 * title AU2048 Touch-Input Sequence Diagram
 *
 * actor User
 * entity MainActivity as MA
 * control Controller as C
 * entity MainActivityFragment as MAF
 * participant BoardView as BV
 * database Model as M
 *
 * create MA
 * User -> MA : Starts App
 * create C
 * activate MA
 * MA -> C : onCreate()
 * activate C
 * create MAF
 * MA -> MAF : onCreate()
 * activate MAF
 * create BV
 * MAF -> BV : onCreateView()
 * activate BV
 *
 * loop watntstoContinue == true
 * alt button_direction == up
 * MA -> MA : UpClick(View)
 * else button_direction == left
 * MA -> MA : LeftClick(View)
 * else button_direction == down
 * MA -> MA : DownClick(View)
 * else button_direction == right
 * MA -> MA : RightClick(View)
 * end alt
 * alt button_direction == up
 * C -> C : shiftUp()
 * else button_direction == left
 * C -> C : shiftLeft()
 * else button_direction == down
 * C -> C : shiftDown()
 * else button_direction == right
 * C -> C : shiftRight()
 * end alt
 * C -> M : setArr()
 * C -> M : setScore()
 * M --> C : doesWantToContinue()
 * MA -> MAF : setupArrView()
 * MA -> MAF : setArrView()
 * MAF -> BV : onCreateView()
 * BV --> MAF : BoardView
 *
 * alt noMovesPossible && past2048
 * C --> MA : checkWin()
 * C --> MA : win()
 * alt dialogChoice == reset
 * C -> MA : reset()
 * else dialogChoice == continue
 * note left
 *  reset like reset button
 *  or simply continue loop
 * end note
 * end alt
 * else noMovesPossible && !past2048
 * C --> MA : noMovesPossible()
 * C --> MA : gameover()
 * User -> MA : Presses Reset
 * MA -> C : Action.DOWN
 * C --> MA : reset()
 * end alt
 * deactivate BV
 * deactivate MAF
 * deactivate C
 * end play
 * BV <- MAF : onDestroy()
 * destroy BV
 * MA -> MAF : onDestroy()
 * destroy MAF
 * deactivate MA
 * destroy MA
 * @enduml
 *
 * */



public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    Controller control;
    private Integer score;
    private Integer highScore;
    private int[][] arr;
    private ImageView[] arrView = new ImageView[16];
    TextView scoreText;
    TextView highScoreText;
    private boolean past2048 = true;
    private Animation shift_anime;
    private MediaPlayer move_sound;
    private boolean demo;
    private Context context;
    private GestureDetector detector;
    private ImageView sound_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreText = (TextView)findViewById(R.id.scoreValue);
        highScoreText = (TextView)findViewById(R.id.highScoreValue);
        arr = new int[4][4];
        demo =false;
        context =this;
        control = new Controller(this);
        detector = new GestureDetector(this,new SwipeListener(){

            @Override
            public boolean onSwipe(Direction direction){
                if(direction == Direction.up)
                    UpClick(findViewById(R.id.upArrow));
                else if(direction == Direction.down)
                    DownClick(findViewById(R.id.downArrow));
                else if(direction == Direction.left)
                    LeftClick(findViewById(R.id.leftArrow));
                else if(direction == Direction.right)
                    RightClick(findViewById(R.id.rightArrow));
                return true;

            }
        });
        View view = findViewById(R.id.fragment);
        view.setOnTouchListener(this);
        view = findViewById(android.R.id.content);
        view.setOnTouchListener(this);

        sound_icon = (ImageView) findViewById(R.id.sound_button);

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
        scoreText.setText(String.format(score.toString(), "#########"));
        past2048 = pref.getBoolean("past2048", true);
        highScore = pref.getInt("highScore", 0);
        highScoreText.setText(String.format(highScore.toString(), "#########"));
        edit.apply();
        control.data.setArr(arr);
        move_sound =  MediaPlayer.create(this, R.raw.pop);
        setArrView(arr, arrView, false);
        control.data.setScore(score);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        detector.onTouchEvent(event);
        return true;
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

    private void setArrView(int[][] arr, ImageView[] arrView, boolean applyAnime) {
        // Apply animation "fixes"
        if(shift_anime != null && shift_anime.isInitialized()) {
            Interpolator i = new AccelerateInterpolator();
            shift_anime.setInterpolator(i);
        }
        setupArrView(arrView);
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                switch(arr[i][j]) {
                    case 2:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
                        arrView[4*i+j].setImageResource(R.drawable.tile2);
                        break;
                    case 4:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
                        arrView[4*i+j].setImageResource(R.drawable.tile4);
                        break;
                    case 8:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
                        arrView[4*i+j].setImageResource(R.drawable.tile8);
                        break;
                    case 16:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
                        arrView[4*i+j].setImageResource(R.drawable.tile16);
                        break;
                    case 32:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
                        arrView[4*i+j].setImageResource(R.drawable.tile32);
                        break;
                    case 64:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
                        arrView[4*i+j].setImageResource(R.drawable.tile64);
                        break;
                    case 128:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
                        arrView[4*i+j].setImageResource(R.drawable.tile128);
                        break;
                    case 256:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
                        arrView[4*i+j].setImageResource(R.drawable.tile256);
                        break;
                    case 512:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
                        arrView[4*i+j].setImageResource(R.drawable.tile512);
                        break;
                    case 1024:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
                        arrView[4*i+j].setImageResource(R.drawable.tile1024);
                        break;
                    case 2048:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
                        arrView[4*i+j].setImageResource(R.drawable.tile2048);
                        break;
                    case 4096:
                        if(applyAnime) arrView[4*i+j].startAnimation(shift_anime);
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
        if(!demo) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("score", score);
            List<Integer> arrBoard = new ArrayList<>();
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++)
                    arrBoard.add(arr[i][j]);
            JSONArray board = new JSONArray(arrBoard);
            edit.putString("board", board.toString());
            edit.putInt("highScore", highScore);
            edit.putBoolean("past2048", past2048);
            edit.apply();

            setArrView(arr, arrView, false);
        }
    }

    public void UpClick(View v){
        if(v.getId() == R.id.upArrow ) {
            control.shiftUp();
            arr = control.data.getArr();
            score = control.data.getScore();
            if(!demo)
                scoreText.setText(String.format(score.toString(), "#########"));
            if(score>highScore)
                highScore=score;
            if(!demo)
                highScoreText.setText(String.format(highScore.toString(), "#########"));
            //draw onto fragment with updated array
            if(control.checkWin() && past2048) {
                if(demo)
                    demo=false;
                else
                    past2048 = control.win();
            }
            if(control.noMovesPossible()) {
                if(demo)
                    demo=false;
                else
                    control.gameOver();
                    setArrView(arr, arrView, false);
            }

        }

        if(!demo) {
            shift_anime = AnimationUtils.loadAnimation(this, R.anim.shift_up);
            setArrView(arr, arrView, true);
            move_sound.start();
        }
    }
    public void DownClick(View v){
        if(v.getId() == R.id.downArrow ) {
            control.shiftDown();
            arr = control.data.getArr();
            score = control.data.getScore();
            if(!demo)
                scoreText.setText(String.format(score.toString(), "#########"));
            if(score>highScore)
                highScore=score;
            if(!demo)
                highScoreText.setText(String.format(highScore.toString(), "#########"));
            //draw onto fragment with updated array
            if(control.checkWin() && past2048) {
                if(demo)
                    demo=false;
                else
                    past2048 = control.win();
            }
            if(control.noMovesPossible()) {
                if(demo)
                    demo=false;
                else
                    control.gameOver();
                    setArrView(arr, arrView, false);
            }
        }

        if(!demo) {
            shift_anime = AnimationUtils.loadAnimation(this, R.anim.shift_down);
            setArrView(arr, arrView, true);
            move_sound.start();
        }
    }
    public void LeftClick(View v){
        if(v.getId() == R.id.leftArrow ) {
            control.shiftLeft();
            arr = control.data.getArr();
            score = control.data.getScore();
            if(!demo)
                scoreText.setText(String.format(score.toString(), "#########"));
            if(score>highScore)
                highScore=score;
            if(!demo)
                highScoreText.setText(String.format(highScore.toString(), "#########"));
            //draw onto fragment with updated array
            if(control.checkWin() && past2048) {
                if(demo)
                    demo=false;
                else
                    past2048 = control.win();
            }
            if(control.noMovesPossible()) {
                if(demo)
                    demo=false;
                else
                    control.gameOver();
                    setArrView(arr, arrView, false);
            }
        }

        if(!demo) {
            shift_anime = AnimationUtils.loadAnimation(this, R.anim.shift_left);
            setArrView(arr, arrView, true);
            move_sound.start();
        }
    }
    public void RightClick(View v){
        if(v.getId() == R.id.rightArrow ) {
            control.shiftRight();
            arr = control.data.getArr();
            score = control.data.getScore();
            if(!demo)
                scoreText.setText(String.format(score.toString(), "#########"));
            if(score>highScore)
                highScore=score;
            if(!demo)
                highScoreText.setText(String.format(highScore.toString(), "#########"));
            //draw onto fragment with updated array
            if(control.checkWin() && past2048) {
                if(demo)
                    demo=false;
                else
                    past2048 = control.win();
            }
            if(control.noMovesPossible()) {
                if(demo)
                    demo=false;
                else
                    control.gameOver();
                    setArrView(arr, arrView, false);
            }

        }
        if(!demo) {
            shift_anime = AnimationUtils.loadAnimation(this, R.anim.shift_right);
            setArrView(arr, arrView, true);
            move_sound.start();
        }
    }
    public void ResetClick(View v){
        if(v.getId() == R.id.restartButton) {
            demo = false;
            control.reset();
            arr = control.data.getArr();
            score = 0;
            past2048 = true;
            scoreText.setText(String.format(score.toString(), "#########"));
            //draw onto fragment with updated array
        }

        setArrView(arr, arrView, false);
    }
    public void DemoClick(View v){
        if(v.getId() == R.id.demoButton && !demo){
            new demo().execute("");
        }
    }

    public void SoundClick(View v) {
        if(v.getId() == R.id.sound_button && control.data.getSound()) {
            move_sound = MediaPlayer.create(this, R.raw.mute);
            control.data.setSound(false);
            sound_icon.setImageResource(R.drawable.sound_off);
        } else {
            control.data.setSound(true);
            move_sound = MediaPlayer.create(this, R.raw.pop);
            sound_icon.setImageResource(R.drawable.sound_on);
        }
    }
    public void AboutClick(View v){
        if(v.getId() == R.id.aboutButton)
        {
           control.about();
        }
    }

    private class demo extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params){

            Random demoRand = new Random();
                control.reset();
                int direction;
                demo=true;
                while(demo) {
                    direction = demoRand.nextInt(4);
                    switch (direction) {
                        case 0:
                            UpClick(findViewById(R.id.upArrow));
                            shift_anime = AnimationUtils.loadAnimation(context, R.anim.shift_up);
                            break;
                        case 1:
                            RightClick(findViewById(R.id.rightArrow));
                            shift_anime = AnimationUtils.loadAnimation(context, R.anim.shift_right);
                            break;
                        case 2:
                            DownClick(findViewById(R.id.downArrow));
                            shift_anime = AnimationUtils.loadAnimation(context, R.anim.shift_down);
                            break;
                        case 3:
                            LeftClick(findViewById(R.id.leftArrow));
                            shift_anime = AnimationUtils.loadAnimation(context, R.anim.shift_left);
                            break;
                        default:
                            UpClick(findViewById(R.id.upArrow));
                            shift_anime = AnimationUtils.loadAnimation(context, R.anim.shift_up);
                            break;
                    }
                    try {
                        publishProgress();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                demo = false;
                control.reset();
            return null;

            }

            @Override
            protected void onProgressUpdate(Void...values){
                move_sound.start();
                setArrView(arr, arrView, true);
            }
        }

}
