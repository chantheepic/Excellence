package cs3500.animator.provider.view;

import cs3500.animator.provider.controller.classes.CommandType;
import cs3500.animator.provider.controller.interfaces.Controller;
import cs3500.animator.provider.model.interfaces.EasyAnimatorModelReadOnly;
import cs3500.animator.provider.view.interfaces.EditorView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeListener;

/**
 * The EditorViewImpl is a view that allows the user to interact directly with a visual
 * representation of the animation created. In addition, the view also allows for
 * the export of an animation in an SVG file.
 */
public class EditorViewImpl extends JFrame implements EditorView, ActionListener {

  EasyAnimatorModelReadOnly model;
  Controller controller;
  Appendable out;
  int tempo;

  private boolean isInMotion;
  private boolean isLooping;

  private ViewPanel panel;
  private Timer timer;

  // buttons and checkboxes to the right of the main animation
  private JButton playPauseButton;
  private JButton restartButton;
  private JButton exportButton;
  private JSlider tickRateSlider;
  private JCheckBox toggleLoopCheckBox;

  // labels for elements that need them
  private JLabel sliderLabel;


  /**
   * Constructs a Hybrid View from the user provided model of animaiton, an appendable
   * that the SVG can be exported to, and a tempo of the speed of the animation.
   *
   * @param model The model that we are using to represent shapes and transformations
   * @param out The appendable that we can export an SVG to
   */
  public EditorViewImpl(EasyAnimatorModelReadOnly model, Appendable out, int tempo) {
    this.model = model;
    // to be added later
    this.controller = null;
    this.out = out;
    this.tempo = tempo;
    this.timer = new Timer((1000 / tempo), this);
    this.tempo = 1;
    this.isLooping = false;

    JPanel controlPanel;
    JPanel sliderPanel;

    // setting the title of the window
    this.setTitle("Our Easy Animator of Shapes");
    this.setSize(750, 750);
    this.setLayout(new BorderLayout());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // setting the control panel properties
    controlPanel = new JPanel();
    controlPanel.setSize(200, this.getHeight());

    // set the slider panel
    sliderPanel = new JPanel();

    // Play/pause button
    playPauseButton = new JButton("Play/Pause");
    playPauseButton.setActionCommand("PLAY_PAUSE");
    controlPanel.add(playPauseButton, BorderLayout.CENTER);

    // Restart button
    restartButton = new JButton("Restart");
    restartButton.setActionCommand("RESTART");
    controlPanel.add(restartButton, BorderLayout.CENTER);

    // Export button
    exportButton = new JButton("Export to SVG");
    exportButton.setActionCommand("EXPORT");
    controlPanel.add(exportButton, BorderLayout.CENTER);

    // Tick rate slider and its label
    tickRateSlider = new JSlider(JSlider.HORIZONTAL, 1,100, tempo);
    tickRateSlider.setSnapToTicks(true);
    tickRateSlider.setMinorTickSpacing(1);
    tickRateSlider.setValue(tempo);
    tickRateSlider.setMajorTickSpacing(10);
    tickRateSlider.setPaintTicks(true);
    tickRateSlider.setPaintLabels(true);
    JLabel sliderLabel = new JLabel("Tempo (ticks per second)");
    sliderPanel.add(sliderLabel, BorderLayout.NORTH);
    sliderPanel.add(tickRateSlider, BorderLayout.SOUTH);
    controlPanel.add(sliderPanel,BorderLayout.CENTER);

    // Check box for toggling the loop function
    toggleLoopCheckBox = new JCheckBox("Toggle the loop");
    toggleLoopCheckBox.setBorderPaintedFlat(false);
    controlPanel.add(toggleLoopCheckBox, BorderLayout.EAST);

    panel = new ViewPanel(model.getShapes());
    panel.setPreferredSize(new Dimension(750, 750));
    JScrollPane scroll = new JScrollPane(panel);
    this.add(scroll, BorderLayout.SOUTH);

    this.add(controlPanel, BorderLayout.NORTH);

    this.isInMotion = true;
    timer.start();

    this.pack();
  }


  @Override
  public void display() throws IOException {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    return;
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void acceptCommand(CommandType commandType, String shapeName, int tempo)
      throws IOException {
    switch (commandType) {
      case PLAY_PAUSE:
        this.isInMotion = !isInMotion;
        if (isInMotion) {
          timer.start();
        } else {
          timer.stop();
        }
        break;
      case LOOP:
        this.isLooping = !isLooping;
        if (isLooping) {
          panel.restart();
        }
        break;
      case SET_SPEED:
        timer.stop();
        this.tempo = tempo;
        timer.setDelay(1000 / tempo);
        timer.start();
        break;
      case EXPORT:
        // TODO add option for looping
        SVGView exportSVG = new SVGView(model, out, tempo);
        exportSVG.display();
        break;
      case RESTART:
        panel.restart();
        break;
      default:
        throw new IllegalArgumentException("bad command type");
    }
  }

  @Override
  public void setListeners(ChangeListener changes, ItemListener items, ActionListener actions) {
    // adding the action listeners
    playPauseButton.addActionListener(actions);
    restartButton.addActionListener(actions);
    exportButton.addActionListener(actions);
    toggleLoopCheckBox.addItemListener(items);
    tickRateSlider.addChangeListener(changes);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (isLooping && panel.getMaxTick() == panel.getTick()) {
      panel.restart();
    }
    panel.animateShapes();
    panel.repaint();
    panel.revalidate();
  }

  /**
   * Adds a controller to the editor view so that it can be used to handle commands and sets
   * the listeners for the controller.
   *
   * @param controller  the controller to be added
   * @throws IllegalArgumentException if the controller is null
   */
  public void addController(Controller controller) {
    this.controller = controller;
    this.setListeners(controller, controller, controller);
  }
}
