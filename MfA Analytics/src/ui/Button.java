package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Button extends VisibleComponent {

	private String text;
	private boolean isClicked;
	private BufferedImage normal;
	private BufferedImage clicked;
	private Action action;
	
	public Button(String text, int x, int y, int width, int height, Action action) {
		super(x, y, width, height);
		this.text = text;
		normal = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		clicked = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		draw(normal, false);
		draw(clicked, true);
		this.action = action;
	}

	private void draw(BufferedImage img, boolean b) {
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color background = (b)?new Color(200,200,200):Color.white;
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 5, 5);
		g.setFont(baseFont);
		FontMetrics fm = g.getFontMetrics();
		int fheight = fm.getHeight();
		GuiUtilities.centerText(g, text, getWidth(), (getHeight()+fheight)/2-fm.getDescent());
	}

	@Override
	public void draw() {
		if(isClicked)image = clicked;
		else image = normal;	
	}
	
	public void setClicked(boolean b){
		isClicked = b;
		setMarkedForUpdate(true);
	}
	
	public boolean isClicked(){
		return isClicked;
	}

	public void act() {
		action.act();
	}

}
