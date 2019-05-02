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
	
	//Is the game still continue ?
	private PieceColor isOver; 
	
	public final static int N = 8;
	public final static int n = 60;
	
	private Piece selectedPiece;
	
	public Game(Board board) {
		this.board = board;
		this.addMouseListener(this);
		this.isOver = null;
	}
	
	public void paint(Graphics g) {
		//Draw Board
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 580, 500);
		
		g.setColor(new Color(125, 135, 150));
		g.fillRect(0, 0, 480, 480);
		
		g.setColor(new Color(125, 135, 150));
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(i % 2 == j % 2) {
					g.clearRect(i * n, j * n, n, n);
				}
			}
		}
		
		if(currentPlayer.equals(player1))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(player1.getName(), 490, 460);
		
		if(currentPlayer.equals(player2))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		
		g.drawString(player2.getName(), 490, 20);
		
		//Draw possibleMoves
		if(selectedPiece != null) {
			g.setColor(new Color(255, 253, 208));
			
			int x, y;
			for(Point point : selectedPiece.getPossibleMoves(board.getPieces())) {
				x = (point.x - 1) * n;
				y = (point.y - 1) * n; 
				
				g.fillRect(x, y, n, n);
			}
		}
		
		//Draw pieces
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
				img = ImageIO.read(new File(current + "/src/game/" + piece.getImagePath()));
			}
			catch(IOException e) {
				e.printStackTrace();
			}
					
			int x = piece.getLocation().x - 1;
			int y = piece.getLocation().y - 1;

			g.drawImage(img, x * n, y * n, n, n, null);
		}		
	}
	
	public void endGame(PieceColor isOver) {
		String winner = isOver.toString(); 
		JOptionPane.showMessageDialog(null,
			    winner + " won!",
			    "Game Over",
			    JOptionPane.PLAIN_MESSAGE);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if(isOver == null) {
			int x, y; 
			double ex, ey;
			
			ex = e.getPoint().getX();
			ey = e.getPoint().getY();
			
			boolean flag = false;
			if(selectedPiece != null) {  
				for(Point cell : selectedPiece.getPossibleMoves(board.getPieces())) {
					x = cell.x * Game.n;
					y = cell.y * Game.n;
					
					if(ex < x && ex > (x - Game.n) && ey < y && ey > (y - Game.n)) {
						board.isThereAnotherPiece(new Point(cell.x, cell.y));
						selectedPiece.move(new Point(cell.x, cell.y)); 
						selectedPiece = null;
						flag = true;
						isOver = board.checkMate();
						if(isOver != null) {
							repaint();
							endGame(isOver);
						}
						switchPlayer();
					}
					
				}
			}
		
			if(!flag) {
				selectedPiece = null;
				for(Piece piece : board.getPieces()) {
					if(piece.getColor() == currentPlayer.getColor()) {
						
						x = piece.getLocation().x * Game.n;
						y = piece.getLocation().y * Game.n;
						
						if(ex < x && ex > (x - Game.n) && ey < y && ey > (y - Game.n)) {
							selectedPiece = piece;
						}
					}
				}
			}
			
			repaint();
			
		}
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
		frame.setSize(580, 500);
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
