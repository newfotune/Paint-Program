/*
 * Nwoke Fortune Chiemeziem
 * Winter 2015.
 */
package listeners;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

import model.DrawingTool;

/**
 * This is the action class that controls all drawing actions.
 * @author n4tunec@uw.edu.
 * @version winter 2015.
 *
 */
@SuppressWarnings("serial")
public class ToolsAction extends AbstractAction {

    /** The name of my action. */
    private final String myActionName;

    /** My Drawing tool. */
    private final DrawingTool myDrawingTool;
    
 
    /**
     * Constructs an action with the specified name and icon to set the panel to the specified
     * color.
     * @param theName The name this is the name of my action.
     * @param theIcon The icon this is my actions Icon.
     * @param theTool this is my drawing tool.
     * @param theMnemonic this is the mnemonic of the action.
     */
    public ToolsAction(final String theName, final Icon theIcon,
                       final DrawingTool theTool, final int theMnemonic) {

        super(theName, theIcon);
    
        myActionName = theName;
        myDrawingTool = theTool;
     //   myCurrentDrawing = myDrawingTool.getShape();
       
        putValue(Action.SELECTED_KEY, true);
        putValue(MNEMONIC_KEY, theMnemonic);
       
   
    }

    /**
     * choose the specified drawing tool. 
     * @param theEvent The event, ignored.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        firePropertyChange("toolChange", null, myDrawingTool); //sends to powerpaint class
        //myCanvas.setMyTool(myDrawingTool);
    }

    /**
     * This returns the current drawing tool.
     * @return the drawing tool
     */
    public DrawingTool getTool() {
        return myDrawingTool;
    }

    /**
     * This returns the name of the action.
     * @return the name of the action.
     */
    public String toString() {
        return myActionName;
    }
}
