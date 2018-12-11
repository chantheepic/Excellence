package cs3500.animator.model;


/**
 * Represents a state of a component. Each State has 7 different parameters.
 */
public final class State {


  //Changed all to int because example only gave ints
  private final int width;
  private final int height;
  private final int posX;
  private final int posY;
  private final int red;
  private final int green;
  private final int blue;
  private final int heading;

  /**
   * Constructs a State with the following parameters.
   *
   * @param x - X position
   * @param y - Y position
   * @param w - width
   * @param h - height
   * @param r - red
   * @param g - green
   * @param b - blue
   */
  public State(int x, int y, int w, int h, int r, int g, int b) {
    this(x, y, w, h, r, g, b, 0);
  }

  public State(int x, int y, int w, int h, int r, int g, int b, int rot) {
    if (r > 255 || g > 255 || b > 255) {
      throw new IllegalArgumentException("Color values cannot exceed 255");
    }
    if (r < 0 || g < 0 || b < 0) {
      throw new IllegalArgumentException("Color values cannot be negative");
    }
    this.width = w;
    this.height = h;
    this.posX = x;
    this.posY = y;
    this.red = r;
    this.green = g;
    this.blue = b;
    this.heading = rot;
  }

  // getters
  public int width() {
    return width;
  }

  public int height() {
    return height;
  }

  public int xPos() {
    return posX;
  }

  public int yPos() {
    return posY;
  }

  public int red() {
    return red;
  }

  public int green() {
    return green;
  }

  public int blue() {
    return blue;
  }

  public int heading() {
    return heading;
  }


  @Override
  public boolean equals(Object o) {
    if (o instanceof State) {
      State that = (State) o;
      return that.posX == this.posX
          && that.posY == this.posY
          && that.width == this.width
          && that.height == this.height
          && that.red == this.red
          && that.green == this.green
          && that.blue == this.blue
          && that.heading == this.heading;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return posX + posY + width + red + green + blue + heading;
  }


}
