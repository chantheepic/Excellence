package cs3500.excellence.view.Editor;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Interactive extends JFrame {
  EditorView c;
  JPanel elements;
  JPanel keyframes;
  JPanel parameters;
  JPanel container;

  //HashMap<String, > components;

  public Interactive (EditorView c){
    this.c = c;
    elements  = new JPanel();
    keyframes = new JPanel();
    parameters = new JPanel();
    container = new JPanel();

    container.add(elements);
    container.add(keyframes);
    container.add(parameters);
    container.setPreferredSize(new Dimension(400,200));
  }

  public JPanel returnPanel(){
    return container;
  }

  public void updateParam() {
  }
}
