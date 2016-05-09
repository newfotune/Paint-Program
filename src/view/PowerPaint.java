/*
 * Nwoke Fortune Chiemeziem Winter 2015.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import listeners.ColorChooserListener;
import listeners.DrawingListener;
import listeners.ToolsAction;
import model.DrawingTool;
import model.EllipseTool;
import model.LineTool;
import model.PencilTool;
import model.RectangleTool;

/**
 * The Power paint front end GUI program.
 * 
 * @author n4tunec@uw.edu.
 * @version winter 2015.
 *
 */
public class PowerPaint implements PropertyChangeListener {

    /** The minor tick spacing for the thickness slider. */
    private static final int MINOR_TICK_SPACING = 1;

    /** The major tick spacing for the thickness slider. */
    private static final int MAJOR_TICK_SPACING = 5;

    /** A list of drawing tools actions. */
    private List<ToolsAction> myToolsActions;

    /** The main frame. */
    private final JFrame myFrame;

    boolean isEnaabled;
    // /** The undo all buttons action.*/
    // private AbstractAction myClearAction;

    /** The canvas panel to be drawn on. */
    private final CanvasPanel myPanel;

    /** The Slider for the shape thickness. */
    private JSlider myThicknessSlider;

    private Undo myUndo;

    private DrawingListener myListener;

    /**
     * This creates the frame and the panel.
     */
    public PowerPaint() {
        myFrame = new JFrame("TCSS 305 PowerPaint");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
        myFrame.setLocationRelativeTo(null);
        myFrame.setIconImage(new ImageIcon("./images/paintbrush.jpg").getImage());
        myListener = new DrawingListener();

        myPanel = new CanvasPanel();
        myPanel.setBackground(Color.WHITE);
        myPanel.addMouseListener(myListener);
        myPanel.addMouseMotionListener(myListener);
        myListener.addObserver(myPanel);

        myUndo = new Undo("Undo all changes");

        myFrame.add(myPanel);
        myPanel.addPropertyChangeListener(this);
        setUpActions();
        start();
    }

    /**
     * This sets up all the drawing tools actions.
     */
    private void setUpActions() {
        myToolsActions = new ArrayList<ToolsAction>();
        final ToolsAction pencilAction = new ToolsAction("Pencil",new ImageIcon(
                                                        "./images/Pencil_bw.gif"),
                                                        new PencilTool(), KeyEvent.VK_P);
        final ToolsAction LineAction = new ToolsAction("Line",
                                      new ImageIcon("./images/Line_bw.gif"),
                                       new LineTool(), KeyEvent.VK_L);
        
        final ToolsAction RectangleAction =new ToolsAction("Rectangle",
                                                        new ImageIcon(
                                                                      "./images/Rectangle_bw.gif"),
                                                        new RectangleTool(), KeyEvent.VK_R);
        final ToolsAction EllipseAction =new ToolsAction("Ellipse",new ImageIcon(
                                                    "./images/ellipse_bw.gif"),
                                                     new EllipseTool(), KeyEvent.VK_E);

        pencilAction.addPropertyChangeListener(this);
        LineAction.addPropertyChangeListener(this);
        RectangleAction.addPropertyChangeListener(this);
        EllipseAction.addPropertyChangeListener(this);

        myToolsActions.add(pencilAction);
        myToolsActions.add(LineAction);
        myToolsActions.add(RectangleAction);
        myToolsActions.add(EllipseAction);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("toolChange".equals(theEvent.getPropertyName())) {
            final DrawingTool tool = (DrawingTool) theEvent.getNewValue();
            myPanel.setMyTool(tool);
            myListener.setTool(tool);

        } else {
            myUndo.setEnabled(!(boolean) theEvent.getNewValue());
        }
    }

    /**
     * 
     */
    private void start() {
        final JMenuBar menuBar = createMenuBar();
        final JToolBar toolBar = createToolBar();

        myFrame.setJMenuBar(menuBar);
        myFrame.add(toolBar, BorderLayout.SOUTH);

        myFrame.pack();

    }

    /**
     * This creates the menu bar on the JFrame.
     * 
     * @return the created menuNBar.
     */

    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        // final MenuBarClass menuBar = new MenuBarClass();
        final JMenu fileMenu = createFileMenu();

        final JMenu optionMenu = createOptionsMenu();

        final JMenu toolsMenu = createToolsMenu();
        final JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        helpMenu.add(new About("About..."));

        final JMenuItem colorMenu = createColorMenuItem();

        menuBar.add(fileMenu);
        menuBar.add(optionMenu);
        menuBar.add(toolsMenu);
        menuBar.add(helpMenu);
        menuBar.add(colorMenu);

        return menuBar;
    }

    /**
     * This creates the color menu item menu bar.
     * 
     * @return the created menuItem.
     */
    private JMenuItem createColorMenuItem() {
        final JMenuItem colorMenuItem = new JMenuItem("Color...");

        colorMenuItem.addActionListener(new ColorChooserListener(myPanel));
        return colorMenuItem;
    }

    /**
     * This creates the tools menu on menu bar, and adds all the actions.
     * 
     * @return the created menu.
     */
    private JMenu createToolsMenu() {
        final JMenu tools = new JMenu("Tools");
        tools.setMnemonic('T');
        final ButtonGroup buttonGroup = new ButtonGroup();

        for (final ToolsAction actions : myToolsActions) {
            final JRadioButtonMenuItem button = new JRadioButtonMenuItem(actions);
            buttonGroup.add(button);
            tools.add(button);
        }
        return tools;
    }

    /**
     * This creates the file menu on menu bar.
     * 
     * @return the created menu.
     */
    private JMenu createFileMenu() {
        final JMenu file = new JMenu("File");
        file.setMnemonic('F');

        file.add(myUndo);

        file.addSeparator();
        file.add(new Exit("Exit"));

        file.add(new Save("Save..."));
        return file;
    }

    /**
     * This creates the options menu on the menu bar.
     * 
     * @return the created menu..
     */
    private JMenu createOptionsMenu() {

        final JMenu options = new JMenu("Options");
        options.setMnemonic('O');
        final JMenu thicknessOption = new JMenu("Thickness");
        thicknessOption.setMnemonic('T');

        final JCheckBoxMenuItem checkbox = new JCheckBoxMenuItem("Grid");
        checkbox.setMnemonic('G');
        checkbox.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                myPanel.setGridSelected(checkbox.isSelected());
                myPanel.repaint();
            }
        });

        options.add(checkbox);
        myThicknessSlider = createSlider();

        myThicknessSlider.addChangeListener(new ChangeListener() {
            /** Called in response to slider events in this window. */
            public void stateChanged(final ChangeEvent theEvent) {
                final float value = myThicknessSlider.getValue();
                myPanel.setThickness(value);
            }
        });

        thicknessOption.add(myThicknessSlider);
        options.add(thicknessOption);

        return options;
    }

    /**
     * This creates the slider on the thickness menu.
     * 
     * @return the created slider.
     */
    private JSlider createSlider() {
        final JSlider slider = new JSlider(0, 20, 1);

        slider.setMajorTickSpacing(MAJOR_TICK_SPACING);
        slider.setMinorTickSpacing(MINOR_TICK_SPACING);

        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        return slider;
    }

    /**
     * This creates the tool bar and adds all the actions.
     * 
     * @return the created toolbar.
     */
    private JToolBar createToolBar() {
        final JToolBar toolBar = new JToolBar();

        final ButtonGroup buttonGroup = new ButtonGroup();
        for (final ToolsAction actions : myToolsActions) {
            final JToggleButton button = new JToggleButton(actions);

            buttonGroup.add(button);
            toolBar.add(button);
        }

        return toolBar;
    }

    /**
     * This is the undo menuitem action listener.
     * 
     * @author n4tunec@uw.edu.
     * @version winter 2015.
     *
     */
    @SuppressWarnings("serial")
    private class Undo extends AbstractAction {

        /**
         * sets name of the action and sets it mnemonic value.
         * 
         * @param theName the name of the menuItem.
         */
        public Undo(final String theName) {
            super(theName);
            putValue(MNEMONIC_KEY, KeyEvent.VK_U);
        }

        /**
         * This clears the panel and repaints it.
         * 
         * @param theEvent the event thrown.
         */
        public void actionPerformed(final ActionEvent theEvent) {
            myPanel.removeAllDrawings();
        }

    }

    /**
     * This is the About menuitem action listener.
     * 
     * @author n4tunec@uw.edu.
     * @version winter 2015.
     *
     */
    @SuppressWarnings("serial")
    private class About extends AbstractAction {

        /**
         * sets name of the action and sets it mnemonic value.
         * 
         * @param theName the name of the menuItem.
         */
        public About(final String theName) {
            super(theName);
            putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        }

        /**
         * This pops up the About dialog.
         * 
         * @param theEvent the event thrown.
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {

            JOptionPane.showMessageDialog(null, "Nwoke Fortune Chiemeziem\n"
                                           + "Power Paint project TCSS 305 winter 2015\n"
                                           + "Icons were made at\n(https://www.iconfinder.com"
                                           + "/icons/197217/art_brush_meanicons_paint_icon)",
                                          "About", JOptionPane.INFORMATION_MESSAGE,
                                          new ImageIcon("./images/paintbrush_small.jpg"));

        }
    }

    /**
     * This is the Exit menuitem action listener.
     * 
     * @author n4tunec@uw.edu.
     * @version winter 2015.
     *
     */
    @SuppressWarnings("serial")
    private class Exit extends AbstractAction {

        /**
         * sets name of the action and sets it mnemonic value.
         * 
         * @param theName the name of the menuItem.
         */
        public Exit(final String theName) {
            super(theName);
            putValue(MNEMONIC_KEY, KeyEvent.VK_X);
        }

        /**
         * This closes down the JFrame.
         * 
         * @param theEvent the event thrown.
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myFrame.dispose();
        }
    }

    private class Save extends AbstractAction {

        private JFileChooser saveFile;

        public Save(final String theName) {
            super(theName);
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        }

        @Override
        public void actionPerformed(final ActionEvent arg0) {

            saveFile = new JFileChooser(".");
            saveFile.showSaveDialog(myFrame);
            // saveFile.setDialogTitle("Select folder to save");
            // System.out.println(saveFile.getDialogTitle());
            final BufferedImage image =
                                            new BufferedImage(myPanel.getWidth(),
                                                              myPanel.getHeight(),
                                                              BufferedImage.TYPE_INT_RGB);
            final Graphics2D graphics2D = image.createGraphics();

            myPanel.paint(graphics2D);
            try {
                // ImageIO.write(image,"png", new File(saveFile.getCurrentDirectory(),
                // "newFile.png"));
                ImageIO.write(image, "png", new File(".", "my Image.png"));

            } catch (final IOException ex) {

                ex.printStackTrace();
            }
        }

    }
}
