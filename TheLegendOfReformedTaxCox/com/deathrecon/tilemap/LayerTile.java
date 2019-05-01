package com.deathrecon.tilemap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.deathrecon.Enum.ID;
import com.deathrecon.game.GameObject;

public class LayerTile extends Tile{
	
	public LayerTile() {
		this.setWidth(24);
		this.setHeight(36);
	}
	public LayerTile(ID id, int x, int y, int width, int height, int layer) {
		this.setId(id);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setLayer(layer);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int)this.getX(), (int)this.getY(), this.getWidth(), this.getHeight());
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)this.getX(),(int)this.getY(),this.getWidth(),this.getHeight());
	}

}
