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


  private static class SVGRect {
    private String declareShape(IROComponent comp) {
      ArrayList<IMotion> motions = comp.returnAllMotions();
      IMotion firstMotion = motions.get(1);
      State firstState = firstMotion.getStateAtTick(firstMotion.initialTick());
      StringBuilder output = new StringBuilder();

      output.append(String.format("<%s x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" fill=\"#%s\"> \n",
              "rect", firstState.xPos(), firstState.yPos(), firstState.width(), firstState.height(),
              Integer.toHexString(firstState.red()) + Integer.toHexString(firstState.green()) + Integer.toHexString(firstState.blue())));
      return output.toString();
    }

    private String animateShape(IROComponent comp, int speed) {
      ArrayList<IMotion> motions = comp.returnAllMotions();
      StringBuilder output = new StringBuilder();
      for (IMotion motion : motions) {
        State s = motion.getStateAtTick(motion.initialTick());
        State e = motion.getStateAtTick(motion.endTick());

        long timeDelta = (motion.endTick() - motion.initialTick()) * 1000 / speed;
        long initialTime = motion.initialTick() * 1000 / speed;
        String stringFormat = "  <animate attributeName=\"%s\" from=\"%s\" to=\"%s\" begin=\"%sms\" dur=\"%sms\"/> \n";
        String[] attributes = new String[]{"x", "y", "width", "height"};

        if (s.xPos() != e.xPos()) {
          output.append(String.format(stringFormat
                  , attributes[0], s.xPos(), e.xPos(), initialTime, timeDelta));
        }

        if (s.yPos() != e.yPos()) {
          output.append(String.format(stringFormat
                  , attributes[1], s.yPos(), e.yPos(), initialTime, timeDelta));
        }

        if (s.width() != e.width()) {
          output.append(String.format(stringFormat
                  , attributes[2], s.width(), e.width(), initialTime, timeDelta));
        }

        if (s.height() != e.height()) {
          output.append(String.format(stringFormat
                  , attributes[3], s.height(), e.height(), initialTime, timeDelta));
        }

        String sColor = "#" + Integer.toHexString(s.red()) + Integer.toHexString(s.green()) + Integer.toHexString(s.blue());
        String eColor = "#" + Integer.toHexString(s.red()) + Integer.toHexString(s.green()) + Integer.toHexString(s.blue());

        if (!sColor.equals(eColor)) {
          output.append(String.format(stringFormat
                  , "fill", sColor, eColor, initialTime, timeDelta));
        }
      }
      return output.toString();
    }
  }

  private static class SVGEllipse {
    private String declareShape(IROComponent comp) {
      ArrayList<IMotion> motions = comp.returnAllMotions();
      IMotion firstMotion = motions.get(1);
      State firstState = firstMotion.getStateAtTick(firstMotion.initialTick());
      StringBuilder output = new StringBuilder();

      output.append(String.format("<%s cx=\"%s\" cy=\"%s\" rx=\"%s\" ry=\"%s\" fill=\"#%s\"> \n",
              "ellipse", firstState.xPos(), firstState.yPos(), firstState.width() / 2, firstState.height() / 2,
              Integer.toHexString(firstState.red()) + Integer.toHexString(firstState.green()) + Integer.toHexString(firstState.blue())));
      return output.toString();
    }

    private String animateShape(IROComponent comp, int speed) {
      ArrayList<IMotion> motions = comp.returnAllMotions();
      StringBuilder output = new StringBuilder();
      for (IMotion motion : motions) {
        State s = motion.getStateAtTick(motion.initialTick());
        State e = motion.getStateAtTick(motion.endTick());

        long timeDelta = (motion.endTick() - motion.initialTick()) * 1000 / speed;
        long initialTime = motion.initialTick() * 1000 / speed;
        String stringFormat = "  <animate attributeName=\"%s\" from=\"%s\" to=\"%s\" begin=\"%sms\" dur=\"%sms\"/> \n";
        String[] attributes = new String[]{"cx", "cy", "rx", "ry"};

        if (s.xPos() != e.xPos()) {
          output.append(String.format(stringFormat
                  , attributes[0], s.xPos(), e.xPos(), initialTime, timeDelta));
        }

        if (s.yPos() != e.yPos()) {
          output.append(String.format(stringFormat
                  , attributes[1], s.yPos(), e.yPos(), initialTime, timeDelta));
        }

        if (s.width() != e.width()) {
          output.append(String.format(stringFormat
                  , attributes[2], s.width() / 2, e.width() / 2, initialTime, timeDelta));
        }

        if (s.height() != e.height()) {
          output.append(String.format(stringFormat
                  , attributes[3], s.height() / 2, e.height() / 2, initialTime, timeDelta));
        }

        String sColor = "#" + Integer.toHexString(s.red()) + Integer.toHexString(s.green()) + Integer.toHexString(s.blue());
        String eColor = "#" + Integer.toHexString(s.red()) + Integer.toHexString(s.green()) + Integer.toHexString(s.blue());

        if (!sColor.equals(eColor)) {
          output.append(String.format(stringFormat
                  , "fill", sColor, eColor, initialTime, timeDelta));
        }
      }
      return output.toString();
    }
  }

}
