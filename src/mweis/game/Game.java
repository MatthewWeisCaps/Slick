package mweis.game;

import mweis.game.entities.Player;
import mweis.game.gfx.Camera;
import mweis.game.world.Level;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {
	
	public static final String TITLE = "Game";
	public static final int WIDTH = 5*320; // this  should always equal the displayMode's value - or logic will be wrong.
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final boolean FULLSCREEN = false;
	public static final boolean CLEAR_SCREEN_BETWEEN_FRAMES = true; // true is slower - only use when not drawing entire screen each frame..
	//---
	private Level level;
	private Camera camera;
	private Player player;
	
	// level transparent: 75, 99, 127, (255 opacity) =s 4B637F
	// not working: maybe tilesheets must be png?
	private Game(String title){
		super(title);
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		try {
			level = new Level("res/testmap.tmx");
		} catch (SlickException e){
			e.printStackTrace();
		}
		player = new Player(0*level.getTileWidth(), 0*level.getTileHeight(), level);
		camera = new Camera(player);
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(new Color(0.5f, 0.5f, 0.5f));
		
		g.translate(-camera.getX(), -camera.getY());
		g.pushTransform();
			level.render(camera, g);
			player.render(container, g); // will eventually only be through  render
		g.popTransform();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		player.update(container, delta);
	}
	
	
	public static void main(String[] args){
		try {
			AppGameContainer app = new AppGameContainer(new Game(TITLE));
			app.setClearEachFrame(CLEAR_SCREEN_BETWEEN_FRAMES); // will render whole screen
			app.setDisplayMode(WIDTH, HEIGHT, FULLSCREEN);
			app.start();
		} catch (SlickException e){
			e.printStackTrace();
		}
	}
} // end class
