package cs3500.animator.controller;

/**
 * This interface represents all of the features that could be carried out. It should be implemented
 * by whatever class can mutate the model accordingly. (Controller). Note: non of the methods throw
 * an error. This is because the implementor should take care of the errors that might be thrown.
 */
public interface Features {

  /**
   * This method requests a speed change of the animation.
   *
   * @param speed - sets the speed.
   */
  void setSpeed(int speed);

  /**
   * Requests that a new shape be added with given name and type.
   *
   * @param name - given name of shape.
   * @param type - given type of shape (See supported types)
   */
  void addShape(String name, String type);

  /**
   * Requests that the shape with the given name be deleted.
   *
   * @param name - given name.
   */
  void deleteShape(String name);

  /**
   * Requests that a frame be created with given name and values (at the current tick).
   *
   * @param name - given shape name
   * @param x - X position
   * @param y - Y position
   * @param w - width
   * @param h - height
   * @param r - red
   * @param g - green
   * @param b - blue
   */
  void createFrame(String name, int x, int y, int w, int h, int r, int g, int b);

  /**
   * Requests that a frame be deleted from the shape with given name (at the current tick).
   *
   * @param name - given shape name.
   */
  void deleteFrame(String name);

  /**
   * Requests that the animation start or stop depending on the current running state.
   */
  void togglePlay();

  /**
   * Requests that the animation be restarted to tick 0.
   */
  void restart();

  /**
   * Requests that the animation either be looped or not loop.
   */
  void toggleLoop();

  /**
   * Requests that the animation jump to the given tick.
   *
   * @param tick - tick to jump to
   */
  void setTick(int tick);

  /**
   * Requests that the animation be saved as a text view to the given file.
   *
   * @param fName - the name of the file
   */
  void saveAsText(String fName);

  /**
   * Requests that the animation be saved as a svg view to the given file.
   *
   * @param fName - the name of the file
   */
  void saveAsSVG(String fName);

  /**
   * Requests that a new animation be loaded and displayed.
   *
   * @param fName - the name of the file
   */
  void load(String fName);

}
