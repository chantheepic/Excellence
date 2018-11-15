package cs3500.excellence.view.Editor;

import cs3500.excellence.model.State;
import cs3500.excellence.model.components.IROComponent;
import java.awt.Color;
import java.awt.FlowLayout;
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

  JPanel elements;
  JRadioButton[] elementOptions;
  JPanel keyframes;
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
  private List<IROComponent> components;
  private IROComponent component;
  private List<State> keyStates;
  private List<Integer> keyTimes;

  JScrollPane elementsPane;
  JScrollPane statesPane;

  public Interactive (EditorView c){
    this.editor = c;
    elements  = new JPanel();
    keyframes = new JPanel();
    parameters = new JPanel();
    container = new JPanel();

    tick = new JTextField();
    setTick = new JButton("Move to tick");
    setTick.setActionCommand("moveto");
    setTick.addActionListener(this);

    elementsPane = new JScrollPane(elements);
    container.add(elementsPane);
    statesPane = new JScrollPane(keyframes);
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
    elements.setBorder(BorderFactory.createLineBorder(Color.black));
  }

  public JPanel returnPanel(){
    return container;
  }

  public void setComponents(List<IROComponent> components) {
    this.components = components;
    elements.removeAll();
    elements.add(new JLabel("Elements"));
    elementOptions = new JRadioButton[components.size()];
    elements.setLayout(new GridLayout(components.size() + 1, 1));
    ButtonGroup group = new ButtonGroup();

    for(int i = 0; i < components.size(); i++){
      elementOptions[i] = new JRadioButton(components.get(i).getID());
      elementOptions[i].setActionCommand("comp " + i);
      elementOptions[i].addActionListener(this);
      group.add(elementOptions[i]);
      elements.add(elementOptions[i]);
    }
    elementsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    elementsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
  }

  public void setKeyTimes(int index){
    this.component = components.get(index);
    this.keyStates = components.get(index).returnAllKeyStates();
    this.keyTimes = components.get(index).returnAllKeyTimes();
    keyframes.removeAll();
    keyframes.add(new JLabel("Key Frames"));
    keyOptions = new JRadioButton[keyTimes.size()];
    keyframes.setLayout(new GridLayout(keyTimes.size() + 1, 1));
    ButtonGroup group2 = new ButtonGroup();

    for(int i = 0; i < keyTimes.size(); i++){
      keyOptions[i] = new JRadioButton(String.valueOf(keyTimes.get(i)));
      keyOptions[i].setActionCommand("key " + i);
      keyOptions[i].addActionListener(this);
      group2.add(keyOptions[i]);
      keyframes.add(keyOptions[i]);
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
        setStateParameters(keyStates.get(index));
        tick.setText(String.valueOf(keyTimes.get(index)));
        editor.drawFrame(keyTimes.get(index));
        break;
      case "color":
        Color color = JColorChooser.showDialog(Interactive.this, "Color palette", paramColor.getBackground());
        paramColor.setBackground(color);
        break;
      case "moveto":
        try{
          int newTick = Integer.parseInt(tick.getText());
          if(newTick >= keyTimes.get(0) && newTick <= keyTimes.get(keyTimes.size() - 1)){
            setStateParameters(component.getStateAtTick(newTick));
            editor.drawFrame(newTick);
            // Changes label on button to pull double duty
            if(!keyTimes.contains(newTick)){
              editKeyframe.setText("Add Keyframe");
            } else {
              editKeyframe.setText("Edit Keyframe");
            }
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