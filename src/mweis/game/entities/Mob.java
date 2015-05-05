package mweis.game.entities;


import mweis.game.world.Level;

import org.newdawn.slick.geom.Rectangle;

public abstract class Mob extends Entity {
	
	private float speed = 0.15f;
	private final float ONE_OVER_SQRT_2 = (float) (1/Math.sqrt(2));
	
	// all mobs are rectangles..
	public Mob(Rectangle shape, Level level) {
		super(shape, level);
	}
	
	// another way to define a mob
	public Mob(float x, float y, float width, float height, Level level){
		this(new Rectangle(x, y, width, height), level);
	}
		
	// guarentee's mob moves same dist (w/ respect to delta) per call.
	// NOT WORKING?
	protected final void move(int xdir, int ydir, int delta){
		assert(xdir == -1 || xdir == 0 || xdir == 1);
		assert(ydir == -1 || ydir == 0 || ydir == 1);
		if (xdir == 0 && ydir == 0){
			return;
		}
		
		final float dist = speed * delta;
		float nx = 0.0f;
		float ny = 0.0f;
		
		// if moving bidirectionally
		if (xdir != 0 && ydir != 0){
			nx = ONE_OVER_SQRT_2 * dist * xdir;
			ny = ONE_OVER_SQRT_2 * dist * ydir;
		} else if (xdir != 0) { // if moving left/right
			nx = dist * xdir;
		} else if (ydir != 0) { // if moving up/down
			ny = dist * ydir;
		}
				
		nx += getX();
		ny += getY();
		// if the path in front of you isn't blocked OR you're already ontop of a blocked block..
		if (!getLevel().isBlocked(new Rectangle(nx, ny, getWidth(), getHeight())) || getLevel().isBlocked(new Rectangle(getX(), getY(), getWidth(), getHeight()))){
			this.setLocation(nx, ny); // move player, setPosition will force mob into level's bounds.
		} else if (!getLevel().isBlocked(new Rectangle(nx, getY(), getWidth(), getHeight()))){
			move(xdir, 0, delta); // y dir is blocked but x isn't. move x.
		} else if (!getLevel().isBlocked(new Rectangle(getX(), ny, getWidth(), getHeight()))){
			move(0, ydir, delta); // x dir is blocked but y isn't. move y.
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
