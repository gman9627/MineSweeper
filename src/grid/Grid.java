package grid;

import java.util.ArrayList;

public class Grid {
	
	public static enum Cover {
		CLOSED, OPEN, FLAGGED;
	}

	public final static int BOMB = -1;
	
	private int[][] numArray;
	private Cover[][] coverArray;
	
	private boolean gameOver;
	private int mineCounter;
	private int totalNumMines;
	
	public Grid() {
		this(16, 31, 99);
		
	}
	
	public Grid(int row, int col, int mines) {
		this.numArray = new int[row][col];
		this.coverArray = new Cover[row][col];
		totalNumMines = mines;
		newGame();
	}
	
	public void newGame() {
		int row = getNumRows();
		int col = getNumCols();
		
		this.numArray = new int[row][col];
		this.coverArray = new Cover[row][col];
		mineCounter = totalNumMines;
		setGameOver(false);
		
		for (int i = 0; i < mineCounter; i++) {
			Location loc = getRandomEmptyLocation();
			set(loc, Grid.BOMB);
		}
		
		for (int r = 0; r < getNumRows(); r++) {
			for (int c = 0; c < getNumCols(); c++) {
				coverArray[r][c] = Cover.CLOSED;
				Location loc = new Location(r,c);
				if (get(loc) != Grid.BOMB) {
					set(loc, getNumNeighboringBombs(loc)); 
				}
			}
		}
	}
	
	public int getNumRows() { return numArray.length; }
	public int getNumCols() { return numArray[0].length; }
	public int getMineCounter() { return mineCounter; }
	public int getTotalNumMines() { return totalNumMines; }
	public boolean isGameOver() { return gameOver; }
	
	public boolean isValid(Location loc) {
		if (loc == null) throw new NullPointerException();
		return (0 <= loc.getRow()) && (loc.getRow() < getNumRows()) && (0 <= loc.getCol()) && (loc.getCol() < getNumCols());
	}
	
	public int get(Location loc) {
		if (!(isValid(loc))) throw new IllegalStateException();
		return numArray[loc.getRow()][loc.getCol()];
	}
	
	public Cover getCover(Location loc) {
		if (!(isValid(loc))) throw new IllegalStateException();
		return coverArray[loc.getRow()][loc.getCol()];
	}
	
	public void set(Location loc, int num) {
		if (!isValid(loc)) throw new IllegalStateException();
		numArray[loc.getRow()][loc.getCol()] = num;
	}
	
	private void set(Location loc, Cover cover) {
		if (!isValid(loc)) throw new IllegalStateException();
		coverArray[loc.getRow()][loc.getCol()] = cover;
	}
	
	private ArrayList<Location> getEmptyLocations() {
		ArrayList<Location> emptyLocs = new ArrayList();
		for (int r = 0; r < getNumRows(); r++) {
			for (int c = 0; c < getNumCols(); c++) {
				Location loc = new Location(r,c);
				if (get(loc) == 0) 
					emptyLocs.add(loc);
			}
		}
		return emptyLocs;
	}
	
	private ArrayList<Location> getNeighboringLocations(Location loc) {
		int d = 0;
		ArrayList<Location> neighbors = new ArrayList();
		for (int i = 0; i < 8; i++) {
			Location neighborLoc = loc.getAdjacentLocation(d);
			if (isValid(neighborLoc)) 
				neighbors.add(neighborLoc);
			d += 45;
		}
		return neighbors;
	}
	
	private Location getRandomEmptyLocation() {
		ArrayList<Location> emptyLocs = getEmptyLocations();
		int size = emptyLocs.size();
		int index = (int) ((Math.random() * 10 * size) % size);
		return emptyLocs.get(index);
	}
	
	private int getNumNeighboringBombs(Location loc) {
		int num = 0;
		for (Location neighbor: getNeighboringLocations(loc)) {
			if (get(neighbor) == -1) {
				num++;
			}
		}
		return num;
	}
	
	private int getNumNeighboringFlaggedCells(Location loc) {
		int num = 0;
		for (Location neighbor: getNeighboringLocations(loc)) {
			if (getCover(neighbor) == Cover.FLAGGED) {
				num++;
			}
		}
		return num;
	}
	
	private void open(Location loc) {	
		if (!isValid(loc)) throw new IllegalStateException();
		if (getCover(loc) != Cover.CLOSED) {
			return;
		}
		set(loc, Cover.OPEN);
		if (get(loc) == 0) {
			for (Location neighbor: this.getNeighboringLocations(loc)) {
				open(neighbor);
			}
		}
		else if (get(loc) == Grid.BOMB) {
			setGameOver(true);
		}	
	}
	
	public void click(Location loc) {
		if (!isValid(loc)) throw new IllegalStateException();
		if (getCover(loc) != Cover.CLOSED) {
			return;
		}
		open(loc);
	}
	
	public void rightClick(Location loc) {
		if (!isValid(loc)) throw new IllegalStateException();
		switch (getCover(loc)) {
		case OPEN:
			if (getNumNeighboringFlaggedCells(loc) == getNumNeighboringBombs(loc)) {
				for (Location neighbor: this.getNeighboringLocations(loc)) {
					open(neighbor);
				}
			}
			break;
		case CLOSED:
			set(loc, Cover.FLAGGED);
			mineCounter--;
			break;
		case FLAGGED: 
			set(loc, Cover.CLOSED);
			mineCounter++;
			break;
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int r = 0; r < getNumRows(); r++) {
			s += "[" ;
			for (int c = 0; c < getNumCols(); c++) {
				Location loc = new Location(r,c);
				switch(getCover(loc)) {
				case OPEN: 
					s += get(loc) + "\t";
					break;
				case CLOSED:
					s += "_\t";
					break;
				case FLAGGED: 
					s += "f\t";
					break;
				}
			}
			s += "]\n";	
		}
		return s;
	}

	public void setGameOver(boolean b) {
		gameOver = b;
		if (!gameOver) {
			return; 
		}
		for (int r = 0; r < getNumRows(); r++) {
			for (int c = 0; c < getNumCols(); c++) {
				Location loc = new Location(r,c);
				if (get(loc) == Grid.BOMB) {
					set(loc, Cover.OPEN);
				}
			}
		}	
	}
}
