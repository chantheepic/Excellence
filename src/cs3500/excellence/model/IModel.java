package cs3500.excellence.model;


/**
 * Represents a model for supporting animations.
 */
public interface IModel extends IROModel {

  /**
   * Adds a component to the model. The given id is used later on to refer to this component. All
   * components added will stored in alphabetical order.
   *
   * @param component - Represents the component being added.
   * @throws IllegalArgumentException when trying to add a component with ID that already exists. or
   *                                  when component is null.
   */
  void addComponent(String name, String type) throws IllegalArgumentException;

  /**
   * Adds a motion to the specified component. Must be added in chronological order. Must also be
   * adjacent. For future projects, a factory could be used.
   *
   * @param id           - unique id for which component to add to.
   * @param initialState - Represents the starting state when tick = initialTick.
   * @param endState     - Represents the ending state when tick = endTick.
   * @param initialTick  - When motion begins
   * @param endTick      - When motion ends.
   * @throws IllegalArgumentException - When endTick < initialTick, either states are null, or id
   *                                  does not exist, or the motion does not lineup.
   */
  void addMotion(String id, State initialState, State endState,
                 int initialTick, int endTick) throws IllegalArgumentException;


}
