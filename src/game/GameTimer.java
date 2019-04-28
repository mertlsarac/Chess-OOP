package game;

public class GameTimer implements Runnable {
	private int time;
	private Game game;
	private boolean isRunning;
	
	public GameTimer() {
		time = 60;
		isRunning = false;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void reduceTime() {
		time--; 
	}
	
	public int getTime() {
		return time;
	}
	
	public void resetTime() {
		time = 60;
	}
	
	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	

	@Override
	public void run() {
		while(true) {
			if(isRunning) {
				reduceTime();
				game.repaint();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
}
