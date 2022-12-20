package ru.vsu.sc.hmelevskoy_n_a.table.pieces;

import java.util.ArrayList;
import ru.vsu.sc.hmelevskoy_n_a.table.Position;
import ru.vsu.sc.hmelevskoy_n_a.table.SideColor;

public abstract class Figure {

	public SideColor color;
	public boolean notMoved;
	public Position pos;
	public int Value;
	
	public short imgSrc;
	
	abstract void loadImage() ;	

	
	public abstract ArrayList<Position> GetMoves(Figure[][] board);
}
