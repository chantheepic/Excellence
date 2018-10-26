package cs3500.excellence.model.hw05;

import cs3500.excellence.model.hw05.shapes.IShape;
import java.util.ArrayList;
import java.util.HashMap;

public class Model {
  HashMap<String, IShape> registeredShapes;

  // Do we allow the shape to change?
  public void addObject(String id, IShape shape) {
    if(!registeredShapes.containsKey(id)){
      registeredShapes.put(id, shape);
    } else{
      throw new IllegalArgumentException("SmartShape already exists");
    }
  }

  public void addMove(String id, State initialState, State endState, int initialTick, int endTick){
    if(registeredShapes.containsKey(id)){
      IShape s = registeredShapes.get(id);
      s.addCommand(initialState, endState, initialTick, endTick);
    } else{
      throw new IllegalArgumentException("SmartShape does not exist");
    }
  }

  public State getStateAtTick(String id, int tick) {
    if(registeredShapes.containsKey(id)){
      IShape s = registeredShapes.get(id);
      return s.getStateAtTick(tick);
    } else{
      throw new IllegalArgumentException("SmartShape does not exist");
    }
  }

  public String getOverView(){
    return null;
  }
}
