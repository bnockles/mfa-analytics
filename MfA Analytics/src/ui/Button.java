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
	protected BufferedImage normal;
	protected BufferedImage clicked;
	protected Action action;
	protected boolean enabled;
	
	public Button(String text, int x, int y, int width, int height, Action action) {
		super(x, y, width, height);
		this.text = text;
		normal = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		clicked = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		draw(normal, false);
		draw(clicked, true);
		this.action = action;
	}
	
	public Button(int x, int y, int width, int height, Action action) {
		super(x, y, width, height);
		this.text = "";
		normal = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		clicked = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.action =action;
	}

	protected void draw(BufferedImage img, boolean b) {
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color background = (b)?new Color(200,200,200):Color.white;
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		drawBorder(img, g);
		g.setFont(baseFont);
		enabled=true;
		FontMetrics fm = g.getFontMetrics();
		int fheight = fm.getHeight();
		GuiUtilities.centerText(g, text, getWidth(), (getHeight()+fheight)/2-fm.getDescent());
	}

	protected void drawBorder(BufferedImage img, Graphics2D g){
		g.setColor(Color.black);
		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 9, 9);
	}
	
	@Override
	public void draw() {
		if(isClicked || !enabled)image = clicked;
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
		if(enabled)action.act();
	}

	public void setText(String string) {
		this.text = string;
		draw(normal, false);
		draw(clicked, true);
		setMarkedForUpdate(true);
	}
	
	public void setEnabled(boolean b){
		enabled = b;
		setMarkedForUpdate(true);
	}

}
