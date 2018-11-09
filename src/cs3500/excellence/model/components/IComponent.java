package cs3500.excellence.model.components;

import cs3500.excellence.model.IMotion;


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

  /**
   * Removes the index_th motion from the component.
   *
   * @param index - the given index of move to remove.
   */
  void removeMotion(int index);

  /**
   * Removes the entire motion that contains the given tick.
   *
   * @param tick - the given tick of move to remove.
   */
  void removeMotionAtTick(int tick);

  /**
   * Removes all motions from the component.
   */
  void removeAllMotion();
}
