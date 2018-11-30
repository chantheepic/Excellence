package cs3500.animator.provider.model.interfaces;

import cs3500.animator.provider.view.interfaces.IViewPanel;
import java.awt.Graphics2D;

/**
 * This interface holds the drawing operations upon shapes.
 */
public interface DrawableShape {

  /**
   * Generates the SVG representation of the drawable shape.
   *
   * @return the SVG representation
   */
  String toSVG(int tempo, boolean isLooping);

  /**
   * Generates the proper SVG representation of the shape which includes any needed SVG tags.
   *
   * @return the proper SVG representation
   */
  String generateProperSVG(Animation a, int tempo);

  /**
   * Draws the shape.
   *
   * @param viewPanel the given view panel to draw this shape on.
   * @param graphics2D  the given graphics2D object to draw this shape with.
   */
  void drawShape(IViewPanel viewPanel, Graphics2D graphics2D);
}
