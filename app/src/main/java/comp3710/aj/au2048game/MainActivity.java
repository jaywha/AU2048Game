package comp3710.aj.au2048game;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //TODO: Make fragment for tiles : BoardView similar to CannonView?
    //TODO: Add High-score to layout : Separate Class in the model?
    //TODO: Connect controller logic to layout : Controller --> View
    //TODO: OPTIONAL :: Make UML Diagrams
}
