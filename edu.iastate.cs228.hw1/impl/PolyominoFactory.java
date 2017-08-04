package impl;

import java.awt.Point;

import api.IPolyomino;
import api.IPolyominoFactory;

public class PolyominoFactory implements IPolyominoFactory 
{

	@Override
	public IPolyomino getLTetromino(Point position, boolean magic) 
	{
		return new LTetromino(position, magic);
	}

	@Override
	public IPolyomino getJTetromino(Point position, boolean magic) 
	{
		return new JTetromino(position, magic);
	}

	@Override
	public IPolyomino getITriomino(Point position, boolean magic) 
	{
		return new ITetromino(position, magic);
	}

	@Override
	public IPolyomino getOTetromino(Point position, boolean magic) 
	{
		return new OTetromino(position, magic);
	}

	@Override
	public IPolyomino getTTetromino(Point position, boolean magic) 
	{
		return new TTetromino(position, magic);
	}

	@Override
	public IPolyomino getSZTetromino(Point position, boolean magic) 
	{
		return new SZTetromino(position, magic);
	}

}
