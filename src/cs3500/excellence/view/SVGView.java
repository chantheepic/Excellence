package cs3500.excellence.view;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.excellence.model.Boundary;
import cs3500.excellence.model.components.IROComponent;

/**
 * Represents the SVG View.
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
  public void setEditListener(IEditListener listener) {

  }

  @Override
  public void tick(int currentTick) {

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
    output.append(String.format
            ("<svg width=\"%spx\" height=\"%spx\" xmlns=\"http://www.w3.org/2000/svg\"> \n",
                    boundary.getWidth(), boundary.getHeight()));
    SVGShapeFactory s = new SVGShapeFactory();
    for (IROComponent comp : components) {
      output.append(s.buildShapeAnimation(comp, boundary, speed));
    }
    output.append("</svg>");
    return output.toString();
  }

}