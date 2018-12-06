package cs3500.animator.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StateTests {

  State s = new State(1, 2, 3, 4, 5, 6, 7);

  @Test
  public void publicStateFieldTests() {
    assertEquals(s.xPos(), 1);
    assertEquals(s.yPos(), 2);
    assertEquals(s.width(), 3);
    assertEquals(s.height(), 4);
    assertEquals(s.red(), 5);
    assertEquals(s.green(), 6);
    assertEquals(s.blue(), 7);
  }

  // Invalid value for colors
  @Test
  public void badIColorInput() {
    try {
      State t = new State(0, 0, 0, 0, 256, 0, 0);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Color values cannot exceed 255");
    }
  }


  @Test(expected = IllegalArgumentException.class)
  public void invalidRed256() {
    s = new State(0, 0, 0, 0, 256, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRedNeg() {
    s = new State(0, 0, 0, 0, -20, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidGreen256() {
    s = new State(0, 0, 0, 0, 0, 256, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidGreenNeg() {
    s = new State(0, 0, 0, 0, 0, -2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidBlue256() {
    s = new State(0, 0, 0, 0, 0, 0, 256);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidBlueNeg() {
    s = new State(0, 0, 0, 0, 0, 0, -1);
  }

}
