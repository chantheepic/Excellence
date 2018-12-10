package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Timer;

import cs3500.animator.model.IModel;
import cs3500.animator.model.Model;
import cs3500.animator.model.State;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import cs3500.animator.view.svg.SVGView;
import cs3500.animator.view.text.TextualView;

/**
 * This is the controller for our IModel and IView. It is responsible for holding information about
 * the current tick. It also is a callback for Features. It has a timer that tells the view how
 * often to update.
 */
public class Controller implements IController, ActionListener, Features {

  private IModel model;
  private IView view;

  private int speed;
  private int currentTick;

  private Timer tickTimer;

  private boolean loop;

  /**
   * Creates an instance of the controller with a given model and speed. It creates the timer, and
   * sets the callback listener to this class.
   *
   * @param m - the given model
   * @param speed - the given speed
   */
  public Controller(IModel m, int speed) {
    model = m;
    this.speed = speed;
    tickTimer = new Timer(1000 / this.speed, this);
    tickTimer.setActionCommand("tick");
    this.currentTick = 0;

  }

  @Override
  public void setView(IView view) {
    this.view = view;
    view.setFeatureListener(this);
    view.setComponents(model.getAllComponents(), model.getBoundary(), this.speed);
  }

  @Override
  public void setSpeed(int speed) {
    if (speed < 0) {
      view.displayError("Speed must be positive");
    }
    this.speed = speed;
    tickTimer.setDelay(1000 / this.speed);
  }

  @Override
  public void addShape(String name, String type, int layer) {
    try {
      model.addComponent(name, type, layer);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
    refreshView();
  }

  @Override
  public void deleteShape(String name) {
    try {
      model.removeComponent(name);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
    refreshView();
  }

  @Override
  public void createFrame(String name, int x, int y, int w, int h, int r, int g, int b) {
    try {
      model.createKeyframe(name, this.currentTick, new State(x, y, w, h, r, g, b));
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
    refreshView();
  }

  @Override
  public void deleteFrame(String name) {
    try {
      model.removeKeyframe(name, this.currentTick);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
    refreshView();
  }

  @Override
  public void togglePlay() {
    if (tickTimer.isRunning()) {
      tickTimer.stop();
    } else {
      tickTimer.start();
    }
  }

  @Override
  public void restart() {
    this.currentTick = 0;
    view.tick(currentTick);
    tickTimer.stop();
  }

  @Override
  public void toggleLoop() {
    loop = !loop;
  }

  @Override
  public void setTick(int tick) {
    if (validTick(tick)) {
      this.currentTick = tick;
      view.tick(currentTick);
    } else {
      view.displayError("invalid tick");
    }
  }

  @Override
  public void saveAsText(String fName) {
    saveWork("text", fName);
  }

  @Override
  public void saveAsSVG(String fName) {
    saveWork("svg", fName);
  }

  @Override
  public void load(String fName) {
    loadFile(fName);
  }

  /**
   * This method takes care of calculation when it receives a "tick" command. This tick command is
   * given by the timer. If necessary is updates the currentTick and tells view to display it.
   *
   * @param e - the Action Event.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("tick")) {
      if (currentTick == model.getFinalTick()) {
        view.tick(currentTick);
        if (loop) {
          currentTick = 0;
        } else {
          tickTimer.stop();
        }

      } else {
        view.tick(currentTick++);
      }
    }
  }

  //Checks if a tick is valid
  private boolean validTick(int tick) {
    return tick >= 0;
  }

  /*
    Refreshes the view by giving it the components, boundary, and speed. Then
    Then it tells it to draw the current tick.
   */
  private void refreshView() {
    view.setComponents(model.getAllComponents(), model.getBoundary(), this.speed);
    view.tick(this.currentTick);
  }

  /*
    Loads a file and creates and sets the model to the newly parsed file.
    Tells the view to display any errors it encounters.
   */
  private void loadFile(String fName) {
    try {
      this.model = AnimationReader
          .parseFile(new FileReader(new File(fName)), Model.builder());
      refreshView();
    } catch (FileNotFoundException | IllegalStateException | IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }

  /*
    Saves the current model to a file, based on the type.
    Tells the view to display any errors it encounters.
   */
  private void saveWork(String type, String fName) {

    if (fName.equals("")) {
      view.displayError("Output file not defined");
    }

    if (type.equals("svg")) {
      try {
        FileWriter fw = new FileWriter(fName);
        new SVGView(fw).setComponents(model.getAllComponents(),
            model.getBoundary(), this.speed);
        fw.close();
      } catch (IOException e) {
        view.displayError(e.getMessage());
      }
    }

    if (type.equals("text")) {
      try {
        FileWriter fw = new FileWriter(fName);
        new TextualView(fw).setComponents(model.getAllComponents(),
            model.getBoundary(), this.speed);
        fw.close();
      } catch (IOException e) {
        view.displayError(e.getMessage());
      }
    }
  }
}
