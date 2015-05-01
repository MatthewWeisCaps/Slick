package mweis.game.gfx;


import mweis.game.Game;
import mweis.game.entities.Mob;

/*
 * A camera that follows the specified entity.
 * This class holds an x and y which should be treated as an offset for drawing operations.
 */
public class Camera {
	Mob mob;

	// follows an entity, can make an entity designed to snap the camera to if needed
	public Camera(Mob mob){
		this.mob = mob;
	}
	
	public boolean isVisible(float x, float y){
		final double gw = Game.WIDTH/2;
		final double gh = Game.HEIGHT/2;
		final double mx = getX();
		final double my = getY(); 
		
		if	((x < mx-gw) || (x > mx+mob.getWidth()+gw) || (y < my-gh) || (y > my+mob.getHeight()+gh))
			return false;
		return true;
	}
	
	// checks if an entity is visible
	public boolean isVisible(Mob mob){
		return isVisible(mob.getX(), mob.getY());
	}
	
	
	// getX and getY return camera's bot-left coord
	public float getX() {
		return mob.getCenterX()-Game.WIDTH/2;
	}
	
	public float getY() {
		return mob.getCenterY()-Game.HEIGHT/2;
	}
	
	public float getWidth(){
		return Game.WIDTH;
	}
	
	public float getHeight(){
		return Game.HEIGHT;
	}

}
