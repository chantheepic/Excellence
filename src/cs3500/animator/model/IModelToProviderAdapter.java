package cs3500.animator.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.components.IROComponent;
import cs3500.animator.model.components.IROComponentToShapeAdapter;
import cs3500.animator.provider.model.classes.Bounds;
import cs3500.animator.provider.model.interfaces.EasyAnimatorModelReadOnly;
import cs3500.animator.provider.model.interfaces.Shape;

/**
 * This class adapts an IModel to the providers version of a read only model. We hade to include a
 * speed variable because there model was responsible for tracking the speed.
 */
public class IModelToProviderAdapter implements EasyAnimatorModelReadOnly {

  private IModel adaptee;
  private int speed;


  /**
   * Constructs an IModelToProviderAdapter with the given adaptee.
   *
   * @param adaptee - the given IModel to use
   * @param speed   - the given speed to use
   */
  public IModelToProviderAdapter(IModel adaptee, int speed) {
    this.adaptee = adaptee;
    this.speed = speed;
  }

  @Override
  public int getTempo() {
    return this.speed;
  }

  /**
   * Gets the list of Shapes by adapting the IROComponent to a Shape.
   *
   * @return list of Shapes
   */
  @Override
  public List<Shape> getShapes() {
    List<Shape> output = new ArrayList<>();
    for (IROComponent component : adaptee.getAllComponents()) {
      output.add(new IROComponentToShapeAdapter(component));
    }

    return output;
  }

  @Override
  public Bounds getBounds() {
    Boundary bounds = adaptee.getBoundary();
    return new Bounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
  }


}
