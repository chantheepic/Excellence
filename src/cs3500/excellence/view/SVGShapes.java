package cs3500.excellence.view;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.Component;
import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.model.components.Shape;
import java.util.ArrayList;

public class SVGShapes {

  public static void main(String[] args) {
    //new SVGShapes().buildShapeAnimation();
  }

  public String buildShapeAnimation(IComponent comp){
    StringBuilder output = new StringBuilder();
    if(comp.getShape() == Shape.RECT){
      SVGRect r = new SVGRect();
      output.append(r.declareShape(comp));
      output.append(r.animateShape(comp));
      output.append("</rect> \n");
    } else {
      SVGEllpse e = new SVGEllpse();
      output.append(e.declareShape(comp));
      output.append(e.animateShape(comp));
      output.append("</ellipse> \n");
    }
    return output.toString();
  }

  private static class SVGRect{
    private String declareShape(IComponent comp){
      ArrayList<IMotion> motions = comp.returnAllMotions();
      IMotion firstMotion = motions.get(1);
      State firstState = firstMotion.getStateAtTick(firstMotion.initialTick());
      StringBuilder output = new StringBuilder();

      output.append(String.format("<%s x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" fill=\"#%s\"> \n",
          comp.getShape(), firstState.xPos(), firstState.yPos(), firstState.width(), firstState.height(),
          Integer.toHexString(firstState.red()) + Integer.toHexString(firstState.green()) + Integer.toHexString(firstState.blue())));
      return output.toString();
    }

    private String animateShape(IComponent comp){
      return "b";
    }
  }

  private static class SVGEllpse{
    private String declareShape(IComponent comp){
      ArrayList<IMotion> motions = comp.returnAllMotions();
      IMotion firstMotion = motions.get(1);
      State firstState = firstMotion.getStateAtTick(firstMotion.initialTick());
      StringBuilder output = new StringBuilder();

      output.append(String.format("<%s cx=\"%s\" cy=\"%s\" rx=\"%s\" ry=\"%s\" fill=\"#%s\"> \n",
          comp.getShape(), firstState.xPos(), firstState.yPos(), firstState.width(), firstState.height(),
          Integer.toHexString(firstState.red()) + Integer.toHexString(firstState.green()) + Integer.toHexString(firstState.blue())));
      return output.toString();
    }

    private String animateShape(IComponent comp){
      return "d";
    }
  }

}

