package cs3500.excellence.view;

import cs3500.excellence.controller.Features;
import cs3500.excellence.model.Boundary;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IROComponent;
import cs3500.excellence.model.components.Shape;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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


  /**
   * Static class that creates a error popup when parsing has failed in the main method.
   */
  public static final class errPanel{
    public void error(String msg){
      JOptionPane optionPane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);
      JDialog dialog = optionPane.createDialog("Parse Failed");
      dialog.setAlwaysOnTop(true);
      dialog.setVisible(true);
    }
  }
}
