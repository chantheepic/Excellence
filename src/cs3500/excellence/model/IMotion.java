package cs3500.excellence.model;

/**
 * Represents the motions that model can utilize.
 * Each motion knows its beginning and end state and time
 */
public interface IMotion {
  /**
   * Gets the State of an IMotion at a specified tick.
   *
   * @param tick - the specified tick
   * @return a State object which holds the parameters required for a component
   */
  State getStateAtTick(int tick);

  /**
   * Gets the starting tick of the motion.
   */
  int initialTick();

  /**
   * Gets the ending tick of the motion.
   */
  int endTick();

  /**
   * Gets the inital state of the motion.
   * @return
   */
  State initialState();

  /**
   * Gets the end state of the motion.
   * @return
   */
  State endState();

  /**
   * Checks of a specific tick falls between this motion.
   *
   * @param tick the given tick
   * @return true or false according to description
   */
  boolean containsTick(int tick);

}
