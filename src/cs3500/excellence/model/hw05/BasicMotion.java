package cs3500.excellence.model.hw05;

public class BasicMotion implements IMotion {

  State initial;
  State end;
  int initialTick;
  int endTick;

  public BasicMotion(State initial, State end, int initialTick, int endTick) {
    this.initial = initial;
    this.end = end;
    this.initialTick = initialTick;
    this.endTick = endTick;
  }

  @Override
  public State getStateAtTick(int tick) {
    return null;
  }


  private class State {

    private int width;
    private int height;
    private int posX;
    private int posY;
    private int red;
    private int green;
    private int blue;

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
  }
}
