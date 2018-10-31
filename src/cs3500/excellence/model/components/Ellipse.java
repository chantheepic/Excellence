package cs3500.excellence.model.components;

import java.util.ArrayList;
import java.util.List;

import cs3500.excellence.model.IMotion;

/**
 * Represents an Ellipse shaped Component.
 */
public final class Ellipse extends AComponent {


  /**
   * Constructs an ellispe.
   */
  public Ellipse() {
    //This does nothing because the abstract class takes care of it.
  }

  /**
   * A copy constructor for Ellipse.
   *
   * @param e - Ellipse to copy
   */
  public Ellipse(Ellipse e) {
    if (e == null) {
      throw new IllegalArgumentException("cannot be null");
    }
    List<IMotion> copy = new ArrayList<>();
    for (IMotion motion : e.motions) {
      copy.add(motion.copy());
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
