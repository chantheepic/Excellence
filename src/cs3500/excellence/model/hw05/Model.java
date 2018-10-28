package cs3500.excellence.model.hw05;

import cs3500.excellence.model.hw05.shapes.IShape;

import java.util.HashMap;

public class Model implements IModel {
  HashMap<String, IShape> registeredShapes;

  public Model(){
    this.registeredShapes = new HashMap<>();
  }

  // Do we allow the shape to change?
  @Override
  public void addShape(String id, IShape shape) {
    if(!registeredShapes.containsKey(id)){
      registeredShapes.put(id, shape);
    } else{
      throw new IllegalArgumentException("Object already exists");
    }
  }


  public void addCommand(String id, State initialState, State endState, int initialTick, int endTick){
    if(registeredShapes.containsKey(id)){
      IShape s = registeredShapes.get(id);
      s.addCommand(initialState, endState, initialTick, endTick);
    } else{
      throw new IllegalArgumentException("Object does not exist");
    }
  }

  public State getStateAtTick(String id, int tick) {
    if(registeredShapes.containsKey(id)){
      IShape s = registeredShapes.get(id);
      return s.getStateAtTick(tick);
    } else{
      throw new IllegalArgumentException("Object does not exist");
    }
  }

  public String getOverView(){
    return null;
  }
}
