package WorldObjects;

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
import com.deathrecon.handler.TileHandler;
import com.deathrecon.map.BackgroundMove;
import com.deathrecon.player.Player;

public class Pot extends GameObject {
	BufferedImage image;
	BufferedImage Vase;
	File file;
	File vase;
	public int vaseHealth = 2;
	public Player player;
	float lastX = 0;
	float lastY = 0;
	float newX = 500;
	float newY = 500;
	public Handler handler;
	public BackgroundMove map;
	public boolean rupeeDropped = false;
	public boolean rupeePickeUp = false;
	public boolean collisionPlaced = false;
	public boolean hit = false;
	
	public Pot() {
		this.setId(ID.Enemy);
		this.setX(900);
		this.setY(300);
		this.setHeight(50);
		this.setWidth(getHeight());
		LoadPot();

	}
	public Pot(ID id, int x, int y, int width, int height, int layer,BackgroundMove map, Handler handler, TileHandler tileHandler) {
		
		this.setId(id);
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
		this.setWidth(width);
		this.setLayer(layer);
		this.setMap(map);
		this.setHandler(handler);
		this.setTileHandler(tileHandler);
		LoadPot();

	}
	
	
	public void LoadPot() {
		map = this.getMap();
		handler = this.getHandler();
		vase = new File("Vase.png");
		if(vaseHealth > 0) {
			try {
				Vase = ImageIO.read(vase);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		
		
	}
	
	

	
	
	
	@Override
	public void update() {
		if(collisionPlaced == false) {
			this.getTileHandler().addTile(ID.CollisionTile,(int)this.getX(),(int)this.getY(),this.getWidth(),this.getHeight(),this.getLayer());
			collisionPlaced = true;
		}
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
		if(handler.player.hasSword) {
			if(handler.isSpace()) {
				if(this.getBounds().intersects(handler.player.getSlashDownBounds()) || this.getBounds().intersects(handler.player.getSlashUpBounds()) || this.getBounds().intersects(handler.player.getSlashLeftBounds()) || this.getBounds().intersects(handler.player.getSlashRightBounds())){
					hit = true;
					if(hit) {
						vaseHealth--;
						hit = false;
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(this.vaseHealth > 0) {
		g.drawImage(Vase,(int)this.getX(),(int)this.getY(),50,50,null);
	}else {
		if(rupeeDropped == false) {
			handler.addObject(new Rupee(ID.Rupee,(int)this.getX() - (int)map.getX(),(int)this.getY() - (int)map.getY(),30,40,this.getLayer(),this.getMap(),this.getHandler(),this.getTileHandler()), this.getLevel());
			rupeeDropped = true;
			
		}
	}}
		
		
		
		
	

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)this.getX()-15,(int)this.getY()-25, 60, 55);
	}
}

