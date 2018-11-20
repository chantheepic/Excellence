package cs3500.excellence.view;

import cs3500.excellence.model.Boundary;
import cs3500.excellence.model.components.IROComponent;

import java.util.List;

/**
 * Represents all the view types supported.
 */
public interface IView {

  /**
   * Sets the list of components, the canvas size of the view, and view tick speed.
   * @param components
   * @param speed
   */
  void setComponents(List<IROComponent> components, Boundary boundary, int speed);

  /**
   * Sets the output of the view.
   * @param app
   */
  void setOutput(Appendable app);


  void setEditListener(IEditListener listener);

  void tick(int currentTick);
}
