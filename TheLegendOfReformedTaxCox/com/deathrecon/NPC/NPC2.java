package com.deathrecon.NPC;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.deathrecon.Enum.ID;
import com.deathrecon.game.GameObject;
import com.deathrecon.handler.Handler;
import com.deathrecon.handler.TileHandler;
import com.deathrecon.map.BackgroundMove;
import com.deathrecon.player.Player;

public class NPC2 extends GameObject {
	public Handler handler;
	private BufferedImage image;
	private BufferedImage imageTile;
	private BufferedImage TEXT;
	public BackgroundMove map;
	//private int[][] tileArray = new int[11][7];
	File file;
	File text;
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
	public boolean played = false;
	public boolean instance = false;
	public Player player;
	float lastX = 0;
	float lastY = 0;
	float newX = 500;
	float newY = 500;
	int decision = 0;
	int frameTimer = 0;
	int waitTimer = 0;
	int walkTimer = 0;
	boolean wait = true;
	boolean walk = false;
	public boolean talkProx =isTalkProx();
	private int Timer = 0;
	
	public NPC2() {
		this.setId(ID.Enemy);           
		this.setX(1205);
		this.setY(702);
		this.setHeight(100);
		this.setWidth(100);

		handler = this.getHandler();
		map = this.getMap();
		System.out.println("IS THIS RUNNING?");
		loadImage();
	}
	
	public NPC2(ID id, int x, int y, int height, int width, int layer,BackgroundMove map, Handler handler,TileHandler tileHandler) {
		this.setId(id);
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
		this.setWidth(width);
		this.setLayer(layer);
		this.setHandler(handler);
		this.setMap(map);
		this.setTileHandler(tileHandler);
		this.setHP(3);
		loadImage();
	}
	
	private void loadImage() {
		map = this.getMap();
		handler = this.getHandler();
		File file = new File("NPC.png");
		text = new File("NPC2TEXT.png");
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			TEXT = ImageIO.read(text);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getSprite(int x,int y) {
		int SIZEX = 18;
		int SIZEY = 28;
		if(second) {
			y+=4;
		}
		imageTile = image.getSubimage(x*SIZEX+1, y*SIZEY, 18, 28);
		if(moving == true) {
			if(currentFrame < 1) {
					currentFrame++;
					if(Timer < 12) {
						
						currentFrame = 1;
						Timer++;
					}
			}else {
				currentFrame = 0;
			}
			
		}else {
			firstRun = true;
			if(Timer == 4) {
				Timer = 0;
			}
			currentFrame = 0;
			second = false;
			subtract = false;
		}
	}

	public void getMovement() {	
		if(decision == 0) {
				moving = true;
				movementAnim = 4;
				this.setVelY(7);
		}
		
		if(decision == 1){
				moving = true;
				movementAnim = 6;
				this.setVelY(-7);
		}
		
		if(decision == 2){
				moving = true;
				movementAnim = 7;
				this.setVelX(7);
		}
		if(decision == 3){
				moving = true;
				movementAnim = 8;
				this.setVelX(-7);
		}
		walkTimer++;
		
	}

	public void update() {
		handler = this.getHandler();
		map = this.getMap();
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
		if(instance == false) {
			if(this.isWaiting()) {
				moving = false;
				this.setVelY(0);
				this.setVelX(0);
				waitTimer++;
	            if(waitTimer == 100) {
	            	Random rand = new Random();
	            	decision = rand.nextInt(3);
	                this.setWait(false);
	                waitTimer = 0;
	            }
	        }
	        if(this.isWaiting() == false) {
	            getMovement();
	            if(walkTimer == 15) {
	            	walkTimer = 0;
	            	this.setWait(true);
	            }
	        }
		}
		
	}


	public void render(Graphics g) {
		if(this.getHP() > 0) {
			getSprite(currentFrame,movementAnim);
			g.drawImage(imageTile,(int)this.getX()-25,(int)this.getY()-40,70,70,null);
			//g.setColor(Color.RED);
			//g.drawRect((int)this.getX()-15,(int)this.getY()-25, 60, 55);
			
			if(handler.player.getX() <= this.getX()+this.getWidth()+30 && handler.player.getX() > this.getX()-this.getWidth()-30){
				
				if(handler.player.getY() < this.getY()+this.getHeight()+ 30 && handler.player.getY() > this.getY() - this.getHeight() -30) {
					
					g.drawImage(TEXT,(int)this.getX(),(int)this.getY()-90,100,100,null);
				
				}
				
			}
			
		}
	}


	public Rectangle getBounds() {
		return new Rectangle((int)this.getX()-15,(int)this.getY()-25, 60, 55);
	}
}


