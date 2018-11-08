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


public class Excellence {

  private IModel model;
  private IView view;
  private int speed;
  private PrintWriter out;

  public Excellence(IModel model, IView view, int speed, PrintWriter out) {
    this.model = model;
    this.view = view;
    this.speed = speed;
    this.out = out;

    view.setComponents(model.getAllComponents(), model.getBoundary(), speed);


    out.close();

  }

  public static void main(String[] args) throws FileNotFoundException {
    Builder builder = new Builder();
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-in":
          if (i + 1 < args.length) {
            builder.parseIn(args[i + 1]);
          }
          break;
        case "-view":
          if (i + 1 < args.length) {
            builder.parseView(args[i + 1]);
          }
          break;
        case "-out":
          if (i + 1 < args.length) {
            builder.parseOut(args[i + 1]);
          }
          break;
        case "-speed":
          if (i + 1 < args.length) {
            builder.parseSpeed(args[i + 1]);
          }
          break;
      }
    }
    builder.build();
  }

  private static final class Builder {
    private IModel model;
    private IView view;
    private String output = "untitled.txt";
    private int speed = 1;
    private PrintWriter out;

    private Excellence build() {
      return new Excellence(model, view, speed, out);
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
    }

    private void parseView(String view) {
      if (view.equals("text")) {
        this.view = new TextualView(out);
      }
      if (view.equals("visual")) {
        this.view = new VisualAnimationView();
      }
      if (view.equals("svg")) {
        this.view = new SVGView(out);
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
