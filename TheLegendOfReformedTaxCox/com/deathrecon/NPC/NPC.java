package com.deathrecon.NPC;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import com.deathrecon.Enum.ID;
import com.deathrecon.audio.AudioPlayer;
import com.deathrecon.game.GameObject;
import com.deathrecon.handler.Handler;
import com.deathrecon.handler.TileHandler;
import com.deathrecon.map.BackgroundMove;
import com.deathrecon.player.Player;

public class NPC extends GameObject implements ActionListener{
	public Handler handler;
	private BufferedImage image;
	private BufferedImage imageTile;
	private BufferedImage TEXT;
	public BackgroundMove map;
	public Timer waitTimer;
	public Timer walkTimer;
	public File voice;
	public AudioPlayer audio = new AudioPlayer();
	public boolean play = false;
	//private int[][] tileArray = new int[11][7];
	File file;
	File text;
	public int currentFrame = 0;
	public int movementAnim = 0;
	public boolean freeze = false;
	public boolean firstRun = true;
	public boolean subtractFlag = false;
	public boolean subtract = false;
	public boolean second = false;
	public boolean moving = false;
	public boolean played = false;
	public boolean instance = false;
	public Player player;
	float lastX = 0;
	float lastY = 0;
	float newX = 500;
	float newY = 500;
	int blockedDecision = 4;
	int decision = 0;
	int frameTimer = 0;
	boolean wait = true;
	boolean walk = false;
	public boolean talkProx =isTalkProx();
	int Timer = 0;
	
	public NPC() {
		this.setId(ID.Enemy);
		this.setX(1205);
		this.setY(702);
		this.setHeight(100);
		this.setWidth(100);
		handler = this.getHandler();
		map = this.getMap();
		loadImage();
	}
	
	public NPC(ID id, int x, int y, int height, int width, int layer,BackgroundMove map, Handler handler,TileHandler tileHandler) {
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
		waitTimer = new Timer(2000,this);
		walkTimer = new Timer(500,this);
		waitTimer.start();
		voice = new File("MC_CastleGuard_Hey.wav");
		loadImage();
	}
	
	private void loadImage() {
		map = this.getMap();
		handler = this.getHandler();
		File file = new File("NPC.png");
		text = new File("HELLO.png");
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
		imageTile = image.getSubimage(x*SIZEX, y*SIZEY, 18, 28);
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
				movementAnim = 0;
				this.setVelY(7);
		}
		
		if(decision == 1){
				moving = true;
				movementAnim = 2;
				this.setVelY(-7);
		}
		
		if(decision == 2){
				moving = true;
				movementAnim = 3;
				this.setVelX(7);
		}
		if(decision == 3){
				moving = true;
				movementAnim = 1;
				this.setVelX(-7);
		}
		this.setBlockedDecision(4);
		
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
	        }
		}
		
	}


	public void render(Graphics g) {
		if(this.getHP() > 0) {
			getSprite(currentFrame,movementAnim);
			g.drawImage(imageTile,(int)this.getX()-25,(int)this.getY()-40,70,70,null);
			//g.setColor(Color.RED);
			//g.drawRect((int)this.getX()-15,(int)this.getY()-25, 60, 55);
			g.setColor(Color.RED);
			g.drawRect((int)this.getX()-15,(int)this.getY()-25, 60, 55);
			Timer++;
			
			if(handler.player.getX() <= this.getX()+this.getWidth()+30 && handler.player.getX() > this.getX()-this.getWidth()-30){
				
				if(handler.player.getY() < this.getY()+this.getHeight()+ 30 && handler.player.getY() > this.getY() - this.getHeight() -60) {
					if(play) {
						play = false;
						audio.playSound(voice);
						
					}
					g.drawImage(TEXT,(int)this.getX()+20,(int)this.getY()-90,70,70,null);
				}
				
			}
			if(Timer > 200 && play == false) {
				play = true;
				Timer = 0;
			}
			
		}

	}


	public Rectangle getBounds() {
		return new Rectangle((int)this.getX()-15,(int)this.getY()-25, 60, 55);
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if(a.getSource() == waitTimer) {
			Random rand = new Random();
			decision = rand.nextInt(3);
			getMovement();
        	this.setWait(false);
        	walkTimer.start();
        	waitTimer.stop();
		}else {
	        this.setWait(true);
	        waitTimer.start();
	        walkTimer.stop();
		}
	}
}


