package cs3500.excellence.model.hw05;


public class BasicMotion implements IMotion {

  State initial;
  State end;
  int initialTick;
  int endTick;



  public BasicMotion(State initial, State end, int initialTick, int endTick) {
    if(initialTick > endTick){
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

  public BasicMotion(BasicMotion bm) {
    if (bm == null) {
      throw new IllegalArgumentException("States cannot be null");
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

      int posX = (int) (initial.x() + ((end.x() - initial.x()) * timeDelta));
      int posY = (int) (initial.y() + ((end.y() - initial.y()) * timeDelta));
      int width = (int) (initial.w() + ((end.w() - initial.w()) * timeDelta));
      int height = (int) (initial.h() + ((end.h() - initial.h()) * timeDelta));
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
    String initOut = String.format("%3d%3s", initialTick, initial);
    String endOut = String.format("%3d%3s", endTick, end);
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
