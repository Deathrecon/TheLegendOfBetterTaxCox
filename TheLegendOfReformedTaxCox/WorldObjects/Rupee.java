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

public class Rupee extends GameObject{
	BufferedImage image;
	File file;
	public Player player;
	float lastX = 0;
	float lastY = 0;
	float newX = 0;
	float newY = 0;
	public Handler handler;
	public BackgroundMove map;
	public boolean collisionPlaced = false;
	
	public Rupee(ID id, int x, int y, int width, int height, int layer,BackgroundMove map, Handler handler, TileHandler tileHandler) {
		this.setId(id);
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
		this.setWidth(width);
		this.setLayer(layer);
		this.setMap(map);
		this.setHandler(handler);
		this.setTileHandler(tileHandler);
		System.out.println("HELLO WORLD");
		LoadImage();

	}
	public void LoadImage() {
		map = this.getMap();
		handler = this.getHandler();
		file = new File("Rupo.png");
		try {
			image = ImageIO.read(file);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void update() {
		if(map.getX() != lastX) {
			newX = ((this.getX() + (map.getX()-lastX)));
			lastX = map.getX();
		}else {
			newX = (this.getX());
		}
		if(map.getY() != lastY) {
			newY = ((this.getY() + (map.getY()-lastY)));
			lastY = map.getY();
		}else {
			newY = (this.getY());
		}
		this.setY(newY);
		this.setX(newX);
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image,(int)this.getX(),(int)this.getY(),this.getWidth(),this.getHeight(),null);
		g.setColor(Color.RED);
		g.drawRect((int)this.getX(),(int)this.getY(), 30, 40);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)this.getX(),(int)this.getY(), 30, 40);
	}
}
