package cs3500.animator.provider.view;


import cs3500.animator.provider.model.interfaces.Animation;
import cs3500.animator.provider.model.interfaces.EasyAnimatorModelReadOnly;
import cs3500.animator.provider.model.interfaces.Shape;
import cs3500.animator.provider.view.interfaces.ExportView;
import cs3500.animator.provider.view.interfaces.View;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a svg view of the animation model. Outputs an svg file that will display
 * the animation in any svg viewer that supports animation.
 */
public class SVGView implements View, ExportView {

  // global variables for this class
  private EasyAnimatorModelReadOnly model;
  private Appendable out;
  private int tempo;
  private boolean isLooping;


  /**
   * Constructs an SVG view.
   *
   * @param model the model to construct the view from.
   * @param out the destination source to write to.
   * @param tempo the speed of the animation.
   */
  public SVGView(EasyAnimatorModelReadOnly model, Appendable out, int tempo) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("null model or out");
    }
    this.model = model;
    this.out = out;
    this.tempo = tempo;
    this.isLooping = false;
  }

  @Override
  public void display() throws IOException {
    List<Shape> shapes = model.getShapes();
    String boundBox = "\"" + model.getBounds().getX() + " " + model.getBounds().getY() + " " +
        model.getBounds().getWidth() + " " + model.getBounds().getHeight() + "\"";

    // normal SVG header
    out.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"1000\" " +
        "height=\"1000\"" + " viewBox=" + boundBox + " version=\"1.1\">\n");

    // used for loop back
    int currentDuration;
    int longestDuration = 0;
    int xReset = shapes.get(0).getX();
    for (Shape shape : shapes) {
      for (Animation animation : shape.getAnimations()) {
        currentDuration = (animation.getEndTime() - animation.getStartTime() / tempo) * 1000;
        longestDuration += currentDuration;
      }
    }
    out.append("<ellipse>\n");
    if (isLooping) {
      out.append(String.format("<animate id=\"base\" begin = \"0; base.end\" " +
                      "dur=\"%dms\" attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>",
              longestDuration + 10));
    }
    else {
      out.append(String.format("<animate id=\"base\" begin = \"0\" " +
                      "dur=\"%dms\" attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>",
              longestDuration + 10));
    }
    out.append("</ellipse>\n");

    // iterate through shapes
    // shapes know their animations, and so generate SVG outputs of their animations.
    for (Shape shape : shapes) {
      out.append(shape.toSVG(tempo, isLooping));
    }

    // reset all attributes for loop back
    if (isLooping) {
      out.append(String.format("<animate attributeType=\"xml\" begin=\"base.end\" dur =\"1ms\"" +
                      " attributeName=\"x\" to=\"%d\" fill =\"freeze\"/>",
              xReset));
    }

    out.append("</svg>");
  }

  @Override
  public void refresh() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Cannot refresh an SVG");
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  /**
   * Converts the duration of the animation from ticks to seconds, because that is needed for
   * the svg file.
   * @param animations The list of animations in the model.
   * @return The duration of the longest animation in seconds.
   */
  private double findDuration(ArrayList<Animation> animations) {
    double output = 0;
    for (Animation a : animations) {
      if ((a.getEndTime() - a.getStartTime()) > output) {
        output = a.getEndTime() - a.getStartTime();
      }
    }
    return output / tempo;
  }

  @Override
  public void export(String fileToExportTo) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileToExportTo)));
    writer.write(out.toString());
    writer.flush();
    writer.close();
  }
}
