package cs3500.excellence;


import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.view.TextualView;
import cs3500.excellence.view.VisualAnimationView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.excellence.model.IModel;
import cs3500.excellence.model.Model;
import cs3500.excellence.util.AnimationReader;
import java.util.List;

public class Excellence {

  public static void main(String[] args) {
    VisualAnimationView view;
    TextualView view2;
    IModel model;

    try {
      model = AnimationReader.parseFile(new FileReader(new File("resources/big-bang-big-crunch.txt")), Model.builder());
      view2 = new TextualView(System.out);
      view2.setOverview(model.getOverview());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }


//    try {
//      model = AnimationReader.parseFile(new FileReader(new File("resources/big-bang-big-crunch.txt")), Model.builder());
//      //System.out.println(model.getOverview());
//
//      List<IComponent> components;
//      view = new VisualAnimationView();
//      view2 = new TextualView();
//
//      for (int tick = 0; tick < model.getFinalTick(); tick++){
//        view.update(tick, model.getComponentsAtTick(tick));
//        view.drawFrame();
//        view.setVisible(true);
//      }
//
////      for (int tick = 0; tick < model.getFinalTick(); tick++){
////        view2.update(tick, model.getComponentsAtTick(tick));
////        view2.drawFrame();
////      }
//
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    }
//  }

//   //Animate on life support
//  public static void main(String[] args) {
//    VisualAnimationView view;
//    IModel basicModel = Model.builder().build();
//
//    State s = new State(1, 200, 30, 50, 100, 6, 7);
//    State t = new State(110, 12, 20, 20, 15, 100, 17);
//    IComponent r = new Rectangle();
//    IComponent v = new Rectangle();
//    basicModel.addComponent("R", r);
//    basicModel.addComponent("V", v);
//    basicModel.addMotion("R", t, s, 2, 100);
//    basicModel.addMotion("V", s, t, 2, 100);
//
//    List<State> states = new ArrayList();
//      List<IComponent> components = basicModel.getComponentsAtTick(2);
//      view = new VisualAnimationView(components, 2, basicModel.getFinalTick());
//      view.drawFrame();
//      view.setVisible(true);
//      view.animate();
//  }
}
