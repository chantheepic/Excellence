package cs3500.excellence.model.components;

import java.util.ArrayList;
import java.util.List;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;

/**
 * Abstract class for Components.
 */
public abstract class AComponent implements IComponent {

  protected List<IMotion> motions = new ArrayList<>();

  @Override
  public void addMotion(IMotion motion) {
    if (motion == null) {
      throw new IllegalArgumentException("Motion cannot be null");
    }
    if (motions.size() > 0) {
      if (motion.initialTick() != motions.get(motions.size() - 1).endTick()) {
        throw new IllegalArgumentException("Not adjacent motions");
      }
    }
    motions.add(motion);
  }

  @Override
  public State getStateAtTick(int tick) {
    for (IMotion motion : motions) {
      if (motion.containsTick(tick)) {
        return motion.getStateAtTick(tick);
      }
    }
    throw new IllegalArgumentException("Tick not valid");
  }

  @Override
  public String getOverview(String id) {
    StringBuilder output = new StringBuilder();
    for (IMotion m : motions) {
      output.append("motion " + id).append(m.getOverview()).append("\n");
    }
    return output.toString();
  }

  @Override
  public boolean hasMotionAtTick(int tick) {
    for (IMotion mot : motions) {
      if (mot.containsTick(tick)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int getFinalTick() {
    if (motions.isEmpty()) {
      return 0;
    } else {
      return motions.get(motions.size() - 1).endTick();
    }

  }

}
