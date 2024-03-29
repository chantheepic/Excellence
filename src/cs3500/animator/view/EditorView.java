package cs3500.animator.view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.animator.controller.Features;
import cs3500.animator.model.Boundary;
import cs3500.animator.model.State;
import cs3500.animator.model.components.IROComponent;
import cs3500.animator.model.components.Keyframe;
import cs3500.animator.model.components.Shape;

/**
 * This class represents a visual for editing an animation. It supports the following: Adding shape,
 * deleting shapes, adding keyframes, deleting keyframes, changing the speed, load, and save.
 */
public class EditorView extends JFrame implements IView, ActionListener, ChangeListener {

  //These three variables are the information from the model.
  private List<IROComponent> components;
  private Boundary boundary;
  private int speed;

  //The JPanel that draws the actual animation.
  private VisualAnimationPanel display;

  //The listener for the various higher level commands.
  private Features listener;

  private JSpinner speedSpinner; //speed selector
  private JComboBox<String> compBox;//shape selector
  private JComboBox<Integer> keyframeTicks; //shape-frame selector
  private JTextField tickChoice; //tick selector
  private JLabel currentTick; //display tick
  private JSlider scrubber; //Scrub through animation


  private JLabel colorChooserDisplay; //color chooser

  private JTextField shapeNameField; //input name
  private JTextField shapeTypeField; //input type
  private JTextField originLayer; //input type
  private JTextField targetLayer; //input type


  private JTextField shapeXField; //input x
  private JTextField shapeYField; //input y

  private JTextField shapeWidthField; //input w
  private JTextField shapeHeightField; //input h

  private JTextField shapeRotationField; //input rotation

  private JScrollPane mainScroll; //scrollable display

  private JLabel fileOpenDisplay; //load file
  private JLabel fileSaveDisplay; //save file

  private JTextArea layerInfo;

  private ScrubHandler scrubHandler = new ScrubHandler();


  /**
   * Method creates a default editorView.
   */
  public EditorView() {
    super();
    this.display = new VisualAnimationPanel();

    mainScroll = new JScrollPane(display);
    add(mainScroll);

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

    shapeRotationField = new JTextField();
    shapeRotationField.setBorder(BorderFactory.createTitledBorder("Heading:"));
    edit.add(shapeRotationField);

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
    JButton createKeyframe = new JButton("Create/Edit Keyframe");
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

    JPanel scrub = new JPanel();
    scrub.add(new JLabel("Playback"));
    scrubber = new JSlider(0, 10);
    scrubber.addChangeListener(scrubHandler);
    scrub.add(scrubber);
    playback.add(scrub);

    add(playback);


    JPanel io = new JPanel();
    io.setLayout(new GridLayout(3, 1));

    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    io.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(this);
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    io.add(filesavePanel);
    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save file");
    fileSaveButton.addActionListener(this);
    filesavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);

    JButton saveAsText = new JButton("Save as Text");
    saveAsText.setActionCommand("saveText");
    saveAsText.addActionListener(this);
    filesavePanel.add(saveAsText);

    JButton saveAsSVG = new JButton("Save as SVG");
    saveAsSVG.setActionCommand("saveSVG");
    saveAsSVG.addActionListener(this);
    filesavePanel.add(saveAsSVG);


    JPanel setLayer = new JPanel();
    setLayer.setLayout(new GridLayout());
    setLayer.setBorder(BorderFactory.createTitledBorder("Set Layer"));

    layerInfo = new JTextArea();
    layerInfo.setEditable(false);
    setLayer.add(layerInfo);


    //Sets the layer
    originLayer = new JTextField();
    setLayer.add(originLayer);

    targetLayer = new JTextField();
    setLayer.add(targetLayer);

    JButton layerSet = new JButton("Set");
    layerSet.setActionCommand("layerSet");
    layerSet.addActionListener(this);
    setLayer.add(layerSet);

    JButton layerDelete = new JButton("Delete");
    layerDelete.setActionCommand("layerDel");
    layerDelete.addActionListener(this);
    setLayer.add(layerDelete);

    JButton layerGo = new JButton("Swap");
    layerGo.setActionCommand("layerSwap");
    layerGo.addActionListener(this);
    setLayer.add(layerGo);

    io.add(setLayer);

    add(io);


    setResizable(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();

    setVisible(true);


  }

  /**
   * Method appropriately responds to actionEvent generated by user.
   *
   * @param e specified ActionEvent
   */
  public void actionPerformed(ActionEvent e) {
    try {
      switch (e.getActionCommand()) {
        case "togglePlay": //from start/stop button
          listener.togglePlay();
          break;
        case "restart": //from restart button
          listener.restart();
          break;
        case "component options": //from shape selector
          populateTickSelector(compBox.getSelectedIndex());
          break;
        case "keyframe go": //from shape-frame selector
          if (keyframeTicks.getSelectedItem() instanceof Integer) {
            listener.setTick((Integer) keyframeTicks.getSelectedItem());
            populateData(compBox.getSelectedIndex(), (Integer) keyframeTicks.getSelectedItem());
          }
          break;
        case "tickGo": //from tick selector
          if (tickChoice.getText().matches("[0-9]+")) {
            listener.setTick(Integer.parseInt(tickChoice.getText()));
            if (keyframeTicks.getSelectedItem().toString().matches("[0-9]]")) {
              populateData(compBox.getSelectedIndex(), Integer.parseInt(tickChoice.getText()));
            }
          }
          break;
        case "layerSet": //from tick selector
          if (originLayer.getText().matches("[0-9]+")) {
            listener.setLayer(shapeNameField.getText(), Integer.parseInt(originLayer.getText()));
          }
          break;
        case "layerDel": //from tick selector
          if (originLayer.getText().matches("[0-9]+")) {
            listener.deleteLayer(Integer.parseInt(originLayer.getText()));
          }
          break;
        case "layerSwap": //from tick selector
          if (originLayer.getText().matches("[0-9]+") && targetLayer.getText().matches("[0-9]+")) {
            listener.swapLayer(Integer.parseInt(originLayer.getText()),
                    Integer.parseInt(targetLayer.getText()));
          }
          break;
        case "create keyframe":
          int heading = 0;
          if(shapeRotationField.getText().matches("[0-9]+")) {
            heading = Integer.parseInt(shapeRotationField.getText());
          }
          listener.createFrame(shapeNameField.getText(),
                  Integer.parseInt(shapeXField.getText()),
                  Integer.parseInt(shapeYField.getText()),
                  Integer.parseInt(shapeWidthField.getText()),
                  Integer.parseInt(shapeHeightField.getText()),
                  colorChooserDisplay.getBackground().getRed(),
                  colorChooserDisplay.getBackground().getGreen(),
                  colorChooserDisplay.getBackground().getBlue(),
                  heading);
          break;
        case "create shape":
          if (originLayer.getText().equals("")) {
            listener.addShape(shapeNameField.getText(), shapeTypeField.getText(), 0);
          } else {
            listener.addShape(shapeNameField.getText(), shapeTypeField.getText(),
                    Integer.parseInt(originLayer.getText()));
          }
          break;
        case "delete shape":
          listener.deleteShape(shapeNameField.getText());
          clearData();
          break;
        case "delete keyframe":
          listener.deleteFrame(shapeNameField.getText());
          clearData();
          break;
        case "loop":
          listener.toggleLoop();
          break;

        case "Color chooser":
          Color col = JColorChooser
                  .showDialog(this, "Choose a color", colorChooserDisplay.getBackground());
          colorChooserDisplay.setBackground(col);
          break;

        case "Open file": {
          final JFileChooser fchooser = new JFileChooser(".");
          int retvalue = fchooser.showOpenDialog(this);
          if (retvalue == JFileChooser.APPROVE_OPTION) {
            File f = fchooser.getSelectedFile();
            fileOpenDisplay.setText(f.getAbsolutePath());
            listener.load(fileOpenDisplay.getText());
          }

        }
        break;
        case "Save file": {
          final JFileChooser fchooser = new JFileChooser(".");
          int retvalue = fchooser.showSaveDialog(this);
          if (retvalue == JFileChooser.APPROVE_OPTION) {
            File f = fchooser.getSelectedFile();
            fileSaveDisplay.setText(f.getAbsolutePath());
          }
        }
        break;

        case "saveText":
          listener.saveAsText(fileSaveDisplay.getText() + ".txt");
          break;
        case "saveSVG":
          listener.saveAsSVG(fileSaveDisplay.getText() + ".svg");
          break;
        // Do nothing by default
        default:
      }
    } catch (IllegalArgumentException nfe) {
      new ErrPanel().error("Fields not filled out");
    }

  }

  @Override
  public void stateChanged(ChangeEvent e) {
    this.speed = (int) ((JSpinner) e.getSource()).getValue();
    listener.setSpeed(this.speed);
  }

  /*
    Updates the display panel with states and shapes at tick.
   */
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
    display.setPreferredSize(new Dimension(boundary.getWidth(), boundary.getHeight()));
    mainScroll.getViewport().revalidate();

    populateCompSelector();
    setMaxScrub();
    populateLayers();

  }

  private void populateLayers() {
    HashMap<Integer, ArrayList<String>> layers = new LinkedHashMap<>();

    for (int i = 0; i <components.size(); i++) {
      int l = components.get(i).getLayer();
      String name = components.get(i).getID();
      if(layers.containsKey(l)) {
        layers.get(l).add(name);
      }
      else {
        layers.put(l, new ArrayList<>(Arrays.asList(name)));
      }
    }

    StringBuilder output = new StringBuilder();
    for (int l : layers.keySet()) {
      output.append(l + ": " +String.join(",", layers.get(l)));
      output.append("\n");
    }
    layerInfo.setText(output.toString());
  }

  private void setMaxScrub() {

    scrubber.setMaximum(getMaxTick(components));

  }

  private int getMaxTick(List<IROComponent> components) {
    int max = 0;
    for (IROComponent component : components) {
      max = Math.max(component.getFinalTick(), max);
    }
    return max;
  }


  @Override
  public void setOutput(Appendable app) {
    //Do Nothing
  }

  @Override
  public void setFeatureListener(Features listener) {
    this.listener = listener;
  }

  @Override
  public void displayError(String msg) {
    ErrPanel.error(msg);
  }

  @Override
  public void tick(int currentTick) {
    //Actually draws the animation
    drawFrame(currentTick);
    //Updates the current tick label
    this.currentTick.setText(currentTick + "");
    this.scrubber.setValue(currentTick);//update the scrubber
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

  //Populates the data in the input fields
  private void populateData(int index, int tick) {
    clearData();
    IROComponent component = components.get(index);

    shapeNameField.setText(component.getID());
    shapeTypeField.setText(component.getShape().toString().toLowerCase());
    originLayer.setText(component.getLayer() + "");

    if (component.hasMotionAtTick(tick)) {
      State currentState = component.getStateAtTick(tick);
      shapeXField.setText(currentState.xPos() + "");
      shapeYField.setText(currentState.yPos() + "");
      shapeHeightField.setText(currentState.height() + "");
      shapeWidthField.setText(currentState.width() + "");
      shapeRotationField.setText(currentState.heading() + "");
      colorChooserDisplay.setBackground(new Color(currentState.red(),
              currentState.green(),
              currentState.blue()));
    }
  }

  //Clears the input field data.
  private void clearData() {
    shapeNameField.setText("");
    shapeTypeField.setText("");
    tickChoice.setText("");
    shapeXField.setText("");
    shapeYField.setText("");
    shapeWidthField.setText("");
    shapeHeightField.setText("");
    colorChooserDisplay.setBackground(Color.white);
  }

  class ScrubHandler implements ChangeListener {

    @Override
    public void stateChanged(ChangeEvent e) {
      listener.setTick(((JSlider) e.getSource()).getValue());
    }
  }

}
