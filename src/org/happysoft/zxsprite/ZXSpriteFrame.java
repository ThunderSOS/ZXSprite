/**
 * Copyright 2013. All rights reserved.
 */
package org.happysoft.zxsprite;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class ZXSpriteFrame extends JFrame {

  JMenu fileMenu = new JMenu("File");
  JMenuItem animation_16_8 = new JMenuItem("New Sprite 8x8");
  JMenuItem animation_16_16 = new JMenuItem("New Sprite 8x16");
  JMenuItem animation_24_16 = new JMenuItem("New Sprite 16x16");
  JMenuItem animation_16_8_p = new JMenuItem("New Sprite 8x8 - Pre-shifted(16x8)");
  JMenuItem animation_16_16_p = new JMenuItem("New Sprite 8x16 - Pre-shifted(16x16)");
  JMenuItem animation_24_16_p = new JMenuItem("New Sprite 16x16 - Pre-shifted(24x16)");
  
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
  
  private boolean[][] buffer = null;

  public ZXSpriteFrame(String title) {
    super(title);    
    jinit();
  }

  private void jinit() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JMenuBar menuBar = new JMenuBar();

    JMenu newMenu = new JMenu("New");
    newMenu.add(animation_16_8);
    newMenu.add(animation_16_16);
    newMenu.add(animation_24_16);
    
    fileMenu.add(newMenu);
    
    fileMenu.add(open);
    fileMenu.add(save);

    editMenu.add(copy);
    editMenu.add(paste);
    editMenu.add(scrollLeft);
    editMenu.add(scrollRight);
    editMenu.add(reset);
    
    animation_16_8.addActionListener((ActionEvent e) -> {
      addAnimationTabPanel(16, 8, 4);
    });
    
    animation_16_16.addActionListener((ActionEvent e) -> {
      addAnimationTabPanel(16, 16, 4);
    });
    
    animation_24_16.addActionListener((ActionEvent e) -> {
      addAnimationTabPanel(24, 16, 4);
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
