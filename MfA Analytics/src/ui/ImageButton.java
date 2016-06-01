package ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageButton extends Button{

	Image icon;
	
	public ImageButton(Image icon, int x, int y, int width, int height, Action action) {
		super(x, y, width, height, action);
		this.icon = icon;
		draw(normal, false);
		draw(clicked, true);
		this.action = action;
	}
	
	protected void draw(BufferedImage img, boolean b) {
		super.draw(img, b);
		Graphics2D g2 = img.createGraphics();
//		g2.drawImage(icon, 0, 0, null);
		GuiUtilities.centerIcon(g2, icon, getWidth(), getHeight());
	}

}
