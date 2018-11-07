package edu.stlawu.final_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class level_one {

    public char[][]board;
    static final char VWALL = '|';
    static final char HWALL = '-';
    static final char MAZE_PATH = '0';
    private int xmax;
    private int ymax;
    private int rec_width;
    private int top_gap;
   // the points where the ball will start on this level running
    private int sx;
    private int sy;
    public RectF wall;
    private Context con;
    public ArrayList<RectF> walls;


    public level_one( int max_x, int max_y, Context con){
        //given the screen demsinsions the game will draw a level maze from memory and place
        // the ball where it is supposed to be within the maze upon this levels start
        this.xmax = max_x;
        this.ymax= max_y;
        this.rec_width = 85;
        this.top_gap = 460;
        this.con = con;
        this.walls =new ArrayList<RectF>();
        char maze[][]={
                {'-','-','|','-','|','-','|','-','|','-','|','-','|','-','|','-','|','-','-'},
                {'|','0','0','0','|','0','|','0','|','0','|','0','0','0','0','0','|','0','|'},
                {'-','0','|','-','-','0','-','0','-','0','-','-','|','-','|','0','-','0','|'},
                {'|','0','0','0','|','0','0','0','0','0','|','0','0','0','|','0','|','0','0'},
                {'-','0','|','-','-','-','|','0','|','-','-','0','-','0','-','0','-','0','|'},
                {'|','0','0','0','|','0','0','0','0','0','|','0','|','0','0','0','0','0','|'},
                {'-','-','|','0','-','-','|','-','|','0','-','-','|','0','-','0','-','0','|'},
                {'0','0','|','0','|','0','0','0','0','0','0','0','0','0','|','0','|','0','0'},
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
      this.board = maze;
    }

// will only generate the walls once
    public void generate_walls(){
        System.out.println("making walls now");
        for(int i=0; i<18; i++) {
            for (int j = 0; j < 18; j++) {
                if (board[i][j] == VWALL || (board[i][j] == HWALL)) {
                    float left = i * rec_width;
                    float top = j * rec_width + top_gap;
                    float right = i * rec_width + rec_width;
                    float bottom = j * rec_width + rec_width + top_gap;
                    // draw the new rectangle to canvas
                    wall = new RectF(left, top, right, bottom);
                    walls.add(wall);

                }

            }
        }
    }


    // need to optimize this badly its so slow
    public void draw(Canvas canvas, Paint paint){
        // need to make proportional to ball size and screen dimensions
        paint.setFilterBitmap(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        Bitmap bitmap = BitmapFactory.decodeResource(con.getResources(),R.drawable.wall);

        for (RectF awall: walls){
            canvas.drawBitmap(bitmap,null,awall, paint);
        }

            // the maze outline
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0,top_gap-5,xmax,ymax-2,paint);


    }
}
