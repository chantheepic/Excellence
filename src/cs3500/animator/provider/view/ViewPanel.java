package cs3500.animator.provider.view;

import cs3500.animator.provider.model.classes.ShapeType;
import cs3500.animator.provider.model.interfaces.Animation;
import cs3500.animator.provider.view.interfaces.IViewPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import javax.swing.JPanel;

import cs3500.animator.provider.model.interfaces.Shape;

/**
 * This class represents a view panel that will be used by a view frame. This panel holds all of the
 * methods required to draw all the visual implementations of shapes and their animations. It holds
 * a list of shapes (which contains their respective animations) and a private tick value that
 * determines the speed of the animation.
 */
public class ViewPanel extends JPanel implements IViewPanel {

  private List<Shape> shapes;
  private List<Shape> initialShapesState;
  private Map<Shape,Boolean> visibleShapes;
  private int tick;
  private int maxTick;

  /**
   * Constructs a view panel for a visual view frame by taking in a list of shapes and a list of
   * animations associated with those shapes.
   */
  public ViewPanel(List<Shape> shapes) throws IllegalArgumentException {
    super();

    if (shapes == null) {
      throw new IllegalArgumentException("Can not have null shapes.");
    }

    this.shapes = shapes;
    saveInitialStateOfShapes();
    this.setBackground(Color.WHITE);
    tick = 0;
    maxTick = 0;
    for (Shape s: shapes) {
      if (s.getEndTime() > maxTick) {
        maxTick = s.getEndTime();
      }
    }

    visibleShapes = new HashMap<>();
    for (Shape s : shapes) {
      visibleShapes.put(s,true);
    }
  }

  /**
   * Overrides the paintComponent from the JPanel class in order to draw our 2D shapes.
   *
   * @param graphic represents the graphic to be drawn
   * @throws IllegalArgumentException if the graphic is null
   */
  @Override
  protected void paintComponent(Graphics graphic) throws IllegalArgumentException {
    if (graphic == null) {
      throw new IllegalArgumentException("Graphics object cannot be null.");
    }
    super.paintComponent(graphic);

    // Cast so that we can use 2D properties
    Graphics2D graphic2D = (Graphics2D) graphic;

    graphic2D.setColor(Color.BLACK);

    // Obtains all the properties of the shape to be animated, and animates the shape.
    for (Shape s : shapes) {
      if (visibleShapes.get(s)) {
        graphic.setColor(s.getColor());

        if (tick >= s.getStartTime() && tick <= s.getEndTime()) {
          s.drawShape(this, graphic2D);
        }
      }
    }
  }

  private void updateState(Shape shape, Animation animation) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null");
    }
    if (animation == null) {
      throw new IllegalArgumentException("Animation cannot be null");
    }

    animation.apply(tick);
  }

  /**
   * Draws a shape in its proper location on the panel.
   *
   * @param shape         shape to be drawn
   * @param graphics2D    where to draw the shape
   * @throws IllegalArgumentException if the shape or graphics object is null
   */
  public void drawShape(Shape shape, Graphics2D graphics2D) throws IllegalArgumentException {
    if ((shape == null) || (graphics2D == null)) {
      throw new IllegalArgumentException("Null input");
    }
    if (shape.getType() == ShapeType.ELLIPSE) {
      graphics2D.fillOval(shape.getX(), shape.getY(), shape.getFirstDimension(),
              shape.getSecondDimension());
    }
    if (shape.getType() == ShapeType.RECTANGLE) {
      graphics2D.fillRect(shape.getX(), shape.getY(), shape.getFirstDimension(),
              shape.getSecondDimension());
    }
  }

  /**
   * Mutates the shapes to their new positions to draw the next frame.
   */
  public void animateShapes() {
    for (Shape s: shapes) {
      List<Animation> animations = s.getAnimations();
      for (Animation a: animations) {
        if (a.getStartTime() <= tick && tick <= a.getEndTime()) {
          updateState(s, a);
        }
      }
    }
    tick++;
  }

  public void restart() {
    tick = 0;
    resetInitialState();
  }

  private void saveInitialStateOfShapes() {
    initialShapesState = new ArrayList<>();
    for (Shape s: shapes) {
      initialShapesState.add(s.copy());
    }
  }

  private void resetInitialState() {
    for (Shape shapeToReset: shapes) {
      for (Shape shapeInitialState: initialShapesState) {
        shapeToReset.setX(shapeInitialState.getX());
        shapeToReset.setY(shapeInitialState.getY());
        shapeToReset.setDimensions(shapeInitialState.getFirstDimension(),
                shapeInitialState.getSecondDimension());
        shapeToReset.setColor(shapeInitialState.getColor());
      }
    }
  }


  /**
   * Gets the max tick of this view panel.
   *
   * @return the max tick
   */
  public int getMaxTick() {
    return this.maxTick;
  }

  /**
   * Gets the tick of this view panel.
   *
   * @return the tick
   */
  public int getTick() {
    return this.tick;
  }

}