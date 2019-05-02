package piece;

import java.awt.Point;
import java.util.ArrayList;

public class Pawn extends Piece {
	private static String blackImagePath = "chesspieces/Chess_pdt60.png";
	private static String whiteImagePath = "chesspieces/Chess_plt60.png";
	
	private String imagePath;
	
	public Pawn(Point location, PieceColor pieceColor) {
		super(location, pieceColor);
		
		if(pieceColor == PieceColor.BLACK)
			imagePath = blackImagePath;
		else if(pieceColor == PieceColor.WHITE)
			imagePath = whiteImagePath;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public boolean pawnSpecialCondition(ArrayList<Piece> pieces, Point location) {
		for(Piece piece : pieces) {
			if(piece.getLocation().x == location.x && piece.getLocation().y == location.y) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ArrayList<Point> getPossibleMoves(ArrayList<Piece> pieces) {
		Point tempLocation = getLocation();
		PieceColor pieceColor = super.getColor();
		
		resetPossibleMoves();
		ArrayList<Point> possibleMoves = getPossibleMoves();
		
		if(pieceColor == PieceColor.BLACK) {
			tempLocation = new Point(tempLocation.x, tempLocation.y + 1);
			if(pawnSpecialCondition(pieces, tempLocation))
				getBoard().checkAndAddToPossibleMove(tempLocation, this);
			
			tempLocation = getLocation();
			if(getLocation().y == 2) {
				tempLocation = new Point(tempLocation.x, tempLocation.y + 2);
				if(pawnSpecialCondition(pieces, tempLocation) && pawnSpecialCondition(pieces, new Point(tempLocation.x, tempLocation.y - 1)))
					getBoard().checkAndAddToPossibleMove(tempLocation, this);
			}
			
			
			for(Piece piece : getBoard().getPieces()) {
				tempLocation = getLocation();
				if(tempLocation.x - 1 == piece.getLocation().x && tempLocation.y + 1 == piece.getLocation().y && piece.getColor() != getColor()) {
					tempLocation = new Point(tempLocation.x - 1, tempLocation.y + 1);
					getBoard().checkAndAddToPossibleMove(tempLocation, this);
				}
				
				tempLocation = getLocation();
				if(tempLocation.x + 1 == piece.getLocation().x && tempLocation.y + 1 == piece.getLocation().y && piece.getColor() != getColor()) {
					tempLocation = new Point(tempLocation.x + 1, tempLocation.y + 1);
					getBoard().checkAndAddToPossibleMove(tempLocation, this);
				}
			}
		}
			
		//reset the tempLocation
		tempLocation = getLocation();
		
		if(pieceColor == PieceColor.WHITE) {
			tempLocation = new Point(tempLocation.x, tempLocation.y - 1);
			if(pawnSpecialCondition(pieces, tempLocation))
				getBoard().checkAndAddToPossibleMove(tempLocation, this);

			//reset the location
			tempLocation = super.getLocation();
			if(getLocation().y == 7) {
				tempLocation = new Point(tempLocation.x, tempLocation.y - 2);
				if(pawnSpecialCondition(pieces, tempLocation) && pawnSpecialCondition(pieces, new Point(tempLocation.x, tempLocation.y + 1)))
					getBoard().checkAndAddToPossibleMove(tempLocation, this);
			}
			
			for(Piece piece : getBoard().getPieces()) {
				tempLocation = getLocation();
				if(tempLocation.x - 1 == piece.getLocation().x && tempLocation.y - 1 == piece.getLocation().y && piece.getColor() != getColor()) {
					tempLocation = new Point(tempLocation.x - 1, tempLocation.y - 1);
					getBoard().checkAndAddToPossibleMove(tempLocation, this);
				}
				
				tempLocation = getLocation();
				if(tempLocation.x + 1 == piece.getLocation().x && tempLocation.y - 1 == piece.getLocation().y && piece.getColor() != getColor()) {
					tempLocation = new Point(tempLocation.x + 1, tempLocation.y - 1);
					getBoard().checkAndAddToPossibleMove(tempLocation, this);
				}
			}
		}
		
		return possibleMoves;
	}
	
	public ArrayList<Point> getPawnThreats(ArrayList<Piece> pieces) {
		ArrayList<Point> possibleMoves = new ArrayList<Point>();
		if(getColor() == PieceColor.BLACK) {
			//x + 1 - y+1
			Point tempLocation = getLocation();
			tempLocation = new Point(tempLocation.x + 1, tempLocation.y + 1);
			
			boolean flag = true;
			for(Piece piece : pieces) {
				if(piece.getLocation().x == tempLocation.x && piece.getLocation().y == tempLocation.y && piece.getColor() == getColor())
					flag = false;
			}
			
			if(flag)
				possibleMoves.add(tempLocation);
			
			//x - y - 1
			tempLocation = getLocation();
			tempLocation = new Point(tempLocation.x - 1, tempLocation.y + 1);
			
			flag = true;
			for(Piece piece : pieces) {
				if(piece.getLocation().x == tempLocation.x && piece.getLocation().y == tempLocation.y && piece.getColor() == getColor())
					flag = false;
			}
			
			if(flag)
				possibleMoves.add(tempLocation);
		}
		
		else if(getColor() == PieceColor.WHITE) {
			//x + 1 , y - 1
			Point tempLocation = getLocation();
			tempLocation = new Point(tempLocation.x + 1, tempLocation.y - 1);
			
			boolean flag = true;
			for(Piece piece : pieces) {
				if(piece.getLocation().x == tempLocation.x && piece.getLocation().y == tempLocation.y && piece.getColor() == getColor())
					flag = false;
			}
			
			if(flag)
				possibleMoves.add(tempLocation);
			
			//x - 1, y - 1
			tempLocation = getLocation();
			tempLocation = new Point(tempLocation.x - 1, tempLocation.y - 1);
			
			flag = true;
			for(Piece piece : pieces) {
				if(piece.getLocation().x == tempLocation.x && piece.getLocation().y == tempLocation.y && piece.getColor() == getColor())
					flag = false;
			}
			
			if(flag)
				possibleMoves.add(tempLocation);
		}
		
		return possibleMoves;
	}
}
