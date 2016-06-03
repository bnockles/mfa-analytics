package search;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import dataStructures.PD;
import dataStructures.Teacher;
import ui.ViewerLabel;

public class SearchResults extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3095363341876772231L;

	private ArrayList<String> results; 
	
	public SearchResults(){
		results = new ArrayList<String>();
	}
	
	
	public void paint(Graphics g){
		 super.paintComponents(g);
		  Graphics2D g2 = (Graphics2D) g;
		  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		  g2.setColor(Color.white);
		  g2.fillRect(0, 0, getWidth(), getHeight());
		  g2.setColor(Color.black);
		  int y = 25;
		  for(String s: results){
			  g2.drawString(s, 2, 25);
		  }
	}


	public void addResult(Teacher t) {
		System.out.println("Match found" + t.getFirstName());
		results.add(t.getName());
		repaint();
	}
	
	public void addResult(PD t) {
		results.add(t.getTitle());
	}
}
