package com.deathrecon.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;

import com.deathrecon.Enum.ID;
import com.deathrecon.audio.AudioPlayer;
import com.deathrecon.enemy.Frog;
import com.deathrecon.game.GameObject;
import com.deathrecon.handler.Handler;
import com.deathrecon.map.BackgroundMove;
import com.deathrecon.map.Layer3;

public class Player extends GameObject{
	public BufferedImage imageTile;
	public Handler handler;
	public PlayerAnimation player;
	public int HP = 6;
	public int RupCount = 0;
	public double SHIELD = 1;
	public int movementAnim = 0;
	public boolean hasSword = false;
	public boolean hasShield = false;
	public AudioPlayer audio = new AudioPlayer();
	File swing = new File("MC_Link_Sword2.wav");
	public AudioPlayer runAudio = new AudioPlayer();
	File run = new File("MC_Link_Run.wav");
	private boolean runPlayed = false;
	private boolean SwingPlayed = false;
	int Sword = 0;
	int Timer = 0;
	public Player() {
		this.setLayer(3);
		this.setId(ID.Player);
		this.setX(905);
		this.setY(521);
		this.setHeight(50);
		this.setWidth(50);
		player = new PlayerAnimation(this);
	}

	@Override
	public void update() {
		if(hasSword) {
			if(handler.isSpace()) {
				player.handler = this.handler;
				player.slashing = true;
				if(!SwingPlayed) {
					SwingPlayed = true;
					audio.playSound(swing);
				}
			}
		}
			getMovement();
			this.setHP(HP);
			float newX = (this.getX() + this.getVelX());
			float newY = (this.getY() + this.getVelY());
			if(handler.linkHouse == false) {
				if(handler.edgeX) {
					if(newX == 905) {
						handler.edgeX = false;
					}
				}
				if(handler.edgeY) {
					if(handler.Level3) {
						if(handler.map.getY() > -1700) {
							if(newY > 520) {
								handler.edgeY = false;
							}
						}else {
							if(newY < 520) {
								handler.edgeY = false;
							}
						}
					}else if(handler.Level2) {
						if(handler.map.getY() > -1700) {
							if(newY > 520) {
								handler.edgeY = false;
							}
						}else {
							if(newY < 520) {
								handler.edgeY = false;
							}
						}
					}else if(handler.Level1) {
						if(handler.map.getY() > -1700) {
							if(newY > 520) {
								handler.edgeY = false;
							}
						}else {
							if(newY < 520) {
								handler.edgeY = false;
							}
						}
					}
				}
			}else {
				handler.edgeY = true;
				handler.edgeX = true;
			}
			this.setY(newY);
			this.setX(newX);
			if(this.getY() < 0) {
				if(handler.Level2) {
					handler.Level3 = true;
					handler.Level2 = false;
					this.setLayer(2);
					this.setY(1080);
				}else if(handler.Level1) {
					handler.Level1 = false;
					handler.Level2 = true;
					this.setLayer(1);
					this.setY(1080);
				}
			}else if(this.getY() > 1080){
				if(handler.Level3) {
					handler.Level3 = false;
					handler.Level2 = true;
					this.setLayer(3);
					this.setY(0);
				}else if(handler.Level2) {
					handler.Level2 = false;
					handler.Level1 = true;
					this.setLayer(2);
					this.setY(0);
				}
			}
			if(SwingPlayed && Timer > 10) {
				Timer = 0;
				SwingPlayed = false;
				
			}else {
				Timer++;
			}
	}	
	public void getMovement() {
		if(this.HP > 0) {
			if(handler.isUp()) {
				if(handler.edgeY) {
					this.setVelY(-7);
				}else {
					this.setVelY(0);
				}
				
				movementAnim = 0;
				player.moving = true;
			}else if(handler.isDown() == false){
				this.setVelY(0);
			}
			if(handler.isDown()) {
				if(handler.edgeY) {
					this.setVelY(7);
				}else {
					this.setVelY(0);
				}
				movementAnim = 2;
				player.moving = true;
			}else if(handler.isUp() == false) {
				this.setVelY(0);
			}
			if(handler.isLeft()) {
				if(handler.edgeX) {
					this.setVelX(-7);
				}else {
					this.setVelX(0);
				}
				movementAnim = 3;
				player.moving = true;
			}else if(handler.isRight() == false) {
				this.setVelX(0);
			}
			if(handler.isRight()) {
				if(handler.edgeX) {
					this.setVelX(7);
				}else {
					this.setVelX(0);
				}
				movementAnim = 1;
				player.moving = true;
			}else if(handler.isLeft() == false) {
				this.setVelX(0);
			}
			if(player.moving == false) {
				this.setVelX(0);
				this.setVelY(0);
			}
			if(handler.isUp() == false && handler.isDown() == false &&
			   handler.isLeft() == false && handler.isRight() == false) {
				player.moving = false;
				this.setVelX(0);
				this.setVelY(0);
			}
			
			if(!runPlayed && player.moving) {
				audio.playSound(run);
				runPlayed = true;
			}
			if(runPlayed && Timer > 25) {
				Timer = 0;
				runPlayed = false;
				
			}else {
				Timer++;
				
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(this.HP > 0) {
			if(player.slashing == true && player.swimming == false) {
				if(movementAnim == 0) {
					
					player.Slash(movementAnim);
					g.drawImage(imageTile,(int)this.getX()-90,(int)this.getY()-75,180,130,null);
					//g.drawRect((int)this.getX()-65,(int)this.getY()-20,160,30);
				}
				if(movementAnim == 1) {
					player.Slash(movementAnim);
					g.drawImage(imageTile,(int)this.getX()-40,(int)this.getY()-45,180,150,null);
					//g.drawRect((int)this.getX()+55,(int)this.getY()-40,50,150);
				}
				if(movementAnim == 2) {
					player.Slash(movementAnim);
					g.drawImage(imageTile,(int)this.getX()-50,(int)this.getY()-15,180,110,null);
					//g.drawRect((int)this.getX()-35,(int)this.getY()+50,150,30);
				}
				if(movementAnim == 3) {
					player.Slash(movementAnim);
					g.drawImage(imageTile,(int)this.getX()-95,(int)this.getY()-55,180,160,null);
					//g.drawRect((int)this.getX()-60,(int)this.getY()-40,50,150);
				}
				handler.updateEnemies();
			}else if(player.swimming == true){
				player.slashing = false;
				if(movementAnim == 0) {
					player.Swim(movementAnim);
					g.drawImage(imageTile,(int)this.getX()-28,(int)this.getY()-20,100,100,null);
				}
				if(movementAnim == 1) {
					player.Swim(movementAnim);
					g.drawImage(imageTile,(int)this.getX()-28,(int)this.getY()-10,100,100,null);
				}
				if(movementAnim == 2) {
					player.Swim(movementAnim);
					g.drawImage(imageTile,(int)this.getX()-26,(int)this.getY()-30,100,100,null);
				}
				if(movementAnim == 3) {
					player.Swim(movementAnim);
					g.drawImage(imageTile,(int)this.getX()-28,(int)this.getY()-10,100,100,null);
				}
				
			}else {
				player.Walk(movementAnim);
				g.drawImage(imageTile,(int)this.getX()-25,(int)this.getY()-40,100,100,null);
				//g.setColor(Color.RED);
				//g.drawRect((int)this.getX()-5,(int)this.getY()+25,58,35);
			}
		}		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)this.getX()-5,(int)this.getY()+25,58,35);
	}
	public Rectangle getSlashUpBounds() {
		return new Rectangle((int)this.getX()-65,(int)this.getY()-20,160,30);
	}
	public Rectangle getSlashRightBounds() {
		return new Rectangle((int)this.getX()+55,(int)this.getY()-40,50,150);
	}
	public Rectangle getSlashDownBounds() {
		return new Rectangle((int)this.getX()-35,(int)this.getY()+50,150,30);
	}
	public Rectangle getSlashLeftBounds() {
		return new Rectangle((int)this.getX()-60,(int)this.getY()-40,50,150);
	}
	
}
