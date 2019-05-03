package piece;

import java.awt.Point;
import java.util.ArrayList;

public class Rook extends Piece {
	private static String blackImagePath = "chesspieces/Chess_rdt60.png";
	private static String whiteImagePath = "chesspieces/Chess_rlt60.png";
	
	private String imagePath;
	
	public Rook(Point location, PieceColor pieceColor) {
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
		Point tempLocation;
		resetPossibleMoves();
		
		ArrayList<Point> possibleMoves = getPossibleMoves();
		
		//reset the tempLocation
		tempLocation = getLocation();
		//reset the flag
		boolean flag = true;
		//(x++, y)
		while(tempLocation.x < 8 && flag) {
			tempLocation = new Point(tempLocation.x + 1, tempLocation.y);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
			

		//reset the tempLocation
		tempLocation = super.getLocation();
		//reset the flag
		flag = true;
		//(x, y++) 
		while(tempLocation.y < 8 && flag) {
			tempLocation = new Point(tempLocation.x, tempLocation.y + 1);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		
		//reset the tempLocation
		tempLocation = super.getLocation();
		//reset the flag
		flag = true;
		//(x--, y)
		while(tempLocation.x > 1 && flag) {
			tempLocation = new Point(tempLocation.x - 1, tempLocation.y);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
			
		
		//reset the tempLocation
		tempLocation = super.getLocation();
		//reset the flag
		flag = true;
		//(x, y--)
		while(tempLocation.y > 1 && flag) {
			tempLocation = new Point(tempLocation.x, tempLocation.y - 1);
			flag = getBoard().checkAndAddToPossibleMove(tempLocation, this);
		}
		
		return possibleMoves;
	}
}
