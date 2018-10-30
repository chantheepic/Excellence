package cs3500.excellence.model.hw05;

import cs3500.excellence.model.hw05.components.IComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Model implements IModel {
  SortedMap<String, IComponent> registeredShapes;

  public Model(){
    this.registeredShapes = new TreeMap<>();
  }

  @Override
  public void addComponent(String id, IComponent component) {
    if (component == null) {
      throw new IllegalArgumentException("Component cannot be null");
    }
    if(!registeredShapes.containsKey(id)){
      registeredShapes.put(id, component);
    } else{
      throw new IllegalArgumentException("Object already exists");
    }
  }

  public void addMotion(String id, State initialState, State endState, int initialTick, int endTick){
    if (initialState == null || endState == null) {
      throw new IllegalArgumentException("States cannot be null");
    }
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
  public List<IComponent> getComponentsAtTick(int tick) {
    List<IComponent> output = new ArrayList<>();
    for (IComponent comp : registeredShapes.values()){
      if(comp.hasMotionAtTick(tick)) {
        output.add(comp.copy());
      }
    }
    return output;
  }

  @Override
  public Set<String> getAllIds() {
    return registeredShapes.keySet();
  }


  public String getOverview(){
    StringBuilder output = new StringBuilder();
    for( String componentId : registeredShapes.keySet()) {
      IComponent component = registeredShapes.get(componentId);
      output.append("shape " + componentId + " " + component).append("\n");
      output.append(component.getOverview(componentId)).append("\n\n");
    }
    return output.toString();
  }
}
