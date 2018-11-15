package edu.stlawu.final_project;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


//modeled after this persons helpful code **
//https://stackoverflow.com/questions/6479637/android-accelerometer-moving-ball

public class GameActivity extends Activity implements SensorEventListener{

    CustomDrawableView mCustomDrawableView = null;

    public float xPosition, xAcceleration,xVelocity = 0.0f;
    public float yPosition, yAcceleration,yVelocity = 0.0f;
    public int xmax,ymax;
    private SensorManager sensorManager = null;
    public float frameTime = 0.666f;
    public int screenWidth, screenHeight;
    public int ballSize;
    private TextView timer_count = null;
    private Timer t = null;
    private Counter ctr = null;
    private Bitmap ballBitmap;
    private Bitmap wallBitmap;
    private Bitmap portalBitmap;
    private Bitmap ball;
    private Bitmap wall;
    private Bitmap portal;
    // keep track of end of level and what level we are on
    private boolean levelOver;
    private int curr_level = 0;

    //keep track of walls hit
    private ArrayList<RectF> wall_hit = new ArrayList<RectF>();
    // get an array of the walls for this level
    public ArrayList<RectF> walls;
    // get the exits to this level
    public ArrayList<portal> portals;
    // the level instance
    public cube_maze currLevel;

    class Counter extends TimerTask {
        private int count = 0;
        @Override
        public void run() {
            GameActivity.this.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            GameActivity.this.timer_count.setText(
                                    Integer.toString(count));
                            count++;
                        }
                    }
            );
        }
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        //display stuff
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        ballSize = (screenWidth/19) -10;
        xmax = screenWidth-(ballSize+10);
        ymax = screenHeight-(2*ballSize+10);   // might have to tweek these
        System.out.println("the device screen width: "+xmax);
        System.out.println("the device screen height: "+ymax);

        // Get a reference to a SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        mCustomDrawableView = new CustomDrawableView(this);
        setContentView(mCustomDrawableView);
        // setContentView(R.layout.main);




        // generate the set levels for time trial
        currLevel = new cube_maze( screenWidth-10, screenHeight, ballSize);
        currLevel.generate_maze();
        this.walls = currLevel.walls;
        this.portals = currLevel.portals;
        //set the start position for the ball in this maze
        xPosition = currLevel.sx;
        yPosition = currLevel.sy-11;

        final int ballWidth = ballSize;
        final int ballHeight = ballSize;
        final int wallsize = ballSize+10;
        //why is there two of these
        ballBitmap = Bitmap.createScaledBitmap(ball, ballWidth, ballHeight, true);
        wallBitmap = Bitmap.createScaledBitmap(wall, wallsize ,wallsize, true);
        portalBitmap = Bitmap.createScaledBitmap(portal, wallsize,wallsize,true);

        // now that we have level generate everything and set the arrays to starting values

        levelOver= false;
    }

    // the equivalent of a sensor event listener
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        {   // for some reason this works the best but the magnetic force one is reliable too
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                //Set sensor values as acceleration
                yAcceleration = sensorEvent.values[1];
                xAcceleration = sensorEvent.values[2];
                updateBall();
            }
        }
    }
    //stuff to update the balls speed location and such
    private void updateBall() {
        //Calculating the new speed so that based on tilt time ball will speed up
        xVelocity += (xAcceleration * frameTime)/2;
        yVelocity += (yAcceleration * frameTime)/2;
        //Calc distance travelled in that time
        float xS = (xVelocity/4)*frameTime;
        float yS = (yVelocity/4)*frameTime;
        //sensors are opposite so this changes them to act accordingly to what we want
        float old_x = xPosition;
        float old_y = yPosition;
        float new_x = xPosition -xS;
        float new_y = yPosition -yS;
        // moves in some direction
        xPosition -= xS;
        yPosition -= yS;
        // check to see that the ball is allowed to exist there
        //ball wall collision (a pain in the butt)
        for (RectF awall: walls) {
            // two test rectangles
            // one with updated x movement
            RectF sideMove = new RectF(((float) xPosition), ((float) old_y), ((float) xPosition + ballSize), ((float) old_y + ballSize));
            // one with updated y movement
            RectF upMove = new RectF(((float) old_x), ((float) yPosition), ((float) old_x + ballSize), ((float) yPosition + ballSize));
            // one where both x and y movement
            RectF diagMove = new RectF(xPosition, yPosition, xPosition + ballSize, yPosition + ballSize);
            // abality to move diagonal
            if (!diagMove.intersect(awall)){
                //allow movement
            }
            // horizontal collision
            else if ((upMove.intersect(awall) && diagMove.intersect(awall))){
                yPosition =old_y;
                yVelocity = yVelocity*-1/4;
                if(!wall_hit.contains(awall)){
                    wall_hit.add(awall);
                }
            // vertical collision
            }else if ((sideMove.intersect(awall)&& diagMove.intersect(awall))){
                xPosition = old_x;
                xVelocity = xVelocity*-1/4;
                if(!wall_hit.contains(awall)){
                    wall_hit.add(awall);
                }
            }else if (upMove.intersect(awall) && !sideMove.intersect(awall) && !diagMove.intersect(awall)){
                if(!wall_hit.contains(awall)){
                    wall_hit.add(awall);
                }
                // let diagonal movement happen
                // allow movement
            }else if(sideMove.intersect(awall) && !upMove.intersect(awall) && !diagMove.intersect(awall)){
                if(!wall_hit.contains(awall)){
                    wall_hit.add(awall);
                }
                //allow diagonal movement
            }else{
            }
        }
        //screen boundaries
        for (portal aPortal: portals){
            if (aPortal.getPortalBitmap().contains(xPosition,yPosition)){
                 levelOver=true;
            }
        }
        //screen bounds collision (works just fine)
        if (xPosition > xmax) {
            xPosition = xmax;
            xVelocity = xVelocity*-1/4;
        } else if (xPosition <0) {
            xPosition = 0;
            xVelocity = xVelocity*-1/4;
        }
        if (yPosition > ymax) {
            yPosition = ymax;
            yVelocity = yVelocity*-1/4;
        } else if (yPosition < 460) {
            yPosition = 460;
            yVelocity = yVelocity*-1/4;
        }
    }
    // this changes with low power setting in devices but we will not mess with this
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop()
    {
        // Unregister the listener
        sensorManager.unregisterListener(this);
        super.onStop();
    }


    //drawing the screen and the ball from a resource image
    public class CustomDrawableView extends View
    {
        public CustomDrawableView(Context context)
        {
            super(context);
            //set the bitmaps up
            ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
            wall = BitmapFactory.decodeResource(getResources(),R.drawable.wall);
            portal = BitmapFactory.decodeResource(getResources(),R.drawable.portal);
        }

        // the canvas that these objects are being drawn on
        protected void onDraw(Canvas canvas)
        {
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);
            //make background black if level is not over
            if(levelOver ==false){
                canvas.drawColor(Color.BLACK);
            }else{
                //reveal the entire maze
                for(RectF awall: currLevel.walls){
                    canvas.drawBitmap(wallBitmap,null,awall, paint);
                }
            }
            //draw rectangles hit
            for (RectF awall: wall_hit){
                canvas.drawBitmap(wallBitmap,null,awall, paint);
            }
            // draw all portals that exist on this level
            for ( portal aPortal: portals){
                RectF RecPortal = aPortal.getPortalBitmap();
                canvas.drawBitmap(portalBitmap,null,RecPortal,paint);
            }
            //draw the ball
            canvas.drawBitmap(ballBitmap, xPosition, yPosition, null);
            invalidate();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}