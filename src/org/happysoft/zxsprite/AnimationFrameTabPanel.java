/**
 * Copyright 2017. All rights reserved.
 */
package org.happysoft.zxsprite;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class AnimationFrameTabPanel extends JTabbedPane implements MouseMotionListener {

  private final ZXSpriteFrame parent;
  private final int numFrames;
  private final ArrayList<SpriteToggler> togglers = new ArrayList<>();

  public AnimationFrameTabPanel(ZXSpriteFrame parent, int width, int height, int numFrames) {
    this.parent = parent;
    this.numFrames = numFrames;
    int maxFrames = numFrames;
    while (maxFrames > 0) {
      SpriteToggler spriteToggler = new SpriteToggler(this, width, height);
      this.addTab("Frame " + (numFrames - maxFrames), spriteToggler);
      togglers.add(spriteToggler);
      maxFrames--;
    }
    SpriteToggler scratchBoard = new SpriteToggler(this, width, height);
    addTab("Clipboard", scratchBoard);
    togglers.add(scratchBoard);
    jinit();
  }

  public AnimationFrameTabPanel(ZXSpriteFrame parent, ArrayList<SpriteModel> models) {
    this.parent = parent;
    int width = models.get(0).getWidth();
    int height = models.get(0).getHeight();
    this.numFrames = models.size();
    int maxFrames = numFrames;
    while (maxFrames > 0) {
      SpriteToggler spriteToggler = new SpriteToggler(this, width, height);
      spriteToggler.setSprite(models.get(numFrames - maxFrames));
      this.addTab("Frame " + (numFrames - maxFrames), spriteToggler);
      togglers.add(spriteToggler);
      maxFrames--;
    }
    SpriteToggler scratchBoard = new SpriteToggler(this, width, height);
    addTab("Clipboard", scratchBoard);
    togglers.add(scratchBoard);
    jinit();
  }

  private void jinit() {
    addMouseMotionListener(this);
    
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

  public SpriteModel getSprite() {
    return getSelectedSpriteToggler().getSprite();
  }

  public void setSprite(SpriteModel sprite) {
    getSelectedSpriteToggler().setSprite(sprite);
  }
  
  public ArrayList<SpriteModel> getSprites() {
    ArrayList<SpriteModel> models = new ArrayList<>();
    for(int i = 0; i < togglers.size()-1; i++) {
      models.add(togglers.get(i).getSprite());
    }
    return models;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    SpriteToggler s = getSelectedSpriteToggler();
    boolean dirty = s.isDirty();
    parent.enableMenus(dirty, true);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }
}
