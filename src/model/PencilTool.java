/*
 * Nwoke Fortune Chiemeziem
 * Winter 2015.
 */
package model;

import java.awt.Shape;
import java.awt.geom.Path2D;

/**
 * This is the pencil drawing tool class.
 * @author n4tunec@uw.edu.
 * @version winter 2015.
 *
 */
public class PencilTool extends AbstractTool {

    /**The pathe2D object. */
    private final Path2D myPath;
    /**
     * This is my pencil tool.
     */
    public PencilTool() {
        super();
        myPath = new Path2D.Double();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Shape getShape() {
        return myPath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape startingPoint(final int theStartX, final int theStartY) {
        
        myPath.moveTo(theStartX, theStartY); 
        return myPath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape endingPoint(final int theEndX, final int theEndY) {
        myPath.lineTo(theEndX, theEndY);
        return myPath;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Shape finalPoint(final int theEndX, final int theEndY) {
        myPath.lineTo(theEndX, theEndY);
        return (Shape) myPath.clone();
    }
}
