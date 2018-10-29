package cs3500.excellence.model.hw05;

import cs3500.excellence.model.hw05.shapes.Ellipse;
import cs3500.excellence.model.hw05.shapes.IComponent;
import cs3500.excellence.model.hw05.shapes.Rectangle;

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
    State s1 = new State(0, 0, 2, 3, 255, 0, 0);
    State s2 = new State(10, 10, 4, 5, 0, 255, 0);
    State s3 = new State(20, 20, 2, 3, 255, 255, 0);
    State s4 = new State(20, 40, 4, 5, 0, 255, 255);
    IComponent c = new Ellipse();
    IComponent r = new Rectangle();
    m.addShape("C", c);
    m.addShape("R", r);
    m.addMotion("C", s1, s2, 2, 10);
    m.addMotion("C", s2, s3, 10, 30);
    m.addMotion("C", s3, s4, 30, 35);
    m.addMotion("R", s2, s1, 2, 4);

    System.out.println(m.getOverview());
    State mid = m.getStateAtTick("C",3);
    System.out.println(mid);
//
//    for(int i = 2; i < 6; i++){
//      State s3 = m.getStateAtTick("r", i);
//      State s4 = m.getStateAtTick("c", i);
//      System.out.println("circle: " + s4.x() + " " + s4.y() + " rectangle: " + s3.x() + " " + s3.y());
//    }
  }
}
