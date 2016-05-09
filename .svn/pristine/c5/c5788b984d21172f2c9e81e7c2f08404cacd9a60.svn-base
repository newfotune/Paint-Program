
package listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JColorChooser;

import view.CanvasPanel;

/**
 * This listener listens for when the user clicks on the Color menuItem.
 * @author ntunec@uw.edu
 * @version winter 2014.
 *
 */
@SuppressWarnings("serial")
public class ColorChooserListener extends AbstractAction {
    /** The canvas to be painted on. */
    private final CanvasPanel myCanvas;
    
    /** 
     * This initiates the canvas.
     * @param theCanvas the canvas to be drawn on.
     */
    public ColorChooserListener(final CanvasPanel theCanvas) {
        super();
        putValue(MNEMONIC_KEY, KeyEvent.VK_C);
        myCanvas = theCanvas;
    }

    /**
     * this pops up the jcolor chooser and sets the canvas drawing color.
     * @param theEvent the event thet was triggered.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        final Color color = JColorChooser.showDialog(myCanvas, "Choose your Color",
                                                                 Color.BLACK);
        myCanvas.setColor(color);

    }

}
