package cs3500.animator.view;

import cs3500.animator.controller.Features;
import cs3500.animator.model.Boundary;
import cs3500.animator.model.State;
import cs3500.animator.model.components.IROComponent;
import cs3500.animator.model.components.Shape;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class VisualAnimationView extends JFrame implements IView {

  private VisualAnimationPanel panel;
  private int finalTick;
  private Boundary boundary;
  List<IROComponent> components;
  private int currentTick;

  Features listener;

  /**
   * Constructor for VisualAnimationVIew. Initializes all necessary swing components before the
   * shapes are drawn.
   */
  public VisualAnimationView() {
    super();
    this.panel = new VisualAnimationPanel();
    add(panel);
    setTitle("EXCELLENCE");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    currentTick = 0;
  }

  @Override
  public void setComponents(List<IROComponent> components, Boundary boundary, int speed) {
    this.components = components;
    this.boundary = boundary;
    findFinalTick();
    setSize(getPreferredSize());
    listener.togglePlay();

  }

  @Override
  public void setOutput(Appendable app) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setFeatureListener(Features listener) {
    this.listener = listener;
  }

  @Override
  public void displayError(String msg) {
    ErrPanel.error(msg);
  }

  @Override
  public void tick(int currentTick) {
    drawFrame(currentTick);
    setVisible(true);
    this.repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(boundary.getWidth() + boundary.getX(),
        boundary.getHeight() + boundary.getY());
  }

  /**
   * Finds overall final tick of an object.
   */
  private void findFinalTick() {
    int output = 0;
    for (IROComponent component : components) {
      int finalTick = component.getFinalTick();
      if (finalTick > output) {
        output = finalTick;
      }
    }
    finalTick = output;
  }

  /**
   * When method is called, the states of all components at tick are updated to the panel.
   *
   * @param tick tick value
   */
  private void drawFrame(int tick) {
    List<State> states = new ArrayList();
    List<Shape> shapes = new ArrayList();
    for (IROComponent c : components) {
      if (c.hasMotionAtTick(tick)) {
        states.add(c.getStateAtTick(tick));
        shapes.add(c.getShape());
      }
    }
    panel.updatePanelStates(states, shapes, boundary);
  }



}
