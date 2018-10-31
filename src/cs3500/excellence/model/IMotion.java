package cs3500.excellence.model;

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
   * Gets an overview of this motion. Formatted according to the writeup.
   *
   * @return - textual representation of the motion
   */
  String getOverview();

  /**
   * Checks of a specific tick falls between this motion.
   *
   * @param tick the given tick
   * @return true or false according to description
   */
  boolean containsTick(int tick);

  /**
   * Makes a copy of the IMotion with the same properties of Object.clone().
   *
   * @return - the copied IMotion
   */
  IMotion copy();

}
