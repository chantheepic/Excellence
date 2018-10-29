package cs3500.excellence.model.hw05.components;

import java.util.ArrayList;


public final class Ellipse extends AComponent {

  public Ellipse() {

  }

  public Ellipse(Ellipse e) {
    this.motions = new ArrayList<>(e.motions);
  }

  public String toString() {
    return "ellipse";
  }

  @Override
  public IComponent clone() {
    return new Ellipse(this);
  }
}
