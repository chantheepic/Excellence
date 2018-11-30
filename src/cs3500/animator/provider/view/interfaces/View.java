package cs3500.animator.provider.view.interfaces;

import java.io.IOException;

/**
 * Represents a view for an Easy Animator Model. Views can be in the form of text, SVG, or a visual
 * representation. This interface allows for the client to display the view, refresh the view, and
 * also set the tempo of the view.
 */
public interface View {
  /**
   * Displays the view as to make it visible to the user.
   */
  void display() throws IOException;

  /**
   * Draws the view each time the method is called.
   */
  void refresh();

  /**
   * Sets the tempo (speed) of the view.
   *
   * @param tempo the speed of the view
   */
  void setTempo(int tempo);

}
