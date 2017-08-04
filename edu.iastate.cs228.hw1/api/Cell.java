package api;

import java.awt.Point;

/**
 * Container for an IGameIcon and a location.
 */
public class Cell
{
  /**
   * The IGameIcon represented by this Cell.
   */
  private IGameIcon icon;
  
  /**
   * The location of this Cell.
   */
  private Point position;
  
  /**
   * Constructs a Cell from the given icon and position. 
   * @param icon
   * @param position
   */
  public Cell(IGameIcon icon, Point position)
  {
    this.icon = icon;
    this.position = new Point(position);
  }
  
  /**
   * Copy constructor creates a deep copy of the given Cell.
   * @param existing the given Cell
   */
  public Cell(Cell existing)
  {
    this.icon = (IGameIcon) existing.getIcon().clone();
    this.position = new Point(existing.position);
  }
    
  /**
   * Returns the x-coordinate for this cell.
   * @return the x-coordinate for this cell
   */
  public int getX()
  {
    return position.x;
  }
  
  /**
   * Returns the y-coordinate for this cell.
   * @return the y-coordinate for this cell
   */
  public int getY()
  {
    return position.y;
  }
  
  /**
   * Sets the x-coordinate for this cell.
   * @param x the new x-coordinate
   */
  public void setX(int x)
  {
    position.x = x;
  }
  
  /**
   * Sets the y-coordinate for this cell.
   * @param y the new y-coordinate
   */
  public void setY(int y)
  {
    position.y = y;
  }
  
  /**
   * Returns the icon associated with this Cell.
   * @return the icon associated with this Cell
   */
  public IGameIcon getIcon()
  {
    return icon;
  }
  
  /**
   * Sets the icon associated with this Cell.
   * @param b the new icon
   */
  public void setIcon(IGameIcon b)
  {
    icon = b;
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if (obj == null || obj.getClass() != this.getClass())
    {
      return false;
    }
    Cell other = (Cell) obj;
    return position.equals(other.position) && icon.equals(other.icon);
  }
}
