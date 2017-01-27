/**
 * Copyright 2013. All rights reserved.
 */
package org.happysoft.zxsprite;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class ZXSpriteFrame extends JFrame {

  private JMenu fileMenu = new JMenu("File");

  private JMenuItem open = new JMenuItem("Open");
  private JMenuItem save = new JMenuItem("Save");
  
  private JMenu export = new JMenu("Export");
  private JMenuItem zeusExport = new JMenuItem("Zeus dg");
  
  private JMenu editMenu = new JMenu("Edit");

  private JMenuItem copy = new JMenuItem("Copy");
  private JMenuItem paste = new JMenuItem("Paste");
  private JMenuItem scrollLeft = new JMenuItem("Shift Left");
  private JMenuItem scrollRight = new JMenuItem("Shift Right");
  private JMenuItem reset = new JMenuItem("Reset");

  private JScrollPane scrollPane;

  private AnimationFrameTabPanel animationFrameTabPanel;

  private JComboBox<Integer> width = new JComboBox(new Integer[]{8, 16, 24, 32});
  private JComboBox<Integer> height = new JComboBox(new Integer[]{8, 16, 24, 32});
  private JComboBox<Integer> frames = new JComboBox(new Integer[]{1, 2, 4, 8});

  private ZeusDgDefaultFormat zeusDgFormat = new ZeusDgDefaultFormat();

  private SpriteModel buffer = null;

  public ZXSpriteFrame(String title) {
    super(title);
    jinit();
  }

  private void jinit() {
    enableMenus(false, false);
    frames.setSelectedItem(4);
    JPanel spriteOptionPanel = createOptionPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    createMenus(spriteOptionPanel);
    setPreferredSize(new Dimension(760, 500));
    scrollPane = new JScrollPane();
    add(scrollPane);
  }

  private void createMenus(JPanel spriteOptionPanel) {
    JMenuBar menuBar = new JMenuBar();
    JMenuItem newMenu = new JMenuItem("New");

    fileMenu.add(newMenu);
    fileMenu.add(open);
    fileMenu.add(save);
    fileMenu.add(export);
    
    export.add(zeusExport);

    editMenu.add(copy);
    editMenu.add(paste);
    editMenu.add(scrollLeft);
    editMenu.add(scrollRight);
    editMenu.add(reset);

    newMenu.addActionListener((ActionEvent e) -> {
      int option = JOptionPane.showOptionDialog(null, spriteOptionPanel, "Select sprite attributes",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, null, null);
      if (option == JOptionPane.CANCEL_OPTION) {
        // user hit cancel
      } else if (option == JOptionPane.OK_OPTION) {
        Integer w = (Integer) width.getSelectedItem();
        Integer h = (Integer) height.getSelectedItem();
        Integer f = (Integer) frames.getSelectedItem();
        enableMenus(false, false);
        buffer = null;
        addAnimationTabPanel(w, h, f);
      }
    });

    reset.addActionListener((ActionEvent e) -> {
      animationFrameTabPanel.getSelectedSpriteToggler().clear();
    });

    copy.addActionListener((ActionEvent e) -> {
      buffer = animationFrameTabPanel.getSelectedSpriteToggler().getSprite();
    });

    paste.addActionListener((ActionEvent e) -> {
      if (buffer != null) {
        animationFrameTabPanel.getSelectedSpriteToggler().setSprite(buffer);
        enableMenus(true, true);
      }
    });

    scrollRight.addActionListener((ActionEvent e) -> {
      animationFrameTabPanel.getSelectedSpriteToggler().shiftRight();
      buffer = null;
    });

    scrollLeft.addActionListener((ActionEvent e) -> {
      animationFrameTabPanel.getSelectedSpriteToggler().shiftLeft();
      buffer = null;
    });

    save.addActionListener((ActionEvent e) -> {
    });
    
    zeusExport.addActionListener((ActionEvent e) -> {
      SpriteToggler st = animationFrameTabPanel.getSelectedSpriteToggler();
      zeusDgFormat.export(st.getGridWidth(), st.getGridHeight(), st.getSprite().getSpriteData());
    });
    
    menuBar.add(fileMenu);
    menuBar.add(editMenu);

    setJMenuBar(menuBar);
  }

  private JPanel createOptionPanel() {
    JPanel p = new JPanel(new FlowLayout());
    JLabel widthLabel = new JLabel("Width");
    JLabel heightLabel = new JLabel("Height");
    JLabel framesLabel = new JLabel("Num Frames");
    p.add(widthLabel);
    p.add(width);
    p.add(heightLabel);
    p.add(height);
    p.add(framesLabel);
    p.add(frames);
    return p;
  }

  private void addAnimationTabPanel(int width, int height, int frames) {
    animationFrameTabPanel = new AnimationFrameTabPanel(this, width, height, frames);
    Dimension d = animationFrameTabPanel.getPreferredSize();
    scrollPane.setViewportView(animationFrameTabPanel);
    setSize(d);
    repaint();
  }

  public void enableMenus(boolean enabled, boolean editEnabled) {
    copy.setEnabled(enabled);
    export.setEnabled(enabled);
    editMenu.setEnabled(editEnabled);
  }

}
