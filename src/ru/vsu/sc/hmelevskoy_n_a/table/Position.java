package ru.vsu.sc.hmelevskoy_n_a.table;

import java.io.Serializable;

public class Position implements Serializable {

	public int x;
	public int y;
	
	public Position(int ix , int iy) {
		x=ix;
		y=iy;
	}
}
