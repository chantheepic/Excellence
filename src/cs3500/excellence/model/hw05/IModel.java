package cs3500.excellence.model.hw05;

import cs3500.excellence.model.hw05.shapes.IShape;

public interface IModel {


  void addShape(String id, IShape shape);

  void addCommand(String id, State initialState, State endState, int initialTick, int endTick);

  State getStateAtTick(String id, int tick);



}
