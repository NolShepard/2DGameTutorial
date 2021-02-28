package com.tutorial.main;

import java.util.Random;

public class Spawner 
{

	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	private int initialSpawn = 0;

	private int scoreKeep = 0;	

	public Spawner(Handler handler, HUD hud)
	{
		this.handler = handler;
		this.hud = hud;
	}
	
	public void tick()
	{
		scoreKeep++;
		
		while(initialSpawn == 0)
		{
		handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
		handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
		++initialSpawn;
		}
		
		if(scoreKeep >= 100)
		{
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);
			
			if(hud.getLevel() == 2)
			{
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
			} else if(hud.getLevel() == 4)
			{
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
			} else if(hud.getLevel() == 5)
			{
				handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy, handler));
			} else if(hud.getLevel() == 6)
			{
				handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy, handler));
			} else if(hud.getLevel() == 7)
			{
				handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
				handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
			} else if(hud.getLevel() == 10)
			{
				handler.clearEnemies();
				handler.addObject(new EnemyBoss1((Game.WIDTH / 2) - 48, -160, ID.EnemyBoss1, handler));
			}
		}
	}
	
	public int getScoreKeep() 
	{
		return scoreKeep;
	}

	public void setScoreKeep(int scoreKeep) 
	{
		this.scoreKeep = scoreKeep;
	}

	public int getInitialSpawn() 
	{
		return initialSpawn;
	}

	public void setInitialSpawn(int initialSpawn) 
	{
		this.initialSpawn = initialSpawn;
	}

}
