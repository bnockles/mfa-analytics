package weightEditor;

import java.awt.Color;
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


	private Node selectedNode;
	private ArrayList<Node> nodes;
	private ArrayList<Point> points;//the graph is stored as Points for each pixel
	private boolean changeMade;
	private boolean smoothCurve;
	private int waviness;//the length of the constructed "tangent" segment

	//mouse coordinates to display when debugging. Delete later
	int mx=0;
	int my = 0;
	
	public WeightVersusTimeGrid(int x, int y) {
		super(x, y, PIXEL_WIDTH, PIXEL_HEIGHT);
		nodes = new ArrayList<Node>();
		
		Node default1 = new Node(X_MIN, Y_MIN/2, this);
		Node default2 = new Node(X_MAX, Y_MAX/2, this);
		default1.freezeX(true);
		default2.freezeX(true);
		
		nodes.add(default1);
		nodes.add(default2);
		changeMade = true;
		smoothCurve = true;
		update();
	}

	@Override
	public void update() {
		if(changeMade) {
			updateGraph();
			changeMade = false;
		}else{
			redrawNodes();
		}
	}



	private void redrawNodes() {
		// TODO Auto-generated method stub
		for(Node n : nodes){
			g.drawImage(n.getImage(), n.getX()-n.getDiameter()/2, n.getY()-n.getDiameter()/2, null);
			g.setColor(Color.white);
			g.fillRect(100, 60, 120, 60);
			g.setColor(Color.black);
			g.drawString("Mouse on "+mx+", "+my, 100, 80);
			g.drawString("Node:("+getAbsoluteX(nodes.get(0))+","+getAbsoluteY(nodes.get(0))+")"+nodes.get(0).getyCoordinate(), 100,100);
		}
	}

	private void updateGraph(){

		//draw axes
		g.setColor(Color.white);
		g.fillRect(0, 0, PIXEL_WIDTH, PIXEL_HEIGHT);
		g.setColor(Color.blue);
		g.drawLine(getYAxis(), 0, getYAxis(), PIXEL_HEIGHT);
		g.drawLine(0, getXAxis(),PIXEL_WIDTH,getXAxis());
		g.setColor(Color.black);
		if(nodes.size()>2 && smoothCurve){
			points = nodesTangentEndpointsAndMidpoints();

			
		}else{
			points = new ArrayList<Point>();
			points.addAll(nodes);
			g.drawLine(nodes.get(0).getX(), nodes.get(0).getY(), nodes.get(1).getX(), nodes.get(1).getY());
			
		}
		//draw all points
		for(Point p: points){
			g.drawImage(p.getImage(), p.getX()-p.getDiameter()/2, p.getY()-p.getDiameter()/2, null);
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
				Point p = (new Point(n.getxCoordinate() - waviness, 
						n.getyCoordinate() - waviness * getSlope(n, nodes.get(i-1)), this));
				points.add(midpoint(points.get(i-1), p));
				points.add(p);
				points.add(n);
			}else{
				
				Point tangentSegmentPoint1;
				
				Point tangentSegmentPoint2;

				if(isRelativeExtrema(i)){
					tangentSegmentPoint1 = new Point(n.getxCoordinate() - waviness, 
							n.getyCoordinate(), this);
					tangentSegmentPoint2 = new Point(n.getxCoordinate() + waviness, 
							n.getyCoordinate(), this);
				}else{
					tangentSegmentPoint1 = new Point(n.getxCoordinate() - waviness, 
							n.getyCoordinate() - waviness * getSlope(nodes.get(i-1), nodes.get(i+1)), this);
					tangentSegmentPoint2 = new Point(n.getxCoordinate() + waviness, 
							n.getyCoordinate() + waviness * getSlope(nodes.get(i-1), nodes.get(i+1)), this);
				}
				points.add(midpoint(points.get(i-1), tangentSegmentPoint1));
				points.add(tangentSegmentPoint1);
				points.add(n);
				points.add(tangentSegmentPoint2);
			}

		}
		return points;
	}

	/**
	 * 
	 * @param node precondition 0 < node < node.size()-1
	 * @return
	 */
	private boolean isRelativeExtrema(int node) {
		double p = nodes.get(node-1).getyCoordinate();
		double r = nodes.get(node+1).getyCoordinate();
		double q = nodes.get(node).getyCoordinate();
		if((p<q && r< q) || (p> q && r> q))return true;
		else return false;
	}

	public Point midpoint(Point p, Point q) {
		return new Point((p.getxCoordinate()+q.getxCoordinate())/2, (p.getxCoordinate()+q.getxCoordinate())/2, this); 
	}

	public static double getSlope(Point x1, Point x2){
		return (x2.getyCoordinate()-x1.getyCoordinate())/(x2.getxCoordinate() - x1.getxCoordinate());
	}


	
	//returns absolute X coordinate of Node relative to Frame
	public int getAbsoluteX(Point p){
		return getAbsoluteX(p.getxCoordinate(), p.getDiameter());
	}
	
	//returns absolute X coordinate of Node relative to Frame
	public int getAbsoluteY(Point p){
		return getAbsoluteY(p.getyCoordinate(), p.getDiameter());
	}
	
	//returns absolute X coordinate of Node relative to Frame
	public int getAbsoluteX(double xCoordinate, int diameter){
		return (int) (getX() + getGridX(xCoordinate, diameter));
	}

	//returns absolute  Y coordinate of Node relative to Frame
	public int getAbsoluteY(double yCoordinate, int diameter){
		return (int) (getY()+getGridY(yCoordinate, diameter));
	}

	private double locationToXCoordinate(int x) {
		return (double)(x+Node.NODE_DIAMETER/2-getYAxis())/getXPixelScale();
	}
	
	private double locationToYCoordinate(int y) {
		return -(double)(y-getY()+Node.NODE_DIAMETER/2-getXAxis())/getYPixelScale();
	}
	
	//returns X coordinate of Point relative to this
	public int getGridX(double xCoordinate, int diameter){
		return (int) (getYAxis()+xCoordinate*getXPixelScale())-diameter/2;
	}

	//returns  Y coordinate of Point relative to this
	public int getGridY(double yCoordinate, int diameter){
		return (int) (getXAxis()-yCoordinate*getYPixelScale())-diameter/2;
	}

	/**
	 * 
	 * @return the X-coordinate of the y-axis within this
	 */
	public int getYAxis() {
		return (int) (-X_MIN*getXPixelScale());
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
	public void mouseDragged(MouseEvent e) {
		if(selectedNode != null){
			selectedNode.setXYCoordinates(locationToXCoordinate(e.getX()), locationToYCoordinate(e.getY()));
			changeMade = true;
		}
	}



	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		update();
		for(Node n: nodes){
			int d = n.getDiameter()/2;
			if(e.getX()>getAbsoluteX(n)-d && e.getX() < getAbsoluteX(n) + 2*d &&
					e.getY()>getAbsoluteY(n)-d && e.getY() < getAbsoluteY(n)+2*d){
				n.setHovered(true);
				selectedNode=n;
				n.update();

				setMarkedForUpdate(true);
			}else{
				if(n.isHovered()){
					n.setHovered(false);
					selectedNode = null;
					n.update();
					setMarkedForUpdate(true);
				}

			}				
		}
	}

}
