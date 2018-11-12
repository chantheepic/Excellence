package cs3500.excellence.view.Editor;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Parameters extends JFrame {
  EditorView c;

  private JPanel container;
  private JLabel label;
  private JTextField fileName;
  private JTextField dimension;
  private JTextField speed;

  public  Parameters(EditorView c){
    this.c = c;

    container = new JPanel();
    container.setLayout(new GridLayout(4,1));

    label = new JLabel("Parameters");

    fileName = new JTextField("File Name: ");
    fileName.setEditable(false);

    dimension = new JTextField("Dimension: ");
    dimension.setEditable(false);

    speed = new JTextField("Speed: ");
    speed.setEditable(false);

    container.add(label);
    container.add(fileName);
    container.add(dimension);
    container.add(speed);

    container.setPreferredSize(new Dimension(200,100));
  }

  public JPanel returnPanel(){
    return container;
  }

  public void updateParam(String fileName, Dimension dimension, int speed) {
    this.fileName.setText("FIle Name: " + fileName);
    this.dimension.setText("Dimension: " + dimension.width + " x " + dimension.height);
    this.speed.setText("Speed: " + speed);
  }
}
