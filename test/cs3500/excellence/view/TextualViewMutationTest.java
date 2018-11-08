package cs3500.excellence.view;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import cs3500.excellence.Interaction;
import cs3500.excellence.model.BasicMotion;
import cs3500.excellence.model.IModel;
import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.Model;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.Shape;

import static org.junit.Assert.assertEquals;

public class TextualViewMutationTest {

  IModel basicModel;

  @Before
  public void setUp() throws Exception {
    basicModel = new Model();
  }

  @Test
  public void basicModelMutation(){

    State s = new State(1, 2, 3, 4, 5, 6, 7);
    State t = new State(11, 12, 13, 14, 15, 16, 17);

    assertTestRun(basicModel,
            prints());

    basicModel.addComponent("R", "rectangle");

    assertTestRun(basicModel,
            prints("shape R RECTANGLE\n\n"));


    basicModel.addMotion("R", s, t, 0, 10);

    assertTestRun(basicModel,
            prints("shape R RECTANGLE"),
            prints("motion R 0 1 2 3 4 5 6 7    10 11 12 13 14 15 16 17\n\n"));
    basicModel.addMotion("R",t,s,10,20);
    assertTestRun(basicModel,
            prints("shape R RECTANGLE"),
            prints("motion R 0 1 2 3 4 5 6 7    10 11 12 13 14 15 16 17"),
            prints("motion R 10 11 12 13 14 15 16 17    20 1 2 3 4 5 6 7\n\n"));

    basicModel.addComponent("E", "ellipse");

    assertTestRun(basicModel,
            prints("shape R RECTANGLE"),
            prints("motion R 0 1 2 3 4 5 6 7    10 11 12 13 14 15 16 17"),
            prints("motion R 10 11 12 13 14 15 16 17    20 1 2 3 4 5 6 7\n\n"),
            prints("shape E ELLIPSE\n\n"));

    basicModel.addMotion("E", t,s,5,16);

    assertTestRun(basicModel,
            prints("shape R RECTANGLE"),
            prints("motion R 0 1 2 3 4 5 6 7    10 11 12 13 14 15 16 17"),
            prints("motion R 10 11 12 13 14 15 16 17    20 1 2 3 4 5 6 7\n\n"),
            prints("shape E ELLIPSE"),
            prints("motion E 5 11 12 13 14 15 16 17    16 1 2 3 4 5 6 7\n\n"));

    basicModel.removeMotion("R", 25);

    assertTestRun(basicModel,
            prints("shape R RECTANGLE"),
            prints("motion R 0 1 2 3 4 5 6 7    10 11 12 13 14 15 16 17"),
            prints("motion R 10 11 12 13 14 15 16 17    20 1 2 3 4 5 6 7\n\n"),
            prints("shape E ELLIPSE"),
            prints("motion E 5 11 12 13 14 15 16 17    16 1 2 3 4 5 6 7\n\n"));

    basicModel.removeMotion("R", 5);
    assertTestRun(basicModel,
            prints("shape R RECTANGLE"),
            prints("motion R 10 11 12 13 14 15 16 17    20 1 2 3 4 5 6 7\n\n"),
            prints("shape E ELLIPSE"),
            prints("motion E 5 11 12 13 14 15 16 17    16 1 2 3 4 5 6 7\n\n"));

    basicModel.removeComponent("Z");
    assertTestRun(basicModel,
            prints("shape R RECTANGLE"),
            prints("motion R 10 11 12 13 14 15 16 17    20 1 2 3 4 5 6 7\n\n"),
            prints("shape E ELLIPSE"),
            prints("motion E 5 11 12 13 14 15 16 17    16 1 2 3 4 5 6 7\n\n"));



    basicModel.removeComponent("E");
    assertTestRun(basicModel,
            prints("shape R RECTANGLE"),
            prints("motion R 10 11 12 13 14 15 16 17    20 1 2 3 4 5 6 7\n\n"));

    basicModel.removeAllComponent();
    assertTestRun(basicModel,
            prints());

  }



  private static Interaction prints(String... lines) {
    return (input, output) -> {
      for (String line : lines) {
        output.append(line).append('\n');
      }
    };
  }

  private static Interaction inputs(String in) {
    return (input, output) -> {
      input.append(in).append("\n");
    };
  }

  private void assertTestRun(IModel model, Interaction... interactions)
          throws IllegalArgumentException, IllegalStateException {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }

    StringReader input = new StringReader(fakeUserInput.toString());
    StringBuilder actualOutput = new StringBuilder();

    IView view = new TextualView(actualOutput);
    view.setComponents(model.getAllComponents(), model.getBoundary(), 1);

    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }
}