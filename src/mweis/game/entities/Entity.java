package mweis.game.entities;

import mweis.game.world.Level;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

public abstract class Entity {
	private Level level;
	private Shape shape; // shapes can be circles, rects, or even points - don't extend this because we want to hide some of the set functions in shape
	
	public Entity(Shape shape, Level level){
		this.level = level;
		this.shape = shape;
		level.addEntity(this);
	}
	
	public abstract void update(GameContainer container, int delta) throws SlickException;
	public abstract void render(GameContainer container, Graphics g) throws SlickException;
	
	public Level getLevel(){
		return level;
	}
	
	// can redefine shape at any time.
	protected void setShape(Shape shape){
		this.shape = shape;
	}
	
	// getters / setters for shape visible to subclasses..
	protected void setLocation(float x, float y){
		shape.setLocation(x, y);
	}
	
	public final float getWidth() {
		return shape.getWidth();
	}

	public final float getHeight() {
		return shape.getHeight();
	}
	
	// same as getLeftX
	public final float getX(){
		return shape.getX();
	}
	
	// same as getLeftY
	public final float getY(){
		return shape.getY();
	}
	
	public final float getMaxX(){
		return shape.getMaxX();
	}
	
	public final float getMaxY(){
		return shape.getMaxY();
	}
	
	public final float getMinX(){
		return shape.getMinX();
	}
	
	public final float getMinY(){
		return shape.getMinY();
	}
	
	public final float getCenterX(){
		return shape.getCenterX();
	}
	
	public final float getCenterY(){
		return shape.getCenterY();
	}
	
	public boolean hasCollided(Entity entity){
		if (shape.intersects(entity.shape)){
			return true;
		}
		return false;
	}
	
}
