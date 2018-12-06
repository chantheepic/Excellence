package cs3500.animator.view;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Static class that creates a error popup when parsing has failed in the main method.
 */
public final class ErrPanel {

  /**
   * Method creates a error popup with specified error message.
   * @param msg specified error message
   */
  public static void error(String msg) {
    JOptionPane optionPane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);
    JDialog dialog = optionPane.createDialog("Error!");
    dialog.setAlwaysOnTop(true);
    dialog.setVisible(true);
  }
}