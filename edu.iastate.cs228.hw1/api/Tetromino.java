package api;

import java.awt.Point;

import impl.Block;

/**
 * The Tetromino superclass which executes all the actual common methods of each 
 * specific Tetromino class.
 * @author Brock Rikkers
 */
public class Tetromino 
{
	/**
	 * This method is called by the specific Tetromino class in order to 
	 * transform the Tetromino.
	 * @param an array of cells representing the Tetromino.
	 * @param an integer denoting which spot in the cells array to change.
	 * @param an integer denoting how much to increment the X value of the cell. 
	 * May be positive or negative.
	 * @param an integer denoting how much to increment the Y value of the cell. 
	 * May be positive or negative.
	 * @return the parameter cell array with the changed values.
	 */
	public Cell[] transform(Cell[] cells, int arraySpot, int incrementX, int incrementY)
	{
		cells[arraySpot].setX(cells[arraySpot].getX() + incrementX);
		cells[arraySpot].setY(cells[arraySpot].getY() + incrementY);
		
		return cells;
	}
	
	/**
	 * This method is called by the specific Tetromino class in order to 
	 * cycle the Tetromino's magic block.
	 * @param an array of cells representing the Tetromino.
	 * @return the parameter cell array with the changed magic block.
	 */
	public Cell[] cycle(Cell[] cells)
	{
		int pos = 0;
		
		for(int i = 0; i <= cells.length-1; i++)
		{
			if(cells[i].getIcon().isMagic())
			{
				pos = i;
			}
		}
		
		// Makes the original magic block non-magic.
		cells[pos] = new Cell(new Block(cells[pos].getIcon().getColorHint(), false), new Point(cells[pos].getX(), cells[pos].getY()));
		
		// If the magic block is not the last one in the array, then make the 
		// next one magic. Otherwise, make the first block magic. 
		if(pos != cells.length-1)
		{
			cells[pos+1] = new Cell(new Block(cells[pos].getIcon().getColorHint(), true), new Point(cells[pos+1].getX(), cells[pos+1].getY()));
		}
		else
		{
			cells[0] = new Cell(new Block(cells[pos].getIcon().getColorHint(), true), new Point(cells[0].getX(), cells[0].getY()));
		}
		
		return cells;
	}
	
	/**
	 * This method is called by the specific Tetromino class in order to 
	 * compare two Tetrominos. 
	 * @param an array of cells representing the original Tetromino.
	 * @param an array of cells representing the Tetromino that the original
	 * will be compared too.
	 * @return a boolean value. True if the two Tetrominos are equal, false if
	 * they are not.
	 */
	public boolean equals(Cell[] cellsOriginal, Cell[] cellsOther)
	{
		boolean b = false;
		
		for(int i = 0; i <= cellsOriginal.length; i++)
		{
			
			// Compares the X and Y values for each cell.
			if(cellsOriginal[i].getX() == cellsOther[i].getX() && cellsOriginal[i].getY() == cellsOther[i].getY())
			{
				
				// Compares the magic state for each cell.
				if(cellsOriginal[i].getIcon().isMagic() == cellsOther[i].getIcon().isMagic())
				{
					
					// Compares the color of each Tetromino.
					if(cellsOriginal[i].getIcon().getColorHint().equals(cellsOther[i].getIcon().getColorHint()))
					{
						b = true;
					}
				}
			}
		}	
		return b;
	}
}
