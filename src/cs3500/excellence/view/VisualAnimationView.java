package cs3500.excellence.view;

import cs3500.excellence.model.BasicMotion;
import cs3500.excellence.model.Boundary;
import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.Component;
import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.model.components.IROComponent;
import cs3500.excellence.model.components.Shape;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class VisualAnimationView extends JFrame implements IView, ActionListener {
  private VisualAnimationPanel panel;
  private int speed;
  private int finalTick;
  private Boundary boundary;
  List<IROComponent> components;
  private int currentTick;
  private Timer tickTimer;

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
    this.speed = speed;
    this.boundary = boundary;
    findFinalTick();
    setSize(new Dimension(boundary.getWidth() + boundary.getX(), boundary.getHeight() + boundary.getY()));
    tickTimer = new Timer(1000/speed, this);
    tickTimer.start();
  }

  @Override
  public void setOutput(Appendable app) {
    throw new UnsupportedOperationException();
  }

//
//  @Override
//  public Dimension getPreferredSize() {
//    return );
//  }

  public void findFinalTick() {
    int output = 0;
    for (IROComponent component : components) {
      int finalTick = component.getFinalTick();
      if (finalTick > output) {
        output = finalTick;
      }
    }
    finalTick = output;
  }

  public void drawFrame(int tick){
    List<State> states = new ArrayList();
    List<Shape> shapes = new ArrayList();
    for(IROComponent c : components){
      if(c.hasMotionAtTick(tick)){
        states.add(c.getStateAtTick(tick));
        shapes.add(c.getShape());
      }
    }
    panel.updatePanelStates(states, shapes, boundary);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    drawFrame(currentTick++);
    setVisible(true);
    this.repaint();
    if(currentTick>= finalTick) {
      tickTimer.stop();
    }
  }

  public static final class errPanel{
    public void error(String msg){
      JOptionPane optionPane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);
      JDialog dialog = optionPane.createDialog("Parse Failed");
      dialog.setAlwaysOnTop(true);
      dialog.setVisible(true);
    }
  }
}
