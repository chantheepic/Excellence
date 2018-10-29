package cs3500.excellence.model.hw05.components;

import java.util.ArrayList;
import java.util.List;

import cs3500.excellence.model.hw05.BasicMotion;
import cs3500.excellence.model.hw05.IMotion;

public final class Rectangle extends AComponent {

  public Rectangle() {

  }

  public Rectangle(Rectangle rectangle) {
    List<IMotion> copy = new ArrayList<>();
    for(IMotion motion : rectangle.motions) {
      copy.add(motion.clone());
    }
    this.motions = new ArrayList<>(rectangle.motions);
  }

  public String toString() {
    return "rectangle";
  }

  @Override
  public IComponent copy() {
    return new Rectangle(this);
  }
}
