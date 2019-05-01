package com.deathrecon.handler;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.deathrecon.Enum.ID;
import com.deathrecon.enemy.Octorok;
import com.deathrecon.game.GameObject;
import com.deathrecon.map.BackgroundMove;
import com.deathrecon.player.Player;
public class Handler {
		
		LinkedList<GameObject> object = new LinkedList<GameObject>();
		LinkedList<GameObject> entitiesLinkHouse = new LinkedList<GameObject>();
		LinkedList<GameObject> entitiesLevel1 = new LinkedList<GameObject>();
		LinkedList<GameObject> enemiesLevel1 = new LinkedList<GameObject>();
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
		public boolean linkHouse = true;
		public boolean Level1 = false;
		public boolean Level2 = false;
		public boolean Level3 = false;
		public boolean changedLevel3 = false;
		public boolean changedLevel2 = false;
		public boolean changedLevel1 = false;
		public boolean changedLinkHouse = false;
		public TileHandler tileHandler;
		public BackgroundMove map;
		public Player player;
		public GameObject testEnemy;
		GameObject tempPlayer;
		GameObject tempLayer1;
		GameObject tempLayer2;
		GameObject tempLayer3;
		GameObject tempHUD;
		GameObject tempHearts;
		GameObject tempRupee;
		GameObject tempNPC;
		
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
				changedLevel2 = false;
				changedLevel1 = false;
				for(int i = 0; i < entitiesLevel3.size(); i++) {
					temp = entitiesLevel3.get(i);
					if(temp.getId() == ID.Rupee) {
						if(player.getBounds().intersects(temp.getBounds())) {
							System.out.println("HELLO?");
							temp.setX(-10000000);
							player.RupCount += 10;
						}
					}
					temp.update();
				}
			}else if(Level2){
				if(changedLevel2 == false) {
					map.setImage("Level2.png");
					map.setHeight(4000);
					if(changedLevel1) {
						map.setY(-2130);
					}else {
						map.setY(0);
					}
					//player.setY(player.getY()+2);
					map.layer3.setImage("MapLayer3.png");
					map.layer3.setHeight(4000);
					map.layer3.setY(map.getX());
					map.setVelY(0);
					map.loadImage();
					map.layer3.loadImage();
					changedLevel2 = true;
				}
				changedLevel3 = false;
				changedLevel1 = false;
				changedLinkHouse = false;
				for(int i = 0; i < entitiesLevel2.size(); i++) {
					temp = entitiesLevel2.get(i);
					if(temp.getId() == ID.Rupee) {
						if(player.getBounds().intersects(temp.getBounds())) {
							System.out.println("HELLO?");
							temp.setX(-10000000);
							player.RupCount += 10;
						}
					}
					temp.update();
				}
			}else if(Level1) {
				if(changedLevel1 == false) {
					map.setImage("Level1.png");
					map.setHeight(4000);
					map.setWidth(4000);
					if(changedLinkHouse) {
						map.setY(-1360);
						map.setX(-1670);
					}else {
						map.setY(0);
					}
					//player.setY(player.getY()+2);
					map.layer3.setImage("Layer2Level1.png");
					map.layer3.setHeight(4000);
					map.layer3.setY(map.getX());
					map.setVelY(0);
					map.loadImage();
					map.layer3.loadImage();
					changedLevel1 = true;
				}
				changedLevel2 = false;
				changedLevel3 = false;
				changedLinkHouse = false;
				for(int i = 0; i < entitiesLevel1.size(); i++) {
					temp = entitiesLevel1.get(i);
					if(temp.getId() == ID.Rupee) {
						if(player.getBounds().intersects(temp.getBounds())) {
							System.out.println("HELLO?");
							temp.setX(-10000000);
							player.RupCount += 10;
						}
					}
					temp.update();
				}
			}else if(linkHouse) {
				if(changedLinkHouse == false) {
					player.setLayer(2);
					player.setY(player.getY()-50);
					map.setImage("linkHouse.png");
					map.setHeight(3500);
					map.setWidth(6500);
					map.setY(-1400);
					map.setX(-2290);
					map.setVelY(0);
					map.loadImage();
					map.layer3.loadImage();
					changedLinkHouse = true;
				}
				changedLevel1 = false;
				changedLevel2 = false;
				changedLevel3 = false;
				for(int i = 0; i < entitiesLinkHouse.size(); i++) {
					temp = entitiesLinkHouse.get(i);
					if(temp.getId() == ID.Rupee) {
						if(player.getBounds().intersects(temp.getBounds())) {
							System.out.println("HELLO?");
							temp.setX(-10000000);
							player.RupCount += 10;
						}
					}
					temp.update();
				}
			}
			
		}
		
		public void render(Graphics g) {
			//Layer mapping and rendering//
			GameObject temp;
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
			}else if(Level1) {
				for(int i = 0; i < entitiesLevel1.size();i++) {
					temp = entitiesLevel1.get(i);
					temp.render(g);
				}
			}else if(linkHouse) {
				for(int i = 0; i < entitiesLinkHouse.size();i++) {
					temp = entitiesLinkHouse.get(i);
					temp.render(g);
				}
			}
			//We orgranize the layers above the player depending on which layer he is on.//
			if(tempPlayer.getLayer() == 1) {
				tempPlayer.render(g);
				if(Level2) {
					tempLayer1.render(g);
					tempLayer2.render(g);
				}
				if(Level2 || Level3) {
					tempLayer3.render(g);
				}
				tempHearts.render(g);
				tempHUD.render(g);
				tempRupee.render(g);
			}else if(tempPlayer.getLayer() == 2) {
				if(Level2) {
					tempLayer1.render(g);
				}
				tempPlayer.render(g);
				if(Level2) {
					tempLayer2.render(g);
				}
				if(Level2 || Level3){
					tempLayer3.render(g);
				}
				tempHearts.render(g);
				tempHUD.render(g);
				tempRupee.render(g);
			}else if(tempPlayer.getLayer() == 3) {
				if(Level2) {
					tempLayer1.render(g);
					tempLayer2.render(g);
				}
				tempPlayer.render(g);
				if(Level2 || Level3) {
					tempLayer3.render(g);
				}
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
			}else if(Level1) {
				for(int i = 0; i < entitiesLevel1.size();i++) {
					GameObject temp = entitiesLevel1.get(i);
					temp.update();
				}
			}else if(linkHouse) {
				for(int i = 0; i < entitiesLinkHouse.size();i++) {
					GameObject temp = entitiesLinkHouse.get(i);
					temp.update();
				}
			}
			////
		}
		
		//Used to update enemy collisions and do damage if hit.//
		public void updateEnemies() {
			LinkedList<GameObject> currentList = new LinkedList<GameObject>();
			if(Level3) {
				currentList = enemiesLevel3;
			}else if(Level2) {
				currentList = enemiesLevel2;
			}else if(Level1) {
				currentList = enemiesLevel1;
			}
			for(int i = 0; i < currentList.size(); i++) {
				GameObject temp = currentList.get(i);
				if(this.isSpace()) {
					if(player.movementAnim == 0) {
						if(temp.getBounds().intersects(player.getSlashUpBounds())) {
							if(temp.getHP() > 0) {
								if(temp.isHit() == false) {
									temp.setHP(temp.getHP()-1);
									temp.setHit(true);
								}
							}
						}
					}
					if(player.movementAnim == 3) {
						if(temp.getBounds().intersects(player.getSlashLeftBounds())) {
							if(temp.getHP() > 0) {
								if(temp.isHit() == false) {
									temp.setHP(temp.getHP()-1);
									temp.setHit(true);
								}
							}
						}
					}
					if(player.movementAnim == 2) {
						if(temp.getBounds().intersects(player.getSlashDownBounds())) {
							if(temp.getHP() > 0) {
								if(temp.isHit() == false) {
									temp.setHP(temp.getHP()-1);
									temp.setHit(true);
								}
							}
						}
					}
					if(player.movementAnim == 1) {
						if(temp.getBounds().intersects(player.getSlashRightBounds())) {
							if(temp.getHP() > 0) {
								if(temp.isHit() == false) {
									temp.setHP(temp.getHP()-1);
									temp.setHit(true);
									testEnemy = temp;
								}
							}
						}
					}
				}
			}
				
		}
		//////
		
		public void refresh() {
			for(int i = 0; i < enemiesLevel1.size(); i++) {
				GameObject temp = enemiesLevel1.get(i);
				temp.setHit(false);
			}
			for(int i = 0; i < enemiesLevel2.size(); i++) {
				GameObject temp = enemiesLevel2.get(i);
				temp.setHit(false);
			}
			for(int i = 0; i < enemiesLevel3.size(); i++) {
				GameObject temp = enemiesLevel3.get(i);
				temp.setHit(false);
			}
		}
		
		public void addObject(GameObject temp) {
			object.add(temp);
		}
		public void addObject(GameObject temp,int level) {
			if(level == 0) {
				if(temp.getId() == ID.Enemy || temp.getId() == ID.Chest || temp.getId() == ID.Rupee || temp.getId() == ID.WorldObjectPot || temp.getId() == ID.NPC) {
					entitiesLevel1.add(temp);
					temp.setLevel(level);
					if(temp.getId() == ID.Enemy) {
						temp.setHandler(this);
						temp.setMap(map);
						enemiesLevel1.add(temp);
					}
				}
			}else if(level == 1) {
				if(temp.getId() == ID.Enemy || temp.getId() == ID.Chest || temp.getId() == ID.Rupee || temp.getId() == ID.WorldObjectPot || temp.getId() == ID.NPC) {
					entitiesLevel2.add(temp);
					temp.setLevel(level);
					if(temp.getId() == ID.Enemy) {
						temp.setHandler(this);
						temp.setMap(map);
						enemiesLevel2.add(temp);
					}
				}
			}else if(level == 2) {
				if(temp.getId() == ID.Enemy || temp.getId() == ID.Chest || temp.getId() == ID.Rupee || temp.getId() == ID.WorldObjectPot) {
					entitiesLevel3.add(temp);
					temp.setLevel(level);
					if(temp.getId() == ID.Enemy) {
						temp.setHandler(this);
						temp.setMap(map);
						enemiesLevel3.add(temp);
					}
				}
			}else if(level == 3) {
				if(temp.getId() == ID.Chest || temp.getId() == ID.Rupee || temp.getId() == ID.WorldObjectPot) {
					entitiesLinkHouse.add(temp);
					temp.setLevel(level);
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
