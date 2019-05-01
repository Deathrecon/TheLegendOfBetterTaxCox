package com.deathrecon.HUD;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.deathrecon.Enum.ID;
import com.deathrecon.player.Player;

public class Rupees extends HUD{
	//YeEt
	private BufferedImage image;
	public Player player;
	File file = new File("Rupo.png");
	
	public Rupees() {
		this.setId(ID.HUDRUPEE);
		this.setX(1600);
		this.setY(950);
		this.setWidth(50);
		this.setHeight(getWidth());
		loadRups();
		
	}
	
	public void loadRups() {
		
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, (int) getX(), (int) getY(), getWidth(), getHeight()+6, null);
	
	}

	@Override
	public Rectangle getBounds() {
		
		return null;
	}
	
}
