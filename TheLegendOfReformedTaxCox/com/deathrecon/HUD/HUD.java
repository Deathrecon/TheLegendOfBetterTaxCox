package com.deathrecon.HUD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.deathrecon.Enum.ID;
import com.deathrecon.game.GameObject;
import com.deathrecon.handler.Handler;
import com.deathrecon.player.Player;

public class HUD extends GameObject {
	public Player player; 
	public Handler handler;
	public Hearts hearts;
	File file = new File("CurrencyBar.png");
	File wpnsl = new File("WeaponSlot.png");
	File sword = new File("Sword1.png");
	File shield = new File("Shield1.png");
	
	BufferedImage image;
	BufferedImage slot;
	BufferedImage Sword;
	BufferedImage Shield;
	
	
	public HUD() {
		this.setId(ID.HUD);
		this.setX(1500);
		this.setY(525);
		this.setWidth(500);
		this.setHeight(getWidth());
		loadHud();
	}
	
	
	
public void loadHud() {
		
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			slot = ImageIO.read(wpnsl);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
			Sword = ImageIO.read(sword);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
			 Shield = ImageIO.read(shield);
			 
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	





	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		
		
		
		// TODO Auto-generated method stub
		//g.drawImage(image, (int) getX(), (int) getY(), getWidth(), getHeight(), null);
		
		
		//nums
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica", Font.ITALIC,50));
		g.drawString(Integer.toString(player.RupCount), (int) getX()+175, 995);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Helvetica", Font.ITALIC,50));
		g.drawString(Integer.toString(player.RupCount), (int) getX()+171, 993);
		
		//Slots
		
		//1
		g.drawImage(slot, (int) getX()+ 100, 50 , 100, 100, null);
		g.drawImage(Sword, (int) getX()+ 115, 50+15 , 70, 70, null);
		
		//2
		g.drawImage(slot, (int) getX() + 250, 50 , 100, 100, null);
		g.drawImage(Shield, (int) getX() + 265, 65 , 70, 70, null);
	}

	@Override
	public Rectangle getBounds() {
		
		return null;
	}
}
