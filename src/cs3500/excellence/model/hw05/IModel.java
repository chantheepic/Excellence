package cs3500.excellence.model.hw05;

import java.util.List;

import cs3500.excellence.model.hw05.shapes.IComponent;

public interface IModel {


  /**
   * Adds a shape to the model, each shape has a unique id.
   * @param id
   * @param component
   */
  void addComponent(String id, IComponent component);

  /**
   * Adds a
   * @param id
   * @param initialState
   * @param endState
   * @param initialTick
   * @param endTick
   */
  void addMotion(String id, State initialState, State endState, int initialTick, int endTick);

  State getStateAtTick(String id, int tick);

  List<String> getAllIds();

  String getOverview();



}
