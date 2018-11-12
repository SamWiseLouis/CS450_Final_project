package edu.stlawu.final_project;

import android.graphics.RectF;

public class portal {

    private int destination;
    private RectF portalBitmap;

    public portal(int aDestination, RectF aRectF){
        this.destination = aDestination;
        this.portalBitmap = aRectF;
    }

    public int getDestination(){
        return destination;
    }
    public RectF getPortalBitmap(){
        return portalBitmap;
    }
}
