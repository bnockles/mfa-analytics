package ui;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageTextButton extends Button {

	Image icon;
	
	public ImageTextButton(String text, Image icon, int x, int y, int width, int height, Action action) {
		super("    "+text, x, y, width, height, action);
		text = "    "+text;
		this.icon = icon;
		draw(normal, false);
		draw(clicked, true);
		this.action = action;
	}
	
	protected void draw(BufferedImage img, boolean b) {
		super.draw(img, b);
		Graphics2D g2 = img.createGraphics();
		FontMetrics fm =g2.getFontMetrics();
		
		if(icon != null) g2.drawImage(icon, 2, 0, null);
//		GuiUtilities.centerIcon(g2, icon, getWidth()-fm.stringWidth(text), getHeight());
	}
	
	
}
