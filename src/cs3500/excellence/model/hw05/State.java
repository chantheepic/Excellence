package cs3500.excellence.model.hw05;

public final class State {
  private double width;
  private double height;
  private double posX;
  private double posY;
  private int red;
  private int green;
  private int blue ;

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
