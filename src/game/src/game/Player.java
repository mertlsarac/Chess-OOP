package game;

import piece.*;

public class Player {
	private String name;
	private PieceColor playerColor;
	
	public Player(String name, PieceColor playerColor) {
		this.name = name; 
		this.playerColor = playerColor;
	}
	
	public PieceColor getColor() {
		return playerColor;
	}
	
	public String getName() {
		return name;
	}
}
