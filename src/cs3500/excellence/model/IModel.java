package cs3500.excellence.model;


/**
 * Extended model interface with setters. Represents a model for supporting animations.
 *
 * A Model is an animation. It has shapes that each might have motions. Each motion has a starting
 * point and ending pont.
 */
public interface IModel extends IROModel {

  /**
   * Adds a component to the model. The given id is used later on to refer to this component. All
   * components added will stored in alphabetical order.
   *
   * @param name - Represents the name of the component being added.
   * @param type - Represents the type of the component being added. So far rectangle and ellipse
   *             are supported.
   * @throws IllegalArgumentException when trying to add a component with ID that already exists. or
   *                                  when component is not one of the ones specified.
   */
  void addComponent(String name, String type) throws IllegalArgumentException;

  /**
   * Adds a motion to the specified component. Must be added in chronological order. Must also be
   * adjacent. For future projects, a factory could be used.
   *
   * @param name         - unique  for which component to add to.
   * @param initialState - Represents the starting state when tick = initialTick.
   * @param endState     - Represents the ending state when tick = endTick.
   * @param initialTick  - When motion begins
   * @param endTick      - When motion ends.
   * @throws IllegalArgumentException - When endTick < initialTick, either states are null, or does
   *                                  not exist, or the motion does not lineup.
   */
  void addMotion(String name, State initialState, State endState,
                 int initialTick, int endTick) throws IllegalArgumentException;

  /**
   * Removes a component from the model. If the name does not exist, error is thrown.
   */
  void removeComponent(String name) throws IllegalArgumentException;

  /**
   * Removes all components from the model.
   */
  void removeAllComponent();

  /**
   * Removes a motion from a component. If a component with matching name exists in the model,
   * remove the motion that contains the specified tick from the component. If the name does not
   * exist in the model, do nothing. If the tick does not exist in the matching component, throw an
   * error.
   *
   * @param name - the name of the component.
   * @param tick - the tick of the motion.
   */
  void removeMotion(String name, int tick) throws IllegalArgumentException;


  /**
   * Inserts a keyframe into the model. There are four cases:
   * 1. In between existing keyframes: splits the motion into two motions
   * 2. After existing keyframes: Adds motion with same initial state, after the last motion.
   * 3. Before existing keyframes: Adds motions with same ending state, before the first motions.
   * 4. No existing keyframes: creates a
   *
   *
   * It does this my splitting a motion into two. Or it adds a new motion
   * @param name
   * @param tick
   */
  void createKeyframe(String name, int tick, State newState) throws IllegalArgumentException;

  /**
   * Removes a keyframe the model. It does this by taking two motions and converting it into one.
   * @param name
   * @param tick
   */
  void removeKeyframe(String name, int tick);




}
