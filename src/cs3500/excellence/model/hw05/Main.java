package cs3500.excellence.model.hw05;

import cs3500.excellence.model.hw05.shapes.Circle;
import cs3500.excellence.model.hw05.shapes.SmartShape;

public class Main {

  public static void main(String[] args) {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    SmartShape obj = new Circle();
    obj.addCommand(s, t, 0, 6);
    //obj.addCommand(s, t, 8, 10);
    System.out.println(obj.getOverview(0,5));
  }
}
