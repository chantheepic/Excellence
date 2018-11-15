package cs3500.excellence.view.Editor;

import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IROComponent;
import cs3500.excellence.model.components.Keyframe;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Interactive extends JPanel implements ActionListener, ItemListener, ListSelectionListener {
  EditorView editor;
  JTextField tick;
  JButton setTick;

  JPanel elementPanel;
  JRadioButton[] elementOptions;
  JPanel keyframePanel;
  JRadioButton[] keyOptions;
  JPanel parameters;

  JTextField paramPosnX;
  JTextField paramPosnY;
  JTextField paramSizeX;
  JTextField paramSizeY;
  JPanel paramColor;
  JButton colorChooser;

  JButton editKeyframe;
  JButton deleteKeyframe;

  JPanel container;
  private List<Keyframe> keyframes;
  private List<IROComponent> components;
  private IROComponent component;

  JScrollPane elementsPane;
  JScrollPane statesPane;

  public Interactive (EditorView c){
    this.editor = c;
    elementPanel = new JPanel();
    keyframePanel = new JPanel();
    parameters = new JPanel();
    container = new JPanel();

    tick = new JTextField();
    setTick = new JButton("Move to tick");
    setTick.setActionCommand("moveto");
    setTick.addActionListener(this);

    elementsPane = new JScrollPane(elementPanel);
    container.add(elementsPane);
    statesPane = new JScrollPane(keyframePanel);
    container.add(statesPane);

    paramSizeX = new JTextField();
    paramSizeY = new JTextField();
    paramPosnX = new JTextField();
    paramPosnY = new JTextField();
    paramColor = new JPanel();
    colorChooser = new JButton("Choose Color");

    parameters.setLayout(new GridLayout(5,3));
    parameters.add(new JLabel("Tick Position"));
    parameters.add(tick);
    parameters.add(setTick);
    parameters.add(new JLabel("Size (X,Y)"));
    parameters.add(paramSizeX);
    parameters.add(paramSizeY);
    parameters.add(new JLabel("Position (X,Y)"));
    parameters.add(paramPosnX);
    parameters.add(paramPosnY);
    parameters.add(new JLabel("Color"));
    parameters.add(paramColor);
    paramColor.setBackground(Color.white);
    paramColor.setOpaque(true);
    colorChooser.setActionCommand("color");
    colorChooser.addActionListener(this);
    parameters.add(colorChooser);

    editKeyframe = new JButton("Edit Keyframe");
    parameters.add(editKeyframe);
    deleteKeyframe = new JButton("Delete Keyframe");
    parameters.add(deleteKeyframe);

    container.add(parameters);
    container.setLayout(new GridLayout(1,3));
    elementPanel.setBorder(BorderFactory.createLineBorder(Color.black));
  }

  public JPanel returnPanel(){
    return container;
  }

  public void setComponents(List<IROComponent> components) {
    this.components = components;
    elementPanel.removeAll();
    elementPanel.add(new JLabel("Elements"));
    elementOptions = new JRadioButton[components.size()];
    elementPanel.setLayout(new GridLayout(components.size() + 1, 1));
    ButtonGroup group = new ButtonGroup();

    for(int i = 0; i < components.size(); i++){
      elementOptions[i] = new JRadioButton(components.get(i).getID());
      elementOptions[i].setActionCommand("comp " + i);
      elementOptions[i].addActionListener(this);
      group.add(elementOptions[i]);
      elementPanel.add(elementOptions[i]);
    }
    elementsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    elementsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
  }

  public void setKeyTimes(int index){
    this.component = components.get(index);
    this.keyframes = component.returnAllKeyframes();

    keyframePanel.removeAll();
    keyframePanel.add(new JLabel("Key Frames"));
    keyOptions = new JRadioButton[keyframes.size()];
    keyframePanel.setLayout(new GridLayout(keyframes.size() + 1, 1));
    ButtonGroup group2 = new ButtonGroup();

    for(int i = 0; i < keyframes.size(); i++){
      keyOptions[i] = new JRadioButton(String.valueOf(keyframes.get(i).getTick()));
      keyOptions[i].setActionCommand("key " + i);
      keyOptions[i].addActionListener(this);
      group2.add(keyOptions[i]);
      keyframePanel.add(keyOptions[i]);
    }
    statesPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    statesPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

  }


  public void setStateParameters(State state){
    paramSizeX.setText(String.valueOf(state.width()));
    paramSizeY.setText(String.valueOf(state.height()));
    paramPosnX.setText(String.valueOf(state.xPos()));
    paramPosnY.setText(String.valueOf(state.yPos()));
    paramColor.setBackground(new Color(state.red(), state.green(), state.blue()));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String[] split = e.getActionCommand().split("\\s+");
    int index = 0;
    if(split.length > 1){
      index = Integer.parseInt(split[1]);
    }
    switch(split[0]){
      case "comp":
        setKeyTimes(index);
        break;
      case "key":
        setStateParameters(keyframes.get(index).getState());
        tick.setText(String.valueOf(keyframes.get(index).getTick()));
        editor.drawFrame(keyframes.get(index).getTick());
//        editKeyframe.setText("Edit Keyframe");
        break;
      case "color":
        Color color = JColorChooser.showDialog(Interactive.this, "Color palette", paramColor.getBackground());
        paramColor.setBackground(color);
        break;
      case "moveto":
        try{
          int newTick = Integer.parseInt(tick.getText());
          if(newTick >= keyframes.get(0).getTick() && newTick <= keyframes.get(keyframes.size() - 1).getTick()){
            setStateParameters(component.getStateAtTick(newTick));
            editor.drawFrame(newTick);
            // Changes label on button to pull double duty
//            if(!keyframes.contains(newTick)){
//              editKeyframe.setText("Add Keyframe");
//            } else {
//              editKeyframe.setText("Edit Keyframe");
//            }
          }
        } catch (NumberFormatException format){

        }
        break;
      default:

    }
  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }
}
