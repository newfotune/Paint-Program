/*
 * Nwoke Fortune Chiemeziem
 * Winter 2015.
 */
package listeners;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import view.CanvasPanel;

/**
 * The Grid listener listens for when the user wants a grid.
 * @author n4tunec@uw.edu
 * @version winter 2015.
 *
 */
@SuppressWarnings("serial")
public class GridListener extends AbstractAction {

    /** The canvas to hold the grid.*/
    private final CanvasPanel myCanvas;
    
    /** To determine of the grid was selected.*/
    private final boolean myGridSelected;
    
    /**
     * Initializes the fields.
     * @param theCanvas the canvas to be drawn on.
     * @param theCheckBoxChosen the state of the checkbox button.
     */
    public GridListener(final CanvasPanel theCanvas, final boolean theCheckBoxChosen) {
        super();
        myCanvas = theCanvas;
        myGridSelected = theCheckBoxChosen;
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) { 
        System.out.println(myGridSelected);
        myCanvas.setGridSelected(myGridSelected);
    }

}
