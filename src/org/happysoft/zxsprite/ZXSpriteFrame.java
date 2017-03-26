/**
 * Copyright 2013. All rights reserved.
 */
package org.happysoft.zxsprite;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Chris Francis (c_francis1@yahoo.com)
 */
public class ZXSpriteFrame extends JFrame {

  private final JMenu fileMenu = new JMenu("File");

  private final JMenuItem open = new JMenuItem("Open");
  private final JMenuItem save = new JMenuItem("Save");

  private final JMenu export = new JMenu("Export");
  private final JMenuItem zeusExport = new JMenuItem("Zeus dg");

  private final JMenu editMenu = new JMenu("Edit");

  private final JMenuItem copy = new JMenuItem("Copy");
  private final JMenuItem paste = new JMenuItem("Paste");
  private final JMenuItem shiftLeft = new JMenuItem("Shift Left");
  private final JMenuItem shiftRight = new JMenuItem("Shift Right");
  private final JMenuItem shiftUp = new JMenuItem("Shift Up");
  private final JMenuItem shiftDown = new JMenuItem("Shift Down");
  private final JMenuItem reverse = new JMenuItem("Reverse");
  private final JMenuItem reset = new JMenuItem("Reset");

  private JScrollPane scrollPane;

  private AnimationFrameTabPanel animationFrameTabPanel;

  private final JComboBox<Integer> width = new JComboBox(new Integer[]{8, 16, 24, 32});
  private final JComboBox<Integer> height = new JComboBox(new Integer[]{8, 16, 24, 32});
  private final JComboBox<Integer> frames = new JComboBox(new Integer[]{1, 2, 4, 8});

  private final JFileChooser fc = new JFileChooser();

  private final ZeusDgDefaultFormat zeusDgFormat = new ZeusDgDefaultFormat();

  // buffer used for copy and paste
  private SpriteModel buffer = null;

  public ZXSpriteFrame(String title) {
    super(title);
    jinit();
  }

  private void jinit() {
    enableMenus(false, false);
    frames.setSelectedItem(4);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    createMenus();
    setPreferredSize(new Dimension(760, 500));
    scrollPane = new JScrollPane();
    add(scrollPane);
  }

  private void createMenus() {
    JMenuBar menuBar = new JMenuBar();
    JMenuItem newMenu = new JMenuItem("New");
    JPanel spriteOptionPanel = createOptionPanel();

    fileMenu.add(newMenu);
    fileMenu.add(open);
    fileMenu.add(save);
    fileMenu.add(export);

    export.add(zeusExport);

    editMenu.add(copy);
    editMenu.add(paste);
    editMenu.add(shiftLeft);
    editMenu.add(shiftRight);
    editMenu.add(shiftUp);
    editMenu.add(shiftDown);
    editMenu.add(reverse);
    editMenu.add(reset);

    newMenu.addActionListener((ActionEvent e) -> {
      int option = JOptionPane.showOptionDialog(null, spriteOptionPanel, "Select sprite attributes",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, null, null);
      if (option == JOptionPane.CANCEL_OPTION) {
        // user hit cancel
      } else if (option == JOptionPane.OK_OPTION) {
        Integer w = (Integer) width.getSelectedItem();
        Integer h = (Integer) height.getSelectedItem();
        Integer f = (Integer) frames.getSelectedItem();
        enableMenus(false, false);
        buffer = null;
        addAnimationTabPanel(w, h, f);
      }
    });

    reset.addActionListener((ActionEvent e) -> {
      animationFrameTabPanel.getSelectedSpriteToggler().clear();
    });

    copy.addActionListener((ActionEvent e) -> {
      buffer = animationFrameTabPanel.getSelectedSpriteToggler().getSprite();
    });

    paste.addActionListener((ActionEvent e) -> {
      if (buffer != null) {
        animationFrameTabPanel.getSelectedSpriteToggler().setSprite(buffer);
        enableMenus(true, true);
      }
    });

    shiftRight.addActionListener((ActionEvent e) -> {
      animationFrameTabPanel.getSelectedSpriteToggler().shiftRight();
      buffer = null;
    });

    shiftLeft.addActionListener((ActionEvent e) -> {
      animationFrameTabPanel.getSelectedSpriteToggler().shiftLeft();
      buffer = null;
    });
    
    shiftUp.addActionListener((ActionEvent e) -> {
      animationFrameTabPanel.getSelectedSpriteToggler().shiftUp();
      buffer = null;
    });

    shiftDown.addActionListener((ActionEvent e) -> {
      animationFrameTabPanel.getSelectedSpriteToggler().shiftDown();
      buffer = null;
    });    
    
    reverse.addActionListener((ActionEvent e) -> {
      animationFrameTabPanel.getSelectedSpriteToggler().reverse();
      buffer = null;
    });

    save.addActionListener((ActionEvent e) -> {
      int ret = fc.showSaveDialog(this);
      if (ret == JFileChooser.APPROVE_OPTION) {
        FileFormat ff = new FileFormat(animationFrameTabPanel);
        try {
          ff.save(fc.getSelectedFile(), animationFrameTabPanel.getSprites());
        } catch (IOException ex) {
          Logger.getLogger(ZXSpriteFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    });

    open.addActionListener((ActionEvent e) -> {
      int ret = fc.showOpenDialog(this);
      if (ret == JFileChooser.APPROVE_OPTION) {
        FileFormat ff = new FileFormat(animationFrameTabPanel);
        try {
          ArrayList<SpriteModel> models = ff.load(fc.getSelectedFile());
          addAnimationTabPanel(models);

        } catch (IOException | ClassCastException ex) {
					Logger.getLogger(ZXSpriteFrame.class.getName()).log(Level.WARNING, "Invalid sprite definition file", ex);
					JOptionPane.showMessageDialog(this, "Invalid sprite definition file.");		
					
        } catch (ClassNotFoundException cnfe) {
					Logger.getLogger(ZXSpriteFrame.class.getName()).log(Level.SEVERE, "Something very weird has happened!", cnfe);
        }
      }
    });

    zeusExport.addActionListener((ActionEvent e) -> {
      String export = zeusDgFormat.export(animationFrameTabPanel.getSprites());
      StringSelection ss = new StringSelection(export);
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(ss, null);
      
    });

    menuBar.add(fileMenu);
    menuBar.add(editMenu);

    setJMenuBar(menuBar);
  }

  private JPanel createOptionPanel() {
    JPanel p = new JPanel(new FlowLayout());
    JLabel widthLabel = new JLabel("Width");
    JLabel heightLabel = new JLabel("Height");
    JLabel framesLabel = new JLabel("Num Frames");
    p.add(widthLabel);
    p.add(width);
    p.add(heightLabel);
    p.add(height);
    p.add(framesLabel);
    p.add(frames);
    return p;
  }

  private void addAnimationTabPanel(int width, int height, int frames) {
    animationFrameTabPanel = new AnimationFrameTabPanel(this, width, height, frames);
    Dimension d = animationFrameTabPanel.getPreferredSize();
    scrollPane.setViewportView(animationFrameTabPanel);
    setSize(d);
    repaint();
  }

  private void addAnimationTabPanel(ArrayList<SpriteModel> models) {
    animationFrameTabPanel = new AnimationFrameTabPanel(this, models);
    Dimension d = animationFrameTabPanel.getPreferredSize();
    scrollPane.setViewportView(animationFrameTabPanel);
    setSize(d);
    repaint();
  }

  public void enableMenus(boolean enabled, boolean editEnabled) {
    copy.setEnabled(enabled);
    export.setEnabled(enabled);
    editMenu.setEnabled(editEnabled);
  }

}
