package application;

public class Anaconda extends Snake {
	private int length;
	private final int WIDTH=2;

	public Anaconda() {
		this.length = 1;
		this.point=0;
		
		description="Anaconda";
	}

	public double getPoint() {
		return point*WIDTH;
	}
	
	
}
