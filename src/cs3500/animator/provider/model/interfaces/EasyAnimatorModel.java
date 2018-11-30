package cs3500.animator.provider.model.interfaces;

import cs3500.animator.provider.model.classes.Bounds;

/**
 * This interface represents the operations offered by the animator
 * model. An animator model will consist of a list of shapes (shapes hold their animations).
 * This interface requires the implementation of methods that add and remove either of those
 * objects. It also allows the client to retrieve a copy of these animations and shapes.
 */
public interface EasyAnimatorModel extends EasyAnimatorModelReadOnly {

  /**
   * Add a single shape to the set of shapes currently in the animation.
   *
   * @param shape the shape to add to the set of shapes in the animation.
   * @throws IllegalArgumentException if the shape trying to be added is null or if there exists a
   *                                  shape of the same type and name in the model already.
   */
  void addShape(Shape shape) throws IllegalArgumentException;

  /**
   * Removes the given shape from the current list of supported shapes.
   *
   * @param shape the shape to be removed from the set of shapes in the animation
   * @throws IllegalArgumentException if the shape that is being attempted to be removed is null, or
   *                                  if the given shape is not in the current list of shapes.
   */
  void removeShape(Shape shape) throws IllegalArgumentException;

  /**
   * Allows a user to set the general speed of the animator model. This means the baseline speed of
   * how animations are executed.
   *
   * @param tempo the tempo the user wants the animator model to run at.
   */
  void setTempo(int tempo);

  /**
   * Specify the custom bounding box to be used for the animation. This means it is the "viewing"
   * window in which we can view the animations.
   *
   * @param bounds The Bounds object that represents the bounding box.
   */
  void setBounds(Bounds bounds);

}
