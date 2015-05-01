package mweis.game.entities;


import mweis.game.world.Level;

import org.newdawn.slick.geom.Rectangle;

public abstract class Mob extends Entity {
	
	private float speed = 0.15f;
	
	// all mobs are rectangles..
	public Mob(Rectangle shape, Level level) {
		super(shape, level);
	}
	
	// another way to define a mob
	public Mob(float x, float y, float width, float height, Level level){
		this(new Rectangle(x, y, width, height), level);
	}
	
	
	protected final void move(float x, float y){
		move(x, y, false);
	}
	
	private final void move(float x, float y, boolean calculatedSpeed){
		if (x == 0.0f && y == 0.0f){
			return;
		}
		
		if (!calculatedSpeed){
			x *= speed;
			y *= speed;
		}
		
		if (x != 0.0f && y != 0.0f){
			move(x, 0, true);
			move(0, y, true);
			return;
		}
		
		
		final float nx = getX() + x, ny = getY() + y; // next x, next y
		
		// if the path in front of you isn't blocked OR you're already ontop of a blocked block..
		if (!getLevel().isBlocked(new Rectangle(nx, ny, getWidth(), getHeight())) || getLevel().isBlocked(new Rectangle(getX(), getY(), getWidth(), getHeight()))){
			this.setLocation(nx, ny); // move player, setPosition will force mob into level's bounds.
		}
	}
	
	/*
	 * Override setPosition as all mobs (but not entities) must remain within the level's bounds..
	 * however, it's still possible (on purpose) to set location onto a block you can't usually walk over (move is used for checking this)
	 */
	@Override
	protected final void setLocation(float x, float y){
		// keep entity within level's bounds..
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		if (x + getWidth() > getLevel().getWorldWidth())
			x = getLevel().getWorldWidth() - getWidth();
		if (y + getHeight() > getLevel().getWorldHeight())
			y = getLevel().getWorldHeight() - getHeight();
		
		super.setLocation(x, y);
	}
	
	public final void setWidth(float width) {
		super.setShape(new Rectangle(getX(), getY(), width, getHeight()));
	}

	public final void setHeight(float height) {
		super.setShape(new Rectangle(getX(), getY(), getWidth(), height));
	}
	
	public final void setWidthHeight(float width, float height){
		super.setShape(new Rectangle(getX(), getY(), width, height));
	}
	
	public final float getSpeed(){
		return speed;
	}
	
	protected final void setSpeed(float speed){
		this.speed = speed;
	}

}
