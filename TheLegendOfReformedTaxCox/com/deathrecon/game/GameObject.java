package com.deathrecon.game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.deathrecon.Enum.ID;
import com.deathrecon.handler.Handler;
import com.deathrecon.handler.TileHandler;
import com.deathrecon.map.BackgroundMove;

public abstract class GameObject {
	private float x;
	private float y;
	private float velX;
	private float velY;
	private int layer;
	private ID id;
	private int height;
	private int width;
	private int HP;
	private boolean wait;
	private Handler handler;
	private TileHandler tileHandler;
	private BackgroundMove map;
	private BufferedImage image;
	public abstract void update();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public float getX() {
		return this.x;
	}
	
	public void setX(float newX) {
		this.x=newX;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	public ID getId() {
		return id;
	}
	public void setId(ID id) {
		this.id = id;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public int getHP() {
		return HP;
	}
	public void setHP(int playerHP) {
		HP = playerHP;
	}
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public BackgroundMove getMap() {
		return map;
	}
	public void setMap(BackgroundMove map) {
		this.map = map;
	}
	public boolean isWaiting() {
		return wait;
	}
	public void setWait(boolean wait) {
		this.wait = wait;
	}
	public TileHandler getTileHandler() {
		return tileHandler;
	}
	public void setTileHandler(TileHandler tileHandler) {
		this.tileHandler = tileHandler;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(String fileName) {
		File file = new File(fileName);
		try {
			this.image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
