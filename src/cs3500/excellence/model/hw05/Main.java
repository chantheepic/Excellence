package cs3500.excellence.model.hw05;

import cs3500.excellence.model.hw05.shapes.Circle;
import cs3500.excellence.model.hw05.shapes.IShape;
import cs3500.excellence.model.hw05.shapes.Rectangle;
import cs3500.excellence.model.hw05.shapes.SmartShape;

public class Main {

//  public static void main(String[] args) {
//    State s = new State(1, 2, 3, 4, 5, 6, 7);
//    State t = new State(11, 12, 13, 14, 15, 16, 17);
//    SmartShape obj = new Circle();
//    obj.addCommand(s, t, 0, 6);
//    //obj.addCommand(s, t, 8, 10);
//    System.out.println(obj.getOverview(0,5));
//  }

  public static void main(String[] args) {
    Model m = new Model();
    State s1 = new State(0, 0, 2, 3, 0, 0, 0);
    State s2 = new State(0, 0, 4, 5, 0, 0, 0);
    IShape c = new Circle();
    IShape r = new Rectangle();
    m.addObject("c", c);
    m.addObject("r", r);
    m.addCommand("c", s1, s2, 2, 4);
    m.addCommand("r", s2, s1, 2, 4);

    for(int i = 2; i < 6; i++){
      State s3 = m.getStateAtTick("r", i);
      State s4 = m.getStateAtTick("c", i);
      System.out.println("circle: " + s4.x() + " " + s4.y() + " rectangle: " + s3.x() + " " + s3.y());
    }
  }
}
