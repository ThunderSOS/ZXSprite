/**
 * Copyright 2013. All rights reserved.
 */
package org.happysoft.zxsprite;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class ZeusDgDefaultFormat implements ExportFormat {

  private static final Properties DG_EXPORT_PROPERTIES = new Properties();

  static {
    try {
      DG_EXPORT_PROPERTIES.load(ExportProperties.class.getClassLoader().getResourceAsStream("dg_export.properties"));
    } catch (IOException ioe) {
      throw new ExceptionInInitializerError(ioe);
    }
  }

  @Override
  public String export(List<SpriteModel> models) {
    StringBuilder sb = new StringBuilder();
    
    for (SpriteModel model : models) {
      int height = model.getHeight();
      int width = model.getWidth();
      boolean[][] tiles = model.getSpriteData();
      char on = ExportProperties.getOnCharacter();
      char off = ExportProperties.getOffCharacter();
      boolean whitespace = ExportProperties.useSpaces();

      for (int j = 0; j < height; j++) {
        sb.append("\n\tdg ");
        for (int i = 0; i < width; i++) {
          sb.append(tiles[i][j] ? on : off);
          if (whitespace) {
            sb.append(" ");
          }
        }
        sb.append(';');
      } 
      sb.append("\n");
    }
    System.out.println(sb.toString());
    return sb.toString();
  }

  private static class ExportProperties {

    /*
    useSpaces = true
    offCharacter = 0
    onCharacter = 1
     */
    public static char getOnCharacter() {
      return ((String) DG_EXPORT_PROPERTIES.get("onCharacter")).trim().charAt(0);
    }

    public static char getOffCharacter() {
      return ((String) DG_EXPORT_PROPERTIES.get("offCharacter")).trim().charAt(0);
    }

    public static boolean useSpaces() {
      String useSpaces = ((String) DG_EXPORT_PROPERTIES.get("useSpaces")).trim();
      return Boolean.valueOf(useSpaces);
    }
  }

}
