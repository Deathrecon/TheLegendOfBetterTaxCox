package com.deathrecon.HUD;

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

public class Hearts extends HUD {
	//yeet
	private BufferedImage Heart1 ;
	private BufferedImage Heart2;
	private BufferedImage Heart3;
	public Player player;
	public Handler handler;
	File FH = new File("FullHeart.png");
	File HH = new File("HalfHeart.png");
	File EH = new File("EmptyHeart.png");
	int hp;
	
	public Hearts() {
		this.setId(ID.HUDHEART);
	}

	public void loadHearts() {
		this.setX(50);
		this.setY(50);
		this.setWidth(50);
		this.setHeight(getWidth());
		
		try {
			Heart1 = ImageIO.read(EH);
			Heart2 = ImageIO.read(EH);
			Heart3 = ImageIO.read(EH);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		

		if (player.HP >= 2) {
			try {
				Heart1 = ImageIO.read(FH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (player.HP >= 1) {
			try {
				Heart1 = ImageIO.read(HH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (player.HP == 0) {
			try {
				Heart1 = ImageIO.read(EH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if (player.HP >= 4) {
			try {
				Heart2 = ImageIO.read(FH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (player.HP >= 3) {
			try {
				Heart2 = ImageIO.read(HH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (player.HP >= 2) {
			try {
				Heart2 = ImageIO.read(EH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
		if (player.HP >= 6) {
			try {
				Heart3 = ImageIO.read(FH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (player.HP >= 5) {
			try {
				Heart3 = ImageIO.read(HH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (player.HP >= 4) {
			try {
				Heart3 = ImageIO.read(EH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		hp = player.HP;
		loadHearts();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Heart1, (int) getX(), (int) getY(), getWidth(), getHeight(), null);
		g.drawImage(Heart2, (int) getX() + getWidth(), (int) getY(), getWidth(), getHeight(), null);
		g.drawImage(Heart3, (int) getX() + (getWidth()+getWidth()), (int) getY(), getWidth(), getHeight(), null);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
}
