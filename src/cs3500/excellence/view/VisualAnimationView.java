package cs3500.excellence.view;

import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IROComponent;
import cs3500.excellence.model.components.Shape;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class VisualAnimationView extends JFrame implements IView {
  private VisualAnimationPanel panel;
  private int speed;
  private int finalTick;
  private int[] offset;
  private int[] windowSize;
  List<IROComponent> components;

  public VisualAnimationView() {
    super();
    this.panel = new VisualAnimationPanel();
    add(panel);
    setTitle("EXCELLENCE");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void setComponents(List<IROComponent> components, int[] boundary, int speed) {
    this.components = components;
    this.speed = speed;
    this.offset = new int[]{boundary[0], boundary[1]};
    this.windowSize = new int[]{boundary[2], boundary[3]};
    findFinalTick();
    animate();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(windowSize[0], windowSize[1]);
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


  // Implementation Specifics
  public void animate(){
    setSize(getPreferredSize());
    for(int tick = 0; tick < finalTick; tick++){
      try{
        Thread.sleep(1000/speed); //~60fps
        drawFrame(tick);
        setVisible(true);
        this.repaint();
      } catch (InterruptedException e){
        System.out.println(e.getMessage());
      }
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
    panel.updatePanelStates(states, shapes, offset);
  }

}
