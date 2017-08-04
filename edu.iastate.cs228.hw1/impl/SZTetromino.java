package impl;

import java.awt.Color;
import java.awt.Point;

import api.Cell;
import api.IPolyomino;
import api.Tetromino;

/**
 * A class which constructs an SZTetromino piece.
 * @author Brock Rikkers
 */
public class SZTetromino extends Tetromino implements IPolyomino 
{
	/**
	 * The color of the Tetromino. The color changes to red on special transforms.
	 */
	private static Color COLOR = Color.GREEN;
	
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
	
	/**
	 * A boolean representing the magic state of the Tetromino.
	 */
	private boolean magic;
	
	public SZTetromino(Point p, boolean magicState)
	{
		position = new Point(p);
		
		magic = magicState;
		
		cells = new Cell[4];
		cells[0] = new Cell(new Block(COLOR, magicState), position);
		cells[1] = new Cell(new Block(COLOR, false), new Point(position.x, position.y+1));
		cells[2] = new Cell(new Block(COLOR, false), new Point(position.x+1, position.y+1));
		cells[3] = new Cell(new Block(COLOR, false), new Point(position.x+1, position.y+2));
		
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
			super.transform(cells, 2, -2, 0);
			super.transform(cells, 3, -3, -1);
			
			orientation2 = "ccw1";
		}
		
		// Changes the green STetromino into a red ZTetromino.
		if(orientation.equals("ccw1"))
		{	
			Point p0 = new Point(cells[0].getX(), cells[0].getY());
			Point p1 = new Point(cells[1].getX(), cells[1].getY());
			Point p2 = new Point(cells[2].getX(), cells[2].getY());
			Point p3 = new Point(cells[3].getX(), cells[3].getY());
			
			cells[0] = new Cell(new Block(Color.RED, magic), new Point(p0.x+1, p0.y));
			cells[1] = new Cell(new Block(Color.RED, false), new Point(p1.x+2, p1.y+1));
			cells[2] = new Cell(new Block(Color.RED, false), new Point(p2.x+1, p2.y));
			cells[3] = new Cell(new Block(Color.RED, false), new Point(p3.x+2, p3.y+1));
			
			orientation2 = "ccw2";
		}
		
		if(orientation.equals("ccw2"))
		{
			super.transform(cells, 0, -1, -1);
			super.transform(cells, 1, 0, -2);
			super.transform(cells, 2, 1, -1);
			super.transform(cells, 3, 2, -2);
			
			orientation2 = "ccw3";
		}
		
		// Changes the red ZTetromino into a green STetromino.
		if(orientation.equals("ccw3"))
		{
			Point p0 = new Point(cells[0].getX(), cells[0].getY());
			Point p1 = new Point(cells[1].getX(), cells[1].getY());
			Point p2 = new Point(cells[2].getX(), cells[2].getY());
			Point p3 = new Point(cells[3].getX(), cells[3].getY());
			
			cells[0] = new Cell(new Block(Color.GREEN, magic), new Point(p0.x, p0.y+1));
			cells[1] = new Cell(new Block(Color.GREEN, false), new Point(p1.x-1, p1.y+2));
			cells[2] = new Cell(new Block(Color.GREEN, false), new Point(p2.x, p2.y+1));
			cells[3] = new Cell(new Block(Color.GREEN, false), new Point(p3.x-1, p3.y+2));
			
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
		IPolyomino clone = new SZTetromino(position, false);
		
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
