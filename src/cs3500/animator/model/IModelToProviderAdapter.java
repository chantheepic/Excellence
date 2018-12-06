package cs3500.animator.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.components.IROComponentToShapeAdapter;
import cs3500.animator.model.components.IROComponent;
import cs3500.animator.provider.model.classes.Bounds;
import cs3500.animator.provider.model.interfaces.EasyAnimatorModelReadOnly;
import cs3500.animator.provider.model.interfaces.Shape;

public class IModelToProviderAdapter implements EasyAnimatorModelReadOnly {

  private IModel adaptee;
  private int speed;


  public IModelToProviderAdapter(IModel adaptee, int speed) {
    this.adaptee = adaptee;
    this.speed = speed;
  }

  @Override
  public int getTempo() {
    return this.speed;
  }

  @Override
  public List<Shape> getShapes() {
    List<Shape> output = new ArrayList<>();
    for (IROComponent component: adaptee.getAllComponents()) {
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
