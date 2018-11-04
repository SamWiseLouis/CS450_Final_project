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
import android.graphics.drawable.ShapeDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;




//modeled after this persons helpful code **
//https://stackoverflow.com/questions/6479637/android-accelerometer-moving-ball

public class GameActivity extends Activity implements SensorEventListener{

    CustomDrawableView mCustomDrawableView = null;
    ShapeDrawable mDrawable = new ShapeDrawable();
    public float xPosition, xAcceleration,xVelocity = 0.0f;
    public float yPosition, yAcceleration,yVelocity = 0.0f;
    public int xmax,ymax;
    private Bitmap mBitmap;
    private SensorManager sensorManager = null;
    public float frameTime = 0.666f;
    public int screenWidth, screenHeight;
    private level_one level;
    private Bitmap ball;
    public int ballSize = 80;
    private Context con;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        xmax = screenWidth-(85);
        ymax = screenHeight-(130);


        // Get a reference to a SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

        mCustomDrawableView = new CustomDrawableView(this);
        setContentView(mCustomDrawableView);
        // setContentView(R.layout.main);

        //set the boundry of the balls movement
        Display display = getWindowManager().getDefaultDisplay();



        System.out.println("the device screen width: "+xmax);
        System.out.println("the device screen height: "+ymax);
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

        //Calculate new speed
        xVelocity += (xAcceleration * frameTime)/2;
        yVelocity += (yAcceleration * frameTime)/2;

        //Calc distance travelled in that time
        float xS = (xVelocity/4)*frameTime;
        float yS = (yVelocity/4)*frameTime;

        //sensors are opposite so this changes them to act accordingly to what we want
        xPosition -= xS;
        yPosition -= yS;

        //need to make a deadzone so that the ball is not so touchy
        // and the player can have better controls

        // ball will bounce off sides and invert velocity direction
        // also decreses the velocity so that the ball acts more like a marble and less like
        // a bouncy ball
        if (xPosition > xmax) {
            xPosition = xmax;
            xVelocity = xVelocity*-1/4;
        } else if (xPosition < 0) {
            xPosition = 0;
            xVelocity = xVelocity*-1/4;
        }
        if (yPosition > ymax) {
            yPosition = ymax;
            yVelocity = yVelocity*-1/4;
        } else if (yPosition < 0) {
            yPosition = 0;
            yVelocity = yVelocity*-1/4;
        }
    }
    // diffrence in power saving mode vs normal?
    // I've chosen to not implement this method
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
            ball = BitmapFactory.decodeResource(getResources(), R.drawable.one);


            // scale sizing of the ball
            System.out.println(xmax);
            System.out.println(ymax);
            final int ballWidth = ballSize;
            final int ballHeight = ballSize;
            level = new level_one( xmax+80, ymax+80, context);
            System.out.println("making walls");
            level.generate_walls();
            mBitmap = Bitmap.createScaledBitmap(ball, ballWidth, ballHeight, true);

        }

        // the canvas that these objects are being drawn on
        protected void onDraw(Canvas canvas)
        {

            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);
            final Bitmap bitmap = mBitmap;
            canvas.drawBitmap(bitmap, xPosition, yPosition, null);
            level.draw(canvas,paint);
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