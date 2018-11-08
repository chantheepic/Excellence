package cs3500.excellence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import cs3500.excellence.model.IModel;
import cs3500.excellence.model.Model;
import cs3500.excellence.util.AnimationReader;
import cs3500.excellence.view.IView;
import cs3500.excellence.view.SVGView;
import cs3500.excellence.view.TextualView;
import cs3500.excellence.view.VisualAnimationView;

/**
 * This is the class that creates a model, and a view from the main method arguments. The required
 * parameters are -in and -view. The optional parameters are -out, and -speed.
 *
 * -in [the input file] -out [where to output animation] (default is System.out) -speed [how many
 * ticks per second] -view [text | visual | svg]
 */
public class Excellence {

  public static void main(String[] args) throws FileNotFoundException {
    Factory factory = new Factory();
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-in":
          if (i + 1 < args.length) {
            factory.parseIn(args[i + 1]);
          }
          break;
        case "-view":
          if (i + 1 < args.length) {
            factory.parseView(args[i + 1]);
          }
          break;
        case "-out":
          if (i + 1 < args.length) {
            factory.parseOut(args[i + 1]);
          }
          break;
        case "-speed":
          if (i + 1 < args.length) {
            factory.parseSpeed(args[i + 1]);
          }
          break;
      }
    }
    factory.execute();
  }

  /**
   * This static class creates the model and the view based on the arguments. This is where the
   * default values are set.
   */
  private static final class Factory {
    private IModel model;
    private IView view;
    private int speed = 1;
    private PrintWriter out = new PrintWriter(System.out);

    private void execute() {
      if (model == null) {
        //TODO output error box
        throw new IllegalArgumentException("Need to define input");
      }
      if (view == null) {
        //TODO output error box
        throw new IllegalArgumentException("Need to specify the view");
      }


      view.setComponents(model.getAllComponents(), model.getBoundary(), speed);


      out.close();

    }

    private void parseIn(String in) {
      try {
        this.model = AnimationReader
                .parseFile(new FileReader(new File(in)), Model.builder());
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }

    private void parseOut(String out) throws FileNotFoundException {
      this.out = new PrintWriter(out);
      if (view != null) {
        view.setOutput(this.out);
      }

    }

    private void parseView(String view) {

      switch (view) {
        case "text":
          this.view = new TextualView(out);
          break;
        case "visual":
          this.view = new VisualAnimationView();
          break;
        case "svg":
          this.view = new SVGView(out);
          break;
      }
      //TODO needs to make popup error
    }

    private void parseSpeed(String view) {
      try {
        speed = Integer.parseInt(view);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(e.getMessage()); //TODO needs to make popup error
      }
    }
  }

}
