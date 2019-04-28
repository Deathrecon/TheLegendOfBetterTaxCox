package com.deathrecon.handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.deathrecon.Enum.ID;
import com.deathrecon.game.GameObject;

public class KeyInputHandler extends KeyAdapter{
	Handler handler;
	TileHandler tileHandler;
	
	public KeyInputHandler(Handler handler,TileHandler tileHandler) {
		this.handler = handler;
		this.tileHandler = tileHandler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				if(key == KeyEvent.VK_W) {
					handler.setUp(true);
				}
				
				if(key == KeyEvent.VK_S) {
					handler.setDown(true);
				}
				
				if(key == KeyEvent.VK_D) {
					handler.setRight(true);
				}
				
				if(key == KeyEvent.VK_A) {
					handler.setLeft(true);
				}
				if(key == KeyEvent.VK_SPACE) {
					handler.setSpace(true);
				}
				if(key == KeyEvent.VK_F1) {
					System.out.println("PRESSED?");
					if(tileHandler.debug == false) {
						tileHandler.debug = true;
					}else {
						tileHandler.debug = false;
					}
				}
				if(key == KeyEvent.VK_F2) {
					tileHandler.tileMode = true;
					tileHandler.entityMode = false;
					tileHandler.id = 1;
				}
				if(key == KeyEvent.VK_F3) {
					tileHandler.entityMode = true;
					tileHandler.tileMode = false;
					tileHandler.id = 1;
				}
				if(key == KeyEvent.VK_NUMPAD1) {
					tileHandler.id = 1;
				}
				if(key == KeyEvent.VK_NUMPAD2) {
					tileHandler.id = 2;
				}
				if(key == KeyEvent.VK_NUMPAD3) {
					tileHandler.id = 3;
				}
				if(key == KeyEvent.VK_NUMPAD4) {
					tileHandler.id = 4;
				}
				if(tileHandler.tileMode) {
					if(key == KeyEvent.VK_NUMPAD5) {
						tileHandler.id = 5;
					}
					if(key == KeyEvent.VK_NUMPAD6) {
						tileHandler.id = 6;
					}
				}
				if(key == KeyEvent.VK_0) {
					tileHandler.layer = 0;
				}
				if(key == KeyEvent.VK_1) {
					tileHandler.layer = 1;
				}
				if(key == KeyEvent.VK_2) {
					tileHandler.layer = 2;
				}
				if(key == KeyEvent.VK_3) {
					tileHandler.layer = 3;
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				if(key == KeyEvent.VK_W) {handler.setUp(false);}
				
				if(key == KeyEvent.VK_S) {handler.setDown(false);}
				
				if(key == KeyEvent.VK_D) {
					handler.setRight(false);
				}
				
				if(key == KeyEvent.VK_A) {
					handler.setLeft(false);
				}
				
				// this may get deleted.... May not need it, so that way the player can spam the
				// attack without having to hold space to complete the attack.
				if(key == KeyEvent.VK_SPACE) {
					handler.setSpace(false);
				}
			}
		}
	}
}
