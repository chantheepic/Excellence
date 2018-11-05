package cs3500.excellence.view;

import cs3500.excellence.model.State;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;

public class VisualAnimationPanel extends JPanel {
  private List<State> states;
  public VisualAnimationPanel(){
  }

  public void updatePanelStates(List<State> states) {
    this.states = states;
  }

  private void drawShape(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    for(State s : states){
      g2d.setPaint(new Color(s.red(), s.green(), s.blue()));
      g2d.fillRect(s.xPos(), s.yPos(), s.width(), s.height());
    }
  }

    @Override
    public void paintComponent (Graphics g) {
      super.paintComponent(g);
      drawShape(g);
    }
}
