package cs3500.excellence.model.hw05;

import static org.junit.Assert.assertEquals;

import cs3500.excellence.model.hw05.shapes.Ellipse;
import cs3500.excellence.model.hw05.shapes.IComponent;
import cs3500.excellence.model.hw05.shapes.Rectangle;
import org.junit.Test;

public class Tests {
  @Test
  public void wellFormedTests(){
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    Model m = new Model();
    IComponent c = new Ellipse();

    // Test simple add shape
    m.addShape("C", c);
    assertEquals(m.getOverview(),
        "shape C ellipse\n"
            + "\n"
            + "\n");

    // Test simple single command
    m.addMotion("C", s, t, 2, 10);
    assertEquals(m.getOverview(),
        "shape C ellipse\n"
        + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
        + "\n"
        + "\n");

    // Test if second object can be added to the model
    IComponent r = new Rectangle();
    m.addShape("R", r);
    m.addMotion("R", t, s, 2, 10);
    assertEquals(m.getOverview(),
        "shape C ellipse\n"
            + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
            + "\n"
            + "\n"
            + "shape R rectangle\n"
            + "motion R  2 13  14  11  12  15  16  17     10  3   4   1   2   5   6   7\n"
            + "\n"
            + "\n");

    State u = new State(3, 6, 9, 12, 15, 18, 21);
    State v = new State(4, 8, 12, 16, 20, 24, 28);
    State w = new State(5, 10, 15, 20, 25, 30, 35);
    State x = new State(6, 12, 18, 24, 30, 36, 42);

    // Test if object can do nothing
    m.addMotion("R", u, u, 10, 20);
    assertEquals(m.getOverview(),
        "shape C ellipse\n"
            + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
            + "\n"
            + "\n"
            + "shape R rectangle\n"
            + "motion R  2 13  14  11  12  15  16  17     10  3   4   1   2   5   6   7\n"
            + "motion R 10  9  12   3   6  15  18  21     20  9  12   3   6  15  18  21\n"
            + "\n"
            + "\n");

    // Test if second move command can be added
    m.addMotion("R", u, s, 20, 25);
    assertEquals(m.getOverview(),
        "shape C ellipse\n"
            + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
            + "\n"
            + "\n"
            + "shape R rectangle\n"
            + "motion R  2 13  14  11  12  15  16  17     10  3   4   1   2   5   6   7\n"
            + "motion R 10  9  12   3   6  15  18  21     20  9  12   3   6  15  18  21\n"
            + "motion R 20  9  12   3   6  15  18  21     25  3   4   1   2   5   6   7\n"
            + "\n"
            + "\n");

    //Test if multiple objects can be added to model
    IComponent a = new Ellipse();
    IComponent b = new Rectangle();
    m.addShape("A", a);
    m.addShape("B", b);
    assertEquals(m.getOverview(),
        "shape C ellipse\n"
            + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
            + "\n"
            + "\n"
            + "shape R rectangle\n"
            + "motion R  2 13  14  11  12  15  16  17     10  3   4   1   2   5   6   7\n"
            + "motion R 10  9  12   3   6  15  18  21     20  9  12   3   6  15  18  21\n"
            + "motion R 20  9  12   3   6  15  18  21     25  3   4   1   2   5   6   7\n"
            + "\n"
            + "\n"
            + "shape A ellipse\n"
            + "\n"
            + "\n"
            + "shape B rectangle\n"
            + "\n"
            + "\n");

    // Test if multiple move commands can be added to model
    m.addMotion("C", v, w, 10, 30);
    m.addMotion("R", v, w, 25, 30);
    m.addMotion("C", w, x, 30, 40);
    m.addMotion("R", w, x, 30, 40);
    assertEquals(m.getOverview(),
        "shape C ellipse\n"
            + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
            + "motion C 10 12  16   4   8  20  24  28     30 15  20   5  10  25  30  35\n"
            + "motion C 30 15  20   5  10  25  30  35     40 18  24   6  12  30  36  42\n"
            + "\n"
            + "\n"
            + "shape R rectangle\n"
            + "motion R  2 13  14  11  12  15  16  17     10  3   4   1   2   5   6   7\n"
            + "motion R 10  9  12   3   6  15  18  21     20  9  12   3   6  15  18  21\n"
            + "motion R 20  9  12   3   6  15  18  21     25  3   4   1   2   5   6   7\n"
            + "motion R 25 12  16   4   8  20  24  28     30 15  20   5  10  25  30  35\n"
            + "motion R 30 15  20   5  10  25  30  35     40 18  24   6  12  30  36  42\n"
            + "\n"
            + "\n"
            + "shape A ellipse\n"
            + "\n"
            + "\n"
            + "shape B rectangle\n"
            + "\n"
            + "\n");

    // Well formed getStateAt tests
  }

  // Test for when object already exists in hashmap
  @Test
  public void badInput() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    Model m = new Model();
    IComponent c = new Ellipse();
    m.addShape("C", c);
    m.addMotion("C", s, t, 2, 10);
    IComponent r = new Rectangle();
    try {
      m.addShape("C", r);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Object already exists");
      assertEquals(m.getOverview(), "shape C ellipse\n"
          + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
          + "\n"
          + "\n");
    }
  }

  // Test for when object doesn't exist in hashmap (has not been registered)
  @Test
  public void badInput2() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    Model m = new Model();
    IComponent c = new Ellipse();
    m.addShape("C", c);
    m.addMotion("C", s, t, 2, 10);
    IComponent r = new Rectangle();
    m.addShape("R", r);
    try {
      m.addMotion("S", t, s, 2, 10);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Object does not exist");
      assertEquals(m.getOverview(), "shape C ellipse\n"
          + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
          + "\n"
          + "\n"
          + "shape R rectangle\n"
          + "\n"
          + "\n");
    }
    try {
      m.getStateAtTick("F", 5);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Object does not exist");
      assertEquals(m.getOverview(), "shape C ellipse\n"
          + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
          + "\n"
          + "\n"
          + "shape R rectangle\n"
          + "\n"
          + "\n");
    }
  }

  // Get state for tick that doesn't exist
  @Test
  public void badInput3() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    Model m = new Model();
    IComponent c = new Ellipse();
    m.addShape("C", c);
    m.addMotion("C", s, t, 2, 10);
    IComponent r = new Rectangle();
    m.addShape("R", r);
    try {
      c.getStateAtTick(11);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Tick not valid");
      assertEquals(m.getOverview(), "shape C ellipse\n"
          + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
          + "\n"
          + "\n"
          + "shape R rectangle\n"
          + "\n"
          + "\n");
    }
    try {
      r.getStateAtTick(0);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Tick not valid");
      assertEquals(m.getOverview(), "shape C ellipse\n"
          + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
          + "\n"
          + "\n"
          + "shape R rectangle\n"
          + "\n"
          + "\n");
    }
  }

  // Starting tick is later than ending tick
  @Test
  public void badInput4() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    Model m = new Model();
    IComponent c = new Ellipse();
    m.addShape("C", c);
    try {
      m.addMotion("C", s, t, 10, 2);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "end tick must be greater than begin tick");
      assertEquals(m.getOverview(), "shape C ellipse\n"
          + "\n"
          + "\n");
    }
  }

  // New starting tick conflicts with existing motion
  @Test
  public void badInput5() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    Model m = new Model();
    IComponent c = new Ellipse();
    m.addShape("C", c);
    m.addMotion("C", s, t, 2, 10);
    try {
      m.addMotion("C", s, t, 8, 12);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Not adjacent motions");
      assertEquals(m.getOverview(), "shape C ellipse\n"
          + "motion C  2  3   4   1   2   5   6   7     10 13  14  11  12  15  16  17\n"
          + "\n"
          + "\n");
    }
  }

  // New ending tick conflicts with existing motion
  @Test
  public void badInput6() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    Model m = new Model();
    IComponent c = new Ellipse();
    m.addShape("C", c);
    m.addMotion("C", s, t, 10, 14);
    try {
      m.addMotion("C", s, t, 8, 12);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Not adjacent motions");
      assertEquals(m.getOverview(), "shape C ellipse\n"
          + "motion C 10  3   4   1   2   5   6   7     14 13  14  11  12  15  16  17\n"
          + "\n"
          + "\n");
    }
  }

  // Both start and end ticks conflict with existing ticks
  @Test
  public void badInput7() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    Model m = new Model();
    IComponent c = new Ellipse();
    m.addShape("C", c);
    m.addMotion("C", s, t, 10, 14);
    m.addMotion("C", s, t, 14, 20);
    try {
      m.addMotion("C", s, t, 12, 16);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Not adjacent motions");
      assertEquals(m.getOverview(), "shape C ellipse\n"
          + "motion C 10  3   4   1   2   5   6   7     14 13  14  11  12  15  16  17\n"
          + "motion C 14  3   4   1   2   5   6   7     20 13  14  11  12  15  16  17\n"
          + "\n"
          + "\n");
    }
  }

  // Test that getStateAtTick gives the same answer between model and component
  @Test
  public void badInput8() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    Model m = new Model();
    IComponent c = new Ellipse();
    m.addShape("C", c);
    m.addMotion("C", s, t, 10, 14);
    m.addMotion("C", s, t, 14, 20);

    for(int i = 10; i < 21; i++){
      assertEquals(m.getStateAtTick("C",i), c.getStateAtTick(i));
    }
  }
}
