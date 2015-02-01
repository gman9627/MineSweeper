package main;

import grid.Grid;
import grid.Grid.Cover;
import grid.Location;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import config.Configuration;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements MouseListener {

	private Grid grid;
	private int numRows, numCols;
	private int cellSize = 16;
	private Dimension dim;
	private final String path = "/minesweeper_tiles.gif";
	private final Map<Integer, Image> imageMap = new HashMap();
	
	public GamePanel() {
		super();
		fillMap();
		setGrid(Configuration.getExpert());
		addMouseListener(this);
		setFocusable(true);
		requestFocus();
		repaint();
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
		numRows = grid.getNumRows();
		numCols = grid.getNumCols();
		dim = new Dimension((cellSize) * numCols, (cellSize) * numRows);
		setSize(dim);
		setPreferredSize(dim);
		repaint();
	}
	
	public Grid getGrid() { return grid; }
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		
		fillGrid(g2);
	}
	
	private void fillMap() {
		for (int x = 0; x < 12; x++) {
			try {
				imageMap.put(x, loadSubImage(x));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void fillGrid(Graphics2D g2) {
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				Location loc = new Location(r, c);
				int xleft = colToXCoord(c);
				int ytop = rowToYCoord(r);
				int tile = this.getTileNum(loc);
				//System.out.println("test");
				g2.drawImage(imageMap.get(tile), xleft, ytop, null);
			}
		}
	}
	
	private int getTileNum(Location loc) {
		if (grid.getCover(loc) == Cover.CLOSED) {
			return 0;
		}
		if (grid.getCover(loc) == Cover.FLAGGED) {
			return 1;
		}
		return grid.get(loc) + 3;
	}
		
	private BufferedImage loadSubImage(int numTile) throws IOException {
		BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
		int numTilesAcross = image.getWidth() / cellSize;
		int x, y = x = 0;
		for (int q = 1; q < numTile + 1; q++) {
			x ++;
			if (q % numTilesAcross == 0) {
				x = 0;
				y ++;
			}
		}	
		
		BufferedImage subImage = image.getSubimage(x * cellSize, y * cellSize, cellSize, cellSize);
		return subImage;
	}
	
	public Location locationForPoint(Point p) {
		return new Location(yCoordToRow(p.y), xCoordToCol(p.x));
	}

	private int xCoordToCol(int xCoord) {
		return (xCoord - getInsets().left) / (this.cellSize);
	}

	private int yCoordToRow(int yCoord) {
		return (yCoord - getInsets().top) / (this.cellSize);
	}

	private int colToXCoord(int col) {
		return (col) * (cellSize) + getInsets().left;
	}

	private int rowToYCoord(int row) {
		return (row) * (cellSize) + getInsets().top;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (grid.isGameOver()) {
			return;
		}
		Location loc = this.locationForPoint(e.getPoint());
		if (e.getButton() == MouseEvent.BUTTON1) {
			grid.click(loc);
		} 
		else if (e.getButton() == MouseEvent.BUTTON3) {
			grid.rightClick(loc);
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	

}
