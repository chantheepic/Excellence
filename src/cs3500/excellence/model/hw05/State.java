package cs3500.excellence.model.hw05;


/**
 * Represents a state of a motion. IComponent has two states (initial and end)
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

  public State(int w, int h, int x, int y, int r, int g, int b) {
    if (r > 255 || g > 255 || b > 255) {
      throw new IllegalArgumentException("Color values cannot exceed 255");
    }
    this.width = w;
    this.height = h;
    this.posX = x;
    this.posY = y;
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  // getters
  public int w() {
    return width;
  }

  public int h() {
    return height;
  }

  public int x() {
    return posX;
  }

  public int y() {
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

  public String toString() {
    return String.format("%3d %3d %3d %3d %3d %3d %3d", posX, posY, width, height, red, green, blue);
  }


}
