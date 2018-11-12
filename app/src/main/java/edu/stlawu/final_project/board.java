package edu.stlawu.final_project;

import android.graphics.Point;
import android.graphics.RectF;

import java.util.ArrayList;

// a short class that will allows the saving of data to seperate levels
public class board {
    //each board should have
    // an array that stores the

    private char[][]board;
    private  ArrayList<RectF> walls;
    private ArrayList<Point>starts;
    public int col;
    public int rows;

    // save all this stuff inside a board
    public board(char[][] aBoard, ArrayList<RectF> walls, ArrayList<Point> starts, int cols, int rows){
        super();
    }


    // all the public getting methods
    public char[][] getBoard() {
        return board;
    }

    public ArrayList<RectF> getWalls() {
        return walls;
    }

    public ArrayList<Point> getStarts() {
        return starts;
    }

    public int getCol() {
        return col;
    }

    public int getRows() {
        return rows;
    }
}
