package cs3500.animator.view.svg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.animator.controller.Features;
import cs3500.animator.model.Boundary;
import cs3500.animator.model.components.IROComponent;
import cs3500.animator.view.IView;
import cs3500.animator.view.ErrPanel;

/**
 * Represents the SVG View. A SVGView is a type of IView that parses the model into an SVG format
 * file.
 */
public class SVGView implements IView {

  private List<IROComponent> components;
  private int speed;
  private Boundary boundary;
  private Appendable out;

  public SVGView(Appendable out) {
    this.out = Objects.requireNonNull(out, "Output cannot be null");
    this.components = new ArrayList<>();
  }

  @Override
  public void setComponents(List<IROComponent> components, Boundary boundary, int speed) {
    this.components = Objects.requireNonNull(components, "Components cannot be null");
    this.speed = speed;
    this.boundary = boundary;
    appendText(this.getOverview());
  }

  @Override
  public void setOutput(Appendable app) {
    this.out = app;
  }

  @Override
  public void setFeatureListener(Features listener) {
    //Do Nothing
  }

  @Override
  public void displayError(String msg) {
    ErrPanel.error(msg);
  }

  @Override
  public void tick(int currentTick) {
    //Do Nothing
  }

  private String getOverview() {
    StringBuilder output = new StringBuilder();
    output.append(setCanvas());
    return output.toString();
  }

  private void appendText(String s) {
    try {
      out.append(s);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // set canvas size
  private String setCanvas() {
    StringBuilder output = new StringBuilder();
    output.append(String.format(
        "<svg width=\"%spx\" height=\"%spx\" xmlns=\"http://www.w3.org/2000/svg\"> \n",
        boundary.getWidth(), boundary.getHeight()));
    SVGShapeFactory s = new SVGShapeFactory();
    for (IROComponent comp : components) {
      output.append(s.buildShapeAnimation(comp, boundary, speed));
    }
    output.append("</svg>");
    return output.toString();
  }

}