package cs3500.animator.provider.view;

import cs3500.animator.provider.model.interfaces.EasyAnimatorModelReadOnly;
import cs3500.animator.provider.view.interfaces.View;

/**
 * Handles delegation of creating different views in the form of either textual, SVG, or visual.
 * The factory will take in a string and a model and will differentiate between which view to
 * use based on the string.
 */
public class ViewFactory {

  private String view;
  private EasyAnimatorModelReadOnly model;
  private Appendable output;
  private View desiredView;

  /**
   * Constructs a View Factory by which we can call our factory method to create different instances
   * of views based on the given String argument.
   *
   * @param view    the specified view
   * @param model   the model to be viewed
   * @param output  the specified output of the view
   * @throws IllegalArgumentException if the view or model is null
   */
  public ViewFactory(String view, EasyAnimatorModelReadOnly model, Appendable output)
          throws IllegalArgumentException {
    if (view == null || model == null) {
      throw new IllegalArgumentException("View type or model can not be null.");
    }
    this.view = view;
    this.model = model;
    this.output = output;
    // default view will be textual
    desiredView = new TextualView(this.model, this.output);
  }

  /**
   * Creates and returns the type of view specified by the String specified in the constructor for
   * this class.
   */
  public View getView() {
    if (view.equals("text")) {
      desiredView = new TextualView(this.model, this.output);
    }
    if (view.equals("svg")) {
      desiredView = new SVGView(this.model, this.output, this.model.getTempo());
    }
    if (view.equals("visual")) {
      desiredView = new VisualViewImpl(this.model);
    }
    if (view.equals("edit")) {
      desiredView = new EditorViewImpl(this.model, this.output, this.model.getTempo());
    }
    return this.desiredView;
  }

  /**
   * Sets the output path for the view specified by the view factory.
   *
   * @param outputPath the output path
   * @throws IllegalArgumentException if the output path is null
   */
  private void setPath(Appendable outputPath) {
    this.output = outputPath;
  }
}
