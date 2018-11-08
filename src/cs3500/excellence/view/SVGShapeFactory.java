package cs3500.excellence.view;

import cs3500.excellence.model.Boundary;
import java.util.ArrayList;

import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IROComponent;

/**
 * Represents a factory that creates the shape in svg form.
 */

public class SVGShapeFactory {


  public String buildShapeAnimation(IROComponent comp, Boundary boundary, int speed) {
    StringBuilder output = new StringBuilder();
    switch (comp.getShape()) {
      case ELLIPSE:
        output.append(ellipseBuild(comp, boundary, speed));
        output.append("</ellipse> \n");
        break;
      case RECTANGLE:
        output.append(rectBuild(comp, boundary, speed));
        output.append("</rect> \n");
        break;
      default:
        throw new IllegalArgumentException("Invalid shape type");
    }
    return output.toString();
  }

  private String rectBuild(IROComponent comp, Boundary boundary, int speed){
    StringBuilder output = new StringBuilder();
    ArrayList<IMotion> motions = comp.returnAllMotions();
    IMotion firstMotion = motions.get(1);
    State firstState = firstMotion.getStateAtTick(firstMotion.initialTick());

    output.append(String.format("<%s x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" fill=\"rgb(%s,%s,%s)\"> \n",
        "rect", firstState.xPos() - boundary.getX(), firstState.yPos() - boundary.getY(), firstState.width(), firstState.height(),
        firstState.red(), firstState.green(), firstState.green()));

    for (IMotion motion : motions) {
      State s = motion.getStateAtTick(motion.initialTick());
      State e = motion.getStateAtTick(motion.endTick());

      int timeDelta = (motion.endTick() - motion.initialTick()) * 1000 / speed;
      int initialTime = motion.initialTick() * 1000 / speed;

      String stringFormat = "  <animate attributeName=\"%s\" from=\"%s\" to=\"%s\" begin=\"%sms\" dur=\"%sms\" fill=\"freeze\"/> \n";
      String[] attributes = new String[]{"x", "y", "width", "height"};

      output.append(commonBuild(boundary, s, e, initialTime, timeDelta, stringFormat, attributes[0], attributes[1]));

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

  private String ellipseBuild(IROComponent comp, Boundary boundary, int speed){
    StringBuilder output = new StringBuilder();
    ArrayList<IMotion> motions = comp.returnAllMotions();
    IMotion firstMotion = motions.get(1);
    State firstState = firstMotion.getStateAtTick(firstMotion.initialTick());

    output.append(String.format("<%s cx=\"%s\" cy=\"%s\" rx=\"%s\" ry=\"%s\" fill=\"rgb(%s,%s,%s)\"> \n",
        "ellipse", firstState.xPos() - boundary.getX(), firstState.yPos() - boundary.getY(), firstState.width(), firstState.height(),
        firstState.red(), firstState.green(), firstState.green()));

    for (IMotion motion : motions) {
      State s = motion.getStateAtTick(motion.initialTick());
      State e = motion.getStateAtTick(motion.endTick());

      int timeDelta = (motion.endTick() - motion.initialTick()) * 1000 / speed;
      int initialTime = motion.initialTick() * 1000 / speed;

      String stringFormat = "  <animate attributeName=\"%s\" from=\"%s\" to=\"%s\" begin=\"%sms\" dur=\"%sms\" fill=\"freeze\"/> \n";
      String[] attributes = new String[]{"cx", "cy", "rx", "ry"};

      output.append(commonBuild(boundary, s, e, initialTime, timeDelta, stringFormat, attributes[0], attributes[1]));

      if (s.width() != e.width()) {
        output.append(String.format(stringFormat
            , attributes[2], s.width()/2, e.width()/2, initialTime, timeDelta));
      }
      if (s.height() != e.height()) {
        output.append(String.format(stringFormat
            , attributes[3], s.height()/2, e.height()/2, initialTime, timeDelta));
      }
    }
    return output.toString();
  }


  private String commonBuild(Boundary boundary, State s, State e, int initialTime, int timeDelta, String stringFormat, String xPos, String yPos){
    StringBuilder output = new StringBuilder();

    if (s.xPos() != e.xPos()) {
      output.append(String.format(stringFormat
          , xPos, s.xPos() - boundary.getX(), e.xPos() - boundary.getX(), initialTime, timeDelta));
    }

    if (s.yPos() != e.yPos()) {
      output.append(String.format(stringFormat
          , yPos, s.yPos() - boundary.getY(), e.yPos() - boundary.getY(), initialTime, timeDelta));
  }

    String sColor = String.format("rgb(%s,%s,%s)", s.red(), s.green(), s.blue());
    String eColor = String.format("rgb(%s,%s,%s)", e.red(), e.green(), e.blue());

    if (!sColor.equals(eColor)) {
      output.append(String.format(stringFormat
          , "fill", sColor, eColor, initialTime, timeDelta));
    }
    return output.toString();
  }
}