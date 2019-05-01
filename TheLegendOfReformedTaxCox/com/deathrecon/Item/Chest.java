package com.deathrecon.Item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.deathrecon.Enum.ID;
import com.deathrecon.game.GameObject;
import com.deathrecon.handler.Handler;
import com.deathrecon.handler.TileHandler;
import com.deathrecon.map.BackgroundMove;
import com.deathrecon.player.Player;

public class Chest extends GameObject{
	
	public Handler handler;
	public Player player;
	private BufferedImage FullChest;
	private BufferedImage EmptyChest;
	private BufferedImage imageTile;
	public BackgroundMove map;
	//private int[][] tileArray = new int[11][7];
	File Chest = new File("testChest.png");
	File Empty = new File("testEmpty.png"); 
	public int currentFrame = 0;
	public int movementAnim = 0;
	public int POINTS = 0;
	public String keyColliding = "";
	public boolean tileTransfer = false;
	public boolean freeze = false;
	public boolean firstRun = true;
	public boolean subtractFlag = false;
	public boolean subtract = false;
	public boolean second = false;
	public boolean moving = false;
	public boolean falling = false;
	public boolean sideCollide = false;
	public boolean collided = false;
	public boolean collisionPlaced = false;
	public boolean initialized = false;
	public boolean open = false;
	float lastX = 0;
	float lastY = 0;
	float newX = 0;
	float newY = 0;
	public boolean played = false;
	int decision = 0;
	int frameTimer = 0;
	int waitTimer = 0;
	int walkTimer = 0;
	boolean wait = true;
	boolean walk = false;
	
	public Chest() {
		this.setId(ID.Chest);
		this.setX(1505);
		this.setY(702);
		this.setHeight(70);
		this.setWidth(70);
		loadImage();
		
	}
	
	public Chest(ID id, int x, int y, int height, int width, int layer,BackgroundMove map,Handler handler,TileHandler tileHandler) {
		this.setId(id);
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
		this.setWidth(width);
		this.setLayer(layer);
		this.setHandler(handler);
		this.setMap(map);
		this.setTileHandler(tileHandler);
		loadImage();
	}

	private void loadImage() {
		map = this.getMap();
		handler = this.getHandler();
		try {
			FullChest = ImageIO.read(Chest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			EmptyChest = ImageIO.read(Empty);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getSpriteFull(int x,int y) {
		int SIZEX = 20;
		int SIZEY = 20;
		imageTile = FullChest.getSubimage(x*SIZEX, y*SIZEY, 16, 16);
	}
	public void getSpriteEmpty(int x,int y) {
		int SIZEX = 20;
		int SIZEY = 20;
		imageTile = EmptyChest.getSubimage(x*SIZEX, y*SIZEY, 16, 16);
	}

	@Override
	public void update() {
		if(map.getX() != lastX) {
			newX = ((this.getX() + (map.getX()-lastX)) + this.getVelX());
			lastX = map.getX();
		}else {
			newX = (this.getX() +  this.getVelX());
		}
		if(map.getY() != lastY) {
			newY = ((this.getY() + (map.getY()-lastY)) + this.getVelY());
			lastY = map.getY();
		}else {
			newY = (this.getY() +  this.getVelY());
		}
		this.setY(newY);
		this.setX(newX);
		getMovement();
		collide();
		
	}
	
	public void collide() {

	}

	
	public void getMovement() {	
				this.setVelY(0);
				this.setVelX(0);
		
		
	}
	public void render(Graphics g) {
		if(open == false) {
			getSpriteFull(currentFrame,movementAnim);
			g.drawImage(imageTile,(int)this.getX(),(int)this.getY()-40,70,70,null);
		}else {
			getSpriteEmpty(currentFrame,movementAnim);
			g.drawImage(imageTile,(int)this.getX(),(int)this.getY()-40,70,70,null);
		}
		
		if(handler.player.getX() <= this.getX()+this.getWidth()+30 && handler.player.getX() > this.getX()-this.getWidth()-30){
			
			if(handler.player.getY() < this.getY()+this.getHeight()+ 30 && handler.player.getY() > this.getY() - this.getHeight() -60) {
				
				if(handler.isSpace()) {
					handler.player.player.slashing = false;
					open = true;
					handler.player.hasSword = true;
				}
			
			}
			
		}
		
	}

	
	public Rectangle getBounds() {
		return new Rectangle((int)this.getX(),(int)this.getY()-50,70,70);
	}

}

