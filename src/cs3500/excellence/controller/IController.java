package cs3500.excellence.controller;

public interface IController {


  String processCommand(String command);

  /**
   * Start the program, i.e. give control to the controller
   */
  void go();



}
