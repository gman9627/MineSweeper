package grid;

public class Location{

	private int row;
	private int col;
	
	public Location(int r, int c) {
		this.row = r;
		this.col = c;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public Location getAdjacentLocation(int direction) {
		int adjustedDirection = (direction + 22) % 360;
	    if (adjustedDirection < 0) {
	    	adjustedDirection += 360;
	    }
	    adjustedDirection = adjustedDirection / 45 * 45;
	    int dc = 0;
	    int dr = 0;
	    if (adjustedDirection == 90) {
	    	dc = 1;
	    }
	    else if (adjustedDirection == 135) {
	    	dc = 1;
	    	dr = 1;
	    }
	    else if (adjustedDirection == 180) {
	    	dr = 1;
	    }
	    else if (adjustedDirection == 225) {
	    	dc = -1;
	    	dr = 1;
	    }
	    else if (adjustedDirection == 270) {
	    	dc = -1;
	    }
	    else if (adjustedDirection == 315) {
	    	dc = -1;
	    	dr = -1;
	    }
	    else if (adjustedDirection == 0) {
	    	dr = -1;
	    }
	    else if (adjustedDirection == 45) {
	    	dc = 1;
	    	dr = -1;
	    }
	    return new Location(getRow() + dr, getCol() + dc);
	}
		
	public String toString()
	{
		return "(" + getRow() + ", " + getCol() + ")";
	}	
}
