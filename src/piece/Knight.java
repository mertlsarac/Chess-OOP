package piece;

import java.awt.Point;
import java.util.ArrayList;

public class Knight extends Piece {
	private static String blackImagePath = "chesspieces/Chess_ndt60.png";
	private static String whiteImagePath = "chesspieces/Chess_nlt60.png";
	
	private String imagePath;
	
	public Knight(Point location, PieceColor pieceColor) {
		super(location, pieceColor);
		
		if(pieceColor == PieceColor.BLACK)
			imagePath = blackImagePath;
		else if(pieceColor == PieceColor.WHITE)
			imagePath = whiteImagePath;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	@Override
	public ArrayList<Point> getPossibleMoves(ArrayList<Piece> pieces) {
		Point tempLocation = super.getLocation();
		resetPossibleMoves();
		ArrayList<Point> possibleMoves = getPossibleMoves();
		
		if(tempLocation.x + 1 <= 8 && tempLocation.y + 2 <= 8) {
			tempLocation = new Point(tempLocation.x + 1, tempLocation.y + 2);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		
		//reset tempLocation
		tempLocation = super.getLocation();
		if(tempLocation.x + 1 <= 8 && tempLocation.y - 2 >= 1) {
			tempLocation = new Point(tempLocation.x + 1, tempLocation.y - 2);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}

		//reset tempLocation
		tempLocation = super.getLocation();
		if(tempLocation.x - 1 >= 1 && tempLocation.y + 2 <= 8) {
			tempLocation = new Point(tempLocation.x - 1, tempLocation.y + 2);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		
		
		//reset tempLocation
		tempLocation = super.getLocation();
		if(tempLocation.x - 1 >= 1 && tempLocation.y - 2 >= 1) {
			tempLocation = new Point(tempLocation.x - 1, tempLocation.y - 2);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		

		//reset tempLocation
		tempLocation = super.getLocation();
		if(tempLocation.x + 2 <= 8 && tempLocation.y + 1 <= 8) {
			tempLocation = new Point(tempLocation.x + 2, tempLocation.y + 1);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);			
		}

		
		//reset tempLocation
		tempLocation = super.getLocation();
		if(tempLocation.x + 2 <= 8 && tempLocation.y - 1 >= 1) {
			tempLocation = new Point(tempLocation.x + 2, tempLocation.y - 1);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}

		
		//reset tempLocation
		tempLocation = super.getLocation();
		if(tempLocation.x - 2 >= 1 && tempLocation.y + 1 <= 8) {
			tempLocation = new Point(tempLocation.x - 2, tempLocation.y + 1);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);			
		}

		
		//reset tempLocation
		tempLocation = super.getLocation();
		if(tempLocation.x - 2 >= 1 && tempLocation.y - 1 <= 8) {
			tempLocation = new Point(tempLocation.x - 2, tempLocation.y - 1);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);			
		}

		return possibleMoves;
	}
	
	
}
