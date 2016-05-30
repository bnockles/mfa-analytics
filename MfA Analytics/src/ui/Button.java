package ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
		Color background = (b)?new Color(200,200,200):Color.white;
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
	
		GuiUtilities.centerText(g, text, getWidth(), getHeight() - 5);
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
