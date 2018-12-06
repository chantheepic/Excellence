package cs3500.animator.model;

import java.awt.*;

import cs3500.animator.provider.model.interfaces.Animation;
import cs3500.animator.provider.model.interfaces.Shape;

public class IMotionToAnimationAdapter implements Animation, IMotion {

  private IMotion adaptee;
  private Shape shapeToMutate;

  public IMotionToAnimationAdapter(IMotion adaptee, Shape shapeToMutate) {
    this.adaptee = adaptee;
    this.shapeToMutate = shapeToMutate;
  }


  /**
   * //TODO explain why we dont support
   * @return
   */
  @Override
  public String getShapeName() {
    throw new UnsupportedOperationException("");
  }

  /**
   * //TODO explain why we dont support
   * @return
   */
  @Override
  public Shape getShape() {
    return shapeToMutate;
  }

  @Override
  public int getStartTime() {
    return adaptee.initialTick();
  }

  @Override
  public int getEndTime() {
    return adaptee.endTick();
  }

  @Override
  public Animation copy() {
    throw new UnsupportedOperationException("");
  }

  @Override
  public String displayAnimation(int tps) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public String toSVG(int tempo) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public String rectangleSVG(int tempo) {
    throw new UnsupportedOperationException("");
  }

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
    shapeToMutate.setDimensions(state.width(),state.height());
  }

  @Override
  public void setStartTime(int start) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public void setEndTime(int end) {
    throw new UnsupportedOperationException("");
  }

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
