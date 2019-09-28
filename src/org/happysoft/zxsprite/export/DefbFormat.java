/**
 * Copyright 2013. All rights reserved.
 */
package org.happysoft.zxsprite.export;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.happysoft.zxsprite.SpriteModel;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class DefbFormat implements ExportFormat {

  @Override
  public String export(List<SpriteModel> models) {
    StringBuilder sb = new StringBuilder();
    
    for (SpriteModel model : models) {
      int height = model.getHeight();
      int width = model.getWidth();
      boolean[][] tiles = model.getSpriteData();

      for (int j = 0; j < height; j++) {
        sb.append("\n\tdefb ");
        for (int i = 0; i < width; i+=8) {
          
          sb.append(tiles[i][j] ? "1" : "0");
        }
        sb.append("b,");
      } 
      sb.append("\n");
    }
    return sb.toString();
  }

}
