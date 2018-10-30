package cs3500.excellence.model.hw05.components;

import cs3500.excellence.model.hw05.IMotion;
import cs3500.excellence.model.hw05.State;

public interface IComponent {


  /**
   * Adds a motion to the component. Must be added in chronological order and must be adjacent
   * end -> start tick.
   * @param motion - the given motion.
   * @throws IllegalArgumentException- if the motion is null, or does not line up.
   * EX: [ tick = 1 ... tick = 10 ] and [t = 10 ... tick = 25 ]
   */
  void addMotion(IMotion motion) throws IllegalArgumentException;


  /**
   * Gets the calculated state at a specific tick.
   * @param tick - the desired tick
   * @return the State representing the different parameters.
   */
  State getStateAtTick(int tick);


  /**
   * Gets overview of the component between start and end tick. Is in chronological order
   * @return - the textual representation of the component's motions.
   */
  String getOverview(String id);

  /**
   * Whether or not the component has a motion defined at a specific tick.
   * @param tick - the desired tick.
   * @return true or false depending on description.
   */
  boolean hasMotionAtTick(int tick);

  /**
   * Gets the ending tick of the last motion that the component has.
   * @return the tick
   */
  int getFinalTick();

  /**
   * Makes a copy of the IComponent, specifically follows the specifications of Object.clone()
   * @return - copied IComponent
   */
  IComponent copy();

}
