package cs3500.excellence.view;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.excellence.model.Boundary;
import cs3500.excellence.model.IMotion;
import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IROComponent;

public class TextualView implements IView {
  private List<IROComponent> components;
  private Appendable out;


  public TextualView(Appendable out) {
    this.out = Objects.requireNonNull(out, "Output cannot be null");
    this.components = new ArrayList<>();
  }

  @Override
  public void setComponents(List<IROComponent> components, Boundary boundary, int speed) {
    this.components = Objects.requireNonNull(components, "Components cannot be null");
    appendText(this.getOverview());
  }

  @Override
  public void setOutput(Appendable app) {
    this.out = Objects.requireNonNull(app, "Output cannot be null");
  }

  @Override
  public void setEditListener(IEditListener listener) {
    //DO NOTHING
  }

  @Override
  public void tick(int currentTick) {
    //DO NOTHING
  }

  private String getOverview() {
    StringBuilder output = new StringBuilder();
    for (IROComponent comp : this.components) {
      output.append(getComponentOverview(comp)).append("\n\n");
    }
    return output.toString();
  }

  private String getComponentOverview(IROComponent component) {

    StringBuilder output = new StringBuilder();
    output.append("shape ").append(component.getID()).append(" ").append(component.getShape()).append("\n");
    for (IMotion m : component.returnAllMotions()) {
      output.append("motion ").append(component.getID()).append(" ").append(getMotionOverview(m)).append("\n");
    }
    return output.toString();

  }

  private String getStateOverview(State state) {
    return String.format("%d %d %d %d %d %d %d",
            state.xPos(), state.yPos(), state.width(), state.height(),
            state.red(), state.green(), state.blue());
  }

  private String getMotionOverview(IMotion motion) {
    StringBuilder output = new StringBuilder();
    String initOut = String.format("%d %s",
            motion.initialTick(), getStateOverview(motion.initialState()));
    String endOut = String.format("%d %s",
            motion.endTick(), getStateOverview(motion.endState()));
    output.append(initOut).append("    ").append(endOut);
    return output.toString();
  }

  private void appendText(String s) {
    try {
      out.append(s);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
