package cs3500.excellence.model;


/**
 * Represents a model for supporting animations.
 */
public interface IModel extends IROModel {

  /**
   * Adds a component to the model. The given id is used later on to refer to this component. All
   * components added will stored in alphabetical order.
   *
<<<<<<< HEAD
   * @param name - Represents the name of the component being added.
   * @param type - Represents the type of the component being added. So far rectangle and ellipse
   *             are supported.
=======
   * @param name - Represents the component being added.
   * @param type - Represents the component being added.
>>>>>>> Remove methods added.
   * @throws IllegalArgumentException when trying to add a component with ID that already exists. or
   *                                  when component is not one of the ones specified.
   */
  void addComponent(String name, String type) throws IllegalArgumentException;

  void removeComponent(String name);

  /**
   * Adds a motion to the specified component. Must be added in chronological order. Must also be
   * adjacent. For future projects, a factory could be used.
   *
   * @param name         - unique  for which component to add to.
   * @param initialState - Represents the starting state when tick = initialTick.
   * @param endState     - Represents the ending state when tick = endTick.
   * @param initialTick  - When motion begins
   * @param endTick      - When motion ends.
   * @throws IllegalArgumentException - When endTick < initialTick, either states are null, or
   *                                  does not exist, or the motion does not lineup.
   */
  void addMotion(String name, State initialState, State endState,
                 int initialTick, int endTick) throws IllegalArgumentException;

  /**
   * Removes a component from the model. If the name does not exist, nothing happens.
   * @param name
   */
  void removeComponent(String name);

  /**
   * Removes a motion from a component. If the name does
   * @param name - the name of the component.
   * @param initialTick - the inital tick of the motion.
   * @param endTick - the ending tick of the motion.
   */
  void removeMotion(String name, int initialTick, int endTick);


}
