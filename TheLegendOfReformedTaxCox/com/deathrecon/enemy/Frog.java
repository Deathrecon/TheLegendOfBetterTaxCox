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
import com.deathrecon.map.BackgroundMove;

public class Frog extends GameObject{
	
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
	float lastX = 0;
	float lastY = 0;
	float newX = 500;
	float newY = 500;
	int decision = 0;
	int frameTimer = 0;
	long waitTimer = 0;
	int walkTimer = 0;
	long startTimer = 0;
	
	public Frog() {
		this.setId(ID.Enemy);
		this.setX(505);
		this.setY(702);
		this.setX(500);
		this.setY(500);
		this.setHeight(100);
		this.setWidth(100);
		handler = this.getHandler();
		map = this.getMap();
		loadImage();
		
	}

	private void loadImage() {
		File file = new File("Frog.png");
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getSprite(int x,int y) {
		int SIZEX = 21;
		int SIZEY = 30;
		if(second) {
			y+=4;
		}
		imageTile = image.getSubimage(x*SIZEX, y*SIZEY, 21, 30);
		if(moving == true) {
			if(currentFrame < 1) {
					currentFrame++;
			}else {
				currentFrame = 0;
			}
			
		}else {
			firstRun = true;
			currentFrame = 0;
			second = false;
			subtract = false;
		}
	}

	public void getMovement() {	
		if(decision == 0) {
				moving = true;
				movementAnim = 3;
				this.setVelY(7);
		}
		
		if(decision == 1){
				moving = true;
				movementAnim = 1;
				this.setVelY(-7);
		}
		
		if(decision == 2){
				moving = true;
				movementAnim = 0;
				this.setVelX(7);
		}
		if(decision == 3){
				moving = true;
				movementAnim = 2;
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
		if(this.isWaiting()) {
			moving = false;
			this.setVelY(0);
			this.setVelX(0);
			waitTimer++;
            if(waitTimer > 250) {
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
		collide();
		
	}

	public void collide() {
		
		

	}


	public void render(Graphics g) {
		getSprite(currentFrame,movementAnim);
		g.drawImage(imageTile,(int)this.getX()-25,(int)this.getY()-40,70,70,null);
	
		g.setColor(Color.RED);
		g.drawRect((int)this.getX()-15,(int)this.getY()-5, 38, 35);
		
	}


	public Rectangle getBounds() {
		return new Rectangle((int)this.getX()-15,(int)this.getY()-5,38,35);
	}
}
