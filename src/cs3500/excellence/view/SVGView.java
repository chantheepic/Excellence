package cs3500.excellence.view;

import cs3500.excellence.model.components.IROComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class SVGView implements IView{
  private List<IROComponent> components;
  private int speed;
  private int[] boundary;
  private Appendable out;

  public SVGView(Appendable out) {
    this.out = Objects.requireNonNull(out, "Output cannot be null");
    this.components = new ArrayList<>();
  }

  @Override
  public void setComponents(List<IROComponent> components, int[] boundary, int speed) {
    this.components = Objects.requireNonNull(components, "Components cannot be null");
    this.speed = speed;
    this.boundary = boundary;
    appendText(this.getOverview());
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
  private String setCanvas(){
    StringBuilder output = new StringBuilder();
    output.append(String.format("<svg width=\"%spx\" height=\"%spx\" xmlns=\"http://www.w3.org/2000/svg\"> \n", boundary[2], boundary[3]));
    for (int i = 0; i < components.size(); i++) {
      IROComponent comp = components.get(i);
      SVGShapes s = new SVGShapes();
      output.append(s.buildShapeAnimation(comp, speed));
    }
    output.append("</svg>");
    return output.toString();
  }



}