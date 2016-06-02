package search;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import ui.UI;

public class SearchWindow extends JFrame implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1108979451048476560L;
	private UI ui;
	private int x;
	private int y;
	private JTextField query;
	
	public static final int SEARCH_WIDTH = 300;
	public static final int SEARCH_HEIGHT = 300;
	
	public SearchWindow(UI ui, int x, int y){
		this.ui = ui;
		this.x = x;
		this.y = y;
		setVisible(false);
		query = new JTextField();

		setSize(SEARCH_WIDTH,SEARCH_HEIGHT);
		setLocation(x,y);
		setUndecorated(true);
		
		add(query);
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void setX(int newX) {
		this.x = newX;
		setLocation(x, y);
	}

	public void getY(int newY) {
		this.y = newY;
		setLocation(x,y);
	}
	
	

}
