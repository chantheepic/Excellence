package cs3500.excellence.model.components;

import cs3500.excellence.model.BasicMotion;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;

/**
 * Represents an animate-able object in the animation.
 * Each component knows its name(String), type(Shape), and moves(List<IMotion>).
 * Contains fields List of motions, a String name, a Shape type.
 */
public class Component implements IComponent, IROComponent {

  private final List<IMotion> motions;
  private final String name;
  private final Shape type;


  /**
   * Creates a component given a name and a type.
   * @param name - the name of the component.
   * @param type - the type of the component.
   */
  public Component(String name, Shape type) {
    this.motions = new ArrayList<>();
    this.name = Objects.requireNonNull(name, "Name not valid");
    this.type = Objects.requireNonNull(type, "Type not valid");
  }

  @Override
  public ArrayList<IMotion> returnAllMotions() {
    return new ArrayList<>(this.motions);
  }

  @Override
  public void addMotion(IMotion motion) {
    if (motion == null) {
      throw new IllegalArgumentException("Motion cannot be null");
    }
    if (motions.size() > 0) {
      IMotion lastMotion = motions.get(motions.size() - 1);
      if (motion.equals(lastMotion) || motion.initialTick() != lastMotion.endTick()) {
        throw new IllegalArgumentException("Not adjacent motions");
      }
    }
    motions.add(motion);
  }

  @Override
  public void removeMotion(int index) {
    if(index < motions.size()){
      if(index == motions.size()){
        motions.remove(index);
      } else {
        State s = motions.get(index).endState();
        State e = motions.get(index+1).endState();
        int i = motions.get(index).initialTick();
        int f = motions.get(index).endTick();
        motions.remove(index);
        motions.remove(index+1);
        motions.add(index, new BasicMotion(s,e,i,f));
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
    throw new IllegalArgumentException("Tick does not exist");
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
