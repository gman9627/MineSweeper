package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import config.Configuration;

public class Main{

	private GamePanel panel;
	private JFrame window;
	
	public static void main(String args[]) {
		new Main();
	}
	
	public  Main() {
		window = new JFrame("MineSweeper");
		panel = new GamePanel();
		window.setContentPane(getGridPanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
	}
	
	public JPanel getGridPanel() {
		JPanel content = new JPanel();
	    content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    content.setLayout(new BorderLayout());
	    content.add(makeMenuBar(), BorderLayout.NORTH);
	    content.add(panel, BorderLayout.CENTER);  
	    return content;	
	}
	
	public JPanel makeMenuBar() {
		JPanel content = new JPanel();
	    content.setLayout(new BorderLayout());
		
		JMenuBar mB= new JMenuBar();
		JMenu game = new JMenu("Game");
		mB.add(game);
		
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.getGrid().newGame();
				window.repaint();
		}});
		
		JMenuItem beginner = new JMenuItem("Beginner");
		beginner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setGrid(Configuration.getBeginner());
				window.repaint();
				window.pack();
		}});
		
		JMenuItem intermediate = new JMenuItem("Intermediate");
		intermediate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setGrid(Configuration.getIntermediate());
				window.repaint();
				window.pack();
		}});
		
		JMenuItem expert = new JMenuItem("Expert");
		expert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setGrid(Configuration.getExpert());
				window.repaint();
				window.pack();
		}});
		

		
		game.add(newGame);
		game.addSeparator();
		game.add(beginner);
		game.add(intermediate);
		game.add(expert);
		
		content.add(mB, BorderLayout.WEST);
	    content.setOpaque(true);
	    return content;
	}
	
	
}
