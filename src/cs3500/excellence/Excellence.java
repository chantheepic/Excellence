package cs3500.excellence;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.excellence.model.IModel;
import cs3500.excellence.model.Model;
import cs3500.excellence.util.AnimationReader;

public class Excellence {


  public static void main(String[] args) {


    IModel model;
    try {
      model = AnimationReader.parseFile(new FileReader(new File("resources/big-bang-big-crunch.txt")), Model.builder());
      System.out.println(model.getOverview());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

}
