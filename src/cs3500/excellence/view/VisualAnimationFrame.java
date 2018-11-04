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

  public VisualAnimationFrame(List<IComponent> components, int tick) {
    super();
    this.components = components;
    this.tick = tick;
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
    panel = new VisualAnimationPanel(states);
    add(panel);
  }

  public void animate(){

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
