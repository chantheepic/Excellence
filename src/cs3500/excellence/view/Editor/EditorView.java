package cs3500.excellence.view.Editor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs3500.excellence.model.Boundary;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IROComponent;
import cs3500.excellence.model.components.Keyframe;
import cs3500.excellence.model.components.Shape;
import cs3500.excellence.view.IEditListener;
import cs3500.excellence.view.IView;
import cs3500.excellence.view.VisualAnimationPanel;

public class EditorView extends JFrame implements IView, ActionListener, ItemListener, ListSelectionListener {

  List<IROComponent> components;
  private IEditListener listener;
  private ImportExport export;
  private Parameters param;
  private VisualAnimationPanel display;
  //  private Interactive interactive;
//  private JPanel leftPanel;
//  private JPanel topPanel;
//  private JPanel container;
  private JPanel edit;
  private JScrollPane mainScrollPane;
  //  private String filename;
  private int speed;
  private int finalTick;
  private boolean loop = false;
  private int currentTick;
  private JPanel currentTickLabel;
  //private Timer tickTimer;
  private Boundary boundary;

  private JLabel comboboxDisplay;
  private JComboBox<Integer> keyframeTicks;
  private JComboBox<String> compBox;


  public EditorView() {
    super();
    this.export = new ImportExport(this);
    this.param = new Parameters(this);
    this.display = new VisualAnimationPanel();
    //    this.interactive = new Interactive(this);
    mainScrollPane = new JScrollPane(display);
    add(mainScrollPane);

    display.setBorder(BorderFactory.createLineBorder(Color.black));

    //    leftPanel = new JPanel();
    //    leftPanel.setLayout(new GridLayout(3,1));
    //    leftPanel.add(param.returnPanel());
    //    leftPanel.add(export.returnPanel());

    this.setLayout(new GridLayout(2, 2));


    edit = new EditPanel();
    //    leftPanel.add(param.returnPanel());
    //    leftPanel.add(export.returnPanel());

    this.add(edit);


    JCheckBox[] loop = new JCheckBox[1];
    loop[0] = new JCheckBox("Loop");
    loop[0].setSelected(false);
    loop[0].setActionCommand("loop");
    loop[0].addActionListener(this);

    // Playback is built in because it needs access to the timer


    JPanel playback = new JPanel();
    playback.setLayout(new GridLayout(3, 3));


    JButton togglePlay = new JButton("Start/Pause");
    togglePlay.setActionCommand("togglePlay");
    togglePlay.addActionListener(this);
    playback.add(togglePlay);

    JButton restart = new JButton("Restart");
    restart.setActionCommand("restart");
    restart.addActionListener(this);
    playback.add(restart);

    JLabel currentTick = new JLabel("0");
    playback.add(currentTick);

    JLabel tickInfo = new JLabel("GoTo Tick:");
    playback.add(tickInfo);

    JTextField tickChoice = new JTextField();
    playback.add(tickChoice);

    JButton tickGo = new JButton("Go");
    tickGo.setActionCommand("tickGo");
    tickGo.addActionListener(this);
    playback.add(tickGo);

    JPanel comboboxPanel = new JPanel();
    comboboxPanel.setBorder(BorderFactory.createTitledBorder("Select Keyframe"));
    comboboxPanel.setLayout(new GridLayout(1,2));
    playback.add(comboboxPanel);

    compBox = new JComboBox<>();
    //the event listener when an option is selected
    compBox.setActionCommand("component options");
    compBox.addActionListener(this);


    keyframeTicks = new JComboBox<>();
    keyframeTicks.setActionCommand("keyframe options");
    keyframeTicks.addActionListener(this);

    comboboxPanel.add(compBox);
    comboboxPanel.add(keyframeTicks);

    this.add(playback);


//    currentTickLabel = new JPanel();
//    playback.add(currentTickLabel);
//    playback.add(loop[0]);

    //    leftPanel.add(playback);
    //
    //    topPanel = new JPanel();
    //    topPanel.setLayout(new GridLayout(1,2));
    //    topPanel.add(leftPanel);
    //    topPanel.add(mainScrollPane);
    //
    //    container = new JPanel();
    //    container.setLayout(new GridLayout(2,1));
    //    container.add(topPanel);
    //    container.add(interactive.returnPanel());
    //
    //    this.add(container);

    setResizable(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  // Previously setComponents();
  public void setInitial(List<IROComponent> components, Boundary boundary, String filename, int speed) {

//    tickTimer = new Timer(1000/this.speed, this);
//    tickTimer.start();
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
    if (e.getActionCommand() == null) {
      drawFrame(currentTick++);
      currentTickLabel.removeAll();
      currentTickLabel.add(new Label(String.valueOf(currentTick)));
      setVisible(true);
      this.repaint();
      if (currentTick >= finalTick) {
        if (loop == true) {
          currentTick = 0;
        } else {
//          tickTimer.stop();
        }
      }
      return;
    }

    switch (e.getActionCommand()) {
      case "togglePlay":
        listener.edit("togglePlay");
        break;
      case "restart":
        listener.edit("restart");
        break;
      case "component options":
        populateTickSelector(compBox.getSelectedIndex());
        break;
      case "keyframe options":
        if(keyframeTicks.getSelectedItem() instanceof Integer) {
          listener.edit("moveto " + keyframeTicks.getSelectedItem());
        }

        break;
    }

  }

  public void drawFrame(int tick) {
    List<State> states = new ArrayList();
    List<Shape> shapes = new ArrayList();
    for (IROComponent c : components) {
      if (c.hasMotionAtTick(tick)) {
        states.add(c.getStateAtTick(tick));
        shapes.add(c.getShape());
      }
    }
    int scale = boundary.getHeight() / 300;
    if (boundary.getHeight() < boundary.getWidth()) {
      scale = boundary.getWidth() / 300;
    }
    display.updatePanelStates(states, shapes, boundary);

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
//    tickTimer.stop();
//    tickTimer.removeActionListener(this);
//    this.tickTimer = new Timer(1000/this.speed, this);
//    tickTimer.start();
    param.updateParam(new Dimension(boundary.getWidth(), boundary.getHeight()), speed);
  }

  @Override
  public void setComponents(List<IROComponent> components, Boundary boundary, int speed) {
    this.speed = speed;
//    this.filename = filename;
    this.components = components;
    this.boundary = boundary;
    findFinalTick();
    display.setPreferredSize(new Dimension(this.boundary.getWidth() + this.boundary.getX(), this.boundary.getHeight() + this.boundary.getY()));
//    interactive.setComponents(this.components);
    param.updateParam(new Dimension(boundary.getWidth(), boundary.getHeight()), speed);
    populateCompSelector();
  }

  @Override
  public void setOutput(Appendable app) {

  }

  @Override
  public void setEditListener(IEditListener listener) {
    this.listener = listener;

  }

  @Override
  public void tick(int currentTick) {
    drawFrame(currentTick);
  }


  private void populateCompSelector() {

    for (IROComponent comp : components) {
      compBox.addItem(comp.getID());
    }

  }

  private void populateTickSelector(int index) {
    IROComponent comp = components.get(index);
    keyframeTicks.removeAllItems();
    for (Keyframe keyframe : comp.returnAllKeyframes()) {
      keyframeTicks.addItem(keyframe.getTick());
    }

  }

}
