package cs3500.excellence.model.hw05.shapes;

import cs3500.excellence.model.hw05.State;

public interface IComponent {


  /**
   * Adds a motion to the shape
   * @param initialState
   * @param endState
   * @param initialTick
   * @param endTick
   */
  void addMotion(State initialState, State endState, int initialTick, int endTick);


  /**
   * Figures out what the shape is at a specific tick
   * @param tick
   * @return
   */
  State getStateAtTick(int tick);


  /**
   * Gets overview of shape between start and end tick.
   * @param initialTick
   * @param endTick
   * @return
   */
  String getOverview(int initialTick, int endTick);
}
