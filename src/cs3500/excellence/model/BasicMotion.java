package cs3500.excellence.model;

/**
 * Represents a linear motion that has starting and ending State.
 */
public class BasicMotion implements IMotion {

  State initial;
  State end;
  int initialTick;
  int endTick;

  /**
   * Constructs a BasicMotion.
   * @param initial - starting State of the motion
   * @param end - ending State of the motion
   * @param initialTick - starting tick
   * @param endTick - ending tick
   */
  public BasicMotion(State initial, State end, int initialTick, int endTick) {
    if (initialTick > endTick) {
      throw new IllegalArgumentException("end tick must be greater than begin tick");
    }
    if (initial == null || end == null) {
      throw new IllegalArgumentException("States cannot be null");
    }
    this.initial = initial;
    this.end = end;
    this.initialTick = initialTick;
    this.endTick = endTick;
  }

  /**
   * Used as a copy constructor.
   * @param bm - the BasicMotion to create of copy of
   */
  public BasicMotion(BasicMotion bm) {
    if (bm == null) {
      throw new IllegalArgumentException("Motion cannot be null");
    }
    this.initial = bm.initial;
    this.end = bm.end;
    this.initialTick = bm.initialTick;
    this.endTick = bm.endTick;
  }

  @Override
  public State getStateAtTick(int tick) {
    double tickDelta = endTick - initialTick;
    double relTick = tick - initialTick;
    double timeDelta = relTick / tickDelta;

    int posX = (int) (initial.xPos() + ((end.xPos() - initial.xPos()) * timeDelta));
    int posY = (int) (initial.yPos() + ((end.yPos() - initial.yPos()) * timeDelta));
    int width = (int) (initial.width() + ((end.width() - initial.width()) * timeDelta));
    int height = (int) (initial.height() + ((end.height() - initial.height()) * timeDelta));
    int red = (int) (initial.red() + ((end.red() - initial.red()) * timeDelta));
    int green = (int) (initial.green() + ((end.green() - initial.green()) * timeDelta));
    int blue = (int) (initial.blue() + ((end.blue() - initial.blue()) * timeDelta));
    State newState = new State(posX, posY, width, height, red, green, blue);
    return newState;
  }

  @Override
  public int initialTick() {
    return initialTick;
  }

  @Override
  public int endTick() {
    return endTick;
  }

  @Override
  public String getOverview() {
    StringBuilder output = new StringBuilder();
    String initOut = String.format("%d %s", initialTick, initial);
    String endOut = String.format("%d %s", endTick, end);
    output.append(initOut + "    " + endOut);
    return output.toString();
  }

  @Override
  public boolean containsTick(int tick) {
    return tick <= endTick && tick >= initialTick;
  }

  @Override
  public IMotion copy() {
    return new BasicMotion(this);
  }

}
