package cs3500.excellence.view.Editor;

import cs3500.excellence.model.Boundary;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.Shape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class Display extends JPanel {
  EditorView editor;
  JPanel container;

  public Display(EditorView c){
    this.editor = c;
    container = new JPanel();
  }

  private List<State> states = new ArrayList<>();
  private List<Shape> shapes = new ArrayList<>();
  private Boundary boundary = new Boundary(0,0,0,0);

  public void updatePanelStates(List<State> states, List<Shape> shapes, Boundary boundary) {
    if(states == null || shapes == null || boundary == null){ return; }
    this.states = states;
    this.shapes = shapes;
    this.boundary = boundary;
  }

  private void doDrawing(Graphics g) {
    Graphics2D gfx = (Graphics2D) g;
    for(int i = 0; i < states.size(); i++){
      State state = states.get(i);
      gfx.setPaint(new Color(state.red(), state.green(), state.blue()));

      int scale = 3;

      switch(shapes.get(i)){
        case RECTANGLE:
          gfx.fillRect((state.xPos() - boundary.getX()) / scale, (state.yPos() - boundary.getY()) / scale, state.width() / scale, state.height() / scale);
          break;
        case ELLIPSE:
          gfx.fillOval((state.xPos() - boundary.getX()) / scale, (state.yPos() - boundary.getY()) / scale, state.width() / scale, state.height() / scale);
          break;

        default: throw new IllegalArgumentException("not a supported shap");
      }
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    doDrawing(g);
  }
}
