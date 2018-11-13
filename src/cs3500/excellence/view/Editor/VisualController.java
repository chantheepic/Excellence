package cs3500.excellence.view.Editor;

import cs3500.excellence.model.IModel;
import cs3500.excellence.model.Model;
import cs3500.excellence.model.components.IROComponent;
import cs3500.excellence.util.AnimationReader;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class VisualController {
  IModel model;
  EditorView editor;
  String pathName;
  int speed;

  public VisualController(IModel model, EditorView editor, String  pathname, int speed){
    this.pathName = pathname;
    this.speed = speed;
    this.model = model;
    this.editor = editor;
  }

  public void run(){
    editor.updateParameters(pathName, new Dimension(model.getBoundary().getWidth(), model.getBoundary().getHeight()), 13);
    editor.setComponents(model.getAllComponents(), model.getBoundary(), 13);
  }

  public static void main(String[] args) throws InterruptedException {
    IModel model;
    try {
      String pathName = "resources/big-bang-big-crunch.txt";
      model = AnimationReader.parseFile(new FileReader(new File(pathName)), Model.builder());
      System.out.println("model Imported");

      EditorView editor = new EditorView();
      VisualController con = new VisualController(model,editor, pathName, 13);
      con.run();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
