package cs3500.excellence.model.hw05;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import cs3500.excellence.model.hw05.components.IComponent;

/**
 * Represents a model for supporting BasicMotions
 */
public class Model implements IModel {

  private final SortedMap<String, IComponent> registeredShapes;


  public Model() {
    this.registeredShapes = new TreeMap<>();
  }


  @Override
  public void addComponent(String id, IComponent component) throws IllegalArgumentException {
    if (component == null) {
      throw new IllegalArgumentException("Component cannot be null");
    }
    if (registeredShapes.containsKey(id)) {
      throw new IllegalArgumentException("Object already exists");
    }
    registeredShapes.put(id, component);
  }

  @Override
  public void addMotion(String id, State initialState, State endState, int initialTick, int endTick)
          throws IllegalArgumentException {
    if (initialState == null || endState == null) {
      throw new IllegalArgumentException("States cannot be null");
    }
    if (registeredShapes.containsKey(id)) {
      IComponent component = registeredShapes.get(id);
      component.addMotion(new BasicMotion(initialState, endState, initialTick, endTick));
    } else {
      throw new IllegalArgumentException("Object does not exist");
    }
  }

  @Override
  public List<IComponent> getComponentsAtTick(int tick) {
    List<IComponent> output = new ArrayList<>();
    for (IComponent comp : registeredShapes.values()) {
      if (comp.hasMotionAtTick(tick)) {
        output.add(comp.copy());
      }
    }
    return output;
  }

  @Override
  public int getFinalTick() {
    int output = 0;
    for (IComponent component : registeredShapes.values()) {
      int finalTick = component.getFinalTick();
      if (finalTick > output) {
        output = finalTick;
      }
    }
    return output;
  }

  @Override
  public Set<String> getAllIds() {
    return new TreeSet<>(registeredShapes.keySet());
  }

  @Override
  public IComponent getComponentByID(String id) {
    if (registeredShapes.containsKey(id)) {
      return registeredShapes.get(id).copy();
    }
      throw new IllegalArgumentException("Component does not exist");
  }


  public String getOverview() {
    StringBuilder output = new StringBuilder();
    for (String componentId : registeredShapes.keySet()) {
      IComponent component = registeredShapes.get(componentId);
      output.append("shape " + componentId + " " + component).append("\n");
      output.append(component.getOverview(componentId)).append("\n\n");
    }
    return output.toString();
  }
}
