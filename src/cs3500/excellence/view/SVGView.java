package cs3500.excellence.view;

import cs3500.excellence.model.components.IComponent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class SVGView implements IView {

  Appendable output;

  List<IComponent> components;


  @Override
  public void drawTick(List<IComponent> components, int tick) {

  }

  @Override
  public void setComponents(List<IComponent> components) {

  }

  @Override
  public void setOverview(String overview) {

  }



}
