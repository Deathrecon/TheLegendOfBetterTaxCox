package com.deathrecon.handler;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.deathrecon.Enum.ID;
import com.deathrecon.game.GameObject;
import com.deathrecon.map.BackgroundMove;
import com.deathrecon.player.Player;
public class Handler {
		
		LinkedList<GameObject> object = new LinkedList<GameObject>();
		LinkedList<GameObject> entitiesLevel2 = new LinkedList<GameObject>();
		LinkedList<GameObject> enemiesLevel2 = new LinkedList<GameObject>();
		LinkedList<GameObject> entitiesLevel3 = new LinkedList<GameObject>();
		LinkedList<GameObject> enemiesLevel3 = new LinkedList<GameObject>();
		
		public boolean edgeX = false;
		public boolean edgeY = false;
		private boolean up = false;
		private boolean	down = false;
		private boolean	left = false;
		private boolean	right = false;
		private boolean space = false;
		public boolean Level2 = true;
		public boolean Level3 = false;
		public boolean changedLevel3 = false;
		public boolean changedLevel2 = false;
		public TileHandler tileHandler;
		public BackgroundMove map;
		public Player player;
		GameObject tempPlayer;
		GameObject tempLayer1;
		GameObject tempLayer2;
		GameObject tempLayer3;
		GameObject tempHUD;
		GameObject tempHearts;
		GameObject tempRupee;
		
		public boolean isUp() {
			return up;
		}

		public void setUp(boolean up) {
			this.up = up;
		}

		public boolean isDown() {
			return down;
		}

		public void setDown(boolean down) {
			this.down = down;
		}

		public boolean isLeft() {
			return left;
		}

		public void setLeft(boolean left) {
			this.left = left;
		}

		public boolean isRight() {
			return right;
		}

		public void setRight(boolean right) {
			this.right = right;
		}

		public void update() {
			GameObject temp;
			for(int i = 0; i < object.size(); i++)
			{
				temp = object.get(i);
				temp.update();
			}
			if(Level3) {
				changedLevel2 = false;
				if(changedLevel3 == false) {
					map.setImage("Level3.png");
					map.setHeight(3500);
					map.setY(-2450);
					map.layer3.setImage("Layer2-3.png");
					map.layer3.setHeight(3500);
					map.layer3.setY(map.getX());
					map.setVelY(0);
					map.loadImage();
					map.layer3.loadImage();
					changedLevel3 = true;
				}
				for(int i = 0; i < entitiesLevel3.size(); i++) {
					temp = entitiesLevel3.get(i);
					temp.update();
				}
			}else if(Level2){
				changedLevel3 = false;
				if(changedLevel2 == false) {
					map.setImage("Level2.png");
					map.setHeight(4000);
					map.setY(0);
					//player.setY(player.getY()+2);
					map.layer3.setImage("MapLayer3.png");
					map.layer3.setHeight(4000);
					map.layer3.setY(map.getX());
					map.setVelY(0);
					map.loadImage();
					map.layer3.loadImage();
					changedLevel2 = true;
					
				}
				for(int i = 0; i < entitiesLevel2.size(); i++) {
					temp = entitiesLevel2.get(i);
					temp.update();
				}
			}
		}
		
		public void render(Graphics g) {
			//Layer mapping and rendering//
			GameObject temp;;
			for(int i = 0; i < object.size(); i++)
			{
				temp = object.get(i);
				if(temp.getId() == ID.Player) {
					tempPlayer = temp;
				}else if(temp.getId() == ID.Layer1) {
					tempLayer1 = temp;
				}else if(temp.getId() == ID.Layer2) {
					tempLayer2 = temp;
				}else if(temp.getId() == ID.Layer3) {
					tempLayer3 = temp;
				}else if(temp.getId() == ID.HUDHEART){
					tempHearts = temp;
				}else if(temp.getId() == ID.HUDRUPEE){
					tempRupee = temp;
				}else if(temp.getId() == ID.HUD){
					tempHUD = temp;
				}else {
					temp.render(g);
				}
			}
			if(Level3) {
				for(int i = 0; i < entitiesLevel3.size();i++) {
					temp = entitiesLevel3.get(i);
					temp.render(g);
				}
			}else if(Level2) {
				for(int i = 0; i < entitiesLevel2.size();i++) {
					temp = entitiesLevel2.get(i);
					temp.render(g);
				}
			}
			//We orgranize the layers above the player depending on which layer he is on.//
			if(tempPlayer.getLayer() == 1) {
				tempPlayer.render(g);
				tempLayer1.render(g);
				tempLayer2.render(g);
				if(tileHandler.debug) {
					tempLayer3.render(g);
				}
				tempHearts.render(g);
				tempHUD.render(g);
				tempRupee.render(g);
			}else if(tempPlayer.getLayer() == 2) {
				tempLayer1.render(g);
				tempPlayer.render(g);
				tempLayer2.render(g);
				tempLayer3.render(g);
				tempHearts.render(g);
				tempHUD.render(g);
				tempRupee.render(g);
			}else if(tempPlayer.getLayer() == 3) {
				tempLayer1.render(g);
				tempLayer2.render(g);
				tempPlayer.render(g);
				tempLayer3.render(g);
				tempHearts.render(g);
				tempHUD.render(g);
				tempRupee.render(g);
			}
			////
		}
		public void updateEntities() {
			//Updating entities method//
			if(Level3) {
				for(int i = 0; i < entitiesLevel3.size(); i++) {
					GameObject temp = entitiesLevel3.get(i);
					temp.update();
				}
			}else if(Level2) {
				for(int i = 0; i < entitiesLevel2.size(); i++){
					GameObject temp = entitiesLevel2.get(i);
					temp.update();
				}
			}
			////
		}
		
		//Used to update enemy collisions and do damage if hit.//
		public void updateEnemies() {
			if(Level3) {
				for(int i = 0; i < enemiesLevel3.size(); i++) {
					GameObject temp = entitiesLevel3.get(i);
					temp.update();
					if(this.isSpace()) {
						if(this.isUp()) {
							if(temp.getBounds().intersects(player.getSlashUpBounds())) {
								if(temp.getHP() > 0) {
									temp.setHP(temp.getHP()-1);
								}
							}
						}
						if(this.isLeft()) {
							if(temp.getBounds().intersects(player.getSlashLeftBounds())) {
								if(temp.getHP() > 0) {
									temp.setHP(temp.getHP()-1);
								}
							}
						}
						if(this.isDown()) {
							if(temp.getBounds().intersects(player.getSlashDownBounds())) {
								if(temp.getHP() > 0) {
									temp.setHP(temp.getHP()-1);
								}
							}
						}
						if(this.isRight()) {
							if(temp.getBounds().intersects(player.getSlashRightBounds())) {
								if(temp.getHP() > 0) {
									temp.setHP(temp.getHP()-1);
								}
							}
						}
					}
				}
			}else if(Level2) {
				for(int i = 0; i < enemiesLevel2.size(); i++) {
					GameObject temp = entitiesLevel2.get(i);
					temp.update();
					if(this.isSpace()) {
						if(this.isUp()) {
							if(temp.getBounds().intersects(player.getSlashUpBounds())) {
								if(temp.getHP() > 0) {
									temp.setHP(temp.getHP()-1);
								}
							}
						}
						if(this.isLeft()) {
							if(temp.getBounds().intersects(player.getSlashLeftBounds())) {
								if(temp.getHP() > 0) {
									temp.setHP(temp.getHP()-1);
								}
							}
						}
						if(this.isDown()) {
							if(temp.getBounds().intersects(player.getSlashDownBounds())) {
								if(temp.getHP() > 0) {
									temp.setHP(temp.getHP()-1);
								}
							}
						}
						if(this.isRight()) {
							if(temp.getBounds().intersects(player.getSlashRightBounds())) {
								if(temp.getHP() > 0) {
									temp.setHP(temp.getHP()-1);
								}
							}
						}
					}
				}
			}
		}
		//////
		public void addObject(GameObject temp) {
			object.add(temp);
		}
		public void addObject(GameObject temp,int level) {
			if(level == 2) {
				if(temp.getId() == ID.Enemy || temp.getId() == ID.Chest || temp.getId() == ID.WorldObjectPot) {
					entitiesLevel2.add(temp);
					if(temp.getId() == ID.Enemy) {
						temp.setHandler(this);
						temp.setMap(map);
						enemiesLevel2.add(temp);
					}
				}
			}else if(level == 3) {
				if(temp.getId() == ID.Enemy || temp.getId() == ID.Chest || temp.getId() == ID.WorldObjectPot) {
					entitiesLevel3.add(temp);
					if(temp.getId() == ID.Enemy) {
						temp.setHandler(this);
						temp.setMap(map);
						enemiesLevel3.add(temp);
					}
				}
			}
		}
		
		public void removeObject(GameObject temp) {
			object.remove(temp);
		}

		public boolean isSpace() {
			return space;
		}

		public void setSpace(boolean space) {
			this.space = space;
		}

}
