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
		//PIECES
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
	
	public boolean checkAndAddToPossibleMove(Point location, Piece selectedPiece) {
		Piece blockerPiece;
		boolean flag = true;
		
		for(Piece piece : pieces) {
			if(piece.getLocation().x == location.x && piece.getLocation().y == location.y) {
				blockerPiece = piece;
				//if the blocker piece is enemy
				if(blockerPiece.getColor() != selectedPiece.getColor())
					selectedPiece.addPossibleMove(location);
				
				flag = false;
			}
		}
	
		//if there is no blocker
		if(flag) {
			selectedPiece.addPossibleMove(location);
			return true;
		}
		return false;
	}
	
	public void removePiece(Piece piece) {
		pieces.remove(piece);
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
	
	public PieceColor checkMate() {
		
		if(checkMateCondition(blackKing))
			return PieceColor.WHITE;
		
		if(checkMateCondition(whiteKing))
			return PieceColor.BLACK;
		
		return null;
	}
	
	//if there is any enemy piece that threats the king
	public boolean checkMateCondition(Piece king) {
		for(Piece piece : pieces) {
			if(piece.getColor() != king.getColor()) {
				for(Point point : piece.getPossibleMoves(pieces)) {
					if(point.x == king.getLocation().x && point.y == king.getLocation().y) {
						System.out.println("Condition 1");
						if(checkMateCondition2(king, piece) && checkMateCondition3(king, piece)) {
							return true;

						}
					}
				}

			}
		}
		System.out.println("asd");
		return false;
	}
	
	//if there is any friendly piece that can beat the enemy piece which threats except king
	public boolean checkMateCondition2(Piece king, Piece possibleCheckMatePiece) {
		System.out.println("Condition 2");
		for(Piece piece : pieces) {
			if(piece.getColor() != possibleCheckMatePiece.getColor()) {
				if(piece.getClass().getSimpleName().equals("Pawn")) {
					Pawn pawn = (Pawn) piece;
					pawn.getPawnThreats(pieces);
					for(Point point : pawn.getPawnThreats(pieces)) {
						if(point.x == possibleCheckMatePiece.getLocation().x && point.y == possibleCheckMatePiece.getLocation().y && !piece.equals(king)) {
							return false;
						}
					}
				}
				else {
					for(Point point : piece.getPossibleMoves(pieces)) {
						if(point.x == possibleCheckMatePiece.getLocation().x && point.y == possibleCheckMatePiece.getLocation().y && !piece.equals(king)) {
							return false;
						}
					}
				}
				
			}
		}
		return true;
	}
	
	//if there is no friendly piece, check that can king beat the enemy piece which threats 
	public boolean checkMateCondition3(Piece king, Piece possibleCheckMatePiece) {
		Point previousKingLocation = king.getLocation();
		for(Point point : king.getPossibleMoves(pieces)) {
			for(Piece piece : pieces) {
				if(piece.getColor() != king.getColor() && piece.getLocation().x == point.x && piece.getLocation().y == point.y) {
					//keep the king's previous location
					
					//move the king the location in order to check any threats coming from this location
					king.move(piece.getLocation());
					
					for(Piece piece2 : pieces) {
						for(Point point2 : piece2.getPossibleMoves(pieces)) {
							if(king.getLocation().x == point2.x && king.getLocation().y == point2.y) {
								king.move(previousKingLocation);
								return true; 
							}
						}
					}
				}
			}
		}
		king.move(previousKingLocation);
		return false;
	}
}
