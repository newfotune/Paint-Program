/*
 * Nwoke Fortune Chiemeziem Winter 2015.
 */

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import listeners.DrawingListener;
import model.DrawingTool;
import model.DrawnShapes;
import model.PencilTool;

/**
 * The canvas panel that is drawn on.
 * 
 * @author n4tunec.uw.edu.
 * @version winter 2015.
 *
 */
@SuppressWarnings("serial")
public class CanvasPanel extends JPanel implements Observer {

    /** The default width of the frame. */
    public static final int DEFAULT_WIDTH = 400;

    /** The default height of the frame. */
    public static final int DEFAULT_HEIGHT = 200;

    /** The start location of the grid to be drawn. */
    private static final int MY_GRID_START = 5;

    /** The current drawing tool. */
    private DrawingTool myDrawingTool;

    /** To determine of grid was selected. */
    private boolean myGridSelected;

    /** The graphics color. */
    private Color myColor;

    /** The list of all shapes drawn. */
    private final List<DrawnShapes> myOldShapes;

    /** The thickness of the graphics. */
    private float myThickness;

    private BufferedImage paintImage;
    
    /** Current Shape. */
    private Shape myCurrentShape;

    private boolean cleared = true;

    /**
     * This paints my home screen panel.
     */
    public CanvasPanel() {
        super();

        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        final DrawingTool defaultTool = new PencilTool();
        myColor = Color.BLACK;
        paintImage = new BufferedImage(400, 200, BufferedImage.TYPE_3BYTE_BGR);
        myOldShapes = new ArrayList<>();
        myDrawingTool = defaultTool;

        myThickness = 1f;
       // myCurrentShape = defaultTool.getShape();
    }

    @Override
    public void paintComponent(final Graphics theGraphics) {

        super.paintComponent(theGraphics);
        
        firePropertyChange("Drawn", !isEmpty(), isEmpty());
        final Graphics2D g2d = (Graphics2D) theGraphics;

        // for better graphics display
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        // print the old shapes
        for (int i = 0; i < myOldShapes.size(); i++) {
            g2d.setColor(myOldShapes.get(i).getColor());

            g2d.setStroke(new BasicStroke(myOldShapes.get(i).getThickness()));
            g2d.draw(myOldShapes.get(i).getShape());
        }

        // if the grid check box is selected.
        if (myGridSelected) {
            int startX = MY_GRID_START;
            int startY = 0;
            final Line2D grid = new Line2D.Double();

            for (int j = 0; j < getWidth() / MY_GRID_START * 2; j++) {
                grid.setLine(startX, startY, startX, getHeight());
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(1f));
                g2d.draw(grid);
                startX += MY_GRID_START * 2;
            }

            startX = 0;
            startY = MY_GRID_START;
            for (int i = 0; i < getHeight() / MY_GRID_START * 2; i++) {

                grid.setLine(startX, startY, getWidth(), startY);
                g2d.setColor(Color.BLACK);
                g2d.draw(grid);

                startY += MY_GRID_START * 2;
            }
        }

        if (!cleared) {
            g2d.setColor(myColor);
            g2d.setStroke(new BasicStroke(myThickness));

            g2d.draw(myCurrentShape);
        }
    }

    // public BufferedImage getImage() {
    // return
    // }

    /**
     * Gets the particular tool selected.
     * 
     * @param theDrawingTool the tool used in drawing.
     */
    public void setMyTool(final DrawingTool theDrawingTool) {
        myDrawingTool = theDrawingTool;

    }

    /**
     * Getter for the drawing tool.
     * 
     * @return the drawing too.
     */
    public DrawingTool getMyTool() {
        return myDrawingTool;
    }

    /**
     * setter for the tool color;.
     * 
     * @param theColor the new tool color.
     */
    public void setToolColor(final Color theColor) {
        myColor = theColor;
    }

    /**
     * Removes all the drawings from the panel.
     */
    public void removeAllDrawings() {
        myOldShapes.clear();
        cleared = true;
        repaint();
    }

    /**
     * changes the color of the graphics.
     * 
     * @param theColor the new graphics color.
     */
    public void setColor(final Color theColor) {
        myColor = theColor;
    }

    /**
     * to know is the grid check box was selected.
     * 
     * @param theGridSelected true if the checkbox was checked.
     */
    public void setGridSelected(final boolean theGridSelected) {
        myGridSelected = theGridSelected;
    }

    /**
     * sets the thickness of the graphics object.
     * 
     * @param theNewThickness the new thickness of the graphics.
     */
    public void setThickness(final float theNewThickness) {
        myThickness = theNewThickness;
    }

    /**
     * stores the drawn shapes.
     * 
     * @param theCurrentShape the shape being added to the arrayList of old shapes.
     */
    public void addOldShape(final Shape theCurrentShape) {
        myOldShapes.add(new DrawnShapes(theCurrentShape, myColor, myThickness));
    }

    /**
     * checks if the array is empty.
     * 
     * @return true if the array is empty and false other wise.
     */
    public boolean isEmpty() {
        return myOldShapes.isEmpty();
    }

    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObservable instanceof DrawingListener) {
            
            if (theObject instanceof Shape) {
                cleared = false; // show that something has been drawn
                myCurrentShape = (Shape) theObject;
                repaint();
            } else if (theObject instanceof Boolean) { // when mouse if relaased, we add the
                                                       // most recent shape to old shapes
                                                       // array.
                addOldShape(myCurrentShape);

            }
        }
    }

}
