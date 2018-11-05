package cs3500.excellence;


import cs3500.excellence.model.BasicMotion;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.Ellipse;
import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.model.components.Rectangle;
import cs3500.excellence.view.IView;
import cs3500.excellence.view.VisualAnimationFrame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.excellence.model.IModel;
import cs3500.excellence.model.Model;
import cs3500.excellence.util.AnimationReader;
import java.util.ArrayList;
import java.util.List;

public class Excellence {


//  public static void main(String[] args) {
//    VisualAnimationFrame view;
//    IModel model;
//
//    try {
//
//      model = AnimationReader.parseFile(new FileReader(new File("resources/big-bang-big-crunch.txt")), Model.builder());
//      System.out.println(model.getOverview());
//
//      List<State> states = new ArrayList();
//      List<IComponent> components = model.getComponentsAtTick(100);
//      view = new VisualAnimationFrame(components, 100);
//      view.drawFrame();
//      view.setVisible(true);
//
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    }
//  }

  public static void main(String[] args) {
    VisualAnimationFrame view;
    IModel basicModel = Model.builder().build();

    State s = new State(1, 200, 30, 50, 100, 6, 7);
    State t = new State(110, 12, 20, 20, 15, 100, 17);
    IComponent r = new Rectangle();
    IComponent v = new Rectangle();
    basicModel.addComponent("R", r);
    basicModel.addComponent("V", v);
    basicModel.addMotion("R", t, s, 2, 100);
    basicModel.addMotion("V", s, t, 2, 100);

    List<State> states = new ArrayList();
      List<IComponent> components = basicModel.getComponentsAtTick(5);
      view = new VisualAnimationFrame(components, 2);
      view.drawFrame();
      view.setVisible(true);
      view.animate();
  }
}
