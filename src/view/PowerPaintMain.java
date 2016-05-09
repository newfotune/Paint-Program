/*
 * Nwoke Fortune Chiemeziem
 * Winter 2015.
 */
package view;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This is my utility class.
 * @author n4tunec@uw.edu.
 * @version winter 2015.
 *
 */
public final class PowerPaintMain {

    /**Unusable constructor. */
    private PowerPaintMain() {
      
    }
    /**
     * The main method kick starts the GUI.
     * @param theArgs This is the main arguments array.
     */
    public static void main(final String[] theArgs) {
        final UIManager ui = new UIManager();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       // ui.setLookAndFeel(newLookAndFeel);
        new PowerPaint();
  
    }

}
