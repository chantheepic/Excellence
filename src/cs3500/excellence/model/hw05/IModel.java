package cs3500.excellence.model.hw05;

import cs3500.excellence.model.hw05.shapes.IShape;

public interface IModel {


  /**
   * Adds a shape to the model, each shape has a unique id.
   * @param id
   * @param shape
   */
  void addShape(String id, IShape shape);

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

  String getOverview();



}
