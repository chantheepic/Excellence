package cs3500.excellence.view;

import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IComponent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class VisualAnimationFrame extends JFrame implements IView {
  private VisualAnimationPanel panel;
  private int tick;
  List<IComponent> components;

  public VisualAnimationFrame(List<IComponent> components, int initialTick) {
    super();
    this.panel = new VisualAnimationPanel();
    this.components = components;
    this.tick = initialTick;
    add(panel);
    setTitle("Basic shapes");
    setSize(500, 500);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  // Implementation Specifics
  public void drawFrame(){
    List<State> states = new ArrayList();
    for(IComponent c : components){
      states.add(c.getStateAtTick(tick));
    }
    panel.updatePanelStates(states);
  }

  public void animate(){
    while(true){
      try{
        Thread.sleep(41);
        //System.out.println(tick);
        tick++;
        drawFrame();
        this.repaint();
      } catch (InterruptedException e){
        System.out.println(e.getMessage());
      }

    }
  }

  // MAIN
//  public static void main(String[] args) {
//    State s = new State(1,1,100,100,100,1,1);
//    List<State> ls = new ArrayList();
//    ls.add(s);
//    VisualAnimationFrame ex = new VisualAnimationFrame(ls);
//    ex.drawFrame();
//    ex.setVisible(true);
//  }
}
