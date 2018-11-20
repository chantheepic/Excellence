package cs3500.animator.controller;

import cs3500.animator.view.IView;

/**
 * This interface represents a controller that can control an IModel and IView.
 */
public interface IController {

  /**
   * Sets up the controller to use a given view. It should hopefully start the view.
   * @param view
   */
  void setView (IView view);

}
