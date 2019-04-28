package piece;

import java.awt.*;
import java.util.ArrayList;

public class King extends Piece {
	private static String blackImagePath = "chesspieces/Chess_kdt60.png";
	private static String whiteImagePath = "chesspieces/Chess_klt60.png";
	
	private String imagePath;
	
	public King(Point location, PieceColor pieceColor) {
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
		Point tempLocation = getLocation();
		resetPossibleMoves();
		ArrayList<Point> possibleMoves = getPossibleMoves();
		 
		//reset tempLocation
		tempLocation = getLocation();
		if(tempLocation.x + 1 <= 8 && tempLocation.y + 1 <= 8) {
			tempLocation = new Point(tempLocation.x + 1, tempLocation.y + 1);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);			
		}


		//reset tempLocation
		tempLocation = getLocation();
		if(tempLocation.x + 1 <= 8) {
			tempLocation = new Point(tempLocation.x + 1, tempLocation.y);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}

		//reset tempLocation
		tempLocation = getLocation();
		if(tempLocation.x + 1 <= 8 && tempLocation.y - 1 >= 1) {
			tempLocation = new Point(tempLocation.x + 1, tempLocation.y - 1);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);			
		}

		//reset tempLocation
		tempLocation = getLocation();
		if(tempLocation.x - 1 >= 1 && tempLocation.y + 1 <= 8) {
			tempLocation = new Point(tempLocation.x - 1, tempLocation.y + 1);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}

		//reset tempLocation
		tempLocation = getLocation();
		if(tempLocation.x - 1 >= 1) {
			tempLocation = new Point(tempLocation.x - 1, tempLocation.y);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}

		//reset tempLocation
		tempLocation = getLocation();
		if(tempLocation.x - 1 >= 1 && tempLocation.y - 1 >= 1) {
			tempLocation = new Point(tempLocation.x - 1, tempLocation.y - 1);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}

		//reset tempLocation
		tempLocation = super.getLocation();
		if(tempLocation.y + 1 <= 8) {
			tempLocation = new Point(tempLocation.x, tempLocation.y + 1);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}

		 //reset tempLocation
		tempLocation = super.getLocation();
		if(tempLocation.y - 1 >= 1) {
			tempLocation = new Point(tempLocation.x, tempLocation.y - 1);
			getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}

		 
		return possibleMoves;
	}
}
