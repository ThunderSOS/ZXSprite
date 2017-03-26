/**
 * Copyright 2013. All rights reserved.
 */
package org.happysoft.zxsprite;

import java.awt.Graphics;
import java.io.Serializable;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class SpriteModel implements Serializable {
  
  private static final long serialVersionUID = -1008625353210632990L;

  private boolean[][] sprite;

  private int width;

  private int height;

  public SpriteModel(int width, int height) {
    this.width = width;
    this.height = height;
    sprite = new boolean[width][height];
  }

  public SpriteModel(SpriteModel sprite) {
    this.width = sprite.width;
    this.height = sprite.height;
    this.sprite = new boolean[width][height];

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        this.sprite[i][j] = sprite.isSet(i, j);
      }
    }
  }

  public boolean isSet(int x, int y) {
    return sprite[x][y];
  }

  public void toggleBit(int x, int y) {
    sprite[x][y] = !sprite[x][y];
  }
  
  public void setBit(int x, int y) {
    sprite[x][y] = true;
  }
  
  public void resetBit(int x, int y) {
    sprite[x][y] = false;
  }

  public boolean[][] getSpriteData() {
    return sprite;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public boolean isDirty() {
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (sprite[i][j]) {
          return true;
        }
      }
    }
    return false;
  }

  public void clear() {
    sprite = new boolean[width][height];
  }
  
  public void shiftLeft() {
    for (int j = 0; j < height; j++) {
      for (int i = 1; i < width; i++) {
        sprite[i-1][j] = sprite[i][j];
      }
      sprite[width-1][j] = false;
    }
  }
  
  public void shiftRight() {
    for (int j = 0; j < height; j++) {
      for (int i = width-1; i > 0; i--) {
        sprite[i][j] = sprite[i-1][j];
      }
      sprite[0][j] = false;
    }
  }
  
  public void shiftUp() {
    for (int i = 0; i < width; i++) {
      for (int j = 1; j < height; j++) {
        sprite[i][j-1] = sprite[i][j];
      }
      sprite[i][height-1] = false;
    }
  }
  
  public void shiftDown() {
    for (int i = 0; i < width; i++) {
      for (int j = height-2; j >= 0; j--) {
        sprite[i][j+1] = sprite[i][j];
      }
      sprite[i][0] = false;
    }
  }
  
  public void reverse() {
    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width/2; i++) {
        boolean temp = sprite[i][j];
        sprite[i][j] = sprite[width-i-1][j];
        sprite[width-i-1][j] = temp;
      }      
    }
  }
  
  public void paint(Graphics g, int cellSize, int xInset, int yInset) {
    
    int totalGridWidth = cellSize*width; 
    int totalGridHeight = cellSize*height;

    // grid bounding rectangle
    g.drawRect(xInset, yInset, totalGridWidth, totalGridHeight);

    // vertical grid lines 
    for (int i = 0; i < width; i++) {
      g.drawLine(xInset + (cellSize * i), yInset, xInset + (cellSize * i), yInset + totalGridHeight);
    }
    // horizontal lines
    for (int i = 0; i < height ;i++) {
      g.drawLine(xInset, yInset + (cellSize * i), xInset + totalGridWidth, yInset + (cellSize * i));
    }
    
    // filled squares
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (sprite[i][j]) {
          g.fillRect(xInset + (cellSize * i), yInset + (cellSize * j), cellSize, cellSize);
        }
      }
    }
    
    // preview image
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (sprite[i][j]) {
          g.fillRect(xInset + totalGridWidth + cellSize + i*2, yInset + j*2, 2, 2);
        }
      }
    }
  }

}
