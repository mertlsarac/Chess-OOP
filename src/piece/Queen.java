package piece;

import java.awt.Point;
import java.util.ArrayList;

public class Queen extends Piece {
	private static String blackImagePath = "chesspieces/Chess_qdt60.png";
	private static String whiteImagePath = "chesspieces/Chess_qlt60.png";
	
	private String imagePath;
	
	public Queen(Point location, PieceColor pieceColor) {
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
		//To keep all possible movements
		ArrayList<Point> possibleMoves = getPossibleMoves();
		
		//Break the loop if there is a piece that block possible moves. 
		boolean flag = true;
		
		Point tempLocation = super.getLocation();
		
		//add bishop and rook possibleMoves algorithms
		
		while(tempLocation.x > 1 && tempLocation.y > 1 && flag) {
			tempLocation= new Point(tempLocation.x - 1, tempLocation.y - 1);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		
		//reset the current location
		tempLocation = super.getLocation();
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
		
		//reset the tempLocation
		tempLocation = super.getLocation();
		//reset the flag
		flag = true;
		while(tempLocation.x < 8 && flag) {
			tempLocation = new Point(tempLocation.x + 1, tempLocation.y);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
			

		//reset the tempLocation
		tempLocation = super.getLocation();
		//reset the flag
		flag = true;
		while(tempLocation.y < 8 && flag) {
			tempLocation = new Point(tempLocation.x, tempLocation.y + 1);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		
		//reset the tempLocation
		tempLocation = super.getLocation();
		//reset the flag
		flag = true;
		while(tempLocation.x > 1 && flag) {
			tempLocation = new Point(tempLocation.x - 1, tempLocation.y);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
			
		
		//reset the tempLocation
		tempLocation = super.getLocation();
		//reset the flag
		flag = true;
		while(tempLocation.y > 1 && flag) {
			tempLocation = new Point(tempLocation.x, tempLocation.y - 1);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		
		return possibleMoves;
	}
}
