package cs3500.animator.provider.model.interfaces;

import cs3500.animator.provider.model.classes.Bounds;
import java.util.List;

/**
 * Read only methods on EasyAnimatorModelImpl.
 */
public interface EasyAnimatorModelReadOnly {

  /**
   * Gets the tempo of the animation.
   *
   * @return the tempo of the animation
   */
  int getTempo();

  /**
   * Gets the set of shapes currently in the animation.
   *
   * @return the set of shapes currently in the animation.
   */
  List<Shape> getShapes();

  /**
   * Gets the bounding box to be used for animations. This states our "view box" or from what we
   * window of sight we are seeing animations.
   *
   * @return the bounding box to be used for animations.
   */
  Bounds getBounds();

}
