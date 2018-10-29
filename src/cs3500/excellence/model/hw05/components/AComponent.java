package cs3500.excellence.model.hw05.components;


import java.util.ArrayList;
import java.util.List;

import cs3500.excellence.model.hw05.IMotion;
import cs3500.excellence.model.hw05.State;

public abstract class AComponent implements IComponent {

  protected List<IMotion> motions = new ArrayList<>();

  @Override
  public void addMotion(IMotion motion){
    if (motion == null) {
      throw new IllegalArgumentException("Motion cannot be null");
    }
    if(motions.size() > 0){
      if(motion.initialTick() != motions.get(motions.size()-1).endTick()) {
        throw new IllegalArgumentException("Not adjacent motions");
      }
    }
    motions.add(motion);
  }

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
  public String getOverview(String id){
    StringBuilder output = new StringBuilder();
    for (IMotion m : motions) {
      output.append("motion " + id).append(m.getOverview()).append("\n");
    }
    return output.toString();
  }

  @Override
  public boolean hasMotionAtTick(int tick) {
    for(IMotion mot: motions) {
      if (mot.containsTick(tick)) {
        return true;
      }
    }
    return false;
  }

}
