package cs3500.excellence.model.hw05.shapes;

import cs3500.excellence.model.hw05.IMotion;
import cs3500.excellence.model.hw05.State;

public interface IComponent {


  /**
   * Adds a motion to the shape
   */
  void addMotion(IMotion motion);


  /**
   * Figures out what the shape is at a specific tick
   * @param tick
   * @return
   */
  State getStateAtTick(int tick);


  /**
   * Gets overview of shape between start and end tick.

   * @return
   */
  String getOverview(String id);
}
