package cs3500.excellence.model.components;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;


/**
 * Extended Interface with setter methods for Component Represents an animate-able object in the
 * animation. Each component knows its name(String), type(Shape), and moves(List<IMotion>) Different
 * components may have different shapes.
 */
public interface IComponent extends IROComponent {


  /**
   * Adds a motion to the component. Each motion must be added in chronological order and each end
   * tick must be adjacent the next start tick.
   *
   * @param motion - the given motion to add.
   * @throws IllegalArgumentException - if the motion is null, or does not line up. EX: [ tick = 1
   *                                  ... tick = 10 ] and [tick = 10 ... tick = 25 ]
   */
  void addMotion(IMotion motion) throws IllegalArgumentException;

  void insertKeyframe(int tick, State state);

  /**
   * Removes all motions from the component.
   */
  void removeAllMotion();

  void removeMotion(int index);

  void removeKeyframe(int tick);

}
