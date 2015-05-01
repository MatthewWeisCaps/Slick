package mweis.game.world;

import java.util.ArrayList;

// a world is many levels put together..
public class World {
	
	private ArrayList<Level> levels = new ArrayList<Level>();
	
	public World(){
		
	}
	
	
	public void addLevel(Level level){
		levels.add(level);
	}
	
	
}
