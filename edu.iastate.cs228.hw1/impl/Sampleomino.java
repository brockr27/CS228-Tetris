package impl;

import java.awt.Color;
import java.awt.Point;

import api.Cell;
import api.IPolyomino;

/**
 * Sample implementation of IPolyomino with two cells.
 */
public class Sampleomino implements IPolyomino
{
  private static Color COLOR = Color.RED;
  private Point position;
  private Cell[] cells;
  
  /**
   * Constructs a Sampleomino at the given location. The 
   * first cell is at the given position and the second cell is
   * on its right.
   * @param p initial position of the polyomino
   */
  public Sampleomino(Point p)
  {
    position = new Point(p);
    cells = new Cell[2];
    cells[0] = new Cell(new Block(COLOR, false), position);
    cells[1] = new Cell(new Block(COLOR, false), new Point(position.x + 1, position.y));
  }
  
  @Override
  public Cell[] getCells()
  {
    Cell[] ret = new Cell[2];
    for (int i = 0; i < cells.length; ++i)
    {
      ret[i] = new Cell(cells[i]);
    }
    return ret;
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
    // TODO
  }

  @Override
  public void shiftRight()
  {
    // TODO
  }

  @Override
  public void transform()
  {
    // TODO 
  }
  
  @Override
  public void cycle()
  {
    // TODO
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj != null && obj.getClass() == this.getClass())
    {
      Sampleomino other = (Sampleomino) obj;
      if (position.equals(other.position) && cells.length == other.cells.length)
      {
        for (int i = 0; i < cells.length; ++i)
        {
          if (!cells[i].equals(other.cells[i]))
          {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }
  
  @Override
  public Sampleomino clone()
  {
    try
    {
      Sampleomino s = (Sampleomino) super.clone();
      
      // make it into a deep copy
      s.position = new Point(position);
      s.cells = new Cell[cells.length];
      for (int i = 0; i < cells.length; ++i)
      {
        s.cells[i] = new Cell(cells[i]);
      }
      return s;
    }
    catch (CloneNotSupportedException e)
    {
      // can't happen
      return null;
    }
  }
}
