package utils;

public class Position {

	private int pos;
	
	public Position() {
		pos = 1;
	}
	
	public final void next() {
		pos++;
	}
	
	public final int getPos() {
		return pos;
	}

}
