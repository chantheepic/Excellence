package cs3500.animator.provider.view.interfaces;

import cs3500.animator.provider.controller.classes.CommandType;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.event.ChangeListener;

/**
 * An editor view interface that requires the implementations to accept commands and set listeners.
 */
public interface EditorView extends View {

  /**
   * Accepts a command given by the controller and then delegates the command to
   * the appropriate method to handle the user input.
   * @param commandType The type of command that the user has issued.
   * @param shapeName Shape whos active state will be toggled if commandType is SELECT
   */

  void acceptCommand(CommandType commandType, String shapeName, int tempo) throws IOException;

  /**
   * This method is used to set up the listeners in the controller and associate them with
   * different swing elements. Based on code from eventHandler lecture.
   */
  void setListeners(ChangeListener changes,
                    ItemListener items, ActionListener actions, KeyListener keys);
}
