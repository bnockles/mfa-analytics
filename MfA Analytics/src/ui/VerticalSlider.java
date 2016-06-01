package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;

public class VerticalSlider extends VisibleComponent{

	private String name;
	private Action action;
	private double max;
	private double min;
	private double value;
	private boolean dragging;

	public static final int TOP_MARGIN = 25;
	public static final int BOTTOM_MARGIN = 10;
	public static final int SLIDER_WIDTH = 60;
	
	
	private int _Y_MAX;
	private int _Y_MIN;
	private int _LENGTH;

	private static final int _SLIDER_HEIGHT = 20;
	private static final Color _SLOT_COLOR = new Color(120,120,120);
	private static final Color _SLIDER_COLOR = new Color(200,200,200);


	public VerticalSlider(String name, int x, int y, int height,Action action) {
		super(x,y,SLIDER_WIDTH,height);
		this.action = action;
		this.name = name;
		baseFont =baseFont.deriveFont(14f);
		g.setFont(baseFont);
		value = 0.0;
		max = 1.0;
		min = -1.0;
		_Y_MAX = 5 + TOP_MARGIN;
		_Y_MIN = height-5;
		_LENGTH = _Y_MIN - _Y_MAX;
		dragging = false;
		update();
	}

	public void setMax(double max){
		this.max = max;
	}

	public void setMin(double min){
		this.min = min;
	}

	public double getValue(){
		return value;
	}

	@Override
	public void draw() {
		g.setColor(backGroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(foreGroundColor);
		GuiUtilities.centerText(g, name, getWidth(), 26);
		int sliderWidth = 6;
		g.setColor(_SLOT_COLOR);
		int pixelIncrement = (_Y_MIN-_Y_MAX)/10;
		double increment = (max-min)/10;
		int n = 0;
		for(int i = _Y_MIN; i > _Y_MAX; i -= pixelIncrement){
			g.drawString(GuiUtilities.formatTenths(min + n*increment), 2, i);
			n++;
		}
		g.fillRoundRect((getWidth()-sliderWidth)/2, _Y_MAX, sliderWidth, getHeight()-TOP_MARGIN-BOTTOM_MARGIN*2,3,3);

		g.setColor(_SLIDER_COLOR);
		int spaceFromEdge = 5;
		g.fillRoundRect(spaceFromEdge, getSliderCoordinate(), getWidth()-2*spaceFromEdge-1, _SLIDER_HEIGHT-1, 5, 5);
		g.setColor(foreGroundColor);
	}

	private int getSliderCoordinate() {
		return (int) (_Y_MAX+_LENGTH*(max-value)/(max-min))-_SLIDER_HEIGHT/2;
	}


	private double getSliderValue(int y) {
		int difference = y - _Y_MAX;
		double v = max -(max-min)*difference/(_Y_MIN-_Y_MAX);
		if(v > max)return max;
		else if(v<min) return min;
		else return v;
	}

	public void setDragging(boolean b){
		dragging = b;
	}
	
	public void notifyDrag(int ry) {
		int sliderY= getSliderCoordinate();
		if(dragging || ry > sliderY && ry < sliderY + _SLIDER_HEIGHT){
			dragging = true;
			value = getSliderValue(ry);
			update();
			action.act();
			setMarkedForUpdate(true);
		}
	}



}
