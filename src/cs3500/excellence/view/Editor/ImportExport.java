package cs3500.excellence.view.Editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ImportExport extends JPanel implements ActionListener, ItemListener, ListSelectionListener {
  EditorView editor;
  JPanel container;
  JPanel panelLeft;
  JPanel panelRight;
  JRadioButton[] saveOptions;
  JButton save;
  JButton open;
  JTextField saveName;
  JTextField openName;

  public ImportExport(EditorView c) {
    this.editor = c;
    container = new JPanel();
    container.setLayout(new GridLayout(1,2));
    panelLeft = new JPanel();
    panelLeft.setLayout(new GridLayout(4,2));
    panelRight = new JPanel();
    panelRight.setLayout(new GridLayout(3,1));

    // Buttons
    // Left Panel
    JButton saveDirectory = new JButton("Save Dir");
    saveDirectory.setActionCommand("save directory");
    saveDirectory.addActionListener(this);
    save = new JButton("Save");
    save.setActionCommand("Save");
    save.addActionListener(this);
    saveName = new JTextField("Save File Name");
    saveName.setForeground(new Color(116, 116, 116));
    panelLeft.add(new JLabel("ImportExport"));
    panelLeft.add(saveName);
    panelLeft.add(saveDirectory);
    panelLeft.add(save);

    JButton importDirectory = new JButton("Import Dir");
    importDirectory.setActionCommand("import directory");
    importDirectory.addActionListener(this);
    open = new JButton("Import");
    open.setActionCommand("Import");
    open.addActionListener(this);
    panelLeft.add(new JLabel("Import File"));
    panelLeft.add(openName);
    openName.setForeground(new Color(116, 116, 116));
    panelLeft.add(saveDirectory);
    panelLeft.add(open);

    // Right Panel
    saveOptions = new JRadioButton[3];
    ButtonGroup group = new ButtonGroup();
    saveOptions[0] = new JRadioButton("Textual");
    saveOptions[1] = new JRadioButton("Visual");
    saveOptions[2] = new JRadioButton("SVG");
    for(int i = 0; i < saveOptions.length; i++){
      saveOptions[i].setActionCommand("SaveOption" + i);
      saveOptions[i].addActionListener(this);
      group.add(saveOptions[i]);
      panelRight.add(saveOptions[i]);
    }

    // Add Panels
    container.add(panelLeft);
    container.add(panelRight);

    // Set default frame behavior
    panelLeft.setPreferredSize(new Dimension(200,100));
  }

  // Actions
  @Override
  public void actionPerformed(ActionEvent e) {
    switch(e.getActionCommand()) {
      case "SaveOption1":
        System.out.println("Textual saved");
        break;
      case "SaveOption2":
        System.out.println("Visual saved");
        break;
      case "SaveOption3":
        System.out.println("SVG saved");
        break;
      case "Save":
        System.out.println(saveName.getText() + " File Saved");
        break;
      case "save directory": {
        final JFileChooser chooser = new JFileChooser(".");
        int val = chooser.showSaveDialog(ImportExport.this);
        if (val == JFileChooser.APPROVE_OPTION) {
          File f = chooser.getSelectedFile();
          saveName.setText(f.getAbsolutePath());
        }
      }
      case "import directory": {
        final JFileChooser chooser = new JFileChooser(".");
        int val = chooser.showSaveDialog(ImportExport.this);
        if (val == JFileChooser.APPROVE_OPTION) {
          File f = chooser.getSelectedFile();
          openName.setText(f.getAbsolutePath());
        }
      }
      default:
    }

  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }

  public JPanel returnPanel(){
    return container;
  }

}