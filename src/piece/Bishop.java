package piece;

import java.awt.Point;
import java.util.ArrayList;

public class Bishop extends Piece {
	private static String blackImagePath = "chesspieces/Chess_bdt60.png";
	private static String whiteImagePath = "chesspieces/Chess_blt60.png";
	
	private String imagePath;
	
	public Bishop(Point location, PieceColor pieceColor) {
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
		
		resetPossibleMoves();
		ArrayList<Point> possibleMoves = getPossibleMoves();
		//Break the loop if there is a piece that block possible moves. 
		
		Point tempLocation = getLocation();
		
		boolean flag = true;
		while(tempLocation.x > 1 && tempLocation.y > 1 && flag) {
			tempLocation = new Point(tempLocation.x - 1, tempLocation.y - 1);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		
		//reset the current location
		tempLocation = getLocation();
		//reset the flag
		flag = true;
		while(tempLocation.x < 8 && tempLocation.y < 8 && flag) {
			tempLocation = new Point(tempLocation.x + 1, tempLocation.y + 1);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		
		//reset the current location
		tempLocation = super.getLocation();
		//reset the flag
		flag = true;
		
		while(tempLocation.x < 8 && tempLocation.y > 1 && flag) {
			tempLocation = new Point(tempLocation.x + 1, tempLocation.y - 1);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		
		//reset the current location
		tempLocation = super.getLocation();
		//reset the flag
		flag = true;
		
		while(tempLocation.x > 1 && tempLocation.y < 8 && flag) {
			tempLocation = new Point(tempLocation.x - 1, tempLocation.y + 1);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		return possibleMoves;
	}
}
