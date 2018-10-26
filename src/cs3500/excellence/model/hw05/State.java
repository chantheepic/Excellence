package cs3500.excellence.model.hw05;

public final class State {
  private double width = 0;
  private double height = 0;
  private double posX = 0;
  private double posY = 0;
  private int red = 0;
  private int green = 0;
  private int blue = 0;

  public State(double w, double h, double x, double y, int r, int g, int b){
    if(r > 255 || g > 255|| b > 255){
      throw new IllegalArgumentException("Color values cannot exceed 255");
    }
    this.width = w;
    this.height = h;
    this.posX = x;
    this.posY = y;
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  // getters
  public double w(){
    return width;
  }

  public double h(){
    return height;
  }

  public double x(){
    return posX;
  }

  public double y(){
    return posY;
  }

  public int red(){
    return red;
  }

  public int green(){
    return green;
  }

  public int blue(){
    return blue;
  }

}
