package search;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dataStructures.PD;
import dataStructures.Teacher;
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
	private SearchResults results;
	
	public static final int SEARCH_WIDTH = 300;
	public static final int SEARCH_HEIGHT = 100;
	
	public SearchWindow(UI ui, int x, int y){
		this.ui = ui;
		this.x = x;
		this.y = y;
		setVisible(false);
		query = new JTextField();
		 query.setPreferredSize(new Dimension(150,20));
		query.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(query.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setSize(SEARCH_WIDTH,SEARCH_HEIGHT);
		setLocation(x,y);
		setUndecorated(true);
		
		Container contentPane = getContentPane();
		BoxLayout layout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
		contentPane.setLayout(layout);
		contentPane.add(query);
		results = new SearchResults();
		results.setPreferredSize(new Dimension(150,100));
		contentPane.add(results);
	}
	
	
	
	protected void search(String text) {
		System.out.println("Searching: "+text);
		if(text.length() > 2){
			for(Teacher t : ui.getCsv().getTeachers()){
				if(t.getName().contains(text))results.addResult(t);
			}
			for(PD pd : ui.getCsv().getPDs()){
				if(pd.getTitle().contains(text))results.addResult(pd);
			}
		}
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
