package cs3500.excellence.view.Editor;

import cs3500.excellence.model.Boundary;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IROComponent;
import cs3500.excellence.model.components.Shape;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EditorView extends JFrame implements ActionListener, ItemListener, ListSelectionListener {

  private ImportExport export;
  private Parameters param;
  private Display display;
  private Interactive interactive;
  private JPanel leftPanel;
  private JPanel topPanel;
  private JPanel container;

  private String filename;
  private int speed;
  private int finalTick;
  private boolean loop = false;
  List<IROComponent> components;
  private int currentTick;
  private JPanel currentTickLabel;
  private Timer tickTimer;
  private Boundary boundary;



  EditorView() throws InterruptedException {
    super();
    this.export = new ImportExport(this);
    this.param = new Parameters(this);
    this.display = new Display(this);
    this.interactive = new Interactive(this);

    leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(3,1));
    leftPanel.add(param.returnPanel());
    leftPanel.add(export.returnPanel());

    JCheckBox[] loop = new JCheckBox[1];
    loop[0] = new JCheckBox("Loop");
    loop[0].setSelected(false);
    loop[0].setActionCommand("loop");
    loop[0].addActionListener(this);

    // Playback is built in because it needs access to the timer
    JPanel playback = new JPanel();
    playback.add(new Label("Playback"));
    currentTickLabel = new JPanel();
    playback.add(currentTickLabel);
    playback.add(loop[0]);
    playback.setLayout(new GridLayout(2,3));

    JButton resume = new JButton("Resume");
    resume.setActionCommand("resume");
    resume.addActionListener(this);
    playback.add(resume);

    JButton pause = new JButton("Pause");
    pause.setActionCommand("pause");
    pause.addActionListener(this);
    playback.add(pause);

    JButton restart = new JButton("Restart");
    restart.setActionCommand("restart");
    restart.addActionListener(this);
    playback.add(restart);

    leftPanel.add(playback);

    topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(1,2));
    topPanel.add(leftPanel);
    topPanel.add(display);

    container = new JPanel();
    container.setLayout(new GridLayout(2,1));
    container.add(topPanel);
    container.add(interactive.returnPanel());

    this.add(container);

    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  // Previously setComponents();
  public void setInitial(List<IROComponent> components, Boundary boundary, String filename, int speed) {
    this.speed = speed;
    this.filename = filename;
    this.components = components;
    this.boundary = boundary;
    findFinalTick();
    display.setPreferredSize(new Dimension(this.boundary.getWidth() + this.boundary.getX(), this.boundary.getHeight() + this.boundary.getY()));
    interactive.setComponents(this.components);
    param.updateParam(this.filename, new Dimension(boundary.getWidth(), boundary.getHeight()), speed);
    tickTimer = new Timer(1000/this.speed, this);
    tickTimer.start();
  }

  public void findFinalTick() {
    int output = 0;
    for (IROComponent component : components) {
      int finalTick = component.getFinalTick();
      if (finalTick > output) {
        output = finalTick;
      }
    }
    finalTick = output;
  }

  public void actionPerformed(ActionEvent e) {
    if(e.getActionCommand() == null){
      drawFrame(currentTick++);
      currentTickLabel.removeAll();
      currentTickLabel.add(new Label(String.valueOf(currentTick)));
      setVisible(true);
      this.repaint();
      if(currentTick>= finalTick) {
        if(loop == true){
          currentTick = 0;
        } else {
          tickTimer.stop();
        }
      }
      return;
    }
    if(e.getActionCommand().equals("pause")){
      tickTimer.stop();
    }
    if(e.getActionCommand().equals("resume")){
      tickTimer.start();
    }
    if(e.getActionCommand().equals("restart")){
      currentTick = 0;
      tickTimer.start();
    }
    if(e.getActionCommand().equals("loop")){
      loop = !loop;
    }
  }

  public void drawFrame(int tick){
    List<State> states = new ArrayList();
    List<Shape> shapes = new ArrayList();
    for(IROComponent c : components){
      if(c.hasMotionAtTick(tick)){
        states.add(c.getStateAtTick(tick));
        shapes.add(c.getShape());
      }
    }
    int scale = boundary.getHeight() / 300;
    if(boundary.getHeight() < boundary.getWidth()){
      scale = boundary.getWidth() / 300;
    }
    display.updatePanelStates(states, shapes, boundary, scale);

    // calling repaint twice enables us to avoid the glitching when manipulating keyframePanel while
    // the animation is playing.
    this.repaint();
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }

  public void changeSpeed(int speed) {
    this.speed = speed;
    tickTimer.stop();
    tickTimer.removeActionListener(this);
    this.tickTimer = new Timer(1000/this.speed, this);
    tickTimer.start();
    param.updateParam(filename, new Dimension(boundary.getWidth(), boundary.getHeight()), speed);
  }
}
