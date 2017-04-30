package comp3710.aj.au2048game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.Random;
import static java.lang.Thread.sleep;


class Controller {
    public Model data = new Model();

    Controller(Context context){
        data.setArr(new int[4][4]);
        data.setScore(0);
        data.setRand(new Random());
        for(int i=0; i<2; i++)
            addNewNumber();
        data.setActivity((Activity) context);
    }

    //It would be a miracle if there were no out of bounds here
    void shiftRight(){
        boolean changed = false;
        for(int iterate = 0; iterate<3; iterate++) { // need to do shifts 3 times to move everything all the way
            for (int i = 0; i < 4; i++) // row loop
            {
                for (int j = 2; j >= 0; j--) { // column loop
                    if (data.getArr()[i][j] != 0) {
                        if (data.getArr()[i][j + 1] == 0) {
                            data.getArr()[i][j + 1] = data.getArr()[i][j];
                            data.getArr()[i][j] = 0;
                            changed = true;
                        } else if (data.getArr()[i][j] == data.getArr()[i][j + 1]) {
                            data.getArr()[i][j + 1] *= 2;
                            data.getArr()[i][j] = 0;
                            changed = true;
                            data.setScore(data.getScore() + data.getArr()[i][j + 1]);
                        }
                    }
                }


            }
        }
        if(changed){
            addNewNumber();

        }


    }
    void shiftLeft(){
        boolean changed = false;
        for(int iterate = 0; iterate<3; iterate++) { // need to do shifts 3 times to move everything all the way
            for (int i = 0; i < 4; i++) // row loop
            {
                for (int j = 1; j < 4; j++) { // column loop
                    if (data.getArr()[i][j] != 0) {
                        if (data.getArr()[i][j - 1] == 0) {
                            data.getArr()[i][j - 1] = data.getArr()[i][j];
                            data.getArr()[i][j] = 0;
                            changed = true;
                        } else if (data.getArr()[i][j] == data.getArr()[i][j - 1]) {
                            data.getArr()[i][j - 1] *= 2;
                            data.getArr()[i][j] = 0;
                            changed = true;
                            data.setScore(data.getScore() + data.getArr()[i][j - 1]);
                        }
                    }
                }


            }
        }
        if(changed){
            addNewNumber();

        }


    }
    void shiftUp(){
        boolean changed = false;
        for(int iterate = 0; iterate<3; iterate++) { // need to do shifts 3 times to move everything all the way
            for (int i = 1; i < 4; i++) // row loop
            {
                for (int j = 0; j < 4; j++) { // column loop
                    if (data.getArr()[i][j] != 0) {
                        if (data.getArr()[i - 1][j] == 0) {
                            data.getArr()[i - 1][j] = data.getArr()[i][j];
                            data.getArr()[i][j] = 0;
                            changed = true;
                        } else if (data.getArr()[i][j] == data.getArr()[i - 1][j]) {
                            data.getArr()[i - 1][j] *= 2;
                            data.getArr()[i][j] = 0;
                            changed = true;
                            data.setScore(data.getScore() + data.getArr()[i - 1][j]);
                        }
                    }
                }


            }
        }
        if(changed){
            addNewNumber();

        }


    }
    void shiftDown(){
        boolean changed = false;
        for(int iterate = 0; iterate<3; iterate++) { // need to do shifts 3 times to move everything all the way
            for (int i = 2; i >= 0; i--) // row loop
            {
                for (int j = 0; j < 4; j++) { // column loop
                    if (data.getArr()[i][j] != 0) {
                        if (data.getArr()[i + 1][j] == 0) {
                            data.getArr()[i + 1][j] = data.getArr()[i][j];
                            data.getArr()[i][j] = 0;
                            changed = true;
                        } else if (data.getArr()[i][j] == data.getArr()[i + 1][j]) {
                            data.getArr()[i + 1][j] *= 2;
                            data.getArr()[i][j] = 0;
                            changed = true;
                            data.setScore(data.getScore() + data.getArr()[i + 1][j]);
                        }
                    }
                }


            }
        }
            if (changed) {
                addNewNumber();

            }



    }

    void addNewNumber(){
        int current=0;
        ArrayList<Integer> empty;
        empty = new ArrayList<>();
        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++) {
                if (data.getArr()[i][j] == 0) {
                    empty.add(current);
                }
                current++;
            }
        int randCheck;
        int randEmpty = data.getRand().nextInt(empty.size());
        randCheck = data.getRand().nextInt(100);

        if (randCheck < 25) {
            data.getArr()[(empty.get(randEmpty))/4][(empty.get(randEmpty)) % 4] = 4;
        } else {
            data.getArr()[(empty.get(randEmpty))/4][(empty.get(randEmpty)) % 4] = 2;
        }

    }
    boolean noMovesPossible(){
        int emptyCount=0;
        for(int i=0; i<4; i++) {
            if(emptyCount != 0)
                break;
            for (int j = 0; j < 4; j++)
                if (data.getArr()[i][j] == 0) {
                    emptyCount++;
                    break;
                }
        }
        if(emptyCount != 0)
            return false;
        else{
            for(int i=0; i<4; i++)
                for(int j=0; j<4; j++)
                {
                    if(i==3 && j==3)
                        continue;
                    else if(i==3){
                        if(j==3)
                            continue;
                        else
                        if(data.getArr()[i][j] == data.getArr()[i][j+1])
                            return false;

                    }
                    else if(j==3){
                        if(i==3)
                            continue;
                        else
                        if(data.getArr()[i][j] == data.getArr()[i+1][j])
                            return false;
                    }
                    else{
                        if(data.getArr()[i][j]==data.getArr()[i][j+1] || data.getArr()[i][j] == data.getArr()[i+1][j])
                            return false;
                    }
                }



        }
        return true; //only executes if no empty spaces exist and no adjacent tiles can merge
    }
    void gameOver()  {
        AlertDialog.Builder builder = new AlertDialog.Builder(data.getModelActivity());
        builder.setMessage("Game Over, Try Again!");
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked reset button
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        reset();
    }
    boolean checkWin(){
        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                if(data.getArr()[i][j] == 2048) {

                    data.setWantsToContinue(false);
                    return true;
                }
        return false;
    }
    boolean win(){
        AlertDialog.Builder builder = new AlertDialog.Builder(data.getModelActivity());
        builder.setMessage("You Win!");
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                reset();// User clicked reset button
            }
        });
        builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked continue button, continue the game
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return data.doesWantToContinue();


    }
    void reset(){
        data.setArr(new int[4][4]);
        data.setScore(0);
        data.setWantsToContinue(true);
        for(int i=0; i<2; i++)
            addNewNumber();
    }
}
