package cs3500.excellence.view;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IComponent;

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
    for (int compIndx = 0; compIndx < components.size(); compIndx++) {
      IComponent comp = components.get(compIndx);
      ArrayList<IMotion> motions = comp.returnAllMotions();


      IMotion firstMotion = motions.get(1);
      State firstState = firstMotion.getStateAtTick(firstMotion.initialTick());
      output.append(String.format("<rect x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" fill=\"%s\"> \n",
          firstState.xPos(), firstState.yPos(), firstState.width(), firstState.height(),
          Integer.toHexString(firstState.red()) + Integer.toHexString(firstState.red()) + Integer.toHexString(firstState.red())));

      output.append(animate(motions));

      output.append("</rect> \n");
    }
    return output.toString();
  }
  // animate

  private String animate(ArrayList<IMotion> motions) {
    StringBuilder output = new StringBuilder();
    for (IMotion motion : motions) {
      State s = motion.getStateAtTick(motion.initialTick());
      State e = motion.getStateAtTick(motion.endTick());

      if(s.xPos() != e.xPos()){
        output.append(String.format("  <animate attributeName=\"%s\" from=\"%s\" to=\"%s\" dur=\"%ss\"/> \n"
            ,"x", s.height(), e.height(), motion.endTick() - motion.initialTick()));
      }

      if(s.yPos() != e.yPos()){
        output.append(String.format("  <animate attributeName=\"%s\" from=\"%s\" to=\"%s\" dur=\"%ss\"/> \n"
            ,"y", s.height(), e.height(), motion.endTick() - motion.initialTick()));
      }

      if(s.width() != e.width()){
        output.append(String.format("  <animate attributeName=\"%s\" from=\"%s\" to=\"%s\" dur=\"%ss\"/> \n"
            ,"width", s.height(), e.height(), motion.endTick() - motion.initialTick()));
      }

      if(s.height() != e.height()){
        output.append(String.format("  <animate attributeName=\"%s\" from=\"%s\" to=\"%s\" dur=\"%ss\"/> \n"
            ,"height", s.height(), e.height(), motion.endTick() - motion.initialTick()));
      }

      String sColor = Integer.toHexString(s.red()) + Integer.toHexString(s.red()) + Integer.toHexString(s.red());
      String eColor = Integer.toHexString(s.red()) + Integer.toHexString(s.red()) + Integer.toHexString(s.red());

      if(!sColor.equals(eColor)){
        output.append(String.format("  <animate attributeName=\"%s\" from=\"%s\" to=\"%s\" dur=\"%ss\"/> \n"
            ,"fill", sColor, eColor, motion.endTick() - motion.initialTick()));
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