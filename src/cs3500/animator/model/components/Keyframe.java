package cs3500.animator.model.components;

import cs3500.animator.model.State;

/**
 * This class represents a keyframe for a given component. It is similar to a State,
 * but it holds information about the tick as well.
 */
public final class Keyframe {

  private final int tick;
  private final State state;

  /**
   * Creates a keyframe object, it is final and completely immutable.
   * @param tick - the given tick.
   * @param state - the given state associated with the tick.
   */
  public Keyframe(int tick, State state) {
    this.tick = tick;
    this.state = state;
  }

  /**
   * Gets the state information for this keyframe.
   * @return - the state
   */
  public State getState() {
    return state;
  }

  /**
   * Gets the tick value for this keyframe.
   * @return - the tick
   */
  public int getTick() {
    return tick;
  }
}
