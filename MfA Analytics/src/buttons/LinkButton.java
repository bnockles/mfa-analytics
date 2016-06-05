package buttons;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ui.GuiUtilities;
import ui.UI;

public class LinkButton extends Button{

	public LinkButton(String text, int x, int y, int width, int height, Action action) {
		super(text, x, y, width, height, Color.white, Color.white, Color.white, UI.ACCENT_COLOR, action);
	}

	@Override
	protected void drawBorder(BufferedImage img, Graphics2D g){
	}
	
	
}
