package cs3500.animator.view;

import java.io.IOException;
import java.util.List;

import cs3500.animator.controller.Features;
import cs3500.animator.model.Boundary;
import cs3500.animator.model.components.IROComponent;
import cs3500.animator.provider.view.interfaces.EditorView;

/**
 * This class adapts an EditorView to an IView. Thus allowing our main method and controller to use
 * this view.
 */
public class ProviderToIViewAdapter implements IView {

  private EditorView adaptee;

  /**
   * This constructor takes in an EditorView and uses it to adapt to IView.
   */
  public ProviderToIViewAdapter(EditorView adaptee) {
    this.adaptee = adaptee;
  }

  /**
   * This only displays the view and sets the speed becuase the composed view relies on mutation.
   *
   * @param components // Not needed
   * @param boundary // Not needed
   * @param speed // Not needed
   */
  @Override
  public void setComponents(List<IROComponent> components, Boundary boundary, int speed) {
    try {
      adaptee.display();
      adaptee.setTempo(speed);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method was not implemented because the composed view doesn't allow changing the output.
   *
   * @param app // Not needed
   */
  @Override
  public void setOutput(Appendable app) {
    // Supressed
  }


  /**
   * This method was not implemented because the composed view relies on mutation.
   *
   * @param currentTick // Not needed
   */
  @Override
  public void tick(int currentTick) {
    // Supressed
  }

  /**
   * This method was not implemented because the composed view never uses a feature listener.
   *
   * @param listener // Not needed
   */
  @Override
  public void setFeatureListener(Features listener) {
    // Supressed
  }

  @Override
  public void displayError(String msg) {
    ErrPanel.error(msg);
  }
}
