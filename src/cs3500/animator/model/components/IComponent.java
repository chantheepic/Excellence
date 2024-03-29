package cs3500.animator.model.components;

import cs3500.animator.model.IMotion;
import cs3500.animator.model.State;


/**
 * Extended Interface with setter methods for Component Represents an animate-able object in the
 * animation. Each component knows its name(String), type(Shape), and {@code moves(List<IMotion>)}
 * Different components may have different shapes.
 */
public interface IComponent extends IROComponent {


  /**
   * Adds a motion to the component. Each motion must be added in chronological order and each end
   * tick must be adjacent the next start tick.
   *
   * @param motion - the given motion to add.
   * @throws IllegalArgumentException - if the motion is null, or does not line up. EX: [ tick = 1
   *          ... tick = 10 ] and [tick = 10 ... tick = 25 ]
   */
  void addMotion(IMotion motion) throws IllegalArgumentException;


  /**
   * Adds a keyframe to the component at a specific tick. The keyframe will either overwrite an
   * existing keyframe or create a new one. It does not throw any errors.
   *
   * @param tick - the specified tick
   * @param state - the specified state
   */
  void createKeyframe(int tick, State state);

  /**
   * Removes all motions from the component.
   */
  void removeAllMotion();

  /**
   * This method removes a keyframe from the specified tick.
   *
   * @param tick - the specified tick
   * @throws IllegalArgumentException - if the tick is not a keyframe it throws error
   */
  void removeKeyframe(int tick) throws IllegalArgumentException;

  /**
   * Sets the layer of the component to what is given.
   * @param layer - given layer [0, infinity)
   */
  void setLayer(int layer);

}
