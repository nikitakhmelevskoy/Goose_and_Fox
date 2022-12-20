package ru.vsu.sc.hmelevskoy_n_a.table.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


import ru.vsu.sc.hmelevskoy_n_a.table.Menu;
import ru.vsu.sc.hmelevskoy_n_a.table.Position;
import ru.vsu.sc.hmelevskoy_n_a.table.SideColor;
import ru.vsu.sc.hmelevskoy_n_a.table.pieces.*;

public abstract class GamePanel extends JPanel{
	
	private final BufferedImage boardImg;
	public int SQUARE_SIZE =64;
	ArrayList<Position> possibleMoves;
	ArrayList<Position> lastMove;
	Position checkPosition;
	Figure selected;
	
	boolean endGame=false;
	protected Position focus;
	public Figure[][] piecesBoard;
	
	private final BufferedImage[] imgTable;
	
	public boolean blueMove;
	public boolean enabled;
	

	abstract void oponentTurn();
	
	public GamePanel() {
		boardImg = new BufferedImage(7*SQUARE_SIZE ,7*SQUARE_SIZE ,BufferedImage.TYPE_INT_ARGB);
		drawBoard();
		this.setPreferredSize(new Dimension(7*SQUARE_SIZE, 7*SQUARE_SIZE));
		
		piecesBoard = new Figure[19][19];
		imgTable = new BufferedImage[23];
		possibleMoves= new ArrayList<>();
		lastMove = new ArrayList<>();
		focus = new Position(0,0);
		blueMove= true;
		enabled =true;
		
		
		generatePieces();
		loadImages();
		MouseListner();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(boardImg , 0 ,0 ,7*SQUARE_SIZE,7*SQUARE_SIZE , null);
		

		if(checkPosition!=null) {
			g.setColor(new Color(255,0,0,128));
			g.fillRect(checkPosition.x*SQUARE_SIZE, checkPosition.y*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE );
		}

		
		g.setColor(new Color(0xCD7A7A7A, true));
		g.fillOval(focus.x*SQUARE_SIZE, focus.y*SQUARE_SIZE,SQUARE_SIZE , SQUARE_SIZE);
		for(int i=0 ; i<piecesBoard.length ;i++) {
			for(int j=0 ; j<piecesBoard[i].length;j++) {
				if(piecesBoard[i][j]==null) continue;
				else {
					g.drawImage(imgTable[piecesBoard[i][j].imgSrc],  i*SQUARE_SIZE,
							j*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE , null);
				}
			}
		}
		g.setColor(new Color(0,255,0,  192));
		if(selected!=null) {
			((Graphics2D) g).setStroke(new BasicStroke(4));
			g.drawOval(selected.pos.x*SQUARE_SIZE+2, selected.pos.y*SQUARE_SIZE+2, SQUARE_SIZE-4, SQUARE_SIZE-4 );
		}

		for(Position ch: possibleMoves) {
			if(piecesBoard[ch.x][ch.y]!= null) {
				if (selected != null && piecesBoard[ch.x][ch.y].color != selected.color) {
					g.setColor(new Color(255, 0, 0, 192));
					g.drawOval(ch.x * SQUARE_SIZE, ch.y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
				}
			}
			else {
				g.setColor(new Color(0,255,0,  192));
				g.fillOval(ch.x*SQUARE_SIZE+SQUARE_SIZE/4, ch.y*SQUARE_SIZE+SQUARE_SIZE/4,
						SQUARE_SIZE/2, SQUARE_SIZE/2);
				
			}
			
		}
	}
	
	private void drawBoard()
	{

		Graphics2D g = (Graphics2D) boardImg.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		g.setColor(Color.BLACK);
		for (int i = 0; i < 7; i++) {
			if (i < 2 || i > 4) {
				g.drawLine(3 * SQUARE_SIZE - SQUARE_SIZE / 2, i * SQUARE_SIZE + SQUARE_SIZE / 2, 5 * SQUARE_SIZE - SQUARE_SIZE / 2, i * SQUARE_SIZE + SQUARE_SIZE / 2);
			} else {
				g.drawLine(SQUARE_SIZE / 2, i * SQUARE_SIZE + SQUARE_SIZE / 2, 7 * SQUARE_SIZE - SQUARE_SIZE / 2, i * SQUARE_SIZE + SQUARE_SIZE / 2);
			}
		}

		for (int i = 0; i < 7; i++) {
			if (i < 2 || i > 4) {
				g.drawLine(i * SQUARE_SIZE + SQUARE_SIZE / 2, 3 * SQUARE_SIZE - SQUARE_SIZE / 2, i * SQUARE_SIZE + SQUARE_SIZE / 2, 5 * SQUARE_SIZE - SQUARE_SIZE / 2);
			} else {
				g.drawLine(i * SQUARE_SIZE + SQUARE_SIZE / 2, SQUARE_SIZE / 2, i * SQUARE_SIZE + SQUARE_SIZE / 2, 7 * SQUARE_SIZE - SQUARE_SIZE / 2);
			}
		}

	}

	private void generatePieces() {



		piecesBoard[2][2] =new Fox(SideColor.FOX ,2 ,2);

		for (int i = 2; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if(i == 4) {
					piecesBoard[j][i] = new Goose(SideColor.GOOSE, j, i);
				}
				if(i < 4 && (j == 0 || j == 6)) {
					piecesBoard[j][i] = new Goose(SideColor.GOOSE, j, i);
				}
				if(i > 4 && (j > 1 && j < 5)) {
					piecesBoard[j][i] = new Goose(SideColor.GOOSE, j, i);
				}
			}
		}
	}
	
	public void loadImages() {
		try {
			String source= "/ru/vsu/sc/hmelevskoy_n_a/resources/";

			imgTable[1]  = ImageIO.read(getClass().getResource(source+"Fox.png"));
			imgTable[2]  = ImageIO.read(getClass().getResource(source+"Goose.png"));

		}
		catch(IOException e)
		{
			System.err.println("Error");
			e.printStackTrace();
		}

	}


	public void MouseListner() {
		 addMouseListener(new MouseAdapter(){ 
	         public void mousePressed(MouseEvent me) { 
	        	 int tempX =me.getX()/SQUARE_SIZE;
	        	 int tempY =me.getY()/SQUARE_SIZE;
	        	 if((piecesBoard[tempX][tempY]==null && selected==null) || !enabled) return;
	        	 else if(selected==null) {
	            	 selected =piecesBoard[tempX][tempY];
	            	 if(selected.color==SideColor.GOOSE && !blueMove) {
	            		 selected=null;
	            		 return;
	            	 }
	            	 else if(selected.color==SideColor.FOX && blueMove) {
	            		 selected=null;
	            		 return;
	            	 }
	                 possibleMoves =preventCheck(selected.GetMoves(piecesBoard), piecesBoard , selected);
	                 repaint();
	        	 }else {
	        		 if(piecesBoard[tempX][tempY]!=null && piecesBoard[tempX][tempY].color== selected.color) {
	                	 selected =piecesBoard[tempX][tempY];
	                     possibleMoves =preventCheck(selected.GetMoves(piecesBoard), piecesBoard , selected);
	                     repaint();
	        		 }else {
	        			 
	        			 checkChessmanMove(new Position(tempX ,tempY));
	        			 selected=null;
	        			 possibleMoves.clear();
	        			 repaint();
	        		 }
	        		 
	        	 }
	           
	         }
	       }); 
		 
		 addMouseMotionListener(new MouseMotionListener() {
			 @Override
			public void mouseMoved(MouseEvent me) {
	        	 focus.x=me.getX()/SQUARE_SIZE;
	        	 focus.y=me.getY()/SQUARE_SIZE;
	        	 repaint();
			}
	
			@Override
			public void mouseDragged(MouseEvent arg0) {
			}
			 
		 });
		
	}

	public void checkChessmanMove(Position newPosition) {
		boolean contains= false;
		
		for(Position m : possibleMoves) {
			
			if(m.x==newPosition.x && m.y==newPosition.y) {
				contains=true;
				break;
			}
		}
	
		if(!contains) return;
		moveChessman(newPosition , selected.pos);
		oponentTurn();
			

	

	}

	public void moveChessman(Position newPosition ,Position oldPosition) {
		lastMove.clear();
		lastMove.add(oldPosition);
		lastMove.add(newPosition);
		Figure piece = piecesBoard[oldPosition.x][oldPosition.y];
		piecesBoard[newPosition.x][newPosition.y]=piece;
		piecesBoard[piece.pos.x][piece.pos.y] =null;
		piece.pos=newPosition;
		piece.notMoved = false;
		SideColor c =piece.color.swapColor();
		
	
		boolean isCheck =check(piecesBoard ,c);
		if(!isCheck) {
			checkPosition=null;
			checkStalemate(piece.color.swapColor() , piecesBoard);
		}
		else {
			checkPosition = findKing(piecesBoard ,c);
			checkmate(piece.color.swapColor() , piecesBoard);
		}
	}


	public static ArrayList<Position> getAllMoves(SideColor col , Figure[][] board){
		ArrayList<Position> moves = new ArrayList<>();
	
		for(int i = 0; i <=7; i++){
			for(int j = 0; j <=7; j++) {
				if(board[i][j] != null)
				{				
					if(board[i][j].color == col) moves.addAll(board[i][j].GetMoves(board));
				}			
			}
		}
		return moves;
	}
	
	public ArrayList<Position> getAllSafeMoves(SideColor col , Figure[][] board){
		ArrayList<Position> moves = new ArrayList<>();
	
		for(int i = 0; i <=7; i++){
			for(int j = 0; j <=7; j++) {
				if(board[i][j] != null)
				{				
					if(board[i][j].color == col) moves.addAll(preventCheck(board[i][j].GetMoves(board),board,board[i][j]));
				}			
			}
		}
		return moves;
	}
	public static Position findKing(Figure[][] board, SideColor col) {
		Position kingPosition = new Position(0,0);
		for(int i=0; i<board.length ; i++) {
			for(int j=0 ; j<board[i].length;j++) {
				if(board[i][j]!=null) {
					if(board[i][j] instanceof Fox && board[i][j].color==col){
						return new Position(i,j);
					}
				}
			}
		}
		return kingPosition;
	}

	public static boolean check(Figure[][] board, SideColor col) {

		Position kingPosition =findKing(board , col);
		SideColor c =col.swapColor();
		
		ArrayList<Position> enemyMoves =getAllMoves(c ,board);

		for(Position p : enemyMoves) {
			if(kingPosition.x==p.x &&kingPosition.y==p.y) {
				return true;
			}
		}
		
		return false;
	}

	public static ArrayList<Position> preventCheck(ArrayList<Position> moves , Figure[][] board, Figure piece){
		
		Iterator<Position> i = moves.iterator();
		while(i.hasNext()) {
			Position p = i.next();
			
			Figure[][] tempBoard = Arrays.stream(board).map(Figure[]::clone).toArray(Figure[][]::new);
			tempBoard[piece.pos.x][piece.pos.y]=null;
			tempBoard[p.x][p.y]=piece;
			boolean isCheck = check(tempBoard, piece.color);
			if(isCheck)
				i.remove();
		}			
		return moves;
	}

	public void checkmate(SideColor col , Figure[][] board) {
		ArrayList<Position> any = getAllSafeMoves(col , board);
		
		if(any.isEmpty()) {
			endGame=true;
			possibleMoves.clear();
			repaint();
			JOptionPane.showMessageDialog(null,col.getBetterString() +" King is checkmate. "+col.swapColor().getBetterString()+
					"s wins. " ,
					"Checkmate",JOptionPane.INFORMATION_MESSAGE);
			closeFrame();
		}
	}
	
	public void checkStalemate(SideColor col , Figure[][] board) {
		ArrayList<Position> any = getAllSafeMoves(col , board);
		
		if(any.isEmpty()) {
			possibleMoves.clear();
			repaint();
			JOptionPane.showMessageDialog(null,col.getBetterString() +"s have no more available moves. The game ends with a draw. " ,
					"Stalemate",JOptionPane.INFORMATION_MESSAGE);
			closeFrame();
			
		}
		
	}
	public void closeFrame() {
		new Menu();
		SwingUtilities.windowForComponent(this).dispose();
	}

}
