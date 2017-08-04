package impl;

import java.awt.Color;

import api.IGameIcon;

/**
 * Simple implementation an IGameIcon as a colored block.
 * Two blocks match if they have the same color.
 */
public class Block implements IGameIcon
{
  /**
   * The color associated with this block.
   */
  private final Color color;
  
  /**
   * The magic status of this block.
   */
  private boolean magic;
  
  /**
   * Constructs a Block with the given color in the unmarked state.
   * @param c color to be associated with this block
   */
  public Block(Color c)
  {
    this.color = c;
  }

  public Block(Color c, boolean magic)
  {
    this.color = c;
    this.magic = magic;
  }
  
  @Override
  public Color getColorHint()
  {
    return color;
  }

  @Override
  public boolean matches(IGameIcon block)
  {
    return (block != null && block.getColorHint() == this.color);
  }
  
  @Override
  public boolean isMagic()
  {
    return magic;
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if (obj == null || obj.getClass() != this.getClass())
    {
      return false;
    }
    Block other = (Block) obj;
    return color == other.color && magic == other.magic;
  }
  
  @Override
  public Block clone()
  {
    try
    {
      // since Color is immutable and magic is primitive, there is
      // nothing else to do but copy the fields
      return (Block) super.clone();
    }
    catch (CloneNotSupportedException e)
    {
      // can't happen
      return null;
    }
  }
}
