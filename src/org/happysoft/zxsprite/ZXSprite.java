
package org.happysoft.zxsprite;

/**
 *
 * @author Chris
 */
import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class ZXSprite {

  public static void main(String[] args) {
    System.out.println(ExportProperties.getOnCharacter());
    SwingUtilities.invokeLater(() -> {
      createAndShowGUI();
    });
  }

  private static void createAndShowGUI() {
    JFrame f = new ZXSpriteFrame("ZX Sprite Editor");
    f.pack();
    f.setVisible(true);
  }
}
