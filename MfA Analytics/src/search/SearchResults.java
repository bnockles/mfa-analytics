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

	private ArrayList<Result> results; 
	private int resultNumber;

	public SearchResults(){
		results = new ArrayList<Result>();
		resultNumber = 0;
	}


	public void paint(Graphics g){
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.black);
		int y = 25;
		for(Result r: results){
			g2.drawImage(r.getImage(), r.getX(), r.getY(), null);
		}
	}


	public void clear(){
		results = new ArrayList<Result>();
		resultNumber = 0;
	}

	public void addResult(Teacher t, int index, int viewMode) {
		results.add(new Result(t.getName(), resultNumber++, index, viewMode));
		repaint();
	}

	public void addResult(PD pd, int index, int viewMode) {
		results.add(new Result(pd.getTitle(), resultNumber++, index, viewMode));
		repaint();
	}


	/**
	 * 
	 * @param my the number of pixels, vertically from the top of the results
	 * @return the index (in the Record Viewer) of the result under the mouse
	 */
	public int getResultIndex(int my) {
		System.out.println("my is "+my + " and my/RESULT_HEIGHT = " + my/Result.RESULT_HEIGHT);
		for(Result r : results){
			if(r.getResultIndex() == my/Result.RESULT_HEIGHT)return r.getViewerIndex();
		}
		return -1;
	}

	/**
	 * 
	 * @param my my the number of pixels, vertically from the top of the results
	 * @return the type
	 */
	public int getResultMode(int my) {

		for(Result r : results){
			if(r.getResultIndex() == my/Result.RESULT_HEIGHT)return r.getRequiredMode();
		}
		return -1;
	}


	public void hoverOver(int my) {
		for(Result r : results){
			if(r.getResultIndex() == my/Result.RESULT_HEIGHT) r.setHover(true);
			else r.setHover(false);
		}
	}
}
