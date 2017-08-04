package impl;

import java.awt.Point;
import java.util.Random;

import api.IPolyomino;
import api.IPolyominoGenerator;
import impl.ITetromino;
import impl.JTetromino;
import impl.LTetromino;
import impl.OTetromino;
import impl.SZTetromino;
import impl.TTetromino;

/**
 * A generator which creates Tetromino pieces at random.
 * @author Brock Rikkers
 */
public class BasicGenerator implements IPolyominoGenerator
{
	@Override
	public IPolyomino getNext() 
	{
		Random r = new Random();
		int i = r.nextInt(6);
		int b = r.nextInt(10);
		boolean magicState;
		
		// Depending on b, the Tetromino will be magic or it won't.
		if(b == 1)
			magicState = true;
		else
			magicState = false;
		
		// Depending on i, it decides which Tetromino to create.
		if(i == 0)
			return new LTetromino(new Point(7, -1), magicState);		
		if(i == 1)
			return new JTetromino(new Point(6, -1), magicState);
		if(i == 2)
			return new ITetromino(new Point(6, -2), magicState);
		if(i == 3)
			return new OTetromino(new Point(5, -1), magicState);
		if(i == 4)
			return new TTetromino(new Point(6, 1), magicState);
		if(i == 5)
			return new SZTetromino(new Point(5, -2), magicState);
		
		else
			return null;
	}
}
