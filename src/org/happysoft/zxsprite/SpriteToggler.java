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
  
  private int gridSquaresTall = 8;
  private int gridSquaresWide = 16;

  private boolean[][] filledSquares = null; 
  
  private final int cellSize = 20;
  
  private final JComponent parent;

  public SpriteToggler(JComponent parent, int width, int height) {
    this.parent = parent;
    
    this.gridSquaresWide = width;
    this.gridSquaresTall = height;

    filledSquares = new boolean[gridSquaresWide][gridSquaresTall];
    jinit();
  }
  
  public int getGridWidth() {
    return gridSquaresWide;
  }
  
  public int getGridHeight() {
    return gridSquaresTall;
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
        if (gridX < gridSquaresWide && gridY < gridSquaresTall) {
          filledSquares[gridX][gridY] = !filledSquares[gridX][gridY];
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
    int totalGridWidth = cellSize*gridSquaresWide; 
    int totalGridHeight = cellSize*gridSquaresTall;

    // grid bounding rectangle
    g.drawRect(xInset, yInset, totalGridWidth, totalGridHeight);

    // vertical grid lines 
    for (int i = 0; i < gridSquaresWide; i++) {
      g.drawLine(xInset + (cellSize * i), yInset, xInset + (cellSize * i), yInset + totalGridHeight);
    }
    // horizontal lines
    for (int i = 0; i < gridSquaresTall ;i++) {
      g.drawLine(xInset, yInset + (cellSize * i), xInset + totalGridWidth, yInset + (cellSize * i));
    }
    
    // filled squares
    for (int i = 0; i < gridSquaresWide; i++) {
      for (int j = 0; j < gridSquaresTall; j++) {
        if (filledSquares[i][j]) {
          g.fillRect(xInset + (cellSize * i), yInset + (cellSize * j), cellSize, cellSize);
        }
      }
    }
    
    // preview image
    for (int i = 0; i < gridSquaresWide; i++) {
      for (int j = 0; j < gridSquaresTall; j++) {
        if (filledSquares[i][j]) {
          g.fillRect(xInset + totalGridWidth + cellSize + i*2, yInset + j*2, 2, 2);
        }
      }
    }
  }
  
  public boolean isDirty() {
    for (int i = 0; i < gridSquaresWide; i++) {
      for (int j = 0; j < gridSquaresTall; j++) {
        if (filledSquares[i][j]) {
          return true;
        }
      }
    }
    return false;
  }
  
  public void clear() {
    for (int i = 0; i < gridSquaresWide; i++) {
      for (int j = 0; j < gridSquaresTall; j++) {
        filledSquares[i][j] = false;
      }
    }
    repaint();
  }
  
  public boolean[][] getFilledSquares() {
    boolean[][] buffer = new boolean[gridSquaresWide][gridSquaresTall];
    for (int j = 0; j < gridSquaresTall; j++) {
      for (int i = 0; i < gridSquaresWide; i++) {
        buffer[i][j] = filledSquares[i][j];
      }
    }
    return buffer;
  }
  
  public void setFilledSquares(boolean[][] filledSquares) {
    this.filledSquares = filledSquares;
    repaint();
  }
  
  public void shiftRight() {
    for (int j = 0; j < gridSquaresTall; j++) {
      for (int i = gridSquaresWide-1; i > 0; i--) {
        filledSquares[i][j] = filledSquares[i-1][j];
      }
      filledSquares[0][j] = false;
    }
    repaint();
  }
  
  public void shiftLeft() {
    for (int j = 0; j < gridSquaresTall; j++) {
      for (int i = 1; i < gridSquaresWide; i++) {
        filledSquares[i-1][j] = filledSquares[i][j];
      }
      filledSquares[gridSquaresWide-1][j] = false;
    }
    repaint();
  }

}
