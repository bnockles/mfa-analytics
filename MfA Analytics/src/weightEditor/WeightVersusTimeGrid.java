package weightEditor;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import dataStructures.AnalysisEquation;
import ui.VisibleComponent;

public class WeightVersusTimeGrid extends VisibleComponent implements MouseMotionListener, MouseListener{


	public static final int PIXEL_WIDTH = 500;
	public static final int PIXEL_HEIGHT = 400;
	private static final double X_MIN = -60;//60 minutes early
	private static final double X_MAX = 90;//90 minutes late
	private static final double Y_MIN = AnalysisEquation.MIN_COEF;
	private static final double Y_MAX = AnalysisEquation.MAX_COEF;


	private Node selectedNode;
	private ArrayList<Node> nodes;
	private List<Point> points;//the graph is stored as Points for each pixel
	private boolean mouseReleased;
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
		mouseReleased = true;
		smoothCurve = false;
		waviness = 10;
		update();
	}

	/**
	 * 
	 * @param time the number of minutes past 5:30 (or whatever is the set beginning of the event. the conversion is done elsewhere)
	 * @return the double (between Y_MIN and Y_MAX) most suitable for this input
	 */
	public double calculateWeight(int time){
		//linear approximation is made when time is out of bounds
		if(time <= nodes.get(0).getxCoordinate())return inferredWeightBeyondMinMax(time);
		else if(time >= nodes.get(nodes.size()-1).getxCoordinate())return inferredWeightBeyondMinMax(time);
		//based on settings, linear or non-linear approximation is made when time is within bounds
		else{
			if(!smoothCurve){
				return makeLinearApproximationWithinBounds(time);
			}else{
				try{
					Point closestPoint = points.get(0);
					double horizontalDistance = Math.abs(closestPoint.getxCoordinate()-time); 
					for(Point p : points){
						double horizontalDistance2 =  Math.abs(p.getxCoordinate()-time);
						if(horizontalDistance2 < horizontalDistance){
							horizontalDistance = horizontalDistance2;
							closestPoint = p;
						}
					}
					return closestPoint.getyCoordinate();
				}catch(Exception e){
					e.printStackTrace();
				}
				return makeLinearApproximationWithinBounds(time);

			}
		}
	}

	private double makeLinearApproximationWithinBounds(int time){
		int i = 0;
		while(!(time > nodes.get(i).getxCoordinate() && time < nodes.get(i+1).getxCoordinate()))i++;
		return inferredWeightBetweenNodes(nodes.get(i), nodes.get(i+1), time);
	}

	private double inferredWeightBetweenNodes(Node n1, Node n2, int time) {
		double slope = getSlope(n1,n2);
		return n1.getyCoordinate() + slope*(time-n1.getxCoordinate());
	}

	private double inferredWeightBeyondMinMax(int time) {	
		//when t beyond max
		Node n1= nodes.get(nodes.size()-2);
		Node n2= nodes.get(nodes.size()-1);
		Node start = n2;
		//when t below min
		if(time <= nodes.get(0).getxCoordinate()){
			n1= nodes.get(0);
			n2= nodes.get(1);
			start = n1;
		}
		double slope = getSlope(n1,n2);
		return start.getyCoordinate() + slope*(time-start.getxCoordinate());

	}

	@Override
	public void draw() {
		//		if(markedForUpdate()) {
		updateGraph();
		//		}else{
		redrawNodes();
		//		}
	}



	private void redrawNodes() {
		// TODO Auto-generated method stub
		//		boolean hoveredTextDrawn = false;
		g.setColor(Color.white);
		g.fillRect(100, 60, 120, 60);
		for(Node n : nodes){
			//			g.drawImage(n.getImage(), n.getX()-n.getDiameter()/2, n.getY()-n.getDiameter()/2, null);
			g.setColor(Color.black);
			g.drawString("Mouse on "+mx+", "+my, 100, 80);

			if(n.isHovered() ){
				g.drawString("Node:("+n.getxCoordinate()+","+n.getyCoordinate()+")", 100,100);
			}

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
		System.out.println("nodes.size is "+nodes.size()+" , smooth curve is "+smoothCurve+", and mouseReleased is "+mouseReleased);
		if(nodes.size()>2 && smoothCurve && mouseReleased){
			List<Point> start = new ArrayList<Point>();
			start.addAll(nodes);
			points = nodesTangentEndpointsAndMidpoints(start);
			points = smooth(points, 0);
			for(int i = 0; i < points.size()-1; i++){
				int d1 = points.get(i).getDiameter()/2;
				int d2 = points.get(i+1).getDiameter()/2;
				g.drawLine(points.get(i).getX()+d1, points.get(i).getY()+d1, points.get(i+1).getX()+d2, points.get(i+1).getY()+d2);


			}

		}else{
			points = new ArrayList<Point>();
			points.addAll(nodes);
			//draw lines connecting nodes
			g.setColor(Color.black);
			for(int i = 0; i < nodes.size()-1; i++){
				int d = nodes.get(i).getDiameter()/2;
				g.drawLine(nodes.get(i).getX()+d, nodes.get(i).getY()+d, nodes.get(i+1).getX()+d, nodes.get(i+1).getY()+d);


			}

		}
		//draw all points
		for(Point p: nodes){
			g.drawImage(p.getImage(), p.getX(), p.getY(), null);
		}
		mouseReleased=true;
	}

	private List<Point> smooth(List<Point> iter, int numberOfIterations) {
		System.out.println("smoothing "+iter.size()+" points");
		if(numberOfIterations > 3){
			return iter;
		}
		else{
			List<Point> fixed = new ArrayList<Point>();
			for(int i = 0; i < iter.size(); i = i + 2){
				fixed.add(iter.get(i));
			}
			
			return smooth(nodesTangentEndpointsAndMidpoints(fixed), numberOfIterations+1);
		}
	}

	/**
	 * 
	 * @return An ArrayList of Points consisting of the notes with a segment constructed 
	 * on each side approximating the tangent line and the midpoints between consecutive tangent lines
	 */
	private List<Point> nodesTangentEndpointsAndMidpoints(List<Point> nodes) {
//		if(points.get(0).getX()>=points.get(1).getX())return points;
//		else{
		List<Point> points = new ArrayList<Point>();
			for(int i = 0; i < nodes.size(); i++){

				Point n = nodes.get(i);
				if(i == 0){
					points.add(n);
					int out = getOut(n,nodes.get(i+1));
					points.add(new Point(n.getxCoordinate() + out, 
							n.getyCoordinate() + out * getSlope(n, nodes.get(i+1)), this));
					System.out.println("Slope between zero and one is "+getSlope(n, nodes.get(i+1)));
				}else if(i == nodes.size()-1){
					int out = getOut(nodes.get(i-1), n);
					Point p = (new Point(n.getxCoordinate() - out, 
							n.getyCoordinate() - out * getSlope(nodes.get(i-1),n), this));
					p.setColor(Color.red);

					Point m1 = midpoint(points.get(points.size()-1), p);
					m1.setColor(Color.green);
					p.update();
					m1.update();
					points.add(m1);
					points.add(p);
					points.add(n);
				}else{

					Point tangentSegmentPoint1;

					Point tangentSegmentPoint2;

					int out = getOut(nodes.get(i-1), n);
					if(isRelativeExtrema(nodes, i)){
						tangentSegmentPoint1 = new Point(n.getxCoordinate() - out, 
								n.getyCoordinate(), this);
						out = getOut(n,nodes.get(i+1));
						tangentSegmentPoint2 = new Point(n.getxCoordinate() + out, 
								n.getyCoordinate(), this);
					}else{
						tangentSegmentPoint1 = new Point(n.getxCoordinate() - out, 
								n.getyCoordinate() - out * getSlope(nodes.get(i-1), nodes.get(i+1)), this);
						out = getOut(n,nodes.get(i+1));
						tangentSegmentPoint2 = new Point(n.getxCoordinate() + out, 
								n.getyCoordinate() + out * getSlope(nodes.get(i-1), nodes.get(i+1)), this);
					}
					tangentSegmentPoint1.setColor(Color.red);
					tangentSegmentPoint2.setColor(Color.green);
					tangentSegmentPoint1.update();
					tangentSegmentPoint2.update();
					points.add(midpoint(points.get(points.size()-1), tangentSegmentPoint1));
					points.add(tangentSegmentPoint1);
					points.add(n);
					points.add(tangentSegmentPoint2);
				}

			}
			return points;
//		}
	}

	private int getOut(Point left, Point right){
		int out = waviness;
		while(left.getxCoordinate() + out >= right.getxCoordinate()-out){
			out--;
		}
		return out;
	}
	
	/**
	 * 
	 * @param node precondition 0 < node < node.size()-1
	 * @return
	 */
	private boolean isRelativeExtrema(List<Point> nodes, int node) {
		double p = nodes.get(node-1).getyCoordinate();
		double r = nodes.get(node+1).getyCoordinate();
		double q = nodes.get(node).getyCoordinate();
		if((p<q && r< q) || (p> q && r> q))return true;
		else return false;
	}

	public void addNode(){
		double greatestDistance = 0;
		int newIndex = 0;
		for(int i = 0; i < nodes.size()-1; i++){
			double distance = nodes.get(i+1).getxCoordinate() - nodes.get(i).getxCoordinate();
			if(distance > greatestDistance){
				greatestDistance = distance;
				newIndex = i+1;

			}
		}
		Point midpoint = midpoint(nodes.get(newIndex-1), nodes.get(newIndex));
		Node n =new Node(midpoint.getxCoordinate(), midpoint.getyCoordinate(), this);
		n.update();
		nodes.add(newIndex, n);
		//		nodes.add(newIndex, new Node(0, 0, this));
		setMarkedForUpdate(true);
	}

	public Point midpoint(Point p, Point q) {
		return new Point((p.getxCoordinate()+q.getxCoordinate())/2.0, (p.getyCoordinate()+q.getyCoordinate())/2.0, this); 
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
		mouseReleased = true;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(selectedNode != null){
			selectedNode.setXYCoordinates(locationToXCoordinate(e.getX()-getX()), locationToYCoordinate(e.getY()));
			setMarkedForUpdate(true);
			mouseReleased = false;
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

	public void setSmooth(boolean b) {
		smoothCurve = b;
		setMarkedForUpdate(true);

	}

}
