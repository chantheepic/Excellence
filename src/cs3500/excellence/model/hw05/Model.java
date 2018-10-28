package cs3500.excellence.model.hw05;

import cs3500.excellence.model.hw05.shapes.IComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Model implements IModel {
  HashMap<String, IComponent> registeredShapes;
  List<String> compIDs;


  public Model(){

    this.registeredShapes = new HashMap<>();
    this.compIDs = new ArrayList<>();
  }


  // Do we allow the shape to change?
  //no^
  @Override
  public void addShape(String id, IComponent shape) {
    if(!registeredShapes.containsKey(id)){
      registeredShapes.put(id, shape);
      compIDs.add(id);
    } else{
      throw new IllegalArgumentException("Object already exists");
    }
  }


  public void addMotion(String id, State initialState, State endState, int initialTick, int endTick){
    if(registeredShapes.containsKey(id)){
      IComponent component = registeredShapes.get(id);
      //TODO: NOT LOOSELY COUPLED
      IMotion motion = new BasicMotion(initialState,endState,initialTick,endTick);
      component.addMotion(motion);
    } else{
      throw new IllegalArgumentException("Object does not exist");
    }
  }

  public State getStateAtTick(String id, int tick) {
    if(registeredShapes.containsKey(id)){
      IComponent s = registeredShapes.get(id);
      return s.getStateAtTick(tick);
    } else{
      throw new IllegalArgumentException("Object does not exist");
    }
  }

  public String getOverview(){
    StringBuilder output = new StringBuilder();
    String title = String.format("%s%3s%3s%3s%3s%3s%3s%3s","t","x","y","w","h","r","g","b");
    output.append(title).append(title).append("\n");
    for( String component : compIDs) {
      output.append(registeredShapes.get(component).getOverview()).append("\n\n");
    }
    return output.toString();
  }
}
