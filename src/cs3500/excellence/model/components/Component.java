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

  //private final List<IMotion> motions;
  List<State> keyStates;
  List<Integer> keyTimes;
  private final String name;
  private final Shape type;


  /**
   * Creates a component given a name and a type.
   * @param name - the name of the component.
   * @param type - the type of the component.
   */
  public Component(String name, Shape type) {
    this.keyStates = new ArrayList<>();
    this.keyTimes = new ArrayList();
    this.name = Objects.requireNonNull(name, "Name not valid");
    this.type = Objects.requireNonNull(type, "Type not valid");
  }

  @Override
  public ArrayList<IMotion> returnAllMotions() {
    ArrayList<IMotion> motions = new ArrayList<>();
    for(int i = 0; i < keyStates.size() - 1; i++){
      IMotion m = new BasicMotion(keyStates.get(i), keyStates.get(i + 1), keyTimes.get(i), keyTimes.get(i + 1));
      motions.add(m);
    }
    return motions;
  }

  public ArrayList<State> returnAllKeyStates() {return new ArrayList<>(this.keyStates);}
  public ArrayList returnAllKeyTimes(){return new ArrayList(this.keyTimes);}

  @Override
  public void addKeyFrame(IMotion motion) {
    if (motion == null) {
      throw new IllegalArgumentException("State cannot be null");
  }
    if(keyStates.size() == 0 || (keyStates.get(keyStates.size() - 1).equals(motion.initialState()) && keyTimes.get(keyStates.size() - 1) == motion.initialTick())){
      keyStates.add(motion.endState());
      keyTimes.add(motion.endTick());
    }
  }

  public void insertKeyFrame(State state, int tick){
    if(tick < keyTimes.get(0)){
      keyStates.add(0, state);
      keyTimes.add(0, tick);
      return;
    }
    if(tick > keyTimes.get(keyTimes.size() - 1)){
      keyStates.add(state);
      keyTimes.add(tick);
      return;
    }
    for(int i = 0; i < keyTimes.get(keyTimes.size() - 1); i++){
      if(tick == keyTimes.get(i)){
        keyStates.remove(i);
        keyStates.add(i, state);
        keyTimes.remove(i);
        keyTimes.add(i, tick);
        return;
      }
      if(tick == keyTimes.get(i + 1)){
        keyStates.remove(i + 1);
        keyStates.add(i + 1, state);
        keyTimes.remove(i + 1);
        keyTimes.add(i + 1, tick);
        return;
      }
      if(tick > keyTimes.get(i) && tick < keyTimes.get(i + 1)){
        keyStates.add(i, state);
        keyTimes.add(i, tick);
        return;
      }
    }
  }

  @Override
  public void removeMotion(int index) {
    if(index < keyStates.size()){
      keyStates.remove(index);
      keyTimes.remove(index);
    }
  }

  @Override
  public void removeAllMotion() {
    keyStates.clear();
    keyTimes.clear();
  }


  @Override
  public Shape getShape(){
    return type;
  }

  @Override
  public State getStateAtTick(int tick) {
    List<IMotion> motions = returnAllMotions();
    for (IMotion motion : motions) {
      if (motion.containsTick(tick)) {
        return motion.getStateAtTick(tick);
      }
    }
    throw new IllegalArgumentException("Tick does not exist");
  }

  @Override
  public boolean hasMotionAtTick(int tick) {
    return tick < keyTimes.get(keyTimes.size() - 1);
  }

  @Override
  public int getFinalTick() {
    return keyTimes.get(keyTimes.size() - 1);
  }

  @Override
  public boolean hasMotion() {
    return !keyStates.isEmpty();
  }

  @Override
  public String getID() {
    return name;
  }
}
