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

public class Layer3 extends GameObject{
	private BufferedImage image;
	public Handler handler;
	
	public Layer3() {
		this.setWidth(4000);
		this.setHeight(4000);
		this.setId(ID.Layer3);
		this.setImage("MapLayer3.png");
		loadImage();
	}
	public void loadImage() {
		this.image = this.getImage();
	}

	public void render(Graphics g) {
		g.drawImage(image,(int)this.getX()+8,(int)this.getY(),this.getWidth(),this.getHeight(),null);
	}
	@Override
	public void update() {
		
	}
	@Override
	public Rectangle getBounds() {
		return null;
	}

}