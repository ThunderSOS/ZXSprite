/**
 * Copyright 2013. All rights reserved. 
 */
package org.happysoft.zxsprite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class FileFormat {
  
  private AnimationFrameTabPanel tabPanel;
  
  public FileFormat(AnimationFrameTabPanel tabPanel) { 
    this.tabPanel = tabPanel;
  }
  
  public void save(File file, ArrayList<SpriteModel> models) throws IOException  {
    ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(file));
    objectOut.writeObject(models);
    objectOut.flush();
    objectOut.close();
  }
  
  public ArrayList<SpriteModel> load(File file) throws IOException, ClassNotFoundException {    
    ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
    ArrayList<SpriteModel> models = (ArrayList<SpriteModel>) oin.readObject();
    oin.close();
    return models;
  }
  
}
