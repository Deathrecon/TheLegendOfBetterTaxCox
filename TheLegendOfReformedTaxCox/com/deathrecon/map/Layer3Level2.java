package com.deathrecon.map;

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

public class Layer3Level2 extends GameObject{
	private BufferedImage image;
	public Handler handler;
	
	public Layer3Level2() {
		this.setWidth(100);
		this.setHeight(100);
		this.setId(ID.Layer2);
		loadImage();
	}
	public void loadImage() {
			File file = new File("MapLayer2.png");
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void render(Graphics g) {
		g.drawImage(image,(int)this.getX()+8,(int)this.getY(),4000,4000,null);
	}
	@Override
	public void update() {
		
	}
	@Override
	public Rectangle getBounds() {
		return null;
	}

}