package cs3500.excellence.model.components;

import java.util.ArrayList;
import java.util.List;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;

/**
 * Read Only interface for Component
 * Represents an animate-able object in the animation.
 * Each component knows its name(String), type(Shape), and moves(List<IMotion>)
 * Different components may have different shapes.
 */
public interface IROComponent {

  /**
   * Returns all motions made by the component.
   * @return An array that contains all motions made by the component.
   */
  ArrayList<IMotion> returnAllMotions();
  List<Keyframe> returnAllKeyframes();


  Shape getShape();

  /**
   * Gets the calculated state at a specific tick.
   *
   * @param tick - the desired tick
   * @return the State representing the different parameters.
   */
  State getStateAtTick(int tick);

  /**
   * Whether or not the component has a motion defined at a specific tick.
   *
   * @param tick - the desired tick.
   * @return true or false depending on description.
   */
  boolean hasMotionAtTick(int tick);

  boolean hasKeyframeAtTick(int tick);

  /**
   * Gets the ending tick of the last motion that the component has.
   *
   * @return the tick
   */
  int getFinalTick();


  /**
   * Does this component contain any motions?
   * @return true if component contains motions
   */
  boolean hasMotion();

  /**
   * Gets the name of the component.
   * @return name of the component
   */
  String getID();
}
