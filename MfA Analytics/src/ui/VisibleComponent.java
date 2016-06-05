package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class VisibleComponent implements Visible {

	private int x;
	private int y;
	private boolean markedForUpdate;
	private boolean visible;

	protected Graphics2D g;
	protected BufferedImage image;
	protected Color backGroundColor;
	protected Color foreGroundColor;
	protected Font baseFont;
	
	public VisibleComponent(int x, int y, int width, int height){
		initImage(width,height);
		this.x = x;
		this.y = y;
		visible = true;
		backGroundColor = Color.white;
		foreGroundColor = Color.black;
	}
	
	public void initImage(int width, int height){
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D)image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		InputStream is = VisibleComponent.class.getResourceAsStream("/AdventPro-Medium.ttf");
		try {
			Font font= Font.createFont(Font.TRUETYPE_FONT, is);
			baseFont=font.deriveFont(18f);
		} catch (Exception e) {
			baseFont = new Font("Avenir",Font.PLAIN,14);
			e.printStackTrace();
		}
		
		g.setFont(baseFont);
	}
	
	public VisibleComponent(){
		image = null;
		this.x = 0;
		this.y = 0;
		visible = true;
		backGroundColor = Color.white;
		foreGroundColor = Color.black;
	}
	
	
	@Override
	public BufferedImage getImage() {
		return image;
	}
	@Override
	public final void update(){
			draw();
		setMarkedForUpdate(false);
	}
	
	
	@Override
	public int getX() {
		return x;
	}
	@Override
	public int getY() {
		return y;
	}
	
	
	public void setX(int x) {
		this.x =x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setBaseFont(Font f){
		baseFont = f;
	}
	
	
	
	public int getWidth(){
		return image.getWidth();
	}
	
	public int getHeight(){
		return image.getHeight();
	}
	
	public boolean markedForUpdate(){
		return markedForUpdate;
	}
	
	public void setMarkedForUpdate(boolean b){
		markedForUpdate = b;
	}
	
	public void setVisible(boolean b){
		visible = b;
		setMarkedForUpdate(true);
	}
	
	public boolean isVisible(){
		return visible;
	}
	

	public void setBackGroundColor(Color backGroundColor) {
		this.backGroundColor = backGroundColor;
	}



	public void setForeGroundColor(Color foreGroundColor) {
		this.foreGroundColor = foreGroundColor;
	}
}
