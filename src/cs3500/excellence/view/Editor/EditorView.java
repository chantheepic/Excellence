package cs3500.excellence.view.Editor;

import cs3500.excellence.view.VisualAnimationView;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EditorView extends JFrame{
  public static void main(String[] args) throws InterruptedException {
    new EditorView();
  }

  Export export;
  Parameters param;
  Display display;
  Interactive interactive;

  String fileName;
  Dimension dimension;
  int speed;

  JPanel leftPanel;
  JPanel topPanel;
  JPanel container;

  EditorView() throws InterruptedException {
    this.export = new Export(this);
    this.param = new Parameters(this);
    this.display = new Display(this);
    this.interactive = new Interactive(this);

    leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(2,1));
    leftPanel.add(param.returnPanel());
    leftPanel.add(export.returnPanel());

    topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(1,2));
    topPanel.add(leftPanel);
    topPanel.add(display.returnPanel());

    container = new JPanel();
    container.setLayout(new GridLayout(2,1));
    container.add(topPanel);
    container.add(interactive.returnPanel());

    this.add(container);

    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);

    Thread.sleep(5000);

    System.out.println("run");
    updateParameters("new name", new Dimension(200, 100), 130);
    updateDisplay();
  }

  public void updateDisplay(){
    VisualAnimationView view = new VisualAnimationView();
    view.sampleView();
    display.updateParam(view);
  }

  public void updateExport(){
    export.updateParam();
    this.repaint();
  }

  public void updateInteractive(){
    interactive.updateParam();
    this.repaint();
  }

  public void updateParameters(String fileName, Dimension dimension, int speed){
    param.updateParam(fileName, dimension, speed);
  }


}
