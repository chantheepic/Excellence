package cs3500.excellence.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Timer;

import cs3500.excellence.model.IModel;
import cs3500.excellence.model.Model;
import cs3500.excellence.model.State;
import cs3500.excellence.util.AnimationReader;
import cs3500.excellence.view.IView;
import cs3500.excellence.view.SVGView;
import cs3500.excellence.view.TextualView;

public class Controller implements IController, ActionListener, Features {

  private IModel model;
  private IView view;

  private int speed;
  private int currentTick;

  private Timer tickTimer;

  private boolean loop;




  public Controller(IModel m, IView v, int speed) {
    model = m;
    view = v;
    this.speed = speed;
    tickTimer = new Timer(1000 / this.speed, this);
    tickTimer.setActionCommand("tick");
    this.currentTick = 0;
    view.setFeatureListener(this);
    view.setComponents(model.getAllComponents(), model.getBoundary(), this.speed);


  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed;
    tickTimer.setDelay(1000/this.speed);
  }

  @Override
  public void addShape(String name, String type) {
    model.addComponent(name, type);
    refreshView();
  }

  @Override
  public void deleteShape(String name) {
    model.removeComponent(name);
    refreshView();
  }

  @Override
  public void createFrame(String name, int x, int y, int w, int h, int r, int g, int b) {
    model.createKeyframe(name, this.currentTick, new State(x, y, w, h, r, g, b));
    refreshView();
  }

  @Override
  public void deleteFrame(String name) {
    model.removeKeyframe(name, this.currentTick);
    refreshView();
  }

  @Override
  public void togglePlay() {
    if(tickTimer.isRunning()){
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
    int nextTick = tick;
    if(validTick(nextTick)) {
      this.currentTick = nextTick;
      view.tick(currentTick);
    }
  }

  @Override
  public void saveAsText(String fname) {
    saveWork("text", fname);
  }

  @Override
  public void saveAsSVG(String fname) {
    saveWork("svg", fname);
  }

  @Override
  public void load(String fname) {
    loadFile(fname);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "tick":
        if(currentTick == model.getFinalTick()){
          view.tick(currentTick);
          if(loop) {
            currentTick = 0;
          } else {
            tickTimer.stop();
          }

        } else {
          view.tick(currentTick++);
        }
        break;
    }

  }


  private boolean validTick(int tick) {
    return tick >=0;
  }

  private void refreshView(){
    view.setComponents(model.getAllComponents(), model.getBoundary(), this.speed);
    view.tick(this.currentTick);
  }

  private void loadFile(String fname) {
    try {
      this.model = AnimationReader
              .parseFile(new FileReader(new File(fname)), Model.builder());
      refreshView();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void saveWork(String type, String fname) {

    switch (type) {
      case "svg":
        try {
          FileWriter fw = new FileWriter(fname);
          new SVGView(fw).setComponents(model.getAllComponents(),
                  model.getBoundary(),this.speed);
          fw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }

        break;
      case "text":
        try {
          FileWriter fw = new FileWriter(fname);
          new TextualView(fw).setComponents(model.getAllComponents(),
                  model.getBoundary(),this.speed);
          fw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
    }
  }



}
