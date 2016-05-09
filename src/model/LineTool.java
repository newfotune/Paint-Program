/*
 * Nwoke Fortune Chiemeziem.
 * Winter 2015.
 */
package model;

import java.awt.Shape;
import java.awt.geom.Line2D;

/**
 * This class handle the Line drawing tool.
 * @author n4tunec@uw.edu 
 * @version winter 2015
 *
 */
public class LineTool extends AbstractTool {

    /**The line to be drawn. */
    private final Line2D myLine;
    /** The starting X of the Line.*/
    private int myStartX;

    /**The starting Y of the Line.*/
    private int myStartY;

    /**
     * The Default constructor initializes our Line to point
     * (0,0,0,0).
     */
    public LineTool() {
        super();
        myStartX = 0;
        myStartY = 0;
        
        myLine = new Line2D.Double(myStartX, myStartY, myStartX, myStartY);
    }

    /**
     * This returns the current shape.
     * @return the current shape which is a line.
     */
    public Shape getShape() {
        return myLine;
    }

    /**
     * Sets the starting point of my Line.
     * @param theStartX the Starting X of my Line.
     * @param theStartY the Starting Y of my Line.
     * @return the new Line.
     */
    public Shape startingPoint(final int theStartX, final int theStartY) {
        myLine.setLine(theStartX, theStartY, theStartX, theStartY);
        myStartX = theStartX;
        myStartY = theStartY;
        return myLine;
    }
    
    /**
     * Sets the ending point of my Line.
     * @param theEndX the ending X of my Line.
     * @param theEndY the ending Y of my Line.
     * @return the new Line.
     */
    public Shape endingPoint(final int theEndX, final int theEndY) {
        myLine.setLine(myStartX, myStartY, theEndX, theEndY);
        return (Shape) myLine.clone();
    }
    
    /**
     * Sets the ending point of my Line.
     * @param theEndX the ending X of my Line.
     * @param theEndY the ending Y of my Line.
     * @return the new Line.
     */
    public Shape finalPoint(final int theEndX, final int theEndY) {
        myLine.setLine(myStartX, myStartY, theEndX, theEndY);
        return (Shape) myLine.clone();
    }
}
