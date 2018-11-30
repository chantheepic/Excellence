package cs3500.animator.provider.view;

import cs3500.animator.provider.view.interfaces.View;
import cs3500.animator.provider.model.interfaces.EasyAnimatorModelReadOnly;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 * An implementation of a visual view of an Easy Animator model.
 */
public class VisualViewImpl extends JFrame implements View, ActionListener {

  private ViewPanel panel;
  private Timer timer;
  private int tempo;

  /**
   * Constructs a visual view of the given model.
   *
   * @param model the model to be displayed
   */
  public VisualViewImpl(EasyAnimatorModelReadOnly model) {
    super();
    // initial tempo of the view is 1
    this.tempo = 1;
    this.panel = new ViewPanel(model.getShapes());

    this.setTitle("Easy Animator");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JScrollPane scroll = new JScrollPane(panel);

    this.setLayout(new BorderLayout());
    this.add(scroll, BorderLayout.CENTER);
    panel.setPreferredSize(new Dimension(500, 500));

    timer = new Timer(Math.round(1000 * (1 / tempo)), this);

    this.pack();
  }

  @Override
  public void display() {
    this.setVisible(true);
    timer.start();
  }

  // TODO decide if we need
  @Override
  public void refresh() {
    this.panel.animateShapes();
    this.panel.repaint();
    this.panel.revalidate();
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    panel.animateShapes();
    panel.repaint();
    panel.revalidate();
  }

}
