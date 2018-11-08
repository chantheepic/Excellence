package cs3500.excellence.model.components;

import java.util.ArrayList;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;

public interface IROComponent {

  ArrayList<IMotion> returnAllMotions();


  Shape getShape();

  /**
   * Gets the calculated state at a specific tick.
   *
   * @param tick - the desired tick
   * @return the State representing the different parameters.
   */
  State getStateAtTick(int tick);

  /**
   * Whether or not the component has a motion defined at a specific tick.
   *
   * @param tick - the desired tick.
   * @return true or false depending on description.
   */
  boolean hasMotionAtTick(int tick);

  /**
   * Gets the ending tick of the last motion that the component has.
   *
   * @return the tick
   */
  int getFinalTick();


  /**
   * Does this component contain any motions?
   * @return
   */
  boolean hasMotions();


  String getID();

}
