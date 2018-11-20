package cs3500.excellence.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.Timer;

import cs3500.excellence.model.IModel;
import cs3500.excellence.model.State;
import cs3500.excellence.view.IEditListener;
import cs3500.excellence.view.IView;

public class Controller implements IController, IEditListener, ActionListener {

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
    view.setEditListener(this);
    view.setComponents(model.getAllComponents(), model.getBoundary(), this.speed);


  }


  @Override
  public void edit(String description) {
    Scanner s = new Scanner(description);


    while (s.hasNext()) {
      String in = s.next();
      switch (in) {
        case "speed":
          this.speed = s.nextInt();
          tickTimer.setDelay(1000 / speed);
          break;
        case "addShape":
          model.addComponent(s.next(), s.next());
          refreshView();
          break;
        case "delShape":
          model.removeComponent(s.next());
          refreshView();
          break;
        case "insertKeyframe":
          model.insertKeyframe(s.next(), this.currentTick, new State(s.nextInt(), s.nextInt(),
                  s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt()));
          refreshView();
          break;
        case "delKeyframe":
          model.removeKeyframe(s.next(), this.currentTick);
          refreshView();
          break;
        case "togglePlay":
          if(tickTimer.isRunning()){
            tickTimer.stop();
          } else {
            tickTimer.start();
          }
          break;
        case "start":
          //tickTimer.start();
          break;
        case "restart":
          this.currentTick = 0;
          view.tick(currentTick);
          tickTimer.stop();
          break;
        case "loop":
          loop = !loop;
          break;
        case "moveto":
          int nextTick = s.nextInt();
          if(validTick(nextTick)) {
            this.currentTick = nextTick;
            view.tick(currentTick);
          }
          break;

      }
    }

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
  }


}
