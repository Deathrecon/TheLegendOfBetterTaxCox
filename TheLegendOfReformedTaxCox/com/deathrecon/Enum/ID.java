package com.deathrecon.Enum;

public enum ID {
	//GameObject ID's
	Player(),
	Enemy(),
	Heart(),
	Key(),
	Sword(),
	Block(),
	Shield(),
	Chest(),
	Rupee(),
	HUD(),

	WorldObjectPot(),


	HUDHEART(),
	HUDRUPEE(),
	
	//Tile Collision ID's
	Background,
	Layer1,
	Layer2,
	Layer3,
	Layer1Switch,
	Layer2Switch,
	Layer3Switch,
	CollisionTile,
	WaterTile,
	DamageTile,
	JumpTile,
	EventTile,
	EventChestTile,
	EnemySpawnTile;
}
