/**
 * Copyright 2017. All rights reserved.
 */
package org.happysoft.zxsprite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class SpriteToggler extends JPanel implements MouseMotionListener {

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
    addMouseMotionListener(this);
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

  public void shiftUp() {
    sprite.shiftUp();
    repaint();
  }

  public void shiftDown() {
    sprite.shiftDown();
    repaint();
  }

  public void reverse() {
    sprite.reverse();
    repaint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    int mx = e.getX() - xInset;
    int my = e.getY() - yInset;

    int gridX = mx / cellSize;
    int gridY = my / cellSize;

    if (gridX < sprite.getWidth() && gridY < sprite.getHeight() && gridX >= 0 && gridY >= 0) {
      if (SwingUtilities.isLeftMouseButton(e)) {
        sprite.setBit(gridX, gridY);
      } else {
        sprite.resetBit(gridX, gridY);
      }
    }
    repaint();
    parent.dispatchEvent(e);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }

}
