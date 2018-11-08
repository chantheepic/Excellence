package cs3500.excellence.model.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;

/**
 * Abstract class for Components.
 */
public class Component implements IComponent, IROComponent {

  private final List<IMotion> motions;
  private final String name;
  private final Shape type;


  public Component(String name, Shape type) {
    this.motions = new ArrayList<>();
    this.name = Objects.requireNonNull(name, "Name not valid");
    this.type = Objects.requireNonNull(type, "Type not valid");
  }


  @Override
  public ArrayList<IMotion> returnAllMotions() {
    return new ArrayList<IMotion>(this.motions);
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

  @Override
  public void removeMotion(int index) {
    motions.remove(index);
  }

  @Override
  public void removeMotionAtTick(int tick) {
    for(int i = 0; i < motions.size(); i++){
      if(motions.get(i).containsTick(tick)){
        motions.remove(i);
        break;
      }
    }
  }

  @Override
  public void removeAllMotion() {
    motions.clear();
  }


  @Override
  public Shape getShape(){
    return type;
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
  public boolean hasMotions() {
    return !motions.isEmpty();
  }

  @Override
  public String getID() {
    return name;
  }

}
