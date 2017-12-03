package application;

public class Python extends Snake {
	private int length;
	
	private final int WIDTH=1;

	
	public Python() {
		this.length = 1;
		this.point=1;
		
		description="Python(x1)";
	}
	
	public double getPoint() {
		return point*WIDTH;		
	}
	
	public String getDescription() {
		return description;
	}
	
}
