package cs3500.animator.view;

import cs3500.animator.controller.Features;
import cs3500.animator.model.Boundary;
import cs3500.animator.model.components.IROComponent;

import java.util.List;

/**
 * Represents all the view types supported.
 */
public interface IView {

  /**
   * Sets the list of components, the canvas size of the view, and view tick speed.
   * @param components specified components
   * @param speed specified speed
   */
  void setComponents(List<IROComponent> components, Boundary boundary, int speed);

  /**
   * Sets the output of the view.
   * @param app appendable output
   */
  void setOutput(Appendable app);

  /**
   * Displays the given tick.
   * @param currentTick - current tick to display
   */
  void tick(int currentTick);

  /**
   * Sets who to call back when a feature is requested.
   * @param listener listener given
   */
  void setFeatureListener(Features listener);

  /**
   * Displays a message in whatever form specified.
   * @param msg msgDisplayed
   */
  void displayError(String msg);
}
