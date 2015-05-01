package mweis.game.world;

import java.util.ArrayList;
import java.util.List;

import mweis.game.entities.Entity;
import mweis.game.gfx.Camera;

import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

/*
 * Level
 * ---------
 * A TiledMap that handles entities and other things.
 * 
 */
public class Level extends TiledMap {
//	private boolean[][] blocked; // list of blocked tiles
	
	private List<Rectangle> blockers = new ArrayList<Rectangle>(); // an array of all blockers as shapes in this level
	private List<Entity> entities = new ArrayList<Entity>(); // an array of all entities in this level
	
	
	public Level(String ref) throws SlickException {
		super(ref);
		if (getTileWidth() != getTileHeight()){
			System.err.println("WARNING: Tile width != height.");
		}
		
//		blocked = new boolean[super.getWidth()][super.getHeight()];
		for (int x=0; x < super.getWidth(); x++){
			for (int y=0; y < super.getHeight(); y++){
				for (int l=0; l < getLayerCount(); l++){
					final String isBlocked = getTileProperty(getTileId(x, y, l), "blocked", "false");
					if (isBlocked.equalsIgnoreCase("true")){
//						blocked[x][y] = true;
						this.blockers.add(new Rectangle(toWorldCoords(x), toWorldCoords(y), getTileWidth(), getTileHeight()));
					}
				}
			}
		}
	}
	
	public void render(Camera camera, Graphics g){
		super.render(0, 0);
		// draw blockers
		g.setColor(new Color(1.0f, 1.0f, 1.0f));
		for (Rectangle blocker : blockers){
			g.draw(blocker);
		}
	}
	
	// tells if entity is touching a tile with a specific properties
	// need to figure out tiled more before doing this..
	public boolean isBlocked(Shape bounds){
		for (Rectangle blocker : blockers){
			if (bounds.intersects(blocker)){
				return true;
			}
		}
		return false;
	}
	
	// adds an entity to the level, this is automatically called from entity's constructor
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	// converts a value in world coords to level coords
	public float toLevelCoords(float worldPos){
		assert(getTileWidth() == getTileHeight());
		return worldPos / getTileWidth();
	}
	
	// converts a value in level coords to world coords
	public float toWorldCoords(float tilePos){
		assert(getTileWidth() == getTileHeight());
		return tilePos * getTileWidth();
	}
	
	public int getWorldWidth(){
		return getWidth() * getTileWidth();
	}
	
	public int getWorldHeight(){
		return getHeight() * getTileHeight();
	}
	
}
