package cs3500.excellence.controller;

public interface Features {

  void setSpeed(int speed);
  void addShape(String name, String type);
  void deleteShape(String name);
  void createFrame(String name, int x, int y, int w, int h, int r, int g, int b);
  void deleteFrame(String name);
  void togglePlay();
  void restart();
  void toggleLoop();
  void setTick(int tick);
  void saveAsText(String fname);
  void saveAsSVG(String fname);
  void load(String fname);

}
