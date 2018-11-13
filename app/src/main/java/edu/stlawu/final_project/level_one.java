package edu.stlawu.final_project;

<<<<<<< HEAD

import android.graphics.RectF;

import java.util.ArrayList;

=======
import android.graphics.RectF;

import java.util.ArrayList;
>>>>>>> f972cfbee8de296c3091a56072e56122ae1dbd67

public class level_one {

    private char[][]board;
    static final char VWALL = '|';
    static final char HWALL = '-';
    static final char MAZE_PATH = '0';
    private int xmax;
    private int ymax;
    private int rec_width;
    private int top_gap;
   // the points where the ball will start on this level running
    public int sx;
    public int sy;

    //
    public RectF wall;

    public portal aPortal;
    public ArrayList<RectF> walls;
    public ArrayList<portal> portals;

    // need an array of mazes
    private ArrayList<char[][]> levels;
    // need an array of maze walls

    public ArrayList<ArrayList<RectF>> level_walls;

    // need an arraylist of portals for each level
    private ArrayList<ArrayList<RectF>> level_portals;


    public level_one( int max_x, int max_y, int ballwidth){
        //given the screen demsinsions the game will draw a level maze from memory and place
        // the ball where it is supposed to be within the maze upon this levels start

        this.rec_width = ballwidth+10;
        System.out.println("the max width is " + max_x);
        this.xmax = max_x-rec_width;
        this.ymax= max_y-rec_width;

        this.top_gap = 460;
        this.walls =new ArrayList<RectF>();
        this.portals = new ArrayList<portal>();

        this.level_walls = new ArrayList<ArrayList<RectF>>();
        char maze_one[][]={
                {'-','-','|','-','|','-','|','-','|','-','|','-','|','-','|','-','|','-','-'},
                {'|','0','0','0','|','0','|','0','|','0','|','0','0','0','0','0','|','0','|'},
                {'-','0','|','-','-','0','-','0','-','0','-','-','|','-','|','0','-','0','|'},
                {'|','0','0','0','|','0','0','0','0','0','|','0','0','0','|','0','|','0','1'},
                {'-','0','|','-','-','-','|','0','|','-','-','0','-','0','-','0','-','0','|'},
                {'|','0','0','0','|','0','0','0','0','0','|','0','|','0','0','0','0','0','|'},
                {'-','-','|','0','-','-','|','-','|','0','-','-','|','0','-','0','-','0','|'},
                {'s','0','|','0','|','0','0','0','0','0','0','0','0','0','|','0','|','0','0'},
                {'-','0','-','0','-','0','|','-','|','-','|','-','|','-','|','-','|','-','|'},
                {'|','0','0','0','0','0','0','0','0','0','|','0','0','0','|','0','0','0','|'},
                {'-','0','|','-','-','-','|','0','-','0','-','0','|','-','-','0','|','-','|'},
                {'|','0','0','0','|','0','0','0','|','0','|','0','0','0','|','0','0','0','|'},
                {'-','0','-','0','-','-','|','0','-','0','-','-','|','0','-','-','|','0','|'},
                {'|','0','|','0','|','0','0','0','|','0','0','0','0','0','|','0','0','0','|'},
                {'-','0','|','-','-','-','|','0','-','-','|','0','|','-','-','0','|','-','|'},
                {'0','0','0','0','|','0','0','0','|','0','0','0','0','0','|','0','0','0','|'},
                {'-','0','-','0','-','-','|','0','-','0','|','-','|','-','-','-','|','0','|'},
                {'|','0','|','0','|','0','0','0','|','0','0','0','0','0','0','0','0','0','|'},
                {'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'}
      };
        char maze_two[][]={

        };

        this.board = maze_one;
      //this.levels.add(board);
}



// will only generate the walls once
    public void generate_walls(){
        System.out.println("making walls now");
        for(int i=0; i<19; i++) {
            for (int j = 0; j < 19; j++) {
                if (board[i][j] == VWALL || (board[i][j] == HWALL)) {
                    float left = i * rec_width;
                    float top = j * rec_width + top_gap;
                    float right = i * rec_width + rec_width;
                    float bottom = j * rec_width + rec_width + top_gap;
                    // draw the new rectangle to canvas
                    wall = new RectF(left, top, right, bottom);
                    walls.add(wall);
                }else if(Character.isDigit(board[i][j])&& board[i][j]!='0'){
                    float left = i * rec_width;
                    float top = j * rec_width + top_gap;
                    float right = i * rec_width + rec_width;
                    float bottom = j * rec_width + rec_width + top_gap;
                    wall = new RectF(left, top, right, bottom);
                    aPortal = new portal(board[i][j], wall);
                    portals.add(aPortal);
                }else if(board[i][j] == 's'){
                    int x = i*rec_width;
                    int y = j *rec_width + top_gap;
                    sx = x+1;
                    sy = x+1;
                }

            }
        }
    }
public int getSx(){
        return sx;
}
public int getSy(){
        return sy;
}

}
