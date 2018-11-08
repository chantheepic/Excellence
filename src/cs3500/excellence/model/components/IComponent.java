package cs3500.excellence.model.components;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.IROModel;
import cs3500.excellence.model.State;
import java.util.ArrayList;


/**
 * Represents an object in the animation. Different components may have different shapes.
 */
public interface IComponent extends IROComponent {


  /**
   * Adds a motion to the component. Must be added in chronological order and must be adjacent end
   * -> start tick.
   *
   * @param motion - the given motion.
   * @throws IllegalArgumentException - if the motion is null, or does not line up. EX: [ tick = 1
   *                                   ... tick = 10 ] and [tick = 10 ... tick = 25 ]
   */
  void addMotion(IMotion motion) throws IllegalArgumentException;
  //void addKeyframe(IStatic key)



}
