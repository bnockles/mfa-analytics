package weightEditor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import dataStructures.AnalysisEquation;
import ui.VisibleComponent;

public class WeightVersusTimeGrid extends VisibleComponent implements MouseListener{

	
	private static final int PIXEL_WIDTH = 500;
	private static final int PIXEL_HEIGHT = 400;
	private static final double X_MIN = -60;//60 minutes early
	private static final double X_MAX = 90;//90 minutes late
	private static final double Y_MIN = AnalysisEquation.MIN_COEF;
	private static final double Y_MAX = AnalysisEquation.MAX_COEF;
	
	
	private ArrayList<Node> nodes;
	
	public WeightVersusTimeGrid(int x, int y, int width, int height) {
		super(0, 0, PIXEL_WIDTH, PIXEL_HEIGHT);
		nodes = new ArrayList<Node>();
		Node default1 = new Node(X_MIN, Y_MIN/2, this);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @return the X-coordinate of the y-axis within the FRAME
	 */
	public int getYAxis() {
		return (int) (getX()-X_MIN*getXPixelScale());
	}
	
	/**
	 * 
	 * @return the Y-coordinate of the x-axis within the FRAME
	 */
	public int getXAxis() {
		return (int) (getY()+Y_MAX*getYPixelScale());
	}
	
	/**
	 * 
	 * @return the number of pixels per x unit
	 */
	public double getXPixelScale(){
		return PIXEL_WIDTH/(X_MAX-X_MIN);
	}
	
	
	/**
	 * 
	 * @return the number of pixels per x unit
	 */
	public double getYPixelScale(){
		return PIXEL_HEIGHT/(Y_MAX-Y_MIN);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		for(Node n: nodes){
			if(e.getX()>n.getAbsoluteX() && e.getX() < n.getAbsoluteX()+Node.DIAMETER &&
					e.getY()>n.getAbsolutetY() && e.getY() < n.getAbsolutetY()+Node.DIAMETER){
				n.setHovered(true);
				n.update();
			}else{
				n.setHovered(false);
				n.update();
			}				
		}
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

}
