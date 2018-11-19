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
    view.setEditListener(this);
    view.setComponents(model.getAllComponents(), model.getBoundary(), speed);
    tickTimer = new Timer(1000 / speed, this);

  }
//
//  @Override
//  public void action(String e) {
//    switch (e) {
//      //read from the input textfield
//      case "Echo Button":
//        String text = view.getInputString();
//        //send text to the model
//        model.setString(text);
//
//        //clear input textfield
//        view.clearInputString();
//        //finally echo the string in view
//        text = model.getString();
//        view.setEchoOutput(text);
//
//        break;
//      case "Exit Button":
//        System.exit(0);
//        break;
//    }
//  }


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
          break;

        case "insertKeyframe":
          model.insertKeyframe(s.next(), s.nextInt(), new State(s.nextInt(), s.nextInt(),
                  s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt()));
          break;
        case "togglePlay":
          if(tickTimer.isRunning()){
            tickTimer.stop();
          } else {
            tickTimer.start();
          }
          break;
        case "start":

          tickTimer.start();
          break;
        case "restart":
          this.currentTick = 0;
          view.tick(currentTick);
          tickTimer.stop();
          break;
        case "loop":
          loop = true;
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
    if(currentTick == model.getFinalTick()){
      if(loop) {
        currentTick = 0;
      } else {

      }
      tickTimer.stop();
    } else {
      view.tick(currentTick++);
    }
  }


  private boolean validTick(int tick) {
    return tick >=0;
  }

}
