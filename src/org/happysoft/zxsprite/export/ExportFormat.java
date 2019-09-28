/**
 * Copyright 2013. All rights reserved. 
 */
package org.happysoft.zxsprite.export;

import java.util.List;
import org.happysoft.zxsprite.SpriteModel;


/**
 *
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public interface ExportFormat {
  
  public String export(List<SpriteModel> model);

}
