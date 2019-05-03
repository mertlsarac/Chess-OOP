package game;

import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import piece.*;

public class Game extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private Board board;
	Player player1, player2, currentPlayer;
	
	private PieceColor possibleCheckMate; 
	
	//does the game still continue ? if there is no winner yet, set this null.
	private PieceColor isOver; 
	
	//number of columns and rows
	public final static int N = 8;
	
	//length of each frame
	public final static int n = 60;
	
	//when a player choose a piece
	private Piece selectedPiece;
	
	public Game(Board board) {
		this.board = board;
		this.addMouseListener(this);
		this.isOver = null;
		this.possibleCheckMate = null; 
	}
	
	public void setPossibleCheckMate(PieceColor possibleCheckMate) {
		this.possibleCheckMate = possibleCheckMate;
	}
	
	public void paint(Graphics g) {
		//fill the all board
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 580, 500);
		
		//fill the chess board
		g.setColor(new Color(125, 135, 150));
		g.fillRect(0, 0, 480, 480);
		
		//clear the frames which are supposed to be gray
		g.setColor(new Color(125, 135, 150));
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(i % 2 == j % 2) {
					g.clearRect(i * n, j * n, n, n);
				}
			}
		}
		
		//when the turn is for player1, the white pieces are allowed to choose
		if(currentPlayer.equals(player1))
			//make the player1 string to red
			g.setColor(Color.RED);
		//if the turn is for player2, make the player1 string to black
		else
			g.setColor(Color.BLACK);
		g.drawString(player1.getName(), 490, 460);
		
	
		if(currentPlayer.equals(player2))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(player2.getName(), 490, 20);
		
		//if there is a chosen piece, show its possible moves
		if(selectedPiece != null) {
			g.setColor(new Color(255, 253, 208));
			int x, y;
			
			if(selectedPiece.getClass().getSimpleName().equals("King")) {
				System.out.println("king");
				ArrayList<Point> kingPossibleMoves = new ArrayList<Point>(); 
				for(Point kingMoves : selectedPiece.getPossibleMoves(board.getPieces())) {
					boolean flag = true;
					for(Piece piece : board.getPieces()) {
						if(piece.getColor() != selectedPiece.getColor()) {
							for(Point enemyMoves : piece.getPossibleMoves(board.getPieces())) {
								if(enemyMoves.x == kingMoves.x && enemyMoves.y == kingMoves.y) {
									flag = false;
								}
							}
						}
					}
					if(flag)
						kingPossibleMoves.add(kingMoves);
				}
				//get the possible moves of selectedPiece
				for(Point point : kingPossibleMoves) {
					x = (point.x - 1) * n;
					y = (point.y - 1) * n; 
					
					//fill these points yellow
					g.fillRect(x, y, n, n);
				}
			}
			else {
				//get the possible moves of selectedPiece
				for(Point point : selectedPiece.getPossibleMoves(board.getPieces())) {
					x = (point.x - 1) * n;
					y = (point.y - 1) * n; 

					//fill these points yellow
					g.fillRect(x, y, n, n);
				}
			}
		}
		
		//get all the pieces from Board class
		ArrayList<Piece> pieces = board.getPieces();
		String current = null;
		try {
			current = new java.io.File( "." ).getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for(Piece piece : pieces) {
			BufferedImage img = null;
			
			try {
				//get the image path of the piece
				img = ImageIO.read(new File(current + "/src/game/" + piece.getImagePath()));
			}
			catch(IOException e) {
				e.printStackTrace();
			}
					
			int x = piece.getLocation().x - 1;
			int y = piece.getLocation().y - 1;
			
			//draw this image to the chess board
			g.drawImage(img, x * n, y * n, n, n, null);
		}		
	}
	
	//when the game is over, call this method 
	public void endGame(PieceColor isOver) {
		//assign the winner color
		String winner = isOver.toString(); 
		
		//show the game over message
		JOptionPane.showMessageDialog(null,
			    winner + " won!",
			    "Game Over",
			    JOptionPane.PLAIN_MESSAGE);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		//get the player's click as long as the game continues
		if(isOver == null) {
			int x, y; 
			double ex, ey;
			
			//if there is selectedPiece but the chosen frame is out of the possible moves
			boolean flag = false;
			
			//assign the coordinate of frames which are chosen by the players
			ex = e.getPoint().getX();
			ey = e.getPoint().getY();
			
			//if there is a piece which is chosen
			if(selectedPiece != null) {  
				for(Point cell : selectedPiece.getPossibleMoves(board.getPieces())) {
					x = cell.x * Game.n;
					y = cell.y * Game.n;
					
					//check if the click is on the frame of possible moves
					if(ex < x && ex > (x - Game.n) && ey < y && ey > (y - Game.n)) {
						//check if there is an enemy piece at the moving point.
						board.isThereAnotherPiece(new Point(cell.x, cell.y));
						
						//move the selected piece on the chosen frame
						selectedPiece.move(new Point(cell.x, cell.y)); 
						
						//make this null in order to stop showing possible moves
						selectedPiece = null;
						
						
						flag = true;
						
						//check for check-mate
						isOver = board.checkMate(this);
						if(isOver != null) {
							repaint();
							endGame(isOver);
						}
						
						switchPlayer();
					}
					
				}
			}
			
			//if there is no selected piece or player choose another piece
			if(!flag) {
				selectedPiece = null;
				
				//check all pieces
				for(Piece piece : board.getPieces()) {
					if(piece.getColor() == currentPlayer.getColor()) {
						
						x = piece.getLocation().x * Game.n;
						y = piece.getLocation().y * Game.n;
						
						//if this piece is on the chosen frame
						if(ex < x && ex > (x - Game.n) && ey < y && ey > (y - Game.n)) {
							selectedPiece = piece;
						}
					}
				}
			}
			
			if(possibleCheckMate != null && possibleCheckMate.equals(currentPlayer.getColor())) {
				System.out.println("possibleCheckmate");
				if(possibleCheckMate.equals(PieceColor.WHITE))
					selectedPiece = board.getWhiteKing();
				else
					selectedPiece = board.getBlackKing();
				
				flag = true;
			}
			
			repaint();
			
		}
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}
	
	public void setPlayer2(Player player2) {
		this.player2 = player2; 
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public void switchPlayer() {
		if(currentPlayer.equals(player1))
			currentPlayer = player2;
		else if(currentPlayer.equals(player2))
			currentPlayer = player1;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setSize(580, 520);
		frame.setResizable(false);
		
	
		Container cont = frame.getContentPane();
		
		Game game = new Game(new Board());
		
		
		cont.add(game);
	
		frame.setLocationRelativeTo(null);
		
		frame.setBackground(Color.LIGHT_GRAY);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);;
		
		Player player1, player2;
		player1 = new Player("Player1", PieceColor.WHITE);
		player2 = new Player("Player2", PieceColor.BLACK);
		
		game.setPlayer1(player1);
		game.setPlayer2(player2);
		
		game.setCurrentPlayer(player1); 
		
	}
}
