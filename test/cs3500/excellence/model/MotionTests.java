package cs3500.excellence.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MotionTests {

  IMotion m;
  State s;
  State t;

  /**
   * Instantiates two states, and a motion.
   */
  @Before
  public void setup() {
    s = new State(1, 2, 3, 4, 5, 6, 7);
    t = new State(11, 12, 13, 14, 15, 16, 17);
    m = new BasicMotion(s, t, 0, 10);
  }

  @Test
  public void containtsTick() {

    //Tests containsTick
    assertEquals(false, m.containsTick(-1));
    assertEquals(true, m.containsTick(0));
    assertEquals(true, m.containsTick(5));
    assertEquals(true, m.containsTick(10));
    assertEquals(false, m.containsTick(11));
  }

  @Test
  public void initEnd() {
    assertEquals(0, m.initialTick());
    assertEquals(10, m.endTick());
  }

//  @Test
//  public void overview() {
//    assertEquals("  0  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17",
//            m.getOverview());
//  }

  @Test
  public void getState() {
    //Test math and getStateAtTick
    assertEquals(new State(1, 2, 3, 4, 5, 6, 7), m.getStateAtTick(0));
    assertEquals(new State(6, 7, 8, 9, 10, 11, 12), m.getStateAtTick(5));
    assertEquals(new State(11, 12, 13, 14, 15, 16, 17), m.getStateAtTick(10));
  }

  // Invalid tick value for getStateAtTick
  @Test
  public void invalidTickInput() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    IMotion m = new BasicMotion(s, t, 0, 10);

    try {
      m.getStateAtTick(-1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Tick not valid");
    }

    try {
      m.getStateAtTick(21);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Tick not valid");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void reverseTick() {
    m = new BasicMotion(s, t, 20, 10);
  }



  @Test(expected = IllegalArgumentException.class)
  public void tNullStates() {
    m = new BasicMotion(s, null, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void sNullStates() {
    m = new BasicMotion(null, t, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void bothNullStates() {
    m = new BasicMotion(null, null, 1, 10);
  }

}
