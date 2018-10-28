package cs3500.excellence.model.hw05;

public interface IMotion {

  State getStateAtTick(int tick);

  int initialTick();

  int endTick();

  String getOverview();

  boolean containsTick(int tick);

}
