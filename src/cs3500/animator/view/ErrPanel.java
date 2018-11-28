package cs3500.animator.view;

import javax.swing.*;

/**
 * Static class that creates a error popup when parsing has failed in the main method.
 */
public final class ErrPanel {

  public static void error(String msg) {
    JOptionPane optionPane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);
    JDialog dialog = optionPane.createDialog("Error!");
    dialog.setAlwaysOnTop(true);
    dialog.setVisible(true);
  }
}