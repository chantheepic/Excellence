package cs3500.animator.model;

import cs3500.animator.model.components.Component;
import cs3500.animator.model.components.IComponent;
import cs3500.animator.model.components.IROComponent;
import cs3500.animator.model.components.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Mock version of the model. For testing purposes only.
 */
public class MockModel implements IModel {

  StringBuilder output;
  IComponent e;
  List<IROComponent> roList;

  /**
   * Default Constructor for Mock Model.
   * @param out output as string.
   */
  public MockModel(StringBuilder out) {
    e = new Component("E", Shape.ELLIPSE);
    roList = new ArrayList<>();
    roList.add(e);
    this.output = out;
  }

  @Override
  public void addComponent(String name, String type) {
    output.append("component added\n");
  }

  @Override
  public void addMotion(String name, State initialState, State endState, int initialTick,
      int endTick) {
    output.append("motion added\n");
  }

  @Override
  public void removeComponent(String name) {
    output.append("component removed\n");
  }

  @Override
  public void removeAllComponent() {
    output.append("all components removed\n");
  }

  @Override
  public void createKeyframe(String name, int tick, State newState) {
    output.append("keyframe added\n");
  }

  @Override
  public void removeKeyframe(String name, int tick) {
    output.append("keyframe removed\n");
  }

  @Override
  public Set<String> getAllIds() {
    output.append("all ID");
    return null;
  }

  @Override
  public IROComponent getComponentByID(String id) {
    output.append("component " + id + "\n");
    return null;
  }

  @Override
  public List<IROComponent> getAllComponents() {
    output.append("all components\n");
    return null;
  }

  @Override
  public Boundary getBoundary() {
    output.append("boundary\n");
    return null;
  }

  @Override
  public List<State> getKeyframes() {
    output.append("all keyframes\n");
    return null;
  }

  @Override
  public int getFinalTick() {
    return 0;
  }
}
