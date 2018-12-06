package cs3500.animator.provider;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.BasicMotion;
import cs3500.animator.model.IMotion;
import cs3500.animator.model.IMotionToAnimationAdapter;
import cs3500.animator.model.State;
import cs3500.animator.provider.model.interfaces.Shape;
import org.junit.Test;

/**
 * Tests for IMotionToAnimationAdapter.
 */
public class IMotionToAnimationAdapterTest {

  State s = new State(10, 10, 10, 10, 10, 10, 10);
  IMotion motion = new BasicMotion(s, s, 10, 12);
  Shape shape = null;
  IMotionToAnimationAdapter adapter = new IMotionToAnimationAdapter(motion, shape);

  @Test
  public void testUnsupported() {
    try {
      adapter.getShapeName();
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), "");
    }

    try {
      adapter.copy();
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), "");
    }

    try {
      adapter.displayAnimation(10);
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), "");
    }

  }

  @Test
  public void testUnsupported2() {
    try {
      adapter.toSVG(10);
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), "");
    }

    try {
      adapter.rectangleSVG(10);
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), "");
    }

    try {
      adapter.ellipseSVG(10);
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), "");
    }
  }

  @Test
  public void testSupported() {
    assertEquals(adapter.getStartTime(), 10);
    assertEquals(adapter.getEndTime(), 12);
  }
}
