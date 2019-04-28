package com.deathrecon.tilemap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.deathrecon.game.GameObject;

public class LayerTile extends Tile{
	
	public LayerTile() {
		this.setWidth(32);
		this.setHeight(26);
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
