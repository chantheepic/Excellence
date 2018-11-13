package cs3500.excellence.view.Editor;

import cs3500.excellence.model.Boundary;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IROComponent;
import cs3500.excellence.model.components.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class EditorView extends JFrame implements ActionListener {

  private Export export;
  private Parameters param;
  private Display display;
  private Interactive interactive;
  private JPanel leftPanel;
  private JPanel topPanel;
  private JPanel container;

  private int speed;
  private int finalTick;
  List<IROComponent> components;
  private int currentTick;
  private Timer tickTimer;
  private Boundary boundary;


  EditorView() throws InterruptedException {
    super();
    this.export = new Export(this);
    this.param = new Parameters(this);
    this.display = new Display(this);
    this.interactive = new Interactive(this);

    leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(3,1));
    leftPanel.add(param.returnPanel());
    leftPanel.add(export.returnPanel());
    // Blank panel as spacer
    leftPanel.add(new JPanel());

    topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(1,2));
    topPanel.add(leftPanel);
    topPanel.add(display);

    container = new JPanel();
    container.setLayout(new GridLayout(2,1));
    container.add(topPanel);
    container.add(interactive.returnPanel());

    this.add(container);

    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  public void setComponents(List<IROComponent> components, Boundary boundary, int speed) {
    this.components = components;
    this.speed = speed;
    this.boundary = boundary;
    findFinalTick();
    display.setPreferredSize(new Dimension(boundary.getWidth() + boundary.getX(), boundary.getHeight() + boundary.getY()));
    interactive.setComponents(components);
    tickTimer = new Timer(10, this);
    tickTimer.start();
  }

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

  public void actionPerformed(ActionEvent e) {
    drawFrame(currentTick++);
    setVisible(true);
    this.repaint();
    if(currentTick>= finalTick) {
      tickTimer.stop();
    }
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
    display.updatePanelStates(states, shapes, boundary);
  }

  public void updateParameters(String fileName, Dimension dimension, int speed){
    param.updateParam(fileName, dimension, speed);
  }

}
