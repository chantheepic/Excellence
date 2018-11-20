package cs3500.animator.model;

/**
 * Stores the 4 values from the canvas.
 * The (x,y) value represents the starting point of the canvas relative to the plane.
 */
public class Boundary {

  private final int x;
  private final int y;
  private final int width;
  private final int height;


  /**
   * Creates a boundary class to store the information.
   * @param x - the x value of the canvas
   * @param y - the y value of the canvas
   * @param width - the width of the canvas
   * @param height - the height of the canvas
   */
  public Boundary(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
