package cs3500.animator.provider.view;

import cs3500.animator.provider.model.interfaces.EasyAnimatorModelReadOnly;
import cs3500.animator.provider.model.interfaces.Shape;
import cs3500.animator.provider.view.interfaces.ExportView;
import cs3500.animator.provider.view.interfaces.View;
import java.io.IOException;

/**
 * Represents a textual view of the animation model. When put into the EasyAnimator runner,
 * should return a string containing the details of the shapes and their transformations.
 */
public class TextualView implements View, ExportView {

  private EasyAnimatorModelReadOnly model;
  private Appendable out;
  private int tempo;

  /**
   * Constructs a TextView object using the model, an appendable object that we will be writing
   * to, and a tempo (speed) as set by the user.
   *
   * @param model The model from which we will be generating the text view.
   * @param out The appendable object that we will be writing to.
   */
  public TextualView(EasyAnimatorModelReadOnly model, Appendable out) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("null model or out");
    }
    this.model = model;
    this.out = out;
    this.tempo = 1;
  }

  @Override
  public void display() throws IOException {
    StringBuilder modelPrint = new StringBuilder();
    modelPrint.append(model.getBounds().toString());
    modelPrint.append("\n");
    for (Shape shape : model.getShapes()) {
      modelPrint.append(shape.toString());
    }
    out.append(modelPrint.toString());
  }


  @Override
  public void refresh() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Cannot refresh for a textual view");
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void export(String fileToExportTo) throws IOException {
    // TODO implement.
  }
}
