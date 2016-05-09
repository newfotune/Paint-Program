package listeners;

import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;

import model.DrawingTool;
import model.PencilTool;

/**
 * This is the Mouse listener class that handles mouse movement on the canvas.
 * 
 * @author n4tunec@uw.edu
 * @version winter 2015.
 *
 */
public class DrawingListener extends Observable implements MouseListener, MouseMotionListener {
    /**
     * My current drawing tool. Changes whenever the user chooses another tool.
     */
    private DrawingTool myDrawingTool;

    /** My current shape. */
    private Shape myCurrentDrawing;
    
    /**
     * Constructor creates a default pencil tool.
     */
    public DrawingListener() {
        super();
        myDrawingTool = new PencilTool();
    }

    /**
     * Changes my tool to the new tool.
     * 
     * @param theDrawingTool the new tool.
     */
    public void setTool(final DrawingTool theDrawingTool) {
        myDrawingTool = theDrawingTool;
    }

    /**
     * This method determines where the line was dragged to.
     * 
     * @param theEvent This is the even passed.
     */
    public void mouseDragged(final MouseEvent theEvent) {
        myCurrentDrawing = myDrawingTool.endingPoint(theEvent.getX(), theEvent.getY());
        this.setChanged();
        this.notifyObservers(myCurrentDrawing);
    }

    @Override
    public void mousePressed(final MouseEvent theEvent) {
        myCurrentDrawing = myDrawingTool.startingPoint(theEvent.getX(), theEvent.getY());
        this.setChanged();
        this.notifyObservers(myCurrentDrawing);

    }

    @Override
    public void mouseReleased(final MouseEvent theEvent) {
        myCurrentDrawing = myDrawingTool.finalPoint(theEvent.getX(), theEvent.getY());
        this.setChanged();
        this.notifyObservers(true);
    }

    @Override
    public void mouseClicked(final MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(final MouseEvent arg0) {
    }

    @Override
    public void mouseExited(final MouseEvent arg0) {
    }

    @Override
    public void mouseMoved(final MouseEvent arg0) {
    }
}
