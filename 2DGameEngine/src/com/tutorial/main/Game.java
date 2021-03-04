package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1550691097823471818L;
	
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	
	private Thread thread;
	private boolean running = false;
	
	public static boolean paused = false;
	public int diff = 0;
	//0 = normal
	//1 = hard
	
	private Random r;	
	private Handler handler;
	private HUD hud;
	private Spawner spawner;
	private Menu menu;
	private Shop shop;
	
	public int endScore;
	public int newHealth;
	public int newBounds;
	
	private int menuSpawn = 0;
	
	public enum STATE 
	{
		Menu,
		Select,
		Help,
		Game,
		Shop,
		End
	};
	
	public static STATE gameState = STATE.Menu;
	
	public Game()
	{
		handler = new Handler();
		hud = new HUD();
		shop = new Shop(this, handler, hud);
		menu = new Menu(this, handler, hud);
		this.addKeyListener(new KeyInput(handler, this, hud));
		this.addMouseListener(menu);
		this.addMouseListener(shop);
		
		new Window(WIDTH, HEIGHT, "Let's Build a Game!", this);
		
		hud = new HUD();		
		spawner = new Spawner(handler, hud, this);		
		r = new Random();
		
		/*if(gameState == STATE.Game)
		{
			handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
		}else 
		{
			for(int i = 0; i < 20; i++)
			{
				handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
			}
		}*/
	}

	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop()
	{
		try
		{
			thread.join();
			running = false;
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1_000_000_000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				delta--;
			}
			if(running)
			{
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick()
	{

		if(gameState == STATE.Game)
		{
			endScore = hud.getScore();
			newBounds = hud.bounds;
			newHealth = HUD.getHEALTH();
			if(!paused)
			{
				handler.tick();
				hud.tick();
				spawner.tick();

				if(HUD.getHEALTH() <= 0)
				{
					HUD.setHEALTH(100);
					hud.setScore(0);
					hud.setLevel(1);
					spawner.setScoreKeep(0);
					spawner.setInitialSpawn(0);
					handler.clearPlayerAndEnemies();
					menuSpawn = 0;
					gameState = STATE.End;
				}
			}

		}
		else if(gameState == STATE.Shop)
		{
			hud.setScore(endScore);
			hud.bounds = newBounds;
			HUD.setHEALTH(newHealth);
		}

		else if(gameState == STATE.Menu)
		{
			menu.tick();
			handler.tick();
			while(menuSpawn < 20)
			{
				handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
				++menuSpawn;
			}
		} else if (gameState == STATE.End || gameState == STATE.Select || gameState == STATE.Help)
		{
			menu.tick();
			handler.tick();
		}

	}

	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(paused)
		{
			g.setColor(Color.white);
			g.drawString("PAUSED", 100, 100);
		}
		
		if(gameState == STATE.Game)
		{
			hud.render(g);
			handler.render(g);
		}else if(gameState == STATE.Shop)
		{
			shop.render(g);	
		}
		
		else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select)
		{
			menu.render(g);
			handler.render(g);
		}
				
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max)
	{
		if(var >= max) return var = max;
		else if( var <= min) return var = min;
		else return var;
	}
	
	public static void main(String args[])
	{
		new Game();
	}

}
