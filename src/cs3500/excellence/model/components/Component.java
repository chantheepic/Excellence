package cs3500.excellence.model.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.excellence.model.BasicMotion;
import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;

/**
 * Represents an animate-able object in the animation. Each component knows its name(String),
 * type(Shape), and moves(List<IMotion>). Contains fields List of motions, a String name, a Shape
 * type.
 */
public class Component implements IComponent, IROComponent {

  //List<State> keyStates;
  //List<Integer> keyTimes;
  private final String name;
  private final Shape type;
  //private final List<IMotion> motions;
  ArrayList<Keyframe> keyframes;


  /**
   * Creates a component given a name and a type.
   *
   * @param name - the name of the component.
   * @param type - the type of the component.
   */
  public Component(String name, Shape type) {
    this.keyframes = new ArrayList<>();
    this.name = Objects.requireNonNull(name, "Name not valid");
    this.type = Objects.requireNonNull(type, "Type not valid");
  }

  @Override
  public ArrayList<IMotion> returnAllMotions() {
    ArrayList<IMotion> motions = new ArrayList<>();
    for (int i = 1; i < keyframes.size(); i++) {
      Keyframe start = keyframes.get(i - 1);
      Keyframe end = keyframes.get(i);
      IMotion m = new BasicMotion(start.getState(), end.getState(), start.getTick(), end.getTick());
      motions.add(m);
    }
    return motions;
  }

  @Override
  public List<Keyframe> returnAllKeyframes() {
    return new ArrayList<>(keyframes);
  }

  @Override
  public void addMotion(IMotion motion) {
    if (motion == null) {
      throw new IllegalArgumentException("State cannot be null");
    }
    if (keyframes.size() == 0) {
      this.createKeyframe(motion.initialTick(), motion.initialState());
      this.createKeyframe(motion.endTick(), motion.endState());
      return;
    }
    Keyframe last = lastKeyframe();
    if (last.getTick() != motion.initialTick() || !(last.getState().equals(motion.initialState()))) {
      throw new IllegalArgumentException("Motions must not overlap");
    }
    this.createKeyframe(motion.initialTick(), motion.initialState());
    this.createKeyframe(motion.endTick(), motion.endState());


  }



  private Keyframe lastKeyframe() {
    return this.keyframes.get(keyframes.size() - 1);
  }

  @Override
  public void createKeyframe(int tick, State state) {
    if (keyframes.size() == 0) {
      keyframes.add(new Keyframe(tick, state));
    } else {
      for (int i = 0; i < keyframes.size(); i++) {
        if (keyframes.get(i).getTick() == tick) {
          keyframes.set(i, new Keyframe(tick, state));
          return;
        } else if (keyframes.get(i).getTick() > tick) {
          keyframes.add(i, new Keyframe(tick, state));
          return;
        }
      }
      keyframes.add(new Keyframe(tick, state));
    }
  }


  @Override
  public void removeAllMotion() {
    keyframes.clear();
  }

  @Override
  public void removeMotion(int index) {
    //TODO
  }

  @Override
  public void removeKeyframe(int tick) {
    keyframes.removeIf(k -> k.getTick() == tick);
  }


  @Override
  public Shape getShape() {
    return type;
  }

  @Override
  public State getStateAtTick(int tick) {
    if (!hasMotion()) {
      throw new IllegalArgumentException("no keyframes have been added");
    }
    if (tick < getInitialTick() || tick > getFinalTick()) {
      throw new IllegalArgumentException("invalid tick");
    }

    // Find index just larger than tick
    int index = 0;
    while(keyframes.get(index).getTick() < tick) {
      index++;
    }


    //The only way for index to be 0 is for tick to align with first keyframe
    if (index == 0) {
      return keyframes.get(0).getState();
    }


    State iState = keyframes.get(index - 1).getState();
    State eState = keyframes.get(index).getState();
    int iTime = keyframes.get(index - 1).getTick();
    int eTime = keyframes.get(index).getTick();
    double tickDelta = eTime - iTime;
    double relTick = tick - iTime;
    double timeDelta = relTick / tickDelta;

    int posX = (int) (iState.xPos() + ((eState.xPos() - iState.xPos()) * timeDelta));
    int posY = (int) (iState.yPos() + ((eState.yPos() - iState.yPos()) * timeDelta));
    int width = (int) (iState.width() + ((eState.width() - iState.width()) * timeDelta));
    int height = (int) (iState.height() + ((eState.height() - iState.height()) * timeDelta));
    int red = (int) (iState.red() + ((eState.red() - iState.red()) * timeDelta));
    int green = (int) (iState.green() + ((eState.green() - iState.green()) * timeDelta));
    int blue = (int) (iState.blue() + ((eState.blue() - iState.blue()) * timeDelta));
    State newState = new State(posX, posY, width, height, red, green, blue);

    return newState;
  }

  private int getInitialTick() {
    return keyframes.get(0).getTick();
  }

  @Override
  public boolean hasMotionAtTick(int tick) {
    if(hasMotion()) {
      return tick >= keyframes.get(0).getTick() &&
              tick <= keyframes.get(keyframes.size() - 1).getTick();
    }
    return false;
  }

  @Override
  public int getFinalTick() {
    if(keyframes.size()!=0) {
      return keyframes.get(keyframes.size() - 1).getTick();
    }
    return -1;
  }

  @Override
  public boolean hasMotion() {
    return !keyframes.isEmpty();
  }

  @Override
  public String getID() {
    return name;
  }

}
