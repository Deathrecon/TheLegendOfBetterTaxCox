package com.deathrecon.tilemap;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.deathrecon.Enum.ID;

public abstract class Tile {
	private float x;
	private float y;
	private int xInd;
	private int yInd;
	private ID id;
	private int layer;
	private int height;
	private int width;

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
	public int getxInd() {
		return xInd;
	}
	public void setxInd(int xInd) {
		this.xInd = xInd;
	}
	public int getyInd() {
		return yInd;
	}
	public void setyInd(int yInd) {
		this.yInd = yInd;
	}

}
