package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Shop extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HUD hud;
	
	private int[] cost = {1000, 1000, 1000};
	
	
	public Shop(Game game, Handler handler, HUD hud)
	{
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}
	
	public void render (Graphics g) 
	{
		g.setColor(Color.white);
		g.setFont(new Font("arial", 0, 48));
		g.drawString("SHOP", Game.WIDTH/2-100, 50);
		
		//box 1 
		g.setFont(new Font("arial", 0, 15));
		g.drawString("Upgrade Health", 110, 120);
		g.drawString("Cost: " + cost[0], 110, 140);
		g.drawRect(100, 100, 120, 80);

		//box 2 
		g.drawString("Upgrade Speed", 260, 120);
		g.drawString("Cost: " + cost[1], 260, 140);
		g.drawRect(250, 100, 120, 80);

		//box 3 
		g.drawString("Refill Health", 410, 120);
		g.drawString("Cost: " + cost[2], 410, 140);
		g.drawRect(400, 100, 120, 80);
		
		g.drawString("SCORE: " + game.endScore, Game.WIDTH/2-50, 300);
		g.drawString("Press Space to go back", Game.WIDTH/2-50, 330);
	}
	
	public void mousePressed(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		//box 1
		if(mx >= 100 && mx <= 220)
		{
			if(my >= 100 && my <= 180)
			{
				//you'v selected box 1
				if(game.endScore >= cost[0])
				{
					//game.endScore -= cost[0];
					game.endScore -= cost[0];
					cost[0] += 1000;	
					game.newBounds += 20;
					game.newHealth = 100 + game.newBounds/2;
					
				}
			}
		}
		//box 2
		if(mx >= 250 && mx <= 370)
		{
			if(my >= 100 && my <= 180)
			{
				//you'v selected box 2
				if(game.endScore >= cost[1])
				{
					game.endScore -= cost[1];
					cost[1] += 1000;
					handler.speed++;
				}
			}
		}
		//box 3
		if(mx >= 400 && mx <= 520)
		{
			if(my >= 100 && my <= 180)
			{
				//you'v selected box 3
				if(game.endScore >= cost[2])
				{
					game.endScore -= cost[2];	
					game.newHealth = 100 + game.newBounds/2;
				}
			}
		}
	}
}
