package weightEditor;

import java.awt.Color;

import ui.VisibleComponent;

public class Point extends VisibleComponent{



	/**
	 * unlike other visible components, point location is primarily based on a 
	 * traditional (x, y) coordinate. The (x,y) coordinate gets converted to screenCoordinates in the getX(), getY() method
	 */
	public static final int POINT_DIAMETER = 1;
	protected int diameter;
	private double xCoordinate;
	private double yCoordinate;
	private WeightVersusTimeGrid grid;
	
	public Point(double x, double y, WeightVersusTimeGrid grid) {
		super((int)(x*grid.getXPixelScale()), (int)(y*grid.getYPixelScale()), POINT_DIAMETER, POINT_DIAMETER);
		diameter = POINT_DIAMETER;
		xCoordinate = x;
		yCoordinate = y;
		this.grid = grid;
	}
	
	
	public Point(int x, int y, int nodeDiameter, int diameter, WeightVersusTimeGrid grid) {
		super((int)(x*grid.getXPixelScale()), (int)(y*grid.getYPixelScale()), diameter, diameter);
		this.diameter = diameter;
		xCoordinate = x;
		yCoordinate = y;
		this.grid = grid;
	}





	public double getxCoordinate() {
		return xCoordinate;
	}


	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}


	public double getyCoordinate() {
		return yCoordinate;
	}


	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}


	@Override
	public void update() {
		//points do not ever change appearance
	}

/**
 * 
 * @param isHovered2 draws bright if hovered, dull otherwise
 */
	private void draw() {
		g.fillOval(0, 0, POINT_DIAMETER, POINT_DIAMETER);
	}

	
	
}
