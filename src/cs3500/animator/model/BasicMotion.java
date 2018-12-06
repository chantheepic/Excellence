package cs3500.animator.model;

/**
 * Represents a linear motion that has starting and ending State.
 */
public class BasicMotion implements IMotion {

  private final State initial;
  private final State end;
  private final int initialTick;
  private final int endTick;

  /**
   * Constructs a BasicMotion.
   *
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

  @Override
  public State getStateAtTick(int tick) {
    if (endTick == initialTick) {
      return initial;
    }
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
  public State initialState() {
    return initial;
  }

  @Override
  public State endState() {
    return end;
  }

  @Override
  public boolean containsTick(int tick) {
    return tick <= endTick && tick >= initialTick;
  }

}
