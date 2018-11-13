package cs3500.excellence.view.Editor;

import cs3500.excellence.model.components.IROComponent;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Interactive extends JPanel implements ActionListener, ItemListener, ListSelectionListener {
  EditorView editor;
  JPanel elements;
  JRadioButton[] elementOptions;
  JPanel keyframes;
  JPanel parameters;
  JPanel container;

  //HashMap<String, > components;

  public static void main(String[] args) throws InterruptedException {
    new Interactive(new EditorView());
  }

  public Interactive (EditorView c){
    this.editor = c;
    elements  = new JPanel();
    keyframes = new JPanel();
    parameters = new JPanel();
    container = new JPanel();

    JScrollPane elementsPane = new JScrollPane(elements);
    elementsPane.setPreferredSize(new Dimension(200,300));
    elementsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    elementsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    container.add(elementsPane);


    container.add(keyframes);
    container.add(parameters);
    container.setLayout(new GridLayout(1,3));
    elements.setBorder(BorderFactory.createLineBorder(Color.black));
  }

  public JPanel returnPanel(){
    return container;
  }

  public void setComponents(List<IROComponent> components) {
    elementOptions = new JRadioButton[components.size()];
    elements.setLayout(new GridLayout(components.size(), 1));
    ButtonGroup group = new ButtonGroup();

    for(int i = 0; i < components.size(); i++){
      elementOptions[i] = new JRadioButton(components.get(i).getID());
      elementOptions[i].setActionCommand("SaveOption");
      elementOptions[i].addActionListener(this);
      group.add(elementOptions[i]);
      elements.add(elementOptions[i]);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }
}
