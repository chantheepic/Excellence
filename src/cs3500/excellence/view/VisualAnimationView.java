package cs3500.excellence.view;

import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IComponent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class VisualAnimationView extends JFrame implements IView {
  private VisualAnimationPanel panel;
  private int tick;
  List<IComponent> components;

  public VisualAnimationView() {
    super();
    this.panel = new VisualAnimationPanel();
    add(panel);
    setTitle("Basic shapes");
    setSize(500, 500);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  // Implementation Specifics
  public void update(int tick, List<IComponent> components){
    this.tick = tick;
    this.components = components;
    try{
      Thread.sleep(17); //~60fps
      drawFrame();
      this.repaint();
    } catch (InterruptedException e){
      System.out.println(e.getMessage());
    }
  }



  @Override
  public void setComponents(List<IComponent> components) {

  }



  public void drawFrame(){
    List<State> states = new ArrayList();
    for(IComponent c : components){
      states.add(c.getStateAtTick(tick));
    }
    panel.updatePanelStates(states);
  }

}
