package cs3500.excellence.model.hw05;

import java.util.List;
import java.util.Set;

import cs3500.excellence.model.hw05.components.IComponent;

public interface IModel {


  /**
   * Adds a shape to the model, each shape has a unique id.
   *
   *
   * Must be added in chronological order. For future projects, a factory could be used
   */
  void addComponent(String id, IComponent component);


  /**
   * Adds a motion to the specified component
   */
  void addMotion(String id, State initialState, State endState, int initialTick, int endTick);

  /**
   * Make sure List is a copy
   *
   * Gets Set of Components at certain tick.
   */
  List<IComponent> getComponentsAtTick(int tick);


  /**
   * Make sure List is a copy
   * @return
   */
  Set<String> getAllIds();

  String getOverview();


}