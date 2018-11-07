package cs3500.excellence.view;

import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.model.components.Shape;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class VisualAnimationView extends JFrame implements IView {
  private VisualAnimationPanel panel;
  private int finalTick;
  private int canvasX;
  private int canvasY;
  List<IComponent> components;

  public VisualAnimationView() {
    super();
    this.panel = new VisualAnimationPanel();
    this.canvasX = 500;
    this.canvasY = 500;
    add(panel);
    setTitle("Basic shapes");
    setSize(canvasX, canvasY);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }


  @Override
  public void setComponents(List<IComponent> components) {
    this.components = components;
    findFinalTick();
    animate();
  }

  public void findFinalTick() {
    int output = 0;
    for (IComponent component : components) {
      int finalTick = component.getFinalTick();
      if (finalTick > output) {
        output = finalTick;
      }
    }
    finalTick = output;
  }

  public void autoFitCanvas() {
  }


  // Implementation Specifics
  public void animate(){
    for(int tick = 0; tick < finalTick; tick++){
      try{
        Thread.sleep(17); //~60fps
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
    for(IComponent c : components){
      if(c.hasMotionAtTick(tick)){
        states.add(c.getStateAtTick(tick));
        shapes.add(c.getShape());
      }
    }
    panel.updatePanelStates(states, shapes);
  }

}
