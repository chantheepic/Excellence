package cs3500.excellence.model.components;

import cs3500.excellence.model.State;

public final class Keyframe {

  private int tick;
  private State state;

  public Keyframe(int tick, State state) {
    this.tick = tick;
    this.state = state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public State getState() {
    return state;
  }

  public int getTick() {
    return tick;
  }
}
