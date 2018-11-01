package edu.stlawu.final_project;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


// This is where we will define the mazes and how to draw them to the canvas, this is also where we
// must be able to check and see if the ball will hit a maze wall so that we can prevent it form
// breaking out of the maze in an unintended way
class maze {
    public ArrayList<Line> walls = new ArrayList<Line>();
    public Paint black = new Paint();

    public void draw(Canvas canvas, int width, int height){
        Point a = new Point(0,120);
        Point b = new Point(width-120,120);
        Point c = new Point(0,240);
        Point d = new Point(width-120,240);
        Line ab = new Line(a,b);
        Line cd = new Line(c,d);
        canvas.drawLine(a.x,a.y,b.x,b.y, black);
        canvas.drawLine(c.x,c.y,d.x,d.y,black);
        walls.add(ab);
        walls.add(cd);


        }
    }
