package impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import impl.BasicGenerator;

/**
 * A class which creates a CS228Tetris game.
 * @author Brock Rikkers
 */
public class CS228Tetris extends AbstractBlockGame 
{
	/**
	 * Score counter for the game.
	 */
	private int score = 0;
	
	/**
	 * Constructs a CS228Tetris game using the BasicGenerator.
	 */
	public CS228Tetris() 
	{
		super(new BasicGenerator());
	}
	
	/**
	 * Method that determines which cells need to be collapsed by checking
	 * if each cell is occupied. If it is, it constructs a point with the same
	 * coordinates and adds the point to the list.
	 * @return a list of Point objects.
	 */
	public List<Point> determineCellsToCollapse() 
	{
		List<Point> cellsToCollapse = new ArrayList<Point>();
		List<Point> cellsToCollapse2 = new ArrayList<Point>();
		int magicBlocks = 0;
		int rowCount = 0;
		
		// Go through and determine if the cell is occupied and magic. 
		// If it is occupied, add the point to the list.
		for(int row = super.getHeight()-1; row >= 0; row--)
		{
			for(int col = 0; col <= super.getWidth()-1; col++)
			{
				if(super.getCell(row, col) != null)
				{
					Point p = new Point(col, row);
					cellsToCollapse2.add(p);
					rowCount++;
					
					if(super.getCell(row, col).isMagic())
					{
						magicBlocks++;
					}
				}
				else
				{
					magicBlocks = 0;
					rowCount = 0;
					break;
				}
			}
			
			// If there's 3 or more magic blocks, turn gravity mode on.
			if(magicBlocks >= 3)
			{
				List<Point> gravityCells = determineCellsToCollapseGravity();
				
				for(int i = 0; i <= gravityCells.size()-1; i++)
				{
					cellsToCollapse.add(gravityCells.get(i));
				}
			}
			
			// If the rowCount is 12 or more, then for every 12 add 1 to the score.
			// Then add the cells from that row into the overall cellsToCollapse array.
			if(rowCount >= 12)
			{
				int n = rowCount/12;
				score = score + n;
				
				for(int i = 0; i <= cellsToCollapse2.size()-1; i++)
				{
					cellsToCollapse.add(cellsToCollapse2.get(i));
				}
			}
			
			// Removes the cells from that row.
			for(int i = cellsToCollapse2.size()-1; i >= 0; i--)
			{
				cellsToCollapse2.remove(i);
			}
		}
		
		return cellsToCollapse;
	}
	
	/**
	 * Returns the score.
	 * @return an integer value representing the score
	 */
	public int determineScore() 
	{
		return score;
	}
	
	/**
	 * This is called if the previous call to determineCellsToCollapse found 
	 * 3 or more magic blocks, then this method checks whether there are empty 
	 * cells below any occupied cells. If there are, it adds a point with the 
	 * same coordinates to a list. 
	 * @return a list of points that will be added to the cellsToCollapse list
	 * in the method determineCellsToCollapse.
	 */
	private List<Point> determineCellsToCollapseGravity() 
	{
		List<Point> cellsToCollapseGravity = new ArrayList<Point>();
		
		for(int row = 0; row <= super.getHeight()-1; row++)
		{
			for(int col = 0; col <= super.getWidth()-1; col++)
			{
				if(super.getCell(row, col) != null)
				{
					for(int i = row; i <= super.getHeight()-1; i++)
					{
						if(super.getCell(i, col) == null)
						{
							Point p = new Point(col, i);
							cellsToCollapseGravity.add(p);
						}
					}
				}
			}
		}
		
		return cellsToCollapseGravity;
	}
}
