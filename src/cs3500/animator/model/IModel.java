package cs3500.animator.model;


/**
 * Extended model interface with setters. Represents a model for supporting animations. A Model is
 * an animation. It has shapes that each might have keyframes. Each keyframe represents the state of
 * a component at a specific tick.
 */
public interface IModel extends IROModel {

  /**
   * Adds a component to the model. The given id is used later on to refer to this component. All
   * components added will stored in the order they are added.
   *
   * @param name - Represents the name of the component being added.
   * @param type - Represents the type of the component being added. So far rectangle and ellipse
   *         are supported.
   * @throws IllegalArgumentException when trying to add a component with ID that already exists. or
   *          when component is not one of the ones specified.
   */
  void addComponent(String name, String type) throws IllegalArgumentException;

  /**
   * Adds a motion to the specified component. Must be added in chronological order. Must also be
   * adjacent. For future projects, a factory could be used.
   *
   * @param name - unique  for which component to add to.
   * @param initialState - Represents the starting state when tick = initialTick.
   * @param endState - Represents the ending state when tick = endTick.
   * @param initialTick - When motion begins
   * @param endTick - When motion ends.
   * @throws IllegalArgumentException - When endTick < initialTick, either states are null, or does
   *          not exist, or the motion does not lineup.
   */
  void addMotion(String name, State initialState, State endState,
      int initialTick, int endTick) throws IllegalArgumentException;

  /**
   * Removes a component from the model. If the name does not exist, error is thrown.
   *
   * @param name - the name of the Shape
   * @throws IllegalArgumentException - if shape does not exist.
   */
  void removeComponent(String name) throws IllegalArgumentException;

  /**
   * Removes all components from the model.
   */
  void removeAllComponent();


  /**
   * Inserts a keyframe into the model. It can override existing keyframes with the same tick.
   *
   * @param name - the name of the shape.
   * @param tick - the tick of the keyframe
   * @param newState - the state at the given tick.
   * @throws IllegalArgumentException - this thrown when the name does not exist.
   */
  void createKeyframe(String name, int tick, State newState) throws IllegalArgumentException;

  /**
   * Removes a keyframe the model. If the name doesn't exist or the tick, it throws error.
   *
   * @param name - the name of the shape.
   * @param tick - the tick of the specified keyframe.
   * @throws IllegalArgumentException - throws error if keyframe does not exist.
   */
  void removeKeyframe(String name, int tick) throws IllegalArgumentException;

}
