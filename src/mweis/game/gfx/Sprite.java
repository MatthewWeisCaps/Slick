package mweis.game.gfx;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


/*
 * A class that allows the user to make animations and set one as the "current" animation.
 * This also let's the user offset the sprite drawing (for hitbox's who's size isn't equal to the image size)
 * 
 * see Player.java for usage example.
 */
public class Sprite {
	HashMap<String, Animation> anims = new HashMap<String, Animation>(); // all animations declared as Strings
	
	private SpriteSheet sheet;
	private Animation current; // use Animation and not String because looking in hashmap per render is a bad idea..	
	private float[] offset = new float[2]; // offsets for Sprite
	
	
	public Sprite(float offsetX, float offsetY, SpriteSheet sheet){
		this.sheet = sheet;
		offset[0] = offsetX;
		offset[1] = offsetY;
	}
	
	public Sprite(SpriteSheet sheet){
		this(0, 0, sheet);
	}
	
	public Sprite(float offsetX, float offsetY, String path, int tileWidth, int tileHeight) throws SlickException{
		this(offsetX, offsetY, new SpriteSheet(path, tileWidth, tileHeight));
	}
	
	public Sprite(String path, int tileWidth, int tileHeight) throws SlickException{
		this(0, 0, new SpriteSheet(path, tileWidth, tileHeight));
	}
	
	/*
	 * this is the whole reason the Sprite class exists, to allow for easy drawing with offsets.
	 */
	public void draw(float x, float y){
		current.draw(x - offset[0], y - offset[1]);
	}
	
	public void draw(float x, float y, Color filter){
		current.draw(x - offset[0], y - offset[1], filter);
	}
	
	public void draw(float x, float y, float width, float height){
		current.draw(x - offset[0], y - offset[1], width, height);
	}
	
	public void draw(float x, float y, float width, float height, Color color){
		current.draw(x - offset[0], y - offset[1], width, height, color);
	}
	
	public void drawFlash(float x, float y, float width, float height){
		current.drawFlash(x - offset[0], y - offset[1], width, height);
	}
	
	public void drawFlash(float x, float y, float width, float height, Color color){
		current.drawFlash(x - offset[0], y - offset[1], width, height, color);
	}
	
	
	public void setAnimation(String animation){
		current = anims.get(animation);
	}
	
	public void update(long delta){
		current.update(delta);
	}
	
	public void setOffset(float ox, float oy){
		offset[0] = ox;
		offset[1] = oy;
	}
	
	// creates & sets an offset for XY that puts the image/hitbox (based on which is smaller) in the center of the hitbox/image (based on which is larger)
	public void setOffsetFromDim(float width, float height){
		setOffset((getWidth()-width)/2.0f, (getHeight()-height)/2.0f);
	}
	
	/*
	 * create an animation
	 * @param animation - the name of this animation    ex. "up"
	 * @param animationDuration - duration per frame for this animation
	 * @param autoUpdate - if timer on anim should auto update on render (good for passive animations, not walking ones)
	 * @param sheetXYD - {sheetX, sheetY, duration}, {sheetX, sheetY, duration} ... for each frame in animation
	 */
	public void createAnimation(String name, boolean autoUpdate, int[] ... sheetXYD){
		assert(sheetXYD[0].length == 3);
		
		// get frames as images
		final Image[] frames = new Image[sheetXYD.length];
		for (int i=0; i < frames.length; i++){
			frames[i] = sheet.getSprite(sheetXYD[i][0], sheetXYD[i][1]);
		}
		
		// create array of animation durations
		int[] animDurations = new int[sheetXYD.length];
		for (int i=0; i < animDurations.length; i++){
			animDurations[i] = sheetXYD[i][2];
		}
		
		// put animation with this name
		anims.put(name, new Animation(frames, animDurations, autoUpdate));
	}
	
	
	public int getWidth(){
		return current.getWidth();
	}
	
	public int getHeight(){
		return current.getHeight();
	}
	
}
