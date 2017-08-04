package impl;

import java.awt.Color;
import java.awt.Point;

import api.Cell;
import api.IPolyomino;
import api.Tetromino;

/**
 * A class which constructs an OTetromino piece.
 * @author Brock Rikkers
 */
public class OTetromino extends Tetromino implements IPolyomino
{
	/**
	 * The color of the Tetromino.
	 */
	private static Color COLOR = Color.YELLOW;
	
	/**
	 * Original position of cell[0]
	 */
	private Point position;
	
	/**
	 * An array of Cells representing the Tetromino.
	 */
	private Cell[] cells;
	
	/**
	 * The orientation of the Tetromino. Starting orientation is "origin".
	 * Other orientations are "ccw1" for counter-clockwise 1, "ccw2" for
	 * counter-clockwise 2, and so on.
	 */
	private String orientation;
	
	public OTetromino(Point p, boolean magicState)
	{
		position = new Point(p);
		
		cells = new Cell[4];
		cells[0] = new Cell(new Block(COLOR, magicState), new Point(position.x, position.y));
		cells[1] = new Cell(new Block(COLOR, false), new Point(position.x+1, position.y));
		cells[2] = new Cell(new Block(COLOR, false), new Point(position.x, position.y+1));
		cells[3] = new Cell(new Block(COLOR, false), new Point(position.x+1, position.y+1));
		
		orientation = "origin";
	}
	@Override
	public Cell[] getCells() {
		return cells;
	}

	@Override
	public void shiftDown() 
	{
	    position.y += 1;
	    for (int i = 0; i < cells.length; ++i)
	    {
	      cells[i].setY(cells[i].getY() + 1);
	    }
	}

	@Override
	public void shiftLeft()
	{
	    position.x -= 1;
	    for(int i = 0; i < cells.length; i++) {
	    	cells[i].setX(cells[i].getX() - 1);
	    }
	}

	@Override
	public void shiftRight()
	{
		  position.x += 1;
		  for(int i = 0; i < cells.length; i++) {
			  cells[i].setX(cells[i].getX() + 1);
		  }
	}

	@Override
	public void transform()
	{
		String orientation2 = null;
			
		if(orientation.equals("origin"))
		{		
			super.transform(cells, 1, -1, -1);
			super.transform(cells, 2, 1, -1);
			super.transform(cells, 3, 0, -2);
			
			orientation2 = "ccw1";
		}
		
		if(orientation.equals("ccw1"))
		{	
			super.transform(cells, 1, -1, 1);
			super.transform(cells, 2, -1, -1);
			super.transform(cells, 3, -2, 0);
			
			orientation2 = "ccw2";
		}
		
		if(orientation.equals("ccw2"))
		{
			super.transform(cells, 1, 1, 1);
			super.transform(cells, 2, -1, 1);
			super.transform(cells, 3, 0, 2);
			
			orientation2 = "ccw3";
		}
		
		if(orientation.equals("ccw3"))
		{	
			super.transform(cells, 1, 1, -1);
			super.transform(cells, 2, 1, 1);
			super.transform(cells, 3, 2, 0);
			
			orientation2 = "origin";
		}
		
		orientation = orientation2;
	}

	@Override
	public void cycle() 
	{
		super.cycle(cells);
	}
	
	@Override
	public IPolyomino clone() 
	{
		IPolyomino clone = new OTetromino(position, false);
		
		if(orientation.equals("ccw1"))
		{
			clone.transform();
		}
		if(orientation.equals("ccw2"))
		{
			clone.transform();
			clone.transform();
		}
		if(orientation.equals("ccw3"))
		{
			clone.transform();
			clone.transform();
			clone.transform();
		}
		
		return clone;
	}
	
	private boolean equals(IPolyomino other)
	{
		boolean b = super.equals(cells, other.getCells());
		return b;
	}
}
