package cs3500.excellence.model.components;

import java.util.ArrayList;
import java.util.List;

import cs3500.excellence.model.IMotion;

/**
 * Represents a Rectangle shaped Component.
 */
public final class Rectangle extends AComponent {

  /**
   * Constructs a rectangle.
   */
  public Rectangle() {
    //This does nothing because the abstract class takes care of it.
  }

  /**
   * A Copy constructor for Rectangle.
   *
   * @param rectangle - Rectangle to copy.
   */
  public Rectangle(Rectangle rectangle) {
    if (rectangle == null) {
      throw new IllegalArgumentException("cannot be null");
    }
    List<IMotion> copy = new ArrayList<>();
    for (IMotion motion : rectangle.motions) {
      copy.add(motion.copy());
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
