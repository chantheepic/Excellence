package cs3500.excellence.model.hw05.components;

import java.util.ArrayList;
import java.util.List;

import cs3500.excellence.model.hw05.IMotion;


public final class Ellipse extends AComponent {

  public Ellipse() {

  }

  public Ellipse(Ellipse e) {
    List<IMotion> copy = new ArrayList<>();
    for(IMotion motion : e.motions) {
      copy.add(motion.clone());
    }
    this.motions = copy;
  }

  public String toString() {
    return "ellipse";
  }

  @Override
  public IComponent copy() {
    return new Ellipse(this);
  }
}
