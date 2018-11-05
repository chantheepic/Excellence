package cs3500.excellence.view;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.excellence.model.components.IComponent;

public class TextualView implements IView {


  List<IComponent> components;
  private String overview;
  Appendable out;


  public TextualView(Appendable out) {
    this.out = Objects.requireNonNull(out, "Output cannot be null");
    components = new ArrayList<>();
  }

  @Override
  public void drawTick(List<IComponent> components, int tick) {
    throw new UnsupportedOperationException("Only supported for visual views");
  }

  @Override
  public void setComponents(List<IComponent> components) {

  }

  public String getOverview() {
    StringBuilder output = new StringBuilder();
    for (String componentId : registeredShapes.keySet()) {
      IComponent component = registeredShapes.get(componentId);
      output.append("shape " + componentId + " " + component).append("\n");
      output.append(component.getOverview(componentId)).append("\n\n");
    }
    return output.toString();
  }

  @Override
  public void setOverview(String overview) {
    this.overview = overview;
    appendText(this.overview);
  }

  private void appendText(String s) {
    try {
      out.append(s);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
