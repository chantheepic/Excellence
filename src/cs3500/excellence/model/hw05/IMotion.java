package cs3500.excellence.model.hw05;

public interface IMotion {

    State getStateAtTick(int tick);

    boolean commonEndpoint(IMotion nextMotion);



}
