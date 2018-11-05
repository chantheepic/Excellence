package cs3500.excellence.view;

import cs3500.excellence.model.components.IComponent;
import java.util.List;

public interface IView {

  /**
   * Draws the given components at a given tick.
   * @param tick
   */
  void drawTick(List<IComponent> components, int tick);

  /**
   * Sets the list of components.
   * @param components
   */
  void setComponents(List<IComponent> components);

}
