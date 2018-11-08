package cs3500.excellence.view;

import com.sun.org.apache.regexp.internal.RECompiler;
import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IComponent;

import cs3500.excellence.model.components.Shape;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SVGView implements IView{
  private List<IComponent> components;
  private int[] boundary;
  private Appendable out;

  public SVGView(Appendable out) {
    this.out = Objects.requireNonNull(out, "Output cannot be null");
    this.components = new ArrayList<>();
  }

  @Override
  public void setComponents(List<IComponent> components, int[] boundary) {
    this.components = Objects.requireNonNull(components, "Components cannot be null");
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
    output.append(String.format("<svg width=\"%spx\" height=\"%spx\"> \n", boundary[2], boundary[3]));
    output.append(assignShapes());
    output.append("</svg>");
    return output.toString();
  }

  // set ID shape, value, size, color
  private String assignShapes(){
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < components.size(); i++) {
      IComponent comp = components.get(i);
      SVGShapes s = new SVGShapes();
      output.append(s.buildShapeAnimation(comp));
    }
    return output.toString();
  }
  // animate

  private String animate(ArrayList<IMotion> motions, Shape shape) {
    StringBuilder output = new StringBuilder();
    for (IMotion motion : motions) {
      State s = motion.getStateAtTick(motion.initialTick());
      State e = motion.getStateAtTick(motion.endTick());

      long timeDelta = (motion.endTick() - motion.initialTick()) * 1000 / 30;
      long initialTime = motion.initialTick() *  1000 / 30;
      String stringFormat = "  <animate attributeName=\"%s\" from=\"%s\" to=\"%s\" begin=\"%sms\" dur=\"%sms\"/> \n";
      String[] attributes;
      if(shape == Shape.RECT){
        attributes = new String[]{"x", "y", "width", "height"};
      } else {
        attributes = new String[]{"cx", "cy", "rx", "ry"};
      }

      if(s.xPos() != e.xPos()){
        output.append(String.format(stringFormat
            ,attributes[0], s.xPos(), e.xPos(), initialTime, timeDelta));
      }

      if(s.yPos() != e.yPos()){
        output.append(String.format(stringFormat
            ,attributes[1], s.yPos(), e.yPos(), initialTime, timeDelta));
      }

      if(s.width() != e.width()){
        output.append(String.format(stringFormat
            ,attributes[2], s.width(), e.width(), initialTime, timeDelta));
      }

      if(s.height() != e.height()){
        output.append(String.format(stringFormat
            ,attributes[3], s.height(), e.height(), initialTime, timeDelta));
      }

      String sColor = "#" + Integer.toHexString(s.red()) + Integer.toHexString(s.green()) + Integer.toHexString(s.blue());
      String eColor = "#" + Integer.toHexString(s.red()) + Integer.toHexString(s.green()) + Integer.toHexString(s.blue());

      if(!sColor.equals(eColor)){
        output.append(String.format(stringFormat
            ,"fill", sColor, eColor, initialTime, timeDelta));
      }
    }
    return output.toString();
  }

}