package ui;

import java.awt.image.BufferedImage;

public interface Visible {
	public BufferedImage getImage();
	public void update();
	public int getX();
	public int getY();
	public void setXY(int x, int y);
	public int getWidth();
	public int getHeight();
}
