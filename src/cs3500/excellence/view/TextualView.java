package cs3500.excellence.view;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.excellence.model.components.IComponent;

public class TextualView implements IView {
  private List<IComponent> components;
  private Appendable out;


  public TextualView(Appendable out) {
    this.out = Objects.requireNonNull(out, "Output cannot be null");
    this.components = new ArrayList<>();
  }

  @Override
  public void setComponents(List<IComponent> components, int[] boundary) {
    this.components = Objects.requireNonNull(components, "Components cannot be null");
    appendText(this.getOverview());
  }

  private String getOverview() {
    StringBuilder output = new StringBuilder();
    for (IComponent comp : this.components) {
      output.append(comp.getOverview()).append("\n\n");
    }
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
