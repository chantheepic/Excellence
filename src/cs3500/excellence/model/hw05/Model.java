package cs3500.excellence.model.hw05;

import cs3500.excellence.model.hw05.components.IComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Model implements IModel {
  HashMap<String, IComponent> registeredShapes;
  List<String> compIDs;


  public Model(){
    this.registeredShapes = new HashMap<>();
    this.compIDs = new ArrayList<>();
  }

  // Do we allow the component to change?
  //no^
  @Override
  public void addComponent(String id, IComponent component) {
    if(!registeredShapes.containsKey(id)){
      registeredShapes.put(id, component);
      compIDs.add(id);
    } else{
      throw new IllegalArgumentException("Object already exists");
    }
  }

  public void addMotion(String id, State initialState, State endState, int initialTick, int endTick){
    if(registeredShapes.containsKey(id)){
      IComponent component = registeredShapes.get(id);
      component.addMotion(new BasicMotion(initialState,endState,initialTick,endTick));
    } else{
      throw new IllegalArgumentException("Object does not exist");
    }
  }

  private State getStateAtTick(String id, int tick) {
    if(registeredShapes.containsKey(id)){
      IComponent s = registeredShapes.get(id);
      return s.getStateAtTick(tick);
    } else{
      throw new IllegalArgumentException("Object does not exist");
    }
  }

  @Override
  public Set<IComponent> getComponentsAtTick(int tick) {
    Set<IComponent> output = new HashSet<>();
    for (IComponent comp : registeredShapes.values()){
      output.add(comp.clone());
    }

    return output;


  }

  @Override
  public List<String> getAllIds() {
    return compIDs;
  }

  public String getOverview(){
    StringBuilder output = new StringBuilder();
    for( String componentId : compIDs) {
      IComponent component = registeredShapes.get(componentId);
      output.append("shape " + componentId + " " + component).append("\n");
      output.append(component.getOverview(componentId)).append("\n\n");
    }
    return output.toString();
  }
}
