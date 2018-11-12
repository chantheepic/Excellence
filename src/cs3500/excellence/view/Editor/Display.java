package cs3500.excellence.view.Editor;

import cs3500.excellence.view.VisualAnimationView;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JFrame {
  EditorView c;
  VisualAnimationView view;
  JPanel container;

  public Display(EditorView c){
    this.c = c;
    container = new JPanel();
    container.setPreferredSize(new Dimension(200,200));
  }

  public JPanel returnPanel(){
    return container;
  }

  public void updateParam(VisualAnimationView view) {
    this.view = view;
    this.repaint();
  }
}
