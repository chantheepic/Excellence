package cs3500.animator.provider.view.interfaces;

import cs3500.animator.provider.model.interfaces.Shape;

import java.awt.Graphics2D;

/**
 * To hold draw methods the view panel may need but is not available in JPanel.
 */
public interface IViewPanel {

  /**
   * To draw a shape on the graphics 2D object.
   * @param shape       the shape to be drawn
   * @param graphics2D  where to draw the shape
   */
  void drawShape(Shape shape, Graphics2D graphics2D);
}
