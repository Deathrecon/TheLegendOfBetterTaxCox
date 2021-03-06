package com.deathrecon.handler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import com.deathrecon.Enum.ID;
import com.deathrecon.Item.Chest;
import com.deathrecon.NPC.NPC;
import com.deathrecon.NPC.NPC2;
import com.deathrecon.NPC.NPC3;
import com.deathrecon.enemy.Octorok;
import com.deathrecon.enemy.Soldier;
import com.deathrecon.game.GameObject;
import com.deathrecon.map.BackgroundMove;
import com.deathrecon.player.Player;
import com.deathrecon.tilemap.LayerTile;
import com.deathrecon.tilemap.Tile;
import WorldObjects.Pot;
import WorldObjects.World;
/**
 * Handler Class to Manage Various Different Types Of Collision Tiles and Distinguish them.
 * Also serves as a real-time editor that can save entities and tiles to the map files.
 * 
 * @author Jordan
 *
 */
public class TileHandler implements MouseListener{
		
	//Level Tile Arrays
		LinkedList<Tile> level1Tiles = new LinkedList<Tile>();
		LinkedList<Tile> level2Tiles = new LinkedList<Tile>();
		LinkedList<Tile> level3Tiles = new LinkedList<Tile>();
		LinkedList<Tile> linkHouseTiles = new LinkedList<Tile>();
		LinkedList<Tile> currentLevelTiles = new LinkedList<Tile>();
		LinkedList<GameObject> currentEnemies = new LinkedList<GameObject>();
		LinkedList<GameObject> currentEntities = new LinkedList<GameObject>();
		public int mouseCount = 0;
		public int xMousePos = 0;
		public int yMousePos = 0;
		public int layer = 1;
		public int numLevels = 4;
		public int id = 4;
		public int switchTimer = 0;
		public boolean rectanglePlaced = false;
		public boolean entityPlaced = false;
		public boolean mousePressed = false;
		public boolean debug = false;
		public boolean entityMode = false;
		public boolean tileMode = true;
		public BackgroundMove back;
		public Player player;
		public Handler handler;
		public LayerTile currentTile;
		public Chest currentChest;
		public Octorok currentOcto;
		public NPC currentNPC;
		public NPC2 currentNPC2;
		public NPC3 currentNPC3;
		public Pot currentPot;
		public Soldier currentSoldier;
		public boolean level1Loaded = false;
		public boolean level2Loaded = false;
		public boolean level3Loaded = false;
		public boolean linkHouseLoaded = false;
		public boolean upBlocked = false;
		public boolean downBlocked = false;
		public boolean leftBlocked = false;
		public boolean rightBlocked = false;
		public boolean edgeX = false;
		public boolean edgeY = false;
		public World w;
		File linkMapFile = new File("LinkHouseCollisionMap.txt");
		File linkEntityFile = new File("LinkHouseEntityMap.txt");
		File level1MapFile = new File("Level1CollisionMap.txt");
		File level1EntityFile = new File("Level1EntityMap.txt");
		File level2MapFile = new File("Level2CollisionMap.txt");
		File level2EntityFile = new File("Level2EntityMap.txt");
		File level3MapFile = new File("Level3CollisionMap.txt");
		File level3EntityFile = new File("Level3EntityMap.txt");
		
		public TileHandler(Handler handler,BackgroundMove back) {
			this.handler = handler;
			this.back = back;
			initTiles();
		}
		
		/**
		 * Updates the current Level and all the tiles within and continuosly check for player collisions with them.
		 * @param String
		 */
		public void update() {
			//Edge Updates and Current Tile Update
			edgeY = handler.edgeY;
			edgeX = handler.edgeX;
			if(handler.Level3) {
				currentLevelTiles = level3Tiles;
			}else if(handler.Level2) {
				currentLevelTiles = level2Tiles;
			}else if(handler.Level1) {
				currentLevelTiles = level1Tiles;
				//System.out.println("updated");
			}else if(handler.linkHouse) {
				handler.map.setY(-1350);
				currentLevelTiles = linkHouseTiles;
			}
			///////
			for(int i = 0; i < currentLevelTiles.size(); i++)
			{
				//Initial update of tile
				Tile temp = currentLevelTiles.get(i);
				temp.setX(back.getX() + temp.getxInd());
				temp.setY(back.getY()+ temp.getyInd());
				temp.update();
				/////
				if(temp.getId() == ID.WaterTile) {
					if(player.getBounds().intersects(temp.getBounds())) {
						handler.player.player.swimming = true;
					}
				}
				//Begin Collision Test//
				//If player intersects the object
				if(player.getBounds().intersects(temp.getBounds())) {
					//If layers are equal
					if(player.getLayer() == temp.getLayer()) {
						//If object equals a collision tile
						if(temp.getId() == ID.CollisionTile) {
							//Direction testing through handler and setting player Y/X opposite to what it was
							//in this frame//
							//These comments will be the same throughout up and down//
							if(handler.isUp()) {
								if(edgeY) {
									//Edge Y is when the player is at max height and needs velocity//
									upBlocked = true;
									player.setY(player.getY()+7);
									////
								}else {
									//If edgeY is false the background needs to move and object need to be
									//updated//
									upBlocked = true;
									back.move = false;
									back.setY(back.getY()-7);
									temp.setX(back.getX() + temp.getxInd());
									temp.setY(back.getY()+ temp.getyInd());
									temp.update();
									handler.updateEntities();
									/////
								}
							}
							if(handler.isDown()) {
								if(edgeY) {
									downBlocked = true;
									player.setY(player.getY()-7);
								}else {
									downBlocked = true;
									back.setY(back.getY()+7);
									back.move = false;
									temp.setX(back.getX() + temp.getxInd());
									temp.setY(back.getY()+ temp.getyInd());
									temp.update();
									handler.updateEntities();
								}	
							}
							if(handler.isLeft()) {
								//Left must be tested for up and down so diagonal collision breaches can not occur//
								//(This applies for Right as well)//
								if(upBlocked == false && downBlocked == false) {
									//Same as up and down...//
									if(edgeX) {
										leftBlocked = true;
										player.setX(player.getX()+7);
									}else {
										leftBlocked = true;
										back.setX(back.getX()-7);
										back.move = false;
										temp.setX(back.getX() + temp.getxInd());
										temp.setY(back.getY()+ temp.getyInd());
										temp.update();
										handler.updateEntities();
									}
									/////
								}else {
									Rectangle tempRect = new Rectangle((int)player.getX()-15,(int)player.getY()+25,1,35);
									if(tempRect.intersects(temp.getBounds())) {
										if(edgeX) {
											leftBlocked = true;
											player.setX(player.getX()+7);
										}else {
											leftBlocked = true;
											back.setX(back.getX()-7);
											back.move = false;
											temp.setX(back.getX() + temp.getxInd());
											temp.setY(back.getY()+ temp.getyInd());
											temp.update();
											handler.updateEntities();
										}
									}
								}
							}
							if(handler.isRight()) {
								if(upBlocked == false && downBlocked == false) {
									if(edgeX) {
										rightBlocked = true;
										player.setX(player.getX()-7);
									}else {
										rightBlocked = true;
										back.setX(back.getX()+7);
										back.move = false;
										temp.setX(back.getX() + temp.getxInd());
										temp.setY(back.getY()+ temp.getyInd());
										temp.update();
										handler.updateEntities();
									}
								}else {
									Rectangle tempRect = new Rectangle((int)player.getX()+60,(int)player.getY()+25,1,35);
									if(tempRect.intersects(temp.getBounds())) {
										if(edgeX) {
											rightBlocked = true;
											player.setX(player.getX()-14);
										}else {
											rightBlocked = true;
											back.setX(back.getX()+7);
											back.move = false;
											temp.setX(back.getX() + temp.getxInd());
											temp.setY(back.getY()+ temp.getyInd());
											temp.update();
											handler.updateEntities();
										}
									}
								}
							}
						}else if(temp.getId() == ID.EventTile) {
							if(handler.linkHouse) {
								handler.linkHouse = false;
								handler.Level1 = true;
								handler.update();

							}else if(handler.Level1) {
								handler.linkHouse = true;
								handler.Level1 = false;
								handler.update();
							}
						}
						//Test for layer switch. (All layer switches are on layer 0)//
					}else if(temp.getLayer() == 0){
						//Check layerswitch ID and adjusts player layer accordingly.//
						if(temp.getId() == ID.Layer1Switch) {
							player.setLayer(1);
						}else if(temp.getId() == ID.Layer2Switch) {
							player.setLayer(2);
						}else if(temp.getId() == ID.Layer3Switch) {
							player.setLayer(3);
						}
						/////
					}
				}else {
					//Block handling.//
					//This part is the aftermath of reversing the direction.//
					//Because we reverse the direction we need to make sure they don't move that way again//
					//So this tests to see if they are pressing a different direction than the one that is blocked//
					//And if they are, and have let go of the blocked direction//
					//it will go that direction instead of the blocked one and reset the boolean//
					if(upBlocked) {
						if(handler.isUp() == false){
							if(handler.isDown() || handler.isLeft() || handler.isRight()) {
								if(edgeY == false) {
									back.move = true;
								}
								upBlocked = false;
							}
						}else {
							//Side rectangle to check if player is still colliding with rectangle.//
							Rectangle tempRect = new Rectangle((int)player.getX()-1,(int)player.getY()+15,50,1);
							if(!tempRect.intersects(temp.getBounds())) {
								if(edgeY == false) {
									back.move = true;
								}
								upBlocked = false;
							}
						}
					}
					if(downBlocked) {
						if(handler.isDown() == false) {
							if(handler.isUp() || handler.isLeft() || handler.isRight()) {
								if(edgeY == false) {
									back.move = true;
								}
								downBlocked = false;
							}
						}else {
							//Side rectangle to check if player is still colliding with rectangle.//
							Rectangle tempRect = new Rectangle((int)player.getX()-1,(int)player.getY()+70,50,1);
							if(!tempRect.intersects(temp.getBounds())) {
								if(edgeY == false) {
									back.move = true;
								}
								downBlocked = false;
							}
						}
					}
					if(leftBlocked) {
						if(handler.isLeft() == false) {
							if(handler.isUp() || handler.isDown() || handler.isRight()) {
								if(edgeX == false) {
									back.move = true;
								}
								leftBlocked = false;
							}
						}else {
							//Side rectangle to check if player is still colliding with rectangle.//
							Rectangle tempRect = new Rectangle((int)player.getX()-15,(int)player.getY()+25,1,35);
							if(!tempRect.intersects(temp.getBounds())) {
								if(edgeX == false) {
									back.move = true;
								}
								leftBlocked = false;
							}
						}
					}
					//Right is a bit more difficult due to the fact that the way I coded the keylistener//
					//Allows you to override a left direction command with a right direction command but not vise versa//
					//Therefore, I have to do an extra check for the directions and change position based on that//
					if(rightBlocked) {
						if(handler.isRight()) {
							if(handler.isUp() || handler.isDown() || handler.isLeft()) {
								if(handler.isLeft()) {
									handler.setLeft(false);
								}
								if(handler.isUp()) {
									if(edgeX) {
										player.setX(player.getX()-7);
									}else {
										back.setX(back.getX()+7);
									}
								}
								if(handler.isDown()) {
									if(edgeX) {
										player.setX(player.getX()-7);
									}else {
										back.setX(back.getX()+7);
									}
								}
								handler.setRight(false);
								if(edgeX == false) {
									back.move = true;
								}
								rightBlocked = false;
							}
						}else {
							//Side rectangle to check if player is still colliding with rectangle.//
							Rectangle tempRect = new Rectangle((int)player.getX()+60,(int)player.getY()+25,1,35);
							if(!tempRect.intersects(temp.getBounds())) {
								handler.setRight(false);
								if(edgeX == false) {
									back.move = true;
								}
								rightBlocked = false;
							}
						}
					}
				}
				//Level check for entity update on collisions//
				//Not near as much code as we can control enemy movements.//
				if(handler.Level3) {
					currentEnemies = handler.enemiesLevel3;
					currentEntities = handler.entitiesLevel3;
				}else if(handler.Level2) {
					currentEnemies = handler.enemiesLevel2;
					currentEntities = handler.entitiesLevel2;
				}else if(handler.Level1) {
					currentEnemies = handler.enemiesLevel1;
					currentEntities = handler.entitiesLevel1;
				}
				for(int j = 0; j < currentEnemies.size(); j++) {
					GameObject tempEnemy = currentEnemies.get(j);
					if(tempEnemy.getBounds().intersects(temp.getBounds())) {
						tempEnemy.setWait(true);
						if(tempEnemy.getVelX() == 7) {
							tempEnemy.setBlockedDecision(2);
							tempEnemy.setVelX(0);
							tempEnemy.setX(tempEnemy.getX() - 7);
						}else if(tempEnemy.getVelX() == -7) {
							tempEnemy.setBlockedDecision(3);
							tempEnemy.setVelX(0);
							tempEnemy.setX(tempEnemy.getX() + 7);
						}
						if(tempEnemy.getVelY() == 7) {
							tempEnemy.setBlockedDecision(0);
							tempEnemy.setVelY(0);
							tempEnemy.setY(tempEnemy.getY() - 7);
						}else if(tempEnemy.getVelY() == -7) {
							tempEnemy.setBlockedDecision(1);
							tempEnemy.setVelY(0);
							tempEnemy.setY(tempEnemy.getY() + 7);
						}
					}
				}
				for( int j = 0; j < currentEntities.size(); j++) {
					GameObject tempEntity = currentEntities.get(j);
					if(tempEntity.getBounds().intersects(temp.getBounds())) {
						tempEntity.setWait(true);
						if(tempEntity.getVelX() == 7) {
							tempEntity.setBlockedDecision(2);
							tempEntity.setVelX(0);
							tempEntity.setX(tempEntity.getX() - 7);
						}else if(tempEntity.getVelX() == -7) {
							tempEntity.setBlockedDecision(3);
							tempEntity.setVelX(0);
							tempEntity.setX(tempEntity.getX() + 7);
						}
						if(tempEntity.getVelY() == 7) {
							tempEntity.setBlockedDecision(0);
							tempEntity.setVelY(0);
							tempEntity.setY(tempEntity.getY() - 7);
						}else if(tempEntity.getVelY() == -7) {
							tempEntity.setBlockedDecision(1);
							tempEntity.setVelY(0);
							tempEntity.setY(tempEntity.getY() + 7);
						}
					}
				}
			}
			if(handler.player.player.swimming) {
				boolean colliding = false;
				for(int i = 0; i < currentLevelTiles.size(); i++){
					Tile temp = currentLevelTiles.get(i);
					if(temp.getId() == ID.WaterTile) {
						if(player.getBounds().intersects(temp.getBounds())) {
							colliding = true;
						}
					}
				}
				if(colliding == false) {
					handler.player.player.swimming = false;
					handler.player.player.timer = 0;
				}
			}
		}
		/**
		 * Render method renders all working tiles and editor HUD elements if F1 is pressed
		 * @param String
		 */
		//Render method in this class is strictly for the debug editor//
		public void render(Graphics g) {
			if(debug) {
				for(int i = 0; i < currentLevelTiles.size(); i++)
				{
					Tile temp = currentLevelTiles.get(i);
					temp.render(g);
				}
				g.setColor(Color.RED);
				g.drawRect((int)player.getX()-15,(int)player.getY()+25,1,35);
				g.drawRect((int)player.getX()+60,(int)player.getY()+25,1,35);
				g.drawRect((int)player.getX()-1,(int)player.getY()+15,50,1);
				g.drawRect((int)player.getX()-1,(int)player.getY()+70,50,1);
				g.fillRect(1300, 950, 200,65);
				g.fillRect(1090, 950, 202,65);
				Font font = new Font("Times Roman", Font.BOLD,25);
				g.setFont(font);
				g.setColor(Color.WHITE);
				if(tileMode) {
					g.drawString("Save Rectangle", 1305, 990);
					g.drawString("Delete Rectangle", 1090, 990);
					g.setColor(Color.MAGENTA);
					g.drawString("Current Layer: " + this.layer , 890,1000);
					if(id == 1) {
						g.drawString("Current ID: Layer1Switch" , 790,970);
					}else if(id == 2) {
						g.drawString("Current ID: Layer2Switch" , 790,970);
					}else if(id == 3) {
						g.drawString("Current ID: Layer3Switch" , 790,970);
					}else if(id == 4) {
						g.drawString("Current ID: CollisionTile" , 790,970);
					}else if(id == 5) {
						g.drawString("Current ID: WaterTile" , 790,970);
					}else if(id == 6) {
						g.drawString("Current ID: EventTile" , 790,970);
					}
				}else if(entityMode){
					g.drawString("Save Entity", 1305, 990);
					g.drawString("Delete Entity", 1090, 990);
					g.setColor(Color.MAGENTA);
					g.drawString("Current Layer: " + this.layer , 890,1000);
					if(id == 1) {
						g.drawString("Current ID: Chest" , 870,970);
					}else if(id == 2) {
						g.drawString("Current ID: Pot" , 870,970);
					}else if(id == 3) {
						g.drawString("Current ID: NPC" , 870,970);
					}else if(id == 4) {
						g.drawString("Current ID: Octorok" , 870,970);
					}else if(id == 5) {
						g.drawString("Current ID: Soldier", 870, 970);
					}else if(id == 6) {
						g.drawString("Current ID: NPC2" , 870,970);
					}else if(id == 7) {
						g.drawString("Current ID: NPC3" , 870,970);
					}
				}
			}

		}
		
		/**
		 * Reads in data from Collision Map Files and Entity Map Files and adds the appropriate object based on the data received.
		 * @param String
		 */
		//Initialization of tile files//
		public void initTiles() {
			File currentCollisionMap = null;
			File currentEntityMap = null;
			int count = 0;
			while(count < numLevels) {
				if(count == 0) {
					currentCollisionMap = level1MapFile;
					currentEntityMap = level1EntityFile;
				}else if(count == 1) {
					currentCollisionMap = level2MapFile;
					currentEntityMap = level2EntityFile;
				}else if(count == 2) {
					currentCollisionMap = level3MapFile;
					currentEntityMap = level3EntityFile;
				}else if(count == 3) {
					currentCollisionMap = linkMapFile;
					currentEntityMap = linkEntityFile;
				}
				int id = 0;
				int x = 0;
				int y = 0;
				int width = 0;
				int height = 0;
				int layer = 0;
				Scanner mapReader = null;
				try {
					mapReader = new Scanner(new FileReader(currentCollisionMap));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Scanner entityReader = null;
				try {
					entityReader = new Scanner(new FileReader(currentEntityMap));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				//Delimiter setting//
				mapReader.useDelimiter(",");
				entityReader.useDelimiter(",");
				////
				//Reading through entire Level2Map File//
				while(mapReader.hasNext()) {
					id = Integer.parseInt(mapReader.next());
					x = Integer.parseInt(mapReader.next());
					y = Integer.parseInt(mapReader.next());
					width = Integer.parseInt(mapReader.next());
					height = Integer.parseInt(mapReader.next());
					layer = Integer.parseInt(mapReader.next());
					//Tile Loading
					//ID sorting so the program knows what is what.//
					if(id == 1) {
						this.addTile(ID.Layer1Switch,x,y, width, height, layer);
					}else if(id == 2) {
						this.addTile(ID.Layer2Switch,x,y, width, height, layer);
					}else if(id == 3) {
						this.addTile(ID.Layer3Switch,x,y, width, height, layer);
					}else if(id == 4) {
						this.addTile(ID.CollisionTile,x,y, width, height, layer);
					}else if(id == 5) {
						this.addTile(ID.WaterTile,x,y, width, height, layer);
					}else if(id == 6) {
						this.addTile(ID.EventTile,x,y, width, height, layer);
					}
					////
				}
				//Reading through entire Leve2EntityMap File//
				while(entityReader.hasNext()) {
					id = Integer.parseInt(entityReader.next());
					x = Integer.parseInt(entityReader.next());
					y = Integer.parseInt(entityReader.next());
					width = Integer.parseInt(entityReader.next());
					height = Integer.parseInt(entityReader.next());
					layer = Integer.parseInt(entityReader.next());
					//Entity Loading
					//More ID sorting//
					if(id == 10) {
						this.addTile(ID.CollisionTile,x,y-40, width, height, layer);
						handler.addObject(new Chest(ID.Chest,x,y,width,height,layer,back,handler,this),count);
					}else if(id == 11) {
						this.addTile(ID.CollisionTile,x,y, width, height, layer);
						handler.addObject(new Pot(ID.WorldObjectPot,x,y,width,height,layer,back,handler,this),count);
					}else if(id == 12) {
						handler.addObject(new NPC(ID.NPC,x,y,width,height,layer,back,handler,this),count);
					}else if(id == 13) {
						handler.addObject(new Octorok(ID.Enemy,x,y,width,height,layer,back,handler,this),count);
					}else if (id == 14) {
						handler.addObject(new Soldier(ID.Enemy, x, y, width, height,layer,back,handler,this),count);
					}else if(id == 15) {
						handler.addObject(new NPC2(ID.NPC,x,y,width,height,layer,back,handler,this),count);
					}else if(id == 16) {
						handler.addObject(new NPC3(ID.NPC,x,y,width,height,layer,back,handler,this),count);
					}
					//Setting level1Loaded to true so the program knows which shared list to add objects to//
					//(Line 569)//
					////
				}
				if(count == 0) {
					level1Loaded = true;
				}else if(count == 1) {
					level2Loaded = true;
				}else if(count == 2){
					level3Loaded = true;
				}else if(count == 3) {
					linkHouseLoaded = true;
				}
				count++;
			}
		}
		
		/**
		 *  Adds Tiles appropriately to their level.
		 * @param String
		 * 
		 */
		public void addTile(ID id, int xInd, int yInd, int width, int height, int layer) {
			Tile temp = new LayerTile();
			temp.setLayer(layer);
			temp.setId(id);
			temp.setX(xInd);
			temp.setY(yInd);
			temp.setxInd(xInd);
			temp.setyInd(yInd);
			temp.setWidth(width);
			temp.setHeight(height);
			//This is where levelloaded comes in handy..//
			if(level1Loaded == false) {
				level1Tiles.add(temp);
			}else if(level2Loaded == false) {
				level2Tiles.add(temp);
			}else if(level3Loaded == false){
				level3Tiles.add(temp);
			}else if(linkHouseLoaded == false){
				linkHouseTiles.add(temp);
			}else {
				if(handler.Level1) {
					level1Tiles.add(temp);
				}else if(handler.Level2) {
					level2Tiles.add(temp);
				}else if(handler.Level3) {
					level3Tiles.add(temp);
				}else if(handler.linkHouse) {
					linkHouseTiles.add(temp);
				}
			}
			////
		}
		public void removeTile(Tile temp) {
			level2Tiles.remove(temp);
		}
		
		//Saving Tiles in Editor and in real time//
		public void saveTile() {
			PrintWriter mapWriter = null;
			PrintWriter entityWriter = null;
			//Determining which level player is on and setting printwriters appropriately.//
			try {
			if(handler.Level1) {
				mapWriter = new PrintWriter(new FileWriter(level1MapFile,true));
				entityWriter = new PrintWriter(new FileWriter(level1EntityFile,true));
			}else if(handler.Level2) {
				mapWriter = new PrintWriter(new FileWriter(level2MapFile,true));
				entityWriter = new PrintWriter(new FileWriter(level2EntityFile,true));
			}else if(handler.Level3) {
				mapWriter = new PrintWriter(new FileWriter(level3MapFile,true));
				entityWriter = new PrintWriter(new FileWriter(level3EntityFile,true));
			}else if(handler.linkHouse) {
				mapWriter = new PrintWriter(new FileWriter(linkMapFile,true));
				entityWriter = new PrintWriter(new FileWriter(linkEntityFile,true));
			}
			}catch(IOException e) {
				e.printStackTrace();
			}
			/////
			//Testing for which mode the editor is in currently and testing id and then writing out data//
			//of the currentTile (Look in mousePressed Method for reference to currentTile)//
			if(tileMode) {
				if(currentTile.getId() == ID.Layer1Switch) {
					id = 1;
				}else if(currentTile.getId() == ID.Layer2Switch) {
					id = 2;
				}else if(currentTile.getId() == ID.Layer3Switch) {
					id = 3;
				}else if(currentTile.getId() == ID.CollisionTile) {
					id = 4;
				}
			    mapWriter.printf("%d,%d,%d,%d,%d,%d,", id, (int)currentTile.getX(),(int)currentTile.getY(),currentTile.getWidth(),currentTile.getHeight(),currentTile.getLayer());
				mapWriter.close();
			}else if(entityMode) {
				//Same thing down here except for entities.//
				if(id == 1) {
					id = 10;
				}else if(id == 2) {
					id = 11;
				}else if(id == 3) {
					id = 12;
				}else if(id == 4) {
					id = 13;
				}else if (id == 5) {
					id = 14;
				}else if(id == 6) {
					id = 15;
				}else if(id == 7) {
					id = 16;
				}
				if(id == 10) {
					System.out.println("CURRENT CHEST X: " + (int)currentChest.getX());
					System.out.println("CURRENT CHEST Y: " + (int)currentChest.getY());
					entityWriter.printf("%d,%d,%d,%d,%d,%d,", id, (int)currentChest.getX(),(int)currentChest.getY(),currentChest.getWidth(),currentChest.getHeight(),currentChest.getLayer());
					id = 1;
				}else if(id == 11) {
					entityWriter.printf("%d,%d,%d,%d,%d,%d,", id, (int)currentPot.getX(),(int)currentPot.getY(),currentPot.getWidth(),currentPot.getHeight(),currentPot.getLayer());
					id = 2;
				}else if(id == 12) {
					entityWriter.printf("%d,%d,%d,%d,%d,%d,", id, (int)currentNPC.getX(),(int)currentNPC.getY(),currentNPC.getWidth(),currentNPC.getHeight(),currentNPC.getLayer());
					id = 3;
				}else if(id == 13) {
					entityWriter.printf("%d,%d,%d,%d,%d,%d,", id, (int)currentOcto.getX(),(int)currentOcto.getY(),currentOcto.getWidth(),currentOcto.getHeight(),currentOcto.getLayer());
					id = 4;
				}else if(id == 14) {
					entityWriter.printf("%d,%d,%d,%d,%d,%d,", id, (int)currentSoldier.getX(),(int)currentSoldier.getY(),currentSoldier.getWidth(),currentSoldier.getHeight(),currentSoldier.getLayer());
					id = 5;
				}else if(id == 15) {
					entityWriter.printf("%d,%d,%d,%d,%d,%d,", id, (int)currentNPC2.getX(),(int)currentNPC2.getY(),currentNPC2.getWidth(),currentNPC2.getHeight(),currentNPC2.getLayer());
					id = 6;
				}else if(id == 16) {
					entityWriter.printf("%d,%d,%d,%d,%d,%d,", id, (int)currentNPC3.getX(),(int)currentNPC3.getY(),currentNPC3.getWidth(),currentNPC3.getHeight(),currentNPC3.getLayer());
				}
			    entityWriter.close();
			}
		    System.out.println("SUCCESFULLY SAVED");
		    //:D////<(*.*<)(>*.*)>//
		}
		
		//The beast itself//
		@Override
		public void mousePressed(MouseEvent e) {
			//Check for mode//
			if(tileMode) {
				//If tile mode if no rectangle has been placed//
				if(rectanglePlaced == false) {
					//backwardsX and backwardsY allows for rectangles to be placed in any way.//
					int backwardsX = 0;
					int backwardsY = 0;
					//Mouse count is to determine if a rectangle should be placed.(checks for second click)//
					mouseCount++;
					if(mouseCount == 1) {
						//First click position//
						xMousePos = e.getX();
						yMousePos = e.getY();
					}
					mousePressed = true;
					if(mouseCount == 2) {
						//Second click is where the magic happens.//
						int width = 0;
						int height = 0;
						mouseCount = 0;
						//Check if second click position X is greater than the 1st one or not//
						if(xMousePos < e.getX()) {
							width = e.getX() - xMousePos;
						}else {
							//If it is this math happens..//
							backwardsX = xMousePos;
							xMousePos = e.getX();
							width = backwardsX - xMousePos;
						}
						////
						//Same with Y//
						if(yMousePos < e.getY()) {
							height = e.getY() - yMousePos;
						}else {
							backwardsY = yMousePos;
							yMousePos = e.getY();
							height = backwardsY - yMousePos;
						}
						////
						//currentTile setting of newly made rectangle for saving.//
						currentTile = new LayerTile();
						if(id == 1) {
							this.addTile(ID.Layer1Switch, xMousePos - (int)back.getX(), yMousePos - (int)back.getY(),width,height, layer);
							currentTile.setId(ID.Layer1Switch);
						}else if(id == 2) {
							this.addTile(ID.Layer2Switch, xMousePos - (int)back.getX(), yMousePos - (int)back.getY(),width,height, layer);
							currentTile.setId(ID.Layer2Switch);
						}else if(id == 3) {
							this.addTile(ID.Layer3Switch, xMousePos - (int)back.getX(), yMousePos - (int)back.getY(),width,height, layer);
							currentTile.setId(ID.Layer3Switch);
						}else if(id == 4) {
							this.addTile(ID.CollisionTile, xMousePos - (int)back.getX(), yMousePos - (int)back.getY(),width,height, layer);
							currentTile.setId(ID.CollisionTile);
							System.out.println("ADDED TILE");
						}else if(id == 5) {
							this.addTile(ID.WaterTile, xMousePos - (int)back.getX(), yMousePos - (int)back.getY(),width,height, layer);
							currentTile.setId(ID.WaterTile);
						}else if(id == 6) {
							this.addTile(ID.EventTile, xMousePos - (int)back.getX(), yMousePos - (int)back.getY(),width,height, layer);
							currentTile.setId(ID.EventTile);
						}
						currentTile.setLayer(layer);
						currentTile.setX(xMousePos - (int)back.getX());
						currentTile.setY(yMousePos - (int)back.getY());
						currentTile.setWidth(width);
						currentTile.setHeight(height);
						//Set rectangle placed to true so no more rectangles can be placed until//
						//this one is saved.//
						rectanglePlaced = true;
					}
				}else {
					//If rectangle is placed, check if mouse click position intersects with either the//
					//save rectangle button or the delete rectangle button.//
					if(new Rectangle(e.getX(),e.getY(),1,1).intersects(new Rectangle(1300, 950, 200,65))) {
						rectanglePlaced = false;
						saveTile();
					}else if(new Rectangle(e.getX(),e.getY(),1,1).intersects(new Rectangle(1090, 950, 202,65))){
						rectanglePlaced = false;
					}
				}
			}else if(entityMode) {
				//Everything is the same as the Rectangles just have to create an entity on the cursor//
				//and add it to the game in real time.//
				mouseCount = 0;
				if(entityPlaced == false) {
					if(id == 1) {
						currentChest = new Chest();
						Chest tempChest = new Chest(ID.Chest, e.getX() - (int)back.getX(),e.getY() - (int)back.getY(),70,70,layer,back,handler,this);
						currentChest.setLayer(tempChest.getLayer());
						currentChest.setX(tempChest.getX());
						currentChest.setY(tempChest.getY());
						currentChest.setWidth(70);
						currentChest.setHeight(70);
						handler.addObject(tempChest);
					}else if(id == 2) {
						currentPot = new Pot();
						Pot tempPot = new Pot(ID.WorldObjectPot, e.getX() - (int)back.getX(),e.getY() - (int)back.getY(),50,50,layer,back,handler,this);
						currentPot.setLayer(tempPot.getLayer());
						currentPot.setX(tempPot.getX());
						currentPot.setY(tempPot.getY());
						currentPot.setWidth(tempPot.getWidth());
						currentPot.setHeight(tempPot.getHeight());
						System.out.println("TEMP CHEST X: " + tempPot.getX());
						System.out.println("TEMP CHEST Y: " + tempPot.getY());
						System.out.println("CURRENT CHEST X: " + (int)currentPot.getX());
						System.out.println("CURRENT CHEST Y: " + (int)currentPot.getY());
						handler.addObject(tempPot);
					}else if(id == 3) {
						currentNPC = new NPC();
						NPC tempNPC = new NPC(ID.NPC, e.getX() - (int)back.getX(),e.getY() - (int)back.getY(),70,70,layer,back,handler,this);
						
						tempNPC.setTileHandler(this);
						tempNPC.instance = true;
						currentNPC.setLayer(tempNPC.getLayer());
						currentNPC.setX(tempNPC.getX());
						currentNPC.setY(tempNPC.getY());
						currentNPC.setWidth(70);
						currentNPC.setHeight(70);
						currentNPC.player = this.player;
						tempNPC.player = player;
						
						handler.addObject(tempNPC);
					}else if(id == 4) {
						currentOcto = new Octorok();
						Octorok tempOcto = new Octorok(ID.Enemy, e.getX() - (int)back.getX(),e.getY() - (int)back.getY(),70,70,layer,back,handler,this);
						tempOcto.setTileHandler(this);
						tempOcto.instance = true;
						currentOcto.setLayer(tempOcto.getLayer());
						currentOcto.setX(tempOcto.getX());
						currentOcto.setY(tempOcto.getY());
						currentOcto.setWidth(70);
						currentOcto.setHeight(70);
						handler.addObject(tempOcto);
					}else if(id == 5) {
						currentSoldier = new Soldier();
						Soldier tempSoldier = new Soldier(ID.Enemy, e.getX() - (int)back.getX(),e.getY() - (int)back.getY(),70,70,layer,back,handler,this);
						tempSoldier.setTileHandler(this);
						tempSoldier.instance = true;
						currentSoldier.setLayer(tempSoldier.getLayer());
						currentSoldier.setX(tempSoldier.getX());
						currentSoldier.setY(tempSoldier.getY());
						currentSoldier.setWidth(100);
						currentSoldier.setHeight(100);
						handler.addObject(tempSoldier);
					}else if(id == 6) {
						currentNPC2 = new NPC2();
						NPC2 tempNPC2 = new NPC2(ID.NPC, e.getX() - (int)back.getX(),e.getY() - (int)back.getY(),70,70,layer,back,handler,this);
						tempNPC2.setTileHandler(this);
						tempNPC2.instance = true;
						currentNPC2.setLayer(tempNPC2.getLayer());
						currentNPC2.setX(tempNPC2.getX());
						currentNPC2.setY(tempNPC2.getY());
						currentNPC2.setWidth(70);
						currentNPC2.setHeight(70);
						handler.addObject(tempNPC2);
					}else if(id == 7) {
						currentNPC3 = new NPC3();
						NPC3 tempNPC3 = new NPC3(ID.NPC, e.getX() - (int)back.getX(),e.getY() - (int)back.getY(),70,70,layer,back,handler,this);
						tempNPC3.setTileHandler(this);
						tempNPC3.instance = true;
						currentNPC3.setLayer(tempNPC3.getLayer());
						currentNPC3.setX(tempNPC3.getX());
						currentNPC3.setY(tempNPC3.getY());
						currentNPC3.setWidth(70);
						currentNPC3.setHeight(70);
						handler.addObject(tempNPC3);
					}
					entityPlaced = true;
				}else {
					//Same checking for either save entity or delete entity.//
					if(new Rectangle(e.getX(),e.getY(),1,1).intersects(new Rectangle(1300, 950, 200,65))) {
						entityPlaced = false;
						saveTile();
					}else if(new Rectangle(e.getX(),e.getY(),1,1).intersects(new Rectangle(1090, 950, 202,65))){
						entityPlaced = false;
					}
					////
				}
			}
			System.out.println("MOUSE PRESSED");
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			mousePressed = false;
			System.out.println("MOUSE RELEASED");
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

}
