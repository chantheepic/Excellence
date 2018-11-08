package cs3500.excellence.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.excellence.model.components.Component;
import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.model.components.Shape;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ComponentTests {

  IComponent e;
  IComponent r;

  @Before
  public void setUp() {
    e = new Component("E", Shape.ELLIPSE);
    r = new Component("R", Shape.RECTANGLE);
  }

  @Test
  public void componentAddMotion() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    IMotion forward = new BasicMotion(s, t, 0, 10);
    IMotion backward = new BasicMotion(t, s, 10, 20);

    assertEquals(0, e.returnAllMotions().size());

    e.addMotion(forward);
    e.addMotion(backward);

    //Has 2 motions
    assertEquals(2, e.returnAllMotions().size());

    IMotion first = e.returnAllMotions().get(0);
    IMotion second = e.returnAllMotions().get(1);

    //Ticks are correct
    assertEquals(0, first.initialTick());
    assertEquals(10, first.endTick());

    assertEquals(new State(1, 2, 3, 4, 5, 6, 7), first.initialState());
    assertEquals(new State(11, 12, 13, 14, 15, 16, 17), first.endState());

//    //Checks that overview works
//    assertEquals("motion E  0  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
//                    + "motion E 10 11  12  13  14  15  16  17     20  1   2   3   4   5   6   7\n",
//            e.getOverview());

    //0->10 , 10->20 are critical points
    Assert.assertEquals(new State( 1 ,  2 ,  3 ,  4 ,  5 ,  6 ,  7), e.getStateAtTick(0));
    Assert.assertEquals(new State( 6,   7 ,  8 ,  9 , 10 , 11 , 12),  e.getStateAtTick(5));
    Assert.assertEquals(new State( 11,   12 ,  13 ,  14 , 15 , 16 , 17),  e.getStateAtTick(10));
    Assert.assertEquals(new State( 10,   11 ,  12 ,  13 , 14 , 15 , 16),  e.getStateAtTick(11));
    Assert.assertEquals(new State( 1,   2 ,  3 ,  4 , 5 , 6 , 7),  e.getStateAtTick(20));


    // Test Resilience of hasMotionAtTick
    assertEquals(false, e.hasMotionAtTick(-1));
    assertEquals(true, e.hasMotionAtTick(0));
    assertEquals(true, e.hasMotionAtTick(5));
    assertEquals(true, e.hasMotionAtTick(10));
    assertEquals(true, e.hasMotionAtTick(11));
    assertEquals(true, e.hasMotionAtTick(20));
    assertEquals(false, e.hasMotionAtTick(21));

    //Final Ticks
    assertEquals(20, e.getFinalTick());
    assertEquals(0, r.getFinalTick());
  }

  // Invalid tick value for getStateAtTick
  @Test
  public void invalidTickInput() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    IMotion m = new BasicMotion(s, t, 0, 10);
    IMotion n = new BasicMotion(t, s, 10, 20);
    e.addMotion(m);
    e.addMotion(n);

    try {
      e.getStateAtTick(-1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Tick not valid");
    }

    try {
      e.getStateAtTick(21);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Tick not valid");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNullEllipse() {
    e.addMotion(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNulRect() {
    r.addMotion(null);
  }


}
