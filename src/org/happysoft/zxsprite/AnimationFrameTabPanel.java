/**
 * Copyright 2017. All rights reserved.
 */
package org.happysoft.zxsprite;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class AnimationFrameTabPanel extends JTabbedPane {

  private final ZXSpriteFrame parent;
  private final int numFrames;
  private final ArrayList<SpriteToggler> togglers = new ArrayList<>();

  public AnimationFrameTabPanel(ZXSpriteFrame parent, int width, int height, int numFrames) {
    this.parent = parent;
    this.numFrames = numFrames;
    jinit(width, height);
  }

  private void jinit(int width, int height) {
    int maxFrames = numFrames;
    while (maxFrames > 0) {
      SpriteToggler spriteToggler = new SpriteToggler(this, width, height);
      this.addTab("Frame " + (numFrames - maxFrames), spriteToggler);
      togglers.add(spriteToggler);
      maxFrames--;
    }
    
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        SpriteToggler s = getSelectedSpriteToggler();
        boolean dirty = s.isDirty();
        parent.enableMenus(dirty, true);
      }
    });
    
    this.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {        
        SpriteToggler s = getSelectedSpriteToggler();
        boolean dirty = s.isDirty();
        parent.enableMenus(dirty, true);
      }
    });
  }

  public SpriteToggler getSelectedSpriteToggler() {
    int tgIndex = getSelectedIndex();
    return togglers.get(tgIndex);
  }
  
  public boolean[][] getGrid() {
    return getSelectedSpriteToggler().getFilledSquares();
  }
  
  public void setGrid(boolean[][] filledSquares) {
    getSelectedSpriteToggler().setFilledSquares(filledSquares);
  }
}
