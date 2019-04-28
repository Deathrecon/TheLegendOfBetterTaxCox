package WorldObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.deathrecon.Enum.ID;
import com.deathrecon.game.GameObject;
import com.deathrecon.handler.Handler;
import com.deathrecon.map.BackgroundMove;
import com.deathrecon.player.Player;

public class World extends GameObject {
	BufferedImage image;
	BufferedImage Vase;
	public Handler handler;
	File file;
	File vase;
	public int vaseHealth = 2;
	public Player player;
	float lastX = 0;
	float lastY = 0;
	float newX = 500;
	float newY = 500;
	public BackgroundMove map;
	boolean pickup = false;
	boolean broken = false;
	boolean inside = false;
	private int count = 0;
	
	
	public World() {
		
		//this.setId(ID.WorldObject);
		this.setX(900);
		this.setY(300);
		this.setHeight(50);
		this.setWidth(getHeight());
		LoadPot();

	}
	
	
	public void LoadPot() {
		vase = new File("Vase.png");
		file = new File("Rupo.png");
			if(vaseHealth == 2) {
				broken = false;
			try {
				Vase = ImageIO.read(vase);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else if(vaseHealth == 1) {
			broken = false;
		}else if(vaseHealth <= 0) {
			broken = true;
			
			if(player.getX() >= this.getX()-this.getWidth() && player.getX() <= this.getX() + 53 ) {
		
				if(player.getY() >= this.getY()-this.getHeight() && player.getY() <= this.getY()+this.getWidth()) {
				
					if(pickup == false) {
					pickup = true;
					player.RupCount = player.RupCount+3;
						
					}
				
				
				
				}
				
				
			}
			
			
			if(broken && pickup == false) {
					try {
					Vase = ImageIO.read(file);
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(broken && pickup) {
				Vase = null;
			}
		}
	}
	
	

	
	
	
	@Override
	public void update() {
		LoadPot();
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
		
	}

	@Override
	public void render(Graphics g) {

		if(vaseHealth > 0) {
			g.drawImage(Vase,(int)getX(),(int)getY(),getWidth(),getHeight(),null);
		}else if(vaseHealth <= 0) {
			for(int index = 0; index < 3; index++) {
				g.drawImage(Vase,(int)getX()+(20 * index),(int)getY(),30,30,null);
			}
			
		}
		
		g.setColor(Color.RED);
		g.drawRect((int)this.getX()-5,(int)this.getY()+25,58,35);
		}
		
		
	

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)this.getX()-5,(int)this.getY()+25,58,35);
	}
}

	