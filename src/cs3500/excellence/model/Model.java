package cs3500.excellence.model;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cs3500.excellence.model.components.Component;
import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.model.components.IROComponent;
import cs3500.excellence.model.components.Shape;
import cs3500.excellence.util.AnimationBuilder;

/**
 * Represents a model for supporting BasicMotions. The model knows all components and the boundary
 * size
 */
public class Model implements IModel {

  private final LinkedHashMap<String, IComponent> registeredShapes;
  private final Boundary boundary;

  public Model(Boundary boundary) {
    registeredShapes = new LinkedHashMap<>();
    this.boundary = boundary;
  }

  /**
   * Constructs an empty model.
   */
  private Model(LinkedHashMap<String, IComponent> shapes, Boundary boundary) {
    this.registeredShapes = shapes;
    this.boundary = boundary;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public void addComponent(String name, String type) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (type == null) {
      throw new IllegalArgumentException("Type cannot be null");
    }
    if (registeredShapes.containsKey(name)) {
      throw new IllegalArgumentException("Object already exists");
    }

    //Tries to create a shape based on the input.
    //Can throw and error
    registeredShapes.put(name, new Component(name, createShape(type)));
  }

  @Override
  public void removeComponent(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (!registeredShapes.containsKey(name)) {
      throw new IllegalArgumentException("Object does not exists");
    }

    registeredShapes.remove(name);
  }

  @Override
  public void removeAllComponent() {
    registeredShapes.clear();
  }

  private Shape createShape(String type) {
    switch (type) {
      case "rectangle":
        return Shape.RECTANGLE;
      case "ellipse":
        return Shape.ELLIPSE;
      default:
        throw new IllegalArgumentException("Not valid type");
    }
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
  public void removeMotion(String name, int index) {
    if (registeredShapes.containsKey(name)) {
      registeredShapes.get(name).removeMotion(index);
    }
  }

  @Override
  public void insertKeyframe(String name, int tick, State newState) throws IllegalArgumentException {
    if (registeredShapes.containsKey(name)) {
      IComponent component = registeredShapes.get(name);
      component.insertKeyframe(tick, newState);
    }
  }

  @Override
  public void removeKeyframe(String name, int tick) throws IllegalArgumentException {
    if (!registeredShapes.containsKey(name)) {
      throw new IllegalArgumentException("Invalid name");
    }
    if (!registeredShapes.get(name).hasMotionAtTick(tick)) {
      throw new IllegalArgumentException("Invalid tick");
    }

    //registeredShapes.get(name).re

  }

  @Override
  // IS anything using this?
  public Set<String> getAllIds() {
    return new TreeSet<>(registeredShapes.keySet());
  }

  @Override
  public IROComponent getComponentByID(String id) throws IllegalArgumentException {
    if (registeredShapes.containsKey(id)) {
      return registeredShapes.get(id);
    }
    throw new IllegalArgumentException("Component does not exist");
  }

  @Override
  public List<IROComponent> getAllComponents() {
    return new ArrayList<>(this.registeredShapes.values());
  }

  @Override
  public Boundary getBoundary() {
    return boundary;
  }

  @Override
  public List<State> getKeyframes() {
    return null;
  }

  @Override
  public int getFinalTick() {
    int output = 0;
    for (IROComponent component : registeredShapes.values()) {
      int finalTick = component.getFinalTick();
      if (finalTick > output) {
        output = finalTick;
      }
    }
    return output;

}

  public static final class Builder implements AnimationBuilder<IModel> {

    private final LinkedHashMap<String, IComponent> registeredShapes = new LinkedHashMap<>();
    private Boundary boundary = new Boundary(0, 0, 500, 500);

    @Override
    public IModel build() {
      return new Model(registeredShapes, boundary);
    }

    //TODO
    @Override
    public AnimationBuilder<IModel> setBounds(int x, int y, int width, int height) {
      this.boundary = new Boundary(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<IModel> declareShape(String name, String type) {

      IComponent shape;

      switch (type) {
        case "rectangle":
          shape = new Component(name, Shape.RECTANGLE);
          break;
        case "ellipse":
          shape = new Component(name, Shape.ELLIPSE);
          break;
        default:
          throw new IllegalArgumentException("Invalid type");
          //TODO interface doesn't declare this
      }
      registeredShapes.put(name, shape);
      return this;
    }

    @Override
    public AnimationBuilder<IModel> addMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
      if (!registeredShapes.containsKey(name)) {
        throw new IllegalArgumentException("Name does not exist");
      }
      IComponent comp = registeredShapes.get(name);
      if (comp.hasMotion() && comp.getFinalTick() != t1) {
        throw new IllegalArgumentException("Motions do not lineup");
      }
      //Throws errors if invalid.
      comp.addMotion(new BasicMotion(new State(x1, y1, w1, h1, r1, g1, b1), new State(x2, y2, w2, h2, r2, g2, b2), t1, t2));
      return this;
    }

    @Override
    public AnimationBuilder<IModel> addKeyframe(String name, int t, int x, int y, int w, int h, int r, int g, int b) {

      if (!registeredShapes.containsKey(name)) {
        throw new IllegalArgumentException("Name does not exist");
      }
      IComponent comp = registeredShapes.get(name);
      comp.insertKeyframe(t, new State(x, y, w, h, r, g, b));
      return this;
    }
  }


}
