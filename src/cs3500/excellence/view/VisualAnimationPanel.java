package cs3500.excellence.view;

import cs3500.excellence.model.State;
import cs3500.excellence.model.components.Shape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;

public class VisualAnimationPanel extends JPanel {
  private List<State> states;
  private List<Shape> shapes;

  public VisualAnimationPanel(){
  }

  public void updatePanelStates(List<State> states, List<Shape> shapes) {
    this.states = states;
    this.shapes = shapes;
  }

  private void drawShape(Graphics g) {
    Graphics2D gfx = (Graphics2D) g;
    for(int i = 0; i < states.size(); i++){
      State state = states.get(i);
      gfx.setPaint(new Color(state.red(), state.green(), state.blue()));

      switch(shapes.get(i)){
        case RECT:
          gfx.fillRect(state.xPos(), state.yPos(), state.width(), state.height());
        case ELLIPSE:
          gfx.fillOval(state.xPos(), state.yPos(), state.width(), state.height());
      }
    }
  }

    @Override
    public void paintComponent (Graphics g) {
      super.paintComponent(g);
      drawShape(g);
    }
}
