package cs3500.excellence.view;

import cs3500.excellence.model.components.IComponent;
import java.util.List;

public interface IView {

  void update(int tick, List<IComponent> components);
  void drawFrame();
}
