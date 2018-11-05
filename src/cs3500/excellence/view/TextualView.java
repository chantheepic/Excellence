package cs3500.excellence.view;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.components.IComponent;
import java.util.List;

public class TextualView implements IView{
  private int tick;
  List<IComponent> components;

  @Override
  public void update(int tick, List<IComponent> components) {
    this.tick = tick;
    this.components = components;
  }

  @Override
  public void drawFrame() {
    for(IComponent c : components) {
      IMotion m = c.getMotionAtTick(tick);
      System.out.println(m.initialTick() + " " + m.endTick());
    }
  }
}
