package cs3500.animator.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cs3500.animator.model.components.Component;
import cs3500.animator.model.components.IComponent;
import cs3500.animator.model.components.IROComponent;
import cs3500.animator.model.components.Shape;
import cs3500.animator.util.AnimationBuilder;

/**
 * Represents a model for supporting BasicMotions. The model knows all components and the boundary
 * size. The model consists of IComponents.
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
  public void addComponent(String name, String type, int layer) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (type == null) {
      throw new IllegalArgumentException("Type cannot be null");
    }
    if (layer < 0) {
      throw new IllegalArgumentException("Layer cannot be negative");
    }
    if (registeredShapes.containsKey(name)) {
      throw new IllegalArgumentException("Object already exists");
    }

    //Tries to create a shape based on the input.
    //Can throw and error
    registeredShapes.put(name, new Component(name, createShape(type), layer));
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
  public void createKeyframe(String name, int tick, State newState)
      throws IllegalArgumentException {
    if (!registeredShapes.containsKey(name)) {
      throw new IllegalArgumentException("Name doesn't exist");
    }
    IComponent component = registeredShapes.get(name);
    component.createKeyframe(tick, newState);

  }

  @Override
  public void removeKeyframe(String name, int tick) throws IllegalArgumentException {
    if (!registeredShapes.containsKey(name)) {
      throw new IllegalArgumentException("Invalid name");
    }
    if (!registeredShapes.get(name).hasKeyframeAtTick(tick)) {
      throw new IllegalArgumentException("Invalid tick");
    }

    registeredShapes.get(name).removeKeyframe(tick);

  }

  @Override
  public void setLayer(String name, int layer) throws IllegalArgumentException {
    if (!registeredShapes.containsKey(name)) {
      throw new IllegalArgumentException("Invalid name");
    }
    registeredShapes.get(name).setLayer(layer);
  }

  @Override
  public void swapLayer(int origin, int target) {
    List<String> originLayer = new ArrayList<>();
    List<String> targetLayer = new ArrayList<>();
    for (IComponent component : registeredShapes.values()) {
      if (component.getLayer() == origin) {
        originLayer.add(component.getID());
      }
      if (component.getLayer() == target) {
        targetLayer.add(component.getID());
      }
    }
    for (String name : originLayer) {
      registeredShapes.get(name).setLayer(target);
    }
    for (String name : targetLayer) {
      registeredShapes.get(name).setLayer(origin);
    }
  }

  @Override
  public void deleteLayer(int layer) {
    List<String> toDelete = new ArrayList<>();
    for (IComponent component : registeredShapes.values()) {
      if (component.getLayer() == layer) {
        toDelete.add(component.getID());
      }
    }
    for (String name : toDelete) {
      registeredShapes.remove(name);
    }
  }

  @Override
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
    List<IROComponent> output = new ArrayList<>(this.registeredShapes.values());
    Collections.sort(output);
    return output;
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

    @Override
    public AnimationBuilder<IModel> setBounds(int x, int y, int width, int height) {
      this.boundary = new Boundary(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<IModel> declareShape(String name, String type, int layer) {

      IComponent shape;

      switch (type) {
        case "rectangle":
          shape = new Component(name, Shape.RECTANGLE, layer);
          break;
        case "ellipse":
          shape = new Component(name, Shape.ELLIPSE, layer);
          break;
        default:
          throw new IllegalArgumentException("Invalid type");
      }
      registeredShapes.put(name, shape);
      return this;
    }

    @Override
    public AnimationBuilder<IModel> addMotion(String name, int t1, int x1, int y1, int w1, int h1,
        int r1, int g1, int b1, int rot1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2, int rot2) {
      if (!registeredShapes.containsKey(name)) {
        throw new IllegalArgumentException("Name does not exist");
      }
      IComponent comp = registeredShapes.get(name);
      if (comp.hasMotion() && comp.getFinalTick() != t1) {
        throw new IllegalArgumentException("Motions do not lineup");
      }
      //Throws errors if invalid.
      comp.addMotion(new BasicMotion(new State(x1, y1, w1, h1, r1, g1, b1, rot1),
          new State(x2, y2, w2, h2, r2, g2, b2, rot2), t1, t2));
      return this;
    }

    @Override
    public AnimationBuilder<IModel> addKeyframe(String name, int t, int x, int y, int w, int h,
        int r, int g, int b, int rot) {

      if (!registeredShapes.containsKey(name)) {
        throw new IllegalArgumentException("Name does not exist");
      }
      IComponent comp = registeredShapes.get(name);
      comp.createKeyframe(t, new State(x, y, w, h, r, g, b, rot));
      return this;
    }
  }

}
