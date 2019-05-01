package com.deathrecon.game;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class GW {
	private static Game game;
	private Dimension dim;
	public static JFrame window;
	
	public GW(String name) {
		dim = new Dimension(1920, 1040);
		window = new JFrame("The Legend of Java");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setPreferredSize(dim);
		window.setMaximumSize(dim);
		window.setMinimumSize(dim);
		window.setResizable(false);
		game = new Game(dim);
		window.add(game);
		  window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    window.setSize(1920, 1040);
		    window.setLocationRelativeTo(null);
		    window.setUndecorated(true);
		    window.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
	    MetalLookAndFeel.setCurrentTheme(new Theme());
	    try {
	      UIManager.setLookAndFeel(new MetalLookAndFeel());
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	    SwingUtilities.updateComponentTreeUI(window);

	    window.setVisible(true);
	  
	}
	
  public static void main(final String args[]) {
	  GW gameWindow = new GW("The Legend Of Rynn");
  }

class Theme extends DefaultMetalTheme {
  public ColorUIResource getWindowTitleInactiveBackground() {
    return new ColorUIResource(java.awt.Color.orange);
  }

  public ColorUIResource getWindowTitleBackground() {
    return new ColorUIResource(java.awt.Color.GREEN);
  }

  public ColorUIResource getPrimaryControlHighlight() {
    return new ColorUIResource(java.awt.Color.orange);
  }

  public ColorUIResource getPrimaryControlDarkShadow() {
    return new ColorUIResource(java.awt.Color.orange);
  }

  public ColorUIResource getPrimaryControl() {
    return new ColorUIResource(java.awt.Color.orange);
  }

  public ColorUIResource getControlHighlight() {
    return new ColorUIResource(java.awt.Color.orange);
  }

  public ColorUIResource getControlDarkShadow() {
    return new ColorUIResource(java.awt.Color.orange);
  }

  public ColorUIResource getControl() {
    return new ColorUIResource(java.awt.Color.orange);
  }
}
}
