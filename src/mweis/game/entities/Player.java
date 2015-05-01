package mweis.game.entities;

import mweis.game.gfx.Sprite;
import mweis.game.world.Level;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Mob {
	
	private Sprite sprite;
	
	public Player(int x, int y, Level level) {
		super(x, y, 20, 20, level); // pre-define 20 as player width & height
		initGraphics();
	}
	
	
	private void initGraphics(){
		try {			
			sprite = new Sprite("res/holder_sprite_2.png", 192/3, 256/4);
			sprite.createAnimation("up", 	false, new int[] {0, 3, 300}, new int[] {0, 3, 300}, new int[] {0, 3, 300});
			sprite.createAnimation("down", 	false, new int[] {0, 0, 300}, new int[] {1, 0, 300}, new int[] {2, 0, 300});
			sprite.createAnimation("left", 	false, new int[] {0, 1, 300}, new int[] {1, 1, 300}, new int[] {2, 1, 300});
			sprite.createAnimation("right", false, new int[] {0, 2, 300}, new int[] {1, 2, 300}, new int[] {2, 2, 300});
		} catch (SlickException se){
			System.out.println(se.toString());
		} finally {
			sprite.setAnimation("up");
			sprite.setOffsetFromDim(getWidth(), getHeight());
		}
	}

	

	@Override
	public void render(GameContainer container, Graphics g) {
		sprite.draw(getX(), getY());
		g.drawRect(getX(), getY(), getWidth(), getHeight());
	}


	@Override
	public void update(GameContainer container, int delta) throws SlickException {		
		Input input = container.getInput();
		final boolean up = input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W);
		final boolean down = input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S);
		final boolean left = input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A);
		final boolean right = input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D);
		
		float dx = 0.0f, dy = 0.0f; // delta x and y
		
		if (up & !down) {
		    sprite.setAnimation("up");
		    sprite.update(delta);
		    dy = -1.0f;
		} else if (down & !up) {
			sprite.setAnimation("down");
		    sprite.update(delta);
			dy = 1.0f;
		}
		
		if (left & !right) {
			sprite.setAnimation("left");
		    sprite.update(delta);
		   	dx = -1.0f;
		} else if (right & !left) {
			sprite.setAnimation("right");
		    sprite.update(delta);
			dx = 1.0f;
		}
		
		move(dx * delta, dy * delta);
		
	}	
}
