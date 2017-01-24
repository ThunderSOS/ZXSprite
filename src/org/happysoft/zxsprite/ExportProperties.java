/**
 * Copyright 2013. All rights reserved. 
 */
package org.happysoft.zxsprite;


import java.io.IOException;
import java.util.Properties;
/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class ExportProperties {

  private static final Properties props = new Properties();
  
  static {
    try {
      props.load(ExportProperties.class.getClassLoader().getResourceAsStream("export.properties"));
    } catch (IOException ioe) {
      throw new ExceptionInInitializerError(ioe);
    }
  }
  
  /*
  useSpaces = true
  offCharacter = 0
  onCharacter = 1
  */  
  public static char getOnCharacter() {
    return ((String)props.get("onCharacter")).trim().charAt(0);
  }
  
  public static char getOffCharacter() {
    return ((String)props.get("offCharacter")).trim().charAt(0);
  }
  
   public static boolean useSpaces() {
    String useSpaces = ((String)props.get("useSpaces")).trim();
    return Boolean.valueOf(useSpaces);
  }
}
