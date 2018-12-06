package cs3500.animator.model;

import java.util.List;
import java.util.Set;

import cs3500.animator.model.components.IROComponent;

/**
 * Read Only model interface with getters.
 * Represents a model for supporting animations.
 */
public interface IROModel {

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
  IROComponent getComponentByID(String id) throws IllegalArgumentException;

  /**
   * Gets all of the components in the model. The components are read only.
   * @return - list of components in the model
   */
  List<IROComponent> getAllComponents();

  /**
   * Gets the boundary information from the model.
   * @return - the boundray information.
   */
  Boundary getBoundary();

  /**
   * Gets the entire list of of keyframes. A Keyframe is completely immutable and the list should be
   * a duplicate one.
   * @return
   */
  List<State> getKeyframes();

  /**
   * Gets the final tick in the animation.
   * @return - the last tick of any component.
   */
  int getFinalTick();

}
