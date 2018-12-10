package cs3500.animator.view;

import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.Controller;
import cs3500.animator.model.IModel;
import cs3500.animator.model.MockModel;
import java.awt.event.ActionEvent;
import org.junit.Before;
import org.junit.Test;

public class EditorViewAndControllerTests {

  IModel mockModel;
  IView mockView;
  Controller controller;
  StringBuilder output;
  int speed = 20;

  @Before
  public void setUp() {
    output = new StringBuilder();
    this.mockModel = new MockModel(output);
    this.mockView = new MockEditorView(10, speed, "name of shape",
        "rectangle", 0, 10, 20, 30, 40, 50,
        60, 70, "name of save");
    this.controller = new Controller(mockModel, speed);
    controller.setView(mockView);
  }

  @Test
  public void addKeyframeTest() {
    output.setLength(0);
    ((MockEditorView)mockView).actionPerformed(new ActionEvent(new Object(), 0, "create keyframe"));
    assertEquals(output.toString(),
        "keyframe added\n"
            + "all components\n"
            + "boundary\n");
  }

  @Test
  public void removeKeyframeTest() {
    output.setLength(0);
    ((MockEditorView)mockView).actionPerformed(new ActionEvent(new Object(), 0, "delete keyframe"));
    assertEquals(output.toString(),
        "keyframe removed\n"
            + "all components\n"
            + "boundary\n");
  }

  @Test
  public void addShapeTest() {
    output.setLength(0);
    ((MockEditorView)mockView).actionPerformed(new ActionEvent(new Object(), 0, "create shape"));
    assertEquals(output.toString(),
        "component added\n"
            + "all components\n"
            + "boundary\n");
  }

  @Test
  public void removeShapeTest() {
    output.setLength(0);
    ((MockEditorView)mockView).actionPerformed(new ActionEvent(new Object(), 0, "delete shape"));
    assertEquals(output.toString(),
        "component removed\n"
            + "all components\n"
            + "boundary\n");
  }
}
