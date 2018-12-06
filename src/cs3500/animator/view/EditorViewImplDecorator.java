package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.animator.provider.controller.classes.CommandType;
import cs3500.animator.provider.view.interfaces.EditorView;

public class EditorViewImplDecorator implements EditorView, ActionListener, ItemListener {

  private EditorView view;
  class HandleChanges implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
      {

        //Lets hope there is only 1 slider...
        if (e.getSource() instanceof JSlider) {
          JSlider slider = (JSlider) e.getSource();
          try {
            EditorViewImplDecorator.this.view.acceptCommand(CommandType.SET_SPEED, null, slider.getValue());
          } catch (IOException exc) {
            exc.printStackTrace();
          }

        }
      }
    }
  }

  private HandleChanges changeListener = new HandleChanges();
  public EditorViewImplDecorator(EditorView view) {
    this.view = view;
    this.view.setListeners(this.changeListener,this,this);
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() instanceof JButton) {
      JButton button = (JButton) e.getSource();

      try {
        switch (button.getText()) {
          case "Play/Pause":
            this.view.acceptCommand(CommandType.PLAY_PAUSE, null, -1);
            break;
          case "Restart":
            this.view.acceptCommand(CommandType.RESTART, null, -1);
            break;
        }
      } catch (IOException exc) {
        exc.printStackTrace();
      }
    }
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    if (e.getSource() instanceof JCheckBox) {
      JCheckBox box = (JCheckBox) e.getSource();

      try {
        switch (box.getText()) {
          case "Toggle the loop":
            this.view.acceptCommand(CommandType.LOOP, null, -1);
            break;
        }
      } catch (IOException exc) {
        exc.printStackTrace();
      }
    }

  }


  @Override
  public void acceptCommand(CommandType commandType, String shapeName, int tempo) throws IOException {
    view.acceptCommand(commandType,shapeName,tempo);
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
}
