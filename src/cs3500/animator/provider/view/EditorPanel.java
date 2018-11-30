package cs3500.animator.provider.view;

import cs3500.animator.provider.model.interfaces.Shape;
import java.util.List;

/**
 * Represents the panel to be used for editing.
 */
public class EditorPanel extends ViewPanel {

  private List<Shape> shapes;
  private int tick;

  /**
   * Constructs an editor panel.
   *
   * @param shapes the list of shapes to construct the view with.
   * @throws IllegalArgumentException if given a null list of shapes
   */
  public EditorPanel(List<Shape> shapes) throws IllegalArgumentException {
    super(shapes);
  }

}
