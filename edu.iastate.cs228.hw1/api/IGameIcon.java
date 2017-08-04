package api;

import java.awt.Color;

/**
 * Interface specifying an icon or block in a Tetris-like
 * game.
 */
public interface IGameIcon extends Cloneable
{
  /**
   * Returns the color associated with this block.
   * @return the color associated with this block
   */
  Color getColorHint();
  
  /**
   * Determines whether this block matches another block.  In most
   * cases this means that the associated colors match.
   * @param block the block to check
   * @return true if the other block is non-null and matches
   * this block
   */
  boolean matches(IGameIcon block);
  

  /**
   * Determines whether this block's state magic.
   * @return true if this block is magic, false otherwise
   */
  boolean isMagic();
  
  /**
   * Returns a copy of this object.
   * @return a copy of this object
   */
  Object clone();
}
