package cs3500.excellence.view.Editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Parameters extends JPanel implements ActionListener, ItemListener, ListSelectionListener {
  EditorView editor;
  private JPanel container;
  private JLabel label;
  private JTextField fileName;
  private JTextField dimension;
  private JPanel speed;
  private JTextField currentSpeed;
  private JTextField newSpeed;

  public  Parameters(EditorView c){
    this.editor = c;

    container = new JPanel();
    container.setLayout(new GridLayout(5,1));

    label = new JLabel("Parameters");

    fileName = new JTextField("File Name: ");
    fileName.setEditable(false);

    dimension = new JTextField("Dimension: ");
    dimension.setEditable(false);

    speed = new JPanel();
    speed.setLayout(new GridLayout(1,5));
    currentSpeed = new JTextField("Speed: ");
    currentSpeed.setEditable(false);
    speed.add(currentSpeed);
    speed.add(new JPanel());
    speed.add(new JLabel("New Speed:"));

    newSpeed = new JTextField();
    speed.add(newSpeed);
    newSpeed.setForeground(new Color(116, 116, 116));

    JButton setSpeed = new JButton("set");
    speed.add(setSpeed);
    setSpeed.setActionCommand("set");
    setSpeed.addActionListener(this);

    container.add(label);
    container.add(fileName);
    container.add(dimension);
    container.add(speed);

    container.setPreferredSize(new Dimension(200,100));
  }

  public JPanel returnPanel(){
    return container;
  }

  public void updateParam(Dimension dimension, int speed) {
    this.fileName.setText("FIle Name: " + "blahhh");
    this.dimension.setText("Dimension: " + dimension.width + " x " + dimension.height);
    this.currentSpeed.setText("Speed: " + speed);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getActionCommand().equals("set")){
      try{
        int i = Integer.parseInt(newSpeed.getText());
        editor.changeSpeed(i);
      } catch (NumberFormatException format){

      }
    }
  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }
}
