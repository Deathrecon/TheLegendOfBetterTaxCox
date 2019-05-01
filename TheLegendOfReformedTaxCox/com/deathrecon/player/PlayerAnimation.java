package com.deathrecon.player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.deathrecon.handler.Handler;

public class PlayerAnimation{
	private BufferedImage imageWalk;
	private BufferedImage imageSlash;
	private BufferedImage imageSwim;
	private BufferedImage imageTile;
	private Player player;
	public Handler handler;
	public int currentFrame = 0;
	public int movementAnim = 0;
	public int slashAnim = 0;
	public boolean firstRun = true;
	public boolean subtractFlag = false;
	public boolean subtract = false;
	public boolean second = false;
	public boolean moving = false;
	public boolean slashing = false;
	public boolean swimming = false;
	public boolean spaceReleased = false;
	public int timer = 0;
	
	public PlayerAnimation(Player player) {
		this.player = player;
		loadImage();
	}
	
	private void loadImage() {
		File file = new File("linkSprite.png");
		try {
			imageWalk = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		file = new File("Slash3.png");
		try {
			imageSlash = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		file = new File("Swim.png");
		try {
			imageSwim = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Walk(int y) {
		//Number of pixels to move by//
		int SIZEX = 24;
		int SIZEY = 32;
		////
		if(second) {
			y+=4;
		}
		//Takes the bufferedimage and creates a window with the x being the current frame times the sizeX
		//and the y being the currentAnim times the sizeY.//
		//The last two numbers are representative for the width and height of the sub image you're creating//
		imageTile = imageWalk.getSubimage(currentFrame*SIZEX, y*SIZEY, 24, 32);
		player.imageTile = imageTile;
		if(moving == true) {
			if(subtract == true) {
				if(currentFrame > 2) {
					currentFrame--;
				}else if(second == true){
					currentFrame = 11;
					second = false;
				}else if(second== false){
					currentFrame = 2;
					second = true;
					subtractFlag = false;
					firstRun = false;
				}
			}
			if(subtract == false) {
				if(firstRun) {
					if(currentFrame < 11) {
						currentFrame++;
					}else if(second == false){
						second = true;
						currentFrame = 0;
					}else if(second == true){
						subtractFlag = true;
					}
				}else {
					if(currentFrame < 11) {
						currentFrame++;
					}else if(second == true){
						subtractFlag = true;
					}
				}
			}
			subtract = subtractFlag;
		}else {
			firstRun = true;
			currentFrame = 0;
			second = false;
			subtract = false;
		}
	}
	
	public void Slash(int movementAnim) {
		int spriteLocationX = 47;
		int spriteLocationY = 45;
		if(handler.isUp()) {
			if(handler.edgeY) {
				player.setY(player.getY()+7);
			}else {
				handler.map.move = false;
			}
		}
		if(handler.isDown()) {
			if(handler.edgeY) {
				player.setY(player.getY()-7);
			}else {
				handler.map.move = false;
			}
		}
		if(handler.isLeft()) {
			if(handler.edgeX) {
				player.setX(player.getX()+7);
			}else {
				handler.map.move = false;
			}
		}
		if(handler.isRight()) {
			if(handler.edgeX) {
				player.setX(player.getX()-7);
			}else {
				handler.map.move = false;
			}
		}
		if(movementAnim == 0) {
			 if(currentFrame*spriteLocationX < 470) {
				 imageTile = imageSlash.getSubimage(currentFrame*spriteLocationX, movementAnim*spriteLocationY, 45, 44);
				 player.imageTile = imageTile;
			 }
		}
		if(movementAnim == 1) {
			 if(currentFrame*spriteLocationX < 470) {
				 imageTile = imageSlash.getSubimage(currentFrame*spriteLocationX, movementAnim*spriteLocationY, 45, 43);
				 player.imageTile = imageTile;
			 }
		}
		if(movementAnim == 2) {
			 if(currentFrame*spriteLocationX < 470) {
				 imageTile = imageSlash.getSubimage(currentFrame*spriteLocationX, movementAnim*spriteLocationY, 45, 35);
				 player.imageTile = imageTile;
			 }
		}
		if(movementAnim == 3) {
			 if(currentFrame*spriteLocationX < 470) {
				 imageTile = imageSlash.getSubimage(currentFrame*spriteLocationX, movementAnim*spriteLocationY, 45, 48);
				 player.imageTile = imageTile;
			 }
		} 
		if(slashing) {
			if(currentFrame < 7) {
				if(timer == 1) {
					currentFrame++;
					timer = 0;
				}
				timer++;
			}else {
				currentFrame = 0;
				slashing = false;
				handler.map.move = true;
				handler.refresh();
			}
		}
	}
	public void Swim(int y) {
		int SIZEX = 0;
		int SIZEY = 0;
		if(currentFrame == 0) {
			SIZEX = 29;
			SIZEY = 25;
		}else{
			SIZEX = 29;
			SIZEY = 25;
		}
        ////
        //Takes the bufferedimage and creates a window with the x being the current frame times the sizeX
        //and the y being the currentAnim times the sizeY.//
        //The last two numbers are representative for the width and height of the sub image you're creating//
        if(y == 0) {
        	imageTile = imageSwim.getSubimage(currentFrame*SIZEX, y*SIZEY, 32, 26);	
        }else if(y == 1) {
        	imageTile = imageSwim.getSubimage(currentFrame*SIZEX, y*SIZEY + 5, 32, 26);
        }else if(y == 2){
        	imageTile = imageSwim.getSubimage(currentFrame*SIZEX, y*SIZEY, 32, 26);	
        }else if(y == 3) {
        	imageTile = imageSwim.getSubimage(currentFrame*SIZEX, y*SIZEY+5, 32, 26);	
        }
        
        player.imageTile = imageTile;
        
        if(swimming) {
			if(currentFrame < 7) {
				if(timer == 5) {
					currentFrame++;
					timer = 0;
				}
				timer++;
			}else {
				currentFrame = 0;
			}
		}
        
    }
}
