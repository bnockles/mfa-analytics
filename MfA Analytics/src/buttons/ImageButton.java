package buttons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import ui.GuiUtilities;
import ui.UI;

public class ImageButton extends Button{

	Image icon;
	
	public ImageButton(Image icon, int x, int y, int width, int height, Action action) {
		super(x, y, width, height, action);
		this.icon = icon;
		draw(normal, foreGroundColor);
		clicked = normal;
		draw(disabled, UI.DISABLED_COLOR);
		draw(hoveredImage, UI.ACCENT_COLOR);
		this.action = action;
	}
	
	protected void draw(BufferedImage img, Color front) {
		super.draw(img, backGroundColor, front);
		Graphics2D g2 = img.createGraphics();
//		g2.drawImage(icon, 0, 0, null);
		GuiUtilities.centerIcon(g2, icon, getWidth(), getHeight());
	}
	
	protected void drawBorder(BufferedImage img, Graphics2D g){
		//Override does nothing, so no border appears
	}

}
