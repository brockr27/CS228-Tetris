package impl;

import java.awt.Point;
import java.util.List;

import api.Cell;
import api.GameStatus;
import api.IGame;
import api.IGameIcon;
import api.IPolyomino;
import api.IPolyominoGenerator;

/**
 * A partial implementation of the IGame interface for 
 * Tetris-like falling block games. Subclasses must implement
 * the determineCellsToCollapse() and determineScore() methods.
 */
public abstract class AbstractBlockGame implements IGame
{
  /**
   * Width of the game grid.
   */
  protected static final int WIDTH = 12;

  /**
   * Height of the game grid.
   */
  protected static final int HEIGHT = 24;

  /**
   * The polyomino that is subject to motion during the step() method
   * or via invocations of the shiftXXX() or rotate() methods.
   */
  protected IPolyomino current;

  /**
   * A WIDTH x HEIGHT grid of cells that may be occupied by either
   * the current polyomino or by frozen polyominoes (that can no longer
   * be moved).  Unoccupied cells are null.
   */
  protected IGameIcon[][] grid;

  /**
   * Status of the game after each invocation of step(), as described
   * in the GameStatus documentation.
   */
  private GameStatus gameStatus;

  /**
   * Generator for new polyominoes.  The BasicGenerator implementation
   * will uniformly select one of the seven tetromino types.
   */
  private IPolyominoGenerator generator;

  /**
   * State variable indicating which blocks to be deleted when the
   * status is COLLAPSING.  The implementation maintains the invariant that
   * cellsToCollapse.size() is nonzero if and only if gameStatus is COLLAPSING.
   */
  private List<Point> cellsToCollapse;

  /**
   * Constructs a new AbstractBlockGame.
   */
  protected AbstractBlockGame(IPolyominoGenerator generator)
  {
    grid = new IGameIcon[getHeight()][getWidth()];
    this.generator = generator;
    current = generator.getNext();
    gameStatus = GameStatus.NEW_POLYOMINO;
  }

  /**
   * Returns a list of locations for all cells that form part of
   * a collapsible group.  This list may contain duplicates.
   * @return list of locations for cells to be collapsed
   */
  protected abstract List<Point> determineCellsToCollapse();
  
  /**
   * Returns the current score.
   * @return the current score
   */
  protected abstract int determineScore();

  @Override
  public int getHeight()
  {
    return HEIGHT;
  }

  @Override
  public IGameIcon getCell(int row, int col)
  {
    return grid[row][col];
  }

  @Override
  public IPolyomino getCurrent()
  {
    if (gameStatus == GameStatus.COLLAPSING || gameStatus == GameStatus.GAME_OVER)
    {
      throw new IllegalStateException();
    }
    return current;
  }

  @Override
  public int getWidth()
  {
    return WIDTH;
  }

  @Override
  public Point[] getCellsToCollapse()
  {
    if (cellsToCollapse.size() == 0)
    {
      throw new IllegalStateException();
    }
    return cellsToCollapse.toArray(new Point[cellsToCollapse.size()]);
  }

  @Override
  public boolean transform()
  {
    boolean ret = canTransform();
    if (ret)
    {
      current.transform();
    }
    return ret;
  }

  @Override
  public void cycle()
  {
    current.cycle();
  }
  
  @Override
  public boolean shiftLeft()
  {
    boolean ret = canShiftLeft();
    if (ret)
    {
      current.shiftLeft();
    }
    return ret;
  }

  @Override
  public boolean shiftRight()
  {
    boolean ret = canShiftRight();
    if (ret)
    {
      current.shiftRight();
    }
    return ret;
  }

  @Override
  public int getScore()
  {
    return determineScore();
  }

  @Override
  public boolean gameOver()
  {
    return gameStatus == GameStatus.GAME_OVER;
  }

  @Override
  public GameStatus step()
  {
    switch (gameStatus)
    {
      case GAME_OVER:
        // do nothing
        break;
      case NEW_POLYOMINO:
      case FALLING:
        if (gameStatus == GameStatus.NEW_POLYOMINO)
        {
          gameStatus = GameStatus.FALLING;
        }
        if (canShiftDown())
        {
          current.shiftDown();
        }
        else
        {
          // Add blocks of the current polyomino to the grid, maybe 
          // temporarily, in order to check whether it has completed
          // a collapsible group
          for (Cell c : current.getCells())
          {
            int x = c.getX();
            int y = c.getY();
            if (y >= 0 && y < HEIGHT && x >= 0 && x < WIDTH)
            {
              grid[y][x] = c.getIcon();
            }
          }
          cellsToCollapse = determineCellsToCollapse();
          if (cellsToCollapse.size() != 0)
          {
            // current polyomino completes a collapsible group,
            // so prepare to collapse
            gameStatus = GameStatus.COLLAPSING;
          }
          else
          {
            // current polyomino is stopped, but has not completed a
            // collapsible group, so it might be moved sideways; 
            // take its blocks back out of the grid
            for (Cell c : current.getCells())
            {
              int x = c.getX();
              int y = c.getY();
              if (y >= 0 && y < HEIGHT && x >= 0 && x < WIDTH)
              {
                grid[y][x] = null;
              }
            }
            gameStatus = GameStatus.STOPPED;
          }
        }
        break;
      case STOPPED:
        // If the polyomino was previously stopped, it still may be possible
        // to shift it downwards since it could have been moved to the side
        // during the last step
        if (canShiftDown())
        {
          current.shiftDown();
          gameStatus = GameStatus.FALLING;
        }
        else
        {
          // we only get in the stopped state when the polyomino doesn't complete
          // a collapsible group; start a new polyomino at the top
          for (Cell c : current.getCells())
          {
            int x = c.getX();
            int y = c.getY();
            if (y >= 0 && y < HEIGHT && x >= 0 && x < WIDTH)
            {
              grid[y][x] = c.getIcon();
            }
          }
          current = generator.getNext();
          if (collides(current))
          {
            gameStatus = GameStatus.GAME_OVER;
          }
          else
          {
            gameStatus = GameStatus.NEW_POLYOMINO;
          }
        }
        break;
      case COLLAPSING:
        collapseCells(cellsToCollapse); 
        cellsToCollapse = determineCellsToCollapse();       
        if (cellsToCollapse.size() == 0)
        {
          // done collapsing, try to start a new polyomino
          current = generator.getNext();
          if (collides(current))
          {
            gameStatus = GameStatus.GAME_OVER;
          }
          else
          {
            gameStatus = GameStatus.NEW_POLYOMINO;
          }
        }
        break;
    }
    return gameStatus;
  }

  /**
   * Determines whether the current polyomino can be shifted down. Does not
   * modify the game state.
   * @return true if the current polyomino can be shifted down, false otherwise
   */
  private boolean canShiftDown()
  {
    IPolyomino t = (IPolyomino) current.clone();
    t.shiftDown();
    return !collides(t);
  }

  /**
   * Determines whether the current polyomino can be shifted right. Does not
   * modify the game state.
   * @return true if the current polyomino can be shifted right, false otherwise
   */
  private boolean canShiftRight()
  {
    IPolyomino t = (IPolyomino) current.clone();
    t.shiftRight();
    return !collides(t);
  }

  /**
   * Determines whether the current polyomino can be shifted left. Does not
   * modify the game state.
   * @return true if the current polyomino can be shifted left, false otherwise
   */
  private boolean canShiftLeft()
  {
    IPolyomino t = (IPolyomino) current.clone();
    t.shiftLeft();
    return !collides(t);
  }

  /**
   * Determines whether the current polyomino can be transform. Does not
   * modify the game state.
   * @return true if the current polyomino can be transformed, false otherwise
   */
  private boolean canTransform()
  {
    IPolyomino t = (IPolyomino) current.clone();
    t.transform();
    return !collides(t);
  }

  /**
   * Determines whether the given polyomino overlaps
   * with the occupied cells of the grid, or extends beyond the sides
   * or bottom of the grid.  (A polyomino in its initial position
   * MAY extend above the grid.)
   *
   * @param t a polyomino
   * @return true if the cells of the given polyomino extend beyond the
   *   sides or bottom of the grid or overlap with any occupied cells of
   *   the grid
   */
  private boolean collides(IPolyomino t)
  {
    for (Cell c : t.getCells())
    {
      int x = c.getX();
      int y = c.getY();
      if (x < 0 || x > WIDTH - 1 || y > HEIGHT - 1)
      {
        return true;
      }

      // row, column
      if (y >= 0 && grid[y][x] != null)
      {
        return true;
      }
    }
    return false;
  }


  /**
   * Delete the blocks at the indicated positions and shift
   * blocks above them down.  Only blocks lying within a column
   * above a deleted block are shifted down.
   * @param cellsToCollapse list of locations of cells to 
   * collapse.  The list may contain duplicates.
   */
  private void collapseCells(List<Point> cellsToCollapse)
  {
    boolean[][] marked = new boolean[getHeight()][getWidth()];
    
    for (Point p : cellsToCollapse)
    {
      marked[p.y][p.x] = true;
    }
    for (int col = 0; col < WIDTH; ++col)
    {
      int start = HEIGHT - 1;
      boolean done = false;
      while (!done)
      {
        // go up the column and find the first marked block
        while (start > 0 && !marked[start][col])
        {
          --start;
        }
        if (marked[start][col])
        {
          // go past all the marked cells, setting them null
          int j = start;
          while (j >= 0 && marked[j][col])
          {
            grid[j][col] = null;
            marked[j][col] = false;
            --j;
          }
          if (j >= 0)
          {
            // found something non-marked, so
            // shift down everything at j and above
            int shift = start - j;
            for (int k = j; k >= 0; --k)
            {
              grid[k + shift][col] = grid[k][col];
              marked[k + shift][col] = marked[k][col];
            }
          }
          else 
          {
            done = true;
          }
        }
        else 
        {
          // no marked cells
          done = true;
        }
      }
    }
  }

}
