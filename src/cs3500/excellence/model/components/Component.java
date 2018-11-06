package cs3500.excellence.model.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;

/**
 * Abstract class for Components.
 */
public class Component implements IComponent {

  private final List<IMotion> motions;
  private final String name;
  private final Shape type;


  public Component(String name, Shape type) {
    this.motions = new ArrayList<>();
    this.name = Objects.requireNonNull(name, "Name not valid");
    this.type = Objects.requireNonNull(type, "Type not valid");
  }

  public Component(Component component) {
    this.name = component.name;
    this.type = component.type;

    List<IMotion> copy = new ArrayList<>();
    for (IMotion motion : component.motions) {
      copy.add(motion.copy());
    }
    this.motions = copy;
  }

  @Override
  public void addMotion(IMotion motion) {
    if (motion == null) {
      throw new IllegalArgumentException("Motion cannot be null");
    }
    if (motions.size() > 0) {
      IMotion lastMotion = motions.get(motions.size() - 1);
      if (motion.equals(lastMotion) && motion.initialTick() != lastMotion.endTick()) {
        throw new IllegalArgumentException("Not adjacent motions");
      }
    }
    motions.add(motion);
  }

//  @Override
//  public void addKeyframe(IStatic key) {
//    if (key == null) {
//      throw new IllegalArgumentException("Motion cannot be null");
//    }
//    keyFrames.add(key);
//  }


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
  public String getOverview() {
    StringBuilder output = new StringBuilder();
    output.append("shape " + this.name + " " + this.type).append("\n");
    for (IMotion m : motions) {
      output.append("motion " + this.name + " ").append(m.getOverview()).append("\n");
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

  @Override
  public IComponent copy() {
    return new Component(this);
  }

  @Override
  public boolean hasMotions() {
    return !motions.isEmpty();
  }

  @Override
  public String getID() {
    return name;
  }

}
