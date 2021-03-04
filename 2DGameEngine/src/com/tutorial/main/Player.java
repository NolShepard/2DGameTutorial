package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject
{

	Random r = new Random();
	Handler handler;
	HUD hud;
	
	public Player(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
		
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle((int) x, (int) y, 32, 32);
	}
	
	public void tick() {

		x += velX;
		y += velY;
		
		x = Game.clamp((int) x, 0, Game.WIDTH - 48);
		y = Game.clamp((int) y, 0, Game.HEIGHT - 72);
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.white, 32, 32, 0.09f, handler));
		
		collision();
	}
	
	private void collision()
	{
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.BasicEnemy || tempObject.getID() == ID.FastEnemy || tempObject.getID() == ID.SmartEnemy || tempObject.getID() == ID.EnemyBoss1 || tempObject.getID() == ID.HardEnemy)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{//collision code
					HUD.setHEALTH(HUD.getHEALTH() - 2);
				}
			}
		}
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect((int) x, (int) y, 32, 32);
	}
	
	

}
