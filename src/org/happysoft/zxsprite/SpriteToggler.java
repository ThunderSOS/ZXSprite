/**
 * Copyright 2017. All rights reserved.
 */
package org.happysoft.zxsprite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class SpriteToggler extends JPanel {

  // offset from panel edges
  private final int xInset = 20;
  private final int yInset = 20;
  
  private SpriteModel sprite;
  
  private final int cellSize = 20;
  
  private final JComponent parent;

  public SpriteToggler(JComponent parent, int width, int height) {
    this.parent = parent;
    sprite = new SpriteModel(width, height);
    jinit();
  }
  
  public int getGridWidth() {
    return sprite.getWidth();
  }
  
  public int getGridHeight() {
    return sprite.getHeight();
  }
  
  private void jinit() {
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
    addMouseListener(new MouseAdapter() {      
      @Override
      public void mouseClicked(MouseEvent e) {
        int mx = e.getX() - xInset;
        int my = e.getY() - yInset;

        int gridX = mx / cellSize;
        int gridY = my / cellSize;
        if (gridX < sprite.getWidth() && gridY < sprite.getHeight()) {
          sprite.toggleBit(gridX, gridY);
        }
        repaint();
        parent.dispatchEvent(e);
      }
    });
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(760, 500);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    sprite.paint(g, cellSize, xInset, yInset);
  }
  
  public boolean isDirty() {
    return sprite.isDirty();
  }
  
  public void clear() {
    sprite.clear();
    repaint();
  }
  
  public SpriteModel getSprite() {
    return sprite;
  }
  
  public void setSprite(SpriteModel sprite) {
    this.sprite = new SpriteModel(sprite);
    repaint();
  }
  
  public void shiftRight() {
    sprite.shiftRight();
    repaint();
  }
  
  public void shiftLeft() {
    sprite.shiftLeft();
    repaint();
  }
  
  public void reverse() {
    sprite.reverse();
    repaint();
  }

}
