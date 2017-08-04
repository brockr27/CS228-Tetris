
package api;

import java.awt.Point;

/**
 * Factory interface for creating polyominoes.
 */
public interface IPolyominoFactory
{
  /**
   * Returns an LTetromino in the given position.
   * @param position initial location of the center of rotation
   * @param magic true if the polyomino should include a magic block
   *   in its first cell
   */
  public IPolyomino getLTetromino(Point position, boolean magic);
  
  /**
   * Returns a JTetromino in the given position.
   * @param position initial location of the center of rotation
   * @param magic true if the polyomino should include a magic block
   *   in its first cell
   */
  public IPolyomino getJTetromino(Point position, boolean magic);
  
  /**
   * Returns an ITriomino in the given position.
   * @param position initial location of the center of rotation
   * @param magic true if the polyomino should include a magic block
   *   in its first cell
   */
  public IPolyomino getITriomino(Point position, boolean magic);
  
  /**
   * Returns an OTetromino in the given position.
   * @param position initial location of the upper left corner
   * @param magic true if the polyomino should include a magic block
   *   in its first cell
   */
  public IPolyomino getOTetromino(Point position, boolean magic);
  
  /**
   * Returns a TTetromino in the given position.
   * @param position initial location of the center of rotation
   * @param magic true if the polyomino should include a magic block
   *   in its first cell
   */
  public IPolyomino getTTetromino(Point position, boolean magic);
  
  /**
   * Returns an SZTetromino in the given position.
   * @param position initial location of the upper left corner
   * @param magic true if the polyomino should include a magic block
   *   in its first cell
   */
  public IPolyomino getSZTetromino(Point position, boolean magic);
}
