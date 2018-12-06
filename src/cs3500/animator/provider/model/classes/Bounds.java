package cs3500.animator.provider.model.classes;

import cs3500.animator.provider.model.interfaces.BoundsReadOnly;

/**
 * Represents the bounding box to be used for animations.
 */
public class Bounds implements BoundsReadOnly {
  private final int x;
  private final int y;
  private final int width;
  private final int height;

  /**
   * Copy Constructor for bounds. Constructs a new Bounds object that has the same exact values and
   * state as given Bounds.
   *
   * @param bounds the bounds object to create a copy of.
   */
  public Bounds(Bounds bounds) {
    this.x = bounds.getX();
    this.y = bounds.getY();
    this.width = bounds.getWidth();
    this.height = bounds.getHeight();
  }

  /**
   * Constructs a bounds object that holds the information about the bounding box that gives
   * what bounds we can see about animations.
   *
   * @param x         represents the leftmost x value
   * @param y         represents the topmost y value
   * @param width     represents the width
   * @param height    represents the height
   */
  public Bounds(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Constructs a bounds object that holds the information about the bounding box that gives
   * what bounds we can see about animations. This creates a default bounds box with values 0,0
   * for the left and top most x and y values, and a width of 100 and a height of 100.
   */
  public Bounds() {
    this(0,0,100,100);
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public String toString() {
    StringBuilder bounds = new StringBuilder();
    bounds.append("canvas ");
    bounds.append(x);
    bounds.append(" ");
    bounds.append(y);
    bounds.append(" ");
    bounds.append(width);
    bounds.append(" ");
    bounds.append(height);
    return bounds.toString();
  }
}
