package cs3500.animator.model;

import cs3500.animator.provider.model.interfaces.Animation;
import cs3500.animator.provider.model.interfaces.Shape;
import java.awt.Color;

/**
 * This class adapts an IMotion to an Animation. Since the animation needs to mutate a Shape, it
 * also includes a Shape. It delegates all IMotion methods to the composed IMotion.
 */
public class IMotionToAnimationAdapter implements Animation, IMotion {

  private IMotion adaptee;
  private Shape shapeToMutate;

  /**
   * Constructs an IMotionToAnimationAdapter with the IMotion and Shape.
   *
   * @param adaptee - given IMotion
   * @param shapeToMutate give Shape
   */
  public IMotionToAnimationAdapter(IMotion adaptee, Shape shapeToMutate) {
    this.adaptee = adaptee;
    this.shapeToMutate = shapeToMutate;
  }

  @Override
  public String getShapeName() {
    return shapeToMutate.getName();
  }


  @Override
  public Shape getShape() {
    return shapeToMutate;
  }

  @Override
  public int getStartTime() {
    return adaptee.initialTick();
  }

  /**
   * We do not support this because we disabled any methods that mutate it.
   */
  @Override
  public void setStartTime(int start) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public int getEndTime() {
    return adaptee.endTick();
  }

  /**
   * We do not support this because we disabled any methods that mutate it.
   */
  @Override
  public void setEndTime(int end) {
    throw new UnsupportedOperationException("");
  }

  /**
   * We do not support this because we disabled any methods that mutate it.
   */
  @Override
  public Animation copy() {
    throw new UnsupportedOperationException("");
  }

  /**
   * We do not support this because it tightly couples the view and model.
   */
  @Override
  public String displayAnimation(int tps) {
    throw new UnsupportedOperationException("");
  }

  /**
   * We do not support this because it tightly couples the view and model.
   */
  @Override
  public String toSVG(int tempo) {
    throw new UnsupportedOperationException("");
  }

  /**
   * We do not support this because it tightly couples the view and model.
   */
  @Override
  public String rectangleSVG(int tempo) {
    throw new UnsupportedOperationException("");
  }

  /**
   * We do not support this because it tightly couples the view and model.
   */
  @Override
  public String ellipseSVG(int tempo) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public void apply(int tick) {
    State state = adaptee.getStateAtTick(tick);
    shapeToMutate.setColor(new Color(state.red(), state.green(), state.blue()));
    shapeToMutate.setX(state.xPos());
    shapeToMutate.setY(state.yPos());
    shapeToMutate.setDimensions(state.width(), state.height());
  }

  /**
   * We do not support this because we do not know what it should be compared using.
   */
  @Override
  public int compareTo(Object o) {
    return 0;
  }

  @Override
  public State getStateAtTick(int tick) {
    return adaptee.getStateAtTick(tick);
  }

  @Override
  public int initialTick() {
    return adaptee.initialTick();
  }

  @Override
  public int endTick() {
    return adaptee.endTick();
  }

  @Override
  public State initialState() {
    return adaptee.initialState();
  }

  @Override
  public State endState() {
    return adaptee.endState();
  }

  @Override
  public boolean containsTick(int tick) {
    return adaptee.containsTick(tick);
  }
}
