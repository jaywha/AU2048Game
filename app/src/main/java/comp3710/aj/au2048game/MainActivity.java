package comp3710.aj.au2048game;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.lang.Integer;
import android.widget.TextView;

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
        control = new Controller();
        arr = control.getArr();
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
