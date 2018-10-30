import static org.junit.Assert.assertEquals;

import cs3500.excellence.model.hw05.State;
import org.junit.Test;

public class StateTests {
  State s = new State(1,2,3,4,5,6,7);

  @Test
  public void publicStateFieldTests(){
    assertEquals(s.x(), 1);
    assertEquals(s.y(), 2);
    assertEquals(s.w(), 3);
    assertEquals(s.h(), 4);
    assertEquals(s.red(),5);
    assertEquals(s.green(),6);
    assertEquals(s.blue(), 7);
  }

  // Invalid value for colors
  @Test
  public void badIColorInput() {
    try {
      State t = new State(0,0,0,0,256, 0,0);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Color values cannot exceed 255");
    }
  }
}
