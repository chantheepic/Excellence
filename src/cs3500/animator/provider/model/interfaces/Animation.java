package cs3500.animator.provider.model.interfaces;

/**
 * This interface represents the operations offered by the animator
 * model. One object of the model represents one set of animations and shapes.
 */
public interface Animation extends Comparable {

  /**
   * Creates a string representation describing the animation.
   * @return the textual output of the animation.
   */
  String toString();

  /**
   * Returns the shape name.
   * @return the name of the shape being manipulated by the animation.
   */
  String getShapeName();

  /**
   * Gets the shape that is being manipulated by this object.
   * @return the shape that is being manipulated by the animation.
   */
  Shape getShape();

  /**
   * Returns the start time of this animation.
   * @return the start time of this animation.
   */
  int getStartTime();

  /**
   * Returns the end time of this animation.
   * @return the end time of this animation.
   */
  int getEndTime();

  /**
   * Creates a copy of the animation by creating a new Animation object with the same information
   * as this animation.
   */
  Animation copy();

  /**
   * Creates a string representation describing the animation with respect to the given ticks per
   * second.
   *
   * @param tps represent the ticks per second to be used for this animation.
   * @return a string representation describing the animation with respect to the given ticks per
   *         second.
   */
  String displayAnimation(int tps);

  /**
   * Creates an SVG representation describing the animation.
   *
   * @return the SVG output of the animation as a String.
   */
  String toSVG(int tempo);


  /**
   * Generates an SVG representation of a rectangle and its animations.
   *
   * @param tempo the speed at which the svg image should be constructed to.
   * @return a rectangle's SVG representation
   */
  String rectangleSVG(int tempo);

  /**
   * Generates an SVG representation of an ellipse and its animations.
   *
   * @param tempo the speed at which the svg image should be constructed to.
   * @return an ellipse's SVG representation
   */
  String ellipseSVG(int tempo);

  /**
   * Applies this animation to its shape with the speed of the given tick.
   * @param tick the speed with which to apply this animation to its shape.
   */
  void apply(int tick);

  /**
   * Sets the start time of the animation.
   *
   * @param start the start time
   */
  void setStartTime(int start);

  /**
   * Sets the end time of the animation.
   *
   * @param end the end time
   */
  void setEndTime(int end);
}
