package ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class VisibleComponent implements Visible {

	Graphics2D g;
	private BufferedImage image;
	private int x;
	private int y;
	
	public VisibleComponent(int x, int y, int width, int height){
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D)image.createGraphics();
		this.x = x;
		this.y = y;
	}
	
	
	@Override
	public BufferedImage getImage() {
		return image;
	}
	@Override
	public abstract void update();
	
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	
	public int getWidth(){
		return image.getWidth();
	}
	
	public int getHeight(){
		return image.getHeight();
	}
}
