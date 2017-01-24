/**
 * Copyright 2013. All rights reserved.
 */
package org.happysoft.zxsprite;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class ZXSpriteFrame extends JFrame {

  JMenu fileMenu = new JMenu("File");
  JMenuItem animation_8_8 = new JMenuItem("New Sprite 8x8");
  JMenuItem animation_8_16 = new JMenuItem("New Sprite 8x16");
  JMenuItem animation_16_16 = new JMenuItem("New Sprite 16x16");
  JMenuItem animation_8_8_p = new JMenuItem("New Sprite 8x8 - Pre-shifted(16x8)");
  JMenuItem animation_8_16_p = new JMenuItem("New Sprite 8x16 - Pre-shifted(16x16)");
  JMenuItem animation_16_16_p = new JMenuItem("New Sprite 16x16 - Pre-shifted(24x16)");
  
  JMenuItem open = new JMenuItem("Open");
  JMenuItem save = new JMenuItem("Save");

  JMenu editMenu = new JMenu("Edit");
  
  JMenuItem copy = new JMenuItem("Copy");
  JMenuItem paste = new JMenuItem("Paste");
  JMenuItem scrollLeft = new JMenuItem("Shift Left");
  JMenuItem scrollRight = new JMenuItem("Shift Right");
  JMenuItem reset = new JMenuItem("Reset");
  
  JScrollPane scrollPane;
  
  AnimationFrameTabPanel animationFrameTabPanel;
  
  private ZeusDgDefaultFormat zeusDgFormat = new ZeusDgDefaultFormat();
  
  private boolean[][] buffer = null;

  public ZXSpriteFrame(String title) {
    super(title);    
    jinit();
  }

  private void jinit() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JMenuBar menuBar = new JMenuBar();

    JMenu newMenu = new JMenu("New");
    newMenu.add(animation_8_8);
    newMenu.add(animation_8_16);
    newMenu.add(animation_16_16);
    
    fileMenu.add(newMenu);
    
    fileMenu.add(open);
    fileMenu.add(save);

    editMenu.add(copy);
    editMenu.add(paste);
    editMenu.add(scrollLeft);
    editMenu.add(scrollRight);
    editMenu.add(reset);
    
    animation_8_8.addActionListener((ActionEvent e) -> {
      addAnimationTabPanel(8, 8, 4);
    });
    
    animation_8_16.addActionListener((ActionEvent e) -> {
      addAnimationTabPanel(8, 16, 4);
    });
    
    animation_16_16.addActionListener((ActionEvent e) -> {
      addAnimationTabPanel(16, 16, 4);
    });   
        
    reset.addActionListener((ActionEvent e) -> {
      animationFrameTabPanel.getSelectedSpriteToggler().clear();
    });
    
    copy.addActionListener((ActionEvent e) -> {
      buffer = animationFrameTabPanel.getSelectedSpriteToggler().getFilledSquares();
    });
   
    paste.addActionListener((ActionEvent e) -> {
      if(buffer != null) {
        animationFrameTabPanel.getSelectedSpriteToggler().setFilledSquares(buffer);
      }
      buffer = null;
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
      SpriteToggler st = animationFrameTabPanel.getSelectedSpriteToggler();
      zeusDgFormat.export(st.getGridWidth(), st.getGridHeight(), st.getFilledSquares());
    });
    
    setPreferredSize(new Dimension(760, 500));
       
    menuBar.add(fileMenu);
    menuBar.add(editMenu);

    setJMenuBar(menuBar);
    
    scrollPane = new JScrollPane();
    add(scrollPane);
  }
  
  public void addAnimationTabPanel(int width, int height, int frames) {
    animationFrameTabPanel = new AnimationFrameTabPanel(this, width, height, frames);
    
    Dimension d = animationFrameTabPanel.getPreferredSize();
    scrollPane.setViewportView(animationFrameTabPanel);
    setSize(d);
    repaint();
  }
  
  public void enableMenus(boolean enabled) {
    copy.setEnabled(enabled);
    scrollLeft.setEnabled(enabled);
    scrollRight.setEnabled(enabled);
  }

}
