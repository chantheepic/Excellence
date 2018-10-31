package cs3500.excellence.model;

import java.util.List;
import java.util.Set;

import cs3500.excellence.model.components.IComponent;

/**
 * Represents a model for supporting animations.
 */
public interface IModel {

  /**
   * Adds a component to the model. The given id is used later on to refer to this component. All
   * components added will stored in alphabetical order.
   *
   * @param id        - represents a unique identifier for the component.
   * @param component - Represents the component being added.
   * @throws IllegalArgumentException when trying to add a component with ID that already exists. or
   *                                  when component is null.
   */
  void addComponent(String id, IComponent component) throws IllegalArgumentException;

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

  /**
   * Returns a copied list of components that are visible at a given tick.
   *
   * @param tick - the given tick.
   */
  List<IComponent> getComponentsAtTick(int tick);


  /**
   * Returns the last tick for any of the motions across all Components.
   *
   * @return ending tick.
   */
  int getFinalTick();


  /**
   * Gets a list of all Component IDs.
   */
  Set<String> getAllIds();

  /**
   * Gets the component based on a given ID. Cannot mutate component and have side effects reflected
   * in model.
   *
   * @param id - unique identifier to search for
   * @return Component if it exists
   * @throws IllegalArgumentException if id doesn't exist
   */
  IComponent getComponentByID(String id) throws IllegalArgumentException;

  /**
   * Outputs an overview of the animation. Components ordered in alphabetical order, and motions in
   * chronological order. Shows starting and ending states of each motion.
   *
   * @return - The textual description of the animation.
   */
  String getOverview();


}
