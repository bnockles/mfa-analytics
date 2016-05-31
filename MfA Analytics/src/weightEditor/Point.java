package weightEditor;

import java.awt.Color;

import ui.VisibleComponent;

public class Point extends VisibleComponent{



	/**
	 * unlike other visible components, point location is primarily based on a 
	 * traditional (x, y) coordinate. The (x,y) coordinate gets converted to screenCoordinates in the getX(), getY() method
	 */
	public static final int POINT_DIAMETER = 7;
	protected int diameter;
	private double xCoordinate;
	private double yCoordinate;
	private WeightVersusTimeGrid grid;
	private Color color;
	
	public Point(double x, double y, WeightVersusTimeGrid grid) {
		super(grid.getGridX(x, POINT_DIAMETER), grid.getGridY(y, POINT_DIAMETER), POINT_DIAMETER, POINT_DIAMETER);
		diameter = POINT_DIAMETER;
		xCoordinate = x;
		yCoordinate = y;
		this.grid = grid;
		color = Color.black;
		draw();
	}
	
	
	public Point(double x, double y, int nodeDiameter, WeightVersusTimeGrid grid) {
		super(grid.getGridX(x, nodeDiameter), grid.getGridY(y, nodeDiameter), nodeDiameter, nodeDiameter);
		this.diameter = nodeDiameter;
		xCoordinate = x;
		yCoordinate = y;
		this.grid = grid;
		color = Color.black;
		draw();
	}





	public double getxCoordinate() {
		return xCoordinate;
	}


	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
		setX(grid.getGridX(xCoordinate, diameter));
	}


	public double getyCoordinate() {
		return yCoordinate;
	}


	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
		setY(grid.getGridY(yCoordinate, diameter));
	}

	public int getDiameter() {
		return diameter;
	}

	public void setColor(Color c){
		color = c;
	}
	
/**
 * 
 * @param isHovered2 draws bright if hovered, dull otherwise
 */
	public void draw() {
		g.setColor(color);
		g.fillOval(0, 0, diameter, diameter);
	}

	
	
}
