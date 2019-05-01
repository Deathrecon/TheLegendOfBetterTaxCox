package com.deathrecon.enemy;

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

import WorldObjects.Rupee;

public class Soldier extends GameObject{
	public Handler handler;
	private BufferedImage image;
	private BufferedImage imageTile;
	public BackgroundMove map;
	File file;
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
	public boolean rupeeDropped = false;
	float lastX = 0;
	float lastY = 0;
	float newX = 500;
	float newY = 500;
	int decision = 0;
	int frameTimer = 0;
	int waitTimer = 0;
	int walkTimer = 0;
	int timer = 0;
	boolean wait = true;
	boolean walk = false;
	
	public Soldier() {
		this.setId(ID.Enemy);
		this.setX(1205);
		this.setY(702);
		this.setHeight(100);
		this.setWidth(100);

		handler = this.getHandler();
		map = this.getMap();
		loadImage();
	}
	
	public Soldier(ID id, int x, int y, int height, int width, int layer,BackgroundMove map, Handler handler,TileHandler tileHandler) {
		this.setId(id);
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
		this.setWidth(width);
		this.setLayer(layer);
		this.setHandler(handler);
		this.setMap(map);
		this.setTileHandler(tileHandler);
		this.setHP(10);
		loadImage();
		
	}

	private void loadImage() {
		File file = new File("basicSoldier.png");
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getSprite(int x,int y) {
		int SIZEX = 24;
		int SIZEY = 30;
		if(second) {
			y+=4;
		}
		imageTile = image.getSubimage(x*SIZEX+2, y*SIZEY, 24, 30);
		if(moving == true) {
			if(currentFrame < 3) {
				if(timer == 4) {
					currentFrame++;
					timer = 0;}
				timer++;
			}else {
				currentFrame = 1;
			}
			
		}else {
			firstRun = true;
			if(timer == 4) {
			currentFrame = 1;
			timer = 0;}
			timer++;
			second = false;
			subtract = false;
		}
	}

	public void getMovement() {	
		if(decision == 0) {
				moving = true;
				movementAnim = 2;
				this.setVelY(2);
		}
		
		if(decision == 1){
				moving = true;
				movementAnim = 0;
				this.setVelY(-2);
		}
		
		if(decision == 2){
				moving = true;
				movementAnim = 1;
				this.setVelX(2);
		}
		if(decision == 3){
				moving = true;
				movementAnim = 3;
				this.setVelX(-2);
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
		if(this.isWaiting()) {
			moving = false;
			this.setVelY(0);
			this.setVelX(0);
			waitTimer++;
            if(waitTimer > 45) {
            	Random rand = new Random();
            	decision = rand.nextInt(4);
                this.setWait(false);
                waitTimer = 0;
            }
        }
        if(this.isWaiting() == false) {
            getMovement();
            if(walkTimer == 50) {
            	walkTimer = 0;
            	this.setWait(true);
            }
        }
		collide();
		
	}

	public void collide() {
		
		

	}


	public void render(Graphics g) {
		if(this.getHP() > 0) {
		getSprite(currentFrame,movementAnim);
		g.drawImage(imageTile,(int)this.getX()-25,(int)this.getY()-5,image.getHeight(),image.getWidth(),null);
	
		g.setColor(Color.RED);
		g.drawRect((int)this.getX(),(int)this.getY()-5, image.getHeight()-50, image.getWidth()-20);
		}else if(this.getHP() <= 0) {
			if(rupeeDropped == false) {
				for(int i = 0; i < 3; i++) {
					handler.addObject(new Rupee(ID.Rupee,(int)this.getX() - (int)map.getX(),(int)this.getY() - (int)map.getY(),30,40,this.getLayer(),this.getMap(),this.getHandler(),this.getTileHandler()), this.getLevel());
				}
				
				rupeeDropped = true;
				
			}
		}
		
	}


	public Rectangle getBounds() {
		return new Rectangle((int)this.getX(),(int)this.getY()-5,image.getHeight()-50, image.getWidth()-20);
	}
}

