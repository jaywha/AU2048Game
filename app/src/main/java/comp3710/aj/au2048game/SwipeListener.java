package comp3710.aj.au2048game;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by AJ on 4/30/2017.
 */

public class SwipeListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityZ, float velocityY)
    {
        float x1 = e1.getX();
        float y1 = e1.getY();
        float x2 = e2.getX();
        float y2 = e2.getY();

        Direction direction = getDirection(x1,y1,x2,y2);
        return onSwipe(direction);


    }
     public boolean onSwipe(Direction direction){
         return false;

    }
    public Direction getDirection(float x1, float y1, float x2, float y2){
        double angle = getAngle(x1,y1,x2,y2);
        return Direction.get(angle);
    }

    public double getAngle(float x1, float y1, float x2, float y2){
        double rad = Math.atan2(y1-y2, x2-x1) + Math.PI;
        double calc = rad*180/Math.PI;
        double calc2 = calc+180;
        return calc2%360;
    }

    public enum Direction{
        up,down,left,right;

        public static Direction get(double angle){
            if((angle>=45) && (angle<135))
                return Direction.up;
            else if(((angle>=0) && (angle<45)) || ((angle >=315) && (angle<360)))
                return Direction.right;
            else if((angle>=225) && (angle<315))
                return Direction.down;
            else
                return Direction.left;
        }
    }
}
