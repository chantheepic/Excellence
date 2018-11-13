package cs3500.excellence.view;

import static org.junit.Assert.assertEquals;

import cs3500.excellence.model.BasicMotion;
import cs3500.excellence.model.Boundary;
import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.Component;
import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.model.components.IROComponent;
import cs3500.excellence.model.components.Shape;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SimpleViewTests {

  List<IROComponent> roList = new ArrayList<>();
  IComponent e;
  IComponent r;

  @Before
  public void setUp() {
    e = new Component("E", Shape.ELLIPSE);
    r = new Component("R", Shape.RECTANGLE);
    State s = new State(1, 2, 33, 40, 5, 6, 7);
    State t = new State(110, 120, 13, 14, 15, 16, 17);
    IMotion forward = new BasicMotion(s, t, 0, 10);
    IMotion backward = new BasicMotion(t, s, 10, 20);
    IMotion forward2 = new BasicMotion(t, s, 0, 10);
    IMotion backward2 = new BasicMotion(s, s, 10, 20);

    e.addKeyFrame(forward);
    e.addKeyFrame(backward);
    r.addKeyFrame(forward2);
    r.addKeyFrame(backward2);
    roList.add(e);
    roList.add(r);
  }

  @Test
  public void simpleTextViewTest() {
    StringBuilder output = new StringBuilder();
    TextualView tView = new TextualView(output);
    tView.setComponents(roList, new Boundary(0, 0, 200, 200), 20);
    tView.setOutput(output);
    assertEquals(output.toString(), "shape E ELLIPSE\n"
        + "motion E 0 1 2 33 40 5 6 7    10 110 120 13 14 15 16 17\n"
        + "motion E 10 110 120 13 14 15 16 17    20 1 2 33 40 5 6 7\n"
        + "\n"
        + "\n"
        + "shape R RECTANGLE\n"
        + "motion R 0 110 120 13 14 15 16 17    10 1 2 33 40 5 6 7\n"
        + "motion R 10 1 2 33 40 5 6 7    20 1 2 33 40 5 6 7\n"
        + "\n"
        + "\n");
  }

  @Test
  public void simpleSVGViewTest() {
    StringBuilder output = new StringBuilder();
    SVGView tView = new SVGView(output);
    tView.setComponents(roList, new Boundary(0, 0, 200, 200), 20);
    tView.setOutput(output);
    assertEquals(output.toString(),
        "<svg width=\"200px\" height=\"200px\" xmlns=\"http://www.w3.org/2000/svg\"> \n"
            + "<ellipse cx=\"17\" cy=\"22\" rx=\"16\" ry=\"20\" fill=\"rgb(5,6,6)\"> \n"
            + "  <animate attributeName=\"fill\" from=\"rgb(5,6,7)\" to=\"rgb(15,16,17)\" "
            + "begin=\"0ms\" dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"rx\" from=\"33\" to=\"13\" begin=\"0ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"ry\" from=\"40\" to=\"14\" begin=\"0ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"cx\" from=\"17\" to=\"116\" begin=\"0ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"cy\" from=\"22\" to=\"127\" begin=\"0ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"fill\" from=\"rgb(15,16,17)\" to=\"rgb(5,6,7)\" "
            + "begin=\"500ms\" dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"rx\" from=\"13\" to=\"33\" begin=\"500ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"ry\" from=\"14\" to=\"40\" begin=\"500ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"cx\" from=\"116\" to=\"17\" begin=\"500ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"cy\" from=\"127\" to=\"22\" begin=\"500ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "</ellipse> \n"
            + "<rect x=\"110\" y=\"120\" width=\"13\" height=\"14\" fill=\"rgb(15,16,16)\"> \n"
            + "  <animate attributeName=\"fill\" from=\"rgb(15,16,17)\" to=\"rgb(5,6,7)\" "
            + "begin=\"0ms\" dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"width\" from=\"13\" to=\"33\" begin=\"0ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"height\" from=\"14\" to=\"40\" begin=\"0ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"x\" from=\"110\" to=\"1\" begin=\"0ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "  <animate attributeName=\"y\" from=\"120\" to=\"2\" begin=\"0ms\" "
            + "dur=\"500ms\" fill=\"freeze\"/> \n"
            + "</rect> \n"
            + "</svg>");
  }
}
