import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.excellence.model.BasicMotion;
import cs3500.excellence.model.IModel;
import cs3500.excellence.model.Model;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.Ellipse;
import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.model.components.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasicMoveModelTest {

  IModel basicModel;
  State s;
  State t;
  IComponent ellipse;


  /**
   * Setups a model, two states, and an ellipse.
   */
  @Before
  public void setUp() {
    basicModel = new Model();
    s = new State(1, 2, 3, 4, 5, 6, 7);
    t = new State(11, 12, 13, 14, 15, 16, 17);
    ellipse = new Ellipse();
  }

  @Test
  public void mutateIDs() {

    basicModel.addComponent("R", new Rectangle());
    assertEquals(1, basicModel.getAllIds().size());
    basicModel.getAllIds().clear();
    assertEquals(1, basicModel.getAllIds().size());

  }

  @Test
  public void mutateThruByID() {

    basicModel.addComponent("R", new Rectangle());

    assertEquals(false, basicModel.getComponentByID("R").hasMotionAtTick(1));

    basicModel.getComponentByID("R").addMotion(new BasicMotion(new State(0, 0, 0, 0, 0, 0, 0),
            new State(0, 0, 0, 0, 0, 0, 0), 1, 1));
    assertEquals(false, basicModel.getComponentByID("R").hasMotionAtTick(1));
  }


  @Test
  public void mutateComponents() {

    basicModel.addComponent("R", new Rectangle());
    basicModel.addMotion("R", new State(200, 200, 50, 100, 255, 0, 0),
            new State(200, 200, 50, 100, 255, 0, 0), 1, 10);

    List<IComponent> components = basicModel.getComponentsAtTick(1);
    assertEquals(1, components.size()); //Contains one component
    assertEquals(new State(200, 200, 50, 100, 255, 0, 0), components.get(0).getStateAtTick(1));

    components.get(0).addMotion(
            new BasicMotion(new State(0, 0, 0, 0, 0, 0, 0),
                    new State(0, 0, 0, 0, 0, 0, 0), 10, 20));

    components = basicModel.getComponentsAtTick(15);
    assertEquals(0, components.size()); //The addMotion only mutated the local version
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullComp() {
    basicModel.addComponent("R", null);
  }

  @Test
  public void emptyModel() {
    assertEquals(0, basicModel.getFinalTick());
    assertEquals("", basicModel.getOverview());
    assertEquals(0, basicModel.getComponentsAtTick(1).size());
    assertEquals(0, basicModel.getAllIds().size());
  }


  @Test
  public void wellFormedTests() {

    // Test simple add shape
    basicModel.addComponent("C", ellipse);
    assertEquals(basicModel.getOverview(),
            "shape C ellipse\n"
                    + "\n"
                    + "\n");
    assertEquals(1, basicModel.getAllIds().size());
    assertTrue(basicModel.getAllIds().contains("C"));
    // Test simple single command
    basicModel.addMotion("C", s, t, 2, 10);
    assertEquals(basicModel.getOverview(),
            "shape C ellipse\n"
                    + "motion C  2  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
                    + "\n"
                    + "\n");

    // Test if second object can be added to the model
    IComponent r = new Rectangle();
    basicModel.addComponent("R", r);
    basicModel.addMotion("R", t, s, 2, 10);
    assertEquals(basicModel.getOverview(),
            "shape C ellipse\n"
                    + "motion C  2  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
                    + "\n"
                    + "\n"
                    + "shape R rectangle\n"
                    + "motion R  2 11  12  13  14  15  16  17     10  1   2   3   4   5   6   7\n"
                    + "\n"
                    + "\n");

    State u = new State(3, 6, 9, 12, 15, 18, 21);
    State v = new State(4, 8, 12, 16, 20, 24, 28);
    State w = new State(5, 10, 15, 20, 25, 30, 35);
    State x = new State(6, 12, 18, 24, 30, 36, 42);

    // Test if object can do nothing
    basicModel.addMotion("R", u, u, 10, 20);
    assertEquals(basicModel.getOverview(),
            "shape C ellipse\n"
                    + "motion C  2  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
                    + "\n"
                    + "\n"
                    + "shape R rectangle\n"
                    + "motion R  2 11  12  13  14  15  16  17     10  1   2   3   4   5   6   7\n"
                    + "motion R 10  3   6   9  12  15  18  21     20  3   6   9  12  15  18  21\n"
                    + "\n"
                    + "\n");

    // Test if second move command can be added
    basicModel.addMotion("R", u, s, 20, 25);
    assertEquals(basicModel.getOverview(),
            "shape C ellipse\n"
                    + "motion C  2  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
                    + "\n"
                    + "\n"
                    + "shape R rectangle\n"
                    + "motion R  2 11  12  13  14  15  16  17     10  1   2   3   4   5   6   7\n"
                    + "motion R 10  3   6   9  12  15  18  21     20  3   6   9  12  15  18  21\n"
                    + "motion R 20  3   6   9  12  15  18  21     25  1   2   3   4   5   6   7\n"
                    + "\n"
                    + "\n");

    //Test if multiple objects can be added to model
    IComponent a = new Ellipse();
    IComponent b = new Rectangle();
    basicModel.addComponent("A", a);
    basicModel.addComponent("B", b);
    assertEquals(basicModel.getOverview(),
            "shape A ellipse\n"
                    + "\n"
                    + "\n"
                    + "shape B rectangle\n"
                    + "\n"
                    + "\n"
                    + "shape C ellipse\n"
                    + "motion C  2  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
                    + "\n"
                    + "\n"
                    + "shape R rectangle\n"
                    + "motion R  2 11  12  13  14  15  16  17     10  1   2   3   4   5   6   7\n"
                    + "motion R 10  3   6   9  12  15  18  21     20  3   6   9  12  15  18  21\n"
                    + "motion R 20  3   6   9  12  15  18  21     25  1   2   3   4   5   6   7\n"
                    + "\n"
                    + "\n");

    // Test if multiple move commands can be added to model
    basicModel.addMotion("C", v, w, 10, 30);
    basicModel.addMotion("R", v, w, 25, 30);
    basicModel.addMotion("C", w, x, 30, 40);
    basicModel.addMotion("R", w, x, 30, 40);
    assertEquals(basicModel.getOverview(),
            "shape A ellipse\n"
                    + "\n"
                    + "\n"
                    + "shape B rectangle\n"
                    + "\n"
                    + "\n"
                    + "shape C ellipse\n"
                    + "motion C  2  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
                    + "motion C 10  4   8  12  16  20  24  28     30  5  10  15  20  25  30  35\n"
                    + "motion C 30  5  10  15  20  25  30  35     40  6  12  18  24  30  36  42\n"
                    + "\n"
                    + "\n"
                    + "shape R rectangle\n"
                    + "motion R  2 11  12  13  14  15  16  17     10  1   2   3   4   5   6   7\n"
                    + "motion R 10  3   6   9  12  15  18  21     20  3   6   9  12  15  18  21\n"
                    + "motion R 20  3   6   9  12  15  18  21     25  1   2   3   4   5   6   7\n"
                    + "motion R 25  4   8  12  16  20  24  28     30  5  10  15  20  25  30  35\n"
                    + "motion R 30  5  10  15  20  25  30  35     40  6  12  18  24  30  36  42\n"
                    + "\n"
                    + "\n");

    // Well formed getStateAt tests
  }

  // Test for when object already exists in hashmap
  @Test
  public void badInput() {
    IComponent c = new Ellipse();
    basicModel.addComponent("C", c);
    basicModel.addMotion("C", s, t, 2, 10);
    IComponent r = new Rectangle();
    try {
      basicModel.addComponent("C", r);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Object already exists");
      assertEquals(basicModel.getOverview(), "shape C ellipse\n"
              + "motion C  2  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
              + "\n"
              + "\n");
    }
  }

  // Test for when object doesn't exist in hashmap (has not been registered)
  @Test
  public void badInput2() {
    IComponent c = new Ellipse();
    basicModel.addComponent("C", c);
    basicModel.addMotion("C", s, t, 2, 10);
    IComponent r = new Rectangle();
    basicModel.addComponent("R", r);
    try {
      basicModel.addMotion("S", t, s, 2, 10);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Object does not exist");
      assertEquals(basicModel.getOverview(), "shape C ellipse\n"
              + "motion C  2  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
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
    IComponent c = new Ellipse();
    basicModel.addComponent("C", c);
    basicModel.addMotion("C", s, t, 0, 10);
    IComponent r = new Rectangle();
    basicModel.addComponent("R", r);
    try {
      c.getStateAtTick(11);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Tick not valid");
      assertEquals(basicModel.getOverview(), "shape C ellipse\n"
              + "motion C  0  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
              + "\n"
              + "\n"
              + "shape R rectangle\n"
              + "\n"
              + "\n");
    }
    try {
      r.getStateAtTick(-1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Tick not valid");
      assertEquals(basicModel.getOverview(), "shape C ellipse\n"
              + "motion C  0  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
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
    basicModel.addComponent("C", ellipse);
    try {
      basicModel.addMotion("C", s, t, 10, 2);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "end tick must be greater than begin tick");
      assertEquals(basicModel.getOverview(), "shape C ellipse\n"
              + "\n"
              + "\n");
    }
  }

  // New starting tick conflicts with existing motion
  @Test
  public void badInput5() {
    IComponent c = new Ellipse();
    basicModel.addComponent("C", c);
    basicModel.addMotion("C", s, t, 2, 10);
    try {
      basicModel.addMotion("C", s, t, 8, 12);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Not adjacent motions");
      assertEquals(basicModel.getOverview(), "shape C ellipse\n"
              + "motion C  2  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
              + "\n"
              + "\n");
    }
  }

  // New ending tick conflicts with existing motion
  @Test
  public void badInput6() {
    IComponent c = new Ellipse();
    basicModel.addComponent("C", c);
    basicModel.addMotion("C", s, t, 10, 14);
    try {
      basicModel.addMotion("C", s, t, 8, 12);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Not adjacent motions");
      assertEquals(basicModel.getOverview(), "shape C ellipse\n"
              + "motion C 10  1   2   3   4   5   6   7     14 11  12  13  14  15  16  17\n"
              + "\n"
              + "\n");
    }
  }

  // Both start and end ticks conflict with existing ticks
  @Test
  public void badInput7() {
    basicModel.addComponent("C", ellipse);
    basicModel.addMotion("C", s, t, 10, 14);
    basicModel.addMotion("C", s, t, 14, 20);
    try {
      basicModel.addMotion("C", s, t, 12, 16);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Not adjacent motions");
      assertEquals(basicModel.getOverview(), "shape C ellipse\n"
              + "motion C 10  1   2   3   4   5   6   7     14 11  12  13  14  15  16  17\n"
              + "motion C 14  1   2   3   4   5   6   7     20 11  12  13  14  15  16  17\n"
              + "\n"
              + "\n");
    }
  }

  // Endpoints match but commands are given in non-chronological order
  @Test
  public void anachronicInput() {
    IComponent c = new Ellipse();
    basicModel.addComponent("C", c);
    try {
      basicModel.addMotion("C", s, t, 14, 20);
      basicModel.addMotion("C", s, t, 1, 14);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Not adjacent motions");
    }
  }

  // Endpoints match but commands are given in non-chronological order
  @Test(expected = IllegalArgumentException.class)
  public void motionGap() {
    basicModel.addComponent("E", ellipse);
    basicModel.addMotion("E", s, t, 1, 10);
    basicModel.addMotion("E", s, t, 15, 20);
  }

  @Test
  public void finalTicks() {
    assertEquals(0, basicModel.getFinalTick());

    basicModel.addComponent("E", ellipse);
    basicModel.addMotion("E", s, t, 1, 10);
    assertEquals(10, basicModel.getFinalTick());

    basicModel.addComponent("Z", new Ellipse());
    basicModel.addMotion("Z", s, t, 1, 30);
    assertEquals(30, basicModel.getFinalTick());
  }

  @Test(expected = IllegalArgumentException.class)
  public void noID() {
    basicModel.addComponent("E", ellipse);
    basicModel.getComponentByID("zack");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullStatesBoth() {
    basicModel.addComponent("E", ellipse);
    basicModel.addMotion("E", null, null, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullStatesFirst() {
    basicModel.addComponent("E", ellipse);
    basicModel.addMotion("E", null, t, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullStatesSecond() {
    basicModel.addComponent("E", ellipse);
    basicModel.addMotion("E", s, null, 1, 10);
  }

}
