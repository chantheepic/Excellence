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
  private int[] offset;

  public VisualAnimationPanel(){
  }

  public void updatePanelStates(List<State> states, List<Shape> shapes, int[] offset) {
    this.states = states;
    this.shapes = shapes;
    this.offset = offset;
  }

  private void drawShape(Graphics g) {
    Graphics2D gfx = (Graphics2D) g;
    for(int i = 0; i < states.size(); i++){
      State state = states.get(i);
      gfx.setPaint(new Color(state.red(), state.green(), state.blue()));

      switch(shapes.get(i)){
        case RECT:
          gfx.fillRect(state.xPos() - offset[0], state.yPos() - offset[1], state.width(), state.height());
          break;
        case ELLIPSE:
          gfx.fillOval(state.xPos() - offset[0], state.yPos() - offset[1], state.width(), state.height());
          break;

          default: throw new IllegalArgumentException("not a supported shape");
      }
    }
  }

    @Override
    public void paintComponent (Graphics g) {
      super.paintComponent(g);
      drawShape(g);
    }
}
