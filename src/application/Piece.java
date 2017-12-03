package application;

import javafx.scene.shape.Circle;

public class Piece extends Circle {
	public int x;
	public int y;

	public Piece(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Piece(Piece piece) {
		this.x = piece.x;
		this.y = piece.y;
	}

	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setCoords(Piece piece) {
		this.x = piece.x;
		this.y = piece.y;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("X:").append(this.x).append(" ");
		sb.append("Y:").append(this.y).append("\n");
		return sb.toString();
	}

}