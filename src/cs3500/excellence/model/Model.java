package cs3500.excellence.model;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cs3500.excellence.model.components.Component;
import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.model.components.Shape;
import cs3500.excellence.util.AnimationBuilder;

/**
 * Represents a model for supporting BasicMotions.
 */
public class Model implements IModel {

  private final LinkedHashMap<String, IComponent> registeredShapes;

  public Model() {
    registeredShapes = new LinkedHashMap<>();
  }
  /**
   * Constructs an empty model.
   */
  private Model(LinkedHashMap<String, IComponent> shapes) {
    this.registeredShapes = shapes;
    //TODO MIGHT NEED TO CLONE
  }


  @Override
  public void addComponent(IComponent component) throws IllegalArgumentException {
    if (component == null) {
      throw new IllegalArgumentException("Component cannot be null");
    }
    String id = component.getID();
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
  public Set<String> getAllIds() {
    return new TreeSet<>(registeredShapes.keySet());
  }

  @Override
  public IComponent getComponentByID(String id) throws IllegalArgumentException {
    if (registeredShapes.containsKey(id)) {
      return registeredShapes.get(id).copy();
    }
    throw new IllegalArgumentException("Component does not exist");
  }



  @Override
  public List<IComponent> getAllComponents() {
    return new ArrayList<>(this.registeredShapes.values());
  }

  @Override
  public String getOverview() {
    return null;
  }

  public static Builder builder(){
    return new Builder();
  }

  public static final class Builder implements AnimationBuilder<IModel> {

    private final LinkedHashMap<String, IComponent> registeredShapes = new LinkedHashMap<>();

    @Override
    public IModel build() {
      return new Model(registeredShapes);
    }

    //TODO
    @Override
    public AnimationBuilder<IModel> setBounds(int x, int y, int width, int height) {
      return this;
    }

    @Override
    public AnimationBuilder<IModel> declareShape(String name, String type) {

      IComponent shape;

      switch (type){
        case "rectangle":
          shape = new Component(name, Shape.RECT);
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
      if(!registeredShapes.containsKey(name)) {
        throw new IllegalArgumentException("Name does not exist");
      }
      IComponent comp = registeredShapes.get(name);
      if(comp.hasMotions() && comp.getFinalTick() != t1){
        throw new IllegalArgumentException("Motions do not lineup");
      }
      //Throws errors if invalid.
      comp.addMotion(new BasicMotion(new State(x1,y1,w1,h1,r1,g1,b1), new State(x2,y2,w2,h2,r2,g2,b2),t1,t2));
      return this;
    }

    @Override
    public AnimationBuilder<IModel> addKeyframe(String name, int t, int x, int y, int w, int h, int r, int g, int b) {
      return this;
    }
  }


}
