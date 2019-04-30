package com.deathrecon.map;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.deathrecon.Enum.ID;
import com.deathrecon.Item.Chest;
import com.deathrecon.enemy.Frog;
import com.deathrecon.enemy.Octorok;
import com.deathrecon.game.GameObject;
import com.deathrecon.handler.Handler;
import com.deathrecon.handler.TileHandler;


public class BackgroundMove extends GameObject{
	private BufferedImage image;
	public Layer3 layer3;
	public Layer2 layer2;
	public Layer1 layer1;
	public TileHandler tileHandler;
	public Handler handler;
	public boolean move = true;
	public boolean init = true;
	public boolean first = true;
	
	public BackgroundMove() {
		this.setId(ID.Background);
		this.setWidth(4000);
		this.setHeight(4000);
		this.setImage("Level2.png");
		loadImage();
	}
	public void loadImage() {
		System.out.println("RUNNING");
		this.image = this.getImage();
		if(first == false) {
			if(init) {
				this.setX(-1000);
				this.setY(-1000);
				init = false;
			}
		}else {
			first = false;
		}
	}

	public void render(Graphics g) {
		if(handler.Level2) {
			layer1.setY(this.getY());
			layer2.setY(this.getY());
			layer1.setX(this.getX());
			layer2.setX(this.getX());
			layer3.setY(this.getY());
			layer3.setX(this.getX());
		}else if(handler.Level3 || handler.Level1){
			layer3.setY(this.getY());
			layer3.setX(this.getX()-8);
		}
		g.drawImage(image,(int)this.getX(),(int)this.getY(),this.getWidth(),this.getHeight(),null);
	}
	@Override
	public void update() {
		getMovement();
		float newX = (this.getX() + this.getVelX());
		float newY = (this.getY() + this.getVelY());
		if(handler.Level1) {
			System.out.println("NEW Y: " + newY);
			if(newY > -2130 && newY < 7) {
				this.setY(newY);
			}else {
				System.out.println("SET TO TRUE");
				handler.edgeY = true;
			}
		}else if(handler.Level2) {
			if(newY > -2130 && newY < 0) {
				this.setY(newY);
			}else {
				handler.edgeY = true;
			}
		}else if(handler.Level3) {
			if(newY > -2473 && newY < 7) {
				this.setY(newY);
			}else {
				handler.edgeY = true;
			}
		}
		if(newX > -2090 && newX < 5) {
			this.setX(newX);
		}else {
			handler.edgeX = true;
		}
	}
	public void getMovement() {
		if(move) {
			if(handler.isUp()) {
				if(handler.edgeY == false) {
					this.setVelY(7);
				}
			}else if(handler.isDown() == false){
				this.setVelY(0);
			}
			if(handler.isDown()) {
				if(handler.edgeY == false) {
					this.setVelY(-7);
				}
			}else if(handler.isUp() == false) {
				this.setVelY(0);
			}
			if(handler.isLeft()) {
				if(handler.edgeX == false) {
					this.setVelX(7);
				}
			}else if(handler.isRight() == false) {
				this.setVelX(0);
			}
			if(handler.isRight()) {
				if(handler.edgeX == false) {
					this.setVelX(-7);
				}
			}else if(handler.isLeft() == false) {
				this.setVelX(0);

			}
		}else {
			this.setVelX(0);
			this.setVelY(0);

		}
	}
	@Override
	public Rectangle getBounds() {
		return null;
	}

}