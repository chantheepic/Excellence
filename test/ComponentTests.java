import static org.junit.Assert.assertEquals;

import cs3500.excellence.model.hw05.BasicMotion;
import cs3500.excellence.model.hw05.IMotion;
import cs3500.excellence.model.hw05.State;
import cs3500.excellence.model.hw05.components.Ellipse;
import cs3500.excellence.model.hw05.components.IComponent;
import org.junit.Test;

public class ComponentTests {
  IComponent e = new Ellipse();

  @Test
  public void ComponentTests(){
    State s = new State(1,2,3,4,5,6,7);
    State t = new State(11,12,13,14,15,16,17);
    IMotion m = new BasicMotion(s,t,0,10);
    IMotion n = new BasicMotion(t,s,10,20);
    e.addMotion(m);
    e.addMotion(n);
    assertEquals(e.getOverview("Z"),"motion Z  0  1   2   3   4   5   6   7     10 11  12  13  14  15  16  17\n"
        + "motion Z 10 11  12  13  14  15  16  17     20  1   2   3   4   5   6   7\n");

    assertEquals(e.getStateAtTick(0).toString(),"  1   2   3   4   5   6   7");
    assertEquals(e.getStateAtTick(5).toString(),"  6   7   8   9  10  11  12");
    assertEquals(e.getStateAtTick(10).toString()," 11  12  13  14  15  16  17");
    assertEquals(e.getStateAtTick(11).toString()," 10  11  12  13  14  15  16");
    assertEquals(e.getStateAtTick(20).toString(),"  1   2   3   4   5   6   7");

    // Test Resilience of hasMotionAtTick
    assertEquals(e.hasMotionAtTick(-1), false);
    assertEquals(e.hasMotionAtTick(0), true);
    assertEquals(e.hasMotionAtTick(5), true);
    assertEquals(e.hasMotionAtTick(10), true);
    assertEquals(e.hasMotionAtTick(11), true);
    assertEquals(e.hasMotionAtTick(20), true);
    assertEquals(e.hasMotionAtTick(21), false);
  }

  // Invalid tick value for getStateAtTick
  @Test
  public void invalidTickInput() {
    State s = new State(1,2,3,4,5,6,7);
    State t = new State(11,12,13,14,15,16,17);
    IMotion m = new BasicMotion(s,t,0,10);
    IMotion n = new BasicMotion(t,s,10,20);
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
}
