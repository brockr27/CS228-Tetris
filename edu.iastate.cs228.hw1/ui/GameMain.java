package ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import api.IGame;
import api.IPlayLevel;
import impl.BasicPlayLevel;
import impl.CS228Tetris;

public class GameMain {
	
	/**
	 * Cell size in pixels.
	 */
	public static final int SIZE = 25;

	/**
	 * Entry point. Main thread passed control immediately
	 * to the Swing event thread.
	 * @param args not used
	 */
	public static void main(String[] args) 
	{
		Runnable r = new Runnable()
		{
			public void run()
			{
				create();
			}
		};
		SwingUtilities.invokeLater(r);
	}

	/**
	 * Helper method for instantiating the components. This
	 * method should be executed in the context of the Swing
	 * event and thread only.
	 */
	private static void create()
	{
		IGame game = new CS228Tetris();
		IPlayLevel level = new BasicPlayLevel();
		GamePanel panel = new GamePanel(game, level);
		JFrame frame = new JFrame();
		frame.getContentPane().add(panel);
		
		// give it a non-zero size
		panel.setPreferredSize(new Dimension(game.getWidth() * GameMain.SIZE,
											game.getHeight() * GameMain.SIZE));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.requestFocus();
		frame.setVisible(true);
	}
}
