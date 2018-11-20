package cs3500.animator.model.components;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.IMotion;
import cs3500.animator.model.State;

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

  /**
   * Gets a list of all of the keyframes for the component. Keyframe is final, so
   * it cannot be mutated.
   * @return - list of Keyframes for component.
   */
  List<Keyframe> returnAllKeyframes();

  /**
   * Gets the shape information for a component.
   * @return - the shape enum
   */
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

  /**
   * Determines if the component has a keyframe a specific tick.
   * @param tick - the specufuc tick
   * @return - true if it exists, false if it does not.
   */
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
