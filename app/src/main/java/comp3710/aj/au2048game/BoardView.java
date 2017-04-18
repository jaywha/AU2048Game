package comp3710.aj.au2048game;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {

    private Activity activity;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        activity = (Activity) context;
        getHolder().addCallback(this);

        //TODO: Get Sound and other things for Final
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //TODO: Do we ned to have a separate thread?
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //TODO: Terminates thread
    }

}
