package com.deathrecon.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.deathrecon.Enum.ID;
import com.deathrecon.Item.Chest;

import com.deathrecon.HUD.HUD;
import com.deathrecon.HUD.Hearts;
import com.deathrecon.HUD.Rupees;
import com.deathrecon.audio.*;
import com.deathrecon.intro.*;
import com.deathrecon.map.*;
import com.deathrecon.player.*;
import com.deathrecon.tilemap.LayerTile;
import com.deathrecon.map.*;

import WorldObjects.World;

import com.deathrecon.enemy.*;
import com.deathrecon.handler.Handler;
import com.deathrecon.handler.KeyInputHandler;
import com.deathrecon.handler.TileHandler;


public class Game extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	BufferedImage image;
	private Timer timer;
	private Player player;
	private Frog frog;
	private BackgroundMove backGround;
	private Layer3 layer3;
	private Layer2 layer2;
	private Layer1 layer1;
	private Layer2Level2 layer2Lvl2;
	private Layer3Level2 layer3Lvl2;
	private Handler handler;
	private TileHandler tileHandler;
	private KeyInputHandler key;
	private HUD hud;
	private Hearts lifeBar;
	private Rupees rups;
	public File title = new File("enterPress.wav");
	
	public Game(Dimension dim) {
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);
		this.setBackground(Color.CYAN);
		this.setFocusable(true);
		
		init();
		
		timer = new Timer(20,this);
		timer.start();
		
		
	}
	
	public void init() {
		//Initialize
		player = new Player();
		lifeBar = new Hearts();
		hud = new HUD();
		lifeBar.player = this.player;
		rups = new Rupees();
		backGround = new BackgroundMove();
		layer3 = new Layer3();
		layer2 = new Layer2();
		layer1 = new Layer1();
		layer2Lvl2 = new Layer2Level2();
		layer3Lvl2 = new Layer3Level2();
		handler = new Handler();
		handler.player = this.player;
		
		
		//Key Listener
		
		//Setting up handler
		
		//Layers
		backGround.layer3 = this.layer3;
		backGround.layer2 = this.layer2;
		backGround.layer1 = this.layer1;
		handler.map = backGround;
		
		//Add objects
		handler.addObject(backGround);
		handler.addObject(player);
		handler.addObject(layer1);
		handler.addObject(layer2);
		handler.addObject(layer3);
		handler.addObject(hud);
		handler.addObject(lifeBar);
		handler.addObject(rups);
		
		tileHandler = new TileHandler(handler,backGround);
		backGround.tileHandler = this.tileHandler;
		handler.tileHandler = this.tileHandler;
		tileHandler.player = this.player;
		player.handler = this.handler;
		layer3.handler = this.handler;
		layer2.handler = this.handler;
		layer1.handler = this.handler;
		hud.handler = this.handler;
		backGround.handler = this.handler;
		lifeBar.handler = this.handler;
		rups.handler = this.handler;
		rups.player = this.player;
		hud.handler = this.handler;
		hud.player = this.player;
		
		key = new KeyInputHandler(handler,tileHandler);
		this.addKeyListener(key);
		this.addMouseListener(tileHandler);
	}
	
	
	public void update() {
		
		handler.update();
		tileHandler.update();
	}
	
	public void render(Graphics g) {
		handler.render(g);
		tileHandler.render(g);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		render(g);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		repaint();
	}
}
