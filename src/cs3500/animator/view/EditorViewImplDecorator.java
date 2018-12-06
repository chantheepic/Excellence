package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.animator.provider.controller.classes.CommandType;
import cs3500.animator.provider.view.interfaces.EditorView;

/**
 * This class decorates the EditorView with change, item and action listeners. Each listener is a
 * separate class. It delegates methods to the composed EditorView.
 */
public class EditorViewImplDecorator implements EditorView {

  private EditorView view;

  private HandleChanges changeListener = new HandleChanges();
  private HandleItems itemListener = new HandleItems();
  private HandleActions actionListener = new HandleActions();


  /**
   * This constructor takes in a view and sets its listeners to the listeners defined in this file.
   */
  public EditorViewImplDecorator(EditorView view) {
    this.view = view;
    this.view.setListeners(changeListener, itemListener, actionListener);
  }


  @Override
  public void acceptCommand(CommandType commandType, String shapeName, int tempo)
      throws IOException {
    view.acceptCommand(commandType, shapeName, tempo);
  }

  @Override
  public void setListeners(ChangeListener changes, ItemListener items, ActionListener actions) {
    view.setListeners(changes, items, actions);
  }

  @Override
  public void display() throws IOException {
    view.display();
  }

  @Override
  public void refresh() {
    view.refresh();
  }

  @Override
  public void setTempo(int tempo) {
    view.setTempo(tempo);
  }

  /**
   * This class is used to handle stateChanges from the EditorView.
   */
  class HandleChanges implements ChangeListener {

    @Override
    public void stateChanged(ChangeEvent e) {
      {

        //Lets hope there is only 1 slider...
        if (e.getSource() instanceof JSlider) {
          JSlider slider = (JSlider) e.getSource();
          try {
            EditorViewImplDecorator.this.view
                .acceptCommand(CommandType.SET_SPEED, null, slider.getValue());
          } catch (IOException exc) {
            exc.printStackTrace();
          }

        }
      }
    }
  }

  /**
   * This class is used to handle itemStateChanges from the EditorView.
   */
  class HandleItems implements ItemListener {

    @Override
    public void itemStateChanged(ItemEvent e) {
      if (e.getSource() instanceof JCheckBox) {
        JCheckBox box = (JCheckBox) e.getSource();

        try {
          if (box.getText().equals("Toggle the loop")) {
            EditorViewImplDecorator.this.view.acceptCommand(CommandType.LOOP, null, -1);
          }
        } catch (IOException exc) {
          exc.printStackTrace();
        }
      }
    }
  }

  /**
   * This class is used to handle actions from the EditorView.
   */
  class HandleActions implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() instanceof JButton) {
        JButton button = (JButton) e.getSource();

        try {
          if (button.getText().equals("Play/Pause")) {
            EditorViewImplDecorator.this.view.acceptCommand(CommandType.PLAY_PAUSE, null, -1);
          }
          if (button.getText().equals("Restart")) {
            EditorViewImplDecorator.this.view.acceptCommand(CommandType.RESTART, null, -1);
          }
        } catch (IOException exc) {
          exc.printStackTrace();
        }
      }
    }
  }

}
