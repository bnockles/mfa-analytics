package weightEditor;

import java.awt.Color;

import ui.VisibleComponent;

public class Node extends Point{

	/**
	 * unlike other visible components, nodes location is primarily based on a 
	 * traditional (x, y) coordinate. The (x,y) coordinate gets converted to screenCoordinates in the getX(), getY() method
	 */
	public static final int NODE_DIAMETER = 20;
	public static final Color HOVER_COLOR = new Color(150, 255,150);
	public static final Color NON_HOVER_COLOR = new Color(50, 205,50);
	private boolean isHovered;
	
	public Node(double x, double y, WeightVersusTimeGrid grid) {
		super((int)(x*grid.getXPixelScale()), (int)(y*grid.getYPixelScale()), NODE_DIAMETER, NODE_DIAMETER, grid);
		isHovered = false;
	}
	


	public boolean isHovered() {
		return isHovered;
	}


	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}


	@Override
	public void update() {
		draw(isHovered);
	}

/**
 * 
 * @param isHovered2 draws bright if hovered, dull otherwise
 */
	private void draw(boolean hovered) {
		if(hovered){
			g.setColor(HOVER_COLOR);
		}else g.setColor(NON_HOVER_COLOR);
		g.fillOval(0, 0, diameter, diameter);
	}

	
	
}
