
package impl;

import java.awt.Point;
import java.util.Random;

import api.IPolyomino;
import api.IPolyominoGenerator;

/**
 * Example of a generator for IPolyomino objects
 */
public class SampleGenerator implements IPolyominoGenerator
{
  @Override
  public IPolyomino getNext()
  {
    return new LTetromino(new Point(7, -1), false);
  }
  
  public boolean magicChance()
  {
	  boolean chance;
	  Random r = new Random();
	  int value = r.nextInt(9);
	  
	  if(value == 0)
		  chance = true;
	  else
		  chance = false;
	  
	  return chance;
  }
}
