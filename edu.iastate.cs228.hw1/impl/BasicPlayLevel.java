package impl;
import api.IPlayLevel;

/**
 * Minimal implementation of IPlayLevel.
 */
public class BasicPlayLevel implements IPlayLevel
{
  private int[] scores = {5, 12, 20, 32};
  private int[] speeds = {800, 600, 400, 200, 100};
  private int[] fastDropSpeeds = {80, 60, 40, 20, 5};
  
  @Override
  public int fastDropSpeed(int score)
  {
    int i = 0;
    while (i < scores.length && score >= scores[i])
    {
      ++i;
    }
    // score < scores[i]
    return fastDropSpeeds[i];
  }

  @Override
  public int speed(int score)
  {
    int i = 0;
    while (i < scores.length && score >= scores[i])
    {
      ++i;
    }
    // score < scores[i]
    return speeds[i];
  }

}
