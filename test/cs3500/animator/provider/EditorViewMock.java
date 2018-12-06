package cs3500.animator.provider;

import cs3500.animator.provider.controller.classes.CommandType;
import cs3500.animator.provider.view.interfaces.EditorView;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.IOException;
import javax.swing.event.ChangeListener;

public class EditorViewMock implements EditorView {

  private StringBuilder out = new StringBuilder();

  @Override
  public void acceptCommand(CommandType commandType, String shapeName, int tempo)
      throws IOException {
    // Suppress
  }

  @Override
  public void setListeners(ChangeListener changes, ItemListener items, ActionListener actions) {
    // Suppress
  }

  @Override
  public void display() {
    out.append("display on \n");
  }

  @Override
  public void refresh() {
    // Suppress
  }

  @Override
  public void setTempo(int tempo) {
    out.append("tempo set to " + tempo + "\n");
  }

  public String returnOutput() {
    return out.toString();
  }
}
