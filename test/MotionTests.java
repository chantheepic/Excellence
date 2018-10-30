import static org.junit.Assert.assertEquals;

import cs3500.excellence.model.hw05.BasicMotion;
import cs3500.excellence.model.hw05.IMotion;
import cs3500.excellence.model.hw05.State;
import org.junit.Test;

public class MotionTests {

  @Test
  public void MotionTests() {
    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);
    IMotion m = new BasicMotion(s, t, 0, 10);

    assertEquals(m.containsTick(-1), false);
    assertEquals(m.containsTick(0), true);
    assertEquals(m.containsTick(5), true);
    assertEquals(m.containsTick(10), true);
    assertEquals(m.containsTick(11), false);

    assertEquals(m.getStateAtTick(0).toString(), "  1   2   3   4   5   6   7");
    assertEquals(m.getStateAtTick(5).toString(), "  6   7   8   9  10  11  12");
    assertEquals(m.getStateAtTick(10).toString(), " 11  12  13  14  15  16  17");

    assertEquals(m.initialTick(), 0);
    assertEquals(m.endTick(), 10);

    assertEquals(m.getOverview(), "  0  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17");

    //TODO How would I test for copy
  }

  // Invalid tick value for getStateAtTick
  @Test
  public void invalidTickInput() {
    State s = new State(1,2,3,4,5,6,7);
    State t = new State(11,12,13,14,15,16,17);
    IMotion m = new BasicMotion(s,t,0,10);

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
}
