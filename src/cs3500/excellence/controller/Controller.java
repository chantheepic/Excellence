package cs3500.excellence.controller;

import cs3500.excellence.model.IModel;
import cs3500.excellence.view.IView;

public class Controller {

  IModel model;
  IView view;


  public Controller(IModel model, IView view) {
    this.model = model;
    this.view = view;
  }


  public void go(){
    view.setComponents(model.getAllComponents(), model.getBoundary());
  }

}
