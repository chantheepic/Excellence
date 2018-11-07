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
      ArrayList<IMotion> motions = comp.returnAllMotions();
      IMotion firstMotion = motions.get(1);
      State firstState = firstMotion.getStateAtTick(firstMotion.initialTick());
      String shape;


      if(comp.getShape() == Shape.RECT){
        shape = "rect";
        output.append(String.format("<%s x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" fill=\"#%s\"> \n",
            shape, firstState.xPos(), firstState.yPos(), firstState.width(), firstState.height(),
            Integer.toHexString(firstState.red()) + Integer.toHexString(firstState.green()) + Integer.toHexString(firstState.blue())));
        output.append(animate(motions, comp.getShape()));
        output.append("</rect> \n");
      } else {
        shape = "ellipse";
        output.append(String.format("<%s cx=\"%s\" cy=\"%s\" rx=\"%s\" ry=\"%s\" fill=\"#%s\"> \n",
            shape, firstState.xPos(), firstState.yPos(), firstState.width(), firstState.height(),
            Integer.toHexString(firstState.red()) + Integer.toHexString(firstState.green()) + Integer.toHexString(firstState.blue())));
        output.append(animate(motions, comp.getShape()));
        output.append("</ellipse> \n");
      }
      //TODO tidy up this mess
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



//<svg width="300px" height="100px">
//<rect x="0" y="50" width="20" height="15" fill="blue">
//<animate attributeName="x" from="0" to="100" dur="2s"/>
//</rect>
//<circle cx="0" cy="50" r="15" fill="blue">
//<animate attributeName="cy" from="0" to="100" dur="2s"/>
//</circle>
//</svg>