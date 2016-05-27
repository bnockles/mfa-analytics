package weightEditor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import dataStructures.AnalysisEquation;
import ui.VisibleComponent;

public class WeightVersusTimeGrid extends VisibleComponent implements MouseMotionListener, MouseListener{


	private static final int PIXEL_WIDTH = 500;
	private static final int PIXEL_HEIGHT = 400;
	private static final double X_MIN = -60;//60 minutes early
	private static final double X_MAX = 90;//90 minutes late
	private static final double Y_MIN = AnalysisEquation.MIN_COEF;
	private static final double Y_MAX = AnalysisEquation.MAX_COEF;


	private ArrayList<Node> nodes;
	private ArrayList<Point> points;//the graph is stored as Points for each pixel
	private boolean changeMade;
	private boolean smoothCurve;
	private int waviness;//the length of the constructed "tangent" segment

	public WeightVersusTimeGrid(int x, int y, int width, int height) {
		super(0, 0, PIXEL_WIDTH, PIXEL_HEIGHT);
		nodes = new ArrayList<Node>();
		points = new ArrayList<Point>();
		Node default1 = new Node(X_MIN, Y_MIN/2, this);
		changeMade = true;
		smoothCurve = true;
		update();
	}

	@Override
	public void update() {
		if(changeMade) updateGraph();
		changeMade = false;
	}



	private void updateGraph(){
		
		if(nodes.size()>2 && smoothCurve){
			points = nodesTangentEndpointsAndMidpoints();
			
			for(int i = 0; i <  
		}else{

		}

	}

/**
 * 
 * @return An ArrayList of Points consisting of the notes with a segment constructed 
 * on each side approximating the tangent line and the midpoints between consecutive tangent lines
 */
	private ArrayList<Point> nodesTangentEndpointsAndMidpoints() {
		points = new ArrayList<Point>();
		for(int i = 0; i < nodes.size(); i++){
			Node n = nodes.get(i);
			if(i == 0){
				points.add(n);
				points.add(new Point(n.getxCoordinate() + waviness, 
						n.getyCoordinate() + waviness * getSlope(n, nodes.get(i+1)), this));
			}else if(i == nodes.size()-1){
				points.add(new Point(n.getxCoordinate() - waviness, 
						n.getyCoordinate() - waviness * getSlope(n, nodes.get(i-1)), this));
				points.add(n);
			}
			
		}
		return null;
	}

	public static double getSlope(Point x1, Point x2){
		return (x2.getyCoordinate()-x1.getyCoordinate())/(x2.getxCoordinate() - x1.getxCoordinate());
	}
	
	//returns absolute X coordinate of Node relative to Frame
	public int getAbsoluteX(int xCoordinate, int diameter){
		return (int) (getX() + getGridX(xCoordinate, diameter));
	}

	//returns absolute  Y coordinate of Node relative to Frame
	public int getAbsolutetY(int yCoordinate, int diameter){
		return (int) (getY()+getGridY(yCoordinate, diameter));
	}

	//returns X coordinate of Point relative to this
	public int getGridX(int xCoordinate, int diameter){
		return (int) (getYAxis()+xCoordinate*getXPixelScale())-diameter/2;
	}

	//returns  Y coordinate of Point relative to this
	public int getGridY(int yCoordinate, int diameter){
		return (int) (getXAxis()-yCoordinate*getYPixelScale())-diameter/2;
	}

	/**
	 * 
	 * @return the X-coordinate of the y-axis within this
	 */
	public int getYAxis() {
		return (int) (X_MIN*getXPixelScale());
	}

	/**
	 * 
	 * @return the Y-coordinate of the x-axis within this
	 */
	public int getXAxis() {
		return (int) (Y_MAX*getYPixelScale());
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

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
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

}
