package cs3500.excellence.view.Editor;

import sun.jvm.hotspot.types.JIntField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.excellence.view.IEditListener;

public class EditPanel extends JPanel implements ActionListener {

  private JLabel colorChooserDisplay;

  private JTextField shapeNameField;
  private JTextField shapeTypeField;

  private JTextField shapeXField;
  private JTextField shapeYField;

  private JTextField shapeWidthField;
  private JTextField shapeHeightField;

  private ActionListener listener;



  public EditPanel(ActionListener listener) {

    this.listener = listener;


    setLayout(new GridLayout(7,2));



    //Fields
    shapeNameField = new JTextField();
    shapeNameField.setBorder(BorderFactory.createTitledBorder("Shape Name:"));
    add(shapeNameField);

    shapeTypeField = new JTextField();
    shapeTypeField.setBorder(BorderFactory.createTitledBorder("Shape Type:"));
    add(shapeTypeField);

    shapeXField = new JTextField();
    shapeXField.setBorder(BorderFactory.createTitledBorder("X Position:"));
    add(shapeXField);

    shapeYField = new JTextField();
    shapeYField.setBorder(BorderFactory.createTitledBorder("Y Position:"));
    add(shapeYField);

    shapeWidthField = new JTextField();
    shapeWidthField.setBorder(BorderFactory.createTitledBorder("Width:"));
    add(shapeWidthField);

    shapeHeightField = new JTextField();
    shapeHeightField.setBorder(BorderFactory.createTitledBorder("Height:"));
    add(shapeHeightField);


    //color chooser
    JPanel colorChooserPanel = new JPanel();
    colorChooserPanel.setLayout(new FlowLayout());
    add(colorChooserPanel);
    JButton colorChooserButton = new JButton("Choose a color");
    colorChooserButton.setActionCommand("Color chooser");
    colorChooserButton.addActionListener(this);
    colorChooserPanel.add(colorChooserButton);
    colorChooserDisplay = new JLabel("      ");
    colorChooserDisplay.setOpaque(true); //so that background color shows up
    colorChooserDisplay.setBackground(Color.WHITE);
    colorChooserPanel.add(colorChooserDisplay);


    JButton insertKeyframe = new JButton("Insert Keyframe");
    insertKeyframe.setActionCommand("insert keyframe");
    insertKeyframe.addActionListener(listener);
    add(insertKeyframe);

    JButton createShape = new JButton("Create Shape");
    createShape.setActionCommand("create shape");
    createShape.addActionListener(listener);
    add(createShape);

    JButton editKeyframe = new JButton("Edit Keyframe");
    editKeyframe.setActionCommand("edit keyframe");
    editKeyframe.addActionListener(listener);
    add(editKeyframe);



  }


  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }




  @Override
    public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Color chooser":
        Color col = JColorChooser.showDialog(this, "Choose a color", colorChooserDisplay.getBackground());
        colorChooserDisplay.setBackground(col);
        break;


    }


  }

  public String getShapeName() {
    return shapeNameField.getText();
  }
  public String getShapeType() {
    return shapeTypeField.getText();
  }
  public String getXPos() {
    return shapeXField.getText();
  }
  public String getYPos() {
    return shapeYField.getText();
  }
  public String getWidthVal() {
     return shapeWidthField.getText();
  }
  public String getHeightVal() {
    return (shapeHeightField.getText());
  }
  public Color getColor() {
    return colorChooserDisplay.getBackground();
  }


}
