package weightEditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ui.GuiUtilities;
import ui.VisibleComponent;

public class AttendanceBar extends VisibleComponent {

	private double xMin;
	private double xMax;
	private int count;
	private int total;
	private WeightVersusTimeGrid grid;
	private int pixelWidth;
	private int pixelHeight;
	private int colorIndex;
	private boolean hovered;
	
	public static int colorIndexTracker = 0;
	public static final Color[] barColors = {new Color(51,51,255),         new Color(229,224,83), new Color(76,153,0),  new Color(80,30,153),    new Color(238,138,50), new Color(200,15,15)};//blue, yellow, green, purple
	public static final Color[] lightBarColors = {new Color(27,163,255),  new Color(255,255,80), new Color(128,255,0), new Color(102,102,255),  new Color(255,162,21),     new Color(255,15,15)};
	
	
	public AttendanceBar(double xMin, double xMax, int total, WeightVersusTimeGrid grid) {
		setX(grid.getGridX(xMin, 0));
		pixelWidth = (int) ((xMax-xMin)*grid.getXPixelScale());
		pixelHeight = 0;
		colorIndex =colorIndexTracker;
		colorIndexTracker++;
		hovered=false;
		this.total = total;
		this.xMin=xMin;
		this.xMax = xMax;
		this.count = 0;
		this.grid = grid;
	}

	@Override
	public void draw() {
		if(count >0){
			double value = (double)count/total;
			pixelHeight = (int) (value*grid.getYAxis());
			setY(grid.getXAxis()-pixelHeight);
			if(pixelWidth>0 && pixelHeight > 0){
				image = new BufferedImage(pixelWidth, pixelHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = image.createGraphics();
				Color fill =(hovered)?lightBarColors[colorIndex%barColors.length]:barColors[colorIndex%barColors.length];
				g.setColor(fill);
				g.fillRect(3, 0, pixelWidth-6, pixelHeight);
				BasicStroke s = new BasicStroke(3);
				g.setStroke(s);
				g.setColor(Color.black);
				g.drawRect(3, 0, pixelWidth-6, pixelHeight);
			}else{
				image = null;
			}
			
		}else{
			
			image = null;
		}
	}

	public int getX2(){
		return getX()+pixelWidth;
	}
	
	public int getY2(){
		return getY()+pixelHeight;
	}
	
	public boolean includes(double x) {
		return x>=xMin && x<=xMax;
	}

	public void increase() {
		count++;
	}

	public void setHovered(boolean b) {
		hovered=b;
		update();
	}

	public int getMin() {
		return (int)xMin;
	}
	
	public int getMax() {
		return (int)xMax;
	}
	
	public int getCount() {
		return count;
	}
	
	public String getPercentage() {
		return GuiUtilities.formatTenths((double)count/total*100)+"%";
	}

}
