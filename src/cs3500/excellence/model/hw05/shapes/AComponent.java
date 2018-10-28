package cs3500.excellence.model.hw05.shapes;

import java.util.ArrayList;
import java.util.List;

import cs3500.excellence.model.hw05.IMotion;
import cs3500.excellence.model.hw05.State;

public abstract class AComponent implements IComponent {
  protected final List<IMotion> motions = new ArrayList<>();

  // each object created calculates its own position when given a tick. To do so, it creates a array that is of length <endTick> where
  // each index of the array represents a tick and the content of the array represents the state of this object at tick.
  // The states of the object between startTick and endTick is calculated when a new command is given.
  // Returning a overview is as simple as fetching the index wanted from the array


  @Override
  public void addMotion(IMotion motion){
//    if(motion.initialTick() != motions.get(motions.size()).endTick()) {
//      throw new IllegalArgumentException("Not adjacent motions");
//    }
    motions.add(motion);
  }

  // Returning a overview is as simple as fetching the index wanted from the array
  // If content does not exist in array at tick t, (i.e. command given from ticks 1~3 and 5~6, tick 4 has no generated state)
  // fetch last successfully created state (as if the object isn't moving during this period)
  @Override
  public State getStateAtTick(int tick){
    for (IMotion motion : motions) {
      if (motion.containsTick(tick)) {
        return motion.getStateAtTick(tick);
      }
    }

    throw new IllegalArgumentException("Tick not valid");

  }

  @Override
  public String getOverview(){
    StringBuilder output = new StringBuilder();
    for (IMotion m : motions) {
      output.append(m.getOverview()).append("\n");
    }
    return output.toString();
  }
}
