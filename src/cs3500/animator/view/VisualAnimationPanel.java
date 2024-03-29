package cs3500.animator.view;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import cs3500.animator.model.Boundary;
import cs3500.animator.model.State;
import cs3500.animator.model.components.Shape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


/**
 * VisualAnimationPanel holds the methods necessary to draw shapes on the visualvie and editorview.
 */
public class VisualAnimationPanel extends JPanel {

  private List<State> states = new ArrayList<>();
  private List<Shape> shapes = new ArrayList<>();
  private Boundary boundary = new Boundary(0, 0, 0, 0);

  /**
   * Constructor for VisualAnimationPanel. Emptiness is explained below.
   */
  public VisualAnimationPanel() {
    /**
     * Constructor is empty because the fields are set when updatePanelStates is called.
     */
  }

  /**
   * Updates the fields and shapes in the panel to prepare it to be redrawn when repaint is called.
   *
   * @param states states to be drawn.
   * @param shapes shapes that correlate to each state being drawn.
   * @param boundary boundary of the animation panel.
   */
  public void updatePanelStates(List<State> states, List<Shape> shapes, Boundary boundary) {
    this.states = states;
    this.shapes = shapes;
    this.boundary = boundary;
  }

  /**
   * Shapes are drawn and placed appropriately.
   *
   * @param g graphics
   * @throws IllegalArgumentException when unknown shape is given
   */
  private void drawShape(Graphics g) throws IllegalArgumentException {
    Graphics2D gfx = (Graphics2D) g;
    for (int i = 0; i < states.size(); i++) {
      State state = states.get(i);
      gfx.rotate(Math.toRadians(state.heading()) ,
              (state.xPos() - boundary.getX()) + state.width() / 2,
              (state.yPos() - boundary.getY()) + state.height() / 2 );

      gfx.setPaint(new Color(state.red(), state.green(), state.blue()));
      switch (shapes.get(i)) {
        case RECTANGLE:
          gfx.fillRect(state.xPos() - boundary.getX(), state.yPos() - boundary.getY(),
              state.width(), state.height());
          break;
        case ELLIPSE:
          gfx.fillOval(state.xPos() - boundary.getX(), state.yPos() - boundary.getY(),
              state.width(), state.height());
          break;

        default:
          throw new IllegalArgumentException("not a supported shape");
      }
      gfx.rotate(Math.toRadians(-state.heading()) ,
              (state.xPos() - boundary.getX()) + state.width() / 2,
              (state.yPos() - boundary.getY()) + state.height() / 2 );
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawShape(g);
  }
}
