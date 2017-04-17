package comp3710.aj.au2048game;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.Random;
import static java.lang.Thread.sleep;


public class Controller {
    private int[][] arr;
    private int score;
    private Random rand;

    public Controller(){
        arr = new int[4][4];
        score = 0;
        rand = new Random();
        for(int i=0; i<2; i++)
            addNewNumber();


    }
    //It would be a miracle if there were no out of bounds here
    public void shiftRight(){
        boolean changed = false;
        for(int i=0; i<4; i++) // row loop
        {
            for(int j = 2; j>=0; j--){ // column loop
                if(arr[i][j] != 0 ){
                    if(arr[i][j+1] == 0){
                        arr[i][j+1]=arr[i][j];
                        arr[i][j]=0;
                        changed = true;
                    }
                    else if(arr[i][j] == arr[i][j+1]){
                        arr[i][j+1] *=2;
                        arr[i][j]=0;
                        changed = true;
                        score+=arr[i][j+1];
                    }
                }
            }
            if(changed){
                addNewNumber();
                if(noMovesPossible())
                    gameOver();
            }

        }


    }
    public void shiftLeft(){
        boolean changed = false;
        for(int i=0; i<4; i++) // row loop
        {
            for(int j = 1; j<4; j++){ // column loop
                if(arr[i][j] != 0 ){
                    if(arr[i][j-1] == 0){
                        arr[i][j-1]=arr[i][j];
                        arr[i][j]=0;
                        changed = true;
                    }
                    else if(arr[i][j] == arr[i][j-1]){
                        arr[i][j-1] *=2;
                        arr[i][j]=0;
                        changed = true;
                        score+=arr[i][j-1];
                    }
                }
            }
            if(changed){
                addNewNumber();
                if(noMovesPossible())
                    gameOver();
            }

        }


    }
    public void shiftUp(){
        boolean changed = false;
        for(int i=1; i<4; i++) // row loop
        {
            for(int j =0; j<4; j++){ // column loop
                if(arr[i][j] != 0 ){
                    if(arr[i-1][j] == 0){
                        arr[i-1][j]=arr[i][j];
                        arr[i][j]=0;
                        changed = true;
                    }
                    else if(arr[i][j] == arr[i-1][j]){
                        arr[i-1][j] *=2;
                        arr[i][j]=0;
                        changed = true;
                        score+=arr[i-1][j];
                    }
                }
            }
            if(changed){
                addNewNumber();
                if(noMovesPossible())
                    gameOver();
            }

        }


    }
    public void shiftDown(){
        boolean changed = false;
        for(int i=2; i>=0; i--) // row loop
        {
            for(int j = 0; j<4; j++){ // column loop
                if(arr[i][j] != 0 ){
                    if(arr[i+1][j] == 0){
                        arr[i+1][j]=arr[i][j];
                        arr[i][j]=0;
                        changed = true;
                    }
                    else if(arr[i][j] == arr[i+1][j]){
                        arr[i+1][j] *=2;
                        arr[i][j]=0;
                        changed = true;
                        score+=arr[i+1][j];
                    }
                }
            }
            if(changed){
                addNewNumber();
                if(noMovesPossible())
                    gameOver();
            }

        }


    }

    public void addNewNumber(){
        int current=0;
        ArrayList<Integer> empty;
        empty = new ArrayList<Integer>();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++) {
                if (arr[i][j] == 0) {
                    empty.add(current);
                }
                current++;
            }
        int randCheck;
        int randEmpty = rand.nextInt(empty.size());
        randCheck = rand.nextInt(100);

        if (randCheck < 25) {
            arr[(empty.get(randEmpty))/4][(empty.get(randEmpty)) % 4] = 4;
        } else {
            arr[(empty.get(randEmpty))/4][(empty.get(randEmpty)) % 4] = 2;
        }

    }
    public boolean noMovesPossible(){
        int emptyCount=0;
        for(int i=0; i<4; i++) {
            if(emptyCount != 0)
                break;
            for (int j = 0; j < 4; j++)
                if (arr[i][j] == 0) {
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
                        if(arr[i][j] == arr[i][j+1])
                            return false;

                    }
                    else if(j==3){
                        if(i==3)
                            continue;
                        else
                        if(arr[i][j] == arr[i+1][j])
                            return false;
                    }
                }



        }
        return true; //only executes if no empty spaces exist and no adjacent tiles can merge
    }
    public void gameOver()  {
        //display game over text or whatever
        try {
            sleep(5000); // display game over text for 5 seconds, then reset
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reset();
    }
    public void reset(){
        arr = new int[4][4];
        score = 0;
        for(int i=0; i<2; i++)
            addNewNumber();
    }
}
