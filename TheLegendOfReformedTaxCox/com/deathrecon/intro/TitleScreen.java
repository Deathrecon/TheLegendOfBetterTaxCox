package com.deathrecon.intro;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.deathrecon.game.GameObject;

public class TitleScreen extends GameObject{
	private BufferedImage image;
	
	public TitleScreen() {
		this.setWidth(100);
		this.setHeight(100);
		loadImage();
	}
	public void loadImage() {
			System.out.println("REPEATING?");
			File file = new File("titlescreen.png");
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void render(Graphics g) {
		g.drawImage(image,(int)this.getX(),(int)this.getY(),1920,1040,null);
		Font titleFont = new Font("TimesRoman", Font.BOLD,75);
		g.setFont(titleFont);
		g.setColor(Color.GREEN);
		g.drawString("PRESS ENTER", 1150, 750);
	}
	@Override
	public void update() {
		float newX = (this.getX() + this.getVelX());
		float newY = (this.getY() + this.getVelY());
		if(newY < 3000 && newY > -3000) {
			this.setY(newY);
		}
		if(newX < 3000 && newX > -3000) {
			this.setX(newX);
		}
	}
	@Override
	public Rectangle getBounds() {
		return null;
	}

}