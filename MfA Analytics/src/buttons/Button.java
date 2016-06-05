package buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import ui.GuiUtilities;
import ui.UI;
import ui.VisibleComponent;

public class Button extends HoverComponent implements ActOnClick{

	protected String text;
	private boolean isClicked;
	protected BufferedImage normal;
	protected BufferedImage clicked;
	protected BufferedImage hoveredImage;
	protected BufferedImage disabled;
	protected Action action;
	protected boolean enabled;
	protected Color normalBackground;
	protected Color clickedBackground;
	
	public Button(String text, int x, int y, int width, int height, Action action) {
		super(x, y, width, height);
		this.text = text;
		normalBackground = Color.white;
		clickedBackground = new Color(200,200,200);
		backHoverColor = clickedBackground;
		frontHoverColor = normalBackground;
		normal = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		clicked = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		hoveredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		disabled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		draw(normal, normalBackground, foreGroundColor);
		draw(clicked, clickedBackground, foreGroundColor);
		draw(hoveredImage, normalBackground, foreGroundColor);
		draw(disabled, normalBackground, UI.DISABLED_COLOR);
		this.action = action;
	}
	
	public Button(String text, int x, int y, int width, int height, Color normalColor, Color clickedColor, Color hoverBack, Color hoverFront, Action action) {
		super(x, y, width, height);
		this.text = text;
		normalBackground = normalColor;
		clickedBackground = clickedColor;
		backHoverColor = hoverBack;
		frontHoverColor = hoverFront;
		normal = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		clicked = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		hoveredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		disabled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		draw(normal, normalBackground, foreGroundColor);
		draw(clicked, clickedBackground, foreGroundColor);
		draw(hoveredImage, hoverBack, hoverFront);
		draw(disabled, normalBackground, UI.DISABLED_COLOR);
		this.action = action;
	}
	
	public Button(int x, int y, int width, int height, Action action) {
		super(x, y, width, height);
		this.text = "";
		normalBackground = Color.white;
		clickedBackground = new Color(200,200,200);
		backHoverColor = clickedBackground;
		frontHoverColor = normalBackground;
		normal = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		clicked = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		hoveredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		disabled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.action =action;
	}

	protected void draw(BufferedImage img, Color backgroundColor, Color foreGroundColor) {
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color background = backgroundColor;
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		drawBorder(img, g);
		g.setFont(baseFont);
		enabled=true;
		drawText(g, text, foreGroundColor);
	}

	//hovered is only used for overriding
	protected void drawText(Graphics2D g, String text, Color textColor){
		g.setColor(textColor);
		FontMetrics fm = g.getFontMetrics();
		int fheight = fm.getHeight();
		GuiUtilities.centerText(g, text, getWidth(), (getHeight()+fheight)/2-fm.getDescent());
	}
	
	public void setHover(boolean b){
		hovered = b;
		draw();
		setMarkedForUpdate(true);
	}
	
	protected void drawBorder(BufferedImage img, Graphics2D g){
		g.setColor(Color.black);
		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 9, 9);
	}
	
	@Override
	public void draw() {
		if(isClicked)image = clicked;
		else if(!enabled)image = disabled;
		else if(hovered)image = hoveredImage;
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
		draw(normal, normalBackground, foreGroundColor);
		draw(clicked, clickedBackground, foreGroundColor);
		draw(hoveredImage, backHoverColor, frontHoverColor);
		draw(disabled, normalBackground, new Color(180,180,180));
		setMarkedForUpdate(true);
	}
	
	public void setEnabled(boolean b){
		enabled = b;
		setMarkedForUpdate(true);
	}

}
