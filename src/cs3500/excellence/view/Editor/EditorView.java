package cs3500.excellence.view.Editor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.excellence.model.Boundary;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IROComponent;
import cs3500.excellence.model.components.Keyframe;
import cs3500.excellence.model.components.Shape;
import cs3500.excellence.view.IEditListener;
import cs3500.excellence.view.IView;
import cs3500.excellence.view.VisualAnimationPanel;

public class EditorView extends JFrame implements IView, ActionListener, ChangeListener {

  private List<IROComponent> components;
  private Boundary boundary;
  private int speed;

  private VisualAnimationPanel display;

  private IEditListener listener;

  private ImportExport export;

  private JSpinner speedSpinner;
  private JComboBox<Integer> keyframeTicks;
  private JComboBox<String> compBox;
  private JTextField tickChoice;
  private JLabel currentTick;

  private JLabel colorChooserDisplay;

  private JTextField shapeNameField;
  private JTextField shapeTypeField;

  private JTextField shapeXField;
  private JTextField shapeYField;

  private JTextField shapeWidthField;
  private JTextField shapeHeightField;

  private JScrollPane mainScroll;

  public EditorView() {
    super();
    this.export = new ImportExport(this);
    this.display = new VisualAnimationPanel();



    mainScroll = new JScrollPane(display);
    add(mainScroll);


    display.setBorder(BorderFactory.createLineBorder(Color.black));

    setLayout(new GridLayout(2, 2));

    JPanel edit = new JPanel();
    edit.setLayout(new GridLayout(0, 1));

    shapeNameField = new JTextField();
    shapeNameField.setBorder(BorderFactory.createTitledBorder("Shape Name:"));
    edit.add(shapeNameField);

    shapeTypeField = new JTextField();
    shapeTypeField.setBorder(BorderFactory.createTitledBorder("Shape Type:"));
    edit.add(shapeTypeField);

    shapeXField = new JTextField();
    shapeXField.setBorder(BorderFactory.createTitledBorder("X Position:"));
    edit.add(shapeXField);

    shapeYField = new JTextField();
    shapeYField.setBorder(BorderFactory.createTitledBorder("Y Position:"));
    edit.add(shapeYField);

    shapeWidthField = new JTextField();
    shapeWidthField.setBorder(BorderFactory.createTitledBorder("Width:"));
    edit.add(shapeWidthField);

    shapeHeightField = new JTextField();
    shapeHeightField.setBorder(BorderFactory.createTitledBorder("Height:"));
    edit.add(shapeHeightField);

    //color chooser
    JPanel colorChooserPanel = new JPanel();
    colorChooserPanel.setLayout(new FlowLayout());
    edit.add(colorChooserPanel);
    JButton colorChooserButton = new JButton("Choose a color");
    colorChooserButton.setActionCommand("Color chooser");
    colorChooserButton.addActionListener(this);
    colorChooserPanel.add(colorChooserButton);
    colorChooserDisplay = new JLabel("      ");
    colorChooserDisplay.setOpaque(true); //so that background color shows up
    colorChooserDisplay.setBackground(Color.WHITE);
    colorChooserPanel.add(colorChooserDisplay);

    //Create a new shape button
    JButton createShape = new JButton("Create Shape");
    createShape.setActionCommand("create shape");
    createShape.addActionListener(this);
    edit.add(createShape);

    //Delete a shape button
    JButton deleteShape = new JButton("Delete Shape");
    deleteShape.setActionCommand("delete shape");
    deleteShape.addActionListener(this);
    edit.add(deleteShape);

    //Edit a keyframe button
    JButton createKeyframe = new JButton("Create Keyframe");
    createKeyframe.setActionCommand("create keyframe");
    createKeyframe.addActionListener(this);
    edit.add(createKeyframe);

    //Delete a keyframe button
    JButton deleteKeyframe = new JButton("Delete Keyframe");
    deleteKeyframe.setActionCommand("delete keyframe");
    deleteKeyframe.addActionListener(this);
    edit.add(deleteKeyframe);



    add(edit);

    //Playback control
    JPanel playback = new JPanel();

    playback.setLayout(new GridLayout(3, 3));

    //Start and Stop
    JButton togglePlay = new JButton("Start/Pause");
    togglePlay.setActionCommand("togglePlay");
    togglePlay.addActionListener(this);
    playback.add(togglePlay);

    //Restart
    JButton restart = new JButton("Restart");
    restart.setActionCommand("restart");
    restart.addActionListener(this);
    playback.add(restart);

    currentTick = new JLabel("0");
    currentTick.setBorder(BorderFactory.createTitledBorder("Current Tick"));
    playback.add(currentTick);

    //Sets the tick
    JPanel setTick = new JPanel();
    setTick.setLayout(new GridLayout());
    setTick.setBorder(BorderFactory.createTitledBorder("Set Tick"));
    tickChoice = new JTextField();
    setTick.add(tickChoice);

    JButton tickGo = new JButton("Go");
    tickGo.setActionCommand("tickGo");
    tickGo.addActionListener(this);
    setTick.add(tickGo);

    playback.add(setTick);

    //See keyframes for all shapes
    JPanel comboboxPanel = new JPanel();
    comboboxPanel.setBorder(BorderFactory.createTitledBorder("Select Keyframe"));
    comboboxPanel.setLayout(new GridLayout(1, 2));

    compBox = new JComboBox<>();
    compBox.setActionCommand("component options");
    compBox.addActionListener(this);

    keyframeTicks = new JComboBox<>();
    //keyframeTicks.setActionCommand("keyframe options");
    //keyframeTicks.addActionListener(this);

    JButton keyframeGo = new JButton("Go");
    keyframeGo.setActionCommand("keyframe go");
    keyframeGo.addActionListener(this);

    comboboxPanel.add(compBox);
    comboboxPanel.add(keyframeTicks);
    comboboxPanel.add(keyframeGo);



    playback.add(comboboxPanel);

    //Change the speed between 1 and infinity at step of 1
    speedSpinner = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
    speedSpinner.addChangeListener(this);
    speedSpinner.setBorder(BorderFactory.createTitledBorder("Set Speed"));
    playback.add(speedSpinner);

    //Make the animation loop
    JCheckBox loop = new JCheckBox("Loop?");
    loop.setSelected(false);
    loop.setActionCommand("loop");
    loop.addActionListener(this);

    playback.add(loop);

    add(playback);

    setResizable(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();



    setVisible(true);


  }

  public void actionPerformed(ActionEvent e) {

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
      case "keyframe go":
        if (keyframeTicks.getSelectedItem() instanceof Integer) {
          listener.edit("moveto " + keyframeTicks.getSelectedItem());
          populateData(compBox.getSelectedIndex(), (Integer) keyframeTicks.getSelectedItem());
        }

        break;
      case "tickGo":
        if (!tickChoice.getText().equals("")) {
          listener.edit("moveto " + tickChoice.getText());
          if (keyframeTicks.getSelectedItem() instanceof Integer) {
            populateData(compBox.getSelectedIndex(), Integer.parseInt(tickChoice.getText()));
          }
        }
        break;
      case "create keyframe":
        listener.edit(new StringJoiner(" ").add("createKeyframe").add(shapeNameField.getText())
                .add(shapeXField.getText()).add(shapeYField.getText())
                .add(shapeWidthField.getText()).add(shapeHeightField.getText())
                .add(colorChooserDisplay.getBackground().getRed() + "")
                .add(colorChooserDisplay.getBackground().getGreen() + "")
                .add(colorChooserDisplay.getBackground().getBlue() + "").toString());
        break;
      case "create shape":
        listener.edit("addShape " + shapeNameField.getText() + " " + shapeTypeField.getText());
        break;
      case "delete shape":
        listener.edit("delShape " + shapeNameField.getText());
        clearData();
        break;
      case "delete keyframe":
        listener.edit("delKeyframe " + shapeNameField.getText());
        clearData();
        break;
      case "loop":
        listener.edit("loop");
        break;

      case "Color chooser":
        Color col = JColorChooser.showDialog(this, "Choose a color", colorChooserDisplay.getBackground());
        colorChooserDisplay.setBackground(col);
        break;
    }

  }

  @Override
  public void stateChanged(ChangeEvent e) {
    this.speed = (int) ((JSpinner) e.getSource()).getValue();
    listener.edit("speed " + this.speed);
  }

  private void drawFrame(int tick) {
    List<State> states = new ArrayList<>();
    List<Shape> shapes = new ArrayList<>();
    for (IROComponent c : components) {
      if (c.hasMotionAtTick(tick)) {
        states.add(c.getStateAtTick(tick));
        shapes.add(c.getShape());
      }
    }

    display.updatePanelStates(states, shapes, boundary);

    this.repaint();
  }


  @Override
  public void setComponents(List<IROComponent> components, Boundary boundary, int speed) {
    this.components = components;
    this.boundary = boundary;
    this.speed = speed;
    speedSpinner.setValue(this.speed);
    display.setPreferredSize(new Dimension(boundary.getWidth(),boundary.getHeight()));
    //mainScroll.getViewport().revalidate();

    populateCompSelector();
    tick(5);

  }

  @Override
  public void setOutput(Appendable app) {
    //Do Nothing
  }

  @Override
  public void setEditListener(IEditListener listener) {
    this.listener = listener;
  }

  @Override
  public void tick(int currentTick) {
    //Actually draws the animation
    drawFrame(currentTick);
    //Updates the current tick label
    this.currentTick.setText(currentTick + "");
  }

  //Repopulates the component Selector
  private void populateCompSelector() {
    compBox.removeAllItems();
    for (IROComponent comp : components) {
      compBox.addItem(comp.getID());
    }
    keyframeTicks.removeAllItems();
    populateTickSelector(compBox.getSelectedIndex());
  }

  //Repopulates the keyframe selector with given component index
  private void populateTickSelector(int index) {
    if (index != -1) {
      IROComponent comp = components.get(index);
      keyframeTicks.removeAllItems();
      for (Keyframe keyframe : comp.returnAllKeyframes()) {
        keyframeTicks.addItem(keyframe.getTick());
      }
    }
  }

  private void populateData(int index, int tick) {
    clearData();
    IROComponent component = components.get(index);

    shapeNameField.setText(component.getID());
    shapeTypeField.setText(component.getShape().toString().toLowerCase());

    if (component.hasMotionAtTick(tick)) {
      State currentState = component.getStateAtTick(tick);
      shapeXField.setText(currentState.xPos() + "");
      shapeYField.setText(currentState.yPos() + "");
      shapeHeightField.setText(currentState.height() + "");
      shapeWidthField.setText(currentState.width() + "");
      colorChooserDisplay.setBackground(new Color(currentState.red(),
              currentState.green(),
              currentState.blue()));
    }
  }

  private void clearData() {
    shapeNameField.setText("");
    shapeTypeField.setText("");
    shapeXField.setText("");
    shapeYField.setText("");
    shapeWidthField.setText("");
    shapeHeightField.setText("");
    colorChooserDisplay.setBackground(Color.white);


  }






}
