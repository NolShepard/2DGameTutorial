package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas
{

	private static final long serialVersionUID = -240840600533728354L;
	
	public Window(int width, int height, String title, Game game)
	{
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // upper righthand "x" works to close out game from thread operations
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // null = window starts in the middle of current window, not top left
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}

}
