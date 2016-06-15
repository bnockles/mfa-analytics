package search;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dataStructures.PD;
import dataStructures.Teacher;
import ui.RecordViewer;
import ui.UI;

public class SearchWindow extends JFrame implements MouseListener, MouseMotionListener {

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
	public static final int QUERY_HEIGHT = 25;
	public static final int SEARCH_HEIGHT = 100;
	
	public SearchWindow(UI ui, int x, int y){
		this.ui = ui;
		this.x = x;
		this.y = y;
		setVisible(false);
		query = new JTextField();
		 query.setPreferredSize(new Dimension(150,QUERY_HEIGHT));
		query.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				results.clear();
				search(query.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				results.clear();
				search(query.getText());
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				
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
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	
	
	protected void search(String text) {
		if(text.length() > 2){
			for(int i = 0 ; i < ui.getCsv().getTeachers().size(); i++){
				Teacher t = ui.getCsv().getTeachers().get(i);
				if(t.getName().contains(text))results.addResult(t, i, RecordViewer.TEACHERS_VIEW);
			}
			for(int j = 0 ; j < ui.getCsv().getPDs().size(); j++){
				PD pd = ui.getCsv().getPDs().get(j);
				if(pd.getTitle().contains(text))results.addResult(pd, j, RecordViewer.PDS_VIEW);
			}
		}
		repaint();
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(0<mx && mx < getWidth() && my > QUERY_HEIGHT && my < getHeight()){
			my = e.getY()-QUERY_HEIGHT;
			int resultMode = results.getResultMode(my);
			int resultIndex = results.getResultIndex(my);
			if(resultMode != -1) ui.setViewerMode(resultMode);
			if(resultIndex != -1){
				ui.setViewerStartIndex(resultIndex);
				setVisible(false);
			}
			
		}
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



	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(0<mx && mx < getWidth() && my > QUERY_HEIGHT && my < getHeight()){
			my = e.getY()-QUERY_HEIGHT;
			results.hoverOver(my);
			repaint();
		}
	}

	
	

}
