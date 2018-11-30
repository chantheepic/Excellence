package cs3500.animator.provider.model.interfaces;

/**
 * Read only methods on Bounds. This interface allows users of the Bounds class to view its
 * data but not mutate any of it.
 */
public interface BoundsReadOnly {

  /**
   * Gets the x value.
   *
   * @return the x value
   */
  int getX();

  /**
   * Gets the y value.
   *
   * @return the y value
   */
  int getY();

  /**
   * Gets the width value.
   *
   * @return the width value
   */
  int getWidth();

  /**
   * Gets the height value.
   *
   * @return the height value
   */
  int getHeight();

  /**
   * Creates a string representation describing the bounds.
   *
   * @return the textual description of the bounds.
   */
  String toString();
}
