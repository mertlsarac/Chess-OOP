package game;

import java.awt.Point;
import java.util.*;

import piece.*;

public class Board {
	private ArrayList<Piece> pieces;
	
	private Piece blackKing, whiteKing;
	
	public Board() {
		createComponents();
	}
	
	public void createComponents() {
		//CREATE PIECES
		pieces = new ArrayList<Piece>();
		
		//Black rooks
		pieces.add(new Rook(new Point(1, 1), PieceColor.BLACK));
		
		pieces.add(new Rook(new Point(8, 1), PieceColor.BLACK));
		
		//White rooks
		pieces.add(new Rook(new Point(1, 8), PieceColor.WHITE));
		
		pieces.add(new Rook(new Point(8, 8), PieceColor.WHITE));
		
		//Black knights
		pieces.add(new Knight(new Point(2, 1), PieceColor.BLACK));
		
		pieces.add(new Knight(new Point(7, 1), PieceColor.BLACK));
		
		//White knights
		pieces.add(new Knight(new Point(2, 8), PieceColor.WHITE));
		
		pieces.add(new Knight(new Point(7, 8), PieceColor.WHITE));
		
		//Black bishops
		pieces.add(new Bishop(new Point(3, 1), PieceColor.BLACK));
		
		pieces.add(new Bishop(new Point(6, 1), PieceColor.BLACK));
		
		//White bishops
		pieces.add(new Bishop(new Point(3, 8), PieceColor.WHITE));
		
		pieces.add(new Bishop(new Point(6, 8), PieceColor.WHITE));
		
		//Black queen
		pieces.add(new Queen(new Point(4, 1), PieceColor.BLACK));
		
		//White queen
		pieces.add(new Queen(new Point(4, 8), PieceColor.WHITE));
		
		//Black king
		blackKing = new King(new Point(5, 1), PieceColor.BLACK);
		pieces.add(blackKing);
		
		//White king
		whiteKing = new King(new Point(5, 8), PieceColor.WHITE);
		pieces.add(whiteKing);
		
		
		for(int i = 1; i <= Game.N; i++) {
			//Black pawns
			pieces.add(new Pawn(new Point(i, 2), PieceColor.BLACK));
			
			//White pawns
			pieces.add(new Pawn(new Point(i, 7), PieceColor.WHITE));
		}
		
		for(Piece piece : pieces)
			piece.setBoard(this);
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	
	//
	public boolean checkAndAddToPossibleMove(Point location, Piece selectedPiece) {
		
		Piece blockerPiece;
		boolean flag = true;
		
		for(Piece piece : pieces) {
			if(piece.getLocation().x == location.x && piece.getLocation().y == location.y) {
				blockerPiece = piece;
				//if the blocker piece is enemy
				if(blockerPiece.getColor() != selectedPiece.getColor())
					//add this frame to frame of selectedPiece
					selectedPiece.addPossibleMove(location);
				
				//if there is a blocker piece
				flag = false;
			}
		}
	
		//if there is no blocker
		if(flag) {
			//add this move to possibleMoves of selectedPiece
			selectedPiece.addPossibleMove(location);
			return true;
		}
		return false;
	}
	
	public void removePiece(Piece piece) {
		pieces.remove(piece);
	}
	
	public Piece getWhiteKing() {
		return whiteKing;
	}
	
	public Piece getBlackKing() {
		return blackKing;
	}
	
	public void isThereAnotherPiece(Point point) {
		int x, y;
		Piece removedPiece = null;
		for(Piece piece : getPieces()) {
			x = piece.getLocation().x;
			y = piece.getLocation().y;
			if(x == point.x && y == point.y) {
				removedPiece = piece;
			}
		}
		if(removedPiece != null)
			removePiece(removedPiece);
	}
	
	public PieceColor checkMate(Game game) {
		if(game.getCurrentPlayer().getColor() == PieceColor.WHITE) {
			
			if(checkMateCondition(blackKing, game)) {
				return PieceColor.WHITE;	
			}
		
		}
		
		else {
			if(checkMateCondition(whiteKing, game)) {
				return PieceColor.BLACK;
			}
				
		}
			
		
		return null;
	}
	
	//if there is any enemy piece that threats the king
	public boolean checkMateCondition(Piece king, Game game) {
		System.out.println(king.getColor().toString());
		game.setPossibleCheckMate(null);
		for(Piece piece : pieces) {
			if(piece.getColor() != king.getColor()) {
				for(Point point : piece.getPossibleMoves(pieces)) {
					if(point.x == king.getLocation().x && point.y == king.getLocation().y) {
						System.out.println("Condition 1");
						game.setPossibleCheckMate(king.getColor());
						if(checkMateCondition2(king, piece)) {
							return true;
						}
					}
				}

			}
		}
		return false;
	}
	
	//is there any free frame where the king can go ?
	public boolean checkMateCondition2(Piece king, Piece possibleCheckMatePiece) {
		System.out.println("condition2");
		boolean flag = true; 
		for(Piece piece : pieces) {
			if(piece.getColor() != king.getColor()) {
				for(Point kingMoves : king.getPossibleMoves(pieces)) {
					if(!checkMateCondition5(king, kingMoves))
						flag = false;
				}
			}
		}
		
		if(!flag)
			return false;
		
		return checkMateCondition3(king, possibleCheckMatePiece); 
	}
	
	//if there is any friendly piece that can beat the enemy piece which threats except king
	public boolean checkMateCondition3(Piece king, Piece possibleCheckMatePiece) {
		System.out.println("Condition 3");
		boolean flag = true;
		for(Piece piece : pieces) {
			if(piece.getColor() != possibleCheckMatePiece.getColor()) {
					for(Point point : piece.getPossibleMoves(pieces)) {
						if(point.x == possibleCheckMatePiece.getLocation().x && point.y == possibleCheckMatePiece.getLocation().y && !piece.equals(king)) {
							System.out.println(point.x + " " + point.y);
							flag = false; 
						}
					}
			}
		}
		
		if(!flag)
			return false; 
		
		return checkMateCondition4(king, possibleCheckMatePiece);
	}
	
	//check that can king beat the enemy piece which threats 
	public boolean checkMateCondition4(Piece king, Piece possibleCheckMatePiece) {
		System.out.println("condition4");
		
		//if the king can't beat the enemy piece
		boolean flag = true;
		
		for(Point point : king.getPossibleMoves(pieces)) {
			
			for(Piece piece : pieces) {
				
				//if the king can beat the enemy piece which threats
				if(piece.getColor() != king.getColor() && piece.getLocation().x == point.x && piece.getLocation().y == point.y) {
					
					//if the king beat this enemy piece
					flag = false;
					
					//if the king can beat the enemy piece which threats, check is there another enemy piece which has a possible move to this point
					if(checkMateCondition5(king, piece.getLocation()))
						flag = true; 
						
				}
				
			}
		}
		return flag;
	}
	
	//check is there another enemy when the king beat the piece which threats
	public boolean checkMateCondition5(Piece king, Point newKingLocation) {
		System.out.println("condition5");
		
		//keep previous location of the king
		Point previousKingPoint = king.getLocation();
		
		//pretend that the king move to the enemy piece
		king.move(newKingLocation);
		
		//check again if there is another enemy piece which can go to this frame
		for(Piece piece : pieces) {
			if(piece.getColor() != king.getColor()) {
				for(Point point : piece.getPossibleMoves(pieces)) {
					if(king.getLocation().x == point.x && king.getLocation().y == point.y) {
						
						//get the king to the his previous location
						king.move(previousKingPoint);
						
						//it is check-mate condition, return true
						return true;
					}
						
				}
			}
		}
		king.move(previousKingPoint);
		return false;
	}
}