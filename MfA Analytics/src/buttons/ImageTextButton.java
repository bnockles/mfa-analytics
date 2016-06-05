package buttons;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import ui.UI;

public class ImageTextButton extends Button {

	Image icon;
	
	public ImageTextButton(String text, Image icon, int x, int y, int width, int height, Action action) {
		super("    "+text, x, y, width, height, action);
		text = "    "+text;
		this.icon = icon;
		draw(normal, foreGroundColor);
		draw(clicked, foreGroundColor);
		draw(disabled, UI.DISABLED_COLOR);
		draw(hoveredImage, UI.ACCENT_COLOR);
		this.action = action;
	}
	
	protected void draw(BufferedImage img, Color frontColor) {
		super.draw(img, backGroundColor, frontColor);
		Graphics2D g2 = img.createGraphics();
		FontMetrics fm =g2.getFontMetrics();
		
		if(icon != null) g2.drawImage(icon, 2, 0, null);
//		GuiUtilities.centerIcon(g2, icon, getWidth()-fm.stringWidth(text), getHeight());
	}
	
	
}
