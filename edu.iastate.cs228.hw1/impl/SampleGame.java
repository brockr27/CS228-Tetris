package impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample implementation of AbstractBlockGame for experimentation.
 */
public class SampleGame extends AbstractBlockGame
{
	/**
	 * Amount of rows collapsed
	 */
	int scoreCount = 0;
	
	/**
	 * Constructs a SampleGame instance.
	 */
	public SampleGame()
  	{
	  	super(new SampleGenerator()); 
  	}
  
  	@Override
  	protected List<Point> determineCellsToCollapse()
	{
		List<Point> cellsToCollapse = new ArrayList<Point>();
		int magicRow = 24;
		int magicBlocks = 0;
		
		//For every row in the grid, starting on the bottom
		for(int i = super.getHeight()-1; i >= 0; i--)
		{
			//For every column in the row
			for(int j = 0; j <= super.getWidth()-1; j++)
			{
				//Check if that cell isn't empty. If it isn't, add the cell point to the list
				if(super.getCell(i, j) != null)
				{
					Point p = new Point(i, j);
					cellsToCollapse.add(p);
					//If the cell is magic, up the magicBlocks count
					if(super.getCell(i, j).isMagic())
					{
						magicBlocks++;
						//If there are at least 3 magic blocks, mark the row that this occurs in
						if(magicBlocks >= 3)
							magicRow = i;
					}
				}
			}
		}
		//If cellsToCollapse doesn't have 12 objects, remove the ones it has. 
		//Otherwise, up the scoreCount by one.
		if(cellsToCollapse.size() != 12)
		{
			for(int i = 0; i <= cellsToCollapse.size(); i++)
				cellsToCollapse.remove(cellsToCollapse.size()-1);
			cellsToCollapse.remove(0);
			magicBlocks = 0;
		}
		else
			scoreCount++;
		
		
		//If there's been at least 3 magic blocks, call the gravity method and combine the lists
		if(magicBlocks >= 3) 
		{
			List<Point> cellsToCollapse2 = determineCellsToCollapseGravity(magicRow);
			for(int i = cellsToCollapse.size(); i < cellsToCollapse2.size(); i++) 
				cellsToCollapse.add(cellsToCollapse2.get(i));
		}
		
		return cellsToCollapse;
	}

  	@Override
  	protected int determineScore()
  	{
  		return scoreCount;
  	}
  
  	private List<Point> determineCellsToCollapseGravity(int magicRow) 
	{
		List<Point> cellsToCollapse2 = new ArrayList<Point>();
		
		//For every row in the grid
		for(int i = 24; i < super.getHeight(); i--) 
		{
			//For every column in the row
			for(int j = 0; j < super.getWidth(); j++) 
			{
				//Check if the cell above this one is empty. If it is, add the point to the list.
				if(super.getCell(i-1, j) == null)
				{
					Point p = new Point(i-1, j);
					cellsToCollapse2.add(p);
				}
			}
		}
		return cellsToCollapse2;
	}

}
