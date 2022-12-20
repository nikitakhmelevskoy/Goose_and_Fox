package ru.vsu.sc.hmelevskoy_n_a.table.pieces;

import ru.vsu.sc.hmelevskoy_n_a.table.Position;
import ru.vsu.sc.hmelevskoy_n_a.table.SideColor;

import java.util.ArrayList;


public class Goose extends Figure {

	public Goose(SideColor col, int x, int y) {
		color = col;
		pos = new Position(x, y);
		Value = 100;
		loadImage();
	}

	public void loadImage() {
		imgSrc = 2;
	}

	public ArrayList<Position> GetMoves(Figure[][] board) {


		ArrayList<Position> moves = new ArrayList<>();

		int movesRight = 7 - this.pos.x;
		int movesLeft = this.pos.x;
		int movesUp = this.pos.y;
		int movesDown = 7 - this.pos.y;
		int i = 0;

		while(movesUp>2) {

			movesUp--;
			i++;

			if(board[pos.x][pos.y-i] == null){
				moves.add(new Position(this.pos.x ,this.pos.y-i));

			}

			if(board[pos.x][pos.y-i] != null) {
				if(board[this.pos.x][this.pos.y-i].color != this.color) {
					moves.add(new Position(this.pos.x ,this.pos.y-i));
				}
				break;
			}
		}

		i = 0;

		while(movesDown>0 ) {

			movesDown--;
			i++;
			if(board[pos.x][pos.y+i] == null) {
				moves.add(new Position(this.pos.x ,this.pos.y+i));
			}

			if(board[pos.x][pos.y+i] != null) {
				if(board[this.pos.x][this.pos.y+i].color != this.color) {
					moves.add(new Position(this.pos.x ,this.pos.y+i));
				}
				break;
			}
		}

		i = 0;

		while(movesLeft>2) {

			movesLeft--;
			i++;
			if(board[pos.x-i][pos.y] == null) {
				moves.add(new Position(this.pos.x-i ,this.pos.y));
			}

			if(board[pos.x-i][pos.y] != null) {
				if(board[this.pos.x-i][this.pos.y].color != this.color) {
					moves.add(new Position(this.pos.x-i ,this.pos.y));
				}
				break;
			}
		}

		i = 0;

		while(movesRight>3) {

			movesRight--;
			i++;
			if(board[pos.x+i][pos.y] == null) {
				moves.add(new Position(this.pos.x+i ,this.pos.y));
			}
			if(board[pos.x+i][pos.y] != null) {
				if(board[this.pos.x+i][this.pos.y].color != this.color) {
					moves.add(new Position(this.pos.x+i ,this.pos.y));
				}
				break;
			}
		}

		return moves;
	}
}
