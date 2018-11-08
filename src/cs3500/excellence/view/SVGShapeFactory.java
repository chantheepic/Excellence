package cs3500.excellence.view;

import java.util.ArrayList;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IROComponent;

/**
 * Represents a factory that creates the shape in svg form.
 */

public class SVGShapeFactory {


  public String buildShapeAnimation(IROComponent comp, int speed) {
    StringBuilder output = new StringBuilder();
    switch (comp.getShape()) {
      case ELLIPSE:
        SVGEllipse e = new SVGEllipse();
        output.append(e.declareShape(comp));
        output.append(e.animateShape(comp, speed));
        output.append("</ellipse> \n");
        break;
      case RECT:
        SVGRect r = new SVGRect();
        output.append(r.declareShape(comp));
        output.append(r.animateShape(comp, speed));
        output.append("</rect> \n");
        break;
      default:
        throw new IllegalArgumentException("Invalid shape type");
    }
    return output.toString();
  }

  private String commonBuild(IROComponent comp){

  }

  private String rectBuild(IROComponent comp, int speed){
    StringBuilder output = new StringBuilder();
    ArrayList<IMotion> motions = comp.returnAllMotions();
    IMotion firstMotion = motions.get(1);
    State firstState = firstMotion.getStateAtTick(firstMotion.initialTick());

    output.append(String.format("<%s x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" fill=\"rgb(%s,%s,%s)\"> \n",
        "rect", firstState.xPos(), firstState.yPos(), firstState.width(), firstState.height(),
        firstState.red(), firstState.green(), firstState.green()));

    for (IMotion motion : motions) {
      State s = motion.getStateAtTick(motion.initialTick());
      State e = motion.getStateAtTick(motion.endTick());

      long timeDelta = (motion.endTick() - motion.initialTick()) * 1000 / speed;
      long initialTime = motion.initialTick() * 1000 / speed;

      String stringFormat = "  <animate attributeName=\"%s\" from=\"%s\" to=\"%s\" begin=\"%sms\" dur=\"%sms\"/> \n";
      String[] attributes = new String[]{"x", "y", "width", "height"};



      if (s.width() != e.width()) {
        output.append(String.format(stringFormat
            , attributes[2], s.width(), e.width(), initialTime, timeDelta));
      }

      if (s.height() != e.height()) {
        output.append(String.format(stringFormat
            , attributes[3], s.height(), e.height(), initialTime, timeDelta));
      }

    }

    return output.toString();
  }

  private String ellipseBuild(IROComponent comp){
    return "";
  }


  private String commonBuild(State s, State e, int initialTime, int timeDelta, String stringFormat, String xPos, String yPos){
    StringBuilder output = new StringBuilder();

    if (s.xPos() != e.xPos()) {
      output.append(String.format(stringFormat
          , xPos, s.xPos(), e.xPos(), initialTime, timeDelta));
    }

    if (s.yPos() != e.yPos()) {
      output.append(String.format(stringFormat
          , yPos, s.yPos(), e.yPos(), initialTime, timeDelta));
    }


    String sColor = String.format("rgb(%s,%s,%s)", s.red(), s.green(), s.blue());
    String eColor = String.format("rgb(%s,%s,%s)", e.red(), e.green(), e.blue());

    if (!sColor.equals(eColor)) {
      output.append(String.format(stringFormat
          , "fill", sColor, eColor, initialTime, timeDelta));
    }
  }
}

