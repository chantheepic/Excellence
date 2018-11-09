import org.junit.Before;
import org.junit.Test;

import cs3500.excellence.model.IModel;
import cs3500.excellence.model.Model;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.Component;
import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.model.components.Shape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



public class BasicMoveModelTest {

  IModel basicModel;
  State s;
  State t;


  /**
   * Setups a model, two states, and an ellipse.
   */
  @Before
  public void setUp() {
    basicModel = Model.builder().build();
    s = new State(1, 2, 3, 4, 5, 6, 7);
    t = new State(11, 12, 13, 14, 15, 16, 17);
  }

  @Test
  public void mutateIDs() {

    basicModel.addComponent("R", "rectangle");
    assertEquals(1, basicModel.getAllIds().size());
    basicModel.getAllIds().clear();
    assertEquals(1, basicModel.getAllIds().size());

  }

  @Test(expected = IllegalArgumentException.class)
  public void nullName() {
    basicModel.addComponent(null, "rectangle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullType() {
    basicModel.addComponent("R", null);
  }

  @Test
  public void emptyModel() {
//    assertEquals(0, basicModel.getFinalTick());
    assertEquals(0, basicModel.getComponentsAtTick(1).size());
    assertEquals(0, basicModel.getAllIds().size());
  }


  @Test
  public void wellFormedTests() {

    // Test simple add shape
    basicModel.addComponent("E", "ellipse");
    //has no motions
    assertEquals(0, basicModel.getComponentByID("E").returnAllMotions().size());
    assertEquals(Shape.ELLIPSE, basicModel.getComponentByID("E").getShape());

    assertEquals(1, basicModel.getAllIds().size());

    basicModel.addComponent("C", "rectangle");
    assertTrue(basicModel.getAllIds().contains("C"));


    // Test simple single command
    basicModel.addMotion("C", s, t, 2, 10);

    assertEquals(1,basicModel.getComponentByID("C").returnAllMotions().size());
    assertEquals(s,basicModel.getComponentByID("C").returnAllMotions().get(0).initialState());
    assertEquals(t,basicModel.getComponentByID("C").returnAllMotions().get(0).endState());


    // Test if second object can be added to the model
    basicModel.addComponent("R", "rectangle");
    basicModel.addMotion("R", t, s, 2, 10);
    assertEquals(3, basicModel.getAllComponents().size());

    State u = new State(3, 6, 9, 12, 15, 18, 21);
    State v = new State(4, 8, 12, 16, 20, 24, 28);
    State w = new State(5, 10, 15, 20, 25, 30, 35);
    State x = new State(6, 12, 18, 24, 30, 36, 42);

    // Test if object can do nothing
    basicModel.addMotion("R", u, u, 10, 20);
    assertEquals(2,basicModel.getComponentByID("R").returnAllMotions().size());
    assertEquals(u,basicModel.getComponentByID("R").returnAllMotions().get(1).initialState());
    assertEquals(u,basicModel.getComponentByID("R").returnAllMotions().get(1).endState());

    // Test if second move command can be added
    basicModel.addMotion("R", u, s, 20, 25);
    assertEquals(3,basicModel.getComponentByID("R").returnAllMotions().size());
    assertEquals(u,basicModel.getComponentByID("R").returnAllMotions().get(2).initialState());
    assertEquals(s,basicModel.getComponentByID("R").returnAllMotions().get(2).endState());


  }

  // Test for when object already exists in hashmap
  @Test
  public void badInput() {
    basicModel.addComponent("C", "ellipse");
    basicModel.addMotion("C", s, t, 2, 10);
    try {
      basicModel.addComponent("R", "rectangle");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Object already exists");
      assertEquals(1, basicModel.getComponentByID("C").returnAllMotions().size());
    }
  }

  // Test for when object doesn't exist in hashmap (has not been registered)
  @Test
  public void badInput2() {
    basicModel.addComponent("C", "ellipse");
    basicModel.addMotion("C", s, t, 2, 10);
    basicModel.addComponent("R", "rectangle");
    try {
      basicModel.addMotion("S", t, s, 2, 10);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Object does not exist");
      assertEquals(2, basicModel.getAllComponents().size());
    }
  }

  // Get state for tick that doesn't exist
  @Test
  public void badInput3() {
    basicModel.addComponent("C", "ellipse");
    basicModel.addMotion("C", s, t, 0, 10);
    basicModel.addComponent("R", "rectangle");
    try {
      basicModel.getComponentByID("C").getStateAtTick(11);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Tick not valid");
    }
    try {
      basicModel.getComponentByID("R").getStateAtTick(-1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Tick not valid");
    }
  }

  // Starting tick is later than ending tick
  @Test
  public void badInput4() {
    basicModel.addComponent("E", "ellipse");
    try {
      basicModel.addMotion("E", s, t, 10, 2);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "end tick must be greater than begin tick");
    }
  }

  // New starting tick conflicts with existing motion
  @Test
  public void badInput5() {
    basicModel.addComponent("C", "ellipse");
    basicModel.addMotion("C", s, t, 2, 10);
    try {
      basicModel.addMotion("C", s, t, 8, 12);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Not adjacent motions");
      assertEquals(1, basicModel.getComponentByID("C").returnAllMotions().size());
    }
  }

  // New ending tick conflicts with existing motion
  @Test
  public void badInput6() {
    basicModel.addComponent("C", "ellipse");
    basicModel.addMotion("C", s, t, 10, 14);
    try {
      basicModel.addMotion("C", s, t, 8, 12);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Not adjacent motions");
      assertEquals(1, basicModel.getComponentByID("C").returnAllMotions().size());
    }
  }

  // Both start and end ticks conflict with existing ticks
  @Test
  public void badInput7() {
    basicModel.addComponent("E", "ellipse");
    basicModel.addMotion("E", s, t, 10, 14);
    basicModel.addMotion("E", s, t, 14, 20);
    try {
      basicModel.addMotion("E", s, t, 12, 16);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Not adjacent motions");
      assertEquals(2, basicModel.getComponentByID("E").returnAllMotions().size());
    }
  }

  // Endpoints match but commands are given in non-chronological order
  @Test
  public void anachronicInput() {
    basicModel.addComponent("C", "ellipse");
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
    basicModel.addComponent("E", "ellipse");
    basicModel.addMotion("E", s, t, 1, 10);
    basicModel.addMotion("E", s, t, 15, 20);
  }

//  @Test
//  public void finalTicks() {
//    assertEquals(0, basicModel.getFinalTick());
//
//    basicModel.addComponent(ellipse);
//    basicModel.addMotion("E", s, t, 1, 10);
//    assertEquals(10, basicModel.getFinalTick());
//
//    basicModel.addComponent(new Component("Z", Shape.ELLIPSE));
//    basicModel.addMotion("Z", s, t, 1, 30);
//    assertEquals(30, basicModel.getFinalTick());
//  }

  @Test(expected = IllegalArgumentException.class)
  public void noID() {
    basicModel.addComponent("E", "ellipse");
    basicModel.getComponentByID("zack");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullStatesBoth() {
    basicModel.addComponent("E", "ellipse");
    basicModel.addMotion("E", null, null, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullStatesFirst() {
    basicModel.addComponent("E", "ellipse");
    basicModel.addMotion("E", null, t, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullStatesSecond() {
    basicModel.addComponent("E", "ellipse");
    basicModel.addMotion("E", s, null, 1, 10);
  }

}


