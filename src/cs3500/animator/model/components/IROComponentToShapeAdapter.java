package cs3500.animator.model.components;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.IMotion;
import cs3500.animator.model.IMotionToAnimationAdapter;
import cs3500.animator.model.State;
import cs3500.animator.provider.model.classes.ShapeType;
import cs3500.animator.provider.model.interfaces.Animation;
import cs3500.animator.provider.model.interfaces.Shape;
import cs3500.animator.provider.view.interfaces.IViewPanel;


/**
 * This class adapts a IROComponent to a Shape. It holds both a immutable state and a IROComponent.
 * addAnimation not usable.
 */
public class IROComponentToShapeAdapter implements Shape {

  private IROComponent adaptee;
  private State currentState;

  /**
   * Constructs an IROComponentToShapeAdapter. Sets the initial state to the first state of the
   * component.
   *
   * @param adaptee - the IROComponent to adapt.
   */
  public IROComponentToShapeAdapter(IROComponent adaptee) {
    this.adaptee = adaptee;
    this.currentState = this.adaptee.getStateAtTick(getStartTime());
  }

  @Override
  public String getName() {
    return adaptee.getID();
  }

  @Override
  public int getX() {
    return currentState.xPos();
  }

  @Override
  public void setX(int x) {
    this.currentState = new State(
            x,
            currentState.yPos(),
            currentState.width(),
            currentState.height(),
            currentState.red(),
            currentState.green(),
            currentState.blue());

  }

  @Override
  public int getY() {
    return currentState.yPos();
  }

  @Override
  public void setY(int y) {
    this.currentState = new State(
            currentState.xPos(),
            y,
            currentState.width(),
            currentState.height(),
            currentState.red(),
            currentState.green(),
            currentState.blue());
  }

  @Override
  public int getFirstDimension() {
    return currentState.width();
  }

  @Override
  public int getSecondDimension() {
    return currentState.height();
  }

  @Override
  public Color getColor() {
    return new Color(currentState.red(), currentState.green(), currentState.blue());
  }

  @Override
  public void setColor(Color color) {
    this.currentState = new State(
            currentState.xPos(),
            currentState.yPos(),
            currentState.width(),
            currentState.height(),
            color.getRed(),
            color.getGreen(),
            color.getBlue());
  }

  /**
   * Gets the list animations by adapting the IROComponent's motions to animations.
   *
   * @return list of Animations
   */
  @Override
  public List<Animation> getAnimations() {
    List<Animation> output = new ArrayList<>();

    for (IMotion motion : adaptee.returnAllMotions()) {
      output.add(new IMotionToAnimationAdapter(motion, this));
    }
    return output;
  }

  /**
   * This is unsupported because the view should not be able to mutate the model. The provider code
   * did not include a read only version of shape.
   *
   * @param animation the animation to be added
   */
  @Override
  public void addAnimation(Animation animation) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setDimensions(int firstDimension, int secondDimension) {
    this.currentState = new State(
            currentState.xPos(),
            currentState.yPos(),
            firstDimension,
            secondDimension,
            currentState.red(),
            currentState.green(),
            currentState.blue());
  }

  @Override
  public Shape copy() {
    return new IROComponentToShapeAdapter(adaptee);
  }

  /**
   * Should not tightly couple the model with the view.
   */
  @Override
  public String toSVG(int tempo, boolean isLooping) {
    throw new UnsupportedOperationException();
  }

  /**
   * Should not tightly couple the model with the view.
   *
   * @param a     animation of shape.
   * @param tempo speed of SVG.
   */
  @Override
  public String generateProperSVG(Animation a, int tempo) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void drawShape(IViewPanel p, Graphics2D graphics2D) {
    p.drawShape(this, graphics2D);
  }

  @Override
  public int getStartTime() {
    return adaptee.returnAllKeyframes().get(0).getTick();
  }

  @Override
  public int getEndTime() {
    return adaptee.getFinalTick();
  }

  @Override
  public ShapeType getType() {

    switch (adaptee.getShape()) {
      case ELLIPSE:
        return ShapeType.ELLIPSE;
      case RECTANGLE:
        return ShapeType.RECTANGLE;
      default:
        throw new IllegalStateException("Should never get here");
    }


  }


}
