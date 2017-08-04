package api;

/**
 * Interface for polyominoes used by Tetris-like games.  Each polyomino
 * has a position and, implicitly, a set of Cells. The initial position and cell 
 * locations are presumed to be given by a constructor, and thereafter the 
 * position can be modified by the shiftXXX() methods. The constructor also
 * fixes an ordering of the cells.  The getCells() method always returns the
 * cells in this ordering, and the cycle() method always uses this ordering.
 * No bounds checking is done in implementations of this interface; 
 * therefore, the position and the cells can have negative coordinates.  
 */
public interface IPolyomino extends Cloneable
{
  /**
   * Returns a new array of Cell objects representing the cells 
   * occupied by this polyomino with their absolute positions.
   * Note that modifications to array or Cell objects returned
   * by this method should NOT affect this polyomino.
   * @return the cells occupied by this polyomino
   */
  Cell[] getCells();
  
  /**
   * Shifts the position of this polyomino down (increasing the y-coordinate) 
   * by one.  No bounds checking is done.
   */
  void shiftDown();
  
  /**
   * Shifts the position of this polyomino left (decreasing the x-coordinate) 
   * by one.  No bounds checking is done.
   */
  void shiftLeft();
  
  /**
   * Shifts the position of this polyomino right (increasing the x-coordinate) 
   * by one.  No bounds checking is done. 
   */  
  void shiftRight();
  
  /**
   * Transforms this polyomino without altering its position
   * according to the rules of the game to be implemented.  
   * Typical operations are rotation and reflection. 
   * No bounds checking is done.
   */    
  void transform();
  
  /**
   * Cycles the icons within the cells of this polyomino.  Each 
   * icon is shifted forward to the next cell in the original ordering
   * of the cells.  The last icon wraps around to the first cell.  
   */
  void cycle();
  
  /**
   * Returns a deep copy of this object.
   * @return a deep copy of this object.
   */
  Object clone();
}
