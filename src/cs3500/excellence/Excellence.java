package cs3500.excellence;


import cs3500.excellence.model.components.IComponent;
import cs3500.excellence.view.SVGView;
import cs3500.excellence.view.TextualView;
import cs3500.excellence.view.VisualAnimationView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.excellence.model.IModel;
import cs3500.excellence.model.Model;
import cs3500.excellence.util.AnimationReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Excellence {

  public static void main(String[] args) {
    //VisualAnimationView view;
    TextualView view2;
    SVGView view3;
    IModel model;

      try {
        model = AnimationReader.parseFile(new FileReader(new File("resources/big-bang-big-crunch.txt")), Model.builder());
        view2 = new TextualView(System.out);
        view2.setComponents(model.getAllComponents(), model.getBoundary());
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }

      //System.out.println(Arrays.toString(model.getBoundary()));
      //view = new VisualAnimationView();
      //view.setComponents(model.getAllComponents(), model.getBoundary());


//      PrintWriter out = new PrintWriter("sample.txt");
//      view3 = new SVGView(out);
//      view3.setComponents(model.getAllComponents(), model.getBoundary());
//      out.close();
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    }

  }

}
