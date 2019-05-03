package piece;

import java.awt.*;
import java.util.ArrayList;

import game.*;

public abstract class Piece {
	private Point location;
	private PieceColor pieceColor;
	
	private Board board;
	
	private ArrayList<Point> possibleMoves;
	
	public abstract String getImagePath();
	
	public abstract ArrayList<Point> getPossibleMoves(ArrayList<Piece> pieces);
	
	public Piece(Point location, PieceColor pieceColor) {
		this.location = location;
		this.pieceColor = pieceColor;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void move(Point location) {
		this.location = location;
	}
	
	public Point getLocation() {
		return location.getLocation();
	}
	
	public PieceColor getColor() {
		return pieceColor;
	}
	
	public ArrayList<Point> getPossibleMoves() {
		return possibleMoves;
	}
	
	public void addPossibleMove(Point point) {
		possibleMoves.add(point);
	}
	
	public void resetPossibleMoves() {
		possibleMoves = new ArrayList<Point>();
	}
	
	public void setPossibleMoves(ArrayList<Point> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}
	
}
