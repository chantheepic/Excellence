package cs3500.excellence.model.hw05.shapes;

import cs3500.excellence.model.hw05.State;

public interface IShape {

  void addCommand(State initialState, State endState, int initialTick, int endTick);


  State getStateAtTick(int tick);


  String getOverview(int initialTick, int endTick);
}
