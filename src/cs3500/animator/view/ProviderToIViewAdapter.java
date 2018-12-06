package cs3500.animator.view;

import java.io.IOException;
import java.util.List;

import cs3500.animator.controller.Features;
import cs3500.animator.model.Boundary;
import cs3500.animator.model.components.IROComponent;
import cs3500.animator.provider.view.interfaces.EditorView;

public class ProviderToIViewAdapter implements IView {

  private EditorView adaptee;
  private Features listenter;


  /**
   * This constructor takes in an EditorView and uses it to adape to IView
   * @param adaptee
   */
  public ProviderToIViewAdapter(EditorView adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void setComponents(List<IROComponent> components, Boundary boundary, int speed) {

    try {
      adaptee.display();
      adaptee.setTempo(speed);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setOutput(Appendable app) {

  }

  @Override
  public void tick(int currentTick) {

  }

  @Override
  public void setFeatureListener(Features listener) {
    this.listenter = listener;

  }

  @Override
  public void displayError(String msg) {

  }
}
