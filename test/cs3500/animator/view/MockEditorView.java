package cs3500.animator.view;

import cs3500.animator.controller.Features;
import cs3500.animator.model.Boundary;
import cs3500.animator.model.components.IROComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A mock version of EditorView for testing purposes only.
 */
public class MockEditorView implements IView, ActionListener, ChangeListener {

  /**
   * Constructor for mock editor.
   */
  public MockEditorView(int keyframeTick, int speed, String shapeName, String shapeType,
                        int shapeLayer, int shapeX, int shapeY, int shapeW, int shapeH, int shapeR,
                        int shapeG, int shapeB, String saveName) {
    this.keyframeTick = keyframeTick;
    this.speed = speed;
    this.shapeName = shapeName;
    this.shapeType = shapeType;
    this.shapeLayer = shapeLayer;
    this.shapeX = shapeX;
    this.shapeY = shapeY;
    this.shapeW = shapeW;
    this.shapeH = shapeH;
    this.shapeR = shapeR;
    this.shapeG = shapeG;
    this.shapeB = shapeB;
    this.shapeRot = 0;
    this.saveName = saveName;
  }

  private Features listener;
  private int keyframeTick;
  private int speed;
  private String shapeName;
  private String shapeType;
  private int shapeLayer;
  private int shapeX;
  private int shapeY;
  private int shapeW;
  private int shapeH;
  private int shapeR;
  private int shapeG;
  private int shapeB;
  private int shapeRot;
  private String saveName;

  /**
   * Mock version of actions.
   * @param e given action event.
   */
  public void actionPerformed(ActionEvent e) {

    switch (e.getActionCommand()) {
      case "togglePlay": //from start/stop button
        listener.togglePlay();
        break;
      case "restart": //from restart button
        listener.restart();
        break;
      case "keyframe go": //from shape-frame selector
        listener.setTick(keyframeTick);
        break;
      case "tickGo": //from tick selector
        listener.setTick(keyframeTick);
        break;
      case "create keyframe":
        listener.createFrame(shapeName, shapeX, shapeY, shapeW, shapeH, shapeR, shapeG, shapeB, shapeRot);
        break;
      case "create shape":
        listener.addShape(shapeName, shapeType,shapeLayer);
        break;
      case "delete shape":
        listener.deleteShape(shapeName);
        break;
      case "delete keyframe":
        listener.deleteFrame(shapeName);
        break;
      case "loop":
        listener.toggleLoop();
        break;
      case "saveText":
        listener.saveAsText(saveName);
        break;
      case "saveSVG":
        listener.saveAsSVG(saveName);
        break;
    }
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    listener.setSpeed(this.speed);
  }


  @Override
  public void setComponents(List<IROComponent> components, Boundary boundary, int speed) {
    //Do Nothing
  }

  @Override
  public void setOutput(Appendable app) {
    //Do Nothing
  }

  @Override
  public void setFeatureListener(Features listener) {
    this.listener = listener;
  }

  @Override
  public void displayError(String msg) {
    //Do Nothing
  }

  @Override
  public void tick(int currentTick) {
    //Do Nothing
  }
}
